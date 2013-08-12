package com.single.plants.client.stage;

import java.util.List;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.fx.AbstractChain;
import com.framework.client.fx.Chain;
import com.framework.client.fx.FxConfig;
import com.framework.client.mvc.AppEvent;
import com.framework.client.mvc.Controller;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.fx.BackgroundImageMove;
import com.single.plants.client.model.plant.CherryBomb;
import com.single.plants.client.model.plant.Peashooter;
import com.single.plants.client.model.plant.SunFlower;
import com.single.plants.client.mvc.StageView;
import com.single.plants.client.util.Profile;
import com.single.plants.client.util.ProfileUtil;
import com.single.plants.client.util.ZombieGen;
import com.single.plants.client.widget.Card;
import com.single.plants.client.widget.NextGuide;
import com.single.plants.client.widget.Planter;
import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.plant.SunFlowerWidget;
import com.single.plants.client.widget.tool.Arrow;
import com.single.plants.client.widget.tool.Hint;
import com.single.plants.client.widget.tool.Arrow.Arrowhead;

public class Stage2 extends StageView{

	public Stage2(Controller controller) {
		super(controller);
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		super.handleEvent(event);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		
		progress.setStageText("第二关");
		
		screen.setBackgroundImage(gameResource.background1unsodded2());
		
		topCards.add(Peashooter.get().createCard());
		topCards.add(SunFlower.get().createCard());
		
	}
	/**
	 * 开始游戏
	 */
	protected void start(){
		
		//背景右移
		BackgroundImageMove moveright=new BackgroundImageMove(screen.el(), 0, -500);
		moveright.setConfig(new FxConfig(2000));
		//关卡的僵尸演示
		Chain zombieShow=new AbstractChain() {
			@Override
			public void run() {
				for(Zombier z:stageConfig.getPreviewList()){
					playGround.addZombierByGrid(z, z.row, z.column);
				}
				if(next!=null){
					new Timer() {
						@Override
						public void run() {
							next.run();
						}
					}.schedule(2000);
				}
				
			}
		};
		moveright.setNext(zombieShow);
		//关卡的僵尸演示消失
		Chain zombieHidden=new AbstractChain() {
			@Override
			public void run() {
				playGround.clearZombies();
				if(next!=null){
					next.run();
				}
			}
		};
		zombieShow.setNext(zombieHidden);
		//背景左移
		BackgroundImageMove moveleft=new BackgroundImageMove(screen.el(), -500, -220);
		moveleft.setConfig(new FxConfig(2000));
		
		zombieHidden.setNext(moveleft);
		
		//向导 种植向日葵
		Chain guide1=new AbstractChain() {
			public void run() {
				interaction.startAll();
				
				sunShineGen.start();
				final Hint hint=new Hint();
				hint.setText("向日葵是极其重要的植物");
				final Hint hint2=new Hint();
				hint2.setText("请至少种下3颗向日葵");
				screen.add(hint);
				new Timer() {
					public void run() {
						hint.removeFromParent();
						screen.add(hint2);
					}
				}.schedule(3000);
				
				final Arrow arrow=new Arrow(Arrowhead.UP);
				arrow.setXY(140, 90);
				screen.add(arrow);
				
				topCards.addListener(PVZEvent.CardDragStart, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						Card card=(Card) be.getSource();
						if(card.getGhostPlanter() instanceof SunFlowerWidget){
							topCards.removeListener(PVZEvent.CardDragStart, this);
						}
					}
				});
				playGround.addListener(PVZEvent.OnPlantAdded, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						List<Planter> plants=playGround.getPlanters();
						int count=0;
						for(Planter p:plants){
							if(p instanceof SunFlowerWidget){
								count++;
							}
						}
						if(count>=3){
							arrow.removeFromParent();
							hint2.removeFromParent();
							playGround.removeListener(PVZEvent.OnPlantAdded, this);
							screen.add(progress);
							next.run();
						}
					}
				});
			}
		};
		moveleft.setNext(guide1);
		
		//迎接僵尸
		zombieGen=new ZombieGen(playGround,progress);
		zombieGen.setBatch(stageConfig.getBattleList());
		guide1.setNext(zombieGen);
		
		//胜利
		Chain win=new AbstractChain() {
			public void run() {
				sunShineGen.stop();//关闭阳光生产
				final Arrow arrow=new Arrow(Arrowhead.DOWN);
				arrow.setXY(500, 250);
				screen.add(arrow);
				
				final Card card=CherryBomb.get().createCard();
				card.setGenerate(false);
				card.getElement().getStyle().setPosition(Position.ABSOLUTE);
				card.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						success();
						Profile profile=ProfileUtil.getCurrentProfile();
						profile.setStage(2);
						ProfileUtil.updateProfile(profile);
						screen.add(new NextGuide(card));
						card.removeListener(EventContant.OnClick, this);
						arrow.removeFromParent();
					}
				});
				screen.add(card);
				card.el().setZIndex(Framework.getTopZIndex());
				card.el().setXY(500, 300);
				card.unmask();
				
				
				
			}
		};
		zombieGen.setNext(win);
		
		moveright.run();
	}

	@Override
	public int getId() {
		return 2;
	}

}
