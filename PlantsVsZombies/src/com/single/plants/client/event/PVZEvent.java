package com.single.plants.client.event;

import com.framework.client.event.EventType;

public class PVZEvent {

	public static final EventType Start=new EventType();
	
	public static final EventType OnPlantDead=new EventType();
	
	public static final EventType OnBeforeShoot=new EventType();
	public static final EventType OnPlantShoot=new EventType();
	
	public static final EventType OnZombieAdded=new EventType();
	public static final EventType OnZombieDead=new EventType();
	
	public static final EventType OnWeaponSend=new EventType();
	public static final EventType OnWeaponDisappear=new EventType();
	
	/** 当收集到阳光数改变时 */
	public static final EventType OnSunCollectChange=new EventType();
	
	public static final EventType PreviewPlant=new EventType();
	/** 试图种植植物 */
	public static final EventType AddPlant=new EventType();
	/** 植物种植成功 */
	public static final EventType OnPlantAdded=new EventType();
	/** 种植植物失败 */
	public static final EventType OnPlantFailed=new EventType();
	/** 僵尸获胜  冲入家门*/
	public static final EventType ZombieSuccess=new EventType();
	
	public static final EventType DialogYes=new EventType();
	public static final EventType DialogNo=new EventType();
	
	public static final EventType GameStart=new EventType();
	/** 当点击卡片开始拖拽时 */
	public static final EventType CardDragStart=new EventType();
	/** 当卡片拖拽取消 */
	public static final EventType CardDragCancel=new EventType();
	/** 开启声音 */
	public static final EventType EnableSound=new EventType();
	/** 关闭声音 */
	public static final EventType DisableSound=new EventType();
	/**关闭背景音乐*/
	public static final EventType DisableBackgroundSound=new EventType();
	/**播放音乐*/
	public static final EventType PlayMusic=new EventType();
	/** 调节音量 */
	public static final EventType ChangeVolume=new EventType();
	
	public static final EventType OnHide=new EventType();
	/**暂停游戏*/
	public static final EventType Pause=new EventType();
	/**恢复游戏*/
	public static final EventType Resume=new EventType();
	/**更新僵尸状态*/
	public static final EventType UpdateZombieStatus=new EventType();
	/**当血量变化时*/
	public static final EventType OnHpChange=new EventType();
	/**退回主界面*/
	public static final EventType Back=new EventType();
}
