public class Statut {

	public final static float DUREE_EFFET = 3; // 3 secondes

	private Tour.Specialite effet;
	private float duration;

	public Statut(Tour.Specialite e) {
		this.effet = e;
		this.duration = 0;
	}

	public void update(float time) {
		this.duration += time;
	}

	public void remise_a_zero() {
		this.duration = 0;
	}

	public Tour.Specialite getEffet() {
		return effet;
	}

	public void setEffet(Tour.Specialite effet) {
		this.effet = effet;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public boolean valable() {
		return (duration < DUREE_EFFET);
	}

}
