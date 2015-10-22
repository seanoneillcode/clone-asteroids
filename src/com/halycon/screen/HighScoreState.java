package com.halycon.screen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.halycon.ScoreBoard;

public class HighScoreState extends BasicGameState{
	int stateID = -1;

	Image image;
	String highScoresImageLocation = "res/screens/highScores.png";
	boolean enterLock;
	
	ScoreBoard scoreBoard;
	
	HighScoreState(int stateID, ScoreBoard scoreBoard) {
		this.stateID = stateID;
		this.scoreBoard = scoreBoard;
	}
	
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		this.image = new Image(this.highScoresImageLocation);
		this.image.setFilter(Image.FILTER_NEAREST);
		
//		scoreBoard.init(gc, arg1);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		image.draw(0, 0);

		
		scoreBoard.render();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_UP)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			sbg.enterState(AsteroidsGame.MAINMENUSTATE);
		}

	}
}
