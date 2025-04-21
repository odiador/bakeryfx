package co.edu.uniquindio.estructuras.tienda.services;

import javafx.scene.image.Image;

public interface Imagenable {
    /**
     * Retorna la ruta local de la imagen asociada a este objeto.
     */
    String getRutaImagen();

    /**
     * Devuelve la imagen cargada desde la ruta local.
     */
    default Image getImageFX() {
    String ruta = getRutaImagen();
    if (ruta != null && new java.io.File(ruta).exists()) {
        return new Image(new java.io.File(ruta).toURI().toString());
    }
    // Intenta cargar la imagen default.png desde el recurso del proyecto
    try {
        return new Image(getClass().getResource("/images/default.png").toExternalForm());
    } catch (Exception e) {
        // Si no se encuentra el recurso, retorna una imagen vacía para evitar null
        System.out.println("No se pudo cargar la imagen default.png");
        return null;
    }
}
}
