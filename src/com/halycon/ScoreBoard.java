package com.halycon;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ScoreBoard {

	Score current;
	List<ScoreEntry> scores;
	Score score;
	private static final int NUM_SCORES = 6;
	
	public ScoreBoard() {
		current = new Score();
		this.score = new Score();
	}
	
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		this.score.init();
		current.init();
		loadScores();
	}
	
	public Score getScore() {
		return current;
	}
	
	private void loadScores() {
		this.scores = ScoreStorage.readScoresFile();
	}
	
	private void saveScores() {
		ScoreStorage.writeScoresFile(scores);
	}
	
	public void saveScore() {
		this.scores.add(new ScoreEntry("new", current.getNumber()));
		updateScores();
		saveScores();
	}
	
	private void updateScores() {
		Collections.sort(scores, new Comparator<ScoreEntry>() {

			@Override
			public int compare(ScoreEntry scoreA, ScoreEntry scoreB) {
				return scoreB.getValue().compareTo(scoreA.getValue());
			}
			
		});
		
		while (scores.size() > NUM_SCORES) {
			scores.remove(scores.size() - 1);
		}
		
	}
	
	public void resetScore() {
		current.setNumber(0);
	}
	
	public void render() {
		score.getPosition().y = 40;
		score.getPosition().x = 40;
		
		for (ScoreEntry scoreEntry : scores) {
			Integer scoreValue = scoreEntry.getValue();
			score.setNumber(scoreValue);
			score.render();
			score.getPosition().y = score.getPosition().y + 14;
		}
		
	}
}
