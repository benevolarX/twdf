import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TowerDefense extends Game implements ApplicationListener {

	Screen scene;
	public static Skin skin;
	
	@Override
	public void create() {
		skin = new Skin(Gdx.files.internal("./assets/uiskin.json"));
		scene = new MenuPrincipal(this);
		this.setScreen(scene); // ecran actif
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
	
	public void setScreen(Screen s){
		ImageManager.vider();
		super.setScreen(s);
	}
}
