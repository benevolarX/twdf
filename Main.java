import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1050;
		config.height = 600;
		config.title = "Tower Defense version 1.0 - 06/12/2014";
		config.fullscreen = false;
		config.resizable = false;
		new LwjglApplication(new TowerDefense(), config);
	}

}
