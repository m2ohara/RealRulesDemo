package com.realrules.game.demo;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class DemoGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	public Stage stage;
	OrthographicCamera camera;
	private boolean isAndroid = false;
	
	
	public boolean isAndroid() {
		return isAndroid;
	}

	public void setAndroid(boolean isAndroid) {
		this.isAndroid = isAndroid;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		setView();
		Gdx.input.setInputProcessor(stage);
		setTitleScreen();
	}
	
	private void setView() {
		if(!isAndroid) {
			stage = new Stage();
		}
		else {
			setViewport();
		}
	}
	
	private void setViewport() {
		OrthographicCamera camera = new OrthographicCamera(1080, 1520);
		StretchViewport viewport = new StretchViewport(1080, 1920);
		camera.zoom = 0.38f;
		stage = new Stage(viewport);
		stage.getViewport().setCamera(camera);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
		batch.end();
	}
	
	@Override
	public void resize(int width, int heigth) {
	    stage.getViewport().update(width, heigth, true);
	}
	
	
	private Actor getButton(String type) {
		TextureAtlas buttonAtlas;
		Skin skin = new Skin();
		BitmapFont font = new BitmapFont();
		TextButtonStyle style = new TextButtonStyle();
		
		buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons//buttonsPack.pack"));
		skin.addRegions(buttonAtlas);
		style.font = font;
		style.up = skin.getDrawable(type);
		style.down = skin.getDrawable(type);
		
		return new TextButton("", style);
	}
	
	private Actor getImage(String type, String pack) {

		TextureAtlas txAtlas;
		Skin txSkin;
		
		txAtlas = new TextureAtlas(Gdx.files.internal(pack+".pack"));
		txSkin = new Skin(txAtlas);
		
		return new Image(txSkin.getDrawable(type));
	}
	
	public void setToStage(Actor actor, float _xCentreOffset, float _yCentreOffset) {
		
		setRelativePosition(actor, _xCentreOffset, _yCentreOffset);

		stage.addActor(actor);
	}
	
	protected void setRelativePosition(Actor actorToSet, float _xCentreOffset, float _yCentreOffset) {
		
		//Centre actor
		float x = (Gdx.graphics.getWidth() - actorToSet.getWidth()) /2 ;
		float y = (Gdx.graphics.getHeight()  - actorToSet.getHeight()) / 2;
		
		x += _xCentreOffset;
		y += _yCentreOffset;
		
		actorToSet.setPosition(x, y);
	}
	
	private void setTitleScreen() {
		
		setToStage(getImage("TitleScreen", "screens//screensPack"), 0, 0);
		Actor btn = getButton("PlayGameBtn");
		setToStage(btn, 0, -260);
		
		btn.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				 setSpeechScreen();
			 }
		});
		
	}
	
	private void setTutorialScreen() {
		
	}
	
	private void setSpeechScreen() {
		
		setToStage(getImage("SpeechScreen", "screens//screensPack"), 0, 0);
		Actor btn = getButton("CreateSpeechBtn");
		setToStage(btn, 0, -260);
		
		btn.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				 displaySpeechScroll();
			 }
		});
	}
	
	private void displaySpeechScroll() {
		Actor icon = getImage("Scroll", "icons//iconsPack");
		setToStage(icon, 0, -60);
		
		Actor btn = getButton("PlayGameBtn");
		setToStage(btn, 0, -260);
		
		btn.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				 setCrowdScreen();
			 }
		});
	}
	

	
	private void setCrowdScreen() {
		
		setToStage(getImage("CrowdScreen", "screens//screensPack"), 0, 0);
		
		new HeadSprite(Head.GOSSIPER, 10, 115, "sprites//gossiperFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, -70, 115, "sprites//deceiverFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, 90, 115, "sprites//deceiverFollowerPack.pack", true);
		
		new HeadSprite(Head.GOSSIPER, 10, 45, "sprites//gossiperFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, -70, 45, "sprites//deceiverFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, 90, 45, "sprites//deceiverFollowerPack.pack", true);
		
		new HeadSprite(Head.GOSSIPER, 10, -25, "sprites//gossiperFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, -70, -25, "sprites//promoterFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, 90, -25, "sprites//promoterFollowerPack.pack", true);
		
		new HeadSprite(Head.GOSSIPER, 10, -95, "sprites//gossiperFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, -70, -95, "sprites//deceiverFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, 90, -95, "sprites//deceiverFollowerPack.pack", true);
	
		
		
	}
	
	public class HeadSprite extends Image {
		
		private IHeadBehaviour behaviour;
		private float startingX;
		private float startingY;
		private Array<AtlasRegion> frames;
		private TextureRegion currentFrame;
		private float movementP = 0.1f;
		private float rotateP = 0.3f;
		private float argueSuccessP = 0.4f;
		private float interactP = 0.7f;
		private InteractSprite soundWave;
		
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
			//Centre origin in frame for rotation;
			this.setOrigin(this.currentFrame.getRegionWidth()/2, this.currentFrame.getRegionHeight()/2);
			
			startingX = x;
			startingY = y;
			
			if(type == type.GOSSIPER) {
				behaviour = new GossiperBehaviour(this,isActive);
			}
			
			setTouchAction();
			
			setToStage(this, this.startingX, this.startingY);
			
			//Set interact sprite
			soundWave = new InteractSprite(this.startingX, this.startingY, "sprites//soundWaveFollower.pack"); 
			
		}
		
		//Implement onTouch action
		private void setTouchAction() {
			
			this.addListener(new ClickListener() {
				
				public void clicked(InputEvent event, float x, float y) 
			    {
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
			
			behaviour.onAct(delta);
		}
		
		
	}
	
	public enum Head { GOSSIPER, DECEIVER, INFLUENCER}

	
	public class GossiperBehaviour implements IHeadBehaviour {
		
		//Behaviour: 
		//Movement: Random interacting in all directions
		//On touch: Send soundwave in currently set direction, with a large spread
		//Draw: Default 
		
		HeadSprite gossiper;
		Random rand = new Random();
		float stateLength = 1.0f;
		float stateTime = stateLength;
		float InStateLength = 0.7f;
		float InStateTime = InStateLength;
		float TouchStateLength = 3.0f;
		float TouchStateTime = 0;
		private boolean isActive = false;
		
		
		public GossiperBehaviour(HeadSprite gossiper, boolean isActive) {
			this.gossiper = gossiper;		
			this.isActive = isActive;
		}

		@Override
		public void onAct(float delta) {
			
			if(isActive) {
				if(stateTime >= stateLength) {
					stateTime = 0.0f;
					setFrame();
				}
				stateTime += delta;
				

				if( InStateTime >= InStateLength) {
					InStateTime = 0.0f;
					setInteraction();
				}
				InStateTime += delta;
			}
//			else if(!isActive) {
//				if(TouchStateTime >= TouchStateLength) {
//					gossiper.soundWave.remove();
//					gossiper.setColor(Color.GRAY);
//				}
//				boolean newVisible = gossiper.soundWave.isVisible() ? false : true;
//				gossiper.soundWave.setVisible(newVisible);
//				
//				TouchStateTime += delta;
//			}
		}

		@Override
		public void onDraw(Batch batch, float parentAlpha) {
		}

		@Override
		public void onTouch() {
			if(isActive) {
//				gossiper.soundWave.setMoveAction();
				isActive = false;
			}
			
			
		}
		
		public void onDrag() {
			
			//Perform interaction
			
		}
		
		private void performInteraction() {
			
			//If actor is at correct angle
			
			//Perform interaction with neighbouring actor
			
			//Move channel 
			
		}
		
		//Implement movement action
		private void setMoveToAction() {
			
    		Random rand = new Random();
    		int xCoord = rand.nextInt(((int)gossiper.getStartingX()) - 20) + 20;
    		int yCoord = rand.nextInt(140) + 180;   

    		gossiper.addAction(Actions.moveTo(xCoord, yCoord, 0.5f));
			
		}
		
		//Implement frame setting
		private void setFrame() {
			
			//Based on rotation probability
			if(rand.nextFloat() > gossiper.rotateP) {
				//Set direction
				int direction = rand.nextInt(2);
				gossiper.currentFrame = gossiper.frames.get(direction);
				//Set angle
				int angleSpread = 90;
				int angleSector = rand.nextInt(2);
				int angleMultiple = angleSpread * rand.nextInt((180/angleSpread)-1);
				int startingAngle = angleSector == 0 ? 0 : 270;
				int finalAngle = startingAngle + angleMultiple;
				gossiper.setRotation((float) (finalAngle));
				
				gossiper.setDrawable(new TextureRegionDrawable(new TextureRegion(gossiper.currentFrame)));
				
				//Rotate soundwave
				int soundWaveAngle = direction == 1 ? finalAngle : (finalAngle + 180) % 360;
				gossiper.soundWave.setRotation(soundWaveAngle);
				gossiper.soundWave.currentAngle = (int)gossiper.soundWave.getRotation();
				
			}
			
		}
		
		private void setInteraction() {
			
			if(rand.nextFloat() > gossiper.interactP) {
				gossiper.soundWave.setVisible(true);
			}
			else {
				gossiper.soundWave.setVisible(false);
			}
		}
		
		
	}
	
	public class DeceiverBehaviour implements IHeadBehaviour {
		
		public DeceiverBehaviour() {
			
		}

		@Override
		public void onAct(float delta) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDraw(Batch batch, float parentAlpha) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTouch() {
			// TODO Auto-generated method stub
			
		}
		
		//Implement movement action
		
		//Implement frame setting
		
		//Implement collision detection
		
	}
	
	public class InfluencerBehaviour implements IHeadBehaviour {
		
		public InfluencerBehaviour() {
			
		}

		@Override
		public void onAct(float delta) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDraw(Batch batch, float parentAlpha) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTouch() {
			// TODO Auto-generated method stub
			
		}
		
		//Implement movement action
		
		//Implement frame setting
		
		//Implement collision detection
		
	}
	
	public interface IHeadBehaviour {
		
		//Act behaviour
		void onAct(float delta);
		
		//Draw behaviour
		void onDraw(Batch batch, float parentAlpha);
		
		//Touch behaviour
		void onTouch();
		
//		void onSwipe();
		
	}
	
	public class InteractSprite extends Image {
		
		private Array<AtlasRegion> frames;
		private TextureRegion currentFrame;
		private int currentAngle;
		private float xCoord;
		private float yCoord;

		public InteractSprite(float x, float y, String framesPath) {
			super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
			frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
			currentFrame = frames.get(0);
			this.xCoord = x;
			this.yCoord = y;
			
			//Centre origin in frame for rotation;
			this.setOrigin(this.currentFrame.getRegionWidth()/2, this.currentFrame.getRegionHeight()/2);			
			
			setToStage(this, this.xCoord, this.yCoord);
			
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
	
	public enum Interact { SOUNDWAVE }
	
	public class DragSprite extends Image {
		
		private boolean isActive;
		
		public boolean isActive() {
			return isActive;
		}

		public DragSprite() {
			
		}
		
		private void setTouchAction() {
			
			this.addListener(new ClickListener() {
				
				public void clicked(InputEvent event, float x, float y) 
			    {
					
			    }
				
			});
		}
		
		private void setDragAction() {
			
			this.addListener(new DragInteraction());
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);
			
		}

		@Override
		public void act(float delta) {
			super.act(delta);
		}
		
		
	}
	
	public class DragInteraction extends DragListener {
		
		//Starting coords
		float x; float y;
		
		private boolean isDragging;
		public boolean isDragging() {
			return isDragging;
		}
		
		public DragInteraction() {
		}
		
		public void dragStart (InputEvent event, float x, float y, int pointer) {
			
			//Set isDrag to true
			isDragging = true;
		}

		public void drag (InputEvent event, float x, float y, int pointer) {
			
			
			
			
		}

		public void dragStop (InputEvent event, float x, float y, int pointer) {
			
			//Set isDrag to false
			isDragging = false;
			
		}
		
	}
	
	public class Interaction {
		
		//Interacting
		HeadSprite interactor;
		HeadSprite interactee;
		
		public void Interact() {
			
			//If interaction probability is achieved
			
			//Update status of receiver
			
		}
		
	}
	
	public interface IInteraction {
		
	}
}
