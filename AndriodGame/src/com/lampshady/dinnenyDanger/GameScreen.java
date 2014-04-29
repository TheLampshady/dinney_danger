package com.lampshady.dinnenyDanger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	public static ArrayList<Button> buttons;
	
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();
	
	public static int score;
	int livesLeft = 1;
	Paint paint, paint2;
	
	public GameScreen(Game game) {
		super(game);

		// Initialize game objects here
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		
		player = new Player();
		
		heliboys = new ArrayList<Heliboy>();
		heliboys.add(new Heliboy(340, 360));
		heliboys.add(new Heliboy(700, 360));

		/*	1-Jump
		 *	2-Duck
		 *	3-Pause		
		 *	4-Left
		 *	5-Right		
		 *	6-Shoot
		 */
		buttons = new ArrayList<Button>();
		buttons.add(new Button(1, 	Button.bgRight, Button.bgBot-Button.len*2,0,0,Button.len,Button.len));
		buttons.add(new Button(2,	Button.bgRight-Button.len, Button.bgBot-Button.len,0,Button.len*2,Button.len,Button.len));
		buttons.add(new Button(3,	0, 0, 0, Button.len*3, 35, 35));
		buttons.add(new Button(4,	0, Button.bgBot-Button.len, 0, Button.len*4, Button.len, Button.len));
		buttons.add(new Button(5,	Button.len*2, Button.bgBot-Button.len, 0, Button.len*5, Button.len, Button.len));
		buttons.add(new Button(6,	Button.bgRight, Button.bgBot, 0, Button.len, Button.len, Button.len));

		
		score = 0;

		loadMap(SampleGame.map);

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		paint2 = new Paint();
		paint2.setTextSize(100);
		paint2.setTextAlign(Paint.Align.CENTER);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.WHITE);
	}
	
	private void loadMap(String map) {
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;

		Scanner scanner = new Scanner(map);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			//End of File
			if (line == null) {
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());

			}
		}
		scanner.close();

		for (int j = 0; j < 12; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {

				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}

			}
		}
	}
	private void loadMap() {
		
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
		Button duck = buttons.get(2);
		Button left = buttons.get(4);
		Button right = buttons.get(5);
		
		int len = touchEvents.size();
		int action=0;
		
		for (int i = 0; i < len; i++) {
			TouchEvent event = (TouchEvent)touchEvents.get(i);
			
			//Button Released
			if (event.type == TouchEvent.TOUCH_UP) {
				for(Button b : buttons)
					if(b.inBounds(event))action = b.getAction();
							
				switch (action) {
				
				case 2:	//Button Release: Duck
					player.setDucked(false);
					break;
				case 3:	//Button Release: Pause
					pause();
					break;
				case 4:	//Button Release: Left
					player.setMovingLeft(false);
					break;

				case 5:	//Button Release: Right
					player.setMovingRight(false);
					break;

				}
			}
			
			//Button Pressed
			if (event.type == TouchEvent.TOUCH_DOWN) {

				for(Button b : buttons)
					if(b.inBounds(event))action = b.getAction();
				
				//Actions for pushing button
				switch (action) {
				case 1:	//Button Pressed: Jump
					player.jump();
					player.setDucked(false);
					break;
					
				case 2:	//Button Pressed: Duck
					if(player.isJumped() == false){
						player.setDucked(true);
						duck.setTouchID(event.pointer);
					}
					break;	
					
				case 4:	//Button Pressed: Left
					player.setMovingLeft(true);
					left.setTouchID(event.pointer);
					break;
				
				case 5:	//Button Pressed: Right
					player.setMovingRight(true);
					right.setTouchID(event.pointer);
					break;	
	
				case 6:	//Button Pressed: Shoot
					if (player.isDucked() == false && player.isJumped() == false && player.isReadyToFire())
						player.shoot();
					break;
					
				}

			}
			
			if (event.type == TouchEvent.TOUCH_DRAGGED) {
								
				duck = buttons.get(2);
				if((event.pointer == duck.getTouchID()) && (!duck.inBounds(event))){
					player.setMovingRight(false);
					duck.setTouchID(-1);
				}
				
				left = buttons.get(4);
				if((event.pointer == left.getTouchID()) && (!left.inBounds(event))){
					player.setMovingLeft(false);
					left.setTouchID(-1);
				}
				
				right = buttons.get(5);
				if((event.pointer == right.getTouchID()) && (!right.inBounds(event))){
					player.setMovingRight(false);
					right.setTouchID(-1);
				}
				
				
				for(Button b : buttons)
					if(b.inBounds(event))action = b.getAction();
				
				switch (action) {
				case 2:	//Button Dragged: Duck
					if(player.isJumped() == false){
						player.setDucked(true);
						duck.setTouchID(event.pointer);
					}
					break;
				case 4:	//Button Dragged: Left
					player.setMovingLeft(true);
					left.setTouchID(event.pointer);
					break;
				case 5:	//Button Dragged: Right
					player.setMovingRight(true);
					right.setTouchID(event.pointer);
					break;

				}

			}

		}// For Loop
		

		//Check Button State: Duck Button
		if(player.isDucked())
			player.setSpeedX(0);
		
		//Check Button State: Left Button
		if(player.isMovingLeft())
			player.moveLeft();
		else
			player.stopLeft();
				
		//Check Button State: Right Button
		if(player.isMovingRight())
			player.moveRight();
		else
			player.stopRight();
		
		
		//Check miscellaneous events like death:
		if (livesLeft == 0) {
			state = GameState.GameOver;
		}
		
		//Update: Background
		bg1.update();
		bg2.update();
					
		//Update: Player
		player.update();

		//Update: Tiles
		updateTiles();
		
		ArrayList<Projectile> projectiles = player.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			if (p.isVisible() == true) {
				p.update();
			} else {
				projectiles.remove(i);
			}
		}

		//Update: Enemy Array
		for (Heliboy hb : heliboys)
			hb.update();
	

		if (player.getCenterY() > 500) {
			state = GameState.GameOver;
		}
	}// updateRunning

	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}
	
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
			TouchEvent event = (TouchEvent) touchEvents.get(i);
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
			TouchEvent event = (TouchEvent) touchEvents.get(i);
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
		paintTiles(g);

		//Draw: Projectiles
		for(Projectile p : player.getProjectiles())
			p.draw(g);
		
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
	
	private void updateTiles() {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}
	}
	
	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			if (t.getType() != 0) {
				g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY());
			}
		}
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
		for(Button b : buttons)
			b.draw(g);
	
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

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static Player getRobot() {
		return player;
	}

}
