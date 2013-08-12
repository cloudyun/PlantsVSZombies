package com.framework.client.event;

import com.framework.client.util.BaseEventPreview;
import com.google.gwt.user.client.Event.NativePreviewEvent;
/**
 * 预览事件
 * @author xuhengfei email:yonglin4605@163.com
 * @version test 2010-7-5
 */
public class PreviewEvent extends DomEvent{

	private BaseEventPreview preview;
	
	public PreviewEvent(BaseEventPreview preview,NativePreviewEvent event){
		super(event);
		this.preview=preview;
	}
	
	public NativePreviewEvent getNativePreviewEvent(){
		return (NativePreviewEvent) getSource();
	}

	public BaseEventPreview getPreview() {
		return preview;
	}

	public void setPreview(BaseEventPreview preview) {
		this.preview = preview;
	}
	
	
}
