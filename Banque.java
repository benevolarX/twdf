import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;

@SuppressWarnings("serial")
public class Banque<T extends Unite> extends ArrayList<T> {

	public int page;

	public Banque() {
		super();
		page = 0;
	}

	public void afficher_info(T u) {

	}

	ArrayList<Monstre> getMonstres() {
		Iterator<T> it = this.iterator();
		ArrayList<Monstre> resultat = new ArrayList<Monstre>();
		Unite u;
		while (it.hasNext()) {
			u = it.next();
			resultat.add((Monstre) u);
		}
		return resultat;
	}

	ArrayList<Emplacement> getEmplacements() {
		Iterator<T> it = this.iterator();
		ArrayList<Emplacement> resultat = new ArrayList<Emplacement>();
		Unite u;
		while (it.hasNext()) {
			u = it.next();
			resultat.add((Emplacement) u);
		}
		return resultat;
	}

	ArrayList<Tour> getTours() {
		Iterator<T> it = this.iterator();
		ArrayList<Tour> resultat = new ArrayList<Tour>();
		Unite u;
		while (it.hasNext()) {
			u = it.next();
			resultat.add((Tour) u);
		}
		return resultat;
	}

	public boolean detruire(Unite u) {
		if (u.getPrix().isVendable()) {
			u.cacher();
			this.remove(u);
		}
		return false;
	}

	public void masquer() {
		Iterator<T> it = this.iterator();
		while (it.hasNext()) {
			Unite u = it.next();
			u.cacher();
		}
	}

	public int afficher(int x, int y, int h) {
		// TODO Auto-generated method stub
		int decalage = 0;
		int depart = 0;
		Iterator<T> it_e = this.iterator();
		while (it_e.hasNext()) {
			Unite e = it_e.next();
			if (e.getPrix().isAchetable() && depart >= page
					&& depart < get_nb_u() && (y + decalage + 100 < y + h)) {
				if (depart %2 == 0){
					e.infos.setColor(new Color(0,0,1,1));
				}
				e.afficher_boutique(x, y + decalage);
				decalage += 50;
			} else {
				e.cacher();
			}
			depart++;
		}
		return (y + decalage);
	}

	public int get_nb_u() {
		// TODO Auto-generated method stub
		Iterator<T> it = this.iterator();
		int cpt = 0;
		Unite u;
		while (it.hasNext()) {
			u = it.next();
			if (u.getPrix() != null) {
				if (u.getPrix().isAchetable()) {
					cpt++;
				}
			}
		}
		return cpt;
	}

	public void debloquer() {
		// TODO Auto-generated method stub
		Iterator<T> it = this.iterator();
		Unite u;
		while (it.hasNext()) {
			u = it.next();
			if (u.getPrix() != null) {
				u.getPrix().setAchetable(true);
			}
		}
	}
}
