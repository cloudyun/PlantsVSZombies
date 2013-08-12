package com.framework.client.fx;

public interface Effect {
	  public void onCancel();

	  public void onComplete();

	  public void onStart();

	  public void onUpdate(double progress);
}
