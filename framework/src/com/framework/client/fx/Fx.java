package com.framework.client.fx;

import com.framework.client.event.BaseObservable;
import com.framework.client.event.CustomEvent;
import com.framework.client.event.EffectListener;
import com.framework.client.event.FxEvent;
import com.google.gwt.animation.client.Animation;

public class Fx extends BaseObservable {

  private boolean isRunning;

  protected Effect effect;
  protected Animation animation;
  protected int duration = 0;

  public Fx() {
    animation = new Animation() {

      @Override
      public void onCancel() {
        Fx.this.onCancel();
        isRunning = false;
      }

      @Override
      public void onComplete() {
        Fx.this.onComplete();
        isRunning = false;
      }

      @Override
      public void onStart() {
        isRunning = true;
        Fx.this.onStart();
      }

      @Override
      public void onUpdate(double progress) {
        Fx.this.onUpdate(progress);
      }

    };
  }

  public Fx(FxConfig config) {
    this();
    duration = config.getDuration();
    addListener(CustomEvent.EffectStart, config.getEffectStartListener());
    addListener(CustomEvent.EffectComplete, config.getEffectCompleteListener());
  }

  public void addEffectListener(EffectListener listener) {
    addListener(CustomEvent.EffectStart, listener);
    addListener(CustomEvent.EffectCancel, listener);
    addListener(CustomEvent.EffectComplete, listener);
  }

  public Effect getEffect() {
    return effect;
  }

  public void cancel() {
    animation.cancel();
  }

  public void removeEffectListener(EffectListener listener) {
    removeListener(CustomEvent.EffectStart, listener);
    removeListener(CustomEvent.EffectCancel, listener);
    removeListener(CustomEvent.EffectComplete, listener);
  }

  public boolean run(Effect effect) {
    return run(duration > 0 ? duration : 500, effect);
  }

  public boolean run(int duration, Effect effect) {
    if (isRunning) return false;
    this.effect = effect;
    animation.run(duration);
    return true;
  }

  protected void onCancel() {
    effect.onCancel();
    fireEvent(CustomEvent.EffectCancel, new FxEvent(this, effect));
  }

  protected void onComplete() {
    effect.onComplete();
    fireEvent(CustomEvent.EffectComplete, new FxEvent(this, effect));
  }

  protected void onStart() {
    effect.onStart();
    fireEvent(CustomEvent.EffectStart, new FxEvent(this, effect));
  }

  protected void onUpdate(double progress) {
    effect.onUpdate(progress);
  }
}
