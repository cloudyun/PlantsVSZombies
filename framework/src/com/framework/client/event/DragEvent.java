package com.framework.client.event;

import com.framework.client.dnd.Draggable;
import com.framework.client.widget.Component;

/**
 * 给Draggable提供事件
 *
 */
public class DragEvent extends DomEvent{

	private Component component;
	
	private Draggable draggable;
	
	private int height;
	
	private int width;
	
	private int x;
	
	private int y;
	
	public DragEvent(Draggable draggable){
		super(draggable);
		this.draggable=draggable;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Draggable getDraggable() {
		return draggable;
	}

	public void setDraggable(Draggable draggable) {
		this.draggable = draggable;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
