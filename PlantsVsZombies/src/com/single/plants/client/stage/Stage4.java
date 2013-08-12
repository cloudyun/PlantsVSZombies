package com.single.plants.client.stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.fx.AbstractChain;
import com.framework.client.fx.Chain;
import com.framework.client.fx.FxConfig;
import com.framework.client.mvc.Controller;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.fx.BackgroundImageMove;
import com.single.plants.client.model.plant.CherryBomb;
import com.single.plants.client.model.plant.Peashooter;
import com.single.plants.client.model.plant.PotatoMine;
import com.single.plants.client.model.plant.SunFlower;
import com.single.plants.client.model.plant.WallNut;
import com.single.plants.client.model.zombie.ConeheadZombie;
import com.single.plants.client.model.zombie.Zombie1;
import com.single.plants.client.mvc.StageView;
import com.single.plants.client.util.Profile;
import com.single.plants.client.util.ProfileUtil;
import com.single.plants.client.util.SunShineGen;
import com.single.plants.client.util.ZombieGen;
import com.single.plants.client.util.ZombieGen.ZombieConfig;
import com.single.plants.client.widget.Card;
import com.single.plants.client.widget.MenuButton;
import com.single.plants.client.widget.NextGuide;
import com.single.plants.client.widget.ShovelSoil;
import com.single.plants.client.widget.tool.Arrow;
import com.single.plants.client.widget.tool.Shovel;
import com.single.plants.client.widget.tool.Arrow.Arrowhead;
/**
 * 第四关
 */
public class Stage4 extends StageView {

	public Stage4(Controller controller) {
		super(controller);
	}

	@Override
	public int getId() {
		return 4;
	}

	@Override
	protected void start() {
		//背景右移
		BackgroundImageMove moveright=new BackgroundImageMove(screen.el(), 0, -500);
		moveright.setConfig(new FxConfig(2000));
		//关卡的僵尸演示
		Chain zombieShow=new AbstractChain() {
			@Override
			public void run() {
				playGround.addZombiersByGrid(stageConfig.getPreviewList());
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
		
		
		Chain start=new AbstractChain() {
			public void run() {
				interaction.startAll();
				
				screen.add(progress);
				sunShineGen.start();
				next.run();
			}
		};
		moveleft.setNext(start);
		
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
				
				final Card card=PotatoMine.get().createCard();
				card.setGenerate(false);
				card.getElement().getStyle().setPosition(Position.ABSOLUTE);
				card.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						success();
						ProfileUtil.updateCurrentStage(4);
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
		moveright.run();
	}

	@Override
	protected void initialize() {
		super.initialize();
		
		progress.setStageText("第四关");
		
		screen.setBackgroundImage(gameResource.background1());
		
		
		topCards.add(Peashooter.get().createCard());
		topCards.add(SunFlower.get().createCard());
		topCards.add(CherryBomb.get().createCard());
		topCards.add(WallNut.get().createCard());
		
	}
}
