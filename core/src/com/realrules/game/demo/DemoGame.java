package com.realrules.game.demo;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
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
//				 setDebateScreen();
				 setCrowdScreen();
			 }
		});
	}
	
	private void setDebateScreen() {
		
		//Android
		//Diff: ~350, ~675
//		final int expX = 540;
//		final int expY = 1100;
//		int plX = 350;
//		int plY = 920;
//		int oppX = 550;
//		int oppY = 925;
//		final int sndWv1X = 25;
//		final int sndWv1Y = 160;
		
		//Desktop
		final int expX = 190;
		final int expY = 440;
		int plX = 40;
		int plY = 260;
		int oppX = 270;
		int oppY = 265;
		final int sndWv1X = -25;
		final int sndWv1Y = 170;
		
		//Desktop
//		if(!isAndroid) {
//			expX = 190;
//			expY = 440;
//			plX = 0;
//			plY = 260;
//			oppX = 230;
//			oppY = 265;
//			sndWv1X = 110;
//			sndWv1Y = 370;
//		}

		
		setToStage(getImage("DebateScreen", "screens//screensPack"), 0, 0);

		final Character player = new Character("playerPack", plX, plY);
		setToStage(player, -90, 30);
		final Character opponent = new Character("opponentPack", oppX, oppY);
		setToStage(opponent, 90, 30);
		
		
		setToStage(getImage("SpeechBubbleLeft", "icons//iconsPack"), 0, 120);
		
		setToStage(getImage("ExpressionBox", "icons//iconsPack"), -130, -100);
		setToStage(getImage("ExpressionBox", "icons//iconsPack"), -65, -100);
		setToStage(getImage("ExpressionBox", "icons//iconsPack"), 65, -100);
		setToStage(getImage("ExpressionBox", "icons//iconsPack"), 130, -100);
		
		final ActionFactory actionFactory = new ActionFactory();
		final Actor btn1 = getButton("ExpressionSmall1Left");
		setToStage(btn1, -130, -100);
		
		btn1.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				 actionFactory.setMoveToAction(btn1, expX, expY, 0.3f);
			 }
		});
		
		final Actor btn2 = getButton("ExpressionSmall2Left");
		setToStage(btn2, -65, -100);
		btn2.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				 actionFactory.setMoveToAction(btn2, expX, expY, 0.3f);
			 }
		});
		
		final Actor btn3 = getButton("ExpressionSmall3Left");
		setToStage(btn3, 65, -100);
		btn3.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				 actionFactory.setMoveToAction(btn3, expX, expY, 0.3f);
			 }
		});
		
		final Actor btn4 = getImage("ExpressionSmall4Left", "buttons//buttonsPack");
		setToStage(btn4, 130, -100);
		btn4.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				 actionFactory.setMoveToAction(btn4, expX, expY, 0.3f);
			 }
		});
		
		Actor playBtn = getButton("PlayExpressionBtn");
		setToStage(playBtn, 130, -230);
		playBtn.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				 setToStage(getImage("SpeechBubbleRight", "icons//iconsPack"), 0, 40);
				 setToStage(getImage("ExpressionSmall6Right", "buttons//buttonsPack"), 10, 90);
				 player.createAnimation();
				 opponent.createAnimation();
				 new BlinkingIcon("SoundWave2", 15, 90, 40);
				 new SpinSprite("soundWave1Pack", sndWv1X, sndWv1Y, new TextureAtlas(Gdx.files.internal("sprites//soundWave1Pack.pack")).getRegions());
			 }
		});
		
		setToStage(getButton("ImproveBtn"), 0, -230);


		Actor ideasBtn = getButton("IdeasBtn");
		setToStage(ideasBtn, -130, -230);
		ideasBtn.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				Actor scroll = getImage("Scroll", "icons//iconsPack");
				if(scroll.isVisible()) {
					scroll.setVisible(false);
				}
				else if(!scroll.isVisible()){
					scroll.setVisible(true);
				}
				else
					setToStage(scroll, 0, -60);
			 }
		});
	}
	
	private void setCrowdScreen() {
		
		setToStage(getImage("CrowdScreen", "screens//screensPack"), 0, 0);
		
		new HeadSprite(Head.GOSSIPER, 10, 115, "sprites//gossiperFollowerPack.pack");
		
		
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
	
	//***************Character class
	public class Character extends Image {
	
	    private String type;
	    private float x;
	    private float y;
	    private TextureRegion currentFrame;
	    private float stateTime;
	    private Animation argue;
		
		public Character(String type, float x, float y) {
			super(new TextureAtlas(Gdx.files.internal("sprites//"+type+".pack")).getRegions().first());
			this.type = type;
			this.x = x;
			this.y = y;
			this.stateTime = 0f;
			currentFrame = new TextureAtlas(Gdx.files.internal("sprites//"+type+".pack")).getRegions().first();
			
		}
		
		public void createAnimation() {
			TextureAtlas txAtlas;
			
			txAtlas = new TextureAtlas(Gdx.files.internal("sprites//"+type+".pack"));
			Array<AtlasRegion> regions  = (Array<AtlasRegion>) txAtlas.getRegions();

			argue = new Animation(0.25f, regions);
		
		}
		
		private void setCurrentFrame() {
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame =  argue.getKeyFrame(stateTime, true);
		}
		
		@Override
		public void act(float delta )
		{
			if(argue != null) {
				super.act( delta );
				setCurrentFrame();
			}
		}

		@Override
		public void draw(Batch batch, float alpha){
			batch.draw(currentFrame,x,y);
		}
	}
	
	public class SpinSprite extends Image {
		
	    private String type;
	    private float x;
	    private float y;
	    private TextureRegion currentFrame;
	    private float stateTime;
	    private Animation rotate;
	    private int state = 0;
	    private Array<AtlasRegion> frames;
	    
	    public SpinSprite(String type, float x, float y, Array<AtlasRegion> frames) {	    	
			super(frames.get(0));
	    	this.x = x;
	    	this.y = y;
	    	this.type = type;
	    	this.frames = frames;
	    	
			this.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) 
			    {
			    	if(state == 0) {
				    	setMoveToAction();		    		
			    	}
			    }
			});
			
			rotate = new Animation(0.5f, this.frames);
			
			setToStage(this, this.x, this.y);
			
	    }
	   
		private void setMoveToAction() {
			
    		Random rand = new Random();
    		int xCoord = rand.nextInt(230) + 20;
    		int yCoord = rand.nextInt(140) + 180; 
    		
//    		int xCoord = rand.nextInt(540) + 330;
//    		int yCoord = rand.nextInt(810) + 850; 
			

    		this.addAction(Actions.moveTo(xCoord, yCoord, 0.5f));
			
		}
		
		private void setCurrentFrame() {
			
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame =  rotate.getKeyFrame(stateTime, true);
			
			this.setDrawable(new TextureRegionDrawable(new TextureRegion(currentFrame)));
			
		}
		
		@Override
		public void act(float delta) {
			super.act(delta);
			
			setCurrentFrame();
		}
		
	}
	
	public class BlinkingIcon extends Image {
		
		//TO DO: Create spinning feature
		
	    protected float x;
	    protected float y;
	    protected TextureRegion currentFrame;
	    protected float stateTime;
	    protected boolean drawImage;
	    protected int displayInterval;
		
		public BlinkingIcon(String type, float x, float y, int displayInterval) {
			super(new TextureAtlas(Gdx.files.internal("icons//iconsPack.pack")).findRegion(type));
			currentFrame = getTextureRegionFromPack(type);
			stateTime = 0f;
			this.x = x;
			this.y = y;
			this.displayInterval = displayInterval;
			setToStage(this, x, y);
		}
		
		@Override
		public void act(float delta )
		{
			super.act( delta );
			
			//Draw at regular intervals
			if(stateTime >= delta * displayInterval) {
				stateTime = 0f;
				drawImage = drawImage == false ? true : false;
			}
			else {
				stateTime += Gdx.graphics.getDeltaTime();
			}
			
		}

		@Override
		public void draw(Batch batch, float alpha){
			if(drawImage) {
				validate();

				Color color = getColor();
				batch.setColor(color.r, color.g, color.b, color.a * this.getParent().getColor().a);

				float x = getX();
				float y = getY();
				float scaleX = getScaleX();
				float scaleY = getScaleY();

				if (getDrawable() instanceof TransformDrawable && drawImage) {
					float rotation = getRotation();
					if (scaleX != 1 || scaleY != 1 || rotation != 0) {
						((TransformDrawable)getDrawable()).draw(batch, x + this.getImageX(), y + this.getImageY(), getOriginX() - this.getImageX(), getOriginY() - this.getImageY(),
								this.getImageWidth(), this.getImageHeight(), scaleX, scaleY, rotation);
						return;
					}
				}
				if (this.getDrawable() != null) this.getDrawable().draw(batch, x + this.getImageX(), y + this.getImageY(), this.getImageWidth() * scaleX, this.getImageHeight()* scaleY);
			}
		}
		
		protected TextureRegion getTextureRegionFromPack(String type) {
			
			TextureAtlas txAtlas;
			
			txAtlas = new TextureAtlas(Gdx.files.internal("icons//iconsPack.pack"));
			TextureRegion txRegion = txAtlas.findRegion(type);
			
			return txRegion;
		}
		
	}
	
	public class ActionFactory {
		
		
		public ActionFactory() {
		}
		
		public void setMoveToAction(Actor actor, int x, int y, float duration) {
			MoveToAction moveTo = new MoveToAction();
			
			moveTo.setPosition(x, y);
			moveTo.setDuration(duration);
			
			actor.addAction(moveTo);
			
		}
		
		
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
		
		public float getStartingX() {
			return startingX;
		}

		public float getStartingY() {
			return startingY;
		}
		
		public HeadSprite(Head type, float x, float y, String framesPath) {
			super(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
			frames = new TextureAtlas(Gdx.files.internal(framesPath)).getRegions();
			currentFrame = frames.get(0);
			//Centre origin in frame for rotation;
			this.setOrigin(this.currentFrame.getRegionWidth()/2, this.currentFrame.getRegionHeight()/2);
			
			startingX = x;
			startingY = y;
			
			if(type == type.GOSSIPER) {
				behaviour = new GossiperBehaviour(this);
			}
			
			setTouchAction();
			
			setToStage(this, this.startingX, this.startingY);
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
		
		public GossiperBehaviour(HeadSprite gossiper) {
			this.gossiper = gossiper;
			
			
		}

		@Override
		public void onAct(float delta) {
			
			if(stateTime >= stateLength) {
				stateTime = 0.0f;
				setFrame();
			}
			stateTime += delta;
		}

		@Override
		public void onDraw(Batch batch, float parentAlpha) {
//			batch.draw(
//					gossiper.currentFrame, 0, 0, gossiper.currentFrame.getRegionWidth()/2, gossiper.currentFrame.getRegionHeight()/2, 
//					gossiper.currentFrame.getRegionWidth(), gossiper.currentFrame.getRegionHeight(), 0, 0,  gossiper.rotation
//					);
			
		}

		@Override
		public void onTouch() {
			// TODO Auto-generated method stub
			
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
				gossiper.currentFrame = gossiper.frames.get(rand.nextInt(2));
				//Set angle
				int angle = rand.nextInt(3);
				gossiper.setRotation((float) (45 * angle));
				
				gossiper.setDrawable(new TextureRegionDrawable(new TextureRegion(gossiper.currentFrame)));
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
		
	}
}
