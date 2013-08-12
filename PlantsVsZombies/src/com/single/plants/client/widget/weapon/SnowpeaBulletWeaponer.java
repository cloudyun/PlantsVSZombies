package com.single.plants.client.widget.weapon;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.Listener;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.Weaponer;
import com.single.plants.client.widget.Zombier;
/**
 * 寒冰子弹
 */
public class SnowpeaBulletWeaponer extends Weaponer {
	
	public SnowpeaBulletWeaponer(int left,int top) {
		ImageElement img=DOM.createImg().cast();
		img.setSrc(weaponResource.snowBullet().getURL());
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
	private boolean pause;
	private Timer moveTimer;
	@Override
	protected void onLoad() {
		moveTimer=new Timer() {
			int count=0;
			@Override
			public void run() {
				if(pause){
					return;
				}
				int left=SnowpeaBulletWeaponer.this.el().getLeft();
				int top=SnowpeaBulletWeaponer.this.el().getTop();
				left+=10;
				if(left>1000){
					SnowpeaBulletWeaponer.this.removeFromParent();
					return ;
				}
				count++;
				count=count%5;
				if(count==0){//碰撞计算
					PlayGround playGround=(PlayGround) SnowpeaBulletWeaponer.this.getParent();
					Zombier z=playGround.zombierCollisionCheck(SnowpeaBulletWeaponer.this.el().getBounds(true));
					if(z!=null){
						z.reduceHP(power);
						z.reduceSpeed(2000);
						SnowpeaBulletWeaponer.this.removeFromParent();
					}
				}
				SnowpeaBulletWeaponer.this.el().setLeft(left);
				SnowpeaBulletWeaponer.this.el().setTop(top);
			}
		};
		moveTimer.scheduleRepeating(repeat);
	}
	
	@Override
	protected void onUnload() {
		moveTimer.cancel();
	}

}
