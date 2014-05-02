package com.lampshady.dinnenyDanger;


import java.util.ArrayList;

import com.lampshady.framework.Graphics;
import com.lampshady.framework.Image;

import android.graphics.Rect;
import android.graphics.Color;

public class Player extends Character{
	final int CENTERSCREEN = 300;

  
	
	private Image character, character2, character3;
	private Image characterDown, characterJumped;
	private Animation anim;
    
    private int bodyWidth;
    private int armHeight;
    private int armWidth;
    
    private static Background bg1;               
	private static Background bg2;
	
    public Rect footleft, footright;
    
	private boolean jumped = false;
    private boolean ducked = false;
    private boolean readyToFire = true;
    
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    
    public Player(){
    	super();
    	
    	centerX = 100;
	    centerY = 385;
		speedX = 0;
		speedY = 0;
		
	    jumpSpeed = -15;
	    moveSpeed = 5;
		
		height = 126;
    	width = 122;
		bodyWidth = 68;
     	armHeight = 20;
    	armWidth = 26;
		
    	//Android Port: Rectangle x = new Rectangle(0, 0, 0, 0);
    	footleft = new Rect(0,0,0,0);
		footright = new Rect(0,0,0,0);
		
		character = Assets.character;
		character2 = Assets.character2;
		character3 = Assets.character3;
		characterDown = Assets.characterDown;
		characterJumped = Assets.characterJump;
		
		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 100);
		anim.addFrame(character3, 100);
		anim.addFrame(character2, 100);
		
		currentSprite = anim.getImage();
		
		bg1 = GameScreen.getBg1();
		bg2 = GameScreen.getBg2();
		
    }//Constructor
    
    
    public void update() {
        // Moves Character or Scrolls Background accordingly.

		if(movingRight) 
			 speedX = moveSpeed;
		
        if (speedX < 0) {
            centerX += speedX;
        }
        
        if (speedX == 0 || speedX < 0 || rightWall) {
            bg1.setSpeedX(0);
            bg2.setSpeedX(0);
        }
        
        //Move Character: If left of Screen Center
        if (centerX <= CENTERSCREEN && speedX > 0) {
            centerX += speedX;
        }
        
        //Scroll Screen: If moving at center screen
        if (speedX > 0 && centerX > CENTERSCREEN && !rightWall) {
            bg1.setSpeedX(-moveSpeed);
            bg2.setSpeedX(-moveSpeed);
        }

        // Updates Y Position
        centerY += speedY;

        //Gravity
        speedY += gravity;

        //Handles falling without jumping
        if (speedY > 3){
            jumped = true;
        }

        // Prevents going beyond X coordinate of 0
        if (centerX + speedX <= (width/2)-1) {
            centerX = (width/2);
        }
        
        //Android Port:  Rect uses positions for all points
        //Android Port:  rect.setRect(centerX - 34, centerY - height/2, 68, height/2);
        //Android Port:  rect2.setRect(rect.getX(), rect.getY() + height/2, 68, height/2);
        topCol.set(centerX - bodyWidth/2, 	centerY - height/2,	
        		centerX + bodyWidth/2, centerY);
        botCol.set(topCol.left, 			centerY, 	
        		topCol.left+bodyWidth, centerY + height/2);
        lftCol.set(topCol.left - armWidth, 	centerY-height/4,	
        		topCol.left,  centerY-height/4+armHeight);
        rhtCol.set(topCol.left + bodyWidth, centerY-height/4, 	
        		topCol.left + bodyWidth+armWidth,  centerY-height/4+armHeight);
        
        //yellowRed.set(centerX - 110, centerY - 110, 180, 180);
        yellowRed.set(centerX - 70, centerY - 70, centerX+70, centerY+70);
        footleft.set(centerX - 50, centerY + 20, centerX, centerY +35);
        footright.set(centerX, centerY + 20, centerX+50, centerY +35);
        
        if (!isDucked()){
			if (isJumped())
				currentSprite = characterJumped;
			else
				currentSprite = anim.getImage();
		}
		else
			currentSprite = characterDown;

        anim.update(10);
        rightWall=false;
        leftWall=false;
    }//Update
    
    public void draw(Graphics g){ 
		super.draw(g);
		
		//Android Port: Drawing Rectangles
		//g.setColor(Color.WHITE);
		//g.drawRect((int)collision.getX(), (int)collision.getY(), (int)collision.getWidth(), (int)collision.getHeight());
		if(GameScreen.DEBUG){
			g.drawRectDebug(topCol.left, topCol.top, topCol.right, topCol.bottom, Color.BLACK);
			g.drawRectDebug(botCol.left, botCol.top, botCol.right, botCol.bottom, Color.YELLOW);
			g.drawRectDebug(lftCol.left, lftCol.top, lftCol.right, lftCol.bottom, Color.WHITE);
			g.drawRectDebug(rhtCol.left, rhtCol.top, rhtCol.right, rhtCol.bottom, Color.BLUE);
			g.drawRectDebug(footleft.left, footleft.top, footleft.right, footleft.bottom, Color.WHITE);
			g.drawRectDebug(footright.left, footright.top, footright.right, footright.bottom, Color.RED);
			g.drawRectDebug(yellowRed.left, yellowRed.top, yellowRed.right, yellowRed.bottom, Color.MAGENTA);
	
		}
	}
    
    public void moveRight() {
        if (ducked == false)
            speedX = moveSpeed;      
    }

    public void moveLeft() {
        if (ducked == false) {
            speedX = -moveSpeed;
        }
    }

    public void stopRight() {
        setMovingRight(false);
        stop();

    }

    public void stopLeft() {
        setMovingLeft(false);
        stop();
    }

    private void stop() {
        if (isMovingRight() == false && isMovingLeft() == false) {
            speedX = 0;
        }
        if (isMovingRight() == false && isMovingLeft() == true) {
            moveLeft();
        }
        if (isMovingRight() == true && isMovingLeft() == false) {
            moveRight();
        }

    }

    public void jump() {
        if (jumped == false) {
            speedY = jumpSpeed;
            jumped = true;
        }
    }
    
    public void shoot() {
    	if (readyToFire) {
    		Projectile p = new Projectile(centerX + 50, centerY - 25);
    		projectiles.add(p);
    	}
    }
    
    public void nullify(){
		currentSprite = null;
		character = null;
		character2 = null;
		character3 = null;
		anim = null;
		
		for (Projectile p : projectiles){
			p.nullify();
			p=null;
		}
    }
	
	public int getCenterX() {
		return centerX;
	}
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}
	public int getCenterY() {
		return centerY;
	}
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
	public int getSpeedX() {
		return speedX;
	}
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	public int getSpeedY() {
		return speedY;
	}
	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	public boolean isJumped() {
		return jumped;
	}
	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}
	public boolean isMovingLeft() {
		return movingLeft;
	}
	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}
	public boolean isMovingRight() {
		return movingRight;
	}
	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}
	public boolean isRightWall() {
		return rightWall;
	}
	public void setRightWall(boolean rightWall) {
		this.rightWall = rightWall;
	}
	public boolean isLeftWall() {
		return leftWall;
	}
	public void setLeftWall(boolean leftWall) {
		this.leftWall = leftWall;
	}
	public boolean isDucked() {
		return ducked;
	}
	public void setDucked(boolean ducked) {
		this.ducked = ducked;
	}
	public boolean isReadyToFire() {
		return readyToFire;
	}
	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}
}
