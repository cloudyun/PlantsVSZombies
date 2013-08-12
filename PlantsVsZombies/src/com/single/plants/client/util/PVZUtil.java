package com.single.plants.client.util;

import java.util.Date;

import com.framework.client.state.CookieManager;
import com.framework.client.util.Point;
import com.framework.client.util.Rectangle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.single.plants.client.widget.PlayGround;

public class PVZUtil {
	private static CookieManager manager;

	public static CookieManager getCookieManager(){
		if(manager==null){
			String url=GWT.getHostPageBaseURL();
			String domain=url.substring(7);//去除http://
			if(domain.indexOf(":")!=-1){
				domain=domain.substring(0,domain.indexOf(":"));
			}
			if(domain.indexOf("/")!=-1){
				domain=domain.substring(0,domain.indexOf("/"));
			}
			manager=new CookieManager(null,  new Date(new Date().getTime() + (1000L * 60 * 60 * 24 * 30*12)),domain , false);
		}
		return manager;
	}

	/**
	 * 根据行 列 计算出中心点坐标
	 * @param row
	 * @param column
	 * @return
	 */
	public static Point convertToPiexl(int row,int column){
		return new Point(column*PlayGround.GridWidth+PlayGround.GridWidth/2,row*PlayGround.GridHeight+PlayGround.GridHeight/2);
	}
	/**
	 * 2个矩形的碰撞检测
	 * @param r
	 * @param t
	 * @return
	 */
	public static boolean collisionCheck(Rectangle r,Rectangle t){
		if(t.y+t.height<r.y||t.y>r.y+r.height||
				t.x+t.width<r.x||t.x>r.x+r.width){
			return false;
		}
		return true;
	}
}
