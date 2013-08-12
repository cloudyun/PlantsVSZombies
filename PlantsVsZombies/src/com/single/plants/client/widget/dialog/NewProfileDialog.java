package com.single.plants.client.widget.dialog;

import com.framework.client.event.DomEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.widget.Component;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.single.plants.client.util.PVZException;
import com.single.plants.client.util.Profile;
import com.single.plants.client.util.ProfileUtil;
import com.single.plants.client.widget.tool.Button;

/**
 * 创建新用户窗口
 */
public class NewProfileDialog extends Dialog {

	private Content panel;
	private Button confirm=new Button("确定");
	private Button cancel=new Button("取消");
	public NewProfileDialog(){
		super(new Content());
		panel=(Content) content;
		addButton(confirm);
		addButton(cancel);
		setSize("400px", "300px");
		el().setStyleAttribute("textAlign", "center");
		el().setStyleAttribute("lineHeight", 2);
	}
	
	private static class Content extends Component{
		private InputElement input;
		public Content(){
			super("newproflie");
			Element div=DOM.createDiv();
			div.setInnerHTML("<div class='title'></div><div style='text-align:center;color:white'>请输入你的名字:</div>");
			input=DOM.createInputText().cast();
			div.appendChild(input);
			
			setElement(div);
		}
	}
	
	@Override
	protected void onLoad() {
		confirm.addListener(EventContant.OnClick, new Listener<DomEvent>() {
			public void handleEvent(DomEvent be) {
				String name=panel.input.getValue();
				Profile profile=new Profile();
				profile.setName(name);
				try {
					ProfileUtil.addProflie(profile);
					ProfileUtil.setCurrentProfile(name);
					NewProfileDialog.this.hide();
				} catch (PVZException e) {
					new AlertDialog(e.getMessage()).show();
				}
			}
		});
		cancel.addListener(EventContant.OnClick, new Listener<DomEvent>() {
			public void handleEvent(DomEvent be) {
				Profile profile=ProfileUtil.getCurrentProfile();
				if(profile==null){
					new AlertDialog("当前没有用户,请先创建一个用户");
				}else{
					NewProfileDialog.this.hide();
				}
			}
		});
	}
}
