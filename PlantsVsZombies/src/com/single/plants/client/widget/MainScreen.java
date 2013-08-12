package com.single.plants.client.widget;

import static com.google.gwt.user.client.Event.ONCLICK;
import static com.google.gwt.user.client.Event.ONMOUSEOUT;
import static com.google.gwt.user.client.Event.ONMOUSEOVER;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.Listener;
import com.framework.client.event.PreviewEvent;
import com.framework.client.mvc.Dispatcher;
import com.framework.client.util.BaseEventPreview;
import com.framework.client.widget.Component;
import com.framework.client.widget.Container;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.resources.MusicList;
import com.single.plants.client.util.MusicConfig;
import com.single.plants.client.util.Profile;
import com.single.plants.client.util.ProfileUtil;
import com.single.plants.client.widget.dialog.AccountDialog;
import com.single.plants.client.widget.dialog.AlertDialog;
import com.single.plants.client.widget.dialog.ConfigDialog;
import com.single.plants.client.widget.dialog.Dialog;

public class MainScreen extends Container<Component> implements PVZResources , Soundable{

	
	private static MainScreenUiBinder uiBinder = GWT
			.create(MainScreenUiBinder.class);

	interface MainScreenUiBinder extends UiBinder<Element, MainScreen> {
	}
	
	@UiField
	SpanElement player;

	@UiField
	DivElement wood;
	@UiField
	DivElement adventure;
	@UiField
	DivElement minigame;
	@UiField
	DivElement challenge;
	@UiField
	DivElement vasebreaker;
	@UiField
	DivElement cornerButton;
	@UiField
	DivElement helpScreen;
	@UiField
	DivElement stage_big;
	@UiField
	DivElement stage_small;
	/** 是否第一次冒险 */
	private boolean firstAdventure=true;
	
	private Listener<BaseEvent> profileListener=new Listener<BaseEvent>() {
		@Override
		public void handleEvent(BaseEvent be) {
			updateProfile();
		}
	};

	public MainScreen() {
		super("mainscreen");
		setElement(uiBinder.createAndBindUi(this));
		updateProfile();
	}
	
	private void updateProfile(){
		Profile profile=ProfileUtil.getCurrentProfile();
		if(profile==null){
			createRegistDialog().show();
		}else{
			Integer stage=profile.getStage();
			if(stage!=null && stage>0){
				firstAdventure=false;
			}else{
				firstAdventure=true;
			}
			if(firstAdventure){
				adventure.setClassName("adventure-start");
				stage_big.getStyle().setDisplay(Display.NONE);
				stage_small.getStyle().setDisplay(Display.NONE);
			}else{
				stage_big.getStyle().setDisplay(Display.BLOCK);
				stage_small.getStyle().setDisplay(Display.BLOCK);
				stage_big.setInnerText(ProfileUtil.prefixStage()+"");
				stage_small.setInnerText(ProfileUtil.suffixStage()+"");
				adventure.setClassName("adventure-continue");
			}
			adventure.setClassName(firstAdventure?"adventure-start":"adventure-continue");
			player.setInnerHTML(profile.getName());
		}
	}
	
