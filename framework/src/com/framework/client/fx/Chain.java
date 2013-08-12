package com.framework.client.fx;
/**
 * 链式传递
 */
public interface Chain {

	static Chain NONE=new Chain(){
		public void run() {
		}
		public void setNext(Chain chain) {
		}
	};
	
	void setNext(Chain chain);
	
	void run();
}
