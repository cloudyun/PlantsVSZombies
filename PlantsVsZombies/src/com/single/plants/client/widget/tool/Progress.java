package com.single.plants.client.widget.tool;

import com.framework.client.Framework;
import com.framework.client.s.S;
import com.framework.client.widget.Component;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;

/**
 * 进度条
 */
public class Progress extends Component{

	private int steps;
	
	private Element over;
	private Element head;
	/**关卡信息*/
	private String stageText="";
	
	public Progress(){
		this(3);
	}
	
	public Progress(int steps){
		super("progress");
		this.steps=steps;
		
		setElement(DOM.createDiv());	
	}
	
	public void setStep(int step){
		head.getStyle().setPropertyPx("left", caluteHead(step));
		over.getStyle().setProperty("clip", "rect(0px 157px 40px "+calcuteClip(step)+"px)");
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		head=DOM.createImg();
		head.addClassName("head");
		head.getStyle().setPropertyPx("left", caluteHead(0));
		
		over=DOM.createDiv();
		over.addClassName("over");
		over.getStyle().setProperty("clip", "rect(0px 157px 40px "+calcuteClip(0)+"px)");
		el().dom.appendChild(over);
		
		el().dom.appendChild(head);
		
		Element stage=DOM.createDiv();
		stage.setInnerText(stageText);
		stage.getStyle().setPosition(Position.ABSOLUTE);
		stage.getStyle().setLeft(-70, Unit.PX);
		stage.getStyle().setTop(15, Unit.PX);
		el().dom.appendChild(stage);
		el().setZIndex(Framework.getTopZIndex());
	}
	
	private int caluteHead(int step){
		if(step<0){
			step=0;
		}else if(step>steps){
			step=steps;
		}
		float s=1-step/(float)steps;
		return (int) (s*150)-8;
	}
	
	private int calcuteClip(int step){
		if(step<0){
			step=0;
		}else if(step>steps){
			step=steps;
		}
		float s=1-(step/(float)steps);
		return (int) (150*s);
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void setStageText(String stageText) {
		this.stageText = stageText;
	}

}
