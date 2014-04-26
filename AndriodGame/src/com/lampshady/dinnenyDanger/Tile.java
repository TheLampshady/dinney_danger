package com.lampshady.dinnenyDanger;

import android.graphics.Rect;

import com.lampshady.framework.Image;

public class Tile {
	private int tileX, tileY, speedX, type;
	private int tileSize = 40;
    public Image tileImage;
    private Rect r;
    
    private Background bg = GameScreen.getBg1();
    //private Robot robot = StartingClass.getRobot();
    private Player robot = GameScreen.getRobot();
    
    public Tile(int x, int y, int typeInt) {
        tileX = x * tileSize;
        tileY = y * tileSize;

        type = typeInt;
        r = new Rect();
        switch (type) {
        
        case 2:		tileImage = Assets.tileBot;
        			break;
        case 4:		tileImage = Assets.tileLeft;
        			break;
        case 5:		tileImage = Assets.tile;
        			break;
        case 6:		tileImage = Assets.tileRight;
        			break;
        case 7:		tileImage = Assets.tileTopLeft;
        			break;
        case 8:		tileImage = Assets.tileTop;
        			break;
        case 9:		tileImage = Assets.tileTopRight;
        			break;
        default: 	type = 0;
        			break;
        	
        }
         

    }

    public void update() {
    	speedX = bg.getSpeedX();
    	tileX += speedX;
    	
    	//Android Port: r.setBounds(tileX, tileY, 40, 40);
    	r.set(tileX, tileY, tileX+tileSize, tileY+tileSize);
    	if (Rect.intersects(r, robot.yellowRed) && type != 0) {
    		checkVerticalCollision(robot.topCol, robot.botCol);
    		checkSideCollision(robot.lftCol, robot.rhtCol, robot.footleft, robot.footright);
    	}
    }
    
    public void checkVerticalCollision(Rect rtop, Rect rbot) {
        if (Rect.intersects(rtop, r)) {
           
        }

        //Action: Falling
        if ((type == 7 || type == 8 ||type == 9) && Rect.intersects(rbot, r) && (robot.getSpeedY() >= 0)) {
            robot.setJumped(false);
            robot.setSpeedY(0);
            robot.setCenterY(tileY - 62);
        }
    }

    public void checkSideCollision(Rect rleft, Rect rright, Rect leftfoot, Rect rightfoot) {
        if (type != 5 && type != 2 && type != 0){
            if (Rect.intersects(rleft, r)) {
                robot.setCenterX(tileX + 102);
                robot.setLeftWall(true);
   
            }else if (Rect.intersects(leftfoot, r)) {
                robot.setCenterX(tileX + 85);
                robot.setLeftWall(true);
            }
           
            if (Rect.intersects(rright, r)) {
                robot.setCenterX(tileX - 62);
                robot.setRightWall(true);
            }
           
            else if (Rect.intersects(rightfoot, r)) {
                robot.setCenterX(tileX - 45);
                robot.setRightWall(true);
            }
        }
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public void setTileImage(Image tileImage) {
        this.tileImage = tileImage;
    }

	public int getType() {
		return type;
	}
    
}
