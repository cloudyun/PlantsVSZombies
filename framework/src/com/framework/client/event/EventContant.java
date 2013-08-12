package com.framework.client.event;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Event;

public class EventContant {

	public static final EventType OnBlur=new EventType(Event.ONBLUR);
	
	public static final EventType OnChange=new EventType(Event.ONCHANGE);
	
	public static final EventType OnClick=new EventType(Event.ONCLICK);
	
	public static final EventType OnContextMenu=new EventType(Event.ONCONTEXTMENU);
	
	public static final EventType OnDoubleClick=new EventType(Event.ONDBLCLICK);
	
	public static final EventType OnError=new EventType(Event.ONERROR);
	
	public static final EventType OnFocus=new EventType(Event.ONFOCUS);
	
	public static final EventType OnKeyDown=new EventType(Event.ONKEYDOWN);
	
	public static final EventType OnKeyPress=new EventType(Event.ONKEYPRESS);
	
	public static final EventType OnKeyUp=new EventType(Event.ONKEYUP);
	
	public static final EventType OnLoad=new EventType(Event.ONLOAD);
	
	public static final EventType OnLoseCapture=new EventType(Event.ONLOSECAPTURE);
	
	public static final EventType OnMouseDown=new EventType(Event.ONMOUSEDOWN);
	
	public static final EventType OnMouseUp=new EventType(Event.ONMOUSEUP);
	
	public static final EventType OnMouseOver=new EventType(Event.ONMOUSEOVER);
	
	public static final EventType OnMouseMove=new EventType(Event.ONMOUSEMOVE);
	
	public static final EventType OnMouseOut=new EventType(Event.ONMOUSEOUT);
	
	public static final EventType OnMouseWheel=new EventType(Event.ONMOUSEWHEEL);
	
	public static final EventType OnPaste=new EventType(Event.ONPASTE);
	
	public static final EventType OnScroll=new EventType(Event.ONSCROLL);
	
	private static Map<String, EventType> browserEvents=new HashMap<String, EventType>();
	
	static{
		browserEvents.put(String.valueOf(Event.ONBLUR), OnBlur);
	    browserEvents.put(String.valueOf(Event.ONCHANGE), OnChange);
	    browserEvents.put(String.valueOf(Event.ONCLICK), OnClick);
	    browserEvents.put(String.valueOf(Event.ONCONTEXTMENU), OnContextMenu);
	    browserEvents.put(String.valueOf(Event.ONDBLCLICK), OnDoubleClick);
	    browserEvents.put(String.valueOf(Event.ONERROR), OnError);
	    browserEvents.put(String.valueOf(Event.ONFOCUS), OnFocus);
	    browserEvents.put(String.valueOf(Event.ONKEYDOWN), OnKeyDown);
	    browserEvents.put(String.valueOf(Event.ONKEYPRESS), OnKeyPress);
	    browserEvents.put(String.valueOf(Event.ONKEYUP), OnKeyUp);
	    browserEvents.put(String.valueOf(Event.ONLOAD), OnLoad);
	    browserEvents.put(String.valueOf(Event.ONLOSECAPTURE), OnLoseCapture);
	    browserEvents.put(String.valueOf(Event.ONMOUSEDOWN), OnMouseDown);
	    browserEvents.put(String.valueOf(Event.ONMOUSEUP), OnMouseUp);
	    browserEvents.put(String.valueOf(Event.ONMOUSEOVER), OnMouseOver);
	    browserEvents.put(String.valueOf(Event.ONMOUSEOUT), OnMouseOut);
	    browserEvents.put(String.valueOf(Event.ONMOUSEMOVE), OnMouseMove);
	    browserEvents.put(String.valueOf(Event.ONMOUSEWHEEL), OnMouseWheel);
	    browserEvents.put(String.valueOf(Event.ONPASTE), OnPaste);
	    browserEvents.put(String.valueOf(Event.ONSCROLL), OnScroll);
	}
	
	public static EventType lookupBrowserEvent(int browserEvent){
		EventType type=browserEvents.get(String.valueOf(browserEvent));
		return type;
	}
	
	protected EventContant(){}
	
}
