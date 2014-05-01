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
	
	public Character(){
		col = new Rect();
		yellowRed = new Rect();
		topCol = new Rect();
		botCol = new Rect();
		lftCol = new Rect();
		rhtCol = new Rect();
	}
	
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
