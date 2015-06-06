package com.realrules.game.demo;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class DemoGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	OrthographicCamera camera;
	private boolean isAndroid = false;
	
	public static float universalTimeRatio = 0.7f;

	public boolean isAndroid() {
		return isAndroid;
	}

	public void setAndroid(boolean isAndroid) {
		this.isAndroid = isAndroid;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		
		Stage stage = setView();
		
		GestureDetector gd = new GestureDetector(new GameGestures(stage));
		
		InputMultiplexer im = new InputMultiplexer(gd, stage);
		Gdx.input.setInputProcessor(im);
		
		GameProperties.get().setStage(stage);
		
		setTitleScreen();
	}
	
	private Stage setView() {
		Stage stage = null;
		if(!isAndroid) {
			stage = new Stage();
		}
		else {
			stage = setViewport(stage);
		}
		
		return stage;
	}
	
	private Stage setViewport(Stage stage) {
		OrthographicCamera camera = new OrthographicCamera(1080, 1520);
		StretchViewport viewport = new StretchViewport(1080, 1920);
		camera.zoom = 0.38f;
		stage = new Stage(viewport);
		stage.getViewport().setCamera(camera);
		
		return stage;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		GameProperties.get().getStage().draw();
		GameProperties.get().getStage().act(Gdx.graphics.getDeltaTime());
		batch.end();
		
		if(ScoreState.IsPlaying()) {
			updateScoreState();
		}
	}
	
	@Override
	public void resize(int width, int heigth) {
		GameProperties.get().getStage().getViewport().update(width, heigth, true);
	}
	@Override
	public void dispose() {
		GameProperties.get().dispose();
		this.dispose();
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

		GameProperties.get().addActorToStage(actor);
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
		
		Random crowdSetter = new Random();
		int starterX = crowdSetter.nextInt(CoordinateSystem.getSystemWidth()-1);
		for(int x = 0; x < CoordinateSystem.getSystemWidth(); x++) {
			for(int y = 0; y < CoordinateSystem.getSystemHeight(); y++) {
				HeadSprite current = null;
				float rand = crowdSetter.nextFloat();
				rand = 0.99f;
				if(rand < 0.33) {
					current = new HeadSprite(Head.GOSSIPER, CoordinateSystem.get().getGameXCoords().get(x), CoordinateSystem.get().getGameYCoords().get(y), "sprites//gossiperFollowerPack.pack", true);
				}
				else if(rand >= 0.33 && rand < 0.66) {
					current = new HeadSprite(Head.DECEIVER, CoordinateSystem.get().getGameXCoords().get(x), CoordinateSystem.get().getGameYCoords().get(y), "sprites//deceiverFollowerPack.pack", true);
				}
				else {
					current = new HeadSprite(Head.INFLUENCER, CoordinateSystem.get().getGameXCoords().get(x), CoordinateSystem.get().getGameYCoords().get(y), "sprites//promoterFollowerPack.pack", true);
				}
				if(y == CoordinateSystem.getSystemHeight()-1 && x == starterX) {
					current.status = 1; current.setColor(Color.GREEN); 
				}
				GameProperties.get().addToActorGroup(current);
			}
		}
		
		ScoreState.setTotalPoints(GameProperties.get().getActorGroup().getChildren().size);	
		GameProperties.get().getStage().addActor(GameProperties.get().getActorGroup());
		GameProperties.get().getStage().addActor(GameProperties.get().getSoundWaveGroup());
	
	}
	
	private void updateScoreState() {
		ScoreState.State state = ScoreState.getScoreState(GameProperties.get().getActorGroup());
		if(state == ScoreState.State.WIN) {
			setToStage(getImage("WinSprite", "sprites//textPack"), 0, 0);
		}
		else if(state == ScoreState.State.LOSE) {
			setToStage(getImage("LoseSprite", "sprites//textPack"), 0, 0);
		}
		else if(state == ScoreState.State.DRAW) {
			setToStage(getImage("DrawSprite", "sprites//textPack"), 0, 0);
		}
	}

	public enum Head { GOSSIPER, DECEIVER, INFLUENCER}

	public enum Interact { SOUNDWAVE }
}
