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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Array;

public class MoveableSprite extends Image {
	
	private String framesPath = null;
	private Array<AtlasRegion> frames;
	private TextureRegion currentFrame;
	private final Actor dragSprite;
	private MoveableSprite instance;
	private float origX;
	private float origY;
	private boolean isActive = false;
	
	
	public MoveableSprite(String framesPath, float x, float y) {
		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
		
		this.setColor(Color.CYAN);
		
		this.origX = x;
		this.origY = y;
		this.framesPath = framesPath;
		setSprite(framesPath, x, y);
		
		instance = this;
		dragSprite = new Image(currentFrame);
		
		dragAndDrop = new DragAndDrop();
		dragAndDrop.setDragActorPosition(-dragSprite.getWidth()/2, dragSprite.getHeight()/2+(dragSprite.getHeight()/8));	
		
		setDragAndDrop();
		
		GameProperties.get().getStage().addActor(this);
	}
	
	private MoveableSprite(String framesPath, float x, float y, float origX, float origY) {
		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
		
		this.setColor(Color.CYAN);
		
		this.framesPath = framesPath;
		setSprite(framesPath, x, y);
		
		instance = this;
		dragSprite = new Image(currentFrame);
		
		dragAndDrop = new DragAndDrop();
		dragAndDrop.setDragActorPosition(-dragSprite.getWidth()/2, dragSprite.getHeight()/2+(dragSprite.getHeight()/8));	
		
		setDragAndDrop();
		addOriginalDropTarget(); 
		
		GameProperties.get().getStage().addActor(this);
	}
	
	private void setSprite(String framesPath, float x, float y) {
		frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
		currentFrame = frames.get(0);
		this.setOrigin(this.currentFrame.getRegionWidth()/2, this.currentFrame.getRegionHeight()/2);
		this.setPosition(x, y);
	}
	
	private DragAndDrop dragAndDrop;
	
	
	private void setDragAndDrop() {	
		
		setDragSource();
		
		Group group = GameProperties.get().getActorGroup();
		for(Actor actor : group.getChildren()) {
			
			addDropTarget(actor);
		}
	}
	
	public void resetLocation(float x, float y) {
		dragAndDrop.clear();
		this.clear();
		this.remove();
		dragSprite.remove();
		instance = new MoveableSprite(framesPath, x, y, origX, origY);
		this.isActive = true;
	}
	
	public void setDragSource() {
		dragAndDrop.addSource(new Source(instance) {
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
				getActor().setColor(Color.WHITE);
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
				
				System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);

				//Set dragActor new source coordinates
				resetLocation(getActor().getX(), getActor().getY());
			}
		});
	}
	
	private void addOriginalDropTarget() {
		Image targetImage = new Image();
		targetImage.setPosition(origX, origY);
		GameProperties.get().getStage().addActor(targetImage);
		
		dragAndDrop.addTarget(new Target(targetImage) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				return true;
			}

			public void reset (Source source, Payload payload) {
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
				
				System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
				
				//TODO: Resolve target implementation
				getActor().remove();
				resetLocation(getActor().getX(), getActor().getY());
			}
		});
	}
	

}
