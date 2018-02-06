public class Position {

	public float x;
	public float y;

	// pour une position de monstre
	public Position(float i, float j) {
		x = i;
		y = j;
	}
	
	// pour la position d'une case
	public Position(int i, int j) {
		x = i;
		y = j;
	}

	// 
	public Position(Position depart, Position direction) {
		// TODO Auto-generated constructor stub
		x = depart.x + direction.x;
		y = depart.y + direction.y;
	}

	public float distance(Position p){
		return (float) Math.sqrt( Math.pow((p.y - y),2) + Math.pow((p.x - x),2) );
	}

	@Override
	public String toString() {
		return "P[x=" + x + ", y=" + y + "]";
	}

	public boolean isEqual(Position p) {
		// TODO Auto-generated method stub
		return ((int) p.x == (int) x && (int) p.y == (int) y);
	}
}
