package com.framework.client.mvc;

import java.util.HashMap;
import java.util.Map;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventType;


public class AppEvent extends BaseEvent {

  private Object data;
  private Map<String, Object> dataMap;
  private boolean historyEvent;
  private String token;

  public AppEvent(EventType type) {
    super(type);
  }

  public AppEvent(EventType type, Object data) {
    super(type);
    this.data = data;
  }

  public AppEvent(EventType type, Object data, String token) {
    this(type, data);
    this.token = token;
    historyEvent = true;
  }

  @SuppressWarnings("unchecked")
  public <X> X getData() {
    return (X) data;
  }

  @SuppressWarnings("unchecked")
  public <X> X getData(String key) {
    if (dataMap == null) return null;
    return (X) dataMap.get(key);
  }

  public String getToken() {
    return token;
  }

  public boolean isHistoryEvent() {
    return historyEvent;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public void setData(String key, Object data) {
    if (dataMap == null) dataMap = new HashMap<String,Object>();
    dataMap.put(key, data);
  }

  public void setHistoryEvent(boolean historyEvent) {
    this.historyEvent = historyEvent;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String toString() {
    return "Event Type: " + getType();
  }
}
