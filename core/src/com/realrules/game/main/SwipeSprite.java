package com.realrules.game.main;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.realrules.game.interact.IInteractionType;
import com.realrules.game.interact.SwipeInteraction;
import com.realrules.game.main.GameSprite.Status;

public class SwipeSprite {
	
	private DragAndDrop dragAndDrop;
	private Actor sourceSprite;
	private Actor dragSprite;
	private Actor lastValidActor;
	private TextureRegion sourceTexture = new TextureAtlas(Gdx.files.internal("sprites//Meep//Effects//Effects.pack")).getRegions().get(0);
	private ArrayList<Target> targets = new ArrayList<Target>();
//	private boolean continueDrag = true;
//	private boolean doOnComplete = true;
//	private Target lastValidTarget = null;
	
	private static SwipeInteraction interaction = null;
	private GameSprite startSprite = null;
	
	public SwipeSprite(IInteractionType interactionType, int influenceType) {
		
		interaction = new SwipeInteraction(interactionType, influenceType);
		GameProperties.get().setSwipeInteraction(interaction);
	}
	
	public void setStartSprite(GameSprite startSprite) {
		this.startSprite = startSprite;
	}
	
	public void activate() {
		setSourceSprite(startSprite.getX(), startSprite.getY());
		setDragAndDrop();
	}
	
	private void setSourceSprite(float x, float y) {
		sourceSprite = new Image(sourceTexture);
		sourceSprite.setOrigin(sourceSprite.getWidth()/2, sourceSprite.getWidth()/2);
		sourceSprite.setPosition(x+sourceSprite.getWidth()/2, y+sourceSprite.getWidth()/2);
		sourceSprite.setScale(WorldSystem.get().getLevelScaleFactor());
		
//		System.out.println("Setting source sprite coords "+sourceSprite.getX()+", "+sourceSprite.getY());
		
		GameProperties.get().getStage().addActor(sourceSprite);
	}
	
	private void setDragAndDrop() {	
		
		dragSprite = new Image(sourceTexture);	
		dragAndDrop = new DragAndDrop();
		dragAndDrop.setDragActorPosition(0, dragSprite.getHeight()/2);
		
		setDragSource();
		
		Group group = GameProperties.get().getActorGroup();
		for(Actor actor : group.getChildren()) {
			addDropTarget(actor);
		}
	}
	
	private void setDragSource() {
		dragAndDrop.addSource(new Source(sourceSprite) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject(dragSprite);
				payload.setDragActor(dragSprite);
				interaction.interactHit(startSprite, true);
				sourceSprite.setVisible(false);
//				System.out.println("Starting drag");

				return payload;
			}
		});
	}
	
	private void addDropTarget(final Actor target) {
		
		Target targetToAdd = new Target(target) {
			boolean isHit = false;
			boolean isValid = true;
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				
//				if(!continueDrag) {
//					return true;
//				}

				//On hit
				if(!isHit) {
					isHit = true;
					if(interaction.interactHit((GameSprite)target, false)) {
						System.out.println("Valid interaction at " + x + ", " + y);
						isValid = true;
						lastValidActor = target;
					}
					else if(((GameSprite)target).interactStatus == Status.NEUTRAL && lastValidActor != null) {
						System.out.println("Invalid interaction at " + x + ", " + y);
						sourceSprite.setVisible(true);
						isValid = false;
//						continueDrag = false;
					}
				}
				
				return isValid;
			}

			public void reset (Source source, Payload payload) {
				if(!isValid) {
//					TODO: Stop dragging and set to current target
//					source.dragStop(null, this.getActor().getX(), this.getActor().getY(), this.getActor()., payload, this);
					removeInvalidTargets(this);
					onComplete(lastValidActor);
				}
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
				
				System.out.println("Dropped at " + x + ", " + y);
				
				//Set dragActor new source coordinates
				onComplete(lastValidActor);
			}
		};
		
		targets.add(targetToAdd);
		dragAndDrop.addTarget(targetToAdd);
	}
	
	private void removeInvalidTargets(Target validTarget) {
		for(Target targetToRemove : targets) {
			if(!targetToRemove.equals(validTarget)) {
				dragAndDrop.removeTarget(targetToRemove);
			}
		}
	}
	
	private void onComplete(Actor lastActor) {
		
//		if(doOnComplete) {
			System.out.println("Resetting swipe sprite");
//			doOnComplete = false;
			
			dragAndDrop.clear();
			sourceSprite.remove();
			dragSprite.remove();
			lastValidActor = null;
			
			startSprite = (GameSprite)lastActor;
			interaction.reset();
			
			activate();
//		}
	
	}

}
