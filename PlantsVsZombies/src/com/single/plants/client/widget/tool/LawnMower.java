package com.single.plants.client.widget.tool;

import com.framework.client.Framework;
import com.framework.client.html5.AudioElement;
import com.framework.client.util.HTML5DOM;
import com.framework.client.util.Point;
import com.framework.client.widget.Component;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.single.plants.client.contant.PVZContant;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.util.PVZConfig;
import com.single.plants.client.util.PVZUtil;
import com.single.plants.client.widget.PlayGround;
import com.single.plants.client.widget.Zombier;

public class LawnMower extends Component implements PVZResources {

	private int row;
//	private AudioElement sound;
	public LawnMower(int row){
		super("lawnmower");
		this.row=row;
		setElement(DOM.createDiv());
		el().setZIndex(Framework.getTopZIndex()+10);
		
//		sound=HTML5DOM.createAudioElement().cast();
//		sound.setSrc(musicResource.MusicPath+"lawnmower.ogg");
//		getElement().appendChild(sound);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		fixPosition();
	}
	/**
	 * 根据自身的大小，调整位置
	 */
	public void fixPosition(){
		Point point=PVZUtil.convertToPiexl(row, 0);
		el().setLeft(-50);
		el().setTop(point.y-15);
	}
	
	public void rush(){
		if(PVZConfig.get().isSoundable()){
//			sound.play();
		}
		Timer timer=new Timer() {
			@Override
			public void run() {
				int left=LawnMower.this.el().getLeft()+10;
				LawnMower.this.el().setLeft(left);
				PlayGround playGround=(PlayGround) LawnMower.this.getParent();
				
				Zombier z=playGround.zombierCollisionCheck(LawnMower.this.el().getBounds(true),row);
				while(z!=null){
					z.reduceHP(PVZContant.CAR_POWER);
					z=playGround.zombierCollisionCheck(LawnMower.this.el().getBounds(true),row);
				}
				if(left>800){
					cancel();
					LawnMower.this.removeFromParent();
				}
			}
		};
		timer.scheduleRepeating(20);
	}

	public int getRow() {
		return row;
	}
}

