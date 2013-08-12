package com.framework.client.event;

import com.framework.client.widget.Component;
import com.google.gwt.user.client.Event;
/**
 * Component事件
 * @author xuhengfei email:yonglin4605@163.com
 * @version test 2010-7-5
 */
public class ComponentEvent extends DomEvent {

	private Component component;
	
	public ComponentEvent(Component component){
		super(component);
		this.component=component;
	}
	
	public ComponentEvent(Component component,Event event){
		super(component, event);
		this.component=component;
	}
	
	@SuppressWarnings("unchecked")
	public <X extends Component> X getComponent(){
		return (X) component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}
	
	
}
