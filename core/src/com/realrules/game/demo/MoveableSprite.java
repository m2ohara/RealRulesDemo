package com.realrules.game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Array;
import com.realrules.game.demo.DemoGame.Head;

public class MoveableSprite 
//extends Image 
{
	
	private Head type = null;
	private String framesPath = null;
	private Array<AtlasRegion> frames;
	private TextureRegion currentFrame;
	private Actor dragSprite;
	private Actor sourceSprite;
	private float origX;
	private float origY;
	private boolean isActive = false;
	private Image targetImage;
	
	
	public MoveableSprite(Head type, String framesPath, float x, float y, Image sourceTargetImage) {
		
		this.type = type;
		this.origX = x;
		this.origY = y;
		this.framesPath = framesPath;
		this.frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
		currentFrame = frames.get(0);
		
		this.targetImage = sourceTargetImage;
		
		setSourceSprite(x, y);
		
		setDragAndDrop(false);
	}
	
	private void setSourceSprite(float x, float y) {
		sourceSprite = new Image(currentFrame);
		sourceSprite.setPosition(x, y);
		sourceSprite.setColor(Color.CYAN);
		
		GameProperties.get().getStage().addActor(sourceSprite);
	}
	
	private DragAndDrop dragAndDrop;
	private void setDragAndDrop(boolean addOrigSource) {	
		
		dragSprite = new Image(currentFrame);	
		dragAndDrop = new DragAndDrop();
		dragAndDrop.setDragActorPosition(-dragSprite.getWidth()/2, dragSprite.getHeight()/2+(dragSprite.getHeight()/8));	
		
		setDragSource();
		
		Group group = GameProperties.get().getActorGroup();
		for(Actor actor : group.getChildren()) {
			
			addDropTarget(actor);
		}
		
		if(addOrigSource) {
			this.isActive = true;
			addOriginalDropTarget();
		}
	}
	
	public void resetLocation(float x, float y, boolean isActive) {
		
		dragAndDrop.clear();
		sourceSprite.remove();
		dragSprite.remove();
		
		setSourceSprite(x, y);
		
		setDragAndDrop(isActive);
		
		this.isActive = isActive;
	}
	
	public void setDragSource() {
		dragAndDrop.addSource(new Source(sourceSprite) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject(dragSprite);

				payload.setDragActor(dragSprite);

				return payload;
			}
		});
	}
	
	private void addDropTarget(Actor target) {
		
		
		dragAndDrop.addTarget(new Target(target) {			
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {			
				getActor().setColor(Color.RED);
				return true;
			}

			public void reset (Source source, Payload payload) {
				if(((HeadSprite)getActor()).status == 1)
					getActor().setColor(Color.YELLOW);
				else
					getActor().setColor(Color.WHITE);
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
				
				System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);

				//Set dragActor new source coordinates
				resetLocation(getActor().getX(), getActor().getY(), true);
			}
		});
	}
	
	private void addOriginalDropTarget() {

//		setPlaceHolder();
		
		dragAndDrop.addTarget(new Target(targetImage) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				return true;
			}

			public void reset (Source source, Payload payload) {
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
				
				System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
				
//				getActor().remove();
				resetLocation(origX, origY, false);
			}
		});
	}
	
//	private void setPlaceHolder() {
//		targetImage.setColor(Color.LIGHT_GRAY);
//		targetImage.setPosition(origX, origY);
//		GameProperties.get().getStage().addActor(targetImage);
//		targetImage.setTouchable(Touchable.disabled);
//	}
//	
//	private void setDropTarget() {
//		targetImage = (Image) GameProperties.get().getStage().hit(origX, origY, true);
//	}
	
	public void setAsActive() {
		this.isActive = true;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public Actor getSourceSprite() {
		return this.sourceSprite;
	}
	
	public Head getType() {
		return this.type;
	}
	
	public float getCurrentX() {
		return sourceSprite.getX();
	}
	
	public float getCurrentY() {
		return sourceSprite.getY();
	}
	
	public String getFramesPath() {
		return this.framesPath;
	}
	
	public Actor getTargetImage() {
		return this.targetImage;
	}
	

}
