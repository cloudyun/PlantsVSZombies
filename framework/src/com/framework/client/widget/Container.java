package com.framework.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
/**
 * 可以放置其他组件的容器
 *
 */
public abstract class Container<T extends Component> extends Component {

	private List<T> items;

	public Container() {
		this("container");
	}
	public Container(String baseStyle){
		super(baseStyle);
		items = new ArrayList<T>();
	}
	
	protected boolean add(T item){
		return insert(item, getItemCount(),getElement());
	}
	protected boolean add(T item,int index){
		return insert(item, index, getElement());
	}
	protected boolean add(T item,Element container){
		return insert(item, getItemCount(), container);
	}
	
	protected boolean remove(T item){
		if(item.getParent()!=this){
			return false;
		}
		item.setParent(null);
		//物理detach
		Element el=item.getElement();
		DOM.removeChild(DOM.getParent(el), el);
		
		items.remove(item);
		return true;
	}
	
	protected boolean removeAll(){
		int count=getItemCount();
		for(int i=0;i<count;i++){
			remove(items.get(0));
		}
		return getItemCount()==0;
	}
	protected boolean insert(T item,int index,Element container){
		item.removeFromParent();
		if(item.isAttached()){
			item.el().removeFromParent();
		}
		
		items.add(index, item);
		
		DOM.appendChild(container, item.getElement());
		
		adopt(item);
		return true;
	}
	protected void adopt(T child) {
	    child.setParent(this);
	  }
	public int getItemCount() {
		return items.size();
	}
	@Override
	protected void doAttachChildren() {
		super.doAttachChildren();
		if(isAttached()){
			for(T item:items){
				ComponentHelper.doAttach(item);
			}
		}
	}
	
	@Override
	protected void doDetachChildren() {
		super.doDetachChildren();
		for(T item:items){
			ComponentHelper.doDetach(item);
		}
	}

	public List<T> getItems() {
		return items;
	}
	
	
}
