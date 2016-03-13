package com.realrules.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.GameSprite.InfluenceType;
import com.realrules.game.main.GameSprite.InteractorType;
import com.realrules.game.main.GameSprite.Status;

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
//		if(interactor.interactorType == InteractorType.First) {
//			//If first interactor of game
//			if(interactor.scoreStatus == 0) {
//				interactor.influenceType = (interactor.behaviour.getInfluenceType());
//				interactor.interactStatus = Status.INFLUENCED;
//				interactee.changeOrientationOnInvalid();
//				setFirstInfluencedSprite();
//				return;
//			}
//			//If first interactor of following swipes
//			else {
////				interactor.interactStatus = interactor.scoreStatus;
//				if(interactee.isIntermediateInteractor){
//					interactee.interactStatus = interactor.behaviour.getInfluenceType();
//				}
////				else {
////					interactee.scoreStatus = interactor.behaviour.getInfluenceType();
////					interactee.interactStatus = 1;
////					interactor.changeOrientationOnInvalid();
////					System.out.println("Setting last interactee status: "+interactee.scoreStatus);
////				}
//			}
//			System.out.println("Setting first interactor status: "+interactor.interactStatus);
//		}
		if(interactor.interactorType == InteractorType.First) {
			if(interactor.interactStatus != Status.INFLUENCED){
				interactor.influenceType = (interactor.behaviour.getInfluenceType());
				interactor.interactStatus = Status.INFLUENCED;
			}
			interactee.changeOrientationOnInvalid();
			setFirstInfluencedSprite();
			interactor.isInteracting = true;
			System.out.println("Setting first interactor status: "+interactor.interactStatus);
		}
		//If intermediate
		else if(interactor.interactorType == InteractorType.Intermediate){
			//Set interactee to interactor's influence type
			interactor.influenceType = (interactor.behaviour.getInfluenceType());
			interactor.interactStatus = Status.INFLUENCED;
			System.out.println("Setting intermediate interactee status: "+interactee.interactStatus);
		}
		else {
			//If last
			interactor.influenceType = (interactor.behaviour.getInfluenceType());
			interactee.interactStatus = Status.SELECTED;
			interactor.changeOrientationOnInvalid();
			System.out.println("Setting last interactee status: "+interactee.scoreStatus);
		}
	}

	//On autonomous interaction complete
	public void complete() {
		interactee.influenceType = interactor.behaviour.getInfluenceType();
		interactee.interactStatus = Status.INFLUENCED;
		interactor.changeOrientationOnInvalid();
		interactor.isInteracting = false;
		interactee.isActive = true;
		setInfluencedSprite();
		System.out.println("individual auto interaction complete");
	}
	
	public void setInfluencedSprite() {
		if(interactor.interactorType == InteractorType.Intermediate) {
			setIntermediateInfluencedSprite();
		}
		else  {
			setLastInfluencedSprite();
		}

	}
	
	private void setFirstInfluencedSprite() {
		System.out.println("Setting first interactor handsign for status: "+interactee.interactStatus);
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(interactor.behaviour.getInfluenceType().ordinal()));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactor.getStartingX(), interactor.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}
	
	private void setIntermediateInfluencedSprite(){
		System.out.println("Setting intermediate interactee handsign for status: "+interactee.interactStatus);
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(interactor.behaviour.getInfluenceType().ordinal()));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}
	
	private void setLastInfluencedSprite() {
		System.out.println("Setting last interactee handsign: "+interactee.scoreStatus);
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(interactor.behaviour.getInfluenceType().ordinal()));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		//Disable to GameSprite can be hit
		handSign.setTouchable(Touchable.disabled);
		GameProperties.get().addActorToStage(handSign);
	}
}
