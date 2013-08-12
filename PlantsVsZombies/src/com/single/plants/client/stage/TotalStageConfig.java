package com.single.plants.client.stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.client.html5.AudioElement;
import com.framework.client.util.HTML5DOM;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.user.client.DOM;
import com.single.plants.client.model.PVZResources;
import com.single.plants.client.model.zombie.AbstractZombie;
import com.single.plants.client.resources.MusicList;
import com.single.plants.client.util.ZombieGen.ZombieConfig;
import com.single.plants.client.widget.Zombier;

/**
 * 所有关卡的配置
 * @author Administrator
 *
 */
public class TotalStageConfig implements PVZResources{
	private static TotalStageConfig totalStageConfig;
	public static TotalStageConfig get(){
		if(totalStageConfig==null){
			totalStageConfig=new TotalStageConfig();
		}
		return totalStageConfig;
	}
	
	private Map<Integer, String> configMap=new HashMap<Integer, String>();
	private TotalStageConfig(){
		configMap.put(1, stageResource.stage1().getText());
		configMap.put(2, stageResource.stage2().getText());
		configMap.put(3, stageResource.stage3().getText());
		configMap.put(4, stageResource.stage4().getText());
		configMap.put(5, stageResource.stage5().getText());
		configMap.put(6, stageResource.stage6().getText());
	}
	/**
	 * 获取关卡的配置
	 * @param stage
	 * @return
	 */
	public StageConfig get(Integer stage){
		return parse(configMap.get(stage));
	}
	
	/**
	 * 根据JSON配置文件解析出关卡的敌人信息
	 * @param json
	 * @return
	 */
	private static  StageConfig parse(String json){
		JSONObject stage=(JSONObject) JSONParser.parse(json);
		JSONArray preview=(JSONArray) stage.get("preview");
		JSONArray battle=(JSONArray) stage.get("battle");
		JSONNumber collection=(JSONNumber)stage.get("collection");
		JSONBoolean shovel=(JSONBoolean)stage.get("shovel");
		JSONArray enableRows=(JSONArray) stage.get("enableRows");
		JSONString scene=(JSONString) stage.get("scene");
		
		List<Zombier> previewList=new ArrayList<Zombier>();
		for(int i=0;i<preview.size();i++){
			JSONObject zombie=(JSONObject) preview.get(i);
			String id=((JSONString)zombie.get("id")).stringValue();
			Zombier z=AbstractZombie.zombiers.get(id).createZombier();
			z.row=Integer.valueOf(zombie.get("row").toString());
			z.column=Integer.valueOf(zombie.get("column").toString());
			previewList.add(z);
		}
		
		List<List<ZombieConfig>> battleList=new ArrayList<List<ZombieConfig>>();
		for(int i=0;i<battle.size();i++){
			JSONArray batch=(JSONArray) battle.get(i);
			List<ZombieConfig> batchList=new ArrayList<ZombieConfig>();
			for(int j=0;j<batch.size();j++){
				JSONObject zombie=(JSONObject) batch.get(j);
				String id=((JSONString)zombie.get("id")).stringValue();
				Zombier z=AbstractZombie.zombiers.get(id).createZombier();
				z.row=Integer.valueOf(zombie.get("row").toString());
				double delay=Double.valueOf(zombie.get("delay").toString());
				ZombieConfig config=new ZombieConfig(z.row, z, delay);
				batchList.add(config);
			}
			battleList.add(batchList);
		}
		
		
		StageConfig config=new StageConfig();
		config.setPreviewList(previewList);
		config.setBattleList(battleList);
		
		//阳光数
		if(collection!=null){
			config.setCollection((int) collection.doubleValue());
		}
		//是否激活铲子
		if(shovel!=null){
			config.setShovel(shovel.booleanValue());
		}
		//激活哪几行
		if(enableRows!=null){
			Integer[] rows=new Integer[enableRows.size()];
			for(int i=0;i<enableRows.size();i++){
				rows[i]=(int) ((JSONNumber)enableRows.get(i)).doubleValue();
			}
			config.setEnableRows(rows);
		}
		//背景音乐
		String music=null;
		if(scene!=null){
			if("day".equals(scene)){
				music=MusicList.Mainmusic01;
			}else if("night".equals(scene)){
				music=MusicList.Mainmusic02;
			}
		}else{//默认白天草坪
			music=MusicList.Mainmusic01;
		}
		config.setMusic(music);
		return config;
	}

}
