package com.realrules.game.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

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
import com.realrules.game.touch.SpriteOrientation;
import com.realrules.game.touch.PromoterTouchAction;

public class GameSprite  extends Image {
	
	public Behaviour behaviour;

	public AutonomousInteraction interaction;
	public float startingX;
	public float startingY;
	public boolean isActive = true;
	public int status = 0; //0 : neutral, 1 : for 2 : against
	public boolean isIntermediateInteractor = false;
	
	private ArrayList<Orientation> validDirections;
	private SpriteOrientation changeOrientation;
	
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
	
	public void setValidOrientations() {
		changeOrientation = new SpriteOrientation(getXGameCoord(), getYGameCoord());
	}
	
	public void setBehaviour(IInteractionType manInteraction) {
		
		//Orientation logic
//		if(validDirections == null) {
//			setValidDirections();
//		}
		
//		orientationOnTouch = new OrientationOnTouch(validDirections, (Observer)this);
//		orientationOnTouch.onTouch();
		
		if(type == type.GOSSIPER) {
			IBehaviourProperties properties = new GossiperProperties();
			//Review
			OnAnimateTalkingAct actType = new OnAnimateTalkingAct(properties.getRotateProbability(), properties.getInteractProbability(), this, changeOrientation);
//			orientationOnTouch.addObserver(actType);
			behaviour = new Behaviour(
					isActive, 
					actType,
					new GossiperTouchAction(manInteraction, getXGameCoord(), getYGameCoord()), 
					properties,
					changeOrientation);
			interaction = new GossiperInteraction();

		}
		if(type == type.DECEIVER) {
			IBehaviourProperties properties = new DeceiverProperties();
			//Review
			OnAnimateTalkingAct actType = new OnAnimateTalkingAct(properties.getRotateProbability(), properties.getInteractProbability(), this, changeOrientation);
//			orientationOnTouch.addObserver(actType);
			behaviour = new Behaviour(
					isActive, 
					actType,
					new DeceiverTouchAction(manInteraction, getXGameCoord(), getYGameCoord()),
					properties,
					changeOrientation);
			interaction = new DeceiverInteraction();
		}
		if(type == type.INFLUENCER) {
			IBehaviourProperties properties = new PromoterProperties();
			//Review
			OnAnimateTalkingAct actType = new OnAnimateTalkingAct(properties.getRotateProbability(), properties.getInteractProbability(), this, changeOrientation);
//			orientationOnTouch.addObserver(actType);
			behaviour = new Behaviour(
					isActive, 
					actType,
					new PromoterTouchAction(manInteraction, getXGameCoord(), getYGameCoord()),
					properties,
					changeOrientation);
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

	public Orientation getOrientation() {
		return behaviour.getOrientation();
	}
	
	public void setOrientation() {
		behaviour.changeOrientationOnInvalid();
	}
	
	
}
