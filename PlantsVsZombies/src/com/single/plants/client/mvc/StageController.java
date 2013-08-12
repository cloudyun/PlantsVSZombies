package com.single.plants.client.mvc;

import static com.single.plants.client.event.PVZEvent.*;

import com.framework.client.mvc.AppEvent;
import com.framework.client.mvc.Controller;
import com.single.plants.client.stage.Stage1;
import com.single.plants.client.stage.Stage2;
import com.single.plants.client.stage.Stage3;
import com.single.plants.client.stage.Stage4;
import com.single.plants.client.stage.Stage5;
import com.single.plants.client.stage.Stage6;
import com.single.plants.client.util.Profile;
import com.single.plants.client.util.ProfileUtil;

public class StageController extends Controller {

	private StageView stage;
	public StageController(){
		registerEventTypes(GameStart,Back);
		registerEventTypes(Pause,Resume,OnSunCollectChange);
		registerEventTypes(EnableSound,DisableSound);
	}
	@Override
	public void handleEvent(AppEvent event) {
		if(event.getType()==GameStart){
			if(stage!=null){
				stage.destory();
				stage=null;
			}
			Profile profile=ProfileUtil.getCurrentProfile();
			Integer s=profile.getStage()+1;
			stage=selectStage(s);
			forwardToView(stage, event);
			return;
		}else if(event.getType()==Back){
			if(stage!=null){
				stage.destory();
				stage=null;
			}
			return;
		}
		if(stage!=null){
			forwardToView(stage, event);
		}
		
	}
	/**
	 * 获得制定关卡
	 * @param stage
	 * @return
	 */
	private StageView selectStage(Integer stage){
		StageView view=null;
		switch (stage) {
		case 1:
			view=new Stage1(this);
			break;
		case 2:
			view=new Stage2(this);
			break;
		case 3:
			view=new Stage3(this);
			break;
		case 4:
			view=new Stage4(this);
			break;
		case 5:
			view=new Stage5(this);
			break;
		case 6:
			view=new Stage6(this);
			break;
		default:
			break;
		}
		return view;
	}

}
