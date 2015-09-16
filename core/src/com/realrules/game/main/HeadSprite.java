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
import com.realrules.game.behaviour.PromoterBehaviour;
import com.realrules.game.interact.AutonomousInteraction;
import com.realrules.game.interact.DeceiverInteraction;
import com.realrules.game.interact.GossiperInteraction;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.interact.PromoterInteraction;
import com.realrules.game.main.DemoGame.Head;
import com.realrules.game.main.WorldSystem.Orientation;

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
	private ArrayList<Orientation> validDirections;
	
	
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
	
	public Orientation getOrientation() {
		return behaviour.getOrientation();
	}
	
	public HeadSprite(Head type, float x, float y, String framesPath, boolean isActive) {
		super(new TextureAtlas(Gdx.files.internal(framesPath+defaultPack)).getRegions().get(0));

		//Centre origin in frame for rotation;
		TextureRegion currentFrame  = new TextureAtlas(Gdx.files.internal(framesPath+defaultPack)).getRegions().get(0);
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
			behaviour = new PromoterBehaviour(isActive, framesPath, getXGameCoord(), getYGameCoord(), manInteraction);
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
				if(ScoreState.validTouchAction()) {
					System.out.println("Pressed at: x: "+x+", y: "+y+"");
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
		
		validDirections = new ArrayList<Orientation> (Arrays.asList(Orientation.N, Orientation.E, Orientation.S, Orientation.W));
		
		if(getXGameCoord() == WorldSystem.getSystemWidth()-1) {
			validDirections.remove(Orientation.E);
		}
		if(getXGameCoord() == 0) {
			validDirections.remove(Orientation.W);
		}
		if(getYGameCoord() == WorldSystem.getSystemHeight()-1) {
			validDirections.remove(Orientation.S);
		}
		if(getYGameCoord() == 0) {
			validDirections.remove(Orientation.N);
		}
	}
	
	
}
