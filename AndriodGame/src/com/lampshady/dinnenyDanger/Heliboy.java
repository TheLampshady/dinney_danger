package com.lampshady.dinnenyDanger;

import com.lampshady.framework.Graphics;
import com.lampshady.framework.Image;

import android.graphics.Color;

public class Heliboy extends Enemy{

	private Image heliboy, heliboy2, heliboy3, heliboy4, heliboy5, currentSprite;
	private Animation hanim;

	
	public Heliboy(int centerX, int centerY) {
		setCenterX(centerX);
		setCenterY(centerY);
		
		setWidth(96);
		setHeight(96);
		
		setMaxHealth(5);
		setCurrentHealth(5);
				
//		setEngage(false);
//		setEngageDistance(300);
		
		heliboy = Assets.heliboy1a;
		heliboy2 = Assets.heliboy1b;
		heliboy3 = Assets.heliboy1c;
		heliboy4 = Assets.heliboy1d;
		heliboy5 = Assets.heliboy1e;
		
		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);
		
		currentSprite = hanim.getImage();
	}

	@Override
	public void update() {
		super.update();
		
		hanim.update(50);
		currentSprite = hanim.getImage();
	}
	
	public void draw(Graphics g){
		if(GameScreen.DEBUG)
			g.drawRectDebug(collision.left, collision.top, collision.right, collision.bottom, Color.WHITE);
		
		g.drawImage(currentSprite, getCenterX() - getWidth()/2, getCenterY() - getHeight()/2);
	}
	
	public void nullify(){
		heliboy = null;
		heliboy2 = null;
		heliboy3 = null;
		heliboy4 = null;
		heliboy5 = null;
		hanim = null;
	
	}
	
}
