package com.lampshady.dinnenyDanger;

import com.lampshady.framework.Graphics;
import com.lampshady.framework.Image;

import android.graphics.Color;

public class Heliboy extends Enemy{

	private Image heliboy, heliboy2, heliboy3, heliboy4, heliboy5, currentSprite;
	private Animation hanim;

	
	public Heliboy(int centerX, int centerY) {
		super();
		
		this.centerX = centerX;
		this.centerY = centerY;
		
		width=96;
		height=96;
		
		maxHealth=5;
		currentHealth=5;
		
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
			g.drawRectDebug(col.left, col.top, col.right, col.bottom, Color.WHITE);
		
		g.drawImage(currentSprite, centerX - width/2, centerY - height/2);
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
