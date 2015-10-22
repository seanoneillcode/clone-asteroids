package com.halycon.screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MainMenuState extends BasicGameState {
	
	int stateID = -1;
	int selectedMenuOption = 0;;
	Image background, cursor;
	String backgroundLocation = "res/screens/menuBackground.png";
	String cursorLocation = "res/screens/menuSelector.png";
	int cursorX = 48;
	int cursorY = 46;
	boolean enterLock, upLock, downLock;
	
	MainMenuState( int stateID ) {
		this.stateID = stateID;
	}
	
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		this.background = new Image(this.backgroundLocation);
		this.background.setFilter(Image.FILTER_NEAREST);
		this.cursor = new Image(this.cursorLocation);
		this.cursor.setFilter(Image.FILTER_NEAREST);
		enterLock = upLock = downLock = true;
		gc.getInput().disableKeyRepeat();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw(0, 0);
		cursor.draw(cursorX, cursorY);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_UP))
		{
			selectedMenuOption -= 1;
		}
		if(input.isKeyPressed(Input.KEY_DOWN))
		{
			selectedMenuOption += 1;
		}
		if (selectedMenuOption < 0) {
			selectedMenuOption = 0;
		}
		if (selectedMenuOption > 2) {
			selectedMenuOption = 2;
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			if (!enterLock) {
				if (selectedMenuOption == 0) {
					enterLock = true;
					sbg.getState(AsteroidsGame.GAMEPLAYSTATE).init(gc, sbg);
					sbg.enterState(AsteroidsGame.GAMEPLAYSTATE);
				}
				if (selectedMenuOption == 1) {
					gc.exit();
				}
				if (selectedMenuOption == 2) {
					sbg.enterState(AsteroidsGame.HIGHSCORESTATE);
				}
			}
		} else {
			enterLock = false;
		}
		if (selectedMenuOption == 0){
			cursorY = 47;
		}
		if (selectedMenuOption == 1){
			cursorY = 66;
		}
		if (selectedMenuOption == 2){
			cursorY = 84;
		}
	}
	
	
}
