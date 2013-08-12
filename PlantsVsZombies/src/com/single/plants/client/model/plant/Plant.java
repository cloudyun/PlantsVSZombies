package com.single.plants.client.model.plant;

import com.google.gwt.dom.client.ImageElement;
import com.single.plants.client.widget.Card;
import com.single.plants.client.widget.Planter;

/**
 * 代表一种植物
 * @author xuhengfei email:yonglin4605@163.com
 * @version test 2010-7-6
 */
public interface Plant {

	String getId();
	
	String getName();
	
	String getDescription();
	
	int getPrice();
	
	int getFreezeTime();
	
	/**
	 * 植物卡片的Element元素
	 * @return
	 */
	ImageElement createCardElement();
	/**
	 * 创建一张植物卡片
	 * @return
	 */
	Card createCard();
	/**
	 * 创建一个植物
	 */
	Planter createPlanter();
	
	Planter getGhostPlanter();
	Planter getGhostPlanter2();
}
