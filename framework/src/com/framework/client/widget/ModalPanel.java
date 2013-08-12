package com.framework.client.widget;

import java.util.Stack;

import com.framework.client.util.BaseEventPreview;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;

public class ModalPanel extends Component{

	private static Stack<ModalPanel> stack=new Stack<ModalPanel>();
	
	public static ModalPanel pop(){
		ModalPanel modal=stack.size()>0 ? stack.pop() :null;
		if(modal==null){
			modal=new ModalPanel();
		}
		return modal;
	}
	
	public static void push(ModalPanel modal){
		modal.hide();
		stack.push(modal);
	}
	
	private BaseEventPreview preview;
	private Component component;
	
	private ModalPanel(){
		super("modal");
		setElement(DOM.createDiv());
	}
	
	public void show(Component component){
		this.component=component;
		RootPanel.get().add(this);
		
		el().makePositionable(true);
		el().updateZIndex(100);
		component.el().updateZIndex(100);
		
		preview.getIgoreList().clear();
		preview.getIgoreList().add(component.getElement());
		preview.add();
		
		syncModal();
	}
	
	public void hide(){
	    setZIndex(-1);
	    component = null;
	    if (preview != null) {
	    	preview.getIgoreList().clear();
	    	preview.remove();
	    }
	    RootPanel.get().remove(this);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		preview=new BaseEventPreview(){
			
		};
	}
	/**
	 * 重新计算并设置遮罩层的大小
	 */
	public void syncModal(){
		el().setSize(0, 0);
		int w=Document.get().getScrollWidth();
		int h=Document.get().getScrollHeight();
		el().setSize(w, h);
	}
	public BaseEventPreview getPreview() {
		return preview;
	}

	public void setPreview(BaseEventPreview preview) {
		this.preview = preview;
	}
}
