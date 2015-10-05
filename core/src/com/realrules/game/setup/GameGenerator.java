package com.realrules.game.setup;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.realrules.game.main.DemoGame.Head;
import com.realrules.game.main.GameProperties;
import com.realrules.game.main.HeadSprite;
import com.realrules.game.main.WorldSystem;
import com.realrules.game.state.FollowerType;
import com.realrules.game.state.PlayerState;

public class GameGenerator {
	
	public float skipPlacementProb;
	public Random rand;
	
	public GameGenerator() {
		rand = new Random();
		skipPlacementProb = ((float)(PlayerState.get().getLevel())/10);
	}

	
	public void populateCrowdScreen() {
		
//		Actor screen = getImage("GameScreen", "screens//screensPack");
//		screen.setTouchable(Touchable.disabled);
//		setToStage(screen, 0, 0);
		
		
		List<FollowerType> types = PlayerState.get().getFollowerTypes();
		Random crowdSetter = new Random();
		int starterX = crowdSetter.nextInt(WorldSystem.getSystemWidth()-1);
		for(int x = 0; x < WorldSystem.getSystemWidth(); x++) {
			for(int y = 0; y < WorldSystem.getSystemHeight(); y++) {
				
				if(!(rand.nextFloat() < skipPlacementProb)) {
				
					HeadSprite current = null;
					float rand = crowdSetter.nextFloat();
					if(rand < 0.33) {
						current = new HeadSprite(Head.GOSSIPER, WorldSystem.get().getGameXCoords().get(x), WorldSystem.get().getGameYCoords().get(y), types.get(0).directoryPath, true);
					}
					else if(rand >= 0.33 && rand < 0.66) {
						current = new HeadSprite(Head.INFLUENCER, WorldSystem.get().getGameXCoords().get(x), WorldSystem.get().getGameYCoords().get(y), types.get(1).directoryPath, true);
					}
					else {
						current = new HeadSprite(Head.DECEIVER, WorldSystem.get().getGameXCoords().get(x), WorldSystem.get().getGameYCoords().get(y), types.get(2).directoryPath, true);
					}
					if(y == WorldSystem.getSystemHeight()-1 && x == starterX) {
						current.status = 1; current.setColor(Color.ORANGE); 
					}
					GameProperties.get().addToActorGroup(current);
				}
			}
		}
		
		GameProperties.get().getStage().addActor(GameProperties.get().getActorGroup());
		GameProperties.get().getStage().addActor(GameProperties.get().getSoundWaveGroup());
		
//		setFollowerScreen();
	
	}
	
	public void populateFullCrowdScreen() {
		
		List<FollowerType> types = PlayerState.get().getFollowerTypes();
		Random crowdSetter = new Random();
		int starterX = crowdSetter.nextInt(WorldSystem.getSystemWidth()-1);
		for(int x = 0; x < WorldSystem.getSystemWidth(); x++) {
			for(int y = 0; y < WorldSystem.getSystemHeight(); y++) {
				HeadSprite current = null;
				float rand = crowdSetter.nextFloat();
				if(rand < 0.33) {
					current = new HeadSprite(Head.GOSSIPER, WorldSystem.get().getGameXCoords().get(x), WorldSystem.get().getGameYCoords().get(y), types.get(0).directoryPath, true);
				}
				else if(rand >= 0.33 && rand < 0.66) {
					current = new HeadSprite(Head.INFLUENCER, WorldSystem.get().getGameXCoords().get(x), WorldSystem.get().getGameYCoords().get(y), types.get(1).directoryPath, true);
				}
				else {
					current = new HeadSprite(Head.DECEIVER, WorldSystem.get().getGameXCoords().get(x), WorldSystem.get().getGameYCoords().get(y), types.get(2).directoryPath, true);
				}
				if(y == WorldSystem.getSystemHeight()-1 && x == starterX) {
					current.status = 1; current.setColor(Color.ORANGE); 
				}
				GameProperties.get().addToActorGroup(current);
			}
		}
	}

}
