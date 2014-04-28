package com.lampshady.dinnenyDanger;

import com.lampshady.framework.Game;
import com.lampshady.framework.Graphics;
import com.lampshady.framework.Screen;
import com.lampshady.framework.Graphics.ImageFormat;

public class SplashScreen extends Screen {
    public SplashScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.splash= g.newImage("splash.jpg", ImageFormat.RGB565);

       
        game.setScreen(new LoadingScreen(game));

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
