package com.lampshady.dinnenyDanger;

import android.graphics.Rect;

import com.lampshady.framework.Graphics;

public abstract class Character {
	
	protected int centerX, centerY;
	protected int speedX, speedY;
    protected int height, width;
    
    protected int maxHealth, currentHealth;
    
	public Rect topCol = new Rect(0, 0, 0, 0);
    public Rect botCol = new Rect(0, 0, 0, 0);
    public Rect lftCol = new Rect(0, 0, 0, 0);
    public Rect rhtCol = new Rect(0, 0, 0, 0);
    
	public abstract void update();
	
	public abstract void draw(Graphics g);
	
	public abstract void nullify();
}
