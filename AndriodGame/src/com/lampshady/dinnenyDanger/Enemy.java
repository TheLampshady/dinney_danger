package com.lampshady.dinnenyDanger;

import android.graphics.Rect;

abstract class Enemy extends Character {
	
	private Background bg = GameScreen.getBg1();
	private Player player = GameScreen.getRobot();
	
	public Enemy(){
		super();
	}
	
	// Behavioral Methods
	public void update() {		
		follow();

		centerX += (bg.getSpeedX() + speedX);		
		
		//Android Port: x.setBounds(centerX-25, centerY-25, width-46, height-36);
		col.set(centerX - width/2, centerY - height/2, centerX + width/2, centerY + height/2);
		
		if (Rect.intersects(col, player.yellowRed)){
			checkPlayerCollision();
		}
	}
	
	
	private void checkPlayerCollision(){
		//Android Port: x.intersects(y)
		if (Rect.intersects(col, player.topCol) || 
				Rect.intersects(col, player.botCol) || 
				Rect.intersects(col, player.lftCol) || 
				Rect.intersects(col, player.rhtCol))
		{			
			System.out.println("collision");
		}
		
	}
	
	public void follow() {
		//Don't Engage: Off Screen
		if (centerX < -95 || centerX > 810)
			speedX = 0;

		//Don't Engage: On Player
		else if (Math.abs(player.getCenterX() - centerX) < 5)
			speedX = 0;

		//Engage Direction
		else {
			if (player.getCenterX() >= centerX)
				speedX = 1;
			else
				speedX = -1;
		}

	}
	
	public void die() {
	}
	public void attack() {
	}


	
}
