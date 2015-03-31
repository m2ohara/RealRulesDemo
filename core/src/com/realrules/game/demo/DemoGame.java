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
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
				 setDebateScreen();
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
//		int oppY = 925
//		final int sndWv1X = 460;
//		final int sndWv1Y = 1040;
		
		//Desktop
		final int expX = 190;
		final int expY = 440;
		int plX = 0;
		int plY = 260;
		int oppX = 230;
		int oppY = 265;
		final int sndWv1X = 110;
		final int sndWv1Y = 370;
		
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
//				 new SoundWave("soundWave1Pack", sndWv1X, sndWv1Y);
				 new SpinSprite("soundWave1Pack", sndWv1X, sndWv1Y);
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
		//Add actor to stage for physics processing
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
	    private float deltaStateTime;
	    private Animation rotate;
	    private int state = 0;
	    final ActionFactory actionFactory = new ActionFactory();
	    
	    public SpinSprite(String type, float x, float y) {	    	
			super(new TextureAtlas(Gdx.files.internal("sprites//"+type+".pack")).getRegions().first());
	    	this.x = x;
	    	this.y = y;
	    	this.type = type;
	    	
			this.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) 
			    {
			    	if(state == 0) {
				    	setMoveToAction();		    		
			    	}
			    }
			});
			
			
//			createSpinAnimation();
			
			setToStage(this, 30, 170);
			
	    }
	   
		private void setMoveToAction() {
			
    		Random rand = new Random();
    		int xCoord = rand.nextInt(50);
    		int yCoord = rand.nextInt(70); 
			
			this.addAction(Actions.moveBy(xCoord, yCoord, 6.0f));
			
		}
		
		public void createSpinAnimation() {
			TextureAtlas txAtlas;
			
			txAtlas = new TextureAtlas(Gdx.files.internal("sprites//"+type+".pack"));
			Array<AtlasRegion> regions  = (Array<AtlasRegion>) txAtlas.getRegions();

			rotate = new Animation(0.03f, regions);
			deltaStateTime = 0.03f;
		
		}
		
		private void setCurrentFrame() {
			
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame =  rotate.getKeyFrame(stateTime, true);
			
		}
		
//		@Override
//		public void draw(Batch batch, float parentAlpha) {
//			
//			setCurrentFrame();
//			
//			Color color = new Color(this.getColor().r, this.getColor().g,
//		            this.getColor().b, this.getColor().a * parentAlpha);
//
//		    batch.setColor(color);	  
//			
//			batch.draw(currentFrame,x,y);
//		}
		
//		@Override
//		public void act(float delta) {
//			super.act(delta);
//		}
		
	}
	
	public class SoundWave extends Image {
		
	    private String type;
	    private float x;
	    private float y;
	    private TextureRegion currentFrame;
	    private float stateTime;
	    private float deltaStateTime;
	    private Animation rotate;
	    final ActionFactory actionFactory = new ActionFactory();
		
		public SoundWave(String type, float x, float y) {
			super(new TextureAtlas(Gdx.files.internal("sprites//"+type+".pack")).getRegions().first());
			this.type = type;
			this.x = x;
			this.y = y;
			this.stateTime = 0f;
			currentFrame = new TextureAtlas(Gdx.files.internal("sprites//"+type+".pack")).getRegions().first();
//			createSpinAnimation();
			
//			this.addListener(new ClickListener() {
//				public void clicked(InputEvent event, float x, float y) 
//			    {
//			    	Random rand = new Random();
//			    	int xCoord = rand.nextInt(200);
//			    	int yCoord = rand.nextInt(300); 
//			    	setMoveToAction(xCoord, yCoord, 2.0f);			    		
//			    }
//			});
			
	    	Random rand = new Random();
	    	int xCoord = rand.nextInt(200);
	    	int yCoord = rand.nextInt(300); 

			
			setToStage(this, 30, 170);
			
	    	this.addAction(Actions.rotateTo(90.0f));
			
		}
		
		private void setMoveToAction(int x, int y, float duration) {
			MoveToAction moveTo = new MoveToAction();
			
			moveTo.setPosition(x, y);
			moveTo.setDuration(duration);
			
			Actor actor = (Actor)this;
			
			actor.addAction(moveTo);
			
			//TODO: Examine casting to resolve acting issue
			
		}
		
		public void createSpinAnimation() {
			TextureAtlas txAtlas;
			
			txAtlas = new TextureAtlas(Gdx.files.internal("sprites//"+type+".pack"));
			Array<AtlasRegion> regions  = (Array<AtlasRegion>) txAtlas.getRegions();

			rotate = new Animation(0.03f, regions);
			deltaStateTime = 0.03f;
		
		}
		
		private void setCurrentFrame() {
			
			stateTime += Gdx.graphics.getDeltaTime();
//			stateTime += deltaStateTime;
			currentFrame =  rotate.getKeyFrame(stateTime, true);
			
//			deltaStateTime -= 0.001;
			
		}
		
		@Override
		public void act(float delta )
		{
			super.act( delta );
		}
		
		

		@Override
		public void draw(Batch batch, float alpha){
			
			if(rotate != null) {
				setCurrentFrame();
			}
			batch.draw(currentFrame,x,y);
		}
	}
	
	//*****************Blinking icon class
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
	
	public class RandomBlinkingExpression extends BlinkingIcon {
		
		private static final int expressionLength = 5;

		public RandomBlinkingExpression(String expressionType, float x, float y,
				int displayInterval) {
			super(expressionType, x, y, displayInterval);
			// TODO Auto-generated constructor stub
		}
		
		private String getRandomExpressionType() {
			String exp = "ExpressionSmall"+(new Random().nextInt(expressionLength)+1);
			return exp;
		}
		
		@Override
		public void act(float delta )
		{
			super.act( delta );
			
			//Draw at regular intervals
			if(stateTime >= delta * displayInterval) {
				stateTime = 0f;
				drawImage = drawImage == false ? true : false;
				currentFrame = getTextureRegionFromPack(getRandomExpressionType());
			}
			else {
				stateTime += Gdx.graphics.getDeltaTime();
			}
			
		}
		
	}
	
	public class ActionFactory extends Image {
		
	    private TextureRegion currentFrame;
		
		public ActionFactory() {
		}
		
		public void setMoveToAction(Actor actor, int x, int y, float duration) {
			MoveToAction moveTo = new MoveToAction();
			
			moveTo.setPosition(x, y);
			moveTo.setDuration(duration);
			
			actor.addAction(moveTo);
			
		}
		
		public void setFightAction(Actor actor, int x, int y, float duration) {
			
		}
		
		@Override
		public void draw(Batch batch, float alpha){
//			batch.draw(currentFrame,x,y);
		}
		
	}

}
