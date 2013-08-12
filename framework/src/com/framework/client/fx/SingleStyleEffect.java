package com.framework.client.fx;

import com.framework.client.core.El;

public class SingleStyleEffect extends BaseEffect{

	public String style;
	
	public double from;
	
	public double to;
	
	public SingleStyleEffect(El el){
		super(el);
	}
	
	public SingleStyleEffect(El el,String style,double from,double to){
		this(el);
		this.style=style;
		this.from=from;
		this.to=to;
	}
	
	public void increase(double value){
		el.setStyleAttribute(style, value);
	}
	
	@Override
	public void onUpdate(double progress) {
		double v=from+(to-from)*progress;
		increase(v);
	}
}
