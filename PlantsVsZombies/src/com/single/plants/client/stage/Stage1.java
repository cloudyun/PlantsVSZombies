package com.single.plants.client.stage;

import java.util.ArrayList;
import java.util.Arrays;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventContant;
import com.framework.client.event.Listener;
import com.framework.client.fx.AbstractChain;
import com.framework.client.fx.Chain;
import com.framework.client.fx.FxConfig;
import com.framework.client.html5.AudioElement;
import com.framework.client.mvc.AppEvent;
import com.framework.client.mvc.Controller;
import com.framework.client.util.HTML5DOM;
import com.framework.client.widget.Component;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.ShineEvent;
import com.single.plants.client.fx.BackgroundImageMove;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.model.plant.Peashooter;
import com.single.plants.client.model.plant.SunFlower;
import com.single.plants.client.model.zombie.FlagZombie;
import com.single.plants.client.model.zombie.Zombie1;
import com.single.plants.client.mvc.StageView;
import com.single.plants.client.util.PVZUtil;
import com.single.plants.client.util.Profile;
import com.single.plants.client.util.ProfileUtil;
import com.single.plants.client.util.SunShineGen;
import com.single.plants.client.util.ZombieGen;
import com.single.plants.client.widget.Card;
import com.single.plants.client.widget.MenuButton;
import com.single.plants.client.widget.NextGuide;
import com.single.plants.client.widget.ShovelSoil;
import com.single.plants.client.widget.Zombier;
import com.single.plants.client.widget.tool.Arrow;
import com.single.plants.client.widget.tool.Hint;
import com.single.plants.client.widget.tool.Arrow.Arrowhead;

public class Stage1 extends StageView implements PVZResources{

	
	public Stage1(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		super.handleEvent(event);
	}

	@Override
	protected void initialize() {
		super.initialize();
		progress.setStageText("第一关");
		
		topCards.add(Peashooter.get().createCard());
		
	}
	
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
		
		final Component road=new Component(){
			{
				Element div=DOM.createDiv();
				div.getStyle().setPosition(Position.ABSOLUTE);
				setElement(div);
			}
			@Override
			protected void onLoad() {
				ImageElement img=DOM.createImg().cast();
				img.setSrc(gameResource.sod1row().getURL());
				getElement().appendChild(img);
				el().setXY(30, 270);
				el().setZIndex(Framework.getTopZIndex());
			}
		};
		
		
		//向导 点击豌豆
		Chain guide1=new AbstractChain() {
			@Override
			public void run() {
				screen.add(road);
				interaction.startAll();
				
				final Hint hint=new Hint();
				hint.setText("点击拾取种子包");
				screen.add(hint);
				
				final Arrow arrow=new Arrow(Arrowhead.UP);
				arrow.setXY(80, 100);
				screen.add(arrow);
				
				topCards.addListener(PVZEvent.CardDragStart, new Listener<BaseEvent>() {
					@Override
					public void handleEvent(BaseEvent be) {
						topCards.removeListener(PVZEvent.CardDragStart, this);
						arrow.removeFromParent();
						hint.removeFromParent();
						next.run();
					}
				});
			}
		};
		moveleft.setNext(guide1);
		
		//向导 种植豌豆
		Chain guide2=new AbstractChain() {
			@Override
			public void run() {
				final Hint hint=new Hint();
				hint.setText("点击草地种下你的种子");
				screen.add(hint);
				
				final Arrow arrow=new Arrow(Arrowhead.DOWN);
				arrow.setXY(300, 200);
				screen.add(arrow);
				playGround.addListener(PVZEvent.OnPlantAdded, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						playGround.removeListener(PVZEvent.OnPlantAdded, this);
						arrow.removeFromParent();
						hint.removeFromParent();
						next.run();
					}
				});
			}
		};
		guide1.setNext(guide2);
		//向导  干的漂亮 
		Chain guide3=new AbstractChain() {
			public void run() {
				final Hint hint=new Hint();
				hint.setText("干的漂亮");
				screen.add(hint);
				new Timer() {
					public void run() {
						hint.removeFromParent();
						next.run();
					}
				}.schedule(2000);
			}
		};
		guide2.setNext(guide3);
		//向导  收集阳光
		Chain guide4=new AbstractChain() {
			public void run() {
				final Hint hint=new Hint();
				hint.setText("点击收集掉落的阳光");
				screen.add(hint);
				sunShineGen.run();
				sunShineGen.start();
				topCards.addListener(PVZEvent.OnSunCollectChange, new Listener<BaseEvent>() {
					@Override
					public void handleEvent(BaseEvent be) {
						topCards.removeListener(PVZEvent.OnSunCollectChange, this);
						hint.removeFromParent();
						next.run();
					}
				});
			}
		};
		guide3.setNext(guide4);
		//向导  继续收集阳光
		Chain guide5=new AbstractChain() {
			public void run() {
				final Hint hint=new Hint();
				hint.setText("继续收集！你需要更多的阳光来种植植物");
				screen.add(hint);
				topCards.addListener(PVZEvent.OnSunCollectChange, new Listener<ShineEvent>() {
					public void handleEvent(ShineEvent be) {
						if(be.getTotalShine()>=100){
							topCards.removeListener(PVZEvent.OnSunCollectChange, this);
							hint.removeFromParent();
							next.run();
						}
					}
				});
			}
		};
		guide4.setNext(guide5);
		//向导  收集够一颗豌豆的阳光
		Chain guide6=new AbstractChain() {
			public void run() {
				final Hint hint=new Hint();
				hint.setText("太棒了！你已经为种植一颗豌豆收集足够的阳光了");
				screen.add(hint);
				final Arrow arrow=new Arrow(Arrowhead.UP);
				arrow.setXY(100, 100);
				screen.add(arrow);
				playGround.addListener(PVZEvent.OnPlantAdded, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						playGround.removeListener(PVZEvent.OnPlantAdded, this);
						arrow.removeFromParent();
						hint.removeFromParent();
						next.run();
					}
				});
			}
		};
		guide5.setNext(guide6);
		//向导 别让僵尸靠近你的房子
		Chain guide8=new AbstractChain() {
			public void run() {
				final Hint hint=new Hint();
				hint.setText("别让僵尸靠近你的房子");
				screen.add(hint);
				new Timer() {
					public void run() {
						hint.removeFromParent();
						next.run();
					}
				}.schedule(2000);
				screen.add(progress);
			}
		};
		guide6.setNext(guide8);
		
		//僵尸进攻
		zombieGen=new ZombieGen(playGround,progress);//new ZombieGen(playGround);
		zombieGen.setBatch(stageConfig.getBattleList());
		guide8.setNext(zombieGen);
		//胜利
		Chain win=new AbstractChain() {
			public void run() {
				sunShineGen.stop();
				
				final Arrow arrow=new Arrow(Arrowhead.DOWN);
				arrow.setXY(500, 250);
				screen.add(arrow);
				
				final Card card=SunFlower.get().createCard();
				card.setGenerate(false);
				card.getElement().getStyle().setPosition(Position.ABSOLUTE);
				card.addListener(EventContant.OnClick, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						success();
						Profile profile=ProfileUtil.getCurrentProfile();
						profile.setStage(1);
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
	protected void destory() {
		topCards.removeFromParent();
		playGround.removeFromParent();
		screen.removeFromParent();
	}
	public int getId() {
		return 1;
	}

}
