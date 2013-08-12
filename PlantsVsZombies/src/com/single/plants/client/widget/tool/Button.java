package com.single.plants.client.widget.tool;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.widget.Component;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class Button extends Component{

	private String text;
	public Button(String text){
		super("button");
		this.text=text;
		String html="<table cellspacing='0'><tr><td class='buttonleft'></td><td class='buttonmiddle'></td><td class='buttonright'></td></tr></table>";
		setElement(DOM.createDiv());
		getElement().setInnerHTML(html);
	}
	
	@Override
	protected void onLoad() {
		Element content=DOM.createDiv();
		content.addClassName("text");
		content.setInnerHTML(text);
		getElement().appendChild(content);
		addListener(EventContant.OnMouseDown, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				Button.this.addStyleDependentName("pressed");
			}
		});
		addListener(EventContant.OnMouseUp, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				Button.this.removeStyleDependentName("pressed");
			}
		});
		addListener(EventContant.OnMouseOut, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				Button.this.removeStyleDependentName("pressed");
			}
		});
	}
	
	@Override
	protected void onUnload() {
		removeAllListeners();
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
