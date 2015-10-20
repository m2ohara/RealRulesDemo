package com.realrules.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.InteractSprite;
import com.realrules.game.main.ManualInteractSprite;

public class ManualOpposerInteraction implements IManualInteraction {
	
	private int origStatus;
	private float interactionStateLength = 2f;
	private ManualInteractSprite interactSprite;

	@Override
	public void setToMiddleFollower(HeadSprite hitActor) {
		setInfluenceSprite(hitActor);
		hitActor.status = 3;		
		hitActor.setColor(Color.WHITE);
	}
	
	public void interact(HeadSprite interactor, HeadSprite interactee) {

		//Influence if interactee is neutral and interactor isn't already interacting
		if(interactee.status == 0) {
			if(interactor.status == 1) {
				interactor.isActive = true;
			}
			interactSprite = new ManualInteractSprite(interactionStateLength, 3, interactor, interactee, 1);

		}
		//Perform interaction
//		if(interactSprite != null && interactSprite.isComplete() == true) {
//			interactor.status = origStatus;
//			interactee.status = 1;
//			setInfluenceSprite(interactee);
//		}
	}
	
	private void setInfluenceSprite(HeadSprite interactee) {
		
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(1));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}

}
