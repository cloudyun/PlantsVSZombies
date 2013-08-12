package com.single.plants.client.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import com.framework.client.event.EventType;
import com.framework.client.mvc.AppEvent;
import com.framework.client.mvc.Controller;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.util.MusicConfig;
import com.single.plants.client.util.PVZConfig;

public class MusicController extends Controller{

	private SoundController soundController;
	
	private Sound backgroundMusic;
	
	private Map<String, Sound> musicMap=new HashMap<String, Sound>();
	
	public MusicController(){
		soundController=new SoundController();
		
		registerEventTypes(PVZEvent.EnableSound,PVZEvent.DisableSound,PVZEvent.PlayMusic,PVZEvent.ChangeVolume);
		registerEventTypes(PVZEvent.DisableBackgroundSound);
	}
	
	public void enableMusic(){
		if(backgroundMusic!=null){
			backgroundMusic.play();
		}
	}
	
	public void disableMusic(){
		if(backgroundMusic!=null){
			backgroundMusic.stop();
		}
	}
	private int volume=50;
	public void changeVolume(int volume){
		this.volume=volume;
		if(backgroundMusic!=null){
			backgroundMusic.setVolume(volume);
		}
	}

	@Override
	public void handleEvent(AppEvent event) {
		EventType type=event.getType();
		if(type==PVZEvent.EnableSound){
			enableMusic();
		}else if(type==PVZEvent.DisableSound){
			disableMusic();
		}else if(type==PVZEvent.PlayMusic){
			MusicConfig config=event.getData();
			Sound sound=musicMap.get(config.getUrl());
			if(sound==null){
				sound=soundController.createSound(config.getType(), config.getUrl());
				musicMap.put(config.getUrl(), sound);
			}
			
			if(config.isBackgroundMusic()){
				if(backgroundMusic!=null){
					backgroundMusic.stop();
				}
				backgroundMusic=sound;
				backgroundMusic.addEventHandler(new SoundHandler() {
					@Override
					public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
					}
					
					@Override
					public void onPlaybackComplete(PlaybackCompleteEvent event) {
						Sound sound=(Sound) event.getSource();
						sound.play();
					}
				});
			}
			if(PVZConfig.get().isSoundable()){
				sound.setVolume(volume);
				sound.play();
			}
		}else if(type==PVZEvent.DisableBackgroundSound){
			if(backgroundMusic!=null){
				backgroundMusic.stop();
			}
		}
	}
}
