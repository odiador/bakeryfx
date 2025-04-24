package co.edu.uniquindio.estructuras.tienda.application;

import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;

public class ProductoPanaderiaTest {

        public void agregarProductosPanaderiaConModelFactory() throws Exception {
                ModelFactoryController mfc = ModelFactoryController.getInstance();
                mfc.agregarProducto("191951d8-1ab5-4ecb-b686-03a60fd79e0c", "Roscón de Arequipe", "2000.0", "10",
                                new javafx.scene.image.Image("C:/Users/juana/OneDrive/Documentos/GitHub/bakeryfx/images/b1ea2cf3-532f-4dcc-9129-b41ab312548d.jpeg"));
                mfc.agregarProducto("4066bba0-edaa-4cc7-8969-27ce7098f66e", "Buñuelo", "1000.0", "10",
                                new javafx.scene.image.Image("C:/Users/juana/OneDrive/Documentos/GitHub/bakeryfx/images/13d42f6c-365b-4c13-9575-46fb86e04673.jpeg"));
                mfc.agregarProducto("6867534a-4a0d-4193-b35f-79fc73dd1022", "Almojábana", "1800.0", "15",
                                new javafx.scene.image.Image("C:/Users/juana/OneDrive/Documentos/GitHub/bakeryfx/images/72a095f9-3c9f-49ee-b05b-351f7fe9cecd.jpg"));
                mfc.agregarProducto("6fad66c1-6599-455c-966f-f5f870a02d6b", "Chicharrón", "1700.0", "25",
                                new javafx.scene.image.Image("C:/Users/juana/OneDrive/Documentos/GitHub/bakeryfx/images/79252c52-4c1f-48ac-aa42-6d4f08ff123b.jpg"));
                mfc.agregarProducto("706d0ecc-6080-4641-91ca-aa0f49aa99cd", "Palito de Queso", "1600.0", "20",
                                new javafx.scene.image.Image("C:/Users/juana/OneDrive/Documentos/GitHub/bakeryfx/images/bf63acd1-0161-4aa8-9dd3-7fd7218cbc94.jpg"));
                mfc.agregarProducto("d02637e0-c205-4549-bc20-c0c0a028f405", "Pan de bono", "1500.0", "17",
                                new javafx.scene.image.Image("C:/Users/juana/OneDrive/Documentos/GitHub/bakeryfx/images/f944080f-01b3-4a0a-a372-8ea1aa99ce9e.jpeg"));
                mfc.agregarProducto("dd5a0f00-389a-46ce-a93f-dc937bd15194", "Fresas con Crema", "7000.0", "20",
                                new javafx.scene.image.Image("C:/Users/juana/OneDrive/Documentos/GitHub/bakeryfx/images/01dd2474-26cb-4327-a2ec-a34b533883b5.jpeg"));
                mfc.agregarProducto("e0642bbd-4726-44b8-8e15-760d2af59087", "Avena", "2500.0", "14",
                                new javafx.scene.image.Image("C:/Users/juana/OneDrive/Documentos/GitHub/bakeryfx/images/30809bc8-3d24-4c61-911f-37478c89dd15.jpeg"));
                mfc.agregarProducto("e618cf74-b5ad-403c-81ea-42307f69682c", "Brazo de Reina", "2500.0", "12",
                                new javafx.scene.image.Image("C:/Users/juana/OneDrive/Documentos/GitHub/bakeryfx/images/55fea9b3-a10f-43c7-abb6-fcaa9068a076.jpeg"));
                mfc.agregarProducto("f8760c9f-0701-46bb-9188-59777668a40d", "Mantecada", "1200.0", "35",
                                new javafx.scene.image.Image("C:/Users/juana/OneDrive/Documentos/GitHub/bakeryfx/images/e7bdb31f-f663-4fd3-9af3-643a236872cb.jpg"));
                // No assertions: just check that no exception is thrown
        }
}
