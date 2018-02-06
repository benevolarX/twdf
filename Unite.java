import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class Unite {

	public final static int cote = 40;
	public static Partie ref_partie;

	public static enum type_unite {
		m, t, e
	};

	protected Position p;
	protected Achetable prix;
	protected String image; // chaque unité a une image
	protected Image img;
	protected Image img_boutique; // image au magasin / en info
	protected TextButton btn_acheter;
	protected Label infos;
	protected String nom;

	protected TextButton btn_vendre;
	protected TextButton btn_poser;
	protected boolean action;

	void init_unite() {
		nom = "";
		action = false;
		p = null;
		prix = new Achetable(0, 0, false, false);
		setImage("./assets/images/jeu/chemin.png");
		infos = new Label("", TowerDefense.skin);
		btn_acheter = new TextButton("acheter", TowerDefense.skin);
		btn_vendre = new TextButton("vendre", TowerDefense.skin);
		btn_poser = new TextButton("placer", TowerDefense.skin);
		btn_acheter.setColor(0, 0, 10, 10);
		cacher();
	}

	void copie(Unite u) {
		init_unite();
		if (u.getPosition() != null) {
			p = new Position(u.p.x, u.p.y);
		}
		if (u.getPrix() != null) {
			prix = new Achetable(u.prix);
		}
		setImage(u.image);
		infos.setText(u.infos.getText());
		this.nom = u.nom;
	}

	public Image getImage() {
		return img;
	}

	public void setImage(String image) {
		this.image = image;
		img = new Image(ImageManager.GetImage(image));
		img_boutique = new Image(ImageManager.GetImage(image));
	}

	public Position getPosition() {
		return p;
	}

	public void setPosition(Position p) {
		this.p = p;
	}

	public Achetable getPrix() {
		return prix;
	}

	public void setPrix(Achetable prix) {
		this.prix = prix;
	}

	public abstract type_unite getType_unite();

	public void cacher() {
		// TODO Auto-generated method stub
		img_boutique.setVisible(false);
		img.setVisible(false);
		if (infos != null) {
			infos.setVisible(false);
		}
		if (btn_acheter != null) {
			btn_acheter.setVisible(false);
		}
		if (btn_vendre != null) {
			btn_vendre.setVisible(false);
		}
		if (btn_poser != null) {
			btn_poser.setVisible(false);
		}
	}

	public void afficher_boutique(int x, int y) {
		img_boutique.setPosition(x, y);
		infos.setPosition(x + img_boutique.getWidth() + 5,
				y + img_boutique.getHeight() / 2);
		btn_acheter.setPosition(
				Gdx.graphics.getWidth() - btn_acheter.getWidth() - 3, y);
		img_boutique.setVisible(true);
		infos.setVisible(true);
		btn_acheter.setVisible(true);
	}

	public abstract void afficher_description();

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void afficher_actions() {
		// TODO Auto-generated method stub
		float centre = (Gdx.graphics.getWidth() - Partie.l_terrain) / 2
				+ Partie.l_terrain;
		btn_vendre.setPosition(Partie.l_terrain, Partie.h_terrain - 70);
		btn_vendre.setVisible(true);
		btn_poser.setPosition(
				Gdx.graphics.getWidth() - 3 - btn_poser.getWidth(),
				Partie.h_terrain - 70);
		btn_poser.setVisible(true);
		img.setPosition(centre, 1);
	}

	public void init_action(final Joueur joueur) {
		// TODO Auto-generated method stub
		final Unite u = this;
		if (this.prix.isVendable()) {
			btn_vendre.setColor(0, 0, 10, 10);
			btn_vendre.addListener(new ClickListener() {
				public boolean touchDown(InputEvent e, float x, float y,
						int pointeur, int bouton) {
					joueur.vendre(u);
					return false;
				}

				public void enter(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
					btn_vendre.setColor(0, 10, 0, 10);
				}

				public void exit(InputEvent event, float x, float y,
						int pointer, Actor fromActor) {
					btn_vendre.setColor(0, 0, 10, 10);
				}
			});
		}
		btn_poser.setColor(0, 0, 10, 10);
		btn_poser.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				joueur.poser(u);
				return false;
			}

			public void enter(InputEvent event, float x, float y,
					int pointer, Actor fromActor) {
				btn_poser.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y,
					int pointer, Actor fromActor) {
				btn_poser.setColor(0, 0, 10, 10);
			}
		});
		action = true;
	}

}
