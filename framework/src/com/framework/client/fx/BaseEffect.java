package com.framework.client.fx;

import com.framework.client.core.El;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;

public class BaseEffect implements Effect {


  static class Blink extends BaseEffect {

    private int interval;
    private Timer t;
    private boolean visible;

    public Blink(final El el, int interval) {
      super(el);
      this.interval = interval;
      t = new Timer() {
        @Override
        public void run() {
          el.setVisibility(visible);
          visible = !visible;
        }
      };
    }

    @Override
    public void onComplete() {
      t.cancel();
      // ensure timer is done executing
      DeferredCommand.addCommand(new Command() {
        public void execute() {
          el.setVisibility(true);
        }
      });
    }

    @Override
    public void onCancel() {
      super.onCancel();
      t.cancel();
    }

    @Override
    public void onStart() {
      t.scheduleRepeating(interval);
    }

    @Override
    public void onUpdate(double progress) {

    }
  }

  static class FadeIn extends SingleStyleEffect {

    public FadeIn(El el) {
      super(el, "opacity", 0, 1);
    }

    @Override
    public void increase(double value) {
      el.setStyleAttribute("opacity", value);
    }

    public void onComplete() {
      el.setStyleAttribute("filter", "");
    }

    public void onStart() {
      el.setStyleAttribute("opacity", 0);
      el.setVisible(true);
    }

  }

  static class FadeOut extends SingleStyleEffect {

    public FadeOut(El el) {
      super(el, "opacity", 1, 0);
    }

    @Override
    public void increase(double value) {
      el.setStyleAttribute("opacity", Math.max(value, 0));
    }

    public void onComplete() {
      el.setVisible(false);
      el.dom.getStyle().setProperty("opacity", "");
      el.dom.getStyle().setProperty("filter", "");
    }

  }

  static class Move extends BaseEffect {

    private int fromX, toX;
    private int fromY, toY;

    public Move(El el, int x, int y) {
      super(el);
      fromX = el.getX();
      fromY = el.getY();

      toX = x;
      toY = y;
    }

    @Override
    public void onComplete() {
      super.onComplete();
      el.setXY(toX, toY);
    }

    @Override
    public void onUpdate(double progress) {
      int x = (int) getValue(fromX, toX, progress);
      int y = (int) getValue(fromY, toY, progress);

      el.setXY(x, y);
    }

  }



  public static void blink(El el, FxConfig config, int interval) {
    Fx fx = new Fx(config);
    fx.run(new Blink(el, interval));
  }

  public static void fadeIn(El el, FxConfig config) {
    Fx fx = new Fx(config);
    fx.run(new FadeIn(el));
  }

  public static void fadeOut(El el, FxConfig config) {
    Fx fx = new Fx(config);
    fx.run(new FadeOut(el));
  }
  
  public static void move(El el,int x,int y,FxConfig config){
	  Fx fx=new Fx(config);
	  fx.run(new Move(el, x, y));
  }

  

  protected El el;

  protected BaseEffect(El el) {
    this.el = el;
  }

  public void onCancel() {

  }

  public void onComplete() {

  }

  public void onStart() {

  }

  public void onUpdate(double progress) {

  }

  protected double getValue(double from, double to, double progress) {
    return (from + ((to - from) * progress));
  }

}
