package com.single.plants.client.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.client.state.CookieManager;

public class ProfileUtil {

	private static CookieManager cookieManager=PVZUtil.getCookieManager();
	/**
	 * 获取当前用户
	 * @return
	 */
	public static Profile getCurrentProfile(){
		String name=cookieManager.getString(Profile.CURRENT_USER_KEY);
		if(name==null){
			return null;
		}
		Map<String, Object> map=cookieManager.getMap(name);
		return convertMapToProfile(map);
	}
	/**
	 * 设置当前用户
	 * @param profile
	 */
	public static void setCurrentProfile(String name){
		Map<String, Object> map=cookieManager.getMap(name);
		Profile profile=convertMapToProfile(map);
		if(profile!=null){
			cookieManager.set(Profile.CURRENT_USER_KEY, profile.getName());
		}
	}
	/**
	 * 更新用户信息
	 * 不得修改名称(name)
	 * @param profile
	 */
	public static void updateProfile(Profile profile){
		cookieManager.set(profile.getName(), convertProfileToMap(profile));
	}
	/**
	 * 更新当前用户的最高通过关卡
	 * @param stage
	 */
	public static void updateCurrentStage(int stage){
		Profile profile=ProfileUtil.getCurrentProfile();
		profile.setStage(stage);
		ProfileUtil.updateProfile(profile);
	}
	
	/**
	 * 添加用户
	 * @param profile
	 * @throws PVZException
	 */
	public static void addProflie(Profile profile) throws PVZException{
		if(profile.getName()==null||profile.getName().trim().equals("")){
			throw new PVZException("名称不能为空");
		}
		List<String> users=getUsers();
		if(users.contains(profile.getName())){
			throw new PVZException("该用户已经存在!");
		}
		if(users.size()>5){
			throw new PVZException("用户数已经到底上限，无法再添加用户");
		}
		Map<String, Object> map=convertProfileToMap(profile);
		cookieManager.set(profile.getName(), map);
		users.add(profile.getName());
		cookieManager.set(Profile.ALL_USER_KEY, users);
	}
	/**
	 * 删除一个用户
	 * @param name
	 */
	public static void removeProfile(String name){
		List<String> users=getUsers();
		users.remove(name);
		cookieManager.set(Profile.ALL_USER_KEY, users);
		cookieManager.clear(name);
	}
	/**
	 * 获取所有用户列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getUsers(){
		List<String> users=(List<String>) cookieManager.get(Profile.ALL_USER_KEY);
		if(users==null){
			users=new ArrayList<String>();
		}
		return users;
	}
	/**
	 * 获得大关卡
	 * @return
	 */
	public static int prefixStage(){
		int stage=getCurrentProfile().getStage();
		return stage/5+1;
	}
	/**
	 * 获得小关卡
	 * @return
	 */
	public static int suffixStage(){
		int stage=getCurrentProfile().getStage();
		return stage%5+1;
	}
	
	private static Map<String, Object> convertProfileToMap(Profile profile){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", profile.getName());
		map.put("stage", profile.getStage());
		return map;
	}
	
	private static Profile convertMapToProfile(Map<String, Object> map){
		Profile profile=new Profile();
		profile.setName((String) map.get("name"));
		profile.setStage((Integer) map.get("stage"));
		return profile;
	}
}
