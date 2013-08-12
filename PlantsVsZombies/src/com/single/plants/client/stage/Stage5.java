package com.single.plants.client.stage;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.fx.AbstractChain;
import com.framework.client.fx.Chain;
import com.framework.client.mvc.Controller;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.model.plant.CherryBomb;
import com.single.plants.client.model.plant.Peashooter;
import com.single.plants.client.model.plant.PotatoMine;
import com.single.plants.client.model.plant.SnowPea;
import com.single.plants.client.model.plant.SunFlower;
import com.single.plants.client.model.plant.WallNut;
import com.single.plants.client.mvc.StageView;
import com.single.plants.client.util.ProfileUtil;
import com.single.plants.client.util.SunShineGen;
import com.single.plants.client.util.ZombieGen;
import com.single.plants.client.widget.Card;
import com.single.plants.client.widget.NextGuide;
import com.single.plants.client.widget.tool.Arrow;
import com.single.plants.client.widget.tool.Hint;
import com.single.plants.client.widget.tool.Shovel;
import com.single.plants.client.widget.tool.Arrow.Arrowhead;
/**
 * 第五关
 */
public class Stage5 extends StageView {

	
	public Stage5(Controller controller) {
		super(controller);
	}

	@Override
	public int getId() {
		return 5;
	}

	@Override
	protected void start() {
		
		playGround.addPlantByGrid(Peashooter.get().createPlanter(), 3, 5);
		playGround.addPlantByGrid(Peashooter.get().createPlanter(), 2, 6);
		playGround.addPlantByGrid(Peashooter.get().createPlanter(), 4, 7);
		
		Chain weedPlants=new AbstractChain() {
			public void run() {
				topCards.el().setDisplayed(false);
				interaction.startAll();
				
				final Hint hint=new Hint();
				hint.setText("用铁铲清除所有的植物");
				screen.add(hint);
				final Arrow arrow=new Arrow(Arrowhead.UP);
				arrow.setXY(600, 80);
				screen.add(arrow);
				//定时检查植物是否被铲除
				new Timer() {
					public void run() {
						if(playGround.getPlanters().size()==0){
							cancel();
							hint.removeFromParent();
							arrow.removeFromParent();
							next.run();
						}
					}
				}.scheduleRepeating(1000);
			}
			
			
		};
		Chain start=new AbstractChain() {
			public void run() {
				topCards.el().setDisplayed(true);
				final Hint hint=new Hint();
				hint.setText("GOOD JOB");
				new Timer() {
					public void run() {
						hint.removeFromParent();
					}
				}.schedule(2000);
				screen.add(hint);
				screen.add(progress);
				sunShineGen.start();
				next.run();
			}
		};
		weedPlants.setNext(start);
		
		zombieGen=new ZombieGen(playGround,progress);
		zombieGen.setBatch(stageConfig.getBattleList());
		
		start.setNext(zombieGen);
		
		//胜利
		Chain win=new AbstractChain() {
			public void run() {
				sunShineGen.stop();//关闭阳光生产
				final Arrow arrow=new Arrow(Arrowhead.DOWN);
				arrow.setXY(500, 250);
				screen.add(arrow);
				
				final Card card=SnowPea.get().createCard();
				card.setGenerate(false);
				card.getElement().getStyle().setPosition(Position.ABSOLUTE);
				card.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						success();
						ProfileUtil.updateCurrentStage(5);
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
		
		//开始
		weedPlants.run();
	}
	@Override
	protected void initialize() {
		super.initialize();
		progress.setStageText("第五关");
		
		screen.setBackgroundImage(gameResource.background1());
		
		topCards.add(Peashooter.get().createCard());
		topCards.add(SunFlower.get().createCard());
		topCards.add(CherryBomb.get().createCard());
		topCards.add(WallNut.get().createCard());
		topCards.add(PotatoMine.get().createCard());
		
	}
}
