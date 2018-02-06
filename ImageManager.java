import java.util.HashMap;
import com.badlogic.gdx.graphics.Texture;

public class ImageManager {

	private static ImageManager imgmng;
	private HashMap<String, Texture> liste_texture;

	// a utiliser sur toutes les images
	public static Texture GetImage(String url) {
		if (imgmng == null) {
			imgmng = new ImageManager();
		}
		if (!imgmng.liste_texture.isEmpty()) {
			if (imgmng.liste_texture.containsKey(url)) {
				return imgmng.liste_texture.get(url);
			}
		}
		imgmng.liste_texture.put(url, new Texture(url));
		return imgmng.liste_texture.get(url);
	}

	public static void vider() {
		if (imgmng == null) {
			imgmng = new ImageManager();
		} else {
			imgmng.liste_texture.clear();
		}
	}

	// constructeur prive du singleton
	private ImageManager() {
		liste_texture = new HashMap<String, Texture>();
	}
}
