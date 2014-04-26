package com.lampshady.dinnenyDanger;

import com.lampshady.framework.Graphics;
import com.lampshady.framework.Image;
import com.lampshady.framework.Input.TouchEvent;

public class Button {

	public static final int len = 65;
	public static final int bgBot = 415;
	public static final int bgRight = 735;
	
	private int touchID;
	private int action;
	
	private int bgX, bgY;
	private int left, top, right, bot;
	
	private Image iButton;
	
	public Button(int action, int bgX, int bgY, int left, int top, int right, int bot){
		iButton = Assets.button;
		touchID=-1;
		
		this.action = action;
		this.bgX = bgX;
		this.bgY = bgY;
		this.left = left;
		this.top = top;
		this.right = right;
		this.bot = bot;
	}
	
	public void draw(Graphics g){
		g.drawImage(iButton, bgX, bgY, left, top, right, bot);
	}
	
	public boolean inBounds(TouchEvent event) {
		if (event.x > bgX && event.x < bgX + right - 1 && event.y > bgY && event.y < bgY + bot - 1){
			touchID=event.pointer;
			return true;
		} else
			return false;
	}

	public int getAction() {
		return action;
	}

	public int getTouchID() {
		return touchID;
	}

	public void setTouchID(int touchID) {
		this.touchID = touchID;
	}
		

}
