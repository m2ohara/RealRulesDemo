package com.realrules.game.main;

import java.util.ArrayList;
import java.util.List;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.realrules.game.gestures.GameGestures;
import com.realrules.game.interact.IManualInteraction;
import com.realrules.game.interact.ManualOpposerInteraction;
import com.realrules.game.interact.ManualSupporterInteraction;
import com.realrules.game.main.ScoreState.State;
import com.realrules.game.state.Follower;
import com.realrules.game.state.FollowerType;
import com.realrules.game.state.PlayerState;

public class DemoGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	OrthographicCamera camera;
	private boolean isAndroid = false;
	public InputMultiplexer im = null;
	public Stage stage;
	
	//Make easier to access
	PlayerState plState = null;
	
	//Refactor to GameSetup
	private ScoreState scoreState = null;
	int winAmount = 0;
	State winState = null;
	IManualInteraction manualInteraction = null;
	Label remainingVotesCounter = null;
	Label endScoreCounter = null;
	
	public static float universalTimeRatio = 0.7f;

	public boolean isAndroid() {
		return isAndroid;
	}

	public void setAndroid(boolean isAndroid) {
		this.isAndroid = isAndroid;
	}

	@Override
	public void create () {

		plState = PlayerState.get();
		plState.load();
		
		batch = new SpriteBatch();
		
		stage = setView();
		
		GameProperties.get().setStage(stage);
		
		createNewGame();
		
		setTitleScreen();
	}
	
	private void createNewGame() {		
		setGestureDetector(new GestureDetector(new GameGestures(stage)));
	}
	
	private void setGestureDetector(GestureDetector gd) {
		im = new InputMultiplexer(gd, stage);
		Gdx.input.setInputProcessor(im);
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		Stage stage = GameProperties.get().getStage();
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
		batch.end();
		
		if(scoreState != null && scoreState.getCurrentState() != State.FINISHED) {
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
		
		setGameVoteRules();
		
		btn.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				 setCrowdScreen();
			 }
		});
	}
	
	//Refactor into GameSetup
	private void setGameVoteRules() {
		Random rand = new Random();
		
		String voteType = "";
		int vType = rand.nextInt(2);
		if(vType == 0) {
			voteType = "PASS";
			winState = State.WIN;
			manualInteraction = new ManualSupporterInteraction();
		}
		else {
			voteType = "DEFEAT";
			winState = State.LOSE;
			manualInteraction = new ManualOpposerInteraction();
		}
		
		
		int amount = CoordinateSystem.getSystemWidth() * CoordinateSystem.getSystemHeight();
		winAmount = rand.nextInt(amount-8)+7;
		
		
		final Skin skin = new Skin();
		BitmapFont font = new BitmapFont();
		font.scale(1f);
		skin.add("default", new LabelStyle(font, Color.BLACK));
		Label label = new Label(""+voteType+" THE BILL", skin);
		setToStage(label, 0, 0);
		Label label2 = new Label("BY "+winAmount+" VOTES TO WIN", skin);
		setToStage(label2, 0, -90);
		
		setGestureDetector(new GestureDetector(new GameGestures(stage, manualInteraction, vType)));
	}
	
	private void setCrowdScreen() {
		
		Actor screen = getImage("GameScreen", "screens//screensPack");
		screen.setTouchable(Touchable.disabled);
		setToStage(screen, 0, 0);
		
		List<FollowerType> types = plState.getFollowerTypes();
		Random crowdSetter = new Random();
		int starterX = crowdSetter.nextInt(CoordinateSystem.getSystemWidth()-1);
		for(int x = 0; x < CoordinateSystem.getSystemWidth(); x++) {
			for(int y = 0; y < CoordinateSystem.getSystemHeight(); y++) {
				HeadSprite current = null;
				float rand = crowdSetter.nextFloat();
				if(rand < -0.33) {
					current = new HeadSprite(Head.GOSSIPER, CoordinateSystem.get().getGameXCoords().get(x), CoordinateSystem.get().getGameYCoords().get(y), types.get(0).spritePath, true);
				}
				else if(rand >= 0 && rand < 1) {
					current = new HeadSprite(Head.DECEIVER, CoordinateSystem.get().getGameXCoords().get(x), CoordinateSystem.get().getGameYCoords().get(y), types.get(2).spritePath, true);
				}
				else {
					current = new HeadSprite(Head.INFLUENCER, CoordinateSystem.get().getGameXCoords().get(x), CoordinateSystem.get().getGameYCoords().get(y), types.get(1).spritePath, true);
				}
				if(y == CoordinateSystem.getSystemHeight()-1 && x == starterX) {
					current.status = 1; current.setColor(Color.ORANGE); 
				}
				GameProperties.get().addToActorGroup(current);
			}
		}
		
		GameProperties.get().getStage().addActor(GameProperties.get().getActorGroup());
		GameProperties.get().getStage().addActor(GameProperties.get().getSoundWaveGroup());
		
		setFollowerScreen();
	
	}
	
	private void setFollowerScreen() {	
		
		final ArrayList<MoveableSprite> followers = new ArrayList<MoveableSprite>();
		final ArrayList<Image> placeHolders = new ArrayList<Image>();
		
		final List<Follower> plFollowers = plState.getFollowers();
		List<FollowerType> types = plState.getFollowerTypes();
		
		for(int i = 0; i < types.size(); i++) {
			Image placeHolder = createTargetImage(types.get(i).spritePath+"Default.pack",CoordinateSystem.get().getHudXCoords().get(i), CoordinateSystem.get().getHudYCoords().get(i));
			placeHolders.add(placeHolder);
			for(Follower follower : plFollowers) {
				if(follower.type.head == types.get(i).head) {
					MoveableSprite followerInstance = new MoveableSprite(follower, CoordinateSystem.get().getHudXCoords().get(i), CoordinateSystem.get().getHudYCoords().get(i), placeHolder);
					followers.add(followerInstance);
				}
			}
		}
		
		final Actor btn = getButton("PlayGameBtn");
		setToStage(btn, 0, -260);
		
		btn.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				 activateGame(followers, placeHolders);
				 btn.remove();
			 }
		});
	}
	
	private Image createTargetImage(String framesPath, float origX, float origY) {
		Image targetImage = new Image(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
		targetImage.setColor(Color.LIGHT_GRAY);
		targetImage.setPosition(origX, origY);
		GameProperties.get().getStage().addActor(targetImage);
		targetImage.setTouchable(Touchable.disabled);
		
		return targetImage;
	}
	
	private void activateGame(List<MoveableSprite> followers, ArrayList<Image> placeHolders) {
		
		scoreState = new ScoreState(winAmount, winState, GameProperties.get().getActorGroup().getChildren().size);
		
		//Set dropped followers into game
		for(MoveableSprite follower : followers) {
			if(follower.isActive()) {
				GameProperties.get().replaceActorInGroup(follower);
			}
			else {
				//Remove remaining followers
				follower.getSourceSprite().remove();
			}
		}
		
		//Remove placeholders
		for(Image placeHolder : placeHolders) {
			placeHolder.remove();
		}
		
		//Activate crowd members
		for(Actor actor : GameProperties.get().getActorGroup().getChildren()) {
			((HeadSprite)actor).setBehaviour(manualInteraction);
		}
		
		//Set remaining votes icon
		setVoteCount();
		
	}
	
	private void setVoteCount() {
		final Skin skin = new Skin();
		BitmapFont font = new BitmapFont();
		font.scale(3.5f);
		skin.add("default", new LabelStyle(font, Color.YELLOW));
		String value = scoreState.getRemaingVotes() < 10 ? "0"+Integer.toString(scoreState.getRemaingVotes()) : Integer.toString(scoreState.getRemaingVotes());
		remainingVotesCounter = new Label(value, skin);
		setToStage(remainingVotesCounter, 160, 180);
	}
	
	private void updateScoreState() {
		scoreState.update();
		
		//Update remaining votes icon
		if(scoreState.getRemaingVotes() >= 0) {
			String value = scoreState.getRemaingVotes() < 10 ? "0"+Integer.toString(scoreState.getRemaingVotes()) : Integer.toString(scoreState.getRemaingVotes());
			remainingVotesCounter.setText(value);
		}
		
		if(scoreState.getCurrentState() == ScoreState.State.WIN) {
			setToStage(getImage("WinSprite", "sprites//textPack"), 0, 0);
		}
		else if(scoreState.getCurrentState() == ScoreState.State.LOSE) {
			setToStage(getImage("LoseSprite", "sprites//textPack"), 0, 0);
		}
		else if(scoreState.getCurrentState() == ScoreState.State.DRAW) {
			setToStage(getImage("DrawSprite", "sprites//textPack"), 0, 0);
		}
		else if(scoreState.getCurrentState() == ScoreState.State.FINISHED) {	
			setEndGameScreen();
		}
	}
	
	private void setEndGameScreen() {
		setToStage(getImage("EndScreen", "screens//screensPack"), 0, 0);
		
		setEndScoreValue();
		
		setFollowerRewards();
		
		Actor btn = getButton("PlayGameBtn");
		setToStage(btn, 0, -260);	
		btn.addListener(new ClickListener() {
			 public void clicked(InputEvent event, float x, float y) {
				disposeGame();
				setSpeechScreen();
			 }
		});
	}
	
	private void setEndScoreValue() {
		final Skin skin = new Skin();
		BitmapFont font = new BitmapFont();
		font.scale(3.5f);
		skin.add("default", new LabelStyle(font, Color.YELLOW));
		int score = plState.getReputationPoints() + scoreState.getEndScore();
		plState.setReputationPoints(score);
		String value = Integer.toString(score);
		remainingVotesCounter = new Label(value, skin);
		setToStage(remainingVotesCounter, 0, -70);
	}
	
	private void setFollowerRewards() {
		int rewardPoints = GameProperties.get().getRewardScore();
		int points = plState.getReputationPoints();
		if(points >= rewardPoints) {
			generateRewardFollowers(points / rewardPoints);
			plState.setReputationPoints(points % rewardPoints);
		}
	}
	
	private void generateRewardFollowers(int amount) {	
		
		List<Follower> rewardedFollowers = new ArrayList<Follower>();
		List<FollowerType> types = plState.getFollowerTypes();
		
		int count = amount > 3 ? 3 : amount;
		
		Random rand = new Random();
		for(int i =0; i < count; i++) {
			FollowerType type = types.get(rand.nextInt(types.size()));
			rewardedFollowers.add(new Follower(type.head, 0, type.spritePath));
		}
		
		plState.addFollowers(rewardedFollowers);
		
		setRewardFollowers(rewardedFollowers);
	}
	
	private void setRewardFollowers(List<Follower> rewardedFollowers) {
		
		for(int count = 0; count < rewardedFollowers.size(); count++) {
			setRewardImage(rewardedFollowers.get(count).type.spritePath, CoordinateSystem.get().getHudXCoords().get(count), CoordinateSystem.get().getHudYCoords().get(count));
		}
	}
	
	private void setRewardImage(String framesPath, float origX, float origY) {
		Image targetImage = new Image(new TextureAtlas(Gdx.files.internal(framesPath+"Default.pack")).getRegions().get(0));
		targetImage.setPosition(origX, origY);
		GameProperties.get().getStage().addActor(targetImage);
		targetImage.setTouchable(Touchable.disabled);
	}
	
	private void disposeGame() {
		scoreState = null;
		GameProperties.get().dispose();
		createNewGame();
	}


	public enum Head { GOSSIPER, DECEIVER, INFLUENCER}

	public enum Interact { SOUNDWAVE }
}
