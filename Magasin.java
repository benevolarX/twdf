import java.util.*;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Magasin {
	public static int page;
	public final static int nb_page_max = 9;

	public Partie ref_partie;
	private Banque<Emplacement> b_surfaces;
	private Banque<Tour> b_tours;
	private Banque<Monstre> b_monstres;

	public Magasin(Partie partie) {
		Magasin.page = 0;
		this.ref_partie = partie;
		b_surfaces = new Banque<Emplacement>();
		b_tours = new Banque<Tour>();
		b_monstres = new Banque<Monstre>();

		init_surface();
		init_tour();
		init_monstre();
	}

	public ArrayList<Monstre> getMonstres() {
		return b_monstres.getMonstres();
	}

	public ArrayList<Tour> getTours() {
		return b_tours.getTours();
	}

	private void init_monstre() {
		// monstre normal
		Monstre normal = new Monstre();
		normal.setNom("normal");
		normal.setPrix(new Achetable(500, 250, false, true));
		normal.setVie(500);
		normal.setVitesse(40);
		normal.setIncome(5);
		normal.setImage("./assets/images/jeu/monstre.png");
		final Monstre objet = normal;
		normal.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(objet);
				return false;
			}
		});
		b_monstres.add(normal);

		// monstre rapide
		Monstre rapide = new Monstre();
		rapide.setNom("rapide");
		rapide.setPrix(new Achetable(400, 200, false, true));
		rapide.setVie(300);
		rapide.setVitesse(80);
		rapide.setIncome(3);
		rapide.setImage("./assets/images/jeu/monstre_rapide.png");
		final Monstre objet2 = rapide;
		rapide.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(objet2);
				return false;
			}
		});
		b_monstres.add(rapide);

		// monstre lent qui donne de l'or
		Monstre lent = new Monstre();
		lent.setNom("lent");
		lent.setPrix(new Achetable(1000, 500, false, true));
		lent.setVie(5000);
		lent.setVitesse(5);
		lent.setIncome(20);
		lent.setImage("./assets/images/jeu/monstre_lent.png");
		final Monstre objet3 = lent;
		lent.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(objet3);
				return false;
			}
		});
		b_monstres.add(lent);

		// monstre tres rapide
		Monstre tres_rapide = new Monstre();
		tres_rapide.setNom("tres_rapide");
		tres_rapide.setPrix(new Achetable(750, 375, false, true));
		tres_rapide.setVie(200);
		tres_rapide.setVitesse(120);
		tres_rapide.setIncome(2);
		tres_rapide.setImage("./assets/images/jeu/monstre_rapide.png");
		final Monstre objet4 = tres_rapide;
		tres_rapide.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(objet4);
				return false;
			}
		});
		b_monstres.add(tres_rapide);

		// monstre invisible
		final Monstre invisible = new Monstre();
		invisible.setNom("invisible");
		invisible.setPrix(new Achetable(3000, 1500, false, true));
		invisible.setVie(800);
		invisible.setVitesse(15);
		invisible.setIncome(15);
		invisible.addSpecial(Monstre.Competence.invisibilite);
		invisible.setImage("./assets/images/jeu/monstre_invisible.png");
		invisible.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(invisible);
				return false;
			}
		});
		b_monstres.add(invisible);

		// monstre volant
		final Monstre vol = new Monstre();
		vol.setNom("vollant");
		vol.setPrix(new Achetable(3000, 1500, false, true));
		vol.setVie(2500);
		vol.setVitesse(15);
		vol.setIncome(15);
		vol.addSpecial(Monstre.Competence.vol);
		vol.setImage("./assets/images/jeu/monstre_vent.png");
		vol.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(vol);
				return false;
			}
		});
		b_monstres.add(vol);

		// monstre volant et invisible
		final Monstre fort = new Monstre();
		fort.setNom("fort");
		fort.setPrix(new Achetable(10000, 5000, false, true));
		fort.setVie(1500);
		fort.setVitesse(4);
		fort.setIncome(30);
		fort.addSpecial(Monstre.Competence.vol);
		fort.addSpecial(Monstre.Competence.invisibilite);
		fort.setImage("./assets/images/jeu/monstre_fort.png");
		fort.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(fort);
				return false;
			}
		});
		b_monstres.add(fort);
	}

	private void init_tour() {
		// tour normale
		final Tour normale = new Tour();
		normale.setNom("normale");
		normale.setPrix(new Achetable(500, 250, true, true));
		normale.setImage("./assets/images/jeu/tour_normale.png");
		normale.setDegats(30);
		normale.setVitesse_attaque(3);
		normale.setPortee(3);
		normale.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(normale);
				return false;
			}
		});
		b_tours.add(normale);

		// tour anti aerien
		final Tour anti_aerien = new Tour();
		anti_aerien.setNom("anti aerien");
		anti_aerien.addCompetences(Tour.Specialite.U_anti_aerien);
		anti_aerien.setPrix(new Achetable(1000, 500, true, true));
		anti_aerien.setImage("./assets/images/jeu/tour_anti_aerien.png");
		anti_aerien.setDegats(100);
		anti_aerien.setVitesse_attaque(1);
		anti_aerien.setPortee(8);
		anti_aerien.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(anti_aerien);
				return false;
			}
		});
		b_tours.add(anti_aerien);

		// tour anti invisible
		final Tour anti_invisible = new Tour();
		anti_invisible.setNom("anti invisible");
		anti_invisible.addCompetences(Tour.Specialite.U_anti_invisible);
		anti_invisible.setPrix(new Achetable(1000, 500, true, true));
		anti_invisible.setImage("./assets/images/jeu/tour_invisible.png");
		anti_invisible.setDegats(10);
		anti_invisible.setVitesse_attaque(10);
		anti_invisible.setPortee(4);
		anti_invisible.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(anti_invisible);
				return false;
			}
		});
		b_tours.add(anti_invisible);

		// tour feu
		final Tour feu = new Tour();
		feu.setNom("feu");
		feu.setDegats(1);
		feu.setVitesse_attaque(4);
		feu.setPortee(3);
		feu.addCompetences(Tour.Specialite.U_feu);
		feu.setPrix(new Achetable(1200, 600, true, true));
		feu.setImage("./assets/images/jeu/tour_feu.png");
		feu.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(feu);
				return false;
			}
		});
		b_tours.add(feu);

		// tour colle
		final Tour colle = new Tour();
		colle.setNom("colle");
		colle.setDegats(10);
		colle.setVitesse_attaque(1);
		colle.setPortee(3);
		colle.addCompetences(Tour.Specialite.U_glue);
		colle.setPrix(new Achetable(800, 400, true, true));
		colle.setImage("./assets/images/jeu/tour_glue.png");
		colle.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(colle);
				return false;
			}
		});
		b_tours.add(colle);

		// tour poison
		final Tour poison = new Tour();
		poison.setNom("poison");
		poison.setDegats(5);
		poison.setVitesse_attaque(10);
		poison.setPortee(3);
		poison.addCompetences(Tour.Specialite.U_poison);
		poison.setPrix(new Achetable(800, 400, true, true));
		poison.setImage("./assets/images/jeu/tour_poison.png");
		poison.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(poison);
				return false;
			}
		});
		b_tours.add(poison);
	}

	private void init_surface() {
		// obstable :
		Emplacement obstacle = new Emplacement();
		obstacle.setNom("obstacle");
		obstacle.setConstructible(false);
		obstacle.setPassable(false);
		obstacle.setPrix(new Achetable(20, -100, true, true));
		obstacle.setImage("./assets/images/jeu/mur.png");
		final Emplacement objet6 = obstacle;
		obstacle.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(objet6);
				return false;
			}
		});
		b_surfaces.add(obstacle);

		// obstable constructible :
		Emplacement obstacle_constructible = new Emplacement();
		obstacle_constructible.setNom("pre-tour");
		obstacle_constructible.setConstructible(true);
		obstacle_constructible.setPassable(false);
		obstacle_constructible.setPrix(new Achetable(100, 50, true, true));
		obstacle_constructible
				.setImage("./assets/images/jeu/obstacle_constructible.png");
		final Emplacement objet7 = obstacle_constructible;
		obstacle_constructible.img_boutique.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				ref_partie.info(objet7);
				return false;
			}
		});
		b_surfaces.add(obstacle_constructible);
	}

	public void afficher(int x, int y, int h) {
		int n_y = y;
		n_y = b_tours.afficher(x, n_y, h - (n_y - y));
		n_y = b_surfaces.afficher(x, n_y, h - (n_y - y));
		n_y = b_monstres.afficher(x, n_y, h - (n_y - y));
	}

	public ArrayList<Emplacement> getSurfaces() {
		// return surfaces;
		return b_surfaces.getEmplacements();
	}

	public void cacher() {
		// cacher le magasin
		b_surfaces.masquer();
		b_monstres.masquer();
		b_tours.masquer();
	}

	public void descendre() {
		// TODO Auto-generated method stub
		if (Magasin.page > 0) {
			Magasin.page--;
			if (b_monstres.page > 0) {
				b_monstres.page--;
			} else {
				if (b_surfaces.page > 0) {
					b_surfaces.page--;
				} else {
					if (b_tours.page > 0) {
						b_tours.page--;
					}
				}
			}
		}
	}

	public void monter() {
		if (Magasin.page < get_nb_u() - Magasin.nb_page_max) {
			Magasin.page++;
			if (b_tours.page < b_tours.get_nb_u()) {
				b_tours.page++;
			} else {
				if (b_surfaces.page < b_surfaces.get_nb_u()) {
					b_surfaces.page++;
				} else {
					if (b_monstres.page < b_monstres.get_nb_u()) {
						b_monstres.page++;
					}
				}
			}
		}
	}

	public int get_nb_u() {
		// TODO Auto-generated method stub
		return b_surfaces.get_nb_u() + b_monstres.get_nb_u()
				+ b_tours.get_nb_u();
	}

	public void debloquer_monstres() {
		// TODO Auto-generated method stub
		b_monstres.debloquer();
	}
}
