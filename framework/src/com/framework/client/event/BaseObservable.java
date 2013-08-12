package com.framework.client.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseObservable implements Observable {

	/** 是否可以被触发 */
	private boolean firesEvents = true;
	/** 是否正在处理中 */
	private boolean activeEvent=false;
	private Map<String, List<Listener<BaseEvent>>> listeners;

	@SuppressWarnings("unchecked")
	@Override
	public void addListener(EventType type,
			Listener<? extends BaseEvent> listener) {
		if (listener == null)
			return;
		if (listeners == null) {
			listeners = new HashMap<String, List<Listener<BaseEvent>>>();
		}
		String key = getKey(type);
		List<Listener<BaseEvent>> list = listeners.get(key);
		if (list == null) {
			list = new ArrayList<Listener<BaseEvent>>();
			list.add((Listener<BaseEvent>) listener);
			listeners.put(key, list);
		} else {
			if (!list.contains(listener)) {
				list.add((Listener<BaseEvent>) listener);
			}
		}
	}

	@Override
	public boolean fireEvent(EventType type, BaseEvent be) {
		if(firesEvents && listeners!=null){
			activeEvent=true;
			be.setType(type);
			
			List<Listener<BaseEvent>> list=listeners.get(getKey(type));
			if(list!=null){
				List<Listener<BaseEvent>> copy=new ArrayList<Listener<BaseEvent>>(list);
				for(Listener<BaseEvent> listener:copy){
					listener.handleEvent(be);
				}
			}
			activeEvent=false;
			return !be.isCancelled();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Listener<? extends BaseEvent>> getListeners(EventType type) {
		if (listeners == null) {
			return new ArrayList<Listener<? extends BaseEvent>>();
		}
		List<Listener<BaseEvent>> list=listeners.get(getKey(type));
		if(list==null){
			list=new ArrayList<Listener<BaseEvent>>();
		}
		return (List)list;
	}

	@Override
	public boolean hasListeners() {
		return listeners!=null && listeners.size()>0;
	}

	@Override
	public boolean hasListeners(EventType type) {
		if(listeners!=null){
			List<Listener<BaseEvent>>list=listeners.get(getKey(type));
			if(list!=null && !list.isEmpty()){
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeAllListeners() {
		if(listeners!=null){
			listeners.clear();
		}
	}
	@Override
	public void removeListener(EventType type,
			Listener<? extends BaseEvent> listener) {
		if(listeners!=null){
			List<Listener<BaseEvent>> list=listeners.get(getKey(type));
			if(list!=null){
				list.remove(listener);
				if(list.isEmpty()){
					listeners.remove(getKey(type));
				}
			}
		}
	}

	public String getKey(EventType type) {
		return type.id;
	}

	public boolean isFiresEvents() {
		return firesEvents;
	}

	public void setFiresEvents(boolean firesEvents) {
		this.firesEvents = firesEvents;
	}

	public boolean isActiveEvent() {
		return activeEvent;
	}
}
