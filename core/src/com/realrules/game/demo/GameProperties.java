package com.realrules.game.demo;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameProperties {
	
	private static GameProperties instance;
	
	private GameProperties() {}
	
	public static GameProperties get() {
		if(instance == null) {
			instance = new GameProperties();
		}
		
		return instance;
	}
	
	private List<Integer> followerTypeAmount = Arrays.asList(1, 2, 1);
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
	
	public void replaceActorInGroup(Actor actor) {
		Actor actorToRemove = stage.hit(actor.getX(), actor.getY(), true);
		try {
			actorGroup.removeActor(actorToRemove);
			actorGroup.addActor(actor);
			actorToRemove.remove();
			actor.setTouchable(Touchable.enabled);
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
