package com.realrules.game.main;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.realrules.game.main.DemoGame.Head;
import com.realrules.game.state.PlayerState;

public class GameProperties {
	
	private static GameProperties instance;
	private List<Head> followerType = null;
	private List<Integer> followerTypeAmount = null;
	private int rewardScore = 3500;
	
	private GameProperties() {
		followerType = Arrays.asList(Head.GOSSIPER, Head.DECEIVER, Head.INFLUENCER);
		followerTypeAmount = Arrays.asList(1, 1, 1);
	}
	
	private GameProperties(PlayerState plState) {
		
	}
	
	public static GameProperties get() {
		if(instance == null) {
			instance = new GameProperties();
		}
		
		return instance;
	}
	
	
	//TODO: Refactor into separate followers class
	public List<Head> getFollowerType() {
		return followerType;
	}
	
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
		actor.getSourceSprite().setTouchable(Touchable.disabled);
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
			actor.getSourceSprite().remove();
			actor.getTargetImage().remove();
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
	
	public int getRewardScore() {
		return rewardScore;
	}
	
	public void dispose() {
		stage.clear();
		actorGroup = new Group();
		actorsToReplace = Arrays.asList();
		soundWaveGroup = new Group();
	}

	

}
