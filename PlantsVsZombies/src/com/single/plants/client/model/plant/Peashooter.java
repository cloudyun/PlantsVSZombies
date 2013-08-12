package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.plant.PeashooterWidget;


public class Peashooter extends AbstractPlant{
	private static Peashooter peashooter;
	public static Peashooter get(){
		if(peashooter==null){
			peashooter=new Peashooter();
		}
		return peashooter;
	}
	{
		id="Peashooter";
		name="豌豆射手";
		price=100;
		desc="";
		freeze=5000;
	}

	private Peashooter(){}
	

	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.peashooter());
	}

	@Override
	public Planter createPlanter() {
		return new PeashooterWidget(this);
	}
}
