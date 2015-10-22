package com.halycon.screen;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.halycon.ScoreBoard;




public class AsteroidsGame extends StateBasedGame{

	public static final int STARTSPLASHSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int GAMEPLAYSTATE = 2;
	public static final int PAUSESTATE = 3;
	public static final int GAMEOVERSTATE = 4;
	public static final int HIGHSCORESTATE = 5;
	
	public static ScoreBoard scoreBoard;
	
	public AsteroidsGame() {
		super("Asteroids");
		scoreBoard = new ScoreBoard();
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new StartSplashState(STARTSPLASHSTATE));
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GamePlayState(GAMEPLAYSTATE, scoreBoard));
		this.addState(new PauseState(PAUSESTATE));
		this.addState(new GameOverState(GAMEOVERSTATE, scoreBoard));
		this.addState(new HighScoreState(HIGHSCORESTATE, scoreBoard));
	}
	
	public static void main (String[] args) throws SlickException {
		 AppGameContainer app = new AppGameContainer(new ScalableGame(new AsteroidsGame(),160,144));
		 app.setDisplayMode(1024, 768, false);
		 app.setMaximumLogicUpdateInterval(10);
		 app.setTargetFrameRate(50);
		 app.setVSync(false);
		 app.setShowFPS(false);
		 app.setMouseGrabbed(true);
		 app.start();
	}
	
}
