package com.single.plants.client.event;

import com.framework.client.event.DomEvent;
import com.framework.client.event.EventType;
import com.single.plants.client.widget.Card;

public class CardEvent extends DomEvent{

	private Card card;
	
	public CardEvent(Card card){
		super(card);
		this.card=card;
	}
	
	public CardEvent(EventType type) {
		super(type);
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

}
