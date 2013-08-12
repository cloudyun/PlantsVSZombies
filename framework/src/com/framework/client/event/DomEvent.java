package com.framework.client.event;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
/**
 * DOM事件
 * @author xuhengfei email:yonglin4605@163.com
 * @version test 2010-7-5
 */
public class DomEvent extends BaseEvent {

	protected Event event;
	
	public DomEvent(Object source){
		super(source);
	}
	
	public DomEvent(Object source,Event event){
		super(source);
		this.event=event;
	}
	/**
	 * 阻止DOM事件冒泡
	 */
	public void cancelBubble(){
		if(event!=null){
			DOM.eventCancelBubble(event, true);
		}
	}
	
	public int getClientX(){
		if(event!=null){
			return DOM.eventGetClientX(event);
		}
		return -1;
	}
	
	public int getClientY(){
		if(event!=null){
			return DOM.eventGetClientY(event);
		}
		return -1;
	}
	
	public Event getEvent(){
		return event;
	}
	
	public int getEventTypeInt(){
		return event==null?-1:DOM.eventGetType(event);
	}
	
	public int getKeyCode(){
		return event==null?-1:DOM.eventGetKeyCode(event);
	}
	
	public Element getTarget(){
		return event==null?null:DOM.eventGetTarget(event);
	}
	
	public boolean isAltKey(){
		return event==null?false:DOM.eventGetAltKey(event);
	}
	
	public boolean isControlKey(){
		return event==null?false:DOM.eventGetCtrlKey(event);
	}
	
	public boolean isNavKeyPress(){
		return isNavKeyPress(getKeyCode());
	}
	
	public boolean isNavKeyPress(int k){
		return (k>=33 && k<=40) || k==KeyCodes.KEY_ESCAPE || k==KeyCodes.KEY_ENTER || k==KeyCodes.KEY_TAB;
	}
	
	public boolean isRightClick(){
		if(event!=null){
			if(DOM.eventGetButton(event)==Event.BUTTON_RIGHT){
				return true;
			}
		}
		return false;
	}
	
	public boolean isShiftKey(){
		return event==null?false:DOM.eventGetShiftKey(event);
	}
	
	public boolean isSpecialKey(){
		return isSpecialKey(getKeyCode());
	}
	
	public boolean isSpecialKey(int k){
		return isNavKeyPress(k) || k==KeyCodes.KEY_BACKSPACE ||
			k==KeyCodes.KEY_CTRL || k==KeyCodes.KEY_SHIFT ||
			k==KeyCodes.KEY_ALT || (k>=19 && k<=20) || (k>=45 && k<=46);
	}
	/**
	 * 阻止浏览器默认行为
	 */
	public void preventDefault(){
		if(event!=null){
			event.preventDefault();
		}
	}
	
	public void setEvent(Event event){
		this.event=event;
	}
	
	public void stopEvent(){
		cancelBubble();
		preventDefault();
	}
	
	
}
