package com.framework.client;

/**
 * 版本
 * @author xuhengfei email:yonglin4605@163.com
 * @version test 2010-7-2
 */
public class Version {

	private static final String release="0.1.0";
	private static final int major=0;
	private static final int minor=1;
	private static final int revision=0;
	
	public static String getRelease() {
		return release;
	}
	public static int getMajor() {
		return major;
	}
	public static int getMinor() {
		return minor;
	}
	public static int getRevision() {
		return revision;
	}
	
}
