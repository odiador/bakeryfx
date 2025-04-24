package co.edu.uniquindio.estructuras.tienda.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;

public class PastelEditorController implements Initializable {

    @FXML
    private CheckBox checkEstrellas1, checkEstrellas2, checkEstrellas3;

    @FXML
    private ComboBox<String> colorEstrellas1, colorEstrellas2, colorEstrellas3;

    @FXML
    private ComboBox<String> colorPastel1, colorPastel2, colorPastel3;

    @FXML
    private ComboBox<Integer> comboPisos;

    @FXML
    private SVGPath svgBig, svgBigStars, svgBigTop, svgMed, svgMedStars, svgMedTop, svgSmall, svgSmallStars,
            svgSmallTop;

    @FXML
    void agregarAlCarritoEvent(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar ComboBox de pisos
        comboPisos.getItems().clear();
        comboPisos.getItems().addAll(java.util.Arrays.asList(1, 2, 3));
        comboPisos.setValue(Integer.valueOf(3)); // Por defecto 3 pisos

        // Listeners para cantidad de pisos
        comboPisos.valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarVisibilidadPisos((Integer) newVal);
        });
        actualizarVisibilidadPisos(3);
        agregarListenersDeshabilitar();

        // Colores bonitos para pasteles (nombre y valor hex)
        java.util.Map<String, String> pastelColors = new java.util.LinkedHashMap<>();
        pastelColors.put("Celeste", "#9efcff");
        pastelColors.put("Rosa", "#ffb6d9");
        pastelColors.put("Lila", "#c6b6ff");
        pastelColors.put("Verde menta", "#b6ffd0");
        pastelColors.put("Durazno", "#ffe4b6");

        colorPastel1.getItems().setAll(pastelColors.keySet());
        colorPastel2.getItems().setAll(pastelColors.keySet());
        colorPastel3.getItems().setAll(pastelColors.keySet());
        colorPastel1.setValue("Celeste");
        colorPastel2.setValue("Rosa");
        colorPastel3.setValue("Lila");

        // Listener para color pastel 1
        colorPastel1.setOnAction(e -> {
            String hex = pastelColors.get(colorPastel1.getValue());
            svgBig.setFill(javafx.scene.paint.Paint.valueOf(hex));
            svgBigTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(hex)));
        });
        // Listener para color pastel 2
        colorPastel2.setOnAction(e -> {
            String hex = pastelColors.get(colorPastel2.getValue());
            svgMed.setFill(javafx.scene.paint.Paint.valueOf(hex));
            svgMedTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(hex)));
        });
        // Listener para color pastel 3
        colorPastel3.setOnAction(e -> {
            String hex = pastelColors.get(colorPastel3.getValue());
            svgSmall.setFill(javafx.scene.paint.Paint.valueOf(hex));
            svgSmallTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(hex)));
        });
        // Inicializa los colores SVG
        svgBig.setFill(javafx.scene.paint.Paint.valueOf(pastelColors.get(colorPastel1.getValue())));
        svgBigTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(pastelColors.get(colorPastel1.getValue()))));
        svgMed.setFill(javafx.scene.paint.Paint.valueOf(pastelColors.get(colorPastel2.getValue())));
        svgMedTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(pastelColors.get(colorPastel2.getValue()))));
        svgSmall.setFill(javafx.scene.paint.Paint.valueOf(pastelColors.get(colorPastel3.getValue())));
        svgSmallTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(pastelColors.get(colorPastel3.getValue()))));

        // Colores para estrellas
        java.util.Map<String, String> starColors = new java.util.LinkedHashMap<>();
        starColors.put("Amarillo", "#FFFF00");
        starColors.put("Blanco", "#FFFFFF");
        starColors.put("Naranja", "#FFD580");
        starColors.put("Azul", "#9EF6FF");
        starColors.put("Rosa", "#FFC1E3");

        colorEstrellas1.getItems().setAll(starColors.keySet());
        colorEstrellas2.getItems().setAll(starColors.keySet());
        colorEstrellas3.getItems().setAll(starColors.keySet());
        colorEstrellas1.setValue("Amarillo");
        colorEstrellas2.setValue("Amarillo");
        colorEstrellas3.setValue("Amarillo");

        colorEstrellas1.setOnAction(
                e -> svgBigStars.setFill(Paint.valueOf(starColors.get(colorEstrellas1.getValue()))));
        colorEstrellas2.setOnAction(
                e -> svgMedStars.setFill(Paint.valueOf(starColors.get(colorEstrellas2.getValue()))));
        colorEstrellas3.setOnAction(e -> svgSmallStars
                .setFill(Paint.valueOf(starColors.get(colorEstrellas3.getValue()))));

        // Inicializa colores SVG de estrellas
        svgBigStars.setFill(Paint.valueOf(starColors.get(colorEstrellas1.getValue())));
        svgMedStars.setFill(Paint.valueOf(starColors.get(colorEstrellas2.getValue())));
        svgSmallStars.setFill(Paint.valueOf(starColors.get(colorEstrellas3.getValue())));

        // Listeners para CheckBox de estrellas
        checkEstrellas1.setOnAction(e -> svgBigStars.setVisible(checkEstrellas1.isSelected()));
        checkEstrellas2.setOnAction(e -> svgMedStars.setVisible(checkEstrellas2.isSelected()));
        checkEstrellas3.setOnAction(e -> svgSmallStars.setVisible(checkEstrellas3.isSelected()));

        // Inicializar visibilidad por defecto
        svgBigStars.setVisible(checkEstrellas1.isSelected());
        svgMedStars.setVisible(checkEstrellas2.isSelected());
        svgSmallStars.setVisible(checkEstrellas3.isSelected());
    }

    /**
     * Calcula un color más claro para el top del pastel (20% más claro)
     */
    private String getTopColor(String hex) {
        Color color = Color.web(hex);
        double lighten = 0.2;
        double r = Math.min(1.0, color.getRed() + (1 - color.getRed()) * lighten);
        double g = Math.min(1.0, color.getGreen() + (1 - color.getGreen()) * lighten);
        double b = Math.min(1.0, color.getBlue() + (1 - color.getBlue()) * lighten);
        return String.format("#%02X%02X%02X", (int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    private void actualizarVisibilidadPisos(int pisos) {
        // Piso 1 (grande) siempre visible
        svgBig.setVisible(true);
        svgBigTop.setVisible(true);
        svgBigStars.setVisible(checkEstrellas1.isSelected());
        colorPastel1.setDisable(false);
        colorEstrellas1.setDisable(!checkEstrellas1.isSelected());
        checkEstrellas1.setDisable(false);

        // Piso 2
        boolean visible2 = pisos >= 2;
        svgMed.setVisible(visible2);
        svgMedTop.setVisible(visible2);
        svgMedStars.setVisible(visible2 && checkEstrellas2.isSelected());
        colorPastel2.setDisable(!visible2);
        colorEstrellas2.setDisable(!visible2 || !checkEstrellas2.isSelected());
        checkEstrellas2.setDisable(!visible2);
        if (!visible2) {
            checkEstrellas2.setSelected(false);
        }

        // Piso 3
        boolean visible3 = pisos == 3;
        svgSmall.setVisible(visible3);
        svgSmallTop.setVisible(visible3);
        svgSmallStars.setVisible(visible3 && checkEstrellas3.isSelected());
        colorPastel3.setDisable(!visible3);
        colorEstrellas3.setDisable(!visible3 || !checkEstrellas3.isSelected());
        checkEstrellas3.setDisable(!visible3);
        if (!visible3) {
            checkEstrellas3.setSelected(false);
        }
    }

    private void agregarListenersDeshabilitar() {
        // ComboBox de pisos
        comboPisos.valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarVisibilidadPisos(newVal);
        });
        // Listeners robustos para los CheckBox de estrellas
        checkEstrellas1.selectedProperty().addListener((obs, oldVal, newVal) -> {
            svgBigStars.setVisible(newVal);
            actualizarVisibilidadPisos(comboPisos.getValue());
        });
        checkEstrellas2.selectedProperty().addListener((obs, oldVal, newVal) -> {
            svgMedStars.setVisible(newVal);
            actualizarVisibilidadPisos(comboPisos.getValue());
        });
        checkEstrellas3.selectedProperty().addListener((obs, oldVal, newVal) -> {
            svgSmallStars.setVisible(newVal);
            actualizarVisibilidadPisos(comboPisos.getValue());
        });
    }

}
