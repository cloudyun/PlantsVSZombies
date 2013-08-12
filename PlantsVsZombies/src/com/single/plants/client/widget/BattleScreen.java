package com.single.plants.client.widget;

import com.framework.client.html5.AudioElement;
import com.framework.client.util.HTML5DOM;
import com.framework.client.widget.Component;
import com.framework.client.widget.Container;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.util.PVZConfig;

public class BattleScreen extends Container<Component> implements PVZResources ,Soundable {

//	private AudioElement battleMusic;
	
	public BattleScreen(){
		Element div=DOM.createDiv();
		div.getStyle().setWidth(800, Unit.PX);
		div.getStyle().setHeight(600, Unit.PX);
		div.getStyle().setBackgroundImage("url("+gameResource.background1unsodded().getURL()+")");
//		battleMusic=HTML5DOM.createAudioElement().cast();
		setElement(div);
	}
	
	public void setBackgroundImage(ImageResource img){
		getElement().getStyle().setBackgroundImage("url("+img.getURL()+")");
		el().setStyleAttribute("backgroundPosition", "-220px");
	}
	
	@Override
	public boolean add(Component item) {
		return super.add(item);
	}
	
	@Override
	public boolean remove(Component item) {
		return super.remove(item);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		//播放音乐
//		getElement().appendChild(battleMusic);
	}

	@Override
	public void disableSound() {
		for(Component c:getItems()){
			if(c instanceof Soundable){
				((Soundable)c).disableSound();
			}
		}
//		battleMusic.pause();
	}

	@Override
	public void enableSound() {
		for(Component c:getItems()){
			if(c instanceof Soundable){
				((Soundable)c).enableSound();
			}
		}
//		battleMusic.play();
	}

	public void setBattleMusic(AudioElement battleMusic) {
//		this.battleMusic = battleMusic;
	}
}
