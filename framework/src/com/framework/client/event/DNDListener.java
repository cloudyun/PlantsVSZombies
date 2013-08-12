package com.framework.client.event;


public class DNDListener implements Listener<DNDEvent> {

  public void handleEvent(DNDEvent e) {
    EventType type = e.getType();
    if (type == CustomEvent.DragStart) {
      dragStart(e);
    } else if (type == CustomEvent.DragEnter){
      dragEnter(e);
    } else if (type == CustomEvent.DragLeave) {
      dragLeave(e);
    } else if (type == CustomEvent.DragMove) {
      dragMove(e);
    } else if (type == CustomEvent.Drop) {
      dragDrop(e);
    }
  }

  public void dragMove(DNDEvent e) {

  }

  public void dragStart(DNDEvent e) {

  }

  public void dragEnter(DNDEvent e) {

  }

  public void dragLeave(DNDEvent e) {

  }

  public void dragDrop(DNDEvent e) {

  }

}
