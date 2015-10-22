package com.halycon.screen;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.halycon.AsteroidType;
import com.halycon.Constants;
import com.halycon.EntityState;
import com.halycon.ScoreBoard;
import com.halycon.drawable.Explosion;
import com.halycon.entity.Alien;
import com.halycon.entity.Asteroid;
import com.halycon.entity.Bullet;
import com.halycon.entity.Entity;
import com.halycon.entity.Heart;
import com.halycon.entity.Ship;



public class GamePlayState extends BasicGameState{

	int stateID = -1;
	
	
	Ship ship;
	LinkedList<Bullet> bullets;
	LinkedList<Asteroid> asteroids;
	LinkedList<Asteroid> newAsteroids;
	LinkedList<Explosion> explosions;
	LinkedList<Entity> pickups;
	
	Random random;
	
	ScoreBoard scoreBoard;
	Image lifeImage;
	
	Alien alien;
	Bullet alienBullet;
	public static Audio alienEntry;
	
	boolean isNewGame;
	
	static float SCREEN_ANGLE = 0.0f;
	static float AIM_SCREEN_ANGLE = 0.0f;
	static float shakeIndex = 0.0f;
	private static final int CHANCE_OF_HEART = 40;
	
	public GamePlayState(int stateID, ScoreBoard scoreBoard) throws SlickException {
		this.stateID = stateID;
		this.scoreBoard = scoreBoard;
		try {
			alienEntry = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/music/sounds/alien_entry0.ogg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void shakeScreen() {
		SCREEN_ANGLE = 0.0f;
		AIM_SCREEN_ANGLE = -2.0f;
		shakeIndex = 0;
	}
	
	@Override
	public int getID() {
		return stateID;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		newGame();
		ship.init();
		scoreBoard.init(arg0, arg1);
		lifeImage = new Image("res/elements/ship5.png");
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		scoreBoard.getScore().setPosition(new Vector3f(1,1,0));
		scoreBoard.resetScore();
	}
	
	private void newGame() throws SlickException {
		random = new Random();
		ship = new Ship();
		ship.setPosition(new Vector3f(80,72,0));
		bullets = new LinkedList<Bullet>();
		asteroids = new LinkedList<Asteroid>();
		newAsteroids = new LinkedList<Asteroid>();
		explosions = new LinkedList<Explosion>();
		pickups = new LinkedList<Entity>();
		generateAsteroids();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.pushTransform();
		g.rotate(ship.getPosition().x, ship.getPosition().y, SCREEN_ANGLE);
		ship.render(g);
		
		for (Asteroid asteroid : asteroids) {
			asteroid.render(g);
		}
		for (Explosion explosion : explosions) {
			explosion.render(g);
		}
		for (Entity entity : pickups) {
			entity.render(g);
		}
		scoreBoard.getScore().render();
		renderLives(new Vector3f(1, 10, 0), g);
		
		if (alien != null) {
			alien.render(g);
		}
		if (alienBullet != null) {
			alienBullet.render(g);
		}
		for (Bullet bullet : bullets) {
			bullet.render(g);
		}
		g.popTransform();
	}

	private void renderLives(Vector3f position, Graphics g) {
		for (int i = 0; i < ship.getLives(); ++i) {
			lifeImage.draw(position.x, position.y, 8, 10);
			position.x += 6;
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		handleInput(gc, sbg, ship);
		
		ship.update(delta);
		handleEdgeCollision(ship);

		float shakeChange = delta * 0.1f;
		
		if (Math.abs(AIM_SCREEN_ANGLE) < shakeChange) {
			AIM_SCREEN_ANGLE = SCREEN_ANGLE = 0.0f;
		}
		
		if (SCREEN_ANGLE > AIM_SCREEN_ANGLE) {
			SCREEN_ANGLE -= shakeChange;
			if (shakeIndex == 1) {
				shakeIndex = 0;
				AIM_SCREEN_ANGLE = -AIM_SCREEN_ANGLE * 0.9f;
			}
		} else {
			SCREEN_ANGLE += shakeChange;
			if (shakeIndex == 0) {
				shakeIndex = 1;
				AIM_SCREEN_ANGLE = -AIM_SCREEN_ANGLE * 0.9f;
			}
		}
		
		updateList(bullets, delta);
		updateList(asteroids, delta);
		updateList(pickups, delta);
		for (Explosion explosion : explosions) {
			explosion.update(delta);
		}
		Iterator<Explosion> explosionIterator = explosions.iterator();
		while (explosionIterator.hasNext()) {
			if (explosionIterator.next().getState() == EntityState.DEAD) {
				explosionIterator.remove();
			}
		}
		
		handleEntityCollision();
		if (newAsteroids.size() > 0) {
			asteroids.addAll(newAsteroids);
			newAsteroids.clear();
		}	
		
		if (asteroids.size() == 0) {
			generateAsteroids();
		}
		updateAlien(delta);
		
		scoreBoard.getScore().update();
		if (ship.getLives() < 0) {
			sbg.enterState(AsteroidsGame.GAMEOVERSTATE);
		}
	}
	
	private void updateAlien(int delta) throws SlickException {
		if (alien == null) {
			if (random.nextInt(1000) == 1) {
				alien = generateAlien();
			}
		} else {
			alien.update(delta, ship.getPosition());
			if (alien.getPosition().x < -10 || alien.getState() == EntityState.DEAD) {
				alien = null;
			}
		}
		if (alienBullet == null) {
			if (alien != null && random.nextInt(500) == 1) {
				alienBullet = new Bullet();
				alienBullet.init();
				alien.shootBullet(alienBullet);
			}
		} else {
			alienBullet.update(delta);
			if (alienBullet.getState() == EntityState.DEAD) {
				alienBullet = null;
			}
		}
	}
	
	private Alien generateAlien() throws SlickException {
		Alien returnAlien = new Alien();
		returnAlien.setMovement(new Vector3f(-0.2f,0,0));
		returnAlien.setPosition(new Vector3f(Constants.LEVEL_WIDTH, random.nextFloat() * Constants.LEVEL_HEIGHT, 0));
		returnAlien.init();
		returnAlien.setRadius(5);
		alienEntry.playAsSoundEffect(1.0f, 0.8f, false);
		return returnAlien;
	}
	
	private void handleEntityCollision() throws SlickException {
		for (Asteroid asteroid : asteroids) {
			if (asteroid.getState() == EntityState.ALIVE && ship.isCollidable() && isColliding(asteroid, ship)) {
				destroyAsteroid(asteroid);
				ship.takeALife();
			}
			for (Bullet bullet : bullets) {
				if (asteroid.getState() == EntityState.ALIVE && isColliding(bullet, asteroid)) {
					bullet.getHit();
					destroyAsteroid(asteroid);
				}
				if (alien != null && alien.getState() == EntityState.ALIVE && isColliding(bullet, alien)) {
					bullet.getHit();
					explosions.add(new Explosion(alien.getPosition()));
					alien.getHit();
					scoreBoard.getScore().increaseNumberBy(Constants.ALIEN_SHIP_SCORE_VALUE);
				}
			}
			if (alienBullet != null) {
				if (asteroid.getState() == EntityState.ALIVE && isColliding(alienBullet, asteroid)) {
					alienBullet = null;
					destroyAsteroid(asteroid);
				}
			}
		}
		if (alienBullet != null) {
			if (ship.isCollidable() && isColliding(ship, alienBullet)) {
				ship.takeALife();
				alienBullet = null;
			}
		}
		for (Entity entity : pickups) {
			if (entity.getState() == EntityState.ALIVE && ship.isCollidable() && isColliding(entity, ship)) {
				if (entity instanceof Heart) {
					Heart heart = (Heart)entity;
					heart.destroy();
					ship.addALife();
				}
			}
		}
	}

	private void destroyAsteroid(Asteroid asteroid) throws SlickException {
		if (asteroid.getType() == AsteroidType.LARGE) {
			newAsteroids.add(buildAsteroid(asteroid.getPosition(), AsteroidType.MEDIUM));
			newAsteroids.add(buildAsteroid(asteroid.getPosition(), AsteroidType.MEDIUM));
		}
		if (asteroid.getType() == AsteroidType.MEDIUM) {
			newAsteroids.add(buildAsteroid(asteroid.getPosition(), AsteroidType.SMALL));
			newAsteroids.add(buildAsteroid(asteroid.getPosition(), AsteroidType.SMALL));
		}
		asteroid.destroy();
		scoreBoard.getScore().increaseNumberBy(asteroid.getType().getValue());
		explosions.add(new Explosion(asteroid.getPosition()));
		if (random.nextInt(CHANCE_OF_HEART) == 1) {
			pickups.add(buildHeart(asteroid.getPosition()));
		}
	}
	
	private Heart buildHeart(Vector3f position) throws SlickException {
		Heart heart = new Heart();
		heart.setPosition(new Vector3f(position));
		float x = (0.5f - random.nextFloat()) * Constants.ASTEROID_SPEED;
		float y = (0.5f - random.nextFloat()) * Constants.ASTEROID_SPEED;
		heart.setMovement(new Vector3f(x * 2, y * 2, 0));
		heart.init();
		return heart;
	}
	
	private Asteroid buildAsteroid(Vector3f position, AsteroidType type) throws SlickException {
		Asteroid returnAsteroid = new Asteroid(type);
		returnAsteroid.setPosition(new Vector3f(position));
		float x = (0.5f - random.nextFloat()) * Constants.ASTEROID_SPEED;
		float y = (0.5f - random.nextFloat()) * Constants.ASTEROID_SPEED;
		returnAsteroid.setMovement(new Vector3f(x * 2, y * 2, 0));
		returnAsteroid.init();
		returnAsteroid.setRotation(random.nextDouble() * 360);
		return returnAsteroid;
	}
	
	private boolean isColliding(Entity e1, Entity e2) {
		boolean returnValue = false;
		Vector3f p1 = e1.getPosition();
		Vector3f p2 = e2.getPosition();
		float distance = e1.getRadius() + e2.getRadius();
		if ((p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y) < (distance * 3)) {
			returnValue = true;
		}
		return returnValue;
	}
	
	private <T> void updateList(LinkedList<T> list, int delta) {
		for(T item : list) {
			handleEdgeCollision(((Entity)item));	
			((Entity)item).update(delta);
		}
		Iterator<T> iterator = list.descendingIterator();
		T item;
		while (iterator.hasNext()) {
			item = iterator.next();
			if (((Entity)item).getState() == EntityState.DEAD) {
				iterator.remove();
			}
		}		
	}
	
	private void handleEdgeCollision(Entity entity) {
		Vector3f position = entity.getPosition();
		
		if (position.x < 0) {
			position.setX(Constants.LEVEL_WIDTH);
		}
		if (position.x > Constants.LEVEL_WIDTH) {
			position.setX(0);
		}
		if (position.y < 0) {
			position.setY(Constants.LEVEL_HEIGHT);
		}
		if (position.y > Constants.LEVEL_HEIGHT) {
			position.setY(0);
		}
		entity.setPosition(position);
	}
	
	private void handleInput(GameContainer gc, StateBasedGame sbg, Ship ship) throws SlickException {
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			sbg.enterState(AsteroidsGame.PAUSESTATE);
		}
		if (input.isKeyDown(Input.KEY_LEFT) || input.isControllerLeft(Input.ANY_CONTROLLER)) {
			ship.rotateLeft();
		}
		if (input.isKeyDown(Input.KEY_RIGHT) || input.isControllerRight(Input.ANY_CONTROLLER)) {
			ship.rotateRight();
		}
		if (input.isKeyDown(Input.KEY_UP) || input.isButton1Pressed(Input.ANY_CONTROLLER)) {
			ship.goForward();
		}
		if (input.isKeyDown(Input.KEY_SPACE) || input.isButton3Pressed(Input.ANY_CONTROLLER)) {
			if (ship.canShoot()) {
				bullets.add(ship.shootBullet());
			}
		}
	}
	
	private void generateAsteroids() throws SlickException {
		for (int i = 0; i < Constants.INITIAL_NUMBER_OF_ASTEROIDS; ++i) {
			Asteroid asteroid = new Asteroid(AsteroidType.LARGE);
			
			Vector3f position = new Vector3f(random.nextFloat() * Constants.LEVEL_WIDTH, random.nextFloat() * Constants.LEVEL_HEIGHT, 0);
			asteroid.setPosition(position);
			
			float x = (0.5f - random.nextFloat()) * Constants.ASTEROID_SPEED;
			float y = (0.5f - random.nextFloat()) * Constants.ASTEROID_SPEED;
			Vector3f movement = new Vector3f(x, y, 0);
			asteroid.setMovement(movement);
			
			asteroid.init();
			
			asteroid.setRotation(random.nextDouble() * 360);
			asteroids.add(asteroid);
		}
	}
}
