package com.realrules.game.interact;

import com.badlogic.gdx.graphics.Color;
import com.realrules.game.demo.HeadSprite;

public class ManualSupporterInteraction implements IManualInteraction {
	
	public void setToMiddleFollower(HeadSprite hitActor) {
		hitActor.setColor(Color.CYAN);
		hitActor.status = 2;
	}

}
