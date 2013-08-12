package com.single.plants.client.model.weapon;

import com.framework.client.event.BaseObservable;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.core.Coordinate;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.WeaponEvent;
import com.single.plants.client.resources.weapon.WeaponResource;

/**
 * 攻击武器
 *
 */
public abstract class Weapon extends BaseObservable{

	protected static final WeaponResource resource=GWT.create(WeaponResource.class);
	
	protected String id;
	protected String name;
	/** 威力  */
	protected int power;
	/** 坐标 */
	protected Coordinate coordinate;
	
	protected Coordinate offset=Coordinate.EMPTY;
	
	protected ImageResource image;
	
	protected ImageElement imageElement;
	
	protected abstract ImageResource getImageResource();

	
	public void move(){
		coordinate.x+=10;
		if(coordinate.x>2000){
			destory();
		}
	}
	
	public void destory(){
		WeaponEvent we=new WeaponEvent(PVZEvent.OnWeaponDisappear, this);
		fireEvent(PVZEvent.OnWeaponDisappear, we);
	}
	


	public Coordinate getCoordinate() {
		return coordinate;
	}


	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}


	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public Coordinate getOffset() {
		return offset;
	}


	public void setOffset(Coordinate offset) {
		this.offset = offset;
	}
	


	public int getPower() {
		return power;
	}
	
	public Coordinate getAbsoluteXY(){
		Coordinate c=new Coordinate();
		c.x=coordinate.x+offset.x;
		c.y=coordinate.y+offset.y;
		return c;
	}
}
