package com.single.plants.client.stage;

import java.util.ArrayList;
import java.util.List;

import com.framework.client.html5.AudioElement;
import com.google.gwt.resources.client.DataResource;
import com.single.plants.client.util.ZombieGen.ZombieConfig;
import com.single.plants.client.widget.Zombier;

public class StageConfig {
	/**起始阳光数*/
	private int collection=50;
	/**是否激活铲子*/
	private boolean shovel=true;
	/**激活哪几行*/
	private Integer[] enableRows=new Integer[]{0,1,2,3,4};
	/**背景音乐*/
	private String music;
	
	private List<Zombier> previewList=new ArrayList<Zombier>();
	
	private List<List<ZombieConfig>> battleList=new ArrayList<List<ZombieConfig>>();

	public List<Zombier> getPreviewList() {
		return previewList;
	}

	public void setPreviewList(List<Zombier> previewList) {
		this.previewList = previewList;
	}

	public List<List<ZombieConfig>> getBattleList() {
		return battleList;
	}

	public void setBattleList(List<List<ZombieConfig>> battleList) {
		this.battleList = battleList;
	}

	public int getCollection() {
		return collection;
	}

	public void setCollection(int collection) {
		this.collection = collection;
	}

	public boolean isShovel() {
		return shovel;
	}

	public void setShovel(boolean shovel) {
		this.shovel = shovel;
	}

	public Integer[] getEnableRows() {
		return enableRows;
	}

	public void setEnableRows(Integer[] enableRows) {
		this.enableRows = enableRows;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	
}
