package com.realrules.game.interact.individual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.GameSprite.InfluenceType;
import com.realrules.game.main.GameSprite.InteractorType;
import com.realrules.game.main.GameSprite.Status;
import com.realrules.game.state.GameScoreState;

public class LastInteraction implements IIndividualInteraction {
	
	private GameSprite interactor;
	private InfluenceType influenceType;
	
	public LastInteraction(GameSprite interactor, InfluenceType influenceType) {
		this.interactor = interactor;
		this.influenceType = influenceType;
	}
	
	public void setStatus() {
		if(interactor.interactorType == InteractorType.Last){
			//If last
			interactor.influenceType = influenceType;
			interactor.interactStatus = Status.SELECTED;
			interactor.changeOrientationOnInvalid();
			setSelectedInteractee();
			GameProperties.get().resetTapCount();
			System.out.println("Setting last interactee influence: "+interactor.influenceType);
		}
	}
	
	public void setInfluencedSprite() {
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(influenceType.ordinal()));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactor.getStartingX(), interactor.getStartingY());
		//Disable to GameSprite can be hit
		handSign.setTouchable(Touchable.disabled);
		GameProperties.get().addActorToStage(handSign);
	}
	
	private void setSelectedInteractee() {
		if(GameScoreState.validTouchAction()) {
			interactor.setColor(Color.ORANGE);
		}
		else {
			interactor.setColor(Color.YELLOW);
		}
		
		//Can last interactee interact on next swipe
		if(interactor.changeOrientationOnInvalid()) {
			GameProperties.get().isAutoInteractionAllowed = false;
		}		
		interactor.isActive = true;
	}

}
