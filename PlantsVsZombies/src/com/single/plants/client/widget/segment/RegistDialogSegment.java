package com.single.plants.client.widget.segment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;

public class RegistDialogSegment  {

	private static RegistDialogUiBinder uiBinder=GWT.create(RegistDialogUiBinder.class);
	interface RegistDialogUiBinder extends UiBinder<Element, RegistDialogSegment>{}
	
	@UiField
	InputElement value;
	
	Element root;
	public RegistDialogSegment(){
		root=uiBinder.createAndBindUi(this);
	}
	
	public Element getElement(){
		return root;
	}
	
	public InputElement getInputElement(){
		return value;
	}
}
