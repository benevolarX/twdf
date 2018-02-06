import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Tour extends Unite implements un_panel {

	private Specialite competences;
	private int vitesse_attaque;
	private int degats;
	private int portee;

	private float derniere_attaque;

	private ArrayList<Projectile> mes_projectiles;

	private Monstre cible;

	public static enum Specialite {
		U_anti_aerien, U_anti_invisible, U_feu, U_glue, U_poison
	}

	// constructeur par défaut
	public Tour() {
		init_unite();
		competences = null;
		vitesse_attaque = 5;
		degats = 5;
		setImage("./assets/images/jeu/tour_normale.png");
		portee = 4;
	}

	// constructeur de copie
	public Tour(Tour u) {
		init_unite();
		copie(u);
		competences = null;
		if (u.getCompetences() != null) {
			competences = (u.getCompetences());
		}
		vitesse_attaque = u.vitesse_attaque;
		degats = u.degats;
		portee = u.getPortee();
		setImage(u.image);

		mes_projectiles = new ArrayList<Projectile>();
		derniere_attaque = 0;

		final Tour objet = this;
		img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.getMoi().unite_description = objet;
				return false;
			}
		});
	}

	public Specialite getCompetences() {
		// TODO Auto-generated method stub
		return this.competences;
	}

	public void addCompetences(Specialite c) {
		this.competences = c;
	}

	public int getVitesse_attaque() {
		return vitesse_attaque;
	}

	public void setVitesse_attaque(int vitesse_attaque) {
		this.vitesse_attaque = vitesse_attaque;
	}

	public int getDegats() {
		return degats;
	}

	public void setDegats(int degats) {
		this.degats = degats;
	}

	public void init_panel(Stage s, final Joueur j) {
		final Tour temp = this;
		btn_acheter.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				j.acheter(temp);
				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_acheter.setColor(0, 1, 0, 1);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_acheter.setColor(0, 0, 1, 1);
			}
		});
		cacher();
		ref_partie.associer(temp);
	}

	public void afficher_boutique(int x, int y) {
		infos.setText(nom + "\n" + degats + "deg/" + vitesse_attaque + "s : "
				+ prix.getPrix_achat() + " $");
		super.afficher_boutique(x, y);
	}

	@Override
	public type_unite getType_unite() {
		// TODO Auto-generated method stub
		return type_unite.t;
	}

	@Override
	public void afficher_description() {
		// TODO Auto-generated method stub
		// afficher les infos de la tour
		float centre = Partie.l_terrain
				+ (Gdx.graphics.getWidth() - Partie.l_terrain) / 2;
		img_boutique.setPosition(centre - img_boutique.getWidth() / 2,
				Partie.h_terrain - 100);
		String infos_m = "nom : " + nom + "\n \n" + "vitesse d'attaque : "
				+ vitesse_attaque + "\n" + "degats : " + degats + "\n"
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

	public Monstre getCible() {
		// TODO Auto-generated method stub
		// retourne la cible de la tour
		return cible;
	}

	public ArrayList<Projectile> getMes_projectiles() {
		return mes_projectiles;
	}

	public void setMes_projectiles(ArrayList<Projectile> mes_projectiles) {
		this.mes_projectiles = mes_projectiles;
	}

	public void setCible(Monstre cible) {
		this.cible = cible;
	}

	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		if (!mes_projectiles.isEmpty()) {
			Iterator<Projectile> it = mes_projectiles.iterator();
			while (it.hasNext()) {
				Projectile prj = it.next();
				if (prj.update(deltaTime)) {
					mes_projectiles.remove(it);
				}
			}
		}

		// les tours attaques
		if (derniere_attaque < (float) ((float) 5 / (float) vitesse_attaque)) {
			derniere_attaque += deltaTime;
		} else {
			if (cible != null) {
				if (cible.getVie() > 0) {
					if (cible.getPosition().distance(p) < portee) {
						if (cible.getSpecial().contains(
								Monstre.Competence.invisibilite)) {
							if (autorisation_tir(cible)){
								tirer();
							}else {
								changer_cible();
							}
						} else {
							tirer();
						}
					} else {
						changer_cible();
					}
				} else {
					changer_cible();
				}
			} else {
				changer_cible();
			}
		}

	}

	private boolean autorisation_tir(Monstre m) {

		if (this.competences != null) {
			if (this.competences
					.equals(Tour.Specialite.U_anti_invisible)) {
				return true;
			} else {
				Iterator<Statut> its = m.getStatut()
						.iterator();
				while (its.hasNext()) {
					Statut st = its.next();
					if (st.getEffet()
							.equals(Tour.Specialite.U_anti_invisible)) {
						if (st.valable()) {
							return true;
						}
					}
				}
			}
		} else {
			Iterator<Statut> its = m.getStatut().iterator();
			while (its.hasNext()) {
				Statut st = its.next();
				if (st.getEffet().equals(
						Tour.Specialite.U_anti_invisible)) {
					if (st.valable()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private void tirer() {
		// TODO Auto-generated method stub
		if (!mes_projectiles.add(new Projectile(this))) {
			mes_projectiles.clear();
		}
		derniere_attaque = 0;
	}

	private void changer_cible() {
		// TODO Auto-generated method stub
		if (competences != null) {
			if (competences.equals(Specialite.U_feu)) {
				degats = 1;
			}
		}

		if (!ref_partie.getMoi().getEnnemis().isEmpty()) {
			Iterator<Monstre> it = ref_partie.getMoi().getEnnemis().iterator();
			while (it.hasNext()) {
				Monstre m = it.next();
				if (m.getVie() > 0) {
					if (m.getPosition().distance(p) < portee) {
						if (m.getSpecial().contains(
								Monstre.Competence.invisibilite)) {
							if (autorisation_tir(m)){
								cible = m;
							}
						} else {
							cible = m;
						}
					}
				}
			}
		}
	}

	public int getPortee() {
		return portee;
	}

	public void setPortee(int portee) {
		this.portee = portee;
	}

}
