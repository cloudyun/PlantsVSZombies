package com.framework.client.widget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasHTML;

public class HtmlComponent extends Component implements HasHTML{

	public HtmlComponent(String html){
		Element div=DOM.createDiv();
		div.setInnerHTML(html);
		setElement(div);
	}
	@Override
	public String getHTML() {
		return getElement().getInnerHTML();
	}

	@Override
	public void setHTML(String html) {
		getElement().setInnerHTML(html);
	}

	@Override
	public String getText() {
		return getElement().getInnerText();
	}

	@Override
	public void setText(String text) {
		getElement().setInnerText(text);
	}

}
