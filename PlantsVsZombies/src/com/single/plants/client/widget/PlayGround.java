package com.single.plants.client.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.framework.client.Framework;
import com.framework.client.event.BaseEvent;
import com.framework.client.event.Listener;
import com.framework.client.html5.AudioElement;
import com.framework.client.mvc.Dispatcher;
import com.framework.client.util.HTML5DOM;
import com.framework.client.util.Point;
import com.framework.client.util.Rectangle;
import com.framework.client.widget.Component;
import com.framework.client.widget.ComponentHelper;
import com.framework.client.widget.Container;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.single.plants.client.core.Pauseable;
import com.single.plants.client.event.PVZEvent;
import com.single.plants.client.event.PlantEvent;
import com.single.plants.client.event.ZombieEvent;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.util.PVZConfig;
import com.single.plants.client.widget.tool.LawnMower;

import static com.single.plants.client.util.PVZUtil.*;
/**
 * 游戏战斗区
 * 
 * 支持的事件：
 * PVZEvent.OnPlantAdded 当植物被种植进来
 * PVZEvent.OnPlantFailed  当种植植物失败
 */
public class PlayGround extends Container<Component> implements Pauseable , Soundable,PVZResources{

	public static final int GridWidth=80;
	public static final int GridHeight=100;
	
	private List<Planter> planters;
	private List<Zombier> zombiers;
	private List<Weaponer> weaponers;
	private List<LawnMower> mowers;
	
	private List<Integer> enableRows=Arrays.asList(new Integer[]{0,1,2,3,4});
	/** 游戏是否已经开始 */
	private boolean start=false;
	/**种植植物时的声音*/
//	private AudioElement plantSound;
	
	public PlayGround(){
		super("playground");
		planters=new ArrayList<Planter>();
		zombiers=new ArrayList<Zombier>();
		weaponers=new ArrayList<Weaponer>();
		mowers=new ArrayList<LawnMower>();
		
		Element div=DOM.createDiv();
		setElement(div);
		
//		plantSound=HTML5DOM.createAudioElement().cast();
//		plantSound.setPreload(true);
//		plantSound.setSrc(musicResource.MusicPath+"plant.ogg");
//		div.appendChild(plantSound);
	}
	
	public void start(){
		start=true;
		for(Integer row:enableRows){
			LawnMower mower=new LawnMower(row);
			mowers.add(mower);
			add(mower);
		}
		for(Zombier z:zombiers){
			z.start();
		}
		for(Planter p:planters){
			p.start();
		}
	}
	public boolean addWeapon(Weaponer w){
		w.removeFromParent();
		weaponers.add(w);
		add(w);
		return true;
	}
	/**
	 * 预览
	 */
	public void previewPlant(Planter planter,int x,int y){
		Point self=el().getXY();
		int left=x-self.x;
		int top=y-self.y;
		if(left>0&&left<el().getWidth()&&top>0&&top<el().getHeight()+GridHeight){
			int row=fixToRow(top);
			if(!enableRows.contains(row)){
				return;
			}
			int column=fixToColumn(left);
			if(!checkPlantable(planter, row, column)){
				planter.hide();
				return;
			}
			planter.show();
			if(!planter.isAttached()){
				planter.el().setStyleAttribute("opacity", 0.5);
				super.add(planter);
			}
			planter.row=row;
			planter.column=column;
			planter.fixPosition();
			
		}else{
			planter.hide();
		}
	}
	
	public boolean addPlantByGlobal(Planter planter,int clientX,int clientY){
		boolean add=false;
		Point self=el().getXY();
		int left=clientX-self.x;
		int top=clientY-self.y;
		if(left>0&&left<el().getWidth()&&top>0&&top<el().getHeight()){
			add= addPlantByGrid(planter, fixToRow(top),  fixToColumn(left));
		}
		if(!add){
			PlantEvent pe=new PlantEvent(planter);
			fireEvent(PVZEvent.OnPlantFailed, pe);
			
		}
		return add;
	}
	/**
	 * 该格子是否已经被占用
	 */
	private boolean checkPlantable(Planter planter,int row,int column){
		for(Planter p:planters){
			if(p.row==row && p.column==column){
				return false;
			}
		}
		return true;
	}
	
