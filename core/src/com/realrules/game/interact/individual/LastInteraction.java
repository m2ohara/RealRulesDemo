package com.realrules.game.interact.individual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.GameSprite.InteractorType;
import com.realrules.game.main.GameSprite.Status;

public class LastInteraction implements IIndividualInteraction {
	
	private GameSprite interactor;
	
	public LastInteraction(GameSprite interactor) {
		this.interactor = interactor;
	}
	
	public void setStatus() {
		if(interactor.interactorType == InteractorType.Last){
			//If last
			interactor.influenceType = (interactor.behaviour.getInfluenceType());
			interactor.interactStatus = Status.SELECTED;
			interactor.changeOrientationOnInvalid();
			System.out.println("Setting last interactee status: "+interactor.scoreStatus);
		}
	}
	
	public void setInfluencedSprite() {
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(interactor.behaviour.getInfluenceType().ordinal()));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactor.getStartingX(), interactor.getStartingY());
		//Disable to GameSprite can be hit
		handSign.setTouchable(Touchable.disabled);
		GameProperties.get().addActorToStage(handSign);
	}

}
