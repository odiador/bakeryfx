package co.edu.uniquindio.estructuras.tienda.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TreeSet;
import java.util.LinkedList;

import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import co.edu.uniquindio.estructuras.tienda.model.Producto;
import co.edu.uniquindio.estructuras.tienda.model.Venta;

import org.xhtmlrenderer.pdf.ITextRenderer;

public class ReportesController {

    @FXML
    private void generarReporteEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/estructuras/tienda/fxml/reportes.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Reportes");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reporteInventarioEvent(ActionEvent event) {
        try {
            // 1. Leer plantilla HTML
            String templatePath = "src/main/resources/templates/inventario_general_template.html";
            String html = new String(Files.readAllBytes(Paths.get(templatePath)), StandardCharsets.UTF_8);

            // 2. Obtener productos del inventario
            TreeSet<Producto> productos = ModelFactoryController.getInstance().getProductos();

            // 3. Generar filas de la tabla
            StringBuilder rows = new StringBuilder();
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
            int sumaCantidad = 0;
            double sumaTotal = 0;
            for (Producto p : productos) {
                double subtotal = p.getCantidad() * p.getPrecio();
                rows.append("<tr>")
                    .append("<td>").append(p.getNombre()).append("</td>")
                    .append("<td>").append(p.getCantidad()).append("</td>")
                    .append("<td>").append(nf.format(p.getPrecio())).append("</td>")
                    .append("<td>").append(nf.format(subtotal)).append("</td>")
                    .append("</tr>");
                sumaCantidad += p.getCantidad();
                sumaTotal += subtotal;
            }
            // Fila de totales
            rows.append("<tr style='font-weight:bold;background:#e0e0e0;'>")
                .append("<td>Total</td>")
                .append("<td>").append(sumaCantidad).append("</td>")
                .append("<td></td>")
                .append("<td>").append(nf.format(sumaTotal)).append("</td>")
                .append("</tr>");

            // 4. Insertar filas y fecha en la plantilla
            html = html.replace("<!-- INVENTARIO_ROWS -->", rows.toString());
            html = html.replace("<!-- FECHA_REPORTE -->", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // 5. Guardar HTML temporal (opcional)
            String reportsDir = "src/main/resources/reports";
            Files.createDirectories(Paths.get(reportsDir));
            String tempHtml = reportsDir + "/inventario_temp.html";
            Files.write(Paths.get(tempHtml), html.getBytes(StandardCharsets.UTF_8));

            // 6. Generar PDF desde HTML usando Flying Saucer
            String pdfPath = reportsDir + "/inventario_general.pdf";
            OutputStream os = Files.newOutputStream(Paths.get(pdfPath));
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(os);
            os.close();

            // 7. Notificar éxito
            System.out.println("Reporte de inventario generado: " + pdfPath);
        } catch (IOException | com.lowagie.text.DocumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reporteVentasEvent(ActionEvent event) {
        try {
            // 1. Leer plantilla HTML
            String templatePath = "src/main/resources/templates/ventas_general_template.html";
            String html = new String(Files.readAllBytes(Paths.get(templatePath)), StandardCharsets.UTF_8);

            // 2. Obtener ventas
            LinkedList<Venta> ventas = ModelFactoryController.getInstance().getVentas();

            // 3. Generar filas de la tabla
            StringBuilder rows = new StringBuilder();
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
            double sumaTotal = 0;
            for (Venta v : ventas) {
                String fecha = v.getFechaVenta().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String cliente = v.getCliente().getNombre();
                String productos = v.obtenerProductosVendidosString();
                double totalVenta = v.getTotal();
                rows.append("<tr>")
                    .append("<td>").append(fecha).append("</td>")
                    .append("<td>").append(cliente).append("</td>")
                    .append("<td>").append(productos).append("</td>")
                    .append("<td>").append(nf.format(totalVenta)).append("</td>")
                    .append("</tr>");
                sumaTotal += totalVenta;
            }
            // Fila de total general
            rows.append("<tr style='font-weight:bold;background:#e0e0e0;'>")
                .append("<td colspan='3'>Total General</td>")
                .append("<td>").append(nf.format(sumaTotal)).append("</td>")
                .append("</tr>");

            // 4. Insertar filas y fecha en la plantilla
            html = html.replace("<!-- VENTAS_ROWS -->", rows.toString());
            html = html.replace("<!-- FECHA_REPORTE -->", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // 5. Guardar HTML temporal (opcional)
            String reportsDir = "src/main/resources/reports";
            Files.createDirectories(Paths.get(reportsDir));
            String tempHtml = reportsDir + "/ventas_temp.html";
            Files.write(Paths.get(tempHtml), html.getBytes(StandardCharsets.UTF_8));

            // 6. Generar PDF desde HTML usando Flying Saucer
            String pdfPath = reportsDir + "/ventas_general.pdf";
            OutputStream os = Files.newOutputStream(Paths.get(pdfPath));
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(os);
            os.close();

            // 7. Notificar éxito
            System.out.println("Reporte de ventas generado: " + pdfPath);
        } catch (IOException | com.lowagie.text.DocumentException e) {
            e.printStackTrace();
        }
    }
}
