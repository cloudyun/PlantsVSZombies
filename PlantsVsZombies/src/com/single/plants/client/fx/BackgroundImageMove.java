package com.single.plants.client.fx;

import com.framework.client.core.El;
import com.framework.client.fx.Chain;
import com.framework.client.fx.Fx;
import com.framework.client.fx.FxConfig;
import com.framework.client.fx.SingleStyleEffect;

public class BackgroundImageMove  extends SingleStyleEffect implements Chain{

	public BackgroundImageMove(El el, double from, double to) {
		super(el, "backgroundPosition", from, to);
	}
	
	@Override
	public void increase(double value) {
		el.setStyleAttribute(style, value+"px");
	}

	private FxConfig config=FxConfig.NONE;
	public void setConfig(FxConfig config){
		this.config=config;
	}
	
	@Override
	public void run() {
		Fx fx=new Fx(config);
		fx.run(this);
	}

	private Chain next;
	@Override
	public void setNext(Chain chain) {
		this.next=chain;
	}

	@Override
	public void onComplete() {
		super.onComplete();
		if(next!=null){
			next.run();
		}
	}


}
