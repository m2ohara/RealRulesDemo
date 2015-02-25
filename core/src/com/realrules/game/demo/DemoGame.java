package com.realrules.game.demo;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class DemoGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	public Stage stage;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		setTitleScreen();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		stage.draw();
		stage.act();
		batch.end();
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
		setToStage(getImage("DebateScreen", "screens//screensPack"), 0, 0);
		
		setToStage(new Character("playerPack", 0, 260), -90, 30);
		setToStage(new Character("opponentPack", 230, 265), 90, 30);
		
		new BlinkingIcon("SpeechBubbleLeft", 100, 320, 50);
		new BlinkingIcon("SpeechBubbleRight", 100, 250, 40);
//		new RandomBlinkingExpression("ExpressionSmall1", 130, 350, 50);
		
		setToStage(getButton("IdeasBtn"), -130, -230);
		setToStage(getButton("ImproveBtn"), 0, -230);
		setToStage(getButton("PlayExpressionBtn"), 130, -230);
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
			createAnimation();

			
		}
		
		private void createAnimation() {
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
			super.act( delta );
			setCurrentFrame();
		}

		@Override
		public void draw(Batch batch, float alpha){
			batch.draw(currentFrame,x,y);
		}
	}
	
	//*****************Blinking icon class
	public class BlinkingIcon extends Image {
		
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
			setToStage(this, 0, -400);
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
			if(drawImage)
				batch.draw(currentFrame,x,y);
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

}
