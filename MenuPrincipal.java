import java.util.regex.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuPrincipal implements Screen {
	/***** OBLIGATOIRE *****/
	private TowerDefense game;
	private Stage stage;
	/***** Design *****/
	// private Texture fond_ecran_texture;
	private Image fond_ecran;
	private Label version;
	// private BitmapFont font;
	/***** boutons et widget *****/
	private TextField adr_ip;
	private TextButton btn_jouer;
	private Label erreur;

	public MenuPrincipal(TowerDefense twdf) {
		this.game = twdf;
	}

	@Override
	public void render(float delta) {
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

		// création des boutons
		adr_ip = new TextField("127.0.0.1", TowerDefense.skin);
		adr_ip.setWidth(100);
		adr_ip.setPosition(Gdx.graphics.getWidth() / 2 - adr_ip.getWidth() / 2
				- 50, 50);
		stage.addActor(adr_ip);

		erreur = new Label("", TowerDefense.skin);
		erreur.setColor(10, 0, 0, 10);
		erreur.setPosition(50, 50 + erreur.getHeight());
		stage.addActor(erreur);

		btn_jouer = new TextButton(" JOUER ", TowerDefense.skin);
		btn_jouer.setColor(0, 0, 10, 10);
		btn_jouer
				.setPosition(Gdx.graphics.getWidth() / 2 - btn_jouer.getWidth()
						/ 2 + 50, 50);
		btn_jouer.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				if (adr_ip.getText() != null && adr_ip.getText() != "") {
					// test si IP valide => NE PAS TOUCHER
					String regex = "(((2[0-4][0-9])|(25[0-5])|(1[0-9][0-9])|([1-9][0-9])|([0-9]))\\.){3}"
							+ "((2[0-4][0-9])|(25[0-5])|(1[0-9][0-9])|([1-9][0-9])|([0-9]))";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(adr_ip.getText());
					// lancement de la recherche de toutes les occurrences
					if (m.matches()) {
						game.setScreen(new Fin_partie(game, adr_ip.getText()));
						//game.setScreen(new Partie(game, adr_ip.getText()));
						//game.setScreen(new Fin_partie(game, adr_ip.getText(), adr_ip.getText()));
					} else {
						erreur.setText("Vous devez entrer une adresse ip valide");
					}
				} else {
					System.out.println("Vous devez entrer une adresse ip");
				}
				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_jouer.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_jouer.setColor(0, 0, 10, 10);
			}
		});
		stage.addActor(btn_jouer);

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

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
}
