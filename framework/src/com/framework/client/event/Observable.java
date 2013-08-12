package com.framework.client.event;

import java.util.List;

public interface Observable {

	public void addListener(EventType type,Listener<? extends BaseEvent> listener);
	
	public void removeListener(EventType type,Listener<? extends BaseEvent> listener);
	
	public void removeAllListeners();
	
	public boolean hasListeners();
	
	public boolean hasListeners(EventType type);
	
	public List<Listener<? extends BaseEvent>> getListeners(EventType type);
	/**
	 * 触发事件
	 * @param type
	 * @param be
	 */
	public boolean fireEvent(EventType type,BaseEvent be);
}
