package es.diputacion.malaga.translater.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.ImageIcon;

public class ImageUtil {

	static public Properties prop;
	static String imageDir = "/images/";

	static {

		try {
			prop = new Properties();
			Class c = prop.getClass();
			InputStream is = c.getResourceAsStream("/system.properties");
			prop.load(is);

		} catch (FileNotFoundException fnfe) {
			Utilidades.log("" + fnfe);
		} catch (IOException ioe) {
			Utilidades.log("" + ioe);
		}

	}

	static public String getProperty(String name) {
		return (String) prop.get(name);
	}

	static public ImageIcon getImageFromJar(String imagen) {
		try {
			InputStream is = prop.getClass().getResourceAsStream(
					imageDir + imagen);
			byte[] imageData = new byte[is.available()];
			is.read(imageData);
			is.close();
			is = null;
			return new ImageIcon(imageData);

		} catch (Exception e) {
			Utilidades.log("Error al capturar la imagen ..." + e);
			return null;
		}
	}

	static public ImageIcon getSimpleImage(String img) {
		Image image = Toolkit.getDefaultToolkit().getImage(img);
		ImageIcon icon = new ImageIcon(image);
		return icon;
	}

	static public Dimension getDimension(int maxX, int maxY) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - maxX) / 2;
		int y = (screen.height - maxY) / 2;

		return new Dimension(x, y);
	}

	static public int getSizeX(int maxX) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		return (screen.width - maxX) / 2;
	}

	static public int getSizeY(int maxY) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		return (screen.height - maxY) / 2;
	}

	static public String execute(String source) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(source);
			InputStream is = p.getErrorStream();
			Utilidades.log("Size ... " + is.available());
			byte[] array = new byte[is.available()];
			is.read(array);

			String result = new String(array);

			return result;

		} catch (java.io.IOException e) {
			Utilidades.log("Error al ejecutar ... " + e);
			return "Error al ejecutar ... " + e;
		}
	}
}