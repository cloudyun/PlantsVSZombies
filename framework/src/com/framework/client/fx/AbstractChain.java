package com.framework.client.fx;

public abstract class AbstractChain implements Chain {

	protected Chain next=Chain.NONE;
	@Override
	public void setNext(Chain chain) {
		next=chain;
	}

}
