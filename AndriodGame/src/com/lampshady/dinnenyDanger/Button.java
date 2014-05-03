package com.lampshady.dinnenyDanger;

import com.lampshady.framework.Graphics;
import com.lampshady.framework.Image;
import com.lampshady.framework.Input.TouchEvent;

public class Button {
	private int index;
	private int touchID;
	private boolean updated;
	
	private int bgX, bgY;
	private int left, top, right, bot;
	
	private Image iButton;
	
	public Button(int action, int bgX, int bgY, int left, int top, int right, int bot){
		iButton = Assets.button;
		touchID=-1;
			
		this.index = action;
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
		return index;
	}
	public int getTouchID() {
		return touchID;
	}
	public boolean isPressed() {
		return touchID!=-1;
	}
	public void setTouchID(int touchID) {
		updated=true;
		this.touchID = touchID;
	}
	public boolean isUpdated() {
		return updated;
	}
	public void setUpdated(boolean released) {
		this.updated = released;
	}
	

}
