import com.badlogic.gdx.scenes.scene2d.Stage;

// le joueur reseau va implémenter des fonctions 
// identiques au joueur mais ses données vont 
// être envoyer à l'ip correspondant

public class Joueur_Reseau extends Joueur {

	private String ip;

	public String getIp() {
		return ip;
	}

	Joueur_Reseau(String ip, Partie partie, Stage stage) {
		super("adversaire", partie, stage);
		this.ip = ip;
	}
}
