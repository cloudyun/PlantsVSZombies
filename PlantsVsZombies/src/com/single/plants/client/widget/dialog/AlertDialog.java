package com.single.plants.client.widget.dialog;

import com.framework.client.event.DomEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.widget.Component;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.single.plants.client.widget.tool.Button;

public class AlertDialog extends Dialog{

	private Button confirm=new Button("确定");
	
	
	public AlertDialog(String title,String text){
		super(new Content(title,text));
		addButton(confirm);
		setSize("400px", "300px");
	}
	
	public AlertDialog(String text){
		this("", text);
	}
	
	private static class Content extends Component{
		
		public Content(String title,String text){
			super("alertdialog");
			String html="<div style='font-weight:bold;font-size:20px'>"+title+"</div><div>"+text+"</div>";
			Element div=DOM.createDiv();
			div.getStyle().setProperty("textAlign", "center");
			div.getStyle().setProperty("color", "white");
			div.setInnerHTML(html);
			setElement(div);
		}
	}
	
	protected void onLoad() {
		confirm.addListener(EventContant.OnClick, new Listener<DomEvent>() {
			public void handleEvent(DomEvent be) {
				AlertDialog.this.hide();
			}
		});
		
	};
}
