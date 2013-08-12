package com.single.plants.client.widget.dialog;

import java.util.List;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.DomEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.widget.Component;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.util.Profile;
import com.single.plants.client.util.ProfileUtil;
import com.single.plants.client.widget.tool.Button;
/**
 * 帐号管理对话框
 */
public class AccountDialog extends Dialog {
	
	private Content panel;
	private Button addUser=new Button("新增用户");
	
	private Button delete=new Button("删除");
	
	private Button confirm=new Button("确定");
	
	private Button cancel=new Button("取消");

	public AccountDialog() {
		panel=new Content();
		content=panel;
		addButton(addUser);
		addButton(delete);
		addButton(confirm);
		addButton(cancel);
		setSize("400px", "500px");
	}

	private static class Content extends Component{
		private Element select;
		private Element ul;
		private Element add;
		public Content(){
			super("account");
			
			Element div=DOM.createDiv();
			String head="<div style='font-weight:bold;color:#E0BB62;font-size:20px' class='head'>你是谁</div>";
			Element body=DOM.createDiv();
			ul=DOM.createElement("ul");
			
			div.setInnerHTML(head);
			div.appendChild(body);
			body.appendChild(ul);
			
			add=DOM.createElement("ol");
			add.getStyle().setProperty("color", "white");
			add.setInnerHTML("(建立一个新用户)");
			body.appendChild(add);
			
			setElement(div);
			
			update();
		}
		
		private void update(){
			List<String> names=ProfileUtil.getUsers();
			Profile profile=ProfileUtil.getCurrentProfile();
			String name=profile==null?"":profile.getName();
			ul.setInnerHTML("");
			for(int i=0;i<names.size();i++){
				Element n=DOM.createElement("li");
				n.setInnerHTML(names.get(i));
				ul.appendChild(n);
				if(names.get(i).equals(name)){
					n.setClassName("select");
					select=n;
				}
			}
		}
		
		@Override
		protected void onLoad() {
			addListener(EventContant.OnClick, new Listener<DomEvent>() {
				public void handleEvent(DomEvent be) {
					if(be.getTarget().getTagName().equalsIgnoreCase("li")){
						if(select!=null){
							select.setClassName("");
						}
						select=be.getTarget();
						select.setClassName("select");
					}else if(be.getTarget()==add){
						NewProfileDialog d=new NewProfileDialog();
						d.addListener(PVZEvent.OnHide, new Listener<BaseEvent>() {
							public void handleEvent(BaseEvent be) {
								update();
							}
						});
						d.show();
					}
				}
			});
		}
	}
	
	@Override
	protected void onLoad() {
		addUser.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				NewProfileDialog d=new NewProfileDialog();
				d.addListener(PVZEvent.OnHide, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						panel.update();
					}
				});
				d.show();
			}
		});
		delete.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				if(panel.select==null){
					Window.alert("空");
				}else{
					String name=panel.select.getInnerText();
					panel.select.getParentElement().removeChild(panel.select);
					panel.select=null;
					ProfileUtil.removeProfile(name);
				}
			}
		});
		confirm.addListener(EventContant.OnClick, new Listener<BaseEvent>(){
			public void handleEvent(BaseEvent be) {
				Element select=AccountDialog.this.panel.select;
				if(select==null){
					new AlertDialog("请选择一个用户").show();
				}else{
					ProfileUtil.setCurrentProfile(select.getInnerText());
					AccountDialog.this.hide();
				}
			}
		});
		cancel.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				if(ProfileUtil.getCurrentProfile()==null){
					new AlertDialog("请选择一个用户").show();
				}else{
					AccountDialog.this.hide();
				}
			}
		});
	}
}
