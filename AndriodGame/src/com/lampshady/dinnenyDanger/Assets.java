package com.lampshady.dinnenyDanger;

import com.lampshady.framework.Image;
import com.lampshady.framework.Music;
import com.lampshady.framework.Sound;

public class Assets {
	   
    public static Image menu, splash, background, button;
    public static Image character, character2, character3, characterJump, characterDown, bullet;
    public static Image tile, tileTop, tileBot, tileLeft, tileRight, tileTopLeft, tileTopRight;
    public static Image heliboy1a, heliboy1b, heliboy1c, heliboy1d, heliboy1e;
    
    public static Sound click;
    public static Music theme;
   
    public static void load(SampleGame sampleGame) {
        theme = sampleGame.getAudio().createMusic("menutheme.mp3");
        theme.setLooping(true);
        theme.setVolume(0.85f);
        theme.play();
    }
    
}
    
