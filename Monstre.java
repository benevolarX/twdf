import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Monstre extends Unite implements un_panel {

	private int income;
	private int vitesse;
	private int vie;
	private int vie_max;
	private Label barre_vie;
	private boolean fou;
	private Position position_a_atteindre; // position de la case où il se
	// déplace != base
	private ArrayList<Competence> special; // invisible, volant, ...
	private ArrayList<Statut> statut;
	private Joueur moi;
	private Position position_exacte;

	public static enum Competence {
		invisibilite, vol
	}

	// constructeur par défaut
	public Monstre() {
		// TODO Auto-generated constructor stub
		init_unite();
		this.statut = new ArrayList<Statut>();
		this.income = 10;
		this.vitesse = 40;
		this.vie = 0;
		this.vie_max = 100;
		this.barre_vie = new Label(vie + "/" + vie_max, TowerDefense.skin);
		fou = false;
		this.position_a_atteindre = null;
		this.special = new ArrayList<Competence>();
		this.moi = null;
		this.position_exacte = null;
		setImage("./assets/images/jeu/monstre.png");
	}

	// constructeur de copie
	public Monstre(Monstre u) {
		// TODO Auto-generated constructor stub
		init_unite();
		copie(u);
		this.statut = new ArrayList<Statut>();
		this.statut.addAll(u.getStatut());
		this.barre_vie = new Label("", TowerDefense.skin);
		this.fou = u.fou;
		this.moi = u.moi;
		this.position_exacte = null;
		this.income = u.income;
		this.vitesse = u.vitesse;
		this.vie = u.vie;
		this.vie_max = u.vie_max;
		this.special = new ArrayList<Competence>();
		special.addAll(u.special);
		// au clic de la souris > afficher les infos
		final Monstre objet = this;
		img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.getMoi().unite_description = objet;
				return false;
			}
		});
	}

	public Label getBarre_vie() {
		return barre_vie;
	}

	public void setBarre_vie(Label barre_vie) {
		this.barre_vie = barre_vie;
	}

	// permet d'envoyer le monstre sur le terrain de j
	public void attaque_joueur(Joueur j) {
		this.vie = this.vie_max;
		setVie(vie);
		moi = j;
		p = moi.getSpawn();
		position_exacte = new Position(j.getSpawn().x * Unite.cote,
				j.getSpawn().y * Unite.cote);
	}

	public ArrayList<Competence> getSpecial() {
		return special;
	}

	public void setSpecial(ArrayList<Competence> special) {
		this.special = special;
	}

	public void addSpecial(Competence special) {
		this.special.add(special);
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		if (this.vie == 0) {
			this.vie_max = vie;
			this.barre_vie.setText(this.vie + "/" + this.vie_max);
		} else {
			this.vie = vie;
			if (vie < 0) {
				this.vie = 0;
			}
			this.barre_vie.setText(this.vie + "/" + this.vie_max);
		}
		barre_vie.setText(this.vie + " / " + vie_max);

		float pour_cent = ((float) vie / (float) vie_max);

		if (pour_cent < 0.1f) {
			// rouge
			barre_vie.setColor(new Color(1, 0, 0, 1));
		}
		if (pour_cent >= 0.1f && pour_cent < 0.3f) {
			// jaune
			barre_vie.setColor(new Color(1, 1, 0, 1));
		}
		if (pour_cent >= 0.3f && pour_cent < 0.8f) {
			// vert
			barre_vie.setColor(new Color(0, 1, 0, 1));
		}

		if (pour_cent >= 0.8f) {
			// bleu
			barre_vie.setColor(new Color(0, 0, 1, 1));
		}

	}

	public boolean isFou() {
		return fou;
	}

	public void setFou(boolean fou) {
		this.fou = fou;
	}

	// permet au monstre de se déplacer sur le terrain
	public boolean deplacement_auto(float temps) {
		if (this.vie <= 0) {
			mourrir();
			return true;
		}
		float reduction_vitesse = 1f;
		if (!this.statut.isEmpty()) {
			Iterator<Statut> it = this.statut.iterator();
			while (it.hasNext()) {
				Statut o = it.next();
				if (o.valable()) {
					if (o.getEffet().equals(Tour.Specialite.U_poison)) {
						if (reduction_vitesse > 0.5f){
							reduction_vitesse = 0.5f;
						}
					}
					if (o.getEffet().equals(Tour.Specialite.U_glue)) {
						if (reduction_vitesse > 0.001f){
							reduction_vitesse = 0.001f;
						}
					}
					o.update(temps);
				}
			}
		}

		if (this.special.contains(Competence.vol)) {
			float modulo = (moi.getBase().x * Unite.cote - position_exacte.x);
			if (modulo < (moi.getBase().y * Unite.cote - position_exacte.y)) {
				modulo = (moi.getBase().y * Unite.cote - position_exacte.y);
			}
			Position avancer = new Position(
					((moi.getBase().x * Unite.cote - position_exacte.x) / (modulo))
							* vitesse * temps * reduction_vitesse,
					((moi.getBase().y * Unite.cote - position_exacte.y) / (modulo))
							* vitesse * temps * reduction_vitesse);
			position_exacte = new Position(position_exacte.x + avancer.x,
					position_exacte.y + avancer.y);
			p = new Position((int) (position_exacte.x / Unite.cote),
					(int) (position_exacte.y / Unite.cote));
		} else {
			boolean se_rapproche;
			Chemin c = new Chemin(moi.getTerrain(), p, moi.getBase());
			if (c.get_deplacement().isEqual(new Position(0, 0))
					&& !p.isEqual(moi.getBase())) {
				// si il n'y a pas de chemin et que nous n'avont pas atteint la
				// base adverse
				setFou(true);
			}
			// case juste à côté :
			position_a_atteindre = new Position(p, c.get_deplacement());
			if (fou) {
				Position fin = moi.get_tour_a_peter();
				if (fin != null) {
					moi.peter_tour(fin);
				}
				mourrir();
				return true;
			}

			// pour un virage :
			float ecart = position_exacte.distance(new Position(
					position_a_atteindre.x * Unite.cote, position_a_atteindre.y
							* Unite.cote));
			// vecteur de décalage par rapport à la position actuelle
			Position avancer = new Position(
					(c.get_deplacement().x * vitesse * reduction_vitesse * temps),
					(c.get_deplacement().y * vitesse * reduction_vitesse * temps));

			// position au pixel près
			position_exacte = new Position(position_exacte.x + avancer.x,
					position_exacte.y + avancer.y);
			// test pour un virage
			se_rapproche = (ecart > position_exacte.distance(new Position(
					position_a_atteindre.x * Unite.cote, position_a_atteindre.y
							* Unite.cote)));
			if (!se_rapproche) {
				p = position_a_atteindre;
				position_exacte = new Position(p.x * Unite.cote, p.y
						* Unite.cote);
			}
		}
		// mise à jour de la position
		img.setPosition(position_exacte.x, position_exacte.y);
		// mise à jour de la barre de vie
		barre_vie.setPosition(position_exacte.x - barre_vie.getPrefWidth() / 2
				+ img.getWidth() / 2,
				position_exacte.y - barre_vie.getPrefHeight());
		if (p.isEqual(moi.getBase())) {
			moi.setVie(moi.getVie() - 1);
			mourrir();
			return true;
		}
		barre_vie.setVisible(true);
		img.setVisible(true);
		return false;
	}

	@Override
	public type_unite getType_unite() {
		// TODO Auto-generated method stub
		return type_unite.m;
	}

	// affichage des informations du monstre dans le magasin
	public void afficher_boutique(int x, int y) {
		// TODO Auto-generated method stub
		infos.setText(nom + " \n" + prix.getPrix_achat() + " $");
		super.afficher_boutique(x, y);
	}

	// associer les attributs du monstre et le stage
	public void init_panel(Stage s, final Joueur j) {
		// TODO Auto-generated method stub
		final Monstre temp = this;
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

		barre_vie = new Label(vie + "/" + vie_max, TowerDefense.skin);
		infos.setText(vie_max + " vies " + vitesse + " px/s \n" + income
				+ " $/s : " + prix.getPrix_achat() + "$");
		barre_vie.setVisible(false);

		cacher();
		ref_partie.associer(temp);
	}

	public void mourrir() {
		vie = 0;
		img.setVisible(false);
		barre_vie.setVisible(false);
		img.remove();
		barre_vie.remove();
	}

	@Override
	public void afficher_description() {
		// TODO Auto-generated method stub
		float centre = Partie.l_terrain
				+ (Gdx.graphics.getWidth() - Partie.l_terrain) / 2;
		img_boutique.setPosition(centre - img_boutique.getWidth() / 2,
				Partie.h_terrain - 100);
		String infos_m = "nom : " + nom + "\n \n" + "vitesse : " + vitesse
				+ " px/s \n" + "income : " + income + "\n" + "prix achat : "
				+ prix.getPrix_achat();
		int p_vente = prix.getPrix_vente();
		if (p_vente != 0) {
			infos_m += "\n" + "prix re-vente : " + prix.getPrix_vente();
		}
		if (vie != 0) {
			infos_m += "\n vie : " + vie + " / " + vie_max;
		} else {
			infos_m += "\n vie : " + vie_max + " / " + vie_max;
		}
		infos.setText(infos_m);
		infos.setPosition(centre - infos.getPrefWidth() / 2, Partie.h_terrain
				- 100 - infos.getPrefHeight());
		infos.setVisible(true);
		img_boutique.setVisible(true);
	}

	public Joueur getMoi() {
		return moi;
	}

	public Position getPosition_exacte() {
		return position_exacte;
	}

	public void setPosition_exacte(Position position_exacte) {
		this.position_exacte = position_exacte;
	}

	public void addStatut(Tour.Specialite c) {
		// TODO Auto-generated method stub
		if (statut.contains(c)) {
			Iterator<Statut> it = statut.iterator();
			while (it.hasNext()) {
				Statut s = it.next();
				if (s.equals(c)) {
					s.remise_a_zero();
				}
			}
		} else {
			statut.add(new Statut(c));
		}
	}

	public ArrayList<Statut> getStatut() {
		return this.statut;
	}
}
