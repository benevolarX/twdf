import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Partie implements Screen {

	public final static int l_terrain = 800;
	public final static int h_terrain = 600;
	protected static boolean attend_clic;

	private TowerDefense game;
	private Stage stage;

	private Joueur moi;
	private Joueur_Reseau adversaire;
	private boolean debut; // debut = false <=> moi et/ou l'adversaire n'est pas
							// prêts
	private Image menu;
	private TextButton GO;

	public Partie(TowerDefense game, String ip_adv) {
		Projectile.ref_partie = this;
		attend_clic = false;
		Unite.ref_partie = this;
		this.game = game;
		adversaire = new Joueur_Reseau(ip_adv, this, stage);
		moi = new Joueur("moi", this, stage);
		debut = false;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(Gdx.graphics.getDeltaTime(), debut);
		if (moi.getVie() <= 0) {
			game.setScreen(new Fin_partie(game, adversaire.getNom(), adversaire
					.getIp()));
		}
		if (adversaire.getVie() <= 0) {
			game.setScreen(new Fin_partie(game, moi.getNom(), adversaire
					.getIp()));
		}
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	private void update(float deltaTime, boolean m) {
		// TODO Auto-generated method stub
		moi.update(deltaTime, m);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		menu = new Image(ImageManager.GetImage("./assets/images/jeu/menu.png"));
		menu.setPosition(l_terrain, 0);
		stage.addActor(menu);

		moi.init_panel(stage, moi);

		GO = new TextButton(" GO ! ", TowerDefense.skin);
		GO.setPosition((Gdx.graphics.getWidth() - l_terrain) / 2 + l_terrain
				- GO.getWidth() / 2, GO.getHeight());
		GO.setColor(0, 0, 10, 10);
		GO.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				if (!debut) {
					// on autorise la partie à commencer (les monstres adverses
					// arrivent)
					debut = true;
					moi.getShop().debloquer_monstres();
					moi.menu();
					GO.setVisible(false);
				}
				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				GO.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				GO.setColor(0, 0, 10, 10);
			}
		});
		stage.addActor(GO);
		
		moi.getTerrain().afficher(0, 0, l_terrain, h_terrain, stage);

		init_hierarchie();
	}

	@Override
	public void resize(int width, int height) {
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public Joueur getMoi() {
		return moi;
	}

	public Joueur_Reseau getAdversaire() {
		return adversaire;
	}

	public void init_hierarchie() {
		// init_mag(); => tours
		Magasin mag = moi.getShop();
		ArrayList<Tour> mag_tour = mag.getTours();
		Iterator<Tour> it = mag_tour.iterator();
		while (it.hasNext()) {
			Tour temp = it.next();
			temp.init_panel(stage, moi);
		}

		// init_mag(); => monstres
		ArrayList<Monstre> mag_monstre = mag.getMonstres();
		Iterator<Monstre> it_m = mag_monstre.iterator();
		while (it_m.hasNext()) {
			Monstre temp = it_m.next();
			temp.init_panel(stage, moi);
		}

		// init_mag(); => monstres
		ArrayList<Emplacement> mag_e = mag.getSurfaces();
		Iterator<Emplacement> it_e = mag_e.iterator();
		while (it_e.hasNext()) {
			Emplacement temp = it_e.next();
			temp.init_panel(stage, moi);
		}
	}

	public void associer(Unite u) {
		// TODO Auto-generated method stub
		if (u.getImage() != null) {
			stage.addActor(u.getImage());
			u.getImage().setSize(Unite.cote, Unite.cote);
			u.img.setVisible(false);
		}
		if (u.img_boutique != null) {
			stage.addActor(u.img_boutique);
			u.img_boutique.setVisible(false);
		}
		if (u.infos != null) {
			stage.addActor(u.infos);
			u.infos.setVisible(false);
		}
		if (u.btn_acheter != null) {
			stage.addActor(u.btn_acheter);
			u.btn_acheter.setVisible(false);
		}
		if (u.btn_vendre != null) {
			stage.addActor(u.btn_vendre);
			u.btn_vendre.setVisible(false);
		}
		if (u.btn_poser != null) {
			stage.addActor(u.btn_poser);
			u.btn_poser.setVisible(false);
		}
		switch (u.getType_unite()) {
		case m:
			if (((Monstre) u).getBarre_vie() != null) {
				stage.addActor(((Monstre) u).getBarre_vie());
				((Monstre) u).getBarre_vie().setVisible(false);
			}
			break;
		case t:
			break;
		case e:
			if (((Emplacement) u).getT() != null){
				this.associer((Tour) ((Emplacement) u).getT()) ;
			}
			break;
		default:
			break;
		}
	}
	
	public void associer(Projectile obus){
		stage.addActor(obus.getObus());
		obus.getObus().setVisible(false);
	}

	public void info(Unite objet) {
		// TODO Auto-generated method stub
		moi.setCible(Joueur.pointeur.modif);
		moi.menu();
		objet.afficher_description();
	}

	public void add_img(Image image) {
		// TODO Auto-generated method stub
		stage.addActor(image);
	}

}
