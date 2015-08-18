package com.realrules.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class InteractSprite extends Image {
	
	private Array<AtlasRegion> frames;
	private TextureRegion currentFrame;
	int currentAngle;
	private float xCoord;
	private float yCoord;

	public InteractSprite(float followerX, float followerY, String framesPath) {
		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
		frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
		currentFrame = frames.get(0);
		
		//Centre sprite to follower's centre
		this.xCoord = followerX + (CoordinateSystem.headSpriteW/2) - (this.getWidth()/2);
		this.yCoord = followerY + (CoordinateSystem.headSpriteH/2) - (this.getHeight()/2);
		
		//Centre origin in frame for rotation;
		this.setOrigin(this.currentFrame.getRegionWidth()/2, this.currentFrame.getRegionHeight()/2);			

		
//		setToStage(this, this.xCoord, this.yCoord);
		this.setPosition(this.xCoord, this.yCoord);
		//TODO uncomment on implementation
//		stage.addActor(this);
		
		this.setDrawable(new TextureRegionDrawable(new TextureRegion(this.currentFrame)));
		
		//TO DO: Temp solution remove
		this.scaleBy(-0.5f);
		
	}
	
	public void setFrame(float rotation) {
		this.setRotation(rotation);
	}
	
	public void setIsDrawable(boolean isDrawable) {
		this.setVisible(isDrawable);
	}
	
	public void setMoveAction() {
		
		this.setVisible(true);
		
		//Get acting coords
		float x = 0; 
		float y = 0; 
		float duration = 1.5f;
		
		//Move sprite to destination
		switch(currentAngle) {
			case 0 :  
				x += this.currentFrame.getRegionWidth()/2;
				break;
			
			case 45: 
				x += this.currentFrame.getRegionWidth()/2;
				y += this.currentFrame.getRegionHeight()/2;
				break;
				
			case 90:
				y += this.currentFrame.getRegionHeight()/2;
				break;
				
			case 135:
				x -= this.currentFrame.getRegionWidth()/2;
				y += this.currentFrame.getRegionHeight()/2;
				break;
				
			case 180:
				x -= this.currentFrame.getRegionWidth()/2;
				break;
				
			case 225:
				x -= this.currentFrame.getRegionWidth()/2;
				y -= this.currentFrame.getRegionHeight()/2;
				break;
				
			case 270:
				y -= this.currentFrame.getRegionHeight()/2;
				break;
				
			case 315:
				x += this.currentFrame.getRegionWidth()/2;
				y -= this.currentFrame.getRegionHeight()/2;
				break;
				
			case 360:
				x += this.currentFrame.getRegionWidth()/2;
				y -= this.currentFrame.getRegionHeight()/2;
				break;					
			
		}
		this.addAction(Actions.moveBy(x, y, duration));
	}
	
}

