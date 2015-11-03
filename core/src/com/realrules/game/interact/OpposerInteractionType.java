package com.realrules.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;

public class OpposerInteractionType implements IInteractionType {
	private GameSprite interactor;
	private GameSprite interactee;

	public void setToMiddleFollower(GameSprite hitActor) {
		setInfluencedSprite(hitActor);
		hitActor.status = 3;		
		hitActor.setColor(Color.WHITE);
	}
	
	public OpposerInteractionType() {}; //TODO: Remove
	
	public OpposerInteractionType(GameSprite interactor, GameSprite interactee) {
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
	
	public void setInfluencedSprite(GameSprite interactee) {
		
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(1));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}

}
