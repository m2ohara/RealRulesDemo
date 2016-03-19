package com.realrules.game.interact.individual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.GameSprite.InteractorType;
import com.realrules.game.main.GameSprite.Status;

public class FirstInteraction implements IIndividualInteraction {
	
	private GameSprite interactor;
	
	public FirstInteraction(GameSprite interactor) {
		this.interactor = interactor;
	}
	
	public void setStatus() {
		//If first
		if(interactor.interactorType == InteractorType.First) {
			if(interactor.interactStatus != Status.INFLUENCED){
				interactor.influenceType = (interactor.behaviour.getInfluenceType());
				interactor.interactStatus = Status.INFLUENCED;
			}
			setInfluencedSprite();
			interactor.isInteracting = true;
			System.out.println("Setting first interactor status: "+interactor.interactStatus);
		}
	}
	
	public void setInfluencedSprite() {
		System.out.println("Setting first interactor handsign for status: "+interactor.interactStatus);
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(interactor.behaviour.getInfluenceType().ordinal()));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactor.getStartingX(), interactor.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}

}