	public boolean addPlantByGrid(Planter planter,int row,int column){
		if(!enableRows.contains(row)||!checkPlantable(planter, row, column)){
			return false;
		}
		planter.row=row;
		planter.column=column;
		planter.removeFromParent();
		planters.add(planter);
		add(planter);
		PlantEvent pe=new PlantEvent(planter);
		if(PVZConfig.get().isSoundable()){
//			plantSound.play();
		}
		fireEvent(PVZEvent.OnPlantAdded, pe);
		planter.start();
		return true;
	}
	
	public void addZombiersByGrid(List<Zombier> zombiers){
		for(Zombier z:zombiers){
			addZombierByGrid(z, z.row, z.column);
		}
	}
	
	public boolean addZombierByGrid(Zombier zombier,int row,int column){
		zombier.row=row;
		zombier.column=column;
		Point point=convertToPiexl(row, column);
		return addZombier(zombier, point.x, point.y);
	}
	protected boolean addZombier(Zombier zombier,int left,int top){
		zombier.removeFromParent();
		zombiers.add(zombier);
		zombier.el().setZIndex(Framework.getTopZIndex()+100*zombier.row);
		add(zombier);
		
		if(start){
			zombier.start();
		}
		zombier.addListener(PVZEvent.ZombieSuccess, zombieWinListener);
		return true;
	}
	protected Listener<ZombieEvent> zombieWinListener=new Listener<ZombieEvent>() {
		public void handleEvent(ZombieEvent be) {
			Dispatcher.forwardEvent(PVZEvent.Pause);
			RootPanel.get().add(new ZombieEatYourBrain());
		}
	};
	
	private int fixToRow(int top){
		return top/GridHeight;
	}
	private int fixToColumn(int left){
		return left/GridWidth;
	}
	/**
	 * 植物碰撞检测
	 */
	public Planter planterCollisionCheck(Rectangle r){
		for(Planter p:planters){
			Rectangle t=p.el().getBounds(true);
			if(t.y+t.height<r.y||t.y>r.y+r.height||
					t.x+t.width<r.x||t.x>r.x+r.width){
				//没有碰撞
			}else{
				return p;
			}
		}
		return null;
	}
	/**
	 * 植物碰撞检测
	 */
	public Planter planterCollisionCheck(Rectangle r,int row){
		for(Planter p:planters){
			if(p.row!=row){
				continue;
			}
			Rectangle t=p.el().getBounds(true);
			if(t.y+t.height<r.y||t.y>r.y+r.height||
					t.x+t.width<r.x||t.x>r.x+r.width){
				//没有碰撞
			}else{
				return p;
			}
		}
		return null;
	}
	/**
	 * 僵尸碰撞检测
	 * @param r
	 * @return
	 */
	public Zombier zombierCollisionCheck(Rectangle r){
		for(Zombier z:zombiers){
			Rectangle t=z.defenseBody();
			if(t.y+t.height<r.y||t.y>r.y+r.height||
					t.x+t.width<r.x||t.x>r.x+r.width){
				//没有碰撞
			}else{
				return z;
			}
		}
		return null;
	}
	public Zombier zombierCollisionCheck(Rectangle r,int row){
		for(Zombier z:zombiers){
			if(z.row!=row){
				continue;
			}
			Rectangle t=z.defenseBody();
			if(t.y+t.height<r.y||t.y>r.y+r.height||
					t.x+t.width<r.x||t.x>r.x+r.width){
				//没有碰撞
			}else{
				return z;
			}
		}
		return null;
	}
	
