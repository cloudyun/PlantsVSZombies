package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Card;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.plant.CherryBomber;
/**
 * 樱桃炸弹
 */
public class CherryBomb extends AbstractPlant {

	private static CherryBomb cherryBomb;

	public static CherryBomb get() {
		if (cherryBomb == null) {
			cherryBomb = new CherryBomb();
		}
		return cherryBomb;
	}

	{
		id = "CherryBomb";
		name = "樱桃炸弹";
		price = 150;
		desc = "一定区域内对僵尸产生巨大的伤害";
		freeze = 5000;
	}

	private CherryBomb() {
	}

	@Override
	public ImageElement createCardElement() {
		return createImageTemplate(plantsCard.cherryBomb());
	}

	public Planter createPlanter() {
		return new CherryBomber(this);
	};
}
