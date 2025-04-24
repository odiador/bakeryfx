package co.edu.uniquindio.estructuras.tienda.controllers;

import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import co.edu.uniquindio.estructuras.tienda.model.Producto;
import co.edu.uniquindio.estructuras.tienda.model.Venta;
import co.edu.uniquindio.estructuras.tienda.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private VBox boxStock;
    @FXML
    private VBox boxVentas;
    @FXML
    private VBox boxUsuarios;
    @FXML
    private Label lblStockTotal;
    @FXML
    private Label lblTotalVentas;
    @FXML
    private Label lblUsuarios;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Calcular el stock total (suma de cantidades de todos los productos)
        int stockTotal = ModelFactoryController.getInstance().getProductos()
                .stream().mapToInt(Producto::getCantidad).sum();
        lblStockTotal.setText(String.valueOf(stockTotal));

        // Calcular el total de ventas
        double totalVentas = ModelFactoryController.getInstance().getVentas()
                .stream().mapToDouble(Venta::getTotal).sum();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        lblTotalVentas.setText(currencyFormat.format(totalVentas));

        // Calcular usuarios registrados
        int totalUsuarios = ModelFactoryController.getInstance().getUsuarios().size();
        lblUsuarios.setText(String.valueOf(totalUsuarios));
    }
}
