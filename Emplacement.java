import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Emplacement extends Unite implements un_panel {

	protected boolean passable; // un monstre peut passer dessus ?
	protected boolean constructible; // on peut placer une tour dessus ?
	protected Tour t; // référence de tour, null si aucune tour placée

	public Emplacement(Position p, Achetable pr) {
		init_unite();
		passable = true;
		constructible = true;
		t = null;

		this.p = new Position(p.x, p.y);
		prix = pr;
		setImage("./assets/images/jeu/chemin.png");
	}

	public Emplacement(int x, int y) {
		init_unite();
		passable = true;
		constructible = true;
		t = null;

		p = new Position(x, y);
		setImage("./assets/images/jeu/chemin.png");
	}

	public Emplacement() {
		init_unite();
		t = null;
		setImage("./assets/images/jeu/chemin.png");
	}

	public Emplacement(Emplacement u) {
		// TODO Auto-generated constructor stub
		init_unite();
		copie(u);
		passable = u.isPassable();
		constructible = u.isConstructible();
		t = null;
		if (u.getT() != null) {
			t = new Tour(u.t);
		}

		final Emplacement objet = this;
		img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.getMoi().unite_description = objet;
				return false;
			}
		});

	}

	public Tour getT() {
		return t;
	}

	public void setT(Tour t) {
		this.t = t;
	}

	public boolean isPassable() {
		return (passable && t == null);
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	public boolean isConstructible() {
		return (constructible && t == null);
	}

	public void setConstructible(boolean constructible) {
		this.constructible = constructible;
	}

	public boolean is_libre() {
		return (t == null);
	}

	public boolean construire(Tour t2) {
		if (t == null) {
			t = new Tour(t2);
			return true;
		}
		return false;
	}

	public String getImage_txt() {
		return image;
	}

	public type_unite getType_unite() {
		return type_unite.e;
	}

	public void afficher_boutique(int x, int y) {
		// TODO Auto-generated method stub
		infos.setText(nom + "\n" + prix.getPrix_achat() + " $");
		super.afficher_boutique(x, y);
	}

	public void init_panel(Stage s, final Joueur j) {
		final Emplacement temp = this;
		btn_acheter.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				j.acheter(temp);
				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_acheter.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_acheter.setColor(0, 0, 10, 10);
			}
		});
		cacher();

		ref_partie.associer(temp);
	}

	public void updateImage() {
		if (passable) {
			setImage("./assets/images/jeu/chemin.png");
		} else {
			if (constructible) {
				setImage("./assets/images/jeu/obstacle_constructible.png");
			} else {
				setImage("./assets/images/jeu/mur.png");
			}
		}
	}

	@Override
	public void afficher_description() {
		// TODO Auto-generated method stub
		float centre = Partie.l_terrain
				+ (Gdx.graphics.getWidth() - Partie.l_terrain) / 2;
		img_boutique.setPosition(centre - img_boutique.getWidth() / 2,
				Partie.h_terrain - 100);
		String infos_m = "nom : " + nom + "\n \n"
				+ "Cet obstacle ne peut pas \n" + "tirer sur monstre" + "\n"
				+ "prix achat : " + prix.getPrix_achat();
		int p_vente = prix.getPrix_vente();
		if (p_vente != 0) {
			infos_m += "\n" + "prix re-vente : " + prix.getPrix_vente();
		}
		infos.setText(infos_m);
		infos.setPosition(centre - infos.getPrefWidth() / 2, Partie.h_terrain
				- 100 - infos.getPrefHeight());
		infos.setVisible(true);
		img_boutique.setVisible(true);
	}

}
