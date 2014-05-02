package com.lampshady.dinnenyDanger;

import android.graphics.Rect;

import com.lampshady.framework.Graphics;
import com.lampshady.framework.Image;

public abstract class Character {
    final protected int gravity = 1;
    
	protected int centerX, centerY;
	protected int speedX, speedY;
    protected int height, width;

    protected int maxHealth, currentHealth;

    protected int jumpSpeed = -15;
    protected int moveSpeed = 5;
    
    protected Rect col, yellowRed;
	protected Rect topCol, botCol, lftCol, rhtCol;
	
	protected Image currentSprite;
	
	protected boolean movingLeft = false;
	protected boolean movingRight = false;
	protected boolean rightWall = false;
	protected boolean leftWall = false;
	
	public Character(){
		col = new Rect();
		yellowRed = new Rect();
		topCol = new Rect();
		botCol = new Rect();
		lftCol = new Rect();
		rhtCol = new Rect();
	}
	
	public abstract void update();
	
	public void draw(Graphics g){
		g.drawImage(currentSprite, centerX - width/2, centerY - height/2);
	}
	
	public abstract void nullify();

	
	public int getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	
}
