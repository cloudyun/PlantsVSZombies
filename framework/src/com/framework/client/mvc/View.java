package com.framework.client.mvc;

import com.framework.client.event.EventType;


public abstract class View {

  protected Controller controller;
  protected boolean initialized;

  public View(Controller controller) {
    this.controller = controller;
  }

  public Controller getController() {
    return controller;
  }

  protected void fireEvent(AppEvent event) {
    Controller c = controller;
    while (c != null) {
      if (c.canHandle(event)) {
        c.handleEvent(event);
      }
      c = c.parent;
    }
  }

  protected void fireEvent(EventType eventType) {
    fireEvent(new AppEvent(eventType));
  }

  protected abstract void handleEvent(AppEvent event);

  protected void initialize() {

  }

}
