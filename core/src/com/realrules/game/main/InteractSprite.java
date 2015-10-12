package com.realrules.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.realrules.game.act.IOnActing;

public class InteractSprite extends Image {
	
//	private Array<AtlasRegion> frames;
//	private TextureRegion currentFrame;
//	int currentAngle;
//	private float xCoord;
//	private float yCoord;
//	private static String framesPath;

//	public InteractSprite(HeadSprite interactor, HeadSprite interactee, IOnActing animation) {
//		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
//		frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
//		currentFrame = frames.get(0);
		
		//Centre sprite to follower's centre
//		this.xCoord = followerX + (WorldSystem.headSpriteW/2) - (this.getWidth()/2);
//		this.yCoord = followerY + (WorldSystem.headSpriteH/2) - (this.getHeight()/2);
		
		//Centre origin in frame for rotation;
//		this.setOrigin(this.currentFrame.getRegionWidth()/2, this.currentFrame.getRegionHeight()/2);			
//
//		
////		setToStage(this, this.xCoord, this.yCoord);
//		this.setPosition(this.xCoord, this.yCoord);
		//TODO uncomment on implementation
//		stage.addActor(this);
		
//		this.setDrawable(new TextureRegionDrawable(new TextureRegion(this.currentFrame)));
//		
//		//TO DO: Temp solution remove
//		this.scaleBy(-0.5f);
		
//	}
	
	private IOnActing animateInteraction;
	private static String framesPath = "sprites//Meep//Effects//Effects.pack";
	public boolean isInteracting = false;
	private float interactionScaleFactor;
	private float currentScaleFactor;
	private float interactionStateLength;
	private ScaleToAction scaleAction;
	private HeadSprite interactor;
	private int origStatus;
	
	public InteractSprite(float interactionStateLength, int interactionStages, HeadSprite interactor) {
		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
		
//		animateInteraction = new OnAnimateInteractionAct(interactionStateLength, interactionStages, this);
		this.interactionStateLength = interactionStateLength;
		this.interactionScaleFactor = 1f/(float)(interactionStages);
		this.currentScaleFactor = interactionScaleFactor;
		setSprite(interactor.getStartingX()+90, interactor.getStartingY()+90);
		
		//Set interactor to interacting
		origStatus = interactor.status;
		interactor.status = 4;
		this.interactor = interactor;
		
		this.setDrawable(new TextureRegionDrawable(new TextureRegion(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0))));
		setInteractionAction(interactionStateLength);
	}
	
	private void setSprite(float xCoord, float yCoord) {

		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
		this.setPosition(xCoord, yCoord);
		this.setTouchable(Touchable.disabled);
		float scaleAmount = 1 - interactionScaleFactor;
		this.scaleBy(-scaleAmount);

		GameProperties.get().addActorToStage(this);
	}
	
	private void setInteractionAction(float interactionStateLength) {
		scaleAction = new ScaleToAction(1, 1, interactionStateLength){
			
			public boolean act( float delta ) {
	        
	        return true;
	    }
	};
		this.addAction(scaleAction);
		this.isInteracting = true;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	
		if(scaleAction.getTime() == 10f) {
//			scaleAction.restart();
//			currentScaleFactor += interactionScaleFactor;
//		}
//		else 
//			if(isFinished == true && currentScaleFactor == 1){
			scaleAction.finish();
			interactor.status = origStatus;
			this.remove();
		}
		
		//animateInteraction.performActing(delta);
	}
	

	
//	public void setIsDrawable(boolean isDrawable) {
//		this.setVisible(isDrawable);
//	}
	
//	public void setMoveAction() {
//		
//		this.setVisible(true);
//		
//		//Get acting coords
//		float x = 0; 
//		float y = 0; 
//		float duration = 1.5f;
//		
//		//Move sprite to destination
//		switch(currentAngle) {
//			case 0 :  
//				x += this.currentFrame.getRegionWidth()/2;
//				break;
//			
//			case 45: 
//				x += this.currentFrame.getRegionWidth()/2;
//				y += this.currentFrame.getRegionHeight()/2;
//				break;
//				
//			case 90:
//				y += this.currentFrame.getRegionHeight()/2;
//				break;
//				
//			case 135:
//				x -= this.currentFrame.getRegionWidth()/2;
//				y += this.currentFrame.getRegionHeight()/2;
//				break;
//				
//			case 180:
//				x -= this.currentFrame.getRegionWidth()/2;
//				break;
//				
//			case 225:
//				x -= this.currentFrame.getRegionWidth()/2;
//				y -= this.currentFrame.getRegionHeight()/2;
//				break;
//				
//			case 270:
//				y -= this.currentFrame.getRegionHeight()/2;
//				break;
//				
//			case 315:
//				x += this.currentFrame.getRegionWidth()/2;
//				y -= this.currentFrame.getRegionHeight()/2;
//				break;
//				
//			case 360:
//				x += this.currentFrame.getRegionWidth()/2;
//				y -= this.currentFrame.getRegionHeight()/2;
//				break;					
//			
//		}
//		this.addAction(Actions.moveBy(x, y, duration));
//	}
	
}

