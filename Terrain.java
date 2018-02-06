import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Terrain {

	public static Position place;
	public static Partie ref_partie;

	private int largeur;
	private int hauteur;
	private Emplacement[][] monde; // cases du jeu (avec leur tour)
	private Position spawn;
	private Position base;

	public Terrain(int l, int h) {
		place = null;
		largeur = l;
		hauteur = h;
		monde = new Emplacement[l][h];
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				monde[i][j] = new Emplacement(i, j);
			}
		}
		int x, y;
		x = (int) ((largeur / 2 - 1) * Math.random());
		y = (int) ((hauteur / 2 - 1) * Math.random());
		// placement du spawn aléatoire
		spawn = new Position(x, y);
		monde[x][y] = new Emplacement(spawn, new Achetable(0, 0, false, false));
		monde[x][y].setImage("./assets/images/jeu/spawn.png");
		x = (int) (largeur / 2 * Math.random() + largeur / 2);
		y = (int) (hauteur / 2 * Math.random() + hauteur / 2);
		// placement de la base aléatoire
		base = new Position(x, y);
		monde[x][y] = new Emplacement(base, new Achetable(0, 0, false, false));
		monde[x][y].setImage("./assets/images/jeu/base.png");
	}

	public int getLargeur() {
		return largeur;
	}

	public void setSpawn(Position spawn) {
		this.spawn = spawn;
	}

	public void setBase(Position base) {
		this.base = base;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public Emplacement get_case(Position p) {
		if (p.x >= 0 && p.x < largeur && p.y >= 0 && p.y < hauteur) {
			return (monde[(int) p.x][(int) p.y]);
		}
		return (null);
	}

	public void afficher(int x, int y, int l, int h, Stage s) {
		// TODO Auto-generated method stub
		final float l_img = l / largeur;
		final float h_img = h / hauteur;
		boolean ch;
		Position temp;
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				ch = false;
				temp = new Position(i, j);
				monde[i][j].updateImage();
				if (temp.isEqual(spawn)) {
					monde[i][j].setImage("./assets/images/jeu/spawn.png");
					ch = true;
				}
				if (temp.isEqual(base)) {
					monde[i][j].setImage("./assets/images/jeu/base.png");
					ch = true;
				}
				if (!ch) {
					final int i2 = i;
					final int j2 = j;
					monde[i][j].getImage().addListener(new ClickListener() {
						public boolean touchDown(InputEvent e, float x,
								float y, int pointeur, int bouton) {
							if (Partie.attend_clic) {
								Terrain.place = new Position(i2, j2);
								Emplacement az = monde[(int) Terrain.place.x][(int) Terrain.place.y];
								if (az.isConstructible()) {
									// poser une tour OU un emplacement
									if (ref_partie.getMoi().unite_a_poser != null) {
										switch (ref_partie.getMoi().unite_a_poser
												.getType_unite()) {
										case t:
											// poser une tour
											Tour tr = new Tour(
													(Tour) ref_partie.getMoi().unite_a_poser);
											if (monde[(int) Terrain.place.x][(int) Terrain.place.y]
													.construire(tr)) {
												ref_partie
														.associer(monde[(int) Terrain.place.x][(int) Terrain.place.y]);
												monde[(int) Terrain.place.x][(int) Terrain.place.y]
														.getT()
														.getImage()
														.setPosition(
																i2 * l_img,
																j2 * h_img);
												monde[(int) Terrain.place.x][(int) Terrain.place.y]
														.getT().getImage()
														.setVisible(true);
												monde[(int) Terrain.place.x][(int) Terrain.place.y]
														.getT()
														.setPosition(
																new Position(
																		i2, j2));
												ref_partie
														.getMoi()
														.getBanque_tour()
														.detruire(
																(Tour) (ref_partie
																		.getMoi().unite_a_poser));
											}
											ref_partie.getMoi().unite_a_poser
													.cacher();
											ref_partie.getMoi().unite_a_poser = null;
											ref_partie.getMoi().menu();
											break;
										case e:
											Emplacement emp = new Emplacement(
													(Emplacement) ref_partie
															.getMoi().unite_a_poser);
											emp.getImage().setPosition(
													i2 * l_img, j2 * h_img);
											if (changer_emp(emp, Terrain.place)) {
												ref_partie
														.associer(monde[(int) Terrain.place.x][(int) Terrain.place.y]);
												monde[(int) Terrain.place.x][(int) Terrain.place.y]
														.getImage().setVisible(
																true);
												ref_partie
														.getMoi()
														.getBanque_emp()
														.detruire(
																(Emplacement) (ref_partie
																		.getMoi().unite_a_poser));
											}

											ref_partie.getMoi().unite_a_poser
													.cacher();
											ref_partie.getMoi().unite_a_poser = null;
											ref_partie.getMoi().menu();
											break;
										default:
											break;
										}
									}
								}
								Terrain.place = null;
							}
							return false;
						}
					});
				}
				monde[i][j].getImage().setSize(l_img, h_img);
				monde[i][j].getImage().setPosition(i * l_img, j * h_img);
				s.addActor(monde[i][j].getImage());
			}
		}
	}

	protected boolean changer_emp(Emplacement emp, Position pl) {
		// TODO Auto-generated method stub
		if (monde[(int) pl.x][(int) pl.y].isConstructible()) {
			monde[(int) pl.x][(int) pl.y] = emp;
			return true;
		}
		return false;
	}

	public Position getSpawn() {
		// TODO Auto-generated method stub
		return spawn;
	}

	public Position getBase() {
		// TODO Auto-generated method stub
		return base;
	}

	public boolean contient(Position p) {
		// TODO Auto-generated method stub
		return (p.x >= 0 && p.y >= 0 && p.x < largeur && p.y < hauteur);
	}

	public int get_nb_tour() {
		// TODO Auto-generated method stub
		int nb = 0;
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				if (!monde[i][j].isPassable()) {
					nb++;
				}
			}
		}
		return nb;
	}

	public void peter(Position pt) {
		// TODO Auto-generated method stub
		int i = (int) pt.x;
		int j = (int) pt.y;
		if (monde[i][j].getT() != null) {
			monde[i][j].getT().cacher();
			monde[i][j].setT(null);
		}
		monde[i][j].setPassable(true);
		monde[i][j].setConstructible(true);
		monde[i][j].cacher();
		monde[i][j].updateImage();
		final int i2 = i;
		final int j2 = j;
		if (monde[i][j].getImage().addListener(new ClickListener() {
			public boolean touchDown(InputEvent e, float x, float y,
					int pointeur, int bouton) {
				if (Partie.attend_clic) {
					System.out.println("CLIC ! ");
					Terrain.place = new Position(i2, j2);
					Emplacement az = monde[(int) Terrain.place.x][(int) Terrain.place.y];
					if (az.isConstructible()) {
						// poser une tour OU un emplacement
						if (ref_partie.getMoi().unite_a_poser != null) {
							switch (ref_partie.getMoi().unite_a_poser
									.getType_unite()) {
							case t:
								// poser une tour
								Tour tr = new Tour(
										(Tour) ref_partie.getMoi().unite_a_poser);
								if (monde[(int) Terrain.place.x][(int) Terrain.place.y]
										.construire(tr)) {
									ref_partie
											.associer(monde[(int) Terrain.place.x][(int) Terrain.place.y]);
									monde[(int) Terrain.place.x][(int) Terrain.place.y]
											.getT()
											.getImage()
											.setPosition(i2 * Unite.cote,
													j2 * Unite.cote);
									monde[(int) Terrain.place.x][(int) Terrain.place.y]
											.getT().getImage().setVisible(true);
									monde[(int) Terrain.place.x][(int) Terrain.place.y]
											.getT().setPosition(
													new Position(i2, j2));
									ref_partie
											.getMoi()
											.getBanque_tour()
											.detruire(
													(Tour) (ref_partie.getMoi().unite_a_poser));
								}
								ref_partie.getMoi().unite_a_poser.cacher();
								ref_partie.getMoi().unite_a_poser = null;
								ref_partie.getMoi().menu();
								break;
							case e:
								Emplacement emp = new Emplacement(
										(Emplacement) ref_partie.getMoi().unite_a_poser);
								emp.getImage().setPosition(i2 * Unite.cote,
										j2 * Unite.cote);
								if (changer_emp(emp, Terrain.place)) {
									ref_partie
											.associer(monde[(int) Terrain.place.x][(int) Terrain.place.y]);
									monde[(int) Terrain.place.x][(int) Terrain.place.y]
											.getImage().setVisible(true);
									ref_partie
											.getMoi()
											.getBanque_emp()
											.detruire(
													(Emplacement) (ref_partie
															.getMoi().unite_a_poser));
								}

								ref_partie.getMoi().unite_a_poser.cacher();
								ref_partie.getMoi().unite_a_poser = null;
								ref_partie.getMoi().menu();
								break;
							default:
								break;
							}
						}
					}
					Terrain.place = null;
				}
				return false;
			}
		})){
			ref_partie.add_img(monde[i][j].getImage());
		}
		monde[i][j].getImage().setSize(Unite.cote, Unite.cote);
		monde[i][j].getImage().setPosition(i * Unite.cote, j * Unite.cote);
		monde[i][j].getImage().setVisible(true);
	}

	public Position get_tour(int tour) {
		// TODO Auto-generated method stub
		int nb = 0;
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				if (!monde[i][j].isPassable()) {
					if (nb == tour) {
						return new Position(i, j);
					}
					nb++;
				}
			}
		}
		return null;
	}

	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		for (int i = 0; i < largeur; i++) {
			for (int j = 0; j < hauteur; j++) {
				// actualiser les tours
				if (monde[i][j].getT() != null) {
					// on attaque
					monde[i][j].getT().update(deltaTime);
				}
			}
		}
	}
}
