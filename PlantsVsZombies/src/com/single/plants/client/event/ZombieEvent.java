package com.single.plants.client.event;

import com.framework.client.event.BaseEvent;
import com.framework.client.event.EventType;
import com.single.plants.client.model.zombie.Zombie;
import com.single.plants.client.widget.Zombier;

public class ZombieEvent extends BaseEvent {

	private Zombier zombier;
	private Status status;
	/**
	 * 前进
	 * 暂停
	 * 攻击
	 *
	 */
	public enum Status{
		/*前进*/Forward,/*停止*/Hold,/*攻击*/Attack,/*爆炸身亡*/BombDie,/*无头僵尸*/LostHead,/*正常死亡*/Dead
	}
	
	public ZombieEvent(EventType type,Zombier z) {
		super(type);
		this.zombier=z;
	}

	public Zombier getZombier() {
		return zombier;
	}

	public void setZombier(Zombier zombier) {
		this.zombier = zombier;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	

}
