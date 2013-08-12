package com.single.plants.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.client.core.El;
import com.framework.client.fx.Fx;
import com.framework.client.fx.FxConfig;
import com.framework.client.fx.SingleStyleEffect;
import com.framework.client.widget.Component;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.single.plants.client.model.plant.Plant;

public class Card extends Component{

	protected boolean freezing=false;
	protected boolean masking=false;
	/** 是否可以拖出植物 */
	protected boolean generate=true;
	private El mask;
	private El freezeEl;
	private El image;
	
	protected Card(ImageElement img){
		super("card");
		this.image=new El((Element) img.cast());
		Element div=DOM.createDiv();
		div.appendChild(img);
		freezeEl=new El(DOM.createDiv());
		freezeEl.setZIndex(this.image.getZIndex()+1);
		freezeEl.setWidth(50);
		div.appendChild(freezeEl.dom);
		
		mask=new El(DOM.createDiv());
		mask.setWidth(50);
		mask.setHeight(70);
		mask.setZIndex(freezeEl.getZIndex()+1);
		mask.setVisibility(false);
		div.appendChild(mask.dom);
		
		setElement(div);
	}
	
	private Plant plant;
	public Card(Plant p){
		this(p.createCardElement());
		this.plant=p;
	}
	/**
	 * 冷却时间的动画效果
	 */
	class Unfreeze extends SingleStyleEffect{
		public Unfreeze(El el,int from,int to){
			super(el,"height",from,to);
		}
		@Override
		public void onStart() {
			el.setStyleAttribute(style, from);
			freezing=true;
		}
		@Override
		public void onComplete() {
			el.setStyleAttribute(style, to);
			freezing=false;
		}
		@Override
		public void increase(double value) {
			el.setStyleAttribute(style, value+"px");
		}
	}
	
	public void mask(){
		masking=true;
		mask.setVisible(true);
	}
	public void unmask(){
		masking=false;
		mask.setVisible(false);
	}
	/**
	 * 冻结卡片，开始解冻动画
	 */
	public void unfreezing(){
		FxConfig config=new FxConfig(getFreeze());
		Fx fx=new Fx(config);
		fx.run(new Unfreeze(freezeEl, image.getHeight(),0));
	}
	
	@Override
	public void onBrowserEvent(Event event) {
		if(generate){
			if(Event.ONCLICK==event.getTypeInt()){
				if(freezing||masking){
					return ;
				}
			}
		}
		super.onBrowserEvent(event);
	}

	public int getPrice() {
		return plant.getPrice();
	}
	
	public int getFreeze(){
		return plant.getFreezeTime();
	}

	public Planter createPlanter(){
		Planter p=plant.createPlanter();
		p.setCard(this);
		return p;
	}
	/**种植植物时，预览效果的半透明植物*/
	private static Map<String, Planter> ghostMap=new HashMap<String, Planter>();
	/**种植植物时，预览效果的鼠标跟随植物*/
	private static Map<String, Planter> mouseGhostMap=new HashMap<String, Planter>();
	
	public Planter getGhostPlanter(){
		Planter p=ghostMap.get(plant.getId());
		if(p==null){
			p=plant.createPlanter();
			ghostMap.put(plant.getId(), p);
		}
//		planter.el().setStyleAttribute("position", "absolute");
		return p;
	}
	
	public Planter getMouseGhost(){
		Planter p=mouseGhostMap.get(plant.getId());
		if(p==null){
			p=plant.createPlanter();
			mouseGhostMap.put(plant.getId(), p);
		}
//		planter.el().setStyleAttribute("position", "absolute");
		return p;
	}

	public void setGenerate(boolean generate) {
		this.generate = generate;
	}

	public Plant getPlant() {
		return plant;
	}

	public boolean isMasking() {
		return masking;
	}

	public boolean isFreezing() {
		return freezing;
	}

	public static Map<String, Planter> getGhostMap() {
		return ghostMap;
	}

	public static Map<String, Planter> getMouseGhostMap() {
		return mouseGhostMap;
	}
	
	
}
