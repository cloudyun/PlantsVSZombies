package com.single.plants.client.util;

public class Profile {
	/**所有用户*/
	public static final String ALL_USER_KEY="_all_";
	/** 当前用户 */
	public static final String CURRENT_USER_KEY="_user_";
	
	private String name;
	/**关卡*/
	private int stage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}
	
	
}
