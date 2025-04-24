package co.edu.uniquindio.estructuras.tienda.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import co.edu.uniquindio.estructuras.tienda.model.CakeColor;
import co.edu.uniquindio.estructuras.tienda.model.Pastel;
import co.edu.uniquindio.estructuras.tienda.model.Piso;
import co.edu.uniquindio.estructuras.tienda.model.StarColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;

public class PastelEditorController implements Initializable {

    @FXML
    private CheckBox checkEstrellas1, checkEstrellas2, checkEstrellas3;

    @FXML
    private ComboBox<StarColor> colorEstrellas1, colorEstrellas2, colorEstrellas3;

    @FXML
    private ComboBox<CakeColor> colorPastel1, colorPastel2, colorPastel3;

    @FXML
    private ComboBox<Integer> comboPisos;
    
    @FXML
    private Label lblPrecio, lblDescripcionPrecio;

    @FXML
    private SVGPath svgBig, svgBigStars, svgBigTop, svgMed, svgMedStars, svgMedTop, svgSmall, svgSmallStars,
            svgSmallTop;

    @FXML
    void agregarAlCarritoEvent(ActionEvent event) {
        // Crear pastel con la configuración actual
        Pastel pastel = getPastelActual();
        try {
            // Por defecto 1 unidad
            ModelFactoryController.getInstance().agregarDetalleCarrito(1, pastel);
            // Opcional: mostrar mensaje de éxito
            System.out.println("Pastel agregado al carrito: " + pastel.getNombre() + " ($" + pastel.getPrecio() + ")");
        } catch (Exception e) {
            // Manejo de errores (puedes mostrar un alert en la UI si prefieres)
            e.printStackTrace();
        }
    }
    
    // Método para obtener el pastel actual según las configuraciones de la UI
    private Pastel getPastelActual() {
        // Obtener cantidad de pisos
        int pisosSeleccionados = comboPisos.getValue();
        Piso[] pisos = new Piso[pisosSeleccionados];
        for (int i = 0; i < pisosSeleccionados; i++) {
            CakeColor color = switch (i) {
                case 0 -> colorPastel1.getValue();
                case 1 -> colorPastel2.getValue();
                case 2 -> colorPastel3.getValue();
                default -> null;
            };
            StarColor colorEstrella = null;
            boolean tieneEstrella = false;
            switch (i) {
                case 0 -> {
                    tieneEstrella = checkEstrellas1.isSelected();
                    colorEstrella = tieneEstrella ? colorEstrellas1.getValue() : null;
                }
                case 1 -> {
                    tieneEstrella = checkEstrellas2.isSelected();
                    colorEstrella = tieneEstrella ? colorEstrellas2.getValue() : null;
                }
                case 2 -> {
                    tieneEstrella = checkEstrellas3.isSelected();
                    colorEstrella = tieneEstrella ? colorEstrellas3.getValue() : null;
                }
            }
            Piso piso = new Piso();
            piso.setColor(color);
            piso.setColorEstrella(colorEstrella);
            pisos[i] = piso;
        }
        return new Pastel(pisos);
    }
    
