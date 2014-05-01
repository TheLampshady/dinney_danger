package com.lampshady.dinnenyDanger;

import com.lampshady.framework.Image;
import com.lampshady.framework.Graphics;

import android.graphics.Rect;

public class Projectile {
	private int damage;
	
	private int x, y, width, height, speedX;
	private boolean visible;
	private Image bullet;
	
	private Rect collision;
	
	public Projectile(int startX, int startY){
		x = startX;
		y = startY;
		width = 20;
		height = 5;
		
		damage =1;
		
		speedX = 7;
		visible = true;
		
		bullet = Assets.bullet;
		
		collision = new Rect(0, 0, 0, 0);
	}

	public void update(){
		x += speedX;
		collision.set(x, y, x+width, y+height);
		if (x > 800) {
		   visible = false;
		}
		
		if (x < 800){
			checkEnemyCollision();
		}
		
	}
	
	private void checkEnemyCollision() {
		int i = 0;
		for(Heliboy hb : GameScreen.heliboys){
			int health = hb.getCurrentHealth();
			
			if(Rect.intersects(collision,hb.col)){
				visible = false;	
				if (health > 0) {
					GameScreen.heliboys.get(i).setCurrentHealth(health - damage);
				}
				if (hb.getCurrentHealth() == 0) {
					GameScreen.heliboys.remove(i);
					GameScreen.score += 5;

				}
			}
			i++;
		}
	}
	
	public void draw(Graphics g){
		g.drawImage(bullet, x, y);
	}
	
	public void nullify(){
		bullet = null;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpeedX() {
		return speedX;
	}
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
