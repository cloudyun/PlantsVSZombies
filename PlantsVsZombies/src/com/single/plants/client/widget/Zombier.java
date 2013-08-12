package com.single.plants.client.widget;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.Listener;
import com.framework.client.util.Point;
import com.framework.client.util.Rectangle;
import com.framework.client.widget.Component;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.single.plants.client.contant.PVZContant;
import com.single.plants.client.core.Pauseable;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.ZombieEvent;
import com.single.plants.client.event.ZombieEvent.Status;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.model.zombie.Zombie;
import com.single.plants.client.util.PVZUtil;

public abstract class Zombier  extends Component implements PVZResources,Pauseable{
	protected int originSpeed=8;
	protected int speed=originSpeed;
	protected int hp=10;
	protected int power=1;
	/** 初始行 */
	public int row;
	/** 初始列 */
	public int column;
	
	protected int offsetX;
	protected int offsetY;
	
	protected ImageResource gif;
	protected Zombie zombie;
	
	protected ImageElement img;
	/**死亡时掉落的头*/
	protected DivElement head;
	
	public Zombier(Zombie zombie,ImageResource gif){
		this.zombie=zombie;
		this.gif=gif;
		img=DOM.createImg().cast();
		img.setSrc(gif.getURL());
		Element div=DOM.createDiv();
		div.getStyle().setPosition(Position.ABSOLUTE);
		
		div.appendChild(img);
		head=DOM.createDiv().cast();
		head.getStyle().setPosition(Position.FIXED);
		head.getStyle().setWidth(159, Unit.PX);
		head.getStyle().setHeight(186, Unit.PX);
		div.appendChild(head);
		setElement(div);
		//添加默认的一些状态
		addListener(PVZEvent.UpdateZombieStatus, new Listener<ZombieEvent>() {
			public void handleEvent(ZombieEvent be) {
				Status status=be.getStatus();
				switch (status) {
				case BombDie:
					img.setSrc(zombiesResource.boomDie().getURL());
					break;
				case LostHead:
					img.setSrc(zombiesResource.zombieLostHead().getURL());
					head.getStyle().setBackgroundImage("url("+zombiesResource.zombieHead().getURL()+")");
					int top=Zombier.this.el().getTop()+50;
					int left=Zombier.this.el().getLeft()+50;
					head.getStyle().setTop(top, Unit.PX);
					head.getStyle().setLeft(left, Unit.PX);
					break;
				case Dead:
					img.setSrc(zombiesResource.zombieDie().getURL());
					img.getStyle().setPosition(Position.ABSOLUTE);
					img.getStyle().setLeft(-50, Unit.PX);
					break;
				default:
					break;
				}
			}
		});
		addListener(PVZEvent.Pause, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				pause=true;
				if(moveTimer!=null){
					moveTimer.cancel();
				}
				if(attackTimer!=null){
					attackTimer.cancel();
				}
			}
		});
		addListener(PVZEvent.Resume, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				pause=false;
				if(moveTimer!=null){
					moveTimer.scheduleRepeating(repeat);
				}
				if(attackTimer!=null){
					attackTimer.scheduleRepeating(attackrepeat);
				}
			}
		});
	}
	private static int repeat=500;
	private static int attackrepeat=2000;
	private boolean pause;
	/**减速后恢复正常状态定时器*/
	protected Timer resumeSpeedTimer;
	protected Timer moveTimer;
	protected Timer attackTimer;
	public void start(){
		forwardPosition();
	}
	/**
	 * 前进
	 */
	protected void forwardPosition(){
		ZombieEvent ze=new ZombieEvent(PVZEvent.UpdateZombieStatus, this);
		ze.setStatus(Status.Forward);
		fireEvent(PVZEvent.UpdateZombieStatus,ze);
		if(moveTimer==null){
			moveTimer=new Timer() {
				@Override
				public void run() {
					Planter p=move();
					if(p!=null){
						holdPosition();
						attackPlant();
					}
				}
			};
		}
		if(!pause){
			moveTimer.scheduleRepeating(repeat);
		}
	}
	/**
	 * 原地待命
	 */
	protected void holdPosition(){
		ZombieEvent ze=new ZombieEvent(PVZEvent.UpdateZombieStatus, this);
		ze.setStatus(Status.Hold);
		fireEvent(PVZEvent.UpdateZombieStatus,ze);
		if(moveTimer!=null){
			moveTimer.cancel();
		}
	}
	/**
	 * 遇到植物，则暂停下来攻击
	 */
	protected void attackPlant(){
		ZombieEvent ze=new ZombieEvent(PVZEvent.UpdateZombieStatus, this);
		ze.setStatus(Status.Attack);
		fireEvent(PVZEvent.UpdateZombieStatus,ze);
		if(attackTimer==null){
			attackTimer=new Timer() {
				@Override
				public void run() {
					if(attack()){
						attackTimer.cancel();
						forwardPosition();
					}
				}
			};
		}
		attackTimer.scheduleRepeating(attackrepeat);
		attackTimer.run();//马上执行一次
		
	}
	/**
	 * 冲到底线，游戏结束
	 */
	public void stop() {
		if(moveTimer!=null){
			moveTimer.cancel();
		}
		if(attackTimer!=null){
			attackTimer.cancel();
		}
	}
	
	@Override
	protected void onUnload() {
		if(moveTimer!=null){
			moveTimer.cancel();
		}
		if(attackTimer!=null){
			attackTimer.cancel();
		}
	}
	public abstract Rectangle attactBody();
	public Rectangle defenseBody(){
		Rectangle r=el().getBounds(true);
		r.x+=r.width/4;
		r.y+=r.height/4;
		r.width/=2;
		r.height/=2;
		return r;
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		getElement().getStyle().setZIndex(Framework.getTopZIndex()+row*10);
		fixPosition();
	}
	
	/**
	 * 根据自身的大小，调整位置
	 */
	public void fixPosition(){
		Point point=PVZUtil.convertToPiexl(row, column);
		el().setLeft(point.x-gif.getWidth()/2);
		el().setTop(point.y-gif.getHeight()/2-40);
	}
	/**
	 * 移动
	 * @return 遇到的障碍植物 如果没有则返回null
	 */
	public Planter move(){
		Point p=el().getXY();
		
		PlayGround ground=(PlayGround) getParent();
		Rectangle r=attactBody();
		Planter planter=ground.planterCollisionCheck(r);
		if(planter!=null){
			return planter;
		}
		
		p.x-=speed;
		if(p.x<-50){
			fireEvent(PVZEvent.ZombieSuccess, new ZombieEvent(PVZEvent.ZombieSuccess, this));
			stop();
			Zombier.this.removeFromParent();
		}
		el().setXY(p);
		if(p.x<30){
			ground.lawnmowerCollisionCheck(defenseBody(),row);
		}
		
		return null;
	}
	
	/**
	 * 攻击植物
	 * @return 是否攻击完毕
	 */
	public boolean attack(){
		PlayGround ground=(PlayGround) getParent();
		Rectangle r=attactBody();
		Planter planter=ground.planterCollisionCheck(r,row);
		if(planter!=null){
			return planter.reduceHp(power);
		}
		return true;
	}
	/**
	 * 收到减速攻击
	 * @param time 持续时间
	 */
	public void reduceSpeed(int time){
		if(resumeSpeedTimer==null){
			resumeSpeedTimer=new Timer() {
				@Override
				public void run() {
					speed=originSpeed;
				}
			};
		}
		speed=originSpeed/2;
		resumeSpeedTimer.schedule(time);
	}
	/**
	 * 收到攻击，减血
	 * 当血量为1时：设置状态“头掉落”
	 * 当血量小于0时：触发死亡事件
	 * 		如果受伤害值是爆炸值，设置爆炸死亡状态
	 * 		如果是正常受豌豆攻击死亡，设置正常死亡状态
	 * @param power
	 */
	public void reduceHP(int power){
		hp-=power;
		if(hp==1){
			ZombieEvent ze=new ZombieEvent(PVZEvent.UpdateZombieStatus,this);
			ze.setStatus(Status.LostHead);
			fireEvent(PVZEvent.UpdateZombieStatus,ze );
			return;
		}else if(hp<=0){
			stop();
			fireEvent(PVZEvent.OnZombieDead, new ZombieEvent(PVZEvent.OnZombieDead,this));
			if(power==PVZContant.BOOM_POWER){//爆炸身亡
				ZombieEvent ze=new ZombieEvent(PVZEvent.UpdateZombieStatus, this);
				ze.setStatus(Status.BombDie);
				fireEvent(PVZEvent.UpdateZombieStatus,ze );
				new Timer() {
					public void run() {
						removeFromParent();
					}
				}.schedule(3000);
			}else{//正常死亡
				ZombieEvent ze=new ZombieEvent(PVZEvent.UpdateZombieStatus, this);
				ze.setStatus(Status.Dead);
				fireEvent(PVZEvent.UpdateZombieStatus, ze);
				new Timer() {
					public void run() {
						removeFromParent();
					}
				}.schedule(2000);
			}
			return;
		}
		blinkMask();
	}
	
	private void blinkMask(){
		el().setStyleAttribute("opacity", 0.5);
		Timer timer=new Timer() {
			@Override
			public void run() {
				el().setStyleAttribute("opacity", 1);
				el().setStyleAttribute("filter", "");
			}
		};
		timer.schedule(100);
	}
	
	public void addZombieSuccessListener(Listener<ZombieEvent> listener){
		addListener(PVZEvent.ZombieSuccess,listener);
	}
	
	@Override
	public void pause() {
		fireEvent(PVZEvent.Pause, new BaseEvent(PVZEvent.Pause));
	}
	
	@Override
	public void resume() {
		fireEvent(PVZEvent.Resume, new BaseEvent(PVZEvent.Resume));
	}
	public int getHp() {
		return hp;
	}
}


