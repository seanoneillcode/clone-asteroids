package com.halycon.screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PauseState extends BasicGameState{
	int stateID = -1;
	int selectedMenuOption = 0;;
	Image background, cursor;
	String backgroundLocation = "res/screens/pauseBackground.png";
	String cursorLocation = "res/screens/menuSelector.png";
	int cursorX = 45;
	int cursorY = 67;
	boolean enterLock;
	
	public PauseState(int stateID) {
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
		enterLock = true;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw(0, 0);
		cursor.draw(cursorX, cursorY);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_UP))
		{
			selectedMenuOption -= 1;
		}
		if(input.isKeyDown(Input.KEY_DOWN))
		{
			selectedMenuOption += 1;
		}
		if (selectedMenuOption < 0) {
			selectedMenuOption = 0;
		}
		if (selectedMenuOption > 1) {
			selectedMenuOption = 1;
		}
		if (input.isKeyDown(Input.KEY_ENTER)) {
			if (!enterLock) {
				if (selectedMenuOption == 0) {
					enterLock = true;
					selectedMenuOption = 0;
					sbg.enterState(AsteroidsGame.GAMEPLAYSTATE);
				}
				if (selectedMenuOption == 1) {
					enterLock = true;
					selectedMenuOption = 0;
					sbg.enterState(AsteroidsGame.MAINMENUSTATE);
				}
			}
		} else {
			enterLock = false;
		}
		if (selectedMenuOption == 0){
			cursorY = 57;
		}
		if (selectedMenuOption == 1){
			cursorY = 76;
		}
	}
	
}
