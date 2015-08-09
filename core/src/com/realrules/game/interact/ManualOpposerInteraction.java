package com.realrules.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.demo.GameProperties;
import com.realrules.game.demo.HeadSprite;

public class ManualOpposerInteraction implements IManualInteraction {

	@Override
	public void setToMiddleFollower(HeadSprite hitActor) {
		setInfluenceSprite(hitActor);
		hitActor.status = 3;		
		hitActor.setColor(Color.WHITE);
	}
	
	private void setInfluenceSprite(HeadSprite interactee) {
		
		Actor disk = new Image(new TextureAtlas(Gdx.files.internal("sprites//influenceDisk.pack")).getRegions().get(0));

		disk.setOrigin(disk.getWidth()/2, disk.getHeight()/2);
		disk.setPosition(interactee.getStartingX(), interactee.getStartingY());
		
		GameProperties.get().addActorToStage(disk);
	}

}
