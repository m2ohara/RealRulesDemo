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
import com.realrules.game.behaviour.DeceiverBehaviour;
import com.realrules.game.behaviour.GossiperBehaviour;
import com.realrules.game.behaviour.IHeadBehaviour;
import com.realrules.game.behaviour.InfluencerBehaviour;
import com.realrules.game.interact.AutonomousInteraction;
import com.realrules.game.interact.DeceiverInteraction;
import com.realrules.game.interact.GossiperInteraction;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.interact.InfluencerInteraction;
import com.realrules.game.main.CoordinateSystem.Coordinates;
import com.realrules.game.main.DemoGame.Head;

public class HeadSprite  extends Image  {
	public IHeadBehaviour behaviour;
	
	public IHeadBehaviour getBehaviour() {
		return behaviour;
	}

	public AutonomousInteraction interaction;
	public float startingX;
	public float startingY;
	public boolean isActive = true;
	public int status = 0; //0 : neutral, 1 : for 2 : against
	private ArrayList<Coordinates> validDirections;
	
	
	private boolean isActing = false;
	private String framesPath = null;
	private Head type = null;
	
	public int getXGameCoord() {
		return CoordinateSystem.get().getGameXCoords().indexOf(this.startingX);
	}
	
	public int getYGameCoord() {
		return CoordinateSystem.get().getGameYCoords().indexOf(this.startingY);
	}

	public int getDirection() {
		return behaviour.getDirection();
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
	
	public HeadSprite(Head type, float x, float y, String framesPath, boolean isActive) {
		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));

		//Centre origin in frame for rotation;
		TextureRegion currentFrame  = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0);
		this.setOrigin(currentFrame.getRegionWidth()/2, currentFrame.getRegionHeight()/2);
		this.setPosition(x, y);
		
		this.startingX = x;
		this.startingY = y;
		this.framesPath = framesPath;
		this.type = type;
		
		setValidDirections();
	}
	
	public void setBehaviour(IManualInteraction manInteraction) {
		
		if(type == type.GOSSIPER) {
			behaviour = new GossiperBehaviour(isActive, framesPath, getXGameCoord(), getYGameCoord(), manInteraction);
			interaction = new GossiperInteraction();

		}
		if(type == type.DECEIVER) {
			behaviour = new DeceiverBehaviour(isActive, framesPath, getXGameCoord(), getYGameCoord(), manInteraction);
			interaction = new DeceiverInteraction();
		}
		if(type == type.INFLUENCER) {
			behaviour = new InfluencerBehaviour(isActive, framesPath, getXGameCoord(), getYGameCoord(), manInteraction);
			interaction = new InfluencerInteraction();
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
				if(ScoreState.validTouchAction()) {
					System.out.println("Touched at: x: "+x+", y: "+y+"");
					behaviour.onTouch();
					ScoreState.resetUserPoints();
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
	
	private void setValidDirections() {
		
		validDirections = new ArrayList<Coordinates> (Arrays.asList(Coordinates.N, Coordinates.E, Coordinates.S, Coordinates.W));
		
		if(getXGameCoord() == CoordinateSystem.getSystemWidth()-1) {
			validDirections.remove(Coordinates.E);
		}
		if(getXGameCoord() == 0) {
			validDirections.remove(Coordinates.W);
		}
		if(getYGameCoord() == CoordinateSystem.getSystemHeight()-1) {
			validDirections.remove(Coordinates.S);
		}
		if(getYGameCoord() == 0) {
			validDirections.remove(Coordinates.N);
		}
	}
	
	
}
