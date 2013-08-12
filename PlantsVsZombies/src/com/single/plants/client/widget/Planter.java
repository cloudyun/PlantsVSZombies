package com.single.plants.client.widget;

import com.framework.client.event.BaseEvent;
import com.framework.client.util.Point;
import com.framework.client.widget.Component;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.single.plants.client.core.Pauseable;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.PlantEvent;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.model.plant.Plant;
import com.single.plants.client.util.PVZUtil;

public abstract class Planter extends Component implements PVZResources ,Pauseable{

	public int row;
	public int column;
	
	protected Plant plant;
	protected Card card;
	
	protected ImageResource gif;
	protected ImageElement img;
	
	protected int hp=5;
	
	public Planter(Plant plant,ImageResource gif){
		super("planter");
		this.plant=plant;
		this.gif=gif;
		Element div=DOM.createDiv();
		img=DOM.createImg().cast();
		img.setSrc(gif.getURL());
		div.appendChild(img);
		setElement(div);
	}
	/**
	 * 被攻击减血
	 * @param power
	 * @return 是否死亡
	 */
	public boolean reduceHp(int power){
		hp-=power;
		if(hp<=0){
			stop();
			removeFromParent();
			return true;
		}
		fireEvent(PVZEvent.OnHpChange, new PlantEvent(this));
		return false;
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		fixPosition();
	}
	
	public abstract void start();
	public abstract void stop();
	/**
	 * 根据自身的大小，调整位置
	 */
	public void fixPosition(){
		Point point=PVZUtil.convertToPiexl(row, column);
		el().setLeft(point.x-gif.getWidth()/2);
		el().setTop(point.y-gif.getHeight()/2);
	}
	

	public Plant getPlant() {
		return plant;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	
	@Override
	public void pause() {
		fireEvent(PVZEvent.Pause, new BaseEvent(PVZEvent.Pause));
	}
	
	@Override
	public void resume() {
		fireEvent(PVZEvent.Resume, new BaseEvent(PVZEvent.Resume));
	}

}
