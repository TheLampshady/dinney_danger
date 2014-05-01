package com.lampshady.dinnenyDanger;

import android.graphics.Rect;

import com.lampshady.framework.Graphics;

public abstract class Character {
	
	protected int centerX, centerY;
	protected int speedX, speedY;
    protected int height, width;

    protected int maxHealth, currentHealth;

    protected int gravity=1;
    
    protected Rect col, yellowRed;
	protected Rect topCol, botCol, lftCol, rhtCol;
	
	public abstract void update();
	
	public abstract void draw(Graphics g);
	
	public abstract void nullify();

	
	public int getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	
}
