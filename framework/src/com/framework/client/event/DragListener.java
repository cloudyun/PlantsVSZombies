package com.framework.client.event;

public class DragListener implements Listener<DragEvent>{

	
	
	@Override
	public void handleEvent(DragEvent de) {
		EventType type=de.getType();
		if(type==CustomEvent.DragCancel){
			dragCancel(de);
		}else if(type==CustomEvent.DragEnd){
			dragEnd(de);
		}else if(type==CustomEvent.DragMove){
			dragMove(de);
		}else if(type==CustomEvent.DragStart){
			dragStart(de);
		}
	}
	
	public void dragStart(DragEvent de){
		
	}
	
	public void dragMove(DragEvent de){
		
	}
	
	public void dragEnd(DragEvent de){
		
	}
	
	public void dragCancel(DragEvent de){
		
	}

}