	public void lawnmowerCollisionCheck(Rectangle r){
		for(LawnMower l:mowers){
			Rectangle t=l.el().getBounds(true);
			if(t.y+t.height<r.y||t.y>r.y+r.height||
					t.x+t.width<r.x||t.x>r.x+r.width){
				//没有碰撞
			}else{
				mowers.remove(l);
				l.rush();
				return ;
			}
		}
	}
	public void lawnmowerCollisionCheck(Rectangle r,int row){
		for(LawnMower l:mowers){
			if(l.getRow()!=row){
				continue;
			}
			Rectangle t=l.el().getBounds(true);
			if(t.y+t.height<r.y||t.y>r.y+r.height||
					t.x+t.width<r.x||t.x>r.x+r.width){
				//没有碰撞
			}else{
				mowers.remove(l);
				l.rush();
				return ;
			}
		}
	}
	@Override
	protected void doAttachChildren() {
		super.doAttachChildren();
		if(isAttached()){
			for(Planter p:planters){
				ComponentHelper.doAttach(p);
			}
			for(Zombier z:zombiers){
				ComponentHelper.doAttach(z);
			}
			for(Weaponer w:weaponers){
				ComponentHelper.doAttach(w);
			}
		}
	}
	
	@Override
	protected void doDetachChildren() {
		super.doDetachChildren();
		for(Planter  p:planters){
			ComponentHelper.doDetach(p);
		}
		for(Zombier z:zombiers){
			ComponentHelper.doDetach(z);
		}
	}
	
	@Override
	protected boolean remove(Component item) {
		if(item instanceof Planter){
			planters.remove(item);
		}else if(item instanceof Zombier){
			zombiers.remove(item);
		}else if(item instanceof Weaponer){
			weaponers.remove(item);
		}
		return super.remove(item);
	}
	/**
	 * 清除所有的僵尸
	 */
	public void clearZombies(){
		while(zombiers.size()>0){
			remove(zombiers.get(0));
		}
	}

	public List<Zombier> getZombiers() {
		return zombiers;
	}

	public List<Planter> getPlanters() {
		return planters;
	}

	public List<Integer> getEnableRows() {
		return enableRows;
	}

	public void setEnableRows(List<Integer> enableRows) {
		this.enableRows = enableRows;
	}

	@Override
	public void pause() {
		for(Zombier z:zombiers){
			z.pause();
		}
		for(Weaponer w:weaponers){
			w.pause();
		}
		for(Planter p:planters){
			p.pause();
		}
	}

	@Override
	public void resume() {
		for(Zombier z:zombiers){
			z.resume();
		}
		for(Weaponer w:weaponers){
			w.resume();
		}
		for(Planter p:planters){
			p.resume();
		}
	}
	/**
	 * 铲除一株植物
	 * @param row
	 * @param column
	 * @param isGrid  输入参数是否为单元格参数，如果不是，则认为是全局坐标参数
	 * @return
	 */
	public boolean weed(int row_x,int column_y,boolean isGrid){
		int row=-1,column=-1;
		if(!isGrid){
			Point self=el().getXY();
			int left=row_x-self.x;
			int top=column_y-self.y;
			if(left>0&&left<el().getWidth()&&top>0&&top<el().getHeight()){
				row=fixToRow(top);
				column=fixToColumn(left);
			}else{
				return false;
			}
		}
		for(Planter p:planters){
			if(p.row==row&&p.column==column){
				p.reduceHp(10000);
				return true;
			}
		}
		return false;
	}
	
	public boolean isStart() {
		return start;
	}

	@Override
	public void disableSound() {
		for(Component c:getItems()){
			if(c instanceof Soundable){
				((Soundable)c).disableSound();
			}
		}
	}

	@Override
	public void enableSound() {
		for(Component c:getItems()){
			if(c instanceof Soundable){
				((Soundable)c).enableSound();
			}
		}
	}
}
