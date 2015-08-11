package com.realrules.game.demo;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.realrules.game.demo.CoordinateSystem.Coordinates;

public class DeceiverInteractBehaviour implements IInteraction {

	@Override
	public void interact(HeadSprite interactor, HeadSprite interactee) {
		Random rand = new Random();
		
		//TODO: Check interactee isn't hit
		//Influence if interactee is neutral
		if(interactee.status == 0) { // && interactee.isActive == true) {
			if(rand.nextFloat() > interactor.argueSuccessP) {
				interactee.status = 3;
//				setConnectorSprite(interactor);
				setInfluenceSprite(interactee);
//				System.out.println("Opposer influenced");
			}
		}
		
	}
	
	private void setConnectorSprite(HeadSprite interactor) {
		Coordinates direction = CoordinateSystem.getCoordDirection(interactor.getDirection(), (int)interactor.getRotation());
		
		Actor connector = new Image(new TextureAtlas(Gdx.files.internal("sprites//connectorPack.pack")).getRegions().get(0));

		connector.setOrigin(connector.getWidth()/2, connector.getHeight()/2);
		connector.setPosition(interactor.getStartingX() - 20, interactor.getStartingY() -22);
		connector.setTouchable(Touchable.disabled);
		
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
		
		Actor disk = new Image(new TextureAtlas(Gdx.files.internal("sprites//influenceDisk.pack")).getRegions().get(0));

		disk.setOrigin(disk.getWidth()/2, disk.getHeight()/2);
		disk.setPosition(interactee.getStartingX(), interactee.getStartingY());
		disk.setTouchable(Touchable.disabled);
		
		GameProperties.get().addActorToStage(disk);
	}
	
}
