package com.realrules.game.main;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Assets {

	private List<String> gameSpriteFilePaths = null;
	
	public Assets() {
		assetManager = new AssetManager();
		gameSpriteFilePaths = Arrays.asList("", "", "");
	}
	
	private AssetManager assetManager = null;
	public AssetManager getAssetManager() {
		return assetManager;
	}
	
	public void load() {
		for(String filePath : gameSpriteFilePaths) {
			assetManager.load(filePath, AtlasRegion.class);
		}
	}

}
