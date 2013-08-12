package com.framework.client.widget;

import com.google.gwt.user.client.ui.Widget;

/**
 * 提供对Widget中protected级别与default级别的方法的访问
 */
public class ComponentHelper {

  public static void doAttach(Widget widget) {
    if (widget != null && !widget.isAttached()) {
      doAttachNative(widget);
    }
  }

  public static void doDetach(Widget widget) {
    if (widget != null && widget.isAttached()) {
      doDetachNative(widget);
    }
  }



  static native void doAttachNative(Widget widget) /*-{
    widget.@com.google.gwt.user.client.ui.Widget::onAttach()();
  }-*/;

  static native void doDetachNative(Widget widget) /*-{
    widget.@com.google.gwt.user.client.ui.Widget::onDetach()();
  }-*/;
}
