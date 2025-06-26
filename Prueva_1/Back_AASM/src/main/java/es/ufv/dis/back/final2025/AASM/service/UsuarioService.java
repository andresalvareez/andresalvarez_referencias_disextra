// src/main/java/es/ufv/dis/back/final2025/service/UsuarioService.java
package es.ufv.dis.back.final2025.AASM.service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import es.ufv.dis.back.final2025.AASM.model.Usuario;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UsuarioService {
    private final String JSON_FILE = "src/main/resources/usuarios.json";
    private final Gson gson = new Gson();

    // Lee todos los usuarios del JSON
    public List<Usuario> listarUsuarios() throws Exception {
        try (FileReader reader = new FileReader(JSON_FILE)) {
            Type listType = new TypeToken<List<Usuario>>(){}.getType();
            return gson.fromJson(reader, listType);
        }
    }

    // Busca un usuario por ID
    public Usuario obtenerUsuario(String id) throws Exception {
        List<Usuario> usuarios = listarUsuarios();
        return usuarios.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElse(null);
    }

    // Añade un nuevo usuario y guarda en JSON
    public Usuario añadirUsuario(Usuario nuevo) throws Exception {
        List<Usuario> usuarios = listarUsuarios();
        nuevo.setId(UUID.randomUUID().toString());
        usuarios.add(nuevo);
        guardarUsuarios(usuarios);
        return nuevo;
    }

    // Edita un usuario existente
    public Usuario editarUsuario(String id, Usuario datosNuevos) throws Exception {
        List<Usuario> usuarios = listarUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (id.equals(usuarios.get(i).getId())) {
                // Mantener el mismo ID en los datos nuevos
                datosNuevos.setId(id);
                // Reemplazar el usuario en la lista
                usuarios.set(i, datosNuevos);
                // Guardar los cambios en el JSON
                guardarUsuarios(usuarios);
                return datosNuevos;
            }
        }
        // Si no se encontró ningún usuario con ese ID
        return null;
    }

    // Método auxiliar para guardar la lista de usuarios en el JSON
    private void guardarUsuarios(List<Usuario> usuarios) throws Exception {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(usuarios, writer);
        }
    }
}
