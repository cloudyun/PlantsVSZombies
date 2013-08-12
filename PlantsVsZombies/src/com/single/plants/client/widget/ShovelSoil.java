package com.single.plants.client.widget;

import com.framework.client.Framework;
import com.framework.client.widget.Container;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.widget.tool.Shovel;

public class ShovelSoil extends Container<Shovel> implements PVZResources{

	private Shovel shovel;
	public ShovelSoil(){
		this(false);
	}
	
	public ShovelSoil(boolean enable){
		setElement(DOM.createDiv());
		if(enable){
			shovel=new Shovel();
			add(shovel);
		}
	}
	
	
	
	@Override
	protected void onAttach() {
		super.onAttach();
		el().dom.getStyle().setPosition(Position.ABSOLUTE);
		el().setSize(70, 72);
		el().setLeft(600);
		el().setTop(0);
		el().setZIndex(Framework.getTopZIndex());
		el().setStyleAttribute("backgroundImage", "url("+toolResource.soil().getURL()+")");
	}

	public Shovel getShovel() {
		return shovel;
	}
}
