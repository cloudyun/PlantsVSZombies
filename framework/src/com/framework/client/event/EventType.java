package com.framework.client.event;

import java.io.Serializable;

/**
 * 所有事件的基本类型
 */
public class EventType implements Serializable {

  private static int count = 0;

  // needed to use FastMap for much better speed
  final String id;

  private int eventCode = -1;

  public EventType() {
    id = String.valueOf(count++);
  }

  public EventType(int eventCode) {
    this();
    this.eventCode = eventCode;
  }

  public int getEventCode() {
    return eventCode;
  }

  /**
   * 是否为浏览器事件
   * 
   * @return 如果是浏览器事件返回true
   */
  public boolean isBrowserEvent() {
    return eventCode != -1;
  }
}
