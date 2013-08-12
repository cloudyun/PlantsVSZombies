package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.plant.PotatoMiner;

/**
 * 土豆雷
 */
public class PotatoMine extends AbstractPlant {
	private static PotatoMine potatoMine;
	public static PotatoMine get(){
		if(potatoMine==null){
			potatoMine=new PotatoMine();
		}
		return potatoMine;
	}
	{
		id="PotatoMine";
		name="土豆地雷";
		price=25;
		desc="敌人靠近后爆炸，但需要安放时间";
		freeze=20000;
	}

	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.potatoMine());
	}
	
	@Override
	public Planter createPlanter() {
		return new PotatoMiner(this);
	}

}
