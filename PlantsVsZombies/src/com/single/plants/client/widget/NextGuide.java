package com.single.plants.client.widget;

import com.framework.client.Framework;
import com.framework.client.mvc.Dispatcher;
import com.framework.client.util.XDOM;
import com.framework.client.widget.Component;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.resources.MusicList;
import com.single.plants.client.util.MusicConfig;

public class NextGuide extends Component implements PVZResources{

	private static NextGuideUiBinder uiBinder = GWT
			.create(NextGuideUiBinder.class);

	interface NextGuideUiBinder extends UiBinder<Element, NextGuide> {
	}

	
	@UiField
	Element title;
	@UiField
	Element name;
	@UiField
	Element content;
	@UiField
	Element cardElement;
	@UiField
	Element next;
	public NextGuide(Card card) {
		super("nextguide");
		setElement(uiBinder.createAndBindUi(this));
		
		card.removeFromParent();
		card.getElement().removeAttribute("style");
		name.setInnerHTML(card.getPlant().getName());
		content.setInnerHTML(card.getPlant().getDescription());
		cardElement.appendChild(card.getElement());
		el().setZIndex(Framework.getTopZIndex()+100);
		
		sinkEvents(Event.ONCLICK);
	}
	
	@Override 
	protected void onAttach() {
		super.onAttach();
		Dispatcher.forwardEvent(PVZEvent.DisableBackgroundSound);
		Dispatcher.forwardEvent(PVZEvent.PlayMusic, new MusicConfig(MusicList.Winmusic));
	}
	
//	public NextGuide(Shovel shovel){
//		super("nextguide");
//		setElement(uiBinder.createAndBindUi(this));
//		
//		title.setInnerText("你得到一把铁铲!");
//		
//		shovel.removeFromParent();
//		shovel.getElement().removeAttribute("style");
//		name.setInnerText(shovel.getName());
//		content.setInnerHTML(shovel.getDescription());
//		cardElement.appendChild(shovel.getElement());
//		el().setZIndex(Framework.getTopZIndex()+100);
//		
//		sinkEvents(Event.ONCLICK);
//	}

	@Override
	public void onBrowserEvent(Event event) {
		if(XDOM.isOrHasChild(next,Element.as(event.getEventTarget()))){
			Dispatcher.forwardEvent(PVZEvent.GameStart);
		}
	}
}
