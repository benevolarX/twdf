import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Projectile {

	public static Partie ref_partie;

	private final static int vitesse = 300;

	private Tour t_mere;
	private Monstre cible;
	// private Position p;
	private Position position_exacte;
	private Tour.Specialite c;
	private int degats;
	private Image obus;
	private boolean a_explose;

	public Projectile(Tour t) {
		t_mere = t;
		cible = t.getCible();
		position_exacte = new Position(t.getPosition().x * Unite.cote,
				t.getPosition().y * Unite.cote);
		c = t.getCompetences();
		degats = t.getDegats();
		obus = new Image(ImageManager.GetImage("./assets/images/jeu/obus.png"));
		ref_partie.associer(this);
		setA_explose(false);
	}

	public boolean update(float time) {
		if (isA_explose()) {
			return true;
		}
		if (cible != null) {
			if (cible.getVie() > 0) {
				if (position_exacte.distance(cible.getPosition_exacte()) < Unite.cote / 4) {
					// on a atteint la cible
					if (c != null){
						if (c.equals(Tour.Specialite.U_anti_aerien)) {
							if (cible.getSpecial().contains(
									Monstre.Competence.vol)) {
								degats *= 2;
							} else {
								degats /= 2;
							}
						}
					}
					
					cible.setVie(cible.getVie() - degats);
					if (cible.getVie() <= 0) {
						ref_partie.getMoi().setOr_par_seconde(
								ref_partie.getMoi().getOr_par_seconde()
										+ cible.getIncome());
					}
					/*****************/
					if (c != null) {
						if (c.equals(Tour.Specialite.U_feu)) {
							t_mere.setDegats(t_mere.getDegats() * 2);
						}
						cible.addStatut(c);
					}
					/*****************/
					return exploser();
				} else {
					// on avance
					float modulo = Math.abs(cible.getPosition_exacte().x
							- position_exacte.x);
					if (modulo < Math.abs(cible.getPosition_exacte().y
							- position_exacte.y)) {
						modulo = Math.abs(cible.getPosition_exacte().y
								- position_exacte.y);
					}
					Position avancer = new Position(
							((cible.getPosition_exacte().x - position_exacte.x) / modulo)
									* vitesse * time,
							((cible.getPosition_exacte().y - position_exacte.y) / modulo)
									* vitesse * time);
					position_exacte = new Position(position_exacte.x
							+ avancer.x, position_exacte.y + avancer.y);
					obus.setPosition(position_exacte.x, position_exacte.y);
					obus.setVisible(true);
				}
			} else {
				return exploser();
			}
		} else {
			return (exploser());
		}
		return false;
	}

	private boolean exploser() {
		// TODO Auto-generated method stub
		setA_explose(true);
		obus.setVisible(false);
		cible = null;
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// obus.remove();
		return true;
	}

	public Image getObus() {
		return obus;
	}

	public void setObus(Image obus) {
		this.obus = obus;
	}

	public boolean isA_explose() {
		return a_explose;
	}

	public void setA_explose(boolean a_explose) {
		this.a_explose = a_explose;
	}

}
