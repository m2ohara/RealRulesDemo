package com.realrules.game.demo;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.realrules.game.demo.DemoGame.Head;

public class GameProperties {
	
	private static GameProperties instance;
	
	private GameProperties() {}
	
	public static GameProperties get() {
		if(instance == null) {
			instance = new GameProperties();
		}
		
		return instance;
	}
	
	
	//TODO: Refactor into separate followers class
	private List<Head> followerType = Arrays.asList(Head.GOSSIPER, Head.DECEIVER, Head.INFLUENCER);
	public List<Head> getFollowerType() {
		return followerType;
		
	}
	
	private List<Integer> followerTypeAmount = Arrays.asList(1, 0, 0);
	public List<Integer> getfollowerTypeAmount() {
		return followerTypeAmount;
	}
	
	private List<String> spritePaths = Arrays.asList( "sprites//gossiperFollowerPack.pack", "sprites//deceiverFollowerPack.pack", "sprites//promoterFollowerPack.pack" );
	public List<String> getSpritePaths() {
		return spritePaths;
	}
	
	
	private float universalTimeRatio = 0.7f;
	
	public float getUniversalTimeRatio() {
		return universalTimeRatio;
	}
	
	private Group actorGroup = new Group();
	public Group getActorGroup() {
		return actorGroup;
	}

	public void addToActorGroup(Actor actor) {
		this.actorGroup.addActor(actor);
	}
	
	public List<MoveableSprite> actorsToReplace = Arrays.asList();
	
	public void replaceActorInGroup(MoveableSprite actor) {
		//Ensure HeadSprite actor gets hit
		actor.setTouchable(Touchable.disabled);
		actor.getInstance().setTouchable(Touchable.disabled);
		Actor actorToRemove = stage.hit(actor.getCurrentX(), actor.getCurrentY(), true);
		try {
			//Remove current actor at coordinates
			actorGroup.removeActor(actorToRemove);
			actorToRemove.remove();
			
			HeadSprite actorToAdd = new HeadSprite(actor.getType(), actor.getCurrentX(), actor.getCurrentY(), actor.getFramesPath(), false);
			if(((HeadSprite)actorToRemove).status == 1) {
				actorToAdd.status = 1;
				actorToAdd.setColor(Color.YELLOW);
			}
			actorGroup.addActor(actorToAdd);
			//Remove placeholder
			actor.getInstance().remove();
			actor.remove();
		}
		catch(Exception ex) {
			System.out.println("Exception replacing actor on stage "+ex);
		}
	}
	
	private Group soundWaveGroup = new Group();

	public Group getSoundWaveGroup() {
		return soundWaveGroup;
	}

	public void addToSoundWaveGroup(Actor soundWave) {
		this.soundWaveGroup.addActor(soundWave);
	}
	

	private Stage stage = null;
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void addActorToStage(Actor actor) {
		this.stage.addActor(actor);
	}
	
	public void dispose() {
		stage.dispose();
	}

	

}
