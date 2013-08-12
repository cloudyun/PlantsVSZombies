package com.framework.client.dnd;

import com.framework.client.Framework;
import com.framework.client.core.El;
import com.framework.client.event.BaseObservable;
import com.framework.client.event.ComponentEvent;
import com.framework.client.event.CustomEvent;
import com.framework.client.event.DragEvent;
import com.framework.client.event.DragListener;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.event.PreviewEvent;
import com.framework.client.util.BaseEventPreview;
import com.framework.client.util.Point;
import com.framework.client.util.Rectangle;
import com.framework.client.widget.Component;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class Draggable extends BaseObservable{
	/** 容器X,Y坐标 宽度 高度 */
	protected int conX,conY,conWidth,conHeight;
	protected int lastX,lastY;
	protected Rectangle startBounds;
	protected El proxyEl;
	
	private boolean enabled = true;
	
	private Component dragWidget;
	private Component handle;
	private Listener<ComponentEvent> listener;
	private BaseEventPreview preview;
	
	//config
	private boolean updateZIndex=true;
	/** 水平方向拖拽限制 */
	private boolean constrainHorizontal;
	/** 垂直方向拖拽限制 */
	private boolean constrainVertical;
	/** 页面可视访问内拖拽限制 */
	private boolean constrainClient=true;
	private boolean useProxy=true;
	/** 页面可视区的宽度  高度 */
	private int clientWidth,clientHeight;
	/** 拖拽组件所在容器 */
	private Component container;
	/** X轴移动范围限制 */
	private int xLeft=-1,xRight=-1;
	/** Y轴移动范围限制 */
	private int xTop=-1,xBottom=-1;
	
	private int dragStartX;
	private int dragStartY;
	private int startDragDistance=2;
	private DragEvent dragEvent;
	private boolean dragging;
	
	
	
	public Draggable(Component dragComponent){
		this(dragComponent, dragComponent);
	}
	public Draggable(Component dragComponent,Component handle){
		listener=new Listener<ComponentEvent>() {
			@Override
			public void handleEvent(ComponentEvent ce) {
				onMouseDown(ce);
			}
		};
		this.dragWidget=dragComponent;
		this.handle=handle;
		handle.addListener(EventContant.OnMouseDown, listener);
		
		preview=new BaseEventPreview(){
			@Override
			protected boolean onPreview(PreviewEvent pe) {
				pe.preventDefault();
				switch (pe.getEventTypeInt()) {
				case Event.ONKEYDOWN:
					if(dragging&& pe.getKeyCode()==KeyCodes.KEY_ESCAPE){
						cancelDrag();
					}
					break;
				case Event.ONMOUSEMOVE:
					onMouseMove(pe.getEvent());
				default:
					break;
				}
				return super.onPreview(pe);
			}

			
		};
		
		handle.sinkEvents(Event.ONMOUSEDOWN);
	}
	protected void onMouseMove(Event event) {
		
		int x=DOM.eventGetClientX(event);
		int y=DOM.eventGetClientY(event);
		
		if(!dragging && (Math.abs(dragStartX-x)>startDragDistance ||(Math.abs(dragStartY-y)>startDragDistance))){
			startDrag(event);
		}
		
		if(dragging){
			int left=constrainHorizontal ? startBounds.x : startBounds.x+(x-dragStartX);
			int top=constrainVertical? startBounds.y : startBounds.y+(y-dragStartY);
			if(constrainClient){
				if(!constrainHorizontal){
					int width=startBounds.width;
					left=Math.max(left, 0);
					left=Math.max(0, Math.min(clientWidth-width, left));
				}
				if(!constrainVertical){
					int height=startBounds.height;
					top=Math.max(top, 0);
					if(Math.min(clientHeight-height, top)>0){
						top=Math.max(2, Math.min(clientHeight-height, top));
					}
				}
			}
			if(container!=null){
				int width=startBounds.width;
				int height=startBounds.height;
				if(!constrainHorizontal){
					left=Math.max(left, conX);
					left=Math.min(conX+conWidth-width, left);
				}
				if(!constrainVertical){
					top=Math.min(conY+conHeight-height, top);
					top=Math.max(top, conY);
				}
			}
			if(!constrainHorizontal){
				if(xLeft!=-1){
					left=Math.max(startBounds.x-xLeft, left);
				}
				if(xRight!=-1){
					left=Math.min(startBounds.x+xRight, left);
				}
			}
			if(!constrainVertical){
				if(xTop!=-1){
					top=Math.max(startBounds.y-xTop, 0);
				}
				if(xBottom!=-1){
					top=Math.min(startBounds.y+xBottom, top);
				}
			}
			
			lastX=left;
			lastY=top;
			
			dragEvent.setSource(this);
			dragEvent.setComponent(dragWidget);
			dragEvent.setCancelled(false);
			
			if(dragEvent.isCancelled()){
				cancelDrag();
				return;
			}
			
			
		}
	}
	public void cancelDrag(){
		preview.remove();
		if(dragging){
			dragging=false;
			if(useProxy){
				proxyEl.setVisibility(false);
				proxyEl.removeFromParent();
			}else{
				dragWidget.el().setPagePosition(startBounds.x, startBounds.y);
			}
			fireEvent(CustomEvent.DragCancel, new DragEvent(this));
		}
	}
	
	protected void onMouseDown(ComponentEvent ce) {
		//如果没有开启拖拽，或者不是鼠标左键 ，返回!
		if(!enabled || ce.getEvent().getButton()!=Event.BUTTON_LEFT){
			return;
		}
		Element target=ce.getTarget();
		//如果点击在输入框上,让输入框正常获取焦点  阻止除此以外所有元素的浏览器默认行为
		if(!"input".equalsIgnoreCase(target.getTagName())
				&& !"textarea".equalsIgnoreCase(target.getTagName())){
			ce.preventDefault();
		}
		
		startBounds=dragWidget.el().getBounds();
		dragStartX=ce.getClientX();
		dragStartY=ce.getClientY();
		
		preview.add();
		
		clientWidth=Window.getClientWidth();
		clientHeight=Window.getClientHeight();
		
		if(container!=null){
			conX=container.getAbsoluteLeft();
			conY=container.getAbsoluteTop();
			conWidth=container.getOffsetWidth();
			conHeight=container.getOffsetHeight();
		}
		if(startDragDistance==0){
			startDrag(ce.getEvent());
		}
	}
	protected void startDrag(Event event) {
		DragEvent de=new DragEvent(this);
		de.setComponent(dragWidget);
		de.setEvent(event);
		de.setX(startBounds.x);
		de.setY(startBounds.y);
		
		if(fireEvent(CustomEvent.DragStart, de)){
			dragging=true;
			dragWidget.el().makePositionable();
			event.preventDefault();
			
			lastX=startBounds.x;
			lastY=startBounds.y;
			
			if(dragEvent==null){
				dragEvent=new DragEvent(this);
			}
			if(useProxy){
				if(proxyEl==null){
					
				}
				if(container==null){
					RootPanel.getBodyElement().appendChild(proxyEl.dom);
				}else{
					container.el().dom.appendChild(proxyEl.dom);
				}
				proxyEl.setVisibility(true);
				proxyEl.setZIndex(Framework.getTopZIndex());
				proxyEl.makePositionable(true);
				
				proxyEl.setXY(new Point(startBounds.x, startBounds.y));
				
				
			}else if(updateZIndex){
				dragWidget.setZIndex(Framework.getTopZIndex());
			}
		}else{
			cancelDrag();
		}
	}
	
	public void addDragListener(DragListener listener){
		addListener(CustomEvent.DragStart, listener);
		addListener(CustomEvent.DragMove, listener);
		addListener(CustomEvent.DragEnd, listener);
		addListener(CustomEvent.DragCancel, listener);
	}
	
	public void setXConstraint(int left,int right){
		xLeft=left;
		xRight=right;
	}
	
	public void setYConstraint(int top,int bottom){
		xTop=top;
		xBottom=bottom;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
