package com.single.plants.client.widget.tool;

import com.framework.client.widget.Component;
import com.google.gwt.user.client.DOM;

public class Hint extends Component  {

	public Hint(){
		super("hint");
		setElement(DOM.createDiv());
	}
	
	public void setText(String text){
		getElement().setInnerText(text);
	}

}
