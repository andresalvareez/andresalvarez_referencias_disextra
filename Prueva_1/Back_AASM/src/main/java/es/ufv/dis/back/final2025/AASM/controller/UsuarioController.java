// src/main/java/es/ufv/dis/back/final2025/controller/UsuarioController.java
package es.ufv.dis.back.final2025.AASM.controller;
//import es.ufv.dis.back.final2025.AASM.PdfUtil.PdfUtil;
import es.ufv.dis.back.final2025.AASM.model.Usuario;
import es.ufv.dis.back.final2025.AASM.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService servicio = new UsuarioService();

    // GET /usuarios - obtener todos
    @GetMapping
    public List<Usuario> getUsuarios() {
        try {
            return servicio.listarUsuarios();
        } catch (Exception e) {
            throw new RuntimeException("Error al leer usuarios", e);
        }
    }

    // GET /usuarios/{id} - obtener uno por id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable String id) {
        try {
            Usuario usuario = servicio.obtenerUsuario(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // POST /usuarios - crear nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario nuevo) {
        try {
            Usuario creado = servicio.añadirUsuario(nuevo);
            // Devolver el usuario creado con código 201 Created
            return ResponseEntity.ok(creado);
        } catch (Exception e) {
            // En caso de error, devolver código 500
            return ResponseEntity.status(500).build();
        }
    }

    // PUT /usuarios/{id} - actualizar usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String id,
                                                     @RequestBody Usuario datos) {
        try {
            Usuario actualizado = servicio.editarUsuario(id, datos);
            if (actualizado != null) {
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    // GET /usuarios/pdf - generar PDF con los datos actuales
    /*@GetMapping("/pdf")
    public ResponseEntity<String> generarPdf() {
        try {
            PdfUtil.crearPdf(servicio.listarUsuarios());
            // Devolver una respuesta indicando éxito (p.ej. mensaje)
            return ResponseEntity.ok("PDF generado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al generar PDF: " + e.getMessage());
        }
    }*/
}
