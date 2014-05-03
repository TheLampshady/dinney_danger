package com.lampshady.dinnenyDanger;

import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;

import android.graphics.Color;
import android.graphics.Paint;

import com.lampshady.framework.Game;
import com.lampshady.framework.Graphics;
import com.lampshady.framework.Input.TouchEvent;
import com.lampshady.framework.Screen;

public class GameScreen extends Screen {
	public static final boolean DEBUG = SampleGame.debug;
	
	enum GameState {Ready, Running, Paused, GameOver}
	GameState state = GameState.Ready;
	
	//Objects: Loading backgroud, player and enemies
	private static Background bg1, bg2;
	private static Player player;
	public static ArrayList<Heliboy> heliboys;
	
	private GameMap level;
	private GamePad gamePad;
		
	
	public static int score;
	int livesLeft = 1;
	Paint paint, paint2;
	
	public GameScreen(Game game) {
		super(game);

		// Initialize game objects here
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		
		player = new Player(100,380);
		
		heliboys = new ArrayList<Heliboy>();

		gamePad = new GamePad();
		level = new GameMap();
		
		score = 0;

		//level.loadMap(SampleGame.map);
		level.loadMap();

		// Defining a paint object
		paint = getDefaultPaint(30);
		paint2 = getDefaultPaint(100);

	}
	
	
	
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		//Update States: Updates are executed based on game state
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	
	}
	
	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		
		gamePad.update(touchEvents);
		gamePad.updateState();
		
		if(gamePad.toPause()) pause();
				
		//Check miscellaneous events like death:
		if (livesLeft == 0) {
			state = GameState.GameOver;
		}
		
		//Update: Background
		bg1.update();
		bg2.update();
					
		//Update: Player
		player.update();

		//Update: Enemy Array
		for (int i=0; i<heliboys.size();i++)
			if (!heliboys.get(i).isVisible()) {
				heliboys.remove(i);
				GameScreen.score += 5;
			}else
				heliboys.get(i).update();
		
		
		//Update: Tiles
		level.update();
			

		if (player.getCenterY() > 500) {
			state = GameState.GameOver;
		}
	}// updateRunning

	
	private void updateReady(List<TouchEvent> touchEvents) {

		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!

		if (touchEvents.size() > 0)
			state = GameState.Running;
	}//updateReady
	
	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 0, 0, 800, 240)) {

					if (!inBounds(event, 0, 0, 35, 35)) {
						resume();
					}
				}

				if (inBounds(event, 0, 240, 800, 240)) {
					nullify();
					goToMenu();
				}
			}
		}
		
	}//updatePaused
	
	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (inBounds(event, 0, 0, 800, 480)) {
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}

	}//updateGameOver
	
	
	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
		g.drawImage(Assets.background, bg2.getBgX(), bg2.getBgY());
					
		//Draw Level
		level.paint(g);
			
		//Draw: Player
		player.draw(g);
		
		//Draw: Enemies
		for (Heliboy hb : heliboys)
			hb.draw(g);

		// Secondly, draw the UI above the game elements.
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

	}
		

	private void nullify() {

		// Set all variables to null. You will be recreating them in the constructor.
				
		paint = null;
		bg1 = null;
		bg2 = null;

		//Clean Memory: Player
		player.nullify();
		player = null;
		
		//Clean Memory: Enemies
		for (Heliboy hb : heliboys){
			hb.nullify();
			hb=null;
		}
		heliboys=null;

		//TODO Nullify Tiles
		level=null;
		// Call garbage collector to clean up memory.
		System.gc();

	}



	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawARGB(155, 0, 0, 0);
		g.drawString("Tap to Start.", 400, 240, paint);

	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		gamePad.draw(g);
	}
	
	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 400, 165, paint2);
		g.drawString("Menu", 400, 360, paint2);

	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER.", 400, 240, paint2);
		g.drawString("Tap to return.", 400, 290, paint);

	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;

	}

	@Override
	public void resume() {
		if (state == GameState.Paused)
			state = GameState.Running;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

	private void goToMenu() {
		game.setScreen(new MainMenuScreen(game));

	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}
	
	private Paint getDefaultPaint(int size){
		Paint p = new Paint();
		p.setTextSize(size);
		p.setTextAlign(Paint.Align.CENTER);
		p.setAntiAlias(true);
		p.setColor(Color.WHITE);
		return p;
	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static Player getPlayer() {
		return player;
	}

}
