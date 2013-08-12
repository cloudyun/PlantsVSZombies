package com.framework.client.widget;

import java.util.List;

import com.framework.client.core.El;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.BaseObservable;
import com.framework.client.event.DomEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.EventType;
import com.framework.client.event.Listener;
import com.framework.client.event.Observable;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class Component extends Widget implements Observable {

	private Observable observable;

	private El el;

	private String baseStyle;

	public El el() {
		return el;
	}

	public Component() {
		this("component");
	}

	public Component(String baseStyle) {
		this.baseStyle = baseStyle;
		observable = createObservable();
	}

	@Override
	protected void onAttach() {
		if(baseStyle!=null){
			getElement().addClassName(baseStyle);
		}
		super.onAttach();
	}

	protected Observable createObservable() {
		return new BaseObservable();
	}

	@Override
	public void addListener(EventType type,
			Listener<? extends BaseEvent> listener) {
		if (type.getEventCode() != -1) {
			sinkEvents(type.getEventCode());
		}
		observable.addListener(type, listener);
	}

	@Override
	public boolean fireEvent(EventType type, BaseEvent be) {
		return observable.fireEvent(type, be);
	}

	@Override
	public List<Listener<? extends BaseEvent>> getListeners(EventType type) {
		return observable.getListeners(type);
	}

	@Override
	public boolean hasListeners() {
		return observable.hasListeners();
	}

	@Override
	public boolean hasListeners(EventType type) {
		return observable.hasListeners(type);
	}

	@Override
	public void removeAllListeners() {
		observable.removeAllListeners();
	}

	@Override
	public void removeListener(EventType type,
			Listener<? extends BaseEvent> listener) {
		observable.removeListener(type, listener);
	}

	public void onBrowserEvent(Event event) {
		int type = DOM.eventGetType(event);
		DomEvent be = new DomEvent(this,event);
		be.setSource(event.getEventTarget());
		fireEvent(EventContant.lookupBrowserEvent(type), be);
		com.google.gwt.event.dom.client.DomEvent.fireNativeEvent(event, this, getElement());
	}

	public void setZIndex(int zIndex) {
		DOM.setIntStyleAttribute(getElement(), "zIndex", Math.max(0, zIndex));
	}

	@Override
	protected void setElement(Element elem) {
		el = new El(elem);
		super.setElement(elem);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeFromParent() {
		if(getParent() instanceof Container){
			((Container)getParent()).remove(this);
		}
		super.removeFromParent();
	}
	/**
	 * 隐藏
	 */
	public void hide(){
		el.setDisplayed(false);
	}
	public void show(){
		el.setDisplayed(true);
	}
	/**
	 * 使用JSNI的方式破除访问权限的问题
	 * @param parent
	 */
	public native void setParent(Widget parent) /*-{
		this.@com.google.gwt.user.client.ui.Widget::setParent(Lcom/google/gwt/user/client/ui/Widget;)(parent);
	}-*/;
}
