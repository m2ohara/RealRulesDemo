package com.realrules.game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.demo.DemoGame.Head;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.interact.InfluencerInteraction;

public class HeadSprite  extends Image  {
	IHeadBehaviour behaviour;
	
	public IHeadBehaviour getBehaviour() {
		return behaviour;
	}

	public AutonomousInteraction interaction;
	float startingX;
	float startingY;
	Array<AtlasRegion> frames;
	TextureRegion currentFrame;
	int direction; //0 : left, 1 : right
	float rotateP = 0.8f;
	public float argueSuccessP = 0.2f;
	float interactSuccessP = 0.2f;
	InteractSprite soundWave;
	public boolean isActive = true;
	public int status = 0; //0 : neutral, 1 : for 2 : against
	
	
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
		frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
		currentFrame = frames.get(0);
		direction = 0;
		//Centre origin in frame for rotation;
		this.setOrigin(this.currentFrame.getRegionWidth()/2, this.currentFrame.getRegionHeight()/2);
		this.setPosition(x, y);
		
		startingX = x;
		startingY = y;
		
		this.framesPath = framesPath;
		this.type = type;
		
	}
	
	public void setBehaviour(IManualInteraction manInteraction) {
		
		if(type == type.GOSSIPER) {
			behaviour = new GossiperBehaviour(this,isActive, manInteraction);
			interaction = new GossiperInteraction();

		}
		if(type == type.DECEIVER) {
			behaviour = new DeceiverBehaviour(isActive, framesPath, getXGameCoord(), getYGameCoord(), manInteraction);
			interaction = new DeceiverInteraction();
			//Refactor into behaviour
			behaviour.setInteractSprite(startingX, startingY);
		}
		if(type == type.INFLUENCER) {
			behaviour = new InfluencerBehaviour(isActive, framesPath, getXGameCoord(), getYGameCoord(), manInteraction);
			interaction = new InfluencerInteraction();
		}
		
		//Refactor into Behaviour
		setTouchAction();
		
//		GameProperties.get().addActorToStage(this);
		

		
		if(type == type.GOSSIPER) {
			//Set interact sprite
			soundWave = new InteractSprite(this.startingX, this.startingY, "sprites//soundWaveFollower.pack");
			soundWave.setTouchable(Touchable.disabled);
//			GameProperties.get().addToSoundWaveGroup(soundWave);
		}
		
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
		
		if(isActing) {
			behaviour.onDraw(batch, parentAlpha);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if(isActive && isActing){
			behaviour.onAct(delta, this);
		}
	}
	
	
}
