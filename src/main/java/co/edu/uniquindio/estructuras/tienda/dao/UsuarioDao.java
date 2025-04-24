package co.edu.uniquindio.estructuras.tienda.dao;

import co.edu.uniquindio.estructuras.tienda.model.Usuario;
import java.io.*;
import java.util.HashMap;

public class UsuarioDao {
    private static UsuarioDao instance;
    private final String filePath = "data/usuarios.dat";

    private UsuarioDao() {}

    public static UsuarioDao getInstance() {
        if (instance == null) instance = new UsuarioDao();
        return instance;
    }

    public void saveData(HashMap<String, Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Usuario> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (HashMap<String, Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Si no existe el archivo o hay error, retornar un nuevo HashMap vacío
            return new HashMap<>();
        }
    }
}
