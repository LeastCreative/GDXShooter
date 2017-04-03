package com.supershooter.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.supershooter.game.Game;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.title = "Test";
        config.foregroundFPS = 400;
        config.forceExit = true;
        config.vSyncEnabled = false;
        //config.fullscreen = true;
        new LwjglApplication(new Game(), config);
    }
}
