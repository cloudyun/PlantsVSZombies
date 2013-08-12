package com.framework.client.mvc;

import java.util.ArrayList;
import java.util.List;

import com.framework.client.event.EventType;


public abstract class Controller {

  protected Controller parent;
  protected List<Controller> children;
  protected boolean initialized;

  private List<EventType> supportedEvents;

  public void addChild(Controller controller) {
    if (children == null) children = new ArrayList<Controller>();
    children.add(controller);
    controller.parent = this;
  }

  public boolean canHandle(AppEvent event) {
    if (supportedEvents != null && supportedEvents.contains(event.getType())) return true;
    if (children != null) {
      for (Controller c : children) {
        if (c.canHandle(event)) return true;
      }
    }
    return false;
  }

  public void forwardToChild(AppEvent event) {
    if (children != null) {
      for (Controller c : children) {
        if (!c.initialized) {
          c.initialize();
          c.initialized = true;
        }
        if (c.canHandle(event)) {
          c.handleEvent(event);
        }
      }
    }
  }

  public void forwardToView(View view, AppEvent event) {
    if (!view.initialized) {
      view.initialize();
      view.initialized = true;
    }
    view.handleEvent(event);
  }

  public void forwardToView(View view, EventType type, Object data) {
    AppEvent e = new AppEvent(type, data);
    forwardToView(view, e);
  }

  public abstract void handleEvent(AppEvent event);

  protected void initialize() {

  }
  
  protected void registerEventTypes(EventType... types) {
    if (supportedEvents == null) {
      supportedEvents = new ArrayList<EventType>();
    }
    if (types != null) {
      for (EventType type : types) {
        if (!supportedEvents.contains(type)) {
          supportedEvents.add(type);
        }
      }
    }
  }

}
