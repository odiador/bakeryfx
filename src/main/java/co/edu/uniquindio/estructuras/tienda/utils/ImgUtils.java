package co.edu.uniquindio.estructuras.tienda.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ImgUtils {

	public static Image cropNormal(Image img, double r) {
    if (img == null) {
        // Intenta cargar la imagen default.png desde el recurso del proyecto
        try {
            img = new Image(ImgUtils.class.getResource("/images/default.png").toExternalForm());
        } catch (Exception e) {
            // Si no se encuentra el recurso, retorna una imagen vacía
			System.out.println("No se pudo cargar la imagen default.png");
            img = null; 
			return null;
        }
    }
    Canvas canvas = new Canvas(img.getWidth(), img.getHeight());
    GraphicsContext g = canvas.getGraphicsContext2D();
    g.fillRoundRect(0, 0, img.getWidth(), img.getHeight(), r, r);
    g.setGlobalBlendMode(BlendMode.SRC_ATOP);
    g.drawImage(img, 0, 0);
    return canvas.snapshot(null, null);
	}

	public static Image cropSquareCenter(Image img) {
		double d = Math.min(img.getWidth(), img.getHeight());
		double x = (d - img.getWidth()) / 2;
		double y = (d - img.getHeight()) / 2;
		Canvas canvas = new Canvas(d, d);
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.fillOval(0, 0, d, d);
		g.setGlobalBlendMode(BlendMode.SRC_ATOP);
		g.drawImage(img, x, y);
		return canvas.snapshot(null, null);
	}
	public static Image createSquareImg(Image img) {
		double d = Math.min(img.getWidth(), img.getHeight());
		double x = (d - img.getWidth()) / 2;
		double y = (d - img.getHeight()) / 2;
		Canvas canvas = new Canvas(d, d);
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.fillRect(0, 0, d, d);
		g.setGlobalBlendMode(BlendMode.SRC_ATOP);
		g.drawImage(img, x, y);
		return canvas.snapshot(null, null);
	}

	public static Circle createCircleWImage(Image img) {
		return new Circle(30, new ImagePattern(img));
	}

}
