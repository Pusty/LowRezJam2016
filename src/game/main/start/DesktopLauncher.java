package game.main.start;

import game.engine.main.GameClass;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Title";
		config.width = 64*8;
		config.height = 64*8;
		config.resizable = true;
		config.foregroundFPS = 60;
		config.backgroundFPS = 30;
		
		GameClass gameclass = new GameClass();

		new LwjglApplication(gameclass, config);

		
	
		

	}
}
