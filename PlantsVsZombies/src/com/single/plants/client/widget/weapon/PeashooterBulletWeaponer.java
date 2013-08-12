package com.single.plants.client.widget.weapon;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.Listener;
import com.framework.client.mvc.Dispatcher;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.resources.MusicList;
import com.single.plants.client.util.MusicConfig;
import com.single.plants.client.util.PVZConfig;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.Weaponer;
import com.single.plants.client.widget.Zombier;

public class PeashooterBulletWeaponer extends Weaponer{

	public PeashooterBulletWeaponer(int left,int top) {
		ImageElement img=DOM.createImg().cast();
		img.setSrc(weaponResource.peashooterBullet().getURL());
		Element div=DOM.createDiv();
		div.getStyle().setPosition(Position.ABSOLUTE);
		div.getStyle().setZIndex(Framework.getTopZIndex());
		div.appendChild(img);
		setElement(div);
		el().setLeft(left);
		el().setTop(top);
		addListener(PVZEvent.Pause, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				pause=true;
				if(moveTimer!=null){
					moveTimer.cancel();
				}
			}
		});
		addListener(PVZEvent.Resume, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				pause=false;
				if(moveTimer!=null){
					moveTimer.scheduleRepeating(repeat);
				}
			}
		});
	}
	private static int repeat=20;
	private Timer moveTimer;
	private boolean pause;
	@Override
	protected void onLoad() {
		moveTimer=new Timer() {
			int count=0;
			@Override
			public void run() {
				int left=PeashooterBulletWeaponer.this.el().getLeft();
				int top=PeashooterBulletWeaponer.this.el().getTop();
				left+=10;
				if(left>800){
					PeashooterBulletWeaponer.this.removeFromParent();
					return ;
				}
				count++;
				count=count%5;
				if(count==0){//碰撞计算
					PlayGround playGround=(PlayGround) PeashooterBulletWeaponer.this.getParent();
					Zombier z=playGround.zombierCollisionCheck(PeashooterBulletWeaponer.this.el().getBounds(true));
					if(z!=null){//攻击
						if(PVZConfig.get().isSoundable()){
							Dispatcher.forwardEvent(PVZEvent.PlayMusic, new MusicConfig(MusicList.Splat));
						}
						z.reduceHP(power);
						PeashooterBulletWeaponer.this.removeFromParent();
					}
				}
				PeashooterBulletWeaponer.this.el().setLeft(left);
				PeashooterBulletWeaponer.this.el().setTop(top);
			}
		};
		if(!pause){
			moveTimer.scheduleRepeating(repeat);
		}
		
	}
	
	@Override
	protected void onUnload() {
		moveTimer.cancel();
	}


	
}
