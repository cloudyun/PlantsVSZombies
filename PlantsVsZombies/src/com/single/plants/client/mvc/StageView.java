package com.single.plants.client.mvc;

import static com.single.plants.client.event.PVZEvent.DisableSound;
import static com.single.plants.client.event.PVZEvent.EnableSound;
import static com.single.plants.client.event.PVZEvent.OnSunCollectChange;
import static com.single.plants.client.event.PVZEvent.Pause;
import static com.single.plants.client.event.PVZEvent.Resume;

import java.util.Arrays;

import com.framework.client.event.EventType;
import com.framework.client.mvc.AppEvent;
import com.framework.client.mvc.Controller;
import com.framework.client.mvc.Dispatcher;
import com.framework.client.mvc.View;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.resources.MusicList;
import com.single.plants.client.stage.PlayGroundInteraction;
import com.single.plants.client.stage.StageConfig;
import com.single.plants.client.stage.StageResource;
import com.single.plants.client.stage.TotalStageConfig;
import com.single.plants.client.util.MusicConfig;
import com.single.plants.client.util.SunShineGen;
import com.single.plants.client.util.ZombieGen;
import com.single.plants.client.widget.BattleScreen;
import com.single.plants.client.widget.Card;
import com.single.plants.client.widget.MenuButton;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.ShovelSoil;
import com.single.plants.client.widget.SunShine;
import com.single.plants.client.widget.TopCards;
import com.single.plants.client.widget.tool.Progress;
import com.single.plants.client.widget.tool.Shovel;

public abstract class StageView extends View implements PVZResources{

	protected static StageResource stageResource=GWT.create(StageResource.class);
	
	protected BattleScreen screen;
	protected PlayGround playGround;
	protected TopCards topCards;
	protected Progress progress=new Progress();
	protected ZombieGen zombieGen;
	protected SunShineGen sunShineGen;
	protected PlayGroundInteraction interaction;
	
	
	public StageView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		EventType type=event.getType();
		if(type==PVZEvent.GameStart){
			try {
				start();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}else if(type==Pause){
			playGround.pause();
			if(zombieGen!=null){
				zombieGen.pause();
			}
			if(sunShineGen!=null){
				sunShineGen.pause();
			}
		}else if(type==Resume){
			playGround.resume();
			if(zombieGen!=null){
				zombieGen.resume();
			}
			if(sunShineGen!=null){
				sunShineGen.resume();
			}
		}else if(type==OnSunCollectChange){
			SunShine sunShine=event.getData();
			topCards.addCollect(sunShine.getShinePoint());
		}else if(type==EnableSound){
			if(screen!=null){
				screen.enableSound();
			}
		}else if(type==DisableSound){
			if(screen!=null){
				screen.disableSound();
			}
		}
	}
	
	protected StageConfig stageConfig;
	@Override
	protected void initialize() {
		stageConfig=TotalStageConfig.get().get(getId());
		screen=new BattleScreen();
		topCards=new TopCards();
		topCards.setCollection(stageConfig.getCollection());
		playGround=new PlayGround();
		playGround.setEnableRows(Arrays.asList(stageConfig.getEnableRows()));
		
		interaction=new PlayGroundInteraction(playGround);
		interaction.setTopCards(topCards);
		interaction.setScreen(screen);
		

		if(stageConfig.isShovel()){
			ShovelSoil soil=new ShovelSoil(true);
			Shovel shovel=soil.getShovel();
			interaction.setShovel(shovel);
			screen.add(soil);
		}
		//背景音乐
//		AudioElement music=HTML5DOM.createAudioElement().cast();
//		music.setLoop(true);
//		music.setSrc(stageConfig.getMusic().getUrl());
//		screen.setBattleMusic(music);
		//FLASH版本背景音乐
		Dispatcher.forwardEvent(PVZEvent.PlayMusic,new MusicConfig(MusicList.Mainmusic01,true));
		
		screen.add(new MenuButton());
		screen.add(topCards);
		screen.add(playGround);
		
		sunShineGen=new SunShineGen(screen);
		RootPanel.get().add(screen);
	}
	
	protected abstract void start();

	protected void destory(){
		screen.removeFromParent();
		if(sunShineGen!=null){
			sunShineGen.stop();
			sunShineGen=null;
		}
		if(zombieGen!=null){
			zombieGen.stop();
			zombieGen=null;
		}
		//清除在RootPanel中的预览植物
		for(String key:Card.getMouseGhostMap().keySet()){
			Card.getMouseGhostMap().get(key).removeFromParent();
		}
		Card.getMouseGhostMap().clear();
	}
	
	public abstract int getId();
	/**过关*/
	public  void success(){
		screen.disableSound();
	}
}
