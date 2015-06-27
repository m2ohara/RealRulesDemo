package com.realrules.game.demo;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameProperties {
	
	private static GameProperties instance;
	
	private GameProperties() {}
	
	public static GameProperties get() {
		if(instance == null) {
			instance = new GameProperties();
		}
		
		return instance;
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
