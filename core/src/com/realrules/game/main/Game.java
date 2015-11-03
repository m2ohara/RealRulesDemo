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
import com.realrules.game.interact.IInteractionType;
import com.realrules.game.interact.OpposerInteractionType;
import com.realrules.game.interact.SupporterInteractionType;
import com.realrules.game.main.ScoreState.State;
import com.realrules.game.setup.GameGenerator;
import com.realrules.game.state.Follower;
import com.realrules.game.state.FollowerType;
import com.realrules.game.state.PlayerState;

public class Game extends ApplicationAdapter {
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
	IInteractionType manualInteraction = null;
	Label remainingVotesCounter = null;
	Label endScoreCounter = null;
	boolean isAssetsLoaded = false;
	
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
		
		if(!isAssetsLoaded) { loadAssets();}
		
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
	
	private void loadAssets() {
		
		if(!Assets.get().isLoaded()) {
			Assets.get().load();
		}
		else {
			setPlayButton();
			isAssetsLoaded = true;
		}
	}
	
	private void setTitleScreen() {
		
		setToStage(getImage("TitleScreen", "screens//screensPack"), 0, 0);
		Actor btn = getButton("LoadingBtn");
		setToStage(btn, 0, -260);
		
	}
	
	private void setPlayButton() {
		
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
		
		if(Assets.get().isLoaded()) {
			setToStage(getImage("SpeechScreen", "screens//screensPack"), 0, 0);
			Actor btn = getButton("CreateSpeechBtn");
			setToStage(btn, 0, -260);
			
			btn.addListener(new ClickListener() {
				 public void clicked(InputEvent event, float x, float y) {
					 displaySpeechScroll();
				 }
			});
		}
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
			manualInteraction = new SupporterInteractionType();
		}
		else {
			voteType = "DEFEAT";
			winState = State.LOSE;
			manualInteraction = new OpposerInteractionType();
		}
		
		
		int amount = WorldSystem.getSystemWidth() * WorldSystem.getSystemHeight();
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
		
		new GameGenerator().populateFullCrowdScreen();
		
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
			Image placeHolder = createTargetImage(types.get(i).directoryPath+"Default.pack",WorldSystem.get().getHudXCoords().get(i), WorldSystem.get().getHudYCoords().get(i));
			placeHolders.add(placeHolder);
			for(Follower follower : plFollowers) {
				if(follower.type.head == types.get(i).head) {
					MoveableSprite followerInstance = new MoveableSprite(follower, WorldSystem.get().getHudXCoords().get(i), WorldSystem.get().getHudYCoords().get(i), placeHolder);
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
		targetImage.setColor(Color.CYAN);
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
			((GameSprite)actor).setBehaviour(manualInteraction);
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
			Actor image = getImage("WinSprite", "sprites//textPack");
			setScoreStateSprite(image);
		}
		else if(scoreState.getCurrentState() == ScoreState.State.LOSE) {
			Actor image = getImage("LoseSprite", "sprites//textPack");
			setScoreStateSprite(image);
		}
		else if(scoreState.getCurrentState() == ScoreState.State.DRAW) {
			Actor image = getImage("DrawSprite", "sprites//textPack");
			setScoreStateSprite(image);

		}
		else if(scoreState.getCurrentState() == ScoreState.State.FINISHED) {	
			setEndGameScreen();
		}
	}
	
	private void setScoreStateSprite(Actor image) {
		image.setOriginX(image.getHeight()/2);
		image.setOriginY(image.getWidth()/2);
		image.scaleBy(-0.5f);
		setToStage(image, -50, -130);
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
			rewardedFollowers.add(new Follower(type.head, 0, type.directoryPath+"Default.pack"));
		}
		
		plState.addFollowers(rewardedFollowers);
		
		setRewardFollowers(rewardedFollowers);
	}
	
	private void setRewardFollowers(List<Follower> rewardedFollowers) {
		
		for(int count = 0; count < rewardedFollowers.size(); count++) {
			setRewardImage(rewardedFollowers.get(count).type.directoryPath, WorldSystem.get().getHudXCoords().get(count), WorldSystem.get().getHudYCoords().get(count));
		}
	}
	
	private void setRewardImage(String framesPath, float origX, float origY) {
		Image targetImage = new Image(new TextureAtlas(Gdx.files.internal(framesPath)).getRegions().get(0));
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
