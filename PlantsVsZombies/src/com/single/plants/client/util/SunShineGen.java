package com.single.plants.client.util;

import com.framework.client.fx.BaseEffect;
import com.framework.client.fx.FxConfig;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.core.Pauseable;
import com.single.plants.client.widget.BattleScreen;
import com.single.plants.client.widget.SunShine;

/**
 * 阳光产生工具
 */
public class SunShineGen extends Timer implements Pauseable {

	private static int repeat=8000;
	private BattleScreen screen;
	public SunShineGen(BattleScreen screen){
		this.screen=screen;
	}
	@Override
	public void run() {
		int x = 50 + (int) (Math.random() * 700);
		int y = 200 + (int) (Math.random() * 350);
		SunShine shine = new SunShine(x, 0);
		screen.add(shine);
		BaseEffect.move(shine.el(), x, y, new FxConfig(y*10));
	}
	private boolean start=false;
	public void start() {
		start=true;
		this.scheduleRepeating(repeat);
	}

	public void stop() {
		this.cancel();
	}
	
	@Override
	public void pause() {
		if(start){
			cancel();
		}
	}
	@Override
	public void resume() {
		if(start){
			scheduleRepeating(repeat);
		}
	}
}