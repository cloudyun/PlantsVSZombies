package com.single.plants.client.widget;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.DomEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.event.PreviewEvent;
import com.framework.client.mvc.AppEvent;
import com.framework.client.mvc.Dispatcher;
import com.framework.client.util.BaseEventPreview;
import com.framework.client.util.XDOM;
import com.framework.client.widget.Container;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.event.CardEvent;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.ShineEvent;
import com.single.plants.client.model.PVZResources;
/**
 * 游戏顶部的卡片栏
 * 
 * 支持的事件：
 * PVZEvent.OnSunCollect  当收集到阳光时触发
 * PVZEvent.CardDragStart 当卡片被点击
 *
 */
public class TopCards extends Container<Card> implements PVZResources{

	private static TopCardsUiBinder uiBinder = GWT.create(TopCardsUiBinder.class);

	interface TopCardsUiBinder extends UiBinder<Element, TopCards> {
	}
	@UiField
	DivElement list;
	@UiField
	DivElement collect;
	/** 容器最大接收个数 */
	private int limit=10;
	private int collection=100;
	
	public TopCards() {
		super("topcards");
		setElement(uiBinder.createAndBindUi(this));
	}
	@Override
	protected void onLoad() {
		collect.setInnerHTML(String.valueOf(collection));
	}
	protected com.google.gwt.user.client.Element getListElement(){
		return list.cast();
	}
	
	public void addCollect(int add){
		collection+=add;
		collect.setInnerHTML(String.valueOf(collection));
		ShineEvent se=new ShineEvent();
		se.setTotalShine(collection);
		se.setChange(add);
		fireEvent(PVZEvent.OnSunCollectChange,se);
	}
	public void reduceCollect(int reduce){
		collection-=reduce;
		collect.setInnerHTML(String.valueOf(collection));
		
		ShineEvent se=new ShineEvent();
		se.setTotalShine(collection);
		se.setChange(reduce*(-1));
		fireEvent(PVZEvent.OnSunCollectChange,se);
	}
	
	@Override
	public boolean add(final Card item) {
		boolean success=super.add(item,getListElement());
		if(success&&isAttached()){
			item.unfreezing();
		}
		return success;
	}
	
	@Override
	protected boolean insert(Card item, int index,
			com.google.gwt.user.client.Element container) {
		if(getItemCount()>limit){
			return false;
		}
		return super.insert(item, index, container);
	}
	
	@Override
	public boolean remove(Card item) {
		return super.remove(item);
	}

	@Override
	public boolean removeAll() {
		return super.removeAll();
	}
	public int getCollection() {
		return collection;
	}
	public void setCollection(int collection) {
		this.collection = collection;
	}
	
	@Override
	public void onBrowserEvent(Event event) {
		EventTarget target=event.getEventTarget();
		if(event.getTypeInt()==Event.ONCLICK){
			for(Card card:getItems()){
				if(XDOM.isOrHasChild(card.getElement(), Element.as(target))){
					CardEvent ce=new CardEvent(card);
					ce.setEvent(event);
					fireEvent(EventContant.OnClick, ce);
					break;
				}
			}
			return ;
		}
			
		
		super.onBrowserEvent(event);
	}
}
