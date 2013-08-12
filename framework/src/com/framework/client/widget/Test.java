package com.framework.client.widget;

import java.util.List;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventType;
import com.framework.client.event.Listener;
import com.framework.client.event.Observable;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;

public class Test extends Composite implements Observable{

	private static TestUiBinder uiBinder = GWT.create(TestUiBinder.class);

	interface TestUiBinder extends UiBinder<HtmlComponent, Test> {
	}

	private HtmlComponent component;
	public Test() {
		component=uiBinder.createAndBindUi(this);
		initWidget(component);
	}


	@Override
	public void addListener(EventType type,
			Listener<? extends BaseEvent> listener) {
		component.addListener(type, listener);
	}


	@Override
	public boolean fireEvent(EventType type, BaseEvent be) {
		return component.fireEvent(type, be);
	}


	@Override
	public List<Listener<? extends BaseEvent>> getListeners(EventType type) {
		return component.getListeners(type);
	}


	@Override
	public boolean hasListeners() {
		return component.hasListeners();
	}


	@Override
	public boolean hasListeners(EventType type) {
		return component.hasListeners(type);
	}


	@Override
	public void removeAllListeners() {
		component.removeAllListeners();
	}


	@Override
	public void removeListener(EventType type,
			Listener<? extends BaseEvent> listener) {
		component.removeListener(type, listener);
	}


	public HtmlComponent getComponent() {
		return component;
	}


}
