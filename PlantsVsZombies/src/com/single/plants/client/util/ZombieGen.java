package com.single.plants.client.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.framework.client.Framework;
import com.framework.client.event.Listener;
import com.framework.client.fx.AbstractChain;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.single.plants.client.core.Pauseable;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.ZombieEvent;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.tool.Progress;

/**
 * 僵尸创建器
 */
public class ZombieGen extends AbstractChain implements Pauseable{
	private PlayGround playGround;
	private Progress progress;
	private int deadZombieCount;
	
	private List<List<ZombieConfig>> batch=new ArrayList<List<ZombieConfig>>();
	private List<Zombier> aliveList=new ArrayList<Zombier>();
	
	public PlayGround getPlayGround() {
		return playGround;
	}
	public void setPlayGround(PlayGround playGround) {
		this.playGround = playGround;
	}
	
	public ZombieGen(PlayGround playGround,Progress progress){
		this.playGround=playGround;
		this.progress=progress;
	}
	@Override
	public void run() {
		int zombieCount=0;
		for(List<ZombieConfig> l:batch){
			zombieCount+=l.size();
		}
		progress.setSteps(zombieCount);
		
		nextBatch();
	}
	
	private void bat(final List<ZombieConfig> list){
		
		if(list.size()==0){
			if(aliveList.size()==0){//一批僵尸已经清除，产生下一批
				nextBatch();
			}else{//等待僵尸被清除
				new Timer() {
					public void run() {
						bat(list);
					}
				}.schedule(2000);
			}
			return;
		}
		//产生一个僵尸
		final ZombieConfig config=list.get(0);
		list.remove(config);
		new Timer() {
			public void run() {
				if(stop){
					return;
				}
				test();
			}
			public void test(){
				if(pause){//如果暂停了，则没隔1秒钟检测一次是否恢复
					new Timer() {
						public void run() {
							test();
						}
					}.schedule(1000);
				}else{
						add();
				}
			}
			public void add(){
				playGround.addZombierByGrid(config.zombier, config.row, 9);
				aliveList.add(config.zombier);
				config.zombier.addListener(PVZEvent.OnZombieDead, new Listener<ZombieEvent>() {
					public void handleEvent(ZombieEvent be) {
						deadZombieCount++;
						aliveList.remove(be.getZombier());
						progress.setStep(deadZombieCount);
					}
				});
				bat(list);
			}
		}.schedule((int)(config.time*1000));
	}
	
	/**
	 * 产生一批僵尸
	 */
	private void nextBatch(){
		if(batch.size()>0){
			if(batch.size()==1){
				RootPanel.get().add(new FinalWave());
			}else if(batch.size()==2){
//				Window.alert("一大波僵尸");
			}
			List<ZombieConfig> list=batch.get(0);
			batch.remove(0);
			bat(list);
		}else{
			//胜利
			next.run();
		}
	}
	
	public void addBatch(List<ZombieConfig> list){
		batch.add(list);
	}
	
	public static class ZombieConfig{
		/**僵尸进攻所在的行*/
		private int row;
		private Zombier zombier;
		/**距离上一次僵尸的时间*/
		private double time;
		public ZombieConfig(int row ,Zombier z,double time){
			this.row=row;
			this.zombier=z;
			this.time=time;
		}
	}

	public void setBatch(List<List<ZombieConfig>> batch) {
		this.batch = batch;
	}
	/**
	 * 最后一波
	 */
	public class FinalWave extends Widget{
		public FinalWave(){
			DivElement div=DOM.createDiv().cast();
			div.addClassName("finalwave");
			div.getStyle().setZIndex(Framework.getTopZIndex()+1000);
			setElement(div);
		}
		@Override
		protected void onAttach() {
			super.onAttach();
			new Timer() {
				public void run() {
					FinalWave.this.removeFromParent();
				}
			}.schedule(3000);
		}
	}

	protected boolean pause;
	@Override
	public void pause() {
		pause=true;
	}
	@Override
	public void resume() {
		pause=false;
	}
	private boolean stop=false;
	public void stop(){
		stop=true;
	}
}