    // Método para actualizar la información en tiempo real
    private void actualizarInformacionPastel() {
        Pastel pastelActual = getPastelActual();
        lblPrecio.setText(String.format("$%.0f", pastelActual.getPrecio()));
        
        // Actualizar la descripción del pastel
        StringBuilder descripcion = new StringBuilder();
        descripcion.append("Pastel de ");
        descripcion.append(pastelActual.getPisos().length);
        descripcion.append(" piso");
        if (pastelActual.getPisos().length > 1) {
            descripcion.append("s");
        }
        
        // Contar estrellas
        int contadorEstrellas = 0;
        for (Piso piso : pastelActual.getPisos()) {
            if (piso.isEstrella()) {
                contadorEstrellas++;
            }
        }
        
        if (contadorEstrellas > 0) {
            descripcion.append(" con ");
            descripcion.append(contadorEstrellas);
            descripcion.append(" decoración");
            if (contadorEstrellas > 1) {
                descripcion.append("es");
            }
            descripcion.append(" de estrella");
            if (contadorEstrellas > 1) {
                descripcion.append("s");
            }
        }
        
        lblDescripcionPrecio.setText(descripcion.toString());
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
            actualizarInformacionPastel();
        });
        
        // Inicializar colores y visibilidad
        actualizarVisibilidadPisos(3);
        agregarListenersDeshabilitar();

        // Colores bonitos para pasteles usando enums
        colorPastel1.getItems().setAll(CakeColor.values());
        colorPastel2.getItems().setAll(CakeColor.values());
        colorPastel3.getItems().setAll(CakeColor.values());
        colorPastel1.setValue(CakeColor.CELESTE);
        colorPastel2.setValue(CakeColor.ROSA);
        colorPastel3.setValue(CakeColor.LILA);

        // Listeners para colores de pasteles con actualización en tiempo real
        colorPastel1.setOnAction(e -> {
            String hex = colorPastel1.getValue().getHex();
            svgBig.setFill(javafx.scene.paint.Paint.valueOf(hex));
            svgBigTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(hex)));
            actualizarInformacionPastel();
        });
        
        colorPastel2.setOnAction(e -> {
            String hex = colorPastel2.getValue().getHex();
            svgMed.setFill(javafx.scene.paint.Paint.valueOf(hex));
            svgMedTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(hex)));
            actualizarInformacionPastel();
        });
        
        colorPastel3.setOnAction(e -> {
            String hex = colorPastel3.getValue().getHex();
            svgSmall.setFill(javafx.scene.paint.Paint.valueOf(hex));
            svgSmallTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(hex)));
            actualizarInformacionPastel();
        });
        
        // Inicializa los colores SVG
        svgBig.setFill(javafx.scene.paint.Paint.valueOf(colorPastel1.getValue().getHex()));
        svgBigTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(colorPastel1.getValue().getHex())));
        svgMed.setFill(javafx.scene.paint.Paint.valueOf(colorPastel2.getValue().getHex()));
        svgMedTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(colorPastel2.getValue().getHex())));
        svgSmall.setFill(javafx.scene.paint.Paint.valueOf(colorPastel3.getValue().getHex()));
        svgSmallTop.setFill(javafx.scene.paint.Paint.valueOf(getTopColor(colorPastel3.getValue().getHex())));

        // Inicializar colores de estrellas
        colorEstrellas1.getItems().setAll(StarColor.values());
        colorEstrellas2.getItems().setAll(StarColor.values());
        colorEstrellas3.getItems().setAll(StarColor.values());
        colorEstrellas1.setValue(StarColor.AMARILLO);
        colorEstrellas2.setValue(StarColor.BLANCO);
        colorEstrellas3.setValue(StarColor.NARANJA);

        // Listeners para colores de estrellas con actualización en tiempo real
        colorEstrellas1.setOnAction(e -> {
            svgBigStars.setFill(Paint.valueOf(colorEstrellas1.getValue().getHex()));
            actualizarInformacionPastel();
        });
        
        colorEstrellas2.setOnAction(e -> {
            svgMedStars.setFill(Paint.valueOf(colorEstrellas2.getValue().getHex()));
            actualizarInformacionPastel();
        });
        
        colorEstrellas3.setOnAction(e -> {
            svgSmallStars.setFill(Paint.valueOf(colorEstrellas3.getValue().getHex()));
            actualizarInformacionPastel();
        });

        // Establecer colores iniciales de estrellas
        svgBigStars.setFill(Paint.valueOf(colorEstrellas1.getValue().getHex()));
        svgMedStars.setFill(Paint.valueOf(colorEstrellas2.getValue().getHex()));
        svgSmallStars.setFill(Paint.valueOf(colorEstrellas3.getValue().getHex()));

        // Listeners para CheckBox de estrellas con actualización en tiempo real
        checkEstrellas1.setOnAction(e -> {
            svgBigStars.setVisible(checkEstrellas1.isSelected());
            colorEstrellas1.setDisable(!checkEstrellas1.isSelected());
            actualizarInformacionPastel();
        });
        
        checkEstrellas2.setOnAction(e -> {
            svgMedStars.setVisible(checkEstrellas2.isSelected());
            colorEstrellas2.setDisable(!checkEstrellas2.isSelected());
            actualizarInformacionPastel();
        });
        
        checkEstrellas3.setOnAction(e -> {
            svgSmallStars.setVisible(checkEstrellas3.isSelected());
            colorEstrellas3.setDisable(!checkEstrellas3.isSelected());
            actualizarInformacionPastel();
        });

        // Inicializar visibilidad por defecto
        svgBigStars.setVisible(checkEstrellas1.isSelected());
        svgMedStars.setVisible(checkEstrellas2.isSelected());
        svgSmallStars.setVisible(checkEstrellas3.isSelected());
        
        // Inicializar la información del pastel al cargar la pantalla
        actualizarInformacionPastel();
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
        // Listeners para los CheckBox de estrellas
        checkEstrellas1.selectedProperty().addListener((obs, oldVal, newVal) -> {
            svgBigStars.setVisible(newVal);
            colorEstrellas1.setDisable(!newVal);
            actualizarInformacionPastel();
        });
        
        checkEstrellas2.selectedProperty().addListener((obs, oldVal, newVal) -> {
            svgMedStars.setVisible(newVal && comboPisos.getValue() >= 2);
            colorEstrellas2.setDisable(!newVal || comboPisos.getValue() < 2);
            actualizarInformacionPastel();
        });
        
        checkEstrellas3.selectedProperty().addListener((obs, oldVal, newVal) -> {
            svgSmallStars.setVisible(newVal && comboPisos.getValue() == 3);
            colorEstrellas3.setDisable(!newVal || comboPisos.getValue() < 3);
            actualizarInformacionPastel();
        });
    }
}