package com.framework.client.fx;

import com.framework.client.event.FxEvent;
import com.framework.client.event.Listener;


public class FxConfig {

  public static final FxConfig NONE = new FxConfig();

  private Listener<FxEvent> effectStartListener;
  private Listener<FxEvent> effectCompleteListener;
  private int duration;

  public FxConfig() {

  }

  public FxConfig(int duration) {
    this.setDuration(duration);
  }

  public FxConfig(Listener<FxEvent> callback) {
    this.effectCompleteListener = callback;
  }

  public FxConfig(int duration, Listener<FxEvent> callback) {
    this.setDuration(duration);
    this.effectCompleteListener = callback;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getDuration() {
    return duration;
  }

  public Listener<FxEvent> getEffectStartListener() {
    return effectStartListener;
  }

  public void setEffectStartListener(Listener<FxEvent> effectStartListener) {
    this.effectStartListener = effectStartListener;
  }

  public Listener<FxEvent> getEffectCompleteListener() {
    return effectCompleteListener;
  }

  public void setEffectCompleteListener(Listener<FxEvent> effectCompleteListener) {
    this.effectCompleteListener = effectCompleteListener;
  }

}
