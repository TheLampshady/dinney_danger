package com.lampshady.dinnenyDanger;

import com.lampshady.framework.Graphics;
import com.lampshady.framework.Image;
import com.lampshady.framework.Input.TouchEvent;

public class Button {
	int alpha;
	private int index;
	private int touchID;
	private boolean updated;
	
	private int bgX, bgY;
	int height, width;
	
	private Image iButton;
	
	public Button(int action, Image image, int bgX, int bgY, int height, int width){
		iButton = Assets.button;
		touchID=-1;
		//From 0(clear) to 255(full)
		alpha = 185;
		
		this.index = action;
		this.bgX = bgX;
		this.bgY = bgY;
		
		this.height = height;
		this.width = width;
		
		iButton = image;
				
	}
	
	public void draw(Graphics g){
		//g.drawImage(iButton, bgX, bgY, left, top, right, bot);
		g.drawClearImage(iButton, bgX, bgY, alpha);
	}
	
	public boolean inBounds(TouchEvent event) {
		if (event.x > bgX && event.x < bgX + width - 1 && event.y > bgY && event.y < bgY + height - 1){
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
