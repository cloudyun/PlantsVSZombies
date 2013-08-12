package com.framework.client.event;

/**
 * 
 * 事件基类
 * 
 */
public class BaseEvent {

  private boolean cancelled;
  private Object source;
  private EventType type;

  public BaseEvent(EventType type) {
    this.type = type;
  }

  /**
   * 如果事件已经取消,返回true
   * 
   * @return 如果事件已经取消,返回true
   */
  public boolean isCancelled() {
    return cancelled;
  }

  /**
   * 设置true以取消该事件
   * 只有对可以取消的事件进行设置才能生效(有些事件不能取消)
   * 
   * @param cancelled 设置true以取消该事件
   */
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  /**
   * 创建一个事件实例
   * 
   * @param source 事件来源
   */
  public BaseEvent(Object source) {
    this.source = source;
  }

  /**
   * 返回事件的来源
   * 
   * @return 返回事件的来源
   */
  public Object getSource() {
    return source;
  }

  /**
   * 返回事件的基本类型
   * 
   * @return 返回事件的基本类型
   */
  public EventType getType() {
    return type;
  }

  /**
   * 设置事件的来源
   * 
   * @param source 事件的来源
   */
  public void setSource(Object source) {
    this.source = source;
  }

  /**
   * 设置事件的基本类型
   * 
   * @param type 事件的基本类型
   */
  public void setType(EventType type) {
    this.type = type;
  }

}
