package com.framework.client.dnd;

import com.framework.client.event.BaseObservable;
import com.framework.client.event.CustomEvent;
import com.framework.client.event.DNDEvent;
import com.framework.client.event.DNDListener;
import com.framework.client.event.DragEvent;
import com.framework.client.event.DragListener;
import com.framework.client.util.XDOM;
import com.framework.client.widget.Component;



public class DragSource extends BaseObservable {

  protected Component component;
  protected Draggable draggable;
  protected DragListener listener;
  protected Object data;
//  protected StatusProxy statusProxy = StatusProxy.get();

  private String statusText;
  private String group = "";
  private boolean enabled = true;

  public DragSource(Component component) {
    this.component = component;

    listener = new DragListener() {

      @Override
      public void dragCancel(DragEvent de) {
        onDraggableDragCancel(de);
      }

      @Override
      public void dragEnd(DragEvent de) {
        onDraggableDragEnd(de);
      }

      @Override
      public void dragMove(DragEvent de) {
        onDraggableDragMove(de);
      }

      @Override
      public void dragStart(DragEvent de) {
        onDraggableDragStart(de);
      }

    };

    draggable = new Draggable(component);
//    draggable.setUseProxy(true);
//    draggable.setSizeProxyToSource(false);
//    draggable.setMoveAfterProxyDrag(false);
    draggable.addDragListener(listener);
//    draggable.setProxy(statusProxy.el());
  }

  public void addDNDListener(DNDListener listener) {
    addListener(CustomEvent.DragStart, listener);
    addListener(CustomEvent.Drop, listener);
  }

  public void disable() {
    enabled = false;
    draggable.setEnabled(false);
  }

  /**
   * Enables the drag source.
   */
  public void enable() {
    enabled = true;
    draggable.setEnabled(true);
  }

  public Component getComponent() {
    return component;
  }

  public Object getData() {
    return data;
  }

  public Draggable getDraggable() {
    return draggable;
  }

  public String getGroup() {
    return group;
  }

  public String getStatusText() {
    return statusText;
  }

  public boolean isEnabled() {
    return enabled && draggable.isEnabled();
  }

  public void release() {
//    draggable.release();
  }

  public void removeDNDListener(DNDListener listener) {
    removeListener(CustomEvent.DragStart, listener);
    removeListener(CustomEvent.Drop, listener);
  }

  public void setData(Object data) {
    this.data = data;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  /**
   * Sets the text to be used on the status proxy object. If the drag source
   * supports selection, {0} will be substituted with the selection size.
   * 
   * @param statusText the status text
   */
  public void setStatusText(String statusText) {
    this.statusText = statusText;
  }

  protected void onDragCancelled(DNDEvent event) {

  }

  protected void onDragDrop(DNDEvent event) {

  }

  protected void onDragFail(DNDEvent event) {

  }

  /**
   * Called when a drag operation begins on the target component. Subclasses or
   * any listeners can cancel the action by calling
   * {@link BaseEvent#setCancelled(boolean)}.
   * 
   * @param event the dnd event
   */
  protected void onDragStart(DNDEvent event) {

  }

  private void onDraggableDragCancel(DragEvent de) {
    DNDEvent e = new DNDEvent(this);
    e.setEvent(de.getEvent());
    e.setDragEvent(de);
    e.setComponent(component);
    DNDManager.get().handleDragCancelled(this, e);
  }

  private void onDraggableDragEnd(DragEvent de) {
    DNDEvent e = new DNDEvent(this, de.getEvent());
    e.setData(data);
    e.setDragEvent(de);
    e.setComponent(component);
//    e.setStatus(statusProxy);
    if (e.getData() != null) {
      DNDManager.get().handleDragEnd(this, e);
    }
  }

  private void onDraggableDragMove(DragEvent de) {
	 
    de.setX(de.getClientX() + 12 + XDOM.getBodyScrollLeft());
    de.setY(de.getClientY() + 12 + XDOM.getBodyScrollTop());

    DNDEvent e = new DNDEvent(this, de.getEvent());
    e.setDragEvent(de);
    e.setComponent(component);
    e.setData(data);
//    e.setStatus(statusProxy);
    DNDManager.get().handleDragMove(this, e);
  }

  private void onDraggableDragStart(DragEvent de) {
    DNDEvent e = new DNDEvent(this, de.getEvent());
    e.setData(data);
    e.setDragEvent(de);
    e.setComponent(component);
//    e.setStatus(statusProxy);
    DNDManager.get().handleDragStart(this, e);
    // instruct draggable
    de.setCancelled(e.isCancelled());
  }

}
