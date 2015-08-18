package com.realrules.game.interact;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.demo.GameProperties;
import com.realrules.game.demo.HeadSprite;

public class InfluencerInteractBehaviour implements IInteraction {
	
	private float interactSuccess = 0.2f;
	
	@Override
	public void interact(HeadSprite interactor, HeadSprite interactee) {
		Random rand = new Random();
		
		//TODO: Check interactee isn't hit
		//Influence if interactee is neutral
		if(interactee.status == 0) {
			if(rand.nextFloat() > interactSuccess) {
				interactee.status = 2;
				setInfluenceSprite(interactee);
			}
		}
		
	}
	
	private void setInfluenceSprite(HeadSprite interactee) {
		Actor disk = new Image(new TextureAtlas(Gdx.files.internal("sprites//influenceDisk.pack")).getRegions().get(1));

		disk.setOrigin(disk.getWidth()/2, disk.getHeight()/2);
		disk.setPosition(interactee.getStartingX(), interactee.getStartingY());
		disk.setTouchable(Touchable.disabled);
		
		GameProperties.get().addActorToStage(disk);
	}
	
//	private void setConnectorSprite(HeadSprite interactor) {
//		Coordinates direction = CoordinateSystem.getCoordDirection(interactor.getDirection(), (int)interactor.getRotation());
//		
//		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(1));
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
