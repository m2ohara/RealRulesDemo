package com.realrules.game.interact;

import com.badlogic.gdx.graphics.Color;
import com.realrules.game.demo.HeadSprite;

public class ManualOpposerInteraction implements IManualInteraction {

	@Override
	public void setToMiddleFollower(HeadSprite hitActor) {
		hitActor.setColor(Color.GRAY);
		hitActor.status = 3;
	}

}
