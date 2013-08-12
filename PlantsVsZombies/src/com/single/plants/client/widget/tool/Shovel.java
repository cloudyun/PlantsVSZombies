package com.single.plants.client.widget.tool;

import com.framework.client.Framework;
import com.framework.client.widget.Component;
import com.google.gwt.user.client.DOM;
import com.single.plants.client.model.PVZResources;
/**
 * 铁铲
 */
public class Shovel extends Component implements PVZResources{

	public Shovel(){
		super("shovel");
		setElement(DOM.createDiv());
		el().setZIndex(Framework.getTopZIndex());
		getElement().setInnerHTML("<img src='"+toolResource.shovel().getURL()+"' />");
	}
	
	public String getName(){
		return "铁铲";
	}
	public String getDescription(){
		return "让你挖出一株植物，腾出空间给其他植物";
	}
	
	private Shovel ghost;
	public Shovel getGhost(){
		if(ghost==null){
			ghost=new Shovel();
		}
		return ghost;
	}
}
