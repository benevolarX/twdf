public class Achetable {

	private int prix_achat;
	private int prix_vente;
	private boolean achetable; // les monstres ne sont pas achetable tant que l'adversaire n'est pas prêts
	private boolean vendable; // le spawn et la base n'est pas vendable

	// les obstacles non constructible ne sont pas vendable
	// les obstacles constructible sont vendable
	// les tours sont achetable et vendable

	public Achetable(int prix, int vente, boolean ache, boolean vend) {
		vendable = vend;
		achetable = ache;
		prix_achat = prix;
		prix_vente = vente;
	}

	public Achetable(Achetable p) {
		// TODO Auto-generated constructor stub
		vendable = p.vendable;
		prix_achat = p.prix_achat;
		prix_vente = p.prix_vente;
		achetable = p.achetable;
	}

	public int getPrix_achat() {
		return prix_achat;
	}

	public void setPrix_achat(int prix_achat) {
		this.prix_achat = prix_achat;
	}

	public int getPrix_vente() {
		return prix_vente;
	}

	public void setPrix_vente(int prix_vente) {
		this.prix_vente = prix_vente;
	}

	public boolean isAchetable() {
		return achetable;
	}

	public void setAchetable(boolean achetable) {
		this.achetable = achetable;
	}

	public boolean isVendable() {
		return vendable;
	}

	public void setVendable(boolean vendable) {
		this.vendable = vendable;
	}
}
