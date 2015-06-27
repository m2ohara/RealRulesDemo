package com.realrules.game.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Array;

public class MoveableSprite extends Image {
	
	private Array<AtlasRegion> frames;
	private TextureRegion currentFrame;
	private final Actor dragSprite;
	private final MoveableSprite instance;
	
	public MoveableSprite(String framesPath, float x, float y) {
		super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
		
		setSprite(framesPath, x, y);
		
		instance = this;
		dragSprite = new Image(currentFrame);
		
		setAsMoveable();
		
		GameProperties.get().getStage().addActor(this);
	}
	
	private void setSprite(String framesPath, float x, float y) {
		frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
		currentFrame = frames.get(0);
		this.setOrigin(this.currentFrame.getRegionWidth()/2, this.currentFrame.getRegionHeight()/2);
		this.setPosition(x, y);
	}
	
	private DragAndDrop dragAndDrop;
	
	
	private void setAsMoveable() {
		
		dragAndDrop = new DragAndDrop();
		dragAndDrop.setDragActorPosition(-dragSprite.getWidth()/2, dragSprite.getHeight()/2+(dragSprite.getHeight()/8));		
		
		setDragSource();
		
		Group group = GameProperties.get().getActorGroup();
		for(Actor actor : group.getChildren()) {
			
			addDropTarget(actor);
		}
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
				
				//Set dragActor onto coordinates
				Actor dragActor = payload.getDragActor();
				dragActor.setPosition(getActor().getX(), getActor().getY());
				GameProperties.get().addActorToStage(dragActor);
				
				instance.setPosition(getActor().getX(), getActor().getY());
				
				//TODO: Resolve new source creation
				//Set dragActor new source coordinates
				setAsMoveable();
			}
		});
	}
	

}
