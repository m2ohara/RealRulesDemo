package com.realrules.touch;

import com.realrules.game.demo.CoordinateSystem;
import com.realrules.game.demo.CoordinateSystem.Coordinates;

public abstract class TouchAction implements ITouchAction {
	
	private int interactorX;
	private int interactorY;
	private Coordinates interactorDir;

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

	public Coordinates getInteractorDir() {
		return interactorDir;
	}

	public void setInteractorDir(Coordinates interactorDir) {
		this.interactorDir = interactorDir;
	}

}
