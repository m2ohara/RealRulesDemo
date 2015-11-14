package com.realrules.game.touch;

import com.realrules.game.main.WorldSystem;
import com.realrules.game.main.WorldSystem.Orientation;

public abstract class TouchAction implements ITouchAction {
	
	private int interactorX;
	private int interactorY;
	private Orientation interactorDir;

	protected abstract void generateValidInteractees();

	public int getInteractorX() {
		return interactorX;
	}

	public void setInteractorX(int interactorX) {
		this.interactorX = interactorX;
	}

	public int getInteractorY() {
		return interactorY;
	}

	public void setInteractorY(int interactorY) {
		this.interactorY = interactorY;
	}

	public Orientation getInteractorDir() {
		return interactorDir;
	}

	public void setInteractorDir(Orientation interactorDir) {
		this.interactorDir = interactorDir;
	}
	
	public boolean isValidInteractor() {
		if(WorldSystem.get().getMemberFromCoords(getInteractorX(), getInteractorY()).status == 1) {;
			return true;
		}
		return false;
	}

}
