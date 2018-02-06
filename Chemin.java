import java.util.ArrayList;

// algo utilisé : Dijkstra
// http://openclassrooms.com/courses/le-pathfinding-avec-dijkstra

public class Chemin {

	// les monstres se déplaces d'une case en une case dans 4 directions
	public static Position droite = new Position(1, 0);
	public static Position gauche = new Position(-1, 0);
	public static Position haut = new Position(0, 1);
	public static Position bas = new Position(0, -1);
	// ajout d'une position par défaut (quand le monstre a atteint la base ou
	// quand il ne peut pas passer => étape intermédiaire pour devenir fou.
	public static Position stop = new Position(0, 0);

	private static int infini = -1; // -1 pour l'algo de Dijkstra

	private Terrain t;
	private Position depart;
	private Position arrive;
	private ArrayList<Position> cases;
	private ArrayList<Integer> parents;
	private ArrayList<Integer> poids;
	private ArrayList<Boolean> parcourru;

	public Chemin(Terrain t, Position depart, Position fin) {
		this.t = t;
		this.depart = depart;
		this.arrive = fin;
		this.cases = new ArrayList<Position>();
		this.parents = new ArrayList<Integer>();
		this.parcourru = new ArrayList<Boolean>();
		this.poids = new ArrayList<Integer>();
	}

	private void initialiser() {
		parents.clear();
		parcourru.clear();
		poids.clear();
		cases.clear();
		for (int i = 0; i < t.getLargeur(); i++) {
			for (int j = 0; j < t.getHauteur(); j++) {
				if (t.get_case(new Position(i, j)).isPassable()) {
					if (new Position(i, j).isEqual(depart)) {
						poids.add(0);
					} else {
						poids.add(infini);
					}
					cases.add(new Position(i, j));
					parents.add(infini);
					parcourru.add(false);
				}
			}
		}
	}

	public boolean chemin_existe() {
		initialiser();
		ArrayList<Position> fils = new ArrayList<Position>();
		int min, indice;
		do {
			min = chercher_min();
			if (min != infini) {
				// on a parcourru ce noeud
				parcourru.set(min, true);
				// on cherche les fils;
				fils.clear();
				fils.add(new Position(cases.get(min), droite));
				fils.add(new Position(cases.get(min), gauche));
				fils.add(new Position(cases.get(min), haut));
				fils.add(new Position(cases.get(min), bas));
				for (int i = 0; i < fils.size(); i++) {
					indice = contient(fils.get(i));
					if (indice != infini) {
						if (parcourru.get(indice) == false) {
							if ((poids.get(min) + 1 < poids.get(indice))
									| poids.get(indice) == infini) {
								poids.set(indice, poids.get(min) + 1);
								parents.set(indice, min);
							}
						}
					}
				}
			}
		} while (min != infini && !cases.get(min).isEqual(arrive));
		if (min != infini) {
			if (cases.get(min) != null) {
				return (cases.get(min).isEqual(arrive));
			}
		}
		return false;
	}

	public int contient(Position position) {
		int i = 0;
		for (Position p : cases) {
			if (p.isEqual(position)) {
				return i;
			}
			i++;
		}
		return infini;
	}

	private int chercher_min() {
		int min = cases.size() + 1;
		int index = infini;
		for (int i = 0; i < poids.size(); i++) {
			if (this.parcourru.get(i) == false && this.poids.get(i) != infini) {
				if (min > this.poids.get(i)) {
					min = this.poids.get(i);
					index = i;
				}
			}
		}
		return index;
	}

	public Position get_deplacement() {
		if (depart == arrive) {
			// on est arrivé
			return (stop);
		}
		if (chemin_existe()) {
			Integer p = contient(arrive);
			Integer suivant = p;
			while (p != contient(depart)) {
				suivant = p;
				p = parents.get(p);
			}
			return (new Position(cases.get(suivant).x - depart.x,
					cases.get(suivant).y - depart.y));
		}
		// chemin inexistant
		return (stop);
	}

	public ArrayList<Position> getParents() {
		ArrayList<Position> reponse = new ArrayList<Position>();
		if (chemin_existe()) {
			Integer p = contient(arrive);
			while (p != contient(depart)) {
				reponse.add(cases.get(p));
				p = parents.get(p);
			}
			return reponse;
		}
		// il n'y a pas de chemin
		reponse.add(stop);
		return reponse;
	}

	public Position getDepart() {
		return depart;
	}

	public void setDepart(Position depart) {
		this.depart = depart;
	}

	public Position getArrive() {
		return arrive;
	}

	public void setArrive(Position arrive) {
		this.arrive = arrive;
	}
}
