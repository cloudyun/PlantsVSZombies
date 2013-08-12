package com.framework.client.event;

import com.framework.client.dnd.DragSource;
import com.framework.client.dnd.DropTarget;
import com.framework.client.dnd.DND.Operation;
import com.framework.client.widget.Component;
import com.google.gwt.user.client.Event;

public class DNDEvent extends DomEvent {

	private Component component;
	private Object data;
	private DragEvent dragEvent;
	private DragSource dragSource;
	private DropTarget dropTarget;
	private Operation operation;

	// private StatusProxy status;

	public DNDEvent(DragSource source) {
		super(source);
		this.dragSource = source;
	}

	public DNDEvent(DragSource source, Event event) {
		super(source, event);
		this.dragSource = source;
	}

	public Component getComponent() {
		return component;
	}

	@SuppressWarnings("unchecked")
	public <X> X getData() {
		return (X) data;
	}

	public DragEvent getDragEvent() {
		return dragEvent;
	}

	public DragSource getDragSource() {
		return dragSource;
	}

	public DropTarget getDropTarget() {
		return dropTarget;
	}

	public Operation getOperation() {
		return operation;
	}

	// public StatusProxy getStatus() {
	// return status;
	// }

	public void setComponent(Component component) {
		this.component = component;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setDragEvent(DragEvent dragEvent) {
		this.dragEvent = dragEvent;
	}

	public void setDragSource(DragSource dragSource) {
		this.dragSource = dragSource;
	}

	public void setDropTarget(DropTarget dragTarget) {
		this.dropTarget = dragTarget;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	// public void setStatus(StatusProxy status) {
	// this.status = status;
	// }

}
