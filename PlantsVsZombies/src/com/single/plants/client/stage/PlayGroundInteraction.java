package com.single.plants.client.stage;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.DomEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.event.PreviewEvent;
import com.framework.client.util.BaseEventPreview;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.event.CardEvent;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.PlantEvent;
import com.single.plants.client.event.ShineEvent;
import com.single.plants.client.util.PVZConfig;
import com.single.plants.client.widget.BattleScreen;
import com.single.plants.client.widget.Card;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.TopCards;
import com.single.plants.client.widget.tool.Shovel;
/**
 * 处理与PlayGround交互的行为
 * @author Administrator
 *
 */
public class PlayGroundInteraction {

	private Shovel shovel;
	private TopCards topCards;
	private PlayGround playGround;
	private BattleScreen screen;
	
	private Card selectCard;
	
	private BaseEventPreview cardPreview;
	private BaseEventPreview shovelPreview;
	
	private BaseEventPreview zuobi=new BaseEventPreview(){
		protected boolean onPreview(PreviewEvent pe) {
			if(pe.isAltKey()&&pe.isControlKey()&&pe.getKeyCode()==112){
				String text=Window.prompt("开启秘技,请输入要增加的阳光数", "500");
				try {
					Integer num=Integer.valueOf(text);
					if(topCards!=null){
						topCards.addCollect(num);
					}
				} catch (Exception e) {
					Window.alert("输入非法");
				}
				
			}
			
			return true;
		};
	};
	/**
	 * 当收集到阳光时
	 */
	private Listener<ShineEvent> suncollectListener=new Listener<ShineEvent>() {
		public void handleEvent(ShineEvent be) {
			updateMask();
		}
	};
	/**
	 * 种植植物失败
	 */
	private Listener<PlantEvent> plantFailedListener=new Listener<PlantEvent>() {
		public void handleEvent(PlantEvent be) {
			updateMask();
		}
	};
	
	private Listener<PlantEvent> plantSuccessListener=new Listener<PlantEvent>() {
		public void handleEvent(PlantEvent be) {
			int count=be.getPlanter().getCard().getPrice();
			topCards.reduceCollect(count);
			be.getPlanter().getCard().unfreezing();
		}
	};
	
	
	
	private Listener<CardEvent> cardClickListener=new Listener<CardEvent>() {
		public void handleEvent(CardEvent be) {
			if(be.getCard().isFreezing()||be.getCard().isMasking()){
				return ;
			}
			selectCard=be.getCard();
			selectCard.mask();
			Planter mouse=selectCard.getMouseGhost();
			if(!mouse.isAttached()){
				RootPanel.get().add(mouse);
			}
			if(!mouse.isVisible()){
				mouse.el().setZIndex(Framework.getTopZIndex());
				mouse.show();
			}
			mouse.el().setXY(be.getClientX()-mouse.el().getWidth()/2, be.getClientY()-mouse.el().getHeight()/2);
			topCards.fireEvent(PVZEvent.CardDragStart, new BaseEvent(be.getCard()));
			cardPreview.add();
		}
	};
	
	public PlayGroundInteraction(PlayGround ground){
		playGround=ground;
		zuobi.add();
	}
	
	
	
	
	public void updateMask(){
		for(Card item:topCards.getItems()){
			if(item.getPrice()>topCards.getCollection()){
				item.mask();
			}else{
				item.unmask();
			}
		}
	}
	/**
	 * 开启铁铲与PlayGround的交互
	 */
	public void startShovel(){
		if(shovel==null){
			return;
		}
		shovelPreview=new BaseEventPreview(){
			@Override
			protected boolean onPreview(PreviewEvent pe) {
				pe.preventDefault();
				int code=pe.getEventTypeInt();
				if(code==Event.ONMOUSEDOWN){
					if(DOM.eventGetButton(pe.getEvent())==Event.BUTTON_LEFT){//如果是左键，则清除植物
						playGround.weed(pe.getClientX(), pe.getClientY(), false);
					}
					shovel.getGhost().hide();
					shovelPreview.remove();
				}else if(code==Event.ONMOUSEMOVE){
					Shovel ghost=shovel.getGhost();
					ghost.el().setXY(pe.getClientX()-5, pe.getClientY()-50);
				}
				return true;
			}
		};
		shovel.addListener(EventContant.OnClick, new Listener<DomEvent>() {
			public void handleEvent(DomEvent be) {
				Shovel ghost=shovel.getGhost();
				if(!ghost.isAttached()){
					ghost.getElement().getStyle().setPosition(Position.ABSOLUTE);
					RootPanel.get().add(ghost);
				}
				if(!ghost.isVisible()){
					ghost.el().setZIndex(Framework.getTopZIndex());
					ghost.show();
				}
				ghost.el().setXY(be.getClientX()-5, be.getClientY()-50);
				shovelPreview.add();
			}
		});
	}
	/**
	 * 开启卡片栏与PlayGround的交互
	 */
	public void startTopCards(){
		if(topCards==null){
			return;
		}
		topCards.addListener(PVZEvent.OnSunCollectChange, suncollectListener);
		playGround.addListener(PVZEvent.OnPlantFailed, plantFailedListener);
		playGround.addListener(PVZEvent.OnPlantAdded, plantSuccessListener);
		
		//初始化时判断阳光数，阳光不够的卡片禁用
		int collection=topCards.getCollection();
		for(Card item:topCards.getItems()){
			if(collection<item.getPrice()){
				item.mask();
			}
			topCards.addListener(EventContant.OnClick,cardClickListener);
		}
		
		cardPreview=new BaseEventPreview(){
			@Override
			protected boolean onPreview(PreviewEvent pe) {
				pe.preventDefault();
				switch(pe.getEventTypeInt()){
					case Event.ONMOUSEMOVE:
						Planter ghost=selectCard.getGhostPlanter();
						Planter mouse=selectCard.getMouseGhost();
						
						mouse.el().setXY(pe.getClientX()-mouse.el().getWidth()/2, pe.getClientY()-mouse.el().getHeight()/2);
						playGround.previewPlant(ghost, pe.getClientX(), pe.getClientY());
						break;
					case Event.ONMOUSEDOWN:
						if(DOM.eventGetButton(pe.getEvent())==Event.BUTTON_LEFT){
							playGround.addPlantByGlobal(selectCard.createPlanter(), pe.getClientX(), pe.getClientY());
							selectCard.getGhostPlanter().hide();
							selectCard.getMouseGhost().hide();
							selectCard=null;
							cardPreview.remove();
						}
						break;
					case Event.ONMOUSEUP:
						updateMask();
						selectCard.getGhostPlanter().hide();
						selectCard.getMouseGhost().hide();
						selectCard=null;
						cardPreview.remove();
						break;
				}
				return false;
			}
		};
		
		if(!playGround.isStart()){
			playGround.start();
		}
	}
	
	public void startAll(){
		zuobi.remove();
		startTopCards();
		startShovel();
		//开始背景音乐
		if(screen!=null){
			if(PVZConfig.get().isSoundable()){
				screen.enableSound();
			}
		}
	}




	public void setTopCards(TopCards topCards) {
		this.topCards = topCards;
	}




	public void setShovel(Shovel shovel) {
		this.shovel = shovel;
	}




	public void setScreen(BattleScreen screen) {
		this.screen = screen;
	}
}
