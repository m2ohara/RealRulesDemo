package com.realrules.game.interact.individual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.GameSprite;
import com.realrules.game.main.GameSprite.InfluenceType;
import com.realrules.game.main.GameSprite.InteractorType;

public class IntermediateInteraction implements IIndividualInteraction {
	
	private GameSprite interactor;
	private InfluenceType influenceType;
	
	public IntermediateInteraction(GameSprite interactor, InfluenceType influenceType) {
		this.interactor = interactor;
		this.influenceType = influenceType;
	}
	
	public void setStatus() {
		if(interactor.interactorType == InteractorType.Intermediate){
			//Set interactee to interactor's influence type
			interactor.influenceType = influenceType;
			System.out.println("Setting intermediate interactee influence: "+interactor.influenceType);
		}
	}
	
	public void setInfluencedSprite(){
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(influenceType.ordinal()));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactor.getStartingX(), interactor.getStartingY());
		
		GameProperties.get().addActorToStage(handSign);
	}
	

}
