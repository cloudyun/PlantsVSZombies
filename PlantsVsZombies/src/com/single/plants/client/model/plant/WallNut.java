package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.plant.WallNuter;

/**
 * 坚果墙
 */
public class WallNut extends AbstractPlant {

	private static WallNut wallNut;
	public static WallNut get(){
		if(wallNut==null){
			wallNut=new WallNut();
		}
		return wallNut;
	}
	{
		id="WallNut";
		name="坚果墙";
		price=50;
		desc="阻碍僵尸前进，并保护你其他的植物";
		freeze=10000;
	}
	
	private WallNut(){}
	
	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.wallNut());
	}
	
	@Override
	public Planter createPlanter() {
		return new WallNuter(this);
	}

}
