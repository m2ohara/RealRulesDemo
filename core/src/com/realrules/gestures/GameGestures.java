package com.realrules.gestures;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.realrules.game.demo.HeadSprite;
import com.realrules.game.demo.ManualInteraction;
import com.realrules.game.interact.IManualInteraction;

public class GameGestures  implements GestureListener {
		
		boolean isFirstHit = true;
		private Stage stage = null;
		ManualInteraction interaction = null;
		
		public GameGestures(Stage stage) {
			this.stage = stage;
		}
		
		public GameGestures(Stage stage, IManualInteraction followerInteractAction) {
			this.stage = stage;
			this.interaction = new ManualInteraction(followerInteractAction);
		}

		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean tap(float x, float y, int count, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean longPress(float x, float y) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			Vector2 coords = stage.screenToStageCoordinates(new Vector2(x, y));
			Actor actor = stage.hit(coords.x, coords.y, true);
			
			if(actor != null && actor.getClass().equals(HeadSprite.class) && ((HeadSprite)actor).isActing()) {
				//TODO: Refector interaction into HeadSprite
				interaction.interactHit((HeadSprite)actor, isFirstHit);
				isFirstHit = false;
			}
			
			return false;
		}

		@Override
		public boolean panStop(float x, float y, int pointer, int button) {
			isFirstHit = true;
			interaction.reset();
			return false;
		}

		@Override
		public boolean zoom(float initialDistance, float distance) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
				Vector2 pointer1, Vector2 pointer2) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
