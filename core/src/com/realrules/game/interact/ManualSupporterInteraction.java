package com.realrules.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.InteractSprite;

public class ManualSupporterInteraction implements IManualInteraction {
	
	private int origStatus;
	private float interactionStateLength = 2f;
	private InteractSprite interactSprite;
	
	public void setToMiddleFollower(HeadSprite hitActor) {
		setInfluenceSprite(hitActor);
		hitActor.status = 2;
		hitActor.setColor(Color.WHITE);
	}
	
	public void interact(HeadSprite interactor, HeadSprite interactee) {

		//Influence if interactee is neutral and interactor isn't already interacting
		if(interactor.status == 1 && interactee.status == 0) {
			interactor.isActive = true;
			interactSprite = new InteractSprite(interactionStateLength, 3, interactor);

		}
		//Perform interaction
		if(interactSprite != null && interactSprite.isComplete() == true) {
			interactor.status = origStatus;
			interactee.status = 1;
			setInfluenceSprite(interactee);
		}
	}
	
	private void setInfluenceSprite(HeadSprite interactee) {
		
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(0));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}

}
