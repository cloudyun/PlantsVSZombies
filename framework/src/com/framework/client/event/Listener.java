package com.framework.client.event;

import java.util.EventListener;

public interface Listener<E extends BaseEvent> extends EventListener{

	public void handleEvent(E be);
}
