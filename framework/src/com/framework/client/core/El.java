package com.framework.client.core;

import java.util.ArrayList;
import java.util.List;

import com.framework.client.Framework;
import com.framework.client.util.Margins;
import com.framework.client.util.Padding;
import com.framework.client.util.Point;
import com.framework.client.util.Rectangle;
import com.framework.client.util.Size;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * 对DOM中Element的封装
 * 
 * @author xuhengfei email:yonglin4605@163.com
 * @version test 2010-7-5
 */
public class El {
	/**
	 * 显示模式
	 * 
	 * @author xuhengfei email:yonglin4605@163.com
	 * @version test 2010-7-5
	 */
	public enum VisMode {
		DISPLAY, VISIBILITY
	}


	public Element dom;
	private El _mask;

	public El(Element element) {
		assert element != null : "The element may not be null";
		this.dom = element;
	}

	public El(com.google.gwt.dom.client.Element element) {
		this.dom = element.cast();
	}

	public El(String html) {
		this(create(html));
	}

	public El mask() {
		if (_mask != null) {
			_mask.removeFromParent();
		}
		_mask = new El("<div></div>");
		_mask.dom.getStyle().setPosition(Position.ABSOLUTE);
		_mask.dom.getStyle().setZIndex(Framework.getTopZIndex());

		dom.appendChild(_mask.dom);
		_mask.setSize(getWidth(), getHeight());

		return _mask;
	}

	public El unmask() {
		if (_mask != null) {
			_mask.removeFromParent();
			_mask = null;
		}
		return this;
	}

	/**
	 * 在该Element上模拟一个click
	 * 
	 * @return
	 */
	public native El click()/*-{
		var dom=this.@com.framework.client.core.El::dom;
		if(dom.click){
			dom.click();
		}else{
			var event=$doc.createEvent("MouseEvents");
			event.initEvent('click',true,true,$wnd,0,0,0,0,0,false,false,false,false,1,dom);
			dom.dispatchEvent(event);
		}
		return this;
	}-*/;

	private static Element create(String html) {
		Element div = DOM.createDiv();
		DOM.setInnerHTML(div, html);
		Element firstChild = DOM.getFirstChild(div);
		// 支持Text Node
		return (firstChild != null) ? firstChild : div;
	}

	public El setMargins(Margins margin) {
		if (margin != null) {
			setStyleAttribute("margin-left", margin.left + "px");
			setStyleAttribute("margin-top", margin.top + "px");
			setStyleAttribute("margin-right", margin.right + "px");
			setStyleAttribute("margin-bottom", margin.bottom + "px");
		}
		return this;
	}

	public El setPadding(Padding padding) {
		if (padding != null) {
			setStyleAttribute("padding-left", padding.left + "px");
			setStyleAttribute("padding-top", padding.top + "px");
			setStyleAttribute("padding-right", padding.right + "px");
			setStyleAttribute("padding-bottom", padding.bottom + "px");
		}
		return this;
	}

	public El setStyleAttribute(String attr, Object value) {
		dom.getStyle().setProperty(attr, String.valueOf(value));
		return this;
	}

	/**
	 * 设置Element相对于页面的坐标
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public El setPagePosition(int x, int y) {
		return setXY(new Point(x, y));
	}

	public El setXY(Point p) {
		makePositionable();
		Point pts = translatePoints(p);
		if (p.x != -1) {
			setLeft(pts.x);
		}
		if (p.y != -1) {
			setTop(pts.y);
		}
		return this;

	}

	public El setXY(int x, int y) {
		return setXY(new Point(x, y));
	}

	public El setLeft(int left) {
		dom.getStyle().setPropertyPx("left", left);
		return this;
	}

	public El setTop(int top) {
		dom.getStyle().setPropertyPx("top", top);
		return this;
	}

	public El setWidth(int width) {
		if (width >= 0) {
			dom.getStyle().setPropertyPx("width", width);
		}
		return this;
	}

	public El setHeight(int height) {
		if (height > 0) {
			dom.getStyle().setPropertyPx("height", height);
		}
		return this;
	}

	public El setValue(String value) {
		dom.setPropertyString("value", value);
		return this;
	}

	/**
	 * 设置Style Position：Relative
	 * 
	 * @return
	 */
	public El makePositionable() {
		return makePositionable(false);
	}

