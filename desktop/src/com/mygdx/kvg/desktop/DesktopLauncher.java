package com.mygdx.kvg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.kvg.KVGames;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = KVGames.WIDTH;
		config.height = KVGames.HEIGHT;
		config.title = KVGames.TITLE;
		new LwjglApplication(new KVGames(), config);
	}
}
