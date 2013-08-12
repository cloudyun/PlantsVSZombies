package com.single.plants.client.model.plant;

import com.framework.client.event.BaseObservable;
import com.framework.client.util.Size;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.widget.Card;
import com.single.plants.client.widget.Planter;

/**
 * 代表一种植物
 * @author xuhengfei email:yonglin4605@163.com
 * @version test 2010-7-6
 */
public abstract class AbstractPlant extends BaseObservable implements Plant  ,PVZResources{

	/** 血量 */
	protected int hp=300;
	
	protected String id;
	protected String name;
	protected String desc;
	/**
	 * 所需阳光数
	 */
	protected int price;
	/**
	 * 冻结时间
	 */
	protected int freeze;

	protected Size size;
	
	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return desc;
	}
	
	public int getPrice(){
		return price;
	}
	
	public int getFreezeTime(){
		return freeze;
	}
	
	protected ImageElement createImageTemplate(ImageResource resource){
		ImageElement image=DOM.createImg().cast();
		image.setSrc(resource.getURL());
		return image;
	}
	
	@Override
	public Card createCard() {
		Card card=new Card(this);
		return card;
	}
	
	
	protected Planter ghost;
	protected Planter ghost2;
	@Override
	public Planter getGhostPlanter() {
		if(ghost==null){
			ghost=createPlanter();
		}
		return ghost;
	}
	@Override
	public Planter getGhostPlanter2() {
		if(ghost2==null){
			ghost2=createPlanter();
		}
		return ghost2;
	}
}
