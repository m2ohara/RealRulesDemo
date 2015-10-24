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
	private HeadSprite interactor;
	private HeadSprite interactee;

	public void setToMiddleFollower(HeadSprite hitActor) {
		setInfluencedSprite(hitActor);
		hitActor.status = 3;		
		hitActor.setColor(Color.WHITE);
	}
	
	public ManualOpposerInteraction() {}; //TODO: Remove
	
	public ManualOpposerInteraction(HeadSprite interactor, HeadSprite interactee) {
		this.interactor = interactor;
		this.interactee = interactee;
	}
	
	@Override
	public void complete() {
		interactor.isInteracting = false;
		interactee.status = 3;
		interactee.isActive = true;
		setInfluencedSprite(interactee);
	}
	
	public void setInfluencedSprite(HeadSprite interactee) {
		
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(1));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}

}