	public El makePositionable(boolean absolute) {
		if (absolute) {
			setStyleAttribute("position", "absolute");
		} else if ("static".equals(dom.getStyle().getProperty("position"))) {
			setStyleAttribute("position", "relative");
		}
		return this;
	}

	public Rectangle getBounds() {
		return getBounds(false, false);
	}

	public Rectangle getBounds(boolean local) {
		return getBounds(local, false);
	}

	public Rectangle getBounds(boolean local, boolean adjust) {
		Size size = getSize(adjust);
		Rectangle rect = new Rectangle();
		rect.width = size.width;
		rect.height = size.height;
		if (local) {
			rect.x = getLeft(true);
			rect.y = getTop(true);
		} else {
			Point p = getXY();
			rect.x = p.x;
			rect.y = p.y;
		}
		return rect;
	}

	public int getTop() {
		return getTop(true);
	}

	public int getTop(boolean local) {
		if (local) {
			String top = dom.getStyle().getProperty("top");
			if (top.length() > 2) {
				top = top.substring(0, top.length() - 2);
			} else {
				top = "0";
			}
			return Integer.valueOf(top);
		}
		return getY();
	}

	public int getLeft() {
		return getLeft(true);
	}

	public int getLeft(boolean local) {
		if (local) {
			String left = dom.getStyle().getProperty("left");
			if (left.length() > 2) {
				left = left.substring(0, left.length() - 2);
			} else {
				left = "0";
			}

			return Integer.valueOf(left);
		}
		return getX();
	}

	public Point getXY() {
		return new Point(getX(), getY());
	}

	public int getY() {
		return dom.getAbsoluteTop();
	}

	public int getX() {
		return dom.getAbsoluteLeft();
	}

	public Size getSize(boolean content) {
		int w = getWidth();
		int h = getHeight();
		if (content) {
			// TODO
		}
		return new Size(w, h);
	}

	public int getWidth() {
		return dom.getOffsetWidth();
	}

	public int getHeight() {
		return dom.getOffsetHeight();
	}

	public El setVisibility(boolean visible) {
		setStyleAttribute("visibility", visible ? "visible" : "hidden");
		return this;
	}

	public El setDisplayed(boolean display) {
		String value = display ? "block" : "none";
		setStyleAttribute("display", value);
		return this;
	}

	public El setDisplayed(String display) {
		setStyleAttribute("display", display);
		return this;
	}

	public int getZIndex() {
		return parseInt(getPropertyImpl(dom,"zIndex"), 0);
	}
	//IE bug fix
	private native String getPropertyImpl(Element element,String name) /*-{
	    return element.style[name]+"";
	  }-*/;
	public String getStyleAttribute(String attr) {
		return dom.getStyle().getProperty(attr);
	}

	public El setZIndex(int zIndex) {
		DOM.setIntStyleAttribute(dom, "zIndex", Math.max(0, zIndex));
		return this;
	}

	public El updateZIndex(int adj) {
		setZIndex(Framework.getTopZIndex() + adj);
		return this;
	}

	public El removeFromParent() {
		if (dom.getParentElement() != null) {
			dom.getParentElement().removeChild(dom);
		}
		return this;
	}

	public El setSize(int width, int height) {
		if (width >= 0) {
			dom.getStyle().setPropertyPx("width", width);
		}
		if (height >= 0) {
			dom.getStyle().setPropertyPx("height", height);
		}
		return this;
	}

	public El setVisible(boolean visible) {
		return setVisibility(visible);
	}

	public Point translatePoints(Point p) {
		List<String> list = new ArrayList<String>(3);
		list.add("position");
		list.add("left");
		list.add("top");

		boolean relative = "relative".equals(dom.getStyle().getPosition());
		int l = parseInt(dom.getStyle().getProperty("left"), -11234);
		int t = parseInt(dom.getStyle().getProperty("top"), -11234);

		l = l != -11234 ? l : (relative ? 0 : dom.getOffsetLeft());
		t = t != -11234 ? t : (relative ? 0 : dom.getOffsetTop());

		Point o = getXY();
		return new Point(p.x - o.x + l, p.y - o.y + t);
	}

	/**
	 * 辅助方法
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public native int parseInt(String value, int defaultValue) /*-{
		return parseInt(value, 10) || defaultValue;
	}-*/;
}
