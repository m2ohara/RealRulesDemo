package com.realrules.game.interact;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.HeadSprite;

public class GossiperInteractBehaviour implements IInteraction {
	
	private float interactSuccess = 0.2f;
	private float promoteOpposeProb = 0.5f;

	@Override
	public void interact(HeadSprite interactor, HeadSprite interactee) {
		Random rand = new Random();
		
		//TODO: Check interactee isn't hit
		//Influence if interactee is neutral
		if(rand.nextFloat() > interactSuccess) {
		if(interactee.status == 0 && interactee.isActive == true) {
			//Oppose
			if(rand.nextFloat() > promoteOpposeProb) {
				interactee.status = 3;
				setInfluenceSprite(interactee, 1);
			}
			//Promote
			else {
				interactee.status = 2;
				setInfluenceSprite(interactee, 0);
			}
		}
		}
		
	}
	
	private void setInfluenceSprite(HeadSprite interactee, int influenceType) {
		
		
		Actor handSign = new Image(new TextureAtlas(Gdx.files.internal("sprites//Meep//Gestures//HandSigns.pack")).getRegions().get(influenceType));

		handSign.setOrigin(handSign.getWidth()/2, handSign.getHeight()/2);
		handSign.setPosition(interactee.getStartingX(), interactee.getStartingY());
		handSign.setTouchable(Touchable.disabled);
		
		GameProperties.get().addActorToStage(handSign);
	}
	
//	private void setConnectorSprite(HeadSprite interactor, int influenceType) {
//		Coordinates direction = CoordinateSystem.getCoordDirection(interactor.getDirection(), (int)interactor.getRotation());
//		
//		int influenceSprite = influenceType == 0 ? 0 : 1;
//		
//		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(influenceSprite ));
//
//		connector.setOrigin(connector.getWidth()/2, connector.getHeight()/2);
//		connector.setPosition(interactor.getStartingX() - 20, interactor.getStartingY() -22);
//		connector.setTouchable(Touchable.disabled);
//		
//		if(direction == Coordinates.E) {
//			connector.rotateBy(270);
//		}
//		else if(direction == Coordinates.S) {
//			connector.rotateBy(180);
//		}
//		else if(direction == Coordinates.W) {
//			connector.rotateBy(90);
//		}
//		
//		
//		GameProperties.get().addActorToStage(connector);
//	}
	
}
