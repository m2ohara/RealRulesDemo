package com.realrules.game.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class DemoGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	public Stage stage;
	OrthographicCamera camera;
	private boolean isAndroid = false;
	ManualInteraction interaction = new ManualInteraction();
	
	
//	public ArrayList<Float> gameXCoords = new ArrayList<Float>(Arrays.asList(134f, 214f, 294f)); 
//	public ArrayList<Float>  gameYCoords = new ArrayList<Float>(Arrays.asList(382.5f, 312.5f, 242.5f, 172.5f));
	public ArrayList<Float> gameXCoords = new ArrayList<Float>();
	public ArrayList<Float>  gameYCoords = new ArrayList<Float>();
//	public final int xSpan = 216;
//	public final int ySpan = 288;
	public final int xGrid = 3;
	public final int yGrid = 5;
	float headSpriteH = 72;
	int headSpriteW = 72;
	

	private void setGameCoords() {
		float centreX = (Gdx.graphics.getWidth()) / 2; 
		float xSpan = headSpriteW*xGrid;
		float startX = centreX - (xSpan/2);
		float spanLengthX = startX + xSpan;
		for(float x = startX; x < spanLengthX; x+=headSpriteW) {
			gameXCoords.add(x);
		}
		
		float centreY = (Gdx.graphics.getHeight()) / 2; 
		float ySpan = headSpriteH*yGrid;
		float startY = centreY + (ySpan/2);
		float spanLengthY = startY - ySpan;
		for(float y = startY; y > spanLengthY; y-=headSpriteH) {
			gameYCoords.add(y);
		}
	}
	
	public boolean isAndroid() {
		return isAndroid;
	}

	public void setAndroid(boolean isAndroid) {
		this.isAndroid = isAndroid;
	}

	@Override
	public void create () {
		setGameCoords();
		batch = new SpriteBatch();
		setView();
		
		GestureDetector gd = new GestureDetector(new GameGestures());
		
		InputMultiplexer im = new InputMultiplexer(gd, stage);
		Gdx.input.setInputProcessor(im);
		
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
		
		Actor image = new Image(txSkin.getDrawable(type));
		image.setTouchable(Touchable.disabled);
		return image;
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
		
		_xCentreOffset = actorToSet.getX();
		_yCentreOffset = actorToSet.getY();
	}
	
	public HeadSprite getMemberFromCoords(int gameXPos, int gameYPos) {
		
		Actor neighbour = null;
//		System.out.println("Getting member at gameCoords "+gameXPos+", "+gameYPos);
		if(gameXPos != -1 && gameXPos < gameXCoords.size() && gameYPos != -1 && gameYPos < gameYCoords.size()) {
			neighbour = stage.hit(gameXCoords.get(gameXPos), gameYCoords.get(gameYPos), true);
		}
		return (HeadSprite)neighbour;
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
		
		Actor screen = getImage("CrowdScreen", "screens//screensPack");
		screen.setTouchable(Touchable.disabled);
		setToStage(screen, 0, 0);
		
		new HeadSprite(Head.DECEIVER, gameXCoords.get(0), gameYCoords.get(0), "sprites//deceiverFollowerPack.pack", true);
		HeadSprite start =  new HeadSprite(Head.GOSSIPER, gameXCoords.get(1), gameYCoords.get(0), "sprites//gossiperFollowerPack.pack", true);
		start.status = 1; start.setColor(Color.CYAN);
		new HeadSprite(Head.DECEIVER, gameXCoords.get(2), gameYCoords.get(0), "sprites//deceiverFollowerPack.pack", true);
		
		new HeadSprite(Head.GOSSIPER, gameXCoords.get(0), gameYCoords.get(1), "sprites//gossiperFollowerPack.pack", true);
		new HeadSprite(Head.DECEIVER, gameXCoords.get(1), gameYCoords.get(1), "sprites//deceiverFollowerPack.pack", true);
		new HeadSprite(Head.DECEIVER, gameXCoords.get(2), gameYCoords.get(1), "sprites//deceiverFollowerPack.pack", true);
		
		new HeadSprite(Head.GOSSIPER, gameXCoords.get(0), gameYCoords.get(2), "sprites//gossiperFollowerPack.pack", true);
		new HeadSprite(Head.DECEIVER, gameXCoords.get(1), gameYCoords.get(2), "sprites//deceiverFollowerPack.pack", true);
		new HeadSprite(Head.DECEIVER, gameXCoords.get(2), gameYCoords.get(2), "sprites//deceiverFollowerPack.pack", true);
		
		new HeadSprite(Head.GOSSIPER, gameXCoords.get(0), gameYCoords.get(3), "sprites//gossiperFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, gameXCoords.get(1), gameYCoords.get(3), "sprites//promoterFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, gameXCoords.get(2), gameYCoords.get(3), "sprites//promoterFollowerPack.pack", true);
		
		new HeadSprite(Head.GOSSIPER, gameXCoords.get(0), gameYCoords.get(4), "sprites//gossiperFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, gameXCoords.get(1), gameYCoords.get(4), "sprites//promoterFollowerPack.pack", true);
		new HeadSprite(Head.GOSSIPER, gameXCoords.get(2), gameYCoords.get(4), "sprites//promoterFollowerPack.pack", true);
	
	}
	
	public class HeadSprite extends Image {
		
		private IHeadBehaviour behaviour;
		public Interaction interaction;
		private float startingX;
		private float startingY;
		private Array<AtlasRegion> frames;
		private TextureRegion currentFrame;
		private int direction; //0 : right, 1 : left
		private float movementP = 0.1f;
		private float rotateP = 0.8f;
		private float argueSuccessP = 0.2f;
		private float interactSuccessP = 0.2f;
		private InteractSprite soundWave;
		public boolean isActive = true;
		public int status = 0; //0 : neutral, 1 : for 2 : against
		
		public int getXGameCoord() {
//			System.out.println("member x is "+this.getX());
			return gameXCoords.indexOf(this.getX());
		}
		
		public int getYGameCoord() {
//			System.out.println("member y is "+this.getY());
			return gameYCoords.indexOf(this.getY());
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
				interaction = new GossiperInteraction();
				this.setPosition(startingX, startingY);
				stage.addActor(this);

			}
			if(type == type.DECEIVER) {
				behaviour = new DeceiverBehaviour(isActive, framesPath, this.startingX, this.startingY);
				interaction = new DeceiverInteraction();
				this.setPosition(startingX, startingY);
				stage.addActor(this);
				//Has to take place after HeadSprite creation
				behaviour.setInteractSprite();
			}
			
			setTouchAction();
			

			
			if(type == type.GOSSIPER) {
				//Set interact sprite
				soundWave = new InteractSprite(this.startingX, this.startingY, "sprites//soundWaveFollower.pack"); 
				soundWave.setTouchable(Touchable.disabled);
			}
			
		}
		
		//Implement onTouch action
		private void setTouchAction() {
			
			this.addListener(new ClickListener() {
				
				public void clicked(InputEvent event, float x, float y) 
			    {
					System.out.println("Hit at: x: "+x+", y: "+y+"");
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

	public enum Head { GOSSIPER, DECEIVER, INFLUENCER}

	
	public class GossiperBehaviour implements IHeadBehaviour {
		
		HeadSprite gossiper;
		float stateLength = 2.0f;
		float stateTime = stateLength;
		float InStateLength = 0.7f;
		float InStateTime = InStateLength;
		float TouchStateLength = 3.0f;
		float TouchStateTime = 0;
		private boolean isActive = false;
		private int direction;
		
		//Skills
		private int influenceAmount = 2;
		Random rand = new Random();
		
		
		public int getInfluenceAmount() {
			return influenceAmount;
		}

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
					performAutonomousInteraction();
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
				isActive = false;
			}
			
			
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
			if(rand.nextFloat() < gossiper.rotateP) {
				//Set direction
				direction = rand.nextInt(2);
				gossiper.direction = direction;
				gossiper.currentFrame = gossiper.frames.get(direction);
				//Set angle
				int angleSpread = 90;
				int angleSector = rand.nextInt(2);
				int angleMultiple = angleSpread * rand.nextInt((180/angleSpread)-1);
				int startingAngle = angleSector == 0 ? 0 : 270;
				int finalAngle = startingAngle + angleMultiple;
				
				//Rotate gossiper
				gossiper.setRotation((float) (finalAngle));	
				gossiper.setDrawable(new TextureRegionDrawable(new TextureRegion(gossiper.currentFrame)));
				
				//Rotate soundwave
				int soundWaveAngle = direction == 1 ? finalAngle : (finalAngle + 180) % 360;
				gossiper.soundWave.setRotation(soundWaveAngle);
				gossiper.soundWave.currentAngle = (int)gossiper.soundWave.getRotation();
				
			}
			
		}
		
		private void performAutonomousInteraction() {
			Random rand = new Random();
			if(rand.nextFloat() < gossiper.interactSuccessP) {
				gossiper.soundWave.setVisible(true);
				gossiper.interaction.interactAutonomous(this.gossiper);
				
			}
			else {
				gossiper.soundWave.setVisible(false);
			}
		}

		@Override
		public void onAct(float delta, HeadSprite actor) {
			// TODO Refactor acting into this method
			this.onAct(delta);
			
		}

		@Override
		public void setInteractSprite() {
			// TODO Refactor soundwave creation into here
			
		}

		@Override
		public int getDirection() {
			return direction;
		}
		
		
	}
	
	public class DeceiverBehaviour implements IHeadBehaviour {
		
		Random rand = new Random();
		float stateLength = 2.0f;
		float stateTime = stateLength;
		float InStateLength = 0.7f;
		float InStateTime = InStateLength;
		float TouchStateLength = 3.0f;
		float TouchStateTime = 0;
		private boolean isActive = true;
		float x;
		float y;
		private int direction; //0 : right, 1 : left

		private Array<AtlasRegion> frames;
		private TextureRegion currentFrame;
		private float movementP = 0.1f;
		private float rotateP = 0.8f;
		private float interactSuccessP = 0.2f;
		private InteractSprite soundWave;
		
		public int status = 0; //0 : neutral, 1 : for 2 : against
		public int influenceAmount = 3;
		public float argueSuccessP = 0.2f;
		
		//Current angles
		int deceiverAngle = 0;
		int soundWaveAngle = 0;
		
		public DeceiverBehaviour(boolean isActive, String framesPath, float x, float y) {
			frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
			currentFrame = frames.get(0);
			this.direction = 0;
			this.isActive = isActive;
			this.x = x;
			this.y = y;
			

		}
		
		public void setInteractSprite() {
			//Set interact sprite
			this.soundWave = new InteractSprite(x, y, "sprites//soundWaveFollower.pack"); 
			this.soundWave.setTouchable(Touchable.disabled);
		}

		@Override
		public void onAct(float delta) {
			//Not implemented
			
		}

		@Override
		public void onDraw(Batch batch, float parentAlpha) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTouch() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getInfluenceAmount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void onAct(float delta, HeadSprite actor) {
			
			if(isActive) {
				if(stateTime >= stateLength) {
					stateTime = 0.0f;
					updateCurrentAngle();
				}
				stateTime += delta;
				

				if( InStateTime >= InStateLength) {
					InStateTime = 0.0f;
					performAutonomousInteraction(actor);
				}
				InStateTime += delta;
			}
			

			
			//Rotate this
			actor.setRotation((float) (deceiverAngle));	
			actor.setDrawable(new TextureRegionDrawable(new TextureRegion(this.currentFrame)));
			
	
			this.soundWave.setRotation(soundWaveAngle);
			this.soundWave.currentAngle = (int)this.soundWave.getRotation();
			
		}
		
		//Deceiver behaviours
		private void updateCurrentAngle() {
			//Based on rotation probability
			if(rand.nextFloat() < this.rotateP) {
				
				//Set direction
				direction = rand.nextInt(2);
				this.currentFrame = this.frames.get(direction);
				//Set angle
				int angleSpread = 90;
				int angleSector = rand.nextInt(2);
				int angleMultiple = angleSpread * rand.nextInt((180/angleSpread)-1);
				int startingAngle = angleSector == 0 ? 0 : 270;
				deceiverAngle = startingAngle + angleMultiple;
				
				//Rotate soundwave
				soundWaveAngle = direction == 1 ? deceiverAngle : (deceiverAngle + 180) % 360;
				
			}
		}
		
		private void performAutonomousInteraction(HeadSprite actor) {
			Random rand = new Random();
			if(rand.nextFloat() < this.interactSuccessP) {
				this.soundWave.setVisible(true);
				actor.interaction.interactAutonomous(actor);
				
			}
			else {
				this.soundWave.setVisible(false);
			}
		}

		@Override
		public int getDirection() {
			return direction;
		}
		
	}
	
	public class InteractSprite extends Image {
		
		private Array<AtlasRegion> frames;
		private TextureRegion currentFrame;
		private int currentAngle;
		private float xCoord;
		private float yCoord;

		public InteractSprite(float followerX, float followerY, String framesPath) {
			super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
			frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
			currentFrame = frames.get(0);
			
			//Centre sprite to follower's centre
			this.xCoord = followerX + (headSpriteW/2) - (this.getWidth()/2);
			this.yCoord = followerY + (headSpriteH/2) - (this.getHeight()/2);
			
			//Centre origin in frame for rotation;
			this.setOrigin(this.currentFrame.getRegionWidth()/2, this.currentFrame.getRegionHeight()/2);			

			
//			setToStage(this, this.xCoord, this.yCoord);
			this.setPosition(this.xCoord, this.yCoord);
			stage.addActor(this);
			
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

	public interface IHeadBehaviour {
		
		//Act behaviour (redundant)
		void onAct(float delta);
		
		//Draw behaviour
		void onDraw(Batch batch, float parentAlpha);
		
		//Touch behaviour
		void onTouch();
		
		//New act behaviour
		void onAct(float delta, HeadSprite actor);
		
		int getInfluenceAmount();
		
		void setInteractSprite();
		
		int getDirection();
		
	}
	
	public class GameGestures implements GestureListener {
		
		boolean isFirstHit = true;
//		ManualInteraction interaction = new ManualInteraction();

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
			
			if(actor != null && actor.getClass().equals(HeadSprite.class)) {
				//TODO: Refector interaction into HeadSprite
//				//Pass actor into interactor
//				interaction = ((HeadSprite)actor).interaction;
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
	
	public class ManualInteraction {
		
		//Interacting
		HeadSprite interactor;
		ArrayList<HeadSprite> interactees = new ArrayList<HeadSprite>();
		HeadSprite lastHitActor = null;
		boolean invalidInteraction = false;
		
		public ManualInteraction() {
			
		}
		
		public void interactHit(HeadSprite hitActor, boolean isFirst) {
			//If new actor is hit
			if((lastHitActor == null || !hitActor.equals(lastHitActor))) {
					
					//If first hit and is influenced actor
					if(isFirst && hitActor.status == 1 && hitActor.isActive) {	
						invalidInteraction =false;
						interactor = hitActor;
						setToFollower(hitActor);
						
						float angle = hitActor.getRotation();
						angle = hitActor.getDirection() == 0 ? angle + 180f : angle;
						
						System.out.println("First follower hit facing "+angle);
					}
					//Neutral interactee
					else if(interactor != null && !invalidInteraction && !isFirst && interactor.behaviour.getInfluenceAmount() > interactees.size() && hitActor.status == 0) {
						if(validInteraction(hitActor)) {
							//Set previous hit actor to untouchable
							lastHitActor.status = 2;
							interactees.add(hitActor);
							setToFollower(hitActor);
						}
						else {
							invalidInteraction = true;
							System.out.println("Invalid hit");
						}
					}		
					
					hitActor.isActive = true;
					
					lastHitActor = hitActor;
				}
		}
		
		public void reset() {
			interactor = null;
			interactees.clear();
			lastHitActor = null;
		}
		
		private boolean validInteraction(HeadSprite hitActor) {
			
			boolean isValid = false;
			//Get lastHitActor's facing angle
			float facingAngle = lastHitActor.getRotation();
			int direction = lastHitActor.getDirection();
			
			//If facing towards the right
			if(direction == 1) {
				if(facingAngle == 0) {
					if(lastHitActor.startingX < hitActor.startingX && lastHitActor.startingY ==  hitActor.startingY) {
						isValid = true;
						System.out.println("Follower hit to the right");
					}
				}
				if(facingAngle == 90) {
					if(lastHitActor.startingX == hitActor.startingX && lastHitActor.startingY <  hitActor.startingY) {
						isValid = true;
						System.out.println("Follower hit above");
					}
				}
				if(facingAngle == 270) {
					if(lastHitActor.startingX == hitActor.startingX && lastHitActor.startingY >  hitActor.startingY) {
						isValid = true;
						System.out.println("Follower hit below");
					}
				}
			}
			
			//If facing towards the left
			if(direction == 0) {
				if(facingAngle == 0) {
					if(lastHitActor.startingX > hitActor.startingX && lastHitActor.startingY ==  hitActor.startingY) {
						isValid = true;
						System.out.println("Follower hit to the left");
					}
				}
				if(facingAngle == 90) {
					if(lastHitActor.startingX == hitActor.startingX && lastHitActor.startingY >  hitActor.startingY) {
						isValid = true;
						System.out.println("Follower hit above");
					}
				}
				if(facingAngle == 270) {
					if(lastHitActor.startingX == hitActor.startingX && lastHitActor.startingY <  hitActor.startingY) {
						isValid = true;
						System.out.println("Follower hit below");
					}
				}
			}
			
			return isValid;

		}
		
		private void setToFollower(HeadSprite hitActor) {
			hitActor.setColor(Color.CYAN);
			hitActor.status = 1;
		}
	}
	
	public class Interaction {
		
		public IInteraction interactionBehaviour = null;
		
		public void interactAutonomous(HeadSprite interactor) {
			
			//As long as interactor isn't neutral
			if(interactor.status != 0) {
				//Determine interactor's direction
				float facingAngle = interactor.getRotation();
				int direction = interactor.getDirection();
				
				HeadSprite interactee = null;
				
				//If facing towards the right
				if(direction == 1) {
					if(facingAngle == 0 && (interactor.getXGameCoord()+1) < gameXCoords.size()) {
						//Get interactee by coordinates
						interactee = getMemberFromCoords(interactor.getXGameCoord()+1, (interactor.getYGameCoord()));
						System.out.println("Member type "+interactor.status+"  influencing to the right at "+(interactor.getXGameCoord()+1)+", "+interactor.getYGameCoord());
	
					}
					if(facingAngle == 90 && (interactor.getYGameCoord()-1) > -1) {
						interactee = getMemberFromCoords(interactor.getXGameCoord(), (interactor.getYGameCoord()-1));
						System.out.println("Member type "+interactor.status+"  influencing above at "+interactor.getXGameCoord()+", "+(interactor.getYGameCoord()-1));
	
					}
					if(facingAngle == 270 && (interactor.getYGameCoord()+1) < gameYCoords.size()) {
						interactee = getMemberFromCoords(interactor.getXGameCoord(), (interactor.getYGameCoord()+1));
						System.out.println("Member type "+interactor.status+"  influencing below at "+interactor.getXGameCoord()+", "+(interactor.getYGameCoord()+1));
					}
				}
				
				//If facing towards the left
				if(direction == 0) {
					if(facingAngle == 0 && (interactor.getXGameCoord()-1) > -1) {
						interactee = getMemberFromCoords(interactor.getXGameCoord()-1, (interactor.getYGameCoord()));
						System.out.println("Member type "+interactor.status+" influencing to the left at "+(interactor.getXGameCoord()+1)+", "+interactor.getYGameCoord());
					}
					if(facingAngle == 90 && (interactor.getYGameCoord()+1) < gameYCoords.size()) {
						interactee = getMemberFromCoords(interactor.getXGameCoord(), (interactor.getYGameCoord()+1));
						System.out.println("Member type "+interactor.status+" influencing above at "+interactor.getXGameCoord()+", "+(interactor.getYGameCoord()+1));
					}
					if(facingAngle == 270 && (interactor.getYGameCoord()-1) > -1) {
						interactee = getMemberFromCoords(interactor.getXGameCoord(), (interactor.getYGameCoord()-1));
						System.out.println("Member type "+interactor.status+" influencing below at "+interactor.getXGameCoord()+", "+(interactor.getYGameCoord()-1));
					}
				}
				
				//Perform interaction
				if(interactee != null) {
					System.out.println("Influencing type "+interactee.status+"");
					interact(interactor, interactee);
				}
			}
			
		}
		
		protected void interact(HeadSprite interactor, HeadSprite interactee) {
			
			if(this.interactionBehaviour != null) {
				this.interactionBehaviour.interact(interactor, interactee);
			}
		}
		
	}
	
	public class GossiperInteraction extends Interaction {
		
		public GossiperInteraction() {
			this.interactionBehaviour = new GossiperInteractBehaviour();
		}
		
	}
	
	public class DeceiverInteraction extends Interaction {
		
		public DeceiverInteraction() {
			this.interactionBehaviour = new DeceiverInteractBehaviour();
		}
		
	}
	
	public interface IInteraction {
		void interact(HeadSprite interactor, HeadSprite interactee);
		
	}	
	
	public class GossiperInteractBehaviour implements IInteraction {

		@Override
		public void interact(HeadSprite interactor, HeadSprite interactee) {
			Random rand = new Random();
			
			//TODO: Check interactee isn't hit
			//Influence if interactee is neutral
			if(interactee.status == 0 && interactee.isActive == true) {
				if(rand.nextFloat() > interactor.argueSuccessP) {
					interactee.status = 3;
					interactee.setColor(Color.GRAY);
					System.out.println("Opposer influenced");
				}
				else {
					interactee.status = 1;
					interactee.setColor(Color.CYAN);
					System.out.println("Follower influenced");
				}
			}
			
		}
		
	}
	
	public class DeceiverInteractBehaviour implements IInteraction {

		@Override
		public void interact(HeadSprite interactor, HeadSprite interactee) {
			Random rand = new Random();
			
			//TODO: Check interactee isn't hit
			//Influence if interactee is neutral
			if(interactee.status == 0) { // && interactee.isActive == true) {
				if(rand.nextFloat() > interactor.argueSuccessP) {
					interactee.status = 3;
					interactee.setColor(Color.GREEN);
					System.out.println("Opposer influenced");
				}
			}
			
		}
		
	}
	

//	
//	public class InfluencerBehaviour implements IHeadBehaviour {
//		
//		public InfluencerBehaviour() {
//			
//		}
//
//		@Override
//		public void onAct(float delta) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onDraw(Batch batch, float parentAlpha) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onTouch() {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		//Implement movement action
//		
//		//Implement frame setting
//		
//		//Implement collision detection
//		
//	}
//	
}
