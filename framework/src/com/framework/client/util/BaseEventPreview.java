package com.framework.client.util;

import java.util.ArrayList;
import java.util.List;

import com.framework.client.event.BaseObservable;
import com.framework.client.event.EventType;
import com.framework.client.event.PreviewEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

public class BaseEventPreview extends BaseObservable implements NativePreviewHandler {

  private static int lastX, lastY;

  public static int getLastClientX() {
    return lastX;
  }

  public static Point getLastXY() {
    return new Point(lastX, lastY);
  }

  public static int getLastClientY() {
    return lastY;
  }

  private HandlerRegistration handler;
  
  private List<Element> igoreList=new ArrayList<Element>();

  public void add() {
    if (handler == null) {
      handler = Event.addNativePreviewHandler(this);
      onAdd();
    }
  }


  public void onPreviewNativeEvent(NativePreviewEvent event) {
    Event e = event.getNativeEvent().<Event> cast();

    PreviewEvent pe = new PreviewEvent(this, event);
    pe.setType(new EventType(e.getTypeInt()));
    pe.setEvent(e);

    lastX = pe.getClientX();
    lastY = pe.getClientY();

    if (!onPreview(pe)) {
      event.cancel();
    }
  }

  /**
   * Pushes the event preview to the stop of the stack.
   */
  public void push() {
    if (handler != null) {
      remove();
      add();
    }
  }

  /**
   * Removes event preview.
   */
  public void remove() {
    if (handler != null) {
      handler.removeHandler();
      handler = null;
      onRemove();
    }
  }

  protected void onAdd() {

  }

  /**
   * Called right before event preview will be removed from auto hide.
   * 
   * @param ce the component event
   * @return true to allow auto hide, false to cancel
   */
  protected boolean onAutoHide(PreviewEvent ce) {
    return true;
  }


  protected void onClick(PreviewEvent pe) {

  }

  /**
   * Called when a preview event is received.
   * 
   * @param pe the component event
   * @return true 是否继续传播事件
   */
  protected boolean onPreview(PreviewEvent pe) {

    return true;
  }

  protected void onPreviewKeyPress(PreviewEvent pe) {
  }

  protected void onRemove() {

  }

public List<Element> getIgoreList() {
	return igoreList;
}

public void setIgoreList(List<Element> igoreList) {
	this.igoreList = igoreList;
}

}
