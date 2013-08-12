package com.framework.client.event;


public class EffectListener implements Listener<BaseEvent> {

  public void handleEvent(BaseEvent e) {
    EventType type = e.getType();
    if (type == CustomEvent.EffectCancel) {
      effectStart(e);
    } else if (type == CustomEvent.EffectComplete){
      effectComplete(e);
    } else if (type == CustomEvent.EffectStart) {
      effectStart(e);
    }
  }

  public void effectStart(BaseEvent be) {

  }

  public void effectCancel(BaseEvent be) {

  }

  public void effectComplete(BaseEvent be) {

  }

}
