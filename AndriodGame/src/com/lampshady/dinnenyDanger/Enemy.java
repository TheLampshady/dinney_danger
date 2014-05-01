package com.lampshady.dinnenyDanger;

import android.graphics.Rect;

abstract class Enemy {
	private int maxHealth, currentHealth;
	private int centerX, centerY, width, height, speedX;
	private int movementSpeed;
	
/*	private int engageDistance;
	private boolean engage;*/
	
	private Background bg = GameScreen.getBg1();
	private Player player = GameScreen.getRobot();
	
	//Android Port: Rectangle x = new Rectangle(0, 0, 0, 0);
	public Rect collision = new Rect(0, 0, 0, 0);

	
	// Behavioral Methods
	public void update() {		
		follow();
		
		centerX += speedX;
		speedX = bg.getSpeedX() + movementSpeed;
		
		//Android Port: x.setBounds(centerX-25, centerY-25, width-46, height-36);
		collision.set(centerX - width/2, centerY - height/2, centerX + width/2, centerY + height/2);
		
		if (Rect.intersects(collision, player.yellowRed)){
			checkPlayerCollision();
		}
	}
	
//	private void checkPlayerEngage(){
//		if(Math.abs(player.getCenterX() - centerX) < engageDistance)engage=true;
//	}
	
	private void checkPlayerCollision(){
		//Android Port: x.intersects(y)
		if (Rect.intersects(collision, player.topCol) || 
				Rect.intersects(collision, player.botCol) || 
				Rect.intersects(collision, player.lftCol) || 
				Rect.intersects(collision, player.rhtCol))
		{			
			System.out.println("collision");
		}
		
	}
	
	public void follow() {

		if (centerX < -95 || centerX > 810)
			movementSpeed = 0;

		else if (Math.abs(player.getCenterX() - centerX) < 5)
			movementSpeed = 0;

		else {
			if (player.getCenterX() >= centerX)
				movementSpeed = 1;
			else
				movementSpeed = -1;
		}

	}
	
	public void die() {
	}
	public void attack() {
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	public int getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getSpeedX() {
		return speedX;
	}
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	public int getCenterX() {
		return centerX;
	}
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}
	public int getCenterY() {
		return centerY;
	}
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
/*	public void setEngageDistance(int engageDistance) {
		this.engageDistance = engageDistance;
	}
	public void setEngage(boolean engage) {
		this.engage = engage;
	}*/
	
}
