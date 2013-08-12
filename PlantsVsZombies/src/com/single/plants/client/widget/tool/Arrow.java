package com.single.plants.client.widget.tool;

import com.framework.client.Framework;
import com.framework.client.widget.Component;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.model.PVZResources;

/**
 * 箭头指示
 */
public class Arrow extends Component implements PVZResources{

	public enum Arrowhead {
		UP,DOWN;
	}
	public Arrow(Arrowhead head){
		super("arrow");
		setElement(DOM.createDiv());
		switch(head){
		case UP:
			getElement().setInnerHTML("<img src='"+toolResource.pointerUp().getURL()+"' />");
			break;
		case DOWN:
			getElement().setInnerHTML("<img src='"+toolResource.pointerDown().getURL()+"' />");
			break;
		}
		
	}
	public void setXY(int x,int y){
		el().setZIndex(Framework.getTopZIndex());
		el().setXY(x, y);
	}
	
	
}
