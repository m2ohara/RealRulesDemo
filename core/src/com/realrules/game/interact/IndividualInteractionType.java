package com.realrules.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;

public class IndividualInteractionType implements IInteractionType {
	private GameSprite interactor;
	private GameSprite interactee;
	
	public IndividualInteractionType() {}
	
	public IndividualInteractionType(GameSprite interactor, GameSprite interactee) {
		this.interactor = interactor;
		this.interactee = interactee;
	}
	
	@Override
	public void setInteracts(GameSprite interactor, GameSprite interactee) {
		this.interactor = interactor;
		this.interactee = interactee;
		
	}
	
	//Swipe interaction
	public void setStatus() {
		//If first
		if(interactor.isFirstInteractor) {
			//If first interactor of game
			if(interactor.scoreStatus == 0) {
				interactor.status = (interactor.behaviour.getInfluenceType()-2);
				setFirstInfluencedSprite();
			}
			//If first interactor of following swipes
			else {
				interactor.status = interactor.scoreStatus;
				interactee.status = interactor.behaviour.getInfluenceType();
			}
			System.out.println("Setting first interactor status: "+interactor.status);
		}
		//If last
		else if(!interactee.isIntermediateInteractor){
			interactee.scoreStatus = interactor.behaviour.getInfluenceType();
			interactee.status = 1;
			System.out.println("Setting last interactee status: "+interactee.scoreStatus);
		}
		else {
			//Set interactee to interactor's influence type
			interactee.status = interactor.behaviour.getInfluenceType();
			System.out.println("Setting intermediate interactee status: "+interactee.status);
		}
	}

	//On autonomous interaction complete
	public void complete() {
		interactee.status = interactor.behaviour.getInfluenceType();
		interactor.isOrientationSet();
		interactor.isInteracting = false;
		interactee.isActive = true;
		setInfluencedSprite();
		System.out.println("individual interaction complete");
	}
	
	public void setInfluencedSprite() {
		if(interactee.isIntermediateInteractor) {
			setIntermediateInfluencedSprite();
		}
		else  {
			setLastInfluencedSprite();
		}

	}
	
	private void setFirstInfluencedSprite() {
		System.out.println("Setting first interactor handsign for status: "+interactee.status);
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(interactor.behaviour.getInfluenceType()-2));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactor.getStartingX(), interactor.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
		interactor.isFirstInteractor = false;
	}
	
	private void setIntermediateInfluencedSprite(){
		System.out.println("Setting intermediate interactee handsign for status: "+interactee.status);
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(interactee.status-2));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}
	
	private void setLastInfluencedSprite() {
		System.out.println("Setting last interactee handsign: "+interactee.scoreStatus);
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(interactee.scoreStatus-2));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		//Disable to GameSprite can be hit
		handSign.setTouchable(Touchable.disabled);
		GameProperties.get().addActorToStage(handSign);
	}
}
