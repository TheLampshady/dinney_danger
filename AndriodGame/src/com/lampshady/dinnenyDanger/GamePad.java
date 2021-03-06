package com.lampshady.dinnenyDanger;

import java.util.ArrayList;
import java.util.List;

import com.lampshady.framework.Graphics;
import com.lampshady.framework.Input.TouchEvent;

public class GamePad {
	public static final int len = 65;
	public static final int bgBot = 415;
	public static final int bgRight = 735;
	
	private Player player = GameScreen.getPlayer();
	
	private ArrayList<Button> buttons;

	public GamePad(){
		/*	1-Jump
		 *	2-Duck
		 *	3-Pause		
		 *	4-Left
		 *	5-Right		
		 *	6-Shoot
		 */
		buttons = new ArrayList<Button>();
		buttons.add(new Button(1, Assets.buttonJump, bgRight, 		bgBot-len*2,len,len));
		buttons.add(new Button(2, Assets.buttonDuck, bgRight-len, 	bgBot-len,	len,len));
		buttons.add(new Button(3, Assets.buttonLeft, 0, bgBot-len, len, len));
		buttons.add(new Button(4, Assets.buttonRight,len*2, bgBot-len, len, len));
		buttons.add(new Button(5, Assets.buttonShoot,bgRight, bgBot, len, len));
		buttons.add(new Button(6, Assets.buttonPause,0, 0,35, 35));
		
	}
	
	
	protected void update(List<TouchEvent> touchEvents){
		for (Button b : buttons)
			b.setUpdated(false);
		
		for (TouchEvent event : touchEvents) {

			// Button Released
			if (event.type == TouchEvent.TOUCH_UP)
				for (Button b : buttons)
					if (b.inBounds(event)) b.setTouchID(-1);

			// Button Pressed
			if (event.type == TouchEvent.TOUCH_DOWN)
				for (Button b : buttons)
					if (b.inBounds(event)) b.setTouchID(event.pointer);

			// Button Dragged: if(onto) else(off of)
			if (event.type == TouchEvent.TOUCH_DRAGGED)
				for (Button b : buttons)
					if (b.inBounds(event)) b.setTouchID(event.pointer);
					else if (event.pointer == b.getTouchID()) b.setTouchID(-1);

		}
	}


	public ArrayList<Button> getButtons() {
		return buttons;
	}


	public void updateState() {
		int action;
		
		for(Button b: buttons){
			action = b.getAction();
			if(b.isPressed()){
				//Button Pressed
				switch (action) {
				case 1:	//Jump
					player.jump();
					player.setDucked(false);
					break;
					
				case 2:	//Duck
					if(player.isJumped() == false)
						player.setDucked(true);
					break;
				case 3:	//Button Pressed: Left
					player.setMovingLeft(true);
					break;
				
				case 4:	//Button Pressed: Right
					player.setMovingRight(true);
					break;
	
				case 5:	//Button Pressed: Shoot
					if (player.isDucked() == false && player.isJumped() == false && player.isReadyToFire())
						player.shoot();
					break;
				}

			} else {

				//Button Release
				switch (action) {

				case 2:	//Duck
					player.setDucked(false);
					break;
				
				case 3:	//Left
					player.setMovingLeft(false);
					break;

				case 4:	//Right
					player.setMovingRight(false);
					break;

				}
				
			}
		}
	

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
		
		
		
	}
	
	public void draw(Graphics g){
		for(Button b : buttons)
			b.draw(g);
	}
	
	public boolean toPause(){
		return(!buttons.get(5).isPressed() && buttons.get(5).isUpdated());
	}
	
}