	private Dialog createRegistDialog(){
		AccountDialog dialog=new AccountDialog();
		dialog.addListener(PVZEvent.OnHide, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				updateProfile();
			}
		});
		return dialog;
	}
	
	@Override
	public void onBrowserEvent(Event event) {
		Element current=event.getCurrentEventTarget().cast();
		if(current==wood){
			handleWoodEvent(event);
		}else if(current==adventure){
			handleAdventureEvent(event);
		}else if(current==minigame){
			handleMinigameEvent(event);
		}else if(current==challenge){
			handleChallengeEvent(event);
		}else if(current==vasebreaker){
			handleVasebreakerEvent(event);
		}else if(current==cornerButton){
			handleCornerButtonEvent(event);
		}else {
			super.onBrowserEvent(event);
		}
	}
	/**
	 * 生存模式 按钮的事件处理
	 * @param event
	 */
	private void handleVasebreakerEvent(Event event){
		switch (event.getTypeInt()) {
		case ONCLICK:
			createUnlockDialog().show();
			break;
		default:
			break;
		}
	}
	/**
	 * 解谜模式 按钮的事件处理
	 * @param event
	 */
	private void handleChallengeEvent(Event event){
		switch (event.getTypeInt()) {
		case ONCLICK:
			createUnlockDialog().show();
			break;
		default:
			break;
		}
	}
	/**
	 * 小游戏按钮的事件处理
	 * @param event
	 */
	private void handleMinigameEvent(Event event){
		switch (event.getTypeInt()) {
		case ONCLICK:
			createUnlockDialog().show();
			break;
		default:
			break;
		}
	}
	/**
	 * 冒险模式按钮的事件处理
	 * @param event
	 */
	private void handleAdventureEvent(Event event){
		switch (event.getTypeInt()) {
		case ONMOUSEOVER:
			adventure.setClassName(firstAdventure?"adventure-start-highlight":"adventure-continue-highlight");
			break;
		case ONMOUSEOUT:
			adventure.setClassName(firstAdventure?"adventure-start":"adventure-continue");
			break;
		case ONCLICK:
			Dispatcher.forwardEvent(PVZEvent.GameStart);
			break;
		}
	}
	/**
	 * 右下角三个按钮的事件处理
	 * 选项 帮助 退出
	 * @param event
	 */
	private void handleCornerButtonEvent(Event event){
		Element element=event.getEventTarget().cast();
		switch (event.getTypeInt()) {
		case ONMOUSEOVER:
			element.getStyle().setColor("blue");
			break;
		case ONMOUSEOUT:
			element.getStyle().setColor("black");
			break;
		case ONCLICK:
			if("选项".equals(element.getInnerText())){
				new ConfigDialog().show();
			}else if("帮助".equals(element.getInnerText())){
				helpScreen.getStyle().setDisplay(Display.BLOCK);
			}else if("退出".equals(element.getInnerText())){
				String text="Sorry 晃点你了,想退出直接关闭网页!\n对本游戏有什么建议或者发现BUG欢迎mail:yonglin4605@163.com告知\n" +
						"或者在http://yonglin4605.javaeye.com上给我留言";
				Window.alert(text);
			}
		default:
			break;
		}
	}
	/**
	 * 更换用户UI的事件处理
	 * @param event
	 */
	private void handleWoodEvent(Event event){
		switch (event.getTypeInt()) {
		case ONMOUSEOVER:
			wood.setClassName("wood2-over");
			break;
		case ONMOUSEOUT:
			wood.setClassName("wood2");
			break;
		case ONCLICK:
			createRegistDialog().show();
		default:
			break;
		}
		return;
	}
	
	private Dialog createUnlockDialog(){
		return new AlertDialog("未解锁", "开发中.... 期待您的加盟!");
	}
	@Override
	protected void onAttach() {
		super.onAttach();
		
		int event=ONMOUSEOUT | ONMOUSEOVER | ONCLICK;
		DOM.sinkEvents((com.google.gwt.user.client.Element) wood.cast(), event);
		DOM.sinkEvents((com.google.gwt.user.client.Element) adventure.cast(), event);
		DOM.sinkEvents((com.google.gwt.user.client.Element) minigame.cast(), event);
		DOM.sinkEvents((com.google.gwt.user.client.Element) challenge.cast(), event);
		DOM.sinkEvents((com.google.gwt.user.client.Element) vasebreaker.cast(), event);
		
		DOM.sinkEvents((com.google.gwt.user.client.Element) cornerButton.cast(), event);
		
		//FLASH版本音乐
		new Timer(){
			@Override
			public void run() {
				Dispatcher.forwardEvent(PVZEvent.PlayMusic, new MusicConfig(MusicList.Mainmusic04, true));
			}
			
		}.schedule(1000);
		//秘技
		daddy.add();
	}
	//秘技
	private BaseEventPreview daddy=new BaseEventPreview(){
		@Override
		protected boolean onPreview(PreviewEvent pe) {
			if(pe.getKeyCode()==112){//pe.isAltKey()&&pe.isControlKey()&&
				String text=Window.prompt("开启秘技,输入你想跳转的关卡", "1");
				if(text==null) return true;
				try {
					Integer num=Integer.valueOf(text)-1;
					if(num<0) throw new RuntimeException();
					ProfileUtil.updateCurrentStage(num);
					updateProfile();
				} catch (Exception e) {
					Window.alert("输入非法");
				}
			}
			return true;
		}
	};
	@Override
	protected void onDetach() {
		super.onDetach();
		DOM.setEventListener((com.google.gwt.user.client.Element) wood.cast(), null);
		DOM.setEventListener((com.google.gwt.user.client.Element) adventure.cast(), null);
		DOM.setEventListener((com.google.gwt.user.client.Element) minigame.cast(), null);
		DOM.setEventListener((com.google.gwt.user.client.Element) challenge.cast(), null);
		DOM.setEventListener((com.google.gwt.user.client.Element) vasebreaker.cast(), null);
		
		DOM.setEventListener((com.google.gwt.user.client.Element) cornerButton.cast(), null);
		
		daddy.remove();
	}

	@Override
	public void disableSound() {
//		music.pause();
	}

	@Override
	public void enableSound() {
//		music.play();
	}
}
