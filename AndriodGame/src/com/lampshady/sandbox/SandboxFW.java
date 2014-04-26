package com.lampshady.sandbox;

import com.lampshady.framework.Screen;
import com.lampshady.framework.implementation.AndroidGame;

public class SandboxFW extends AndroidGame{
	@Override
    public Screen getInitScreen() {
        return new LoadingScreen(this);
    }
	
	@Override
	public void onBackPressed() {
		getCurrentScreen().backButton();
	}
   
}
