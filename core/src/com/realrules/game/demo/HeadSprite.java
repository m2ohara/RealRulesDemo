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
	private float movementP = 0.1f;
	float rotateP = 0.8f;
	float argueSuccessP = 0.2f;
	float interactSuccessP = 0.2f;
	InteractSprite soundWave;
	public boolean isActive = true;
	public int status = 0; //0 : neutral, 1 : for 2 : against
	
	public int getXGameCoord() {
//		System.out.println("member x is "+this.startingX);
		return CoordinateSystem.get().getGameXCoords().indexOf(this.startingX);
	}
	
	public int getYGameCoord() {
//		System.out.println("member y is "+this.getY());
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
	
	public HeadSprite(Head type, float x, float y, String framesPath, boolean isActive) {
		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
		frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
		currentFrame = frames.get(0);
		direction = 0;
		//Centre origin in frame for rotation;
		this.setOrigin(this.currentFrame.getRegionWidth()/2, this.currentFrame.getRegionHeight()/2);
		
		startingX = x;
		startingY = y;
		
		if(type == type.GOSSIPER) {
			behaviour = new GossiperBehaviour(this,isActive);
			interaction = new GossiperInteractionD();
			this.setPosition(startingX, startingY);
			GameProperties.get().addActorToStage(this);

		}
		if(type == type.DECEIVER) {
			behaviour = new DeceiverBehaviour(isActive, framesPath, this.startingX, this.startingY);
			interaction = new DeceiverInteraction();
			this.setPosition(startingX, startingY);
			GameProperties.get().addActorToStage(this);
			//Refactor into beaviour
			behaviour.setInteractSprite();
		}
		
		setTouchAction();
		

		
		if(type == type.GOSSIPER) {
			//Set interact sprite
			soundWave = new InteractSprite(this.startingX, this.startingY, "sprites//soundWaveFollower.pack");
			soundWave.setTouchable(Touchable.disabled);
			GameProperties.get().addToSoundWaveGroup(soundWave);
		}
		
	}
	
	//Implement onTouch action
	private void setTouchAction() {
		
		this.addListener(new ClickListener() {
			
			public void clicked(InputEvent event, float x, float y) 
		    {
				System.out.println("Touched at: x: "+x+", y: "+y+"");
				behaviour.onTouch();
		    }
			
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		behaviour.onDraw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if(isActive)
			behaviour.onAct(delta, this);
	}
	
	
}
