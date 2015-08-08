package com.realrules.game.interact;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.demo.CoordinateSystem;
import com.realrules.game.demo.CoordinateSystem.Coordinates;
import com.realrules.game.demo.GameProperties;
import com.realrules.game.demo.HeadSprite;
import com.realrules.game.demo.IInteraction;

public class InfluencerInteractBehaviour implements IInteraction {
	
	@Override
	public void interact(HeadSprite interactor, HeadSprite interactee) {
		Random rand = new Random();
		
		//TODO: Check interactee isn't hit
		//Influence if interactee is neutral
		if(interactee.status == 0) { // && interactee.isActive == true) {
			if(rand.nextFloat() > interactor.argueSuccessP) {
				interactee.status = 2;
				setConnectorSprite(interactor);
				setInfluenceSprite(interactee);
				System.out.println("Follower influenced");
			}
		}
		
	}
	
	private void setConnectorSprite(HeadSprite interactor) {
		Coordinates direction = CoordinateSystem.getCoordDirection(interactor.getDirection(), (int)interactor.getRotation());
		
		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(1));

		connector.setOrigin(connector.getWidth()/2, connector.getHeight()/2);
		connector.setPosition(interactor.getStartingX() - 20, interactor.getStartingY() -22);
		
		if(direction == Coordinates.E) {
			connector.rotateBy(270);
		}
		else if(direction == Coordinates.S) {
			connector.rotateBy(180);
		}
		else if(direction == Coordinates.W) {
			connector.rotateBy(90);
		}
		
		
		GameProperties.get().addActorToStage(connector);
	}
	
	private void setInfluenceSprite(HeadSprite interactee) {
		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//influenceDisk.pack")).getRegions().get(1));

		connector.setOrigin(connector.getWidth()/2, connector.getHeight()/2);
		connector.setPosition(interactee.getStartingX(), interactee.getStartingY());
		
		GameProperties.get().addActorToStage(connector);
	}

}
