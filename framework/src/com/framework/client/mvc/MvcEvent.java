package com.framework.client.mvc;

import com.framework.client.event.BaseEvent;


public class MvcEvent extends BaseEvent {

  private AppEvent appEvent;
  private Dispatcher dispatcher;
  private String name;

  public MvcEvent(Dispatcher d, AppEvent ae) {
    super(d);
    this.dispatcher = d;
    this.appEvent = ae;
  }

  public AppEvent getAppEvent() {
    return appEvent;
  }

  public Dispatcher getDispatcher() {
    return dispatcher;
  }

  public String getName() {
    return name;
  }

  public void setAppEvent(AppEvent appEvent) {
    this.appEvent = appEvent;
  }

  public void setDispatcher(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  public void setName(String name) {
    this.name = name;
  }

}
