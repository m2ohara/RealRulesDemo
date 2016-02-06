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
	
	public OpposerInteractionType() {}; //TODO: Remove
	
	public OpposerInteractionType(GameSprite interactor, GameSprite interactee) {
		this.interactor = interactor;
		this.interactee = interactee;
	}
	
	//Swipe interaction
	public void setInteractorStatus(GameSprite interactor) {
		interactor.status = 3;		
		System.out.println("Setting middle opposer");
	}
	
	//On autonomous interaction complete
	public void complete() {
		interactee.status = 3;
		interactor.setOrientation();
		interactor.isInteracting = false;
		interactee.isActive = true;
		setInfluencedSprite(interactee);
		System.out.println("opposer interaction complete");
	}
	
	public void setInfluencedSprite(GameSprite interactee) {
		
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(1));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}

}
