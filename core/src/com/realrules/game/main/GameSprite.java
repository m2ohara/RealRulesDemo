package com.realrules.game.main;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.realrules.game.act.OnAnimateTalkingAct;
import com.realrules.game.behaviour.Behaviour;
import com.realrules.game.behaviour.DeceiverProperties;
import com.realrules.game.behaviour.GossiperProperties;
import com.realrules.game.behaviour.IBehaviourProperties;
import com.realrules.game.behaviour.ISpriteBehaviour;
import com.realrules.game.behaviour.PromoterProperties;
import com.realrules.game.interact.AutonomousInteraction;
import com.realrules.game.interact.DeceiverInteraction;
import com.realrules.game.interact.GossiperInteraction;
import com.realrules.game.interact.IInteractionType;
import com.realrules.game.interact.PromoterInteraction;
import com.realrules.game.main.Game.Head;
import com.realrules.game.main.WorldSystem.Orientation;
import com.realrules.game.state.GameScoreState;
import com.realrules.game.touch.DeceiverTouchAction;
import com.realrules.game.touch.GossiperTouchAction;
import com.realrules.game.touch.PromoterTouchAction;

public class GameSprite  extends Image  {
	public ISpriteBehaviour behaviour;
	
	public ISpriteBehaviour getBehaviour() {
		return behaviour;
	}

	public AutonomousInteraction interaction;
	public float startingX;
	public float startingY;
	public boolean isActive = true;
	public int status = 0; //0 : neutral, 1 : for 2 : against
	public boolean isManualInteractor = false;
	private ArrayList<Orientation> validDirections;
	
	public boolean isInteracting = false;
	private boolean isActing = false;
	private String framesPath = null;
	private static String defaultPack = "Default.pack";
	private Head type = null;
	
	public int getXGameCoord() {
		return WorldSystem.get().getGameXCoords().indexOf(this.startingX);
	}
	
	public int getYGameCoord() {
		return WorldSystem.get().getGameYCoords().indexOf(this.startingY);
	}

	public float getStartingX() {
		return startingX;
	}

	public float getStartingY() {
		return startingY;
	}
	
	public boolean isActing() {
		return isActing;
	}
	
	public String getFramesPath() {
		return framesPath;
	}
	
	public Orientation getOrientation() {
		return behaviour.getOrientation();
	}
	
	public GameSprite(Head type, float x, float y, String framesPath, boolean isActive) {
		super(new TextureAtlas(Gdx.files.internal(framesPath+defaultPack)).getRegions().get(0));

		//Centre origin in frame for rotation;
		TextureRegion currentFrame  = new TextureAtlas(Gdx.files.internal(framesPath+defaultPack)).getRegions().get(0);
		this.setOrigin(currentFrame.getRegionWidth()/2, currentFrame.getRegionHeight()/2);
		this.setPosition(x, y);
		
		float scaleFactor = WorldSystem.get().getLevelScaleFactor();
		this.setScale(scaleFactor);
		
		this.startingX = x;
		this.startingY = y;
		this.framesPath = framesPath;
		this.type = type;
	}
	
	public void setBehaviour(IInteractionType manInteraction) {
		
		if(validDirections == null) {
			setValidDirections();
		}
		
		if(type == type.GOSSIPER) {
			IBehaviourProperties properties = new GossiperProperties();
			behaviour = new Behaviour(
					isActive, 
					new OnAnimateTalkingAct(properties.getRotateProbability(), properties.getInteractProbability(), this, validDirections),
					new GossiperTouchAction(manInteraction, getXGameCoord(), getYGameCoord()), properties);
			interaction = new GossiperInteraction();

		}
		if(type == type.DECEIVER) {
			IBehaviourProperties properties = new DeceiverProperties();
			behaviour = new Behaviour(
					isActive, 
					new OnAnimateTalkingAct(properties.getRotateProbability(), properties.getInteractProbability(), this, validDirections),
					new DeceiverTouchAction(manInteraction, getXGameCoord(), getYGameCoord()), properties);
			interaction = new DeceiverInteraction();
		}
		if(type == type.INFLUENCER) {
			IBehaviourProperties properties = new PromoterProperties();
			behaviour = new Behaviour(
					isActive, 
					new OnAnimateTalkingAct(properties.getRotateProbability(), properties.getInteractProbability(), this, validDirections),
					new PromoterTouchAction(manInteraction, getXGameCoord(), getYGameCoord()), properties);
			interaction = new PromoterInteraction();
		}
		
		//Refactor into Behaviour
		setTouchAction();

		this.isActing = true;
	}
	
	//Implement onTouch action
	private void setTouchAction() {
		
		this.addListener(new ClickListener() {
			
			public void clicked(InputEvent event, float x, float y) 
		    {
				if(GameScoreState.validTouchAction()) {
					System.out.println("Pressed at: x: "+x+", y: "+y+"");
					behaviour.onTouch();
				}
		    }
			
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if(isActive && isActing){
			behaviour.onAct(delta, this, validDirections);
		}
	}
	
	//TODO: Refactor into IBehaviourProperties
	public void setValidDirections() {
		
		validDirections = new ArrayList<Orientation> (Arrays.asList(Orientation.N, Orientation.E, Orientation.S, Orientation.W));
		
		if(getXGameCoord() == WorldSystem.get().getSystemWidth()-1 || WorldSystem.get().getMemberFromCoords(getXGameCoord() + 1, getYGameCoord()) == null) {
			validDirections.remove(Orientation.E);
		}
		if(getXGameCoord() == 0 || WorldSystem.get().getMemberFromCoords(getXGameCoord() - 1, getYGameCoord()) == null) {
			validDirections.remove(Orientation.W);
		}
		if(getYGameCoord() == WorldSystem.get().getSystemHeight()-1 || WorldSystem.get().getMemberFromCoords(getXGameCoord(), getYGameCoord() + 1) == null) {
			validDirections.remove(Orientation.S);
		}
		if(getYGameCoord() == 0 || WorldSystem.get().getMemberFromCoords(getXGameCoord(), getYGameCoord() - 1) == null) {
			validDirections.remove(Orientation.N);
		}

	}
	
	
}
