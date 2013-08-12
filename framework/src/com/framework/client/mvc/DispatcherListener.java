package com.framework.client.mvc;

import com.framework.client.event.EventType;
import com.framework.client.event.Listener;


public class DispatcherListener implements Listener<MvcEvent> {

  public void handleEvent(MvcEvent e) {
    EventType type = e.getType();
    if (type == Dispatcher.BeforeDispatch) {
      beforeDispatch(e);
    } else if (type == Dispatcher.AfterDispatch) {
      afterDispatch(e);
    }
  }

  public void beforeDispatch(MvcEvent mvce) {

  }

  public void afterDispatch(MvcEvent mvce) {

  }

}
