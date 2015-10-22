package com.halycon.screen;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.halycon.ScoreBoard;

public class GameOverState extends BasicGameState{
	int stateID = -1;

	Image image;
	String gameOverImageLocation = "res/screens/gameOver.png";
	ScoreBoard scoreBoard;
	
	GameOverState(int stateID, ScoreBoard scoreBoard) {
		this.stateID = stateID;
		this.scoreBoard = scoreBoard;
	}
	
	public int getID() {
		return stateID;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		scoreBoard.getScore().setPosition(new Vector3f(60,40,0));
		scoreBoard.saveScore();
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.image = new Image(this.gameOverImageLocation);
		this.image.setFilter(Image.FILTER_NEAREST);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		image.draw(0, 0);
		scoreBoard.getScore().render();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		Input input = gc.getInput();
		
		if (input.isKeyDown(Input.KEY_UP)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}
		if (input.isKeyDown(Input.KEY_ENTER)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}

	}
}
