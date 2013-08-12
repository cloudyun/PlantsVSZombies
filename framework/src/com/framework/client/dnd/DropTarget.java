package com.framework.client.dnd;

import java.util.ArrayList;
import java.util.List;

import com.framework.client.dnd.DND.Feedback;
import com.framework.client.dnd.DND.Operation;
import com.framework.client.event.BaseObservable;
import com.framework.client.event.CustomEvent;
import com.framework.client.event.DNDEvent;
import com.framework.client.event.DNDListener;
import com.framework.client.widget.Component;


public class DropTarget extends BaseObservable {

  protected Component component;
  protected String overStyle;
  protected Operation operation;
  protected Feedback feedback;

  private boolean allowSelfAsSource;
  private String group = "";
  private boolean enabled = true;

  /**
   * Creates a new drop target.
   * 
   * @param target the target component
   */
  public DropTarget(Component target) {
    this.component = target;
    this.operation = Operation.MOVE;
    this.feedback = Feedback.APPEND;
    DNDManager.get().registerDropTarget(this);
  }

  public void addDNDListener(DNDListener listener) {
    addListener(CustomEvent.DragEnter, listener);
    addListener(CustomEvent.DragLeave, listener);
    addListener(CustomEvent.DragCancel, listener);
    addListener(CustomEvent.Drop, listener);
    addListener(CustomEvent.DragMove, listener);
  }

  public void disable() {
    enabled = false;
  }

  public void enable() {
    enabled = true;
  }

  public Component getComponent() {
    return component;
  }

  public Feedback getFeedback() {
    return feedback;
  }

  public String getGroup() {
    return group;
  }

  public Operation getOperation() {
    return operation;
  }

  public String getOverStyle() {
    return overStyle;
  }

  public boolean isAllowSelfAsSource() {
    return allowSelfAsSource;
  }

  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Unregisters the target as a drop target.
   */
  public void release() {
    DNDManager.get().unregisterDropTarget(this);
  }

  /**
   * Removes the listener.
   * 
   * @param listener the listener to be removed
   */
  public void removeDNDListener(DNDListener listener) {
    removeListener(CustomEvent.DragStart, listener);
    removeListener(CustomEvent.DragLeave, listener);
    removeListener(CustomEvent.DragMove, listener);
    removeListener(CustomEvent.DragCancel, listener);
    removeListener(CustomEvent.Drop, listener);
  }

  /**
   * Sets whether internal drops are allowed (defaults to false).
   * 
   * @param allowSelfAsSource true to allow internal drops
   */
  public void setAllowSelfAsSource(boolean allowSelfAsSource) {
    this.allowSelfAsSource = allowSelfAsSource;
  }

  /**
   * Sets the target's feedback. Feedback determines the type of visual
   * indicators a drop target supports. Subclasses will determine range of valid
   * values.
   * 
   * @param feedback the feedback
   */
  public void setFeedback(Feedback feedback) {
    this.feedback = feedback;
  }

  /**
   * Sets the drag group. If specified, only drag sources with the same group
   * value are allowed.
   * 
   * @param group the group name
   */
  public void setGroup(String group) {
    this.group = group;
  }

  /**
   * Sets the operation for the drop target which specifies if data should be
   * moved or copied when dropped. Drag sources use this value to determine if
   * the target data should be removed from the source component.
   * 
   * @param operation the operation
   */
  public void setOperation(Operation operation) {
    this.operation = operation;
  }

  /**
   * Sets the style name to be applied when the cursor is over the target
   * (defaults to null).
   * 
   * @param overStyle the over style
   */
  public void setOverStyle(String overStyle) {
    this.overStyle = overStyle;
  }

  /**
   * Called if the user cancels the drag operations while the mouse is over the
   * target.
   * 
   * @param event the dd event
   */
  protected void onDragCancelled(DNDEvent event) {
//    Insert.get().hide();
  }

  /**
   * Called when the user releases the mouse over the target component.
   * 
   * @param event the dd event
   */
  protected void onDragDrop(DNDEvent event) {

  }

  /**
   * Called when the cursor first enters the bounds of the drop target.
   * Subclasses or listeners can change the status of status proxy via the
   * passed event.
   * 
   * @param event the dd event
   */
  protected void onDragEnter(DNDEvent event) {

  }

  /**
   * Called when the cursor leaves the target.
   * 
   * @param event the dd event
   */
  protected void onDragLeave(DNDEvent event) {

  }

  /**
   * Called when the cursor is moved within the target component. Subclasses or
   * listeners can change the status of status proxy via the passed event. If
   * either a subclass or listener sets {@link BaseEvent#setCancelled(boolean)}
   * to true, {@link #showFeedback(DNDEvent)} will be called.
   * 
   * @param event the dd event
   */
  protected void onDragMove(DNDEvent event) {

  }


  /**
   * Called as the mouse is moved over the target component. The default
   * implementation does nothing.
   * 
   * @param event the dd event
   */
  protected void showFeedback(DNDEvent event) {

  }

  boolean handleDragEnter(DNDEvent event) {
    event.setCancelled(false);
//    event.getStatus().setStatus(true);
    onDragEnter(event);
    if (!fireEvent(CustomEvent.DragEnter, event)) {
//      event.getStatus().setStatus(false);
      return false;
    }
    if (overStyle != null) {
      component.addStyleName(overStyle);
    }
    return true;
  }

  void handleDragLeave(DNDEvent event) {
    if (overStyle != null) {
      component.removeStyleName(overStyle);
    }
//    event.getStatus().setStatus(false);
    onDragLeave(event);
    fireEvent(CustomEvent.DragLeave, event);
  }

  void handleDragMove(DNDEvent event) {
    showFeedback(event);
    onDragMove(event);
  }

  void handleDrop(DNDEvent event) {
    if (overStyle != null) {
      component.removeStyleName(overStyle);
    }
    onDragDrop(event);
  }

}
