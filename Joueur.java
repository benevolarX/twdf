import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Joueur implements un_panel {
	private final static int MAX_ACHAT = 31;
	public final static int OR_DE_BASE = 10000;
	public final static int VIE_DE_BASE = 10;
	public final static int LARGEUR_DE_BASE = 20; // nombre de case de largeur
													// du terrain
	public final static int HAUTEUR_DE_BASE = 15; // nombre de case de hauteur
													// du terrain
	private String nom;
	private int or;
	private int vie;
	private int or_par_seconde;
	private float seconde;

	private Terrain terrain; // avec cases du terrain et tours placé dessus
	private Magasin shop; // acheter tour / monstre / obstacles
	// liste des unités achetés par le joueur
	private Banque<Tour> banque_tour;
	private Banque<Monstre> banque_monstre;
	private Banque<Emplacement> banque_emp;
	private Partie ref_partie; // permet d'obtenir des infos sur l'adversaire
								// avec des get
	private ArrayList<Monstre> ennemis;

	private Label ma_vie;
	private Label mon_or;
	private Image img_vie;
	private Image img_or;

	private TextButton magazin; // pour acheter des unités
	private TextButton achats; // pour voir ses achats (avant de poser)
	private TextButton carte; // pour voir les infos des unités (upgrade)

	private TextButton btn_monter;
	private TextButton btn_descendre;

	private pointeur cible; // unité ciblé pour afficher ses infos en détaille

	public Unite unite_description;
	public Unite unite_a_poser;

	public Banque<Tour> getBanque_tour() {
		return banque_tour;
	}

	public void setBanque_tour(Banque<Tour> banque_tour) {
		this.banque_tour = banque_tour;
	}

	public Banque<Emplacement> getBanque_emp() {
		return banque_emp;
	}

	public void setBanque_emp(Banque<Emplacement> banque_emp) {
		this.banque_emp = banque_emp;
	}

	public static enum pointeur {
		mag, bank, modif;
	};

	public Joueur(String nom, Partie partie, Stage stage) {
		this.ref_partie = partie;
		Terrain.ref_partie = partie;
		or = OR_DE_BASE;
		vie = VIE_DE_BASE;
		terrain = new Terrain(LARGEUR_DE_BASE, HAUTEUR_DE_BASE);
		shop = new Magasin(partie);
		this.nom = nom;
		this.cible = null;
		this.banque_tour = new Banque<Tour>();
		this.banque_monstre = new Banque<Monstre>();
		this.banque_emp = new Banque<Emplacement>();
		this.ennemis = new ArrayList<Monstre>();
		this.or_par_seconde = 1;
		this.seconde = 0;
	}

	public void init_panel(Stage mon_stage, final Joueur j) {
		img_vie = new Image(
				ImageManager.GetImage("./assets/images/jeu/vie.png"));
		img_vie.setPosition(Partie.l_terrain + 3, 3);
		mon_stage.addActor(img_vie);

		ma_vie = new Label("" + vie, TowerDefense.skin);
		ma_vie.setPosition(Partie.l_terrain + 3 + img_vie.getWidth(), 3);
		mon_stage.addActor(ma_vie);

		img_or = new Image(ImageManager.GetImage("./assets/images/jeu/or.png"));
		img_or.setPosition(Gdx.graphics.getWidth() - img_or.getWidth() - 3, 3);
		mon_stage.addActor(img_or);

		mon_or = new Label("" + or, TowerDefense.skin);
		mon_or.setPosition(Gdx.graphics.getWidth() - img_or.getWidth() - 3
				- mon_or.getWidth(), 3);
		mon_stage.addActor(mon_or);

		magazin = new TextButton("magasin", TowerDefense.skin);
		magazin.setPosition(Partie.l_terrain + 3, Gdx.graphics.getHeight() - 3
				- magazin.getHeight());
		magazin.setColor(0, 0, 10, 10);
		magazin.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y, int p,
					int bouton) {
				if (cible != pointeur.mag) {
					cible = pointeur.mag;
					menu();
				}
				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				magazin.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				magazin.setColor(0, 0, 10, 10);
			}
		});
		mon_stage.addActor(magazin);

		achats = new TextButton("mes achats", TowerDefense.skin);
		achats.setPosition(magazin.getX() + magazin.getWidth(),
				Gdx.graphics.getHeight() - 3 - achats.getHeight());
		achats.setColor(0, 0, 10, 10);
		achats.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y, int p,
					int bouton) {
				if (cible != pointeur.bank) {
					cible = pointeur.bank;
					menu();
				}

				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				achats.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				achats.setColor(0, 0, 10, 10);
			}
		});
		mon_stage.addActor(achats);

		carte = new TextButton("info", TowerDefense.skin);
		carte.setPosition(achats.getX() + achats.getWidth(),
				Gdx.graphics.getHeight() - 3 - carte.getHeight());
		carte.setColor(0, 0, 10, 10);
		carte.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y, int p,
					int bouton) {
				if (cible != pointeur.modif) {
					cible = pointeur.modif;
					menu();
				}
				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				carte.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				carte.setColor(0, 0, 10, 10);
			}
		});
		mon_stage.addActor(carte);

		float decalage = (Gdx.graphics.getWidth() - (carte.getX() + carte
				.getWidth())) / 4;
		carte.setPosition(carte.getX() + 3 * decalage, Gdx.graphics.getHeight()
				- 3 - carte.getHeight());
		achats.setPosition(achats.getX() + 2 * decalage,
				Gdx.graphics.getHeight() - 3 - achats.getHeight());
		magazin.setPosition(magazin.getX() + decalage, Gdx.graphics.getHeight()
				- 3 - magazin.getHeight());

		btn_monter = new TextButton("precedent", TowerDefense.skin);
		btn_monter.setPosition(Partie.l_terrain + 3,
				(int) (3 + img_or.getHeight()));
		btn_monter.setColor(0, 0, 10, 10);
		btn_monter.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y, int p,
					int bouton) {
				if (cible == pointeur.mag) {
					shop.monter();
					menu();
				}
				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_monter.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_monter.setColor(0, 0, 10, 10);
			}
		});
		mon_stage.addActor(btn_monter);

		btn_descendre = new TextButton("suivant", TowerDefense.skin);
		btn_descendre.setPosition(
				Gdx.graphics.getWidth() - btn_descendre.getWidth() - 3,
				(int) (3 + img_or.getHeight()));
		btn_descendre.setColor(0, 0, 10, 10);
		btn_descendre.addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y, int p,
					int bouton) {
				if (cible == pointeur.mag) {
					shop.descendre();
					menu();
				}
				return false;
			}

			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_descendre.setColor(0, 10, 0, 10);
			}

			public void exit(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				btn_descendre.setColor(0, 0, 10, 10);
			}
		});
		mon_stage.addActor(btn_descendre);
	}

	public String getNom() {
		return nom;
	}

	public int getOr() {
		return or;
	}

	public void setOr(int or) {
		this.or = or;
		mon_or.setText("" + or);
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
		ma_vie.setText("" + vie);
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public Magasin getShop() {
		return shop;
	}

	public Position getSpawn() {
		return terrain.getSpawn();
	}

	public void setSpawn(Position spawn) {
		terrain.setSpawn(spawn);
	}

	public Position getBase() {
		return terrain.getBase();
	}

	public void setBase(Position base) {
		terrain.setBase(base);
	}

	public ArrayList<Monstre> getEnnemis() {
		return ennemis;
	}

	public void add_Ennemis(Monstre m) {
		m.attaque_joueur(this);
		this.ennemis.add(m);
		ref_partie.associer(m);
	}

	public boolean acheter(Unite u) {
		if (u.getPrix().isAchetable()) {
			// si l'unite est achetable
			if (or - u.getPrix().getPrix_achat() >= 0) {
				if (!sac_plein()) {
					// si on a assez d'or
					switch (u.getType_unite()) {
					case m:
						Monstre m = new Monstre((Monstre) u); // création de
																// copie
						ref_partie.associer(m);
						banque_monstre.add(m);
						break;
					case t:
						Tour t = new Tour((Tour) u);
						ref_partie.associer(t);
						banque_tour.add(t); // création de copie
						break;
					case e:
						Emplacement e = new Emplacement((Emplacement) u);
						ref_partie.associer(e);
						banque_emp.add(e);
						break;
					default:
						System.err.println("erreur cet objet est une unite");
						break;
					}
					setOr(or - u.getPrix().getPrix_achat());
				}
			}
		}
		return false;
	}

	public void menu() {
		if (cible == pointeur.bank) {
			unite_description = null;
			masquer_inventaire();
			shop.cacher();
			afficher_unites(
					Partie.l_terrain + 3 + 15,
					(int) (3 + img_or.getHeight() + btn_monter.getHeight() + 5),
					(int) (Gdx.graphics.getHeight() - 12 - img_or.getHeight() - magazin
							.getHeight()));
		}
		if (cible == pointeur.mag) {
			unite_description = null;
			masquer_inventaire();
			shop.afficher(
					Partie.l_terrain + 3,
					(int) (3 + img_or.getHeight() + btn_monter.getHeight() + 15),
					(int) (Gdx.graphics.getHeight() - 12 - img_or.getHeight() - magazin
							.getHeight()));
		}
		if (cible == pointeur.modif) {
			unite_description = null;
			shop.cacher();
			masquer_inventaire();
		}
	}

	private void masquer_inventaire() {
		// TODO Auto-generated method stub
		banque_tour.masquer();
		banque_monstre.masquer();
		banque_emp.masquer();
	}

	public void afficher_unites(int x, int y, int h) {
		int x2 = 0;
		int y2 = 0;
		int decalage = 5;
		boolean continuer = true;
		Iterator<Monstre> it = banque_monstre.iterator();
		while (it.hasNext() && continuer) {
			Monstre m_a = it.next();
			m_a.img_boutique.setPosition(x + x2
					* (m_a.img_boutique.getWidth() + decalage), y
					+ (m_a.img_boutique.getHeight() + decalage) * y2);
			m_a.img_boutique.setVisible(true);

			x2++;
			if (x2 >= 4) {
				y2++;
				x2 %= 4;
			}
		}
		Iterator<Tour> it_t = banque_tour.iterator();
		while (it_t.hasNext() && continuer) {
			Tour t_a = it_t.next();
			t_a.img_boutique.setPosition(x + x2
					* (t_a.img_boutique.getWidth() + decalage), y
					+ (t_a.img_boutique.getHeight() + decalage) * y2);
			t_a.img_boutique.setVisible(true);

			x2++;
			if (x2 >= 4) {
				y2++;
				x2 %= 4;
			}
		}

		Iterator<Emplacement> it_e = banque_emp.iterator();
		while (it_e.hasNext() && continuer) {
			Emplacement e_a = it_e.next();
			e_a.img_boutique.setPosition(x + x2
					* (e_a.img_boutique.getWidth() + decalage), y
					+ (e_a.img_boutique.getHeight() + decalage) * y2);
			e_a.img_boutique.setVisible(true);

			x2++;
			if (x2 >= 4) {
				y2++;
				x2 %= 4;

			}
		}

	}

	public void update(float deltaTime, boolean m2) {
		// TODO Auto-generated method stub

		if (m2) {
			// deplacement des monstres
			for (int i = 0; i < ennemis.size(); i++) {
				Monstre m = ennemis.get(i);
				if (m.deplacement_auto(deltaTime)) {
					ennemis.remove(i);
				}
			}

			// mis à jour de la carte
			terrain.update(deltaTime);

			if (seconde > 1) {
				this.setOr(or + or_par_seconde); // income
				this.seconde = 0;
			} else {
				this.seconde += deltaTime;
			}

		}

		// mis en place de bouton pour poser une unité
		if (ref_partie.getMoi().unite_description != null
				&& this.cible == pointeur.bank) {
			if (!unite_description.action) {
				unite_description.init_action(this);
			}
			unite_description.afficher_actions();
		}

	}

	public pointeur getCible() {
		return cible;
	}

	public void setCible(pointeur cible) {
		this.cible = cible;
	}

	public void peter_tour(Position tour_a_peter) {
		terrain.peter(tour_a_peter);
	}

	public Position get_tour_a_peter() {
		int nb_tour = terrain.get_nb_tour();
		double alea = Math.random();
		int tour = (int) (alea * nb_tour);
		return terrain.get_tour(tour);
	}

	public void vendre(Unite u) {
		// TODO Auto-generated method stub
		if (u.getPrix().isVendable()) {
			int n_or = or + u.getPrix().getPrix_vente();
			boolean vendu = false;
			if (n_or >= 0) {
				switch (u.getType_unite()) {
				case m:
					if (((Monstre) u).getMoi() == null) {
						vendu = banque_monstre.detruire((Monstre) u);
					}
					break;
				case t:
					vendu = banque_tour.detruire((Tour) u);
					break;
				case e:
					vendu = banque_emp.detruire((Emplacement) u);
					break;
				default:
					n_or = or;
					System.out
							.println("erreur Joueur::vendre(u) => impossible de vendre");
					break;
				}
				setOr(n_or);
				if (vendu) {
					ref_partie.getMoi().unite_description = null;
				}
				menu();
			}
		}
	}

	public void poser(Unite u) {
		// TODO Auto-generated method stub
		// poser tour / emplacement ou envoyer monstre
		switch (u.getType_unite()) {
		case m:
			Monstre m = new Monstre((Monstre) u);
			add_Ennemis(m); // envoyer une copie de nos monstres sur nous
			u.cacher();
			banque_monstre.remove((Monstre) u);
			menu();
			this.setOr_par_seconde(or_par_seconde + m.getIncome());
			break;
		case t:
		case e:
			// en attente de position
			Terrain.place = null;
			Partie.attend_clic = true;
			unite_a_poser = u;
			break;
		default:
			break;
		}
	}

	private boolean sac_plein() {
		// TODO Auto-generated method stub
		return ((banque_emp.size() + banque_monstre.size() + banque_tour.size()) > MAX_ACHAT);
	}

	public int getOr_par_seconde() {
		return or_par_seconde;
	}

	public void setOr_par_seconde(int or_par_seconde) {
		this.or_par_seconde = or_par_seconde;
	}

}
