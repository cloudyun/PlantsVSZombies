package com.framework.client.event;
/**
 * 实现此接口表示可以换皮肤
 * @author xuhengfei email:yonglin4605@163.com
 * @version test 2010-7-2
 */
public interface Skinable{
	public enum Skin{
		Blue,Black
	}
	
	void switchSkin(Skin skin);
}
