package com.framework.client.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.client.Framework;
import com.framework.client.event.BaseObservable;
import com.framework.client.event.EventType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;

public class Dispatcher extends BaseObservable {

  public static final EventType BeforeDispatch = new EventType();

  public static final EventType AfterDispatch = new EventType();

  private static Dispatcher instance;

  private static boolean historyEnabled = true;

  public static void forwardEvent(AppEvent event) {
    get().dispatch(event);
  }

  public static void forwardEvent(EventType eventType) {
    get().dispatch(eventType);
  }

  public static void forwardEvent(EventType eventType, Object data) {
    get().dispatch(new AppEvent(eventType, data));
  }

  public static void forwardEvent(EventType eventType, Object data, boolean historyEvent) {
    AppEvent ae = new AppEvent(eventType, data);
    ae.setHistoryEvent(historyEvent);
    get().dispatch(ae);
  }

  public static Dispatcher get() {
    if (instance == null) {
      instance = new Dispatcher();
    }
    return instance;
  }

  private Map<String, AppEvent> history;

  private List<Controller> controllers;
  private Boolean supportsHistory = null;

  private Dispatcher() {
    controllers = new ArrayList<Controller>();
    history = new HashMap<String,AppEvent>();
    if (supportsHistory()) {
      History.addValueChangeHandler(new ValueChangeHandler<String>() {
        public void onValueChange(ValueChangeEvent<String> event) {
          String historyToken = event.getValue();
          if (history.containsKey(historyToken)) {
            dispatch(history.get(historyToken), false);
          }
        }
      });
    }
  }

  public void addController(Controller controller) {
    if (!controllers.contains(controller)) {
      controllers.add(controller);
    }
  }

  public void addDispatcherListener(DispatcherListener listener) {
    addListener(BeforeDispatch, listener);
    addListener(AfterDispatch, listener);
  }

  public void dispatch(AppEvent event) {
    dispatch(event, event.isHistoryEvent());
  }

  public void dispatch(EventType type) {
    dispatch(new AppEvent(type));
  }

  public void dispatch(EventType type, Object data) {
    dispatch(new AppEvent(type, data));
  }

  public List<Controller> getControllers() {
    return controllers;
  }

  public Map<String, AppEvent> getHistory() {
    return history;
  }

  public void removeController(Controller controller) {
    controllers.remove(controller);
  }

  public void removeDispatcherListener(DispatcherListener listener) {
    removeListener(BeforeDispatch, listener);
    removeListener(AfterDispatch, listener);
  }

  private void dispatch(AppEvent event, boolean createhistory) {
    MvcEvent e = new MvcEvent(this, event);
    e.setAppEvent(event);
    if (fireEvent(BeforeDispatch, e)) {
      List<Controller> copy = new ArrayList<Controller>(controllers);
      for (Controller controller : copy) {
        if (controller.canHandle(event)) {
          if (!controller.initialized) {
            controller.initialized = true;
            controller.initialize();
          }
          controller.handleEvent(event);
        }
      }
      fireEvent(AfterDispatch, e);
    }
    if (createhistory && event.isHistoryEvent()) {
      String token = event.getToken();
      if (token == null) {
        token = "" + new Date().getTime();
      }
      history.put(token, event);
      if (supportsHistory()) {
        History.newItem(token, false);
      }
    }
  }

  private boolean supportsHistory() {
    if (supportsHistory == null) {
      supportsHistory = historyEnabled && GWT.isClient()
          && (DOM.getElementById("__gwt_historyFrame") != null || !(Framework.isIE6 || Framework.isIE7));
    }
    return supportsHistory;
  }
}
