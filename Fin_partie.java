import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Fin_partie implements Screen {

	private TowerDefense game;
	private TextButton btn_rejouer;
	private TextButton btn_menu_principal;
	private Label message;
	private String ip_adversaire;
	private Stage stage;
	private Image fond_ecran;
	private Label version;

	public Fin_partie(TowerDefense twdf, String gagnant, String ip) {
		this.game = twdf;
		ip_adversaire = ip;
		message = new Label("Bravo a " + gagnant
				+ " qui a gagne la partie \n Voulez vous rejouer ?",
				TowerDefense.skin);
		btn_rejouer = new TextButton("Rejouer", TowerDefense.skin);
	}

	public Fin_partie(TowerDefense twdf, String ip) {
		// TODO Auto-generated constructor stub

		this.game = twdf;
		ip_adversaire = ip;
		message = new Label("Un adversaire a ete trouve ! ", TowerDefense.skin);
		btn_rejouer = new TextButton("Jouer", TowerDefense.skin);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		// fond ecran
		fond_ecran = new Image(
				ImageManager.GetImage("./assets/images/menu/fond.png"));
		stage.addActor(fond_ecran);

		// label
		version = new Label("version 1.0 / 06/12/2014", TowerDefense.skin);
		version.setPosition(850, 50);
		stage.addActor(version);

		btn_menu_principal = new TextButton("Menu Principal", TowerDefense.skin);
		message.setFontScale(2);
		message.setPosition(Gdx.graphics.getWidth() / 2 - message.getWidth(),
				Gdx.graphics.getHeight() * 3 / 4);
		btn_rejouer.setPosition(
				Gdx.graphics.getWidth() / 2 - 200 - btn_rejouer.getWidth(),
				Gdx.graphics.getHeight() / 4);
		btn_menu_principal.setPosition(Gdx.graphics.getWidth() / 2 + 200,
				Gdx.graphics.getHeight() / 4);

		btn_rejouer.setColor(0, 0, 10, 10);
		btn_rejouer.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				game.setScreen(new Partie(game, ip_adversaire));
				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_rejouer.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_rejouer.setColor(0, 0, 10, 10);
			}
		});
		stage.addActor(btn_rejouer);

		btn_menu_principal.setColor(0, 0, 10, 10);
		btn_menu_principal.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				game.setScreen(new MenuPrincipal(game));
				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_menu_principal.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_menu_principal.setColor(0, 0, 10, 10);
			}
		});
		stage.addActor(btn_menu_principal);
		stage.addActor(message);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
