package com.realrules.game.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.realrules.game.interact.SwipeInteraction;
import com.realrules.game.main.Game.Head;
import com.realrules.game.state.PlayerState;

public class GameProperties {

	private static GameProperties instance;
	private List<Head> followerType = null;
	private List<Integer> followerTypeAmount = null;
	private int rewardScore = 3500;
	private ArrayList<GameSprite> gameSprites = new ArrayList<GameSprite>();
	public boolean IsSwipeInteraction = false;
	public SwipeInteraction swipeInteraction = null;

	private GameProperties() {
		followerType = Arrays.asList(Head.GOSSIPER, Head.INFLUENCER, Head.DECEIVER);//TODO: Set from DB
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

	public void Load() {

	}	
	
	public void setSwipeInteraction(SwipeInteraction swipeInteraction) {
		this.swipeInteraction = swipeInteraction;
	}
	
	public SwipeInteraction getSwipeInteraction() {
		return this.swipeInteraction;
	}

	//TODO: Refactor into separate followers class
	public List<Head> getFollowerType() {
		return followerType;
	}

	public List<Integer> getfollowerTypeAmount() {
		return followerTypeAmount;
	}	

	private float universalTimeRatio = 0.7f;

	public float getUniversalTimeRatio() {
		return universalTimeRatio;
	}

	private Group actorGroup = new Group();
	public Group getActorGroup() {
		return actorGroup;
	}

	public ArrayList<GameSprite> getGameSprites() {
		gameSprites.clear();
		Actor[] actors = ((Actor[])actorGroup.getChildren().toArray());
		for(Actor actor : actors) {
			gameSprites.add((GameSprite) actor);
		}
		return gameSprites;	
	}

	public void addToActorGroup(Actor actor) {
		this.actorGroup.addActor(actor);
	}

	public List<MoveableSprite> actorsToReplace = Arrays.asList();

	public void replaceActorInGroup(MoveableSprite actor) {
		
		//Ensure HeadSprite actor gets hit
		setActorGroupOriginToZero();
		actor.getSourceSprite().setTouchable(Touchable.disabled);
		System.out.println("replacing actor at "+actor.getCurrentX()+", "+actor.getCurrentY());
		Actor actorToRemove = stage.hit(actor.getCurrentX(), actor.getCurrentY(), true);
		try {
			//Remove current actor at coordinates
			actorGroup.removeActor(actorToRemove);
			actorToRemove.remove();

			GameSprite actorToAdd = new GameSprite(actor.getType(), actor.getCurrentX(), actor.getCurrentY(), actor.getFramesPath(), false);
			if(((GameSprite)actorToRemove).status == 1) {
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
		setActorGroupOriginToCentre();
	}
	
	//Hack for hitting scaled actors. NB always reset to centre when finished hit
	private void setActorGroupOriginToZero() {
		for(Actor actor : actorGroup.getChildren()) {
			actor.setOrigin(0f, 0f);
		}
	}
	
	private void setActorGroupOriginToCentre() {
		for(Actor actor : actorGroup.getChildren()) {
			actor.setOrigin(actor.getWidth()/2, actor.getHeight()/2);
		}
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
		gameSprites.clear();
	}



}
