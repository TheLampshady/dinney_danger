package com.lampshady.dinnenyDanger;

import java.util.Calendar;

import com.lampshady.framework.Game;
import com.lampshady.framework.Graphics;
import com.lampshady.framework.Screen;
import com.lampshady.framework.Graphics.ImageFormat;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
       
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        String prefix = SampleGame.version+".";
        String world = SampleGame.world+".";
        int waitTime = 5;
        
        Calendar c = Calendar.getInstance();
        int loadTime = c.get(Calendar.SECOND);
        
        Assets.menu = g.newImage("menu.png", ImageFormat.RGB565);
        
        Assets.background = g.newImage(prefix+"background.png", ImageFormat.RGB565);
        Assets.character = g.newImage(prefix+"character.png", ImageFormat.ARGB4444);
        Assets.character2 = g.newImage(prefix+"character2.png", ImageFormat.ARGB4444);
        Assets.character3  = g.newImage(prefix+"character3.png", ImageFormat.ARGB4444);
        Assets.characterJump = g.newImage(prefix+"jumped.png", ImageFormat.ARGB4444);
        Assets.characterDown = g.newImage(prefix+"down.png", ImageFormat.ARGB4444);
        Assets.bullet = g.newImage(prefix+"bullet.png", ImageFormat.ARGB4444);

        Assets.heliboy1a = g.newImage(prefix+"enemy1a.png", ImageFormat.ARGB4444);
        Assets.heliboy1b = g.newImage(prefix+"enemy1b.png", ImageFormat.ARGB4444);
        Assets.heliboy1c = g.newImage(prefix+"enemy1c.png", ImageFormat.ARGB4444);
        Assets.heliboy1d = g.newImage(prefix+"enemy1d.png", ImageFormat.ARGB4444);
        Assets.heliboy1e = g.newImage(prefix+"enemy1e.png", ImageFormat.ARGB4444);

        Assets.tile = g.newImage(world+"tile5.png", ImageFormat.RGB565);
        Assets.tileTop = g.newImage(world+"tile8.png", ImageFormat.RGB565);
        Assets.tileBot = g.newImage(world+"tile2.png", ImageFormat.RGB565);
        Assets.tileLeft = g.newImage(world+"tile4.png", ImageFormat.RGB565);
        Assets.tileRight = g.newImage(world+"tile6.png", ImageFormat.RGB565);
        Assets.tileTopLeft = g.newImage(world+"tile7.png", ImageFormat.RGB565);
        Assets.tileTopRight = g.newImage(world+"tile9.png", ImageFormat.RGB565);
       
        Assets.button = g.newImage("button.jpg", ImageFormat.RGB565);

        //LLoad Sound
        //Assets.click = game.getAudio().createSound("explode.ogg");
      
        c = Calendar.getInstance();
        loadTime = c.get(Calendar.SECOND)-loadTime;
        
        if(loadTime<5 && loadTime>0){
	        try {
				Thread.sleep((waitTime-loadTime)*1000);
			} catch (InterruptedException e) {}
        }

        game.setScreen(new MainMenuScreen(game));

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.splash, 0, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}
