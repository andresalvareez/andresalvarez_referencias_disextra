package org.vaadin.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import org.vaadin.example.model.Usuario;
import org.vaadin.example.model.Direccion;
import org.vaadin.example.model.MetodoPago;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {
    private Grid<Usuario> grid = new Grid<>(Usuario.class, false);
    private List<Usuario> usuarios;
    private Gson gson = new Gson();
    private HttpClient httpClient = HttpClient.newHttpClient();

    public MainView() {
        // Configurar columnas del grid
        grid.addColumn(Usuario::getId).setHeader("ID");
        grid.addColumn(Usuario::getNombre).setHeader("Nombre");
        grid.addColumn(Usuario::getApellidos).setHeader("Apellidos");
        grid.addColumn(Usuario::getNif).setHeader("NIF");
        grid.addColumn(new NativeButtonRenderer<>("Editar", this::onEditarClick))
                .setHeader("Acciones");
        grid.addItemDoubleClickListener(e -> mostrarDetallesUsuario(e.getItem()));
        add(grid);

        // Botones Añadir usuario y Generar PDF
        Button btnAgregar = new Button("Añadir usuario",
                e -> abrirFormularioUsuario(null, true));
        Button btnPdf = new Button("Generar PDF",
                e -> generarPdfBackend());
        add(new HorizontalLayout(btnAgregar, btnPdf));

        // Carga inicial
        cargarDatosUsuarios();
    }

    private void cargarDatosUsuarios() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/usuarios"))
                    .GET().build();
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<Usuario>>(){}.getType();
            usuarios = gson.fromJson(response.body(), listType);
            grid.setItems(usuarios);
        } catch (Exception ex) {
            ex.printStackTrace();
            Notification.show("Error cargando usuarios");
        }
    }

    private void mostrarDetallesUsuario(Usuario u) {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");
        dialog.setCloseOnOutsideClick(true);

        StringBuilder info = new StringBuilder();
        info.append("ID: ").append(u.getId()).append("<br/>")
                .append("Nombre: ").append(u.getNombre())
                .append(" ").append(u.getApellidos()).append("<br/>")
                .append("NIF: ").append(u.getNif()).append("<br/>");

        Direccion dir = u.getDireccion();
        if (dir != null) {
            info.append("Dirección: ")
                    .append(dir.getCalle()).append(" nº").append(dir.getNumero())
                    .append(", ").append(dir.getCodigoPostal())
                    .append(" (").append(dir.getPisoLetra()).append(")")
                    .append(", ").append(dir.getCiudad())
                    .append("<br/>");
        }

        MetodoPago mp = u.getMetodoPago();
        if (mp != null) {
            info.append("Método de pago: ")
                    .append(mp.getNumeroTarjeta())
                    .append(" (").append(mp.getNombreAsociado()).append(")")
                    .append("<br/>");
        }

        Span contenido = new Span();
        contenido.getElement().setProperty("innerHTML", info.toString());
        dialog.add(contenido);
        dialog.open();
    }

    private void onEditarClick(Usuario u) {
        abrirFormularioUsuario(u, false);
    }

    private void abrirFormularioUsuario(Usuario usuarioExistente, boolean esNuevo) {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");

        TextField nombreField = new TextField("Nombre");
        TextField apellidoField = new TextField("Apellidos");
        TextField nifField = new TextField("NIF");
        TextField calleField = new TextField("Calle");
        TextField ciudadField = new TextField("Ciudad");
        TextField numeroTarjetaField = new TextField("Número de tarjeta");
        TextField nombreAsociadoField = new TextField("Nombre asociado");

        if (!esNuevo && usuarioExistente != null) {
            nombreField.setValue(usuarioExistente.getNombre());
            apellidoField.setValue(usuarioExistente.getApellidos());
            nifField.setValue(usuarioExistente.getNif());
            if (usuarioExistente.getDireccion() != null) {
                calleField.setValue(usuarioExistente.getDireccion().getCalle());
                ciudadField.setValue(usuarioExistente.getDireccion().getCiudad());
            }
            if (usuarioExistente.getMetodoPago() != null) {
                numeroTarjetaField.setValue(
                        String.valueOf(usuarioExistente.getMetodoPago().getNumeroTarjeta()));
                nombreAsociadoField.setValue(
                        usuarioExistente.getMetodoPago().getNombreAsociado());
            }
        }

        Button btnGuardar = new Button("Guardar", ev -> {
            Usuario u = (usuarioExistente != null)
                    ? usuarioExistente
                    : new Usuario();

            u.setNombre(nombreField.getValue());
            u.setApellidos(apellidoField.getValue());
            u.setNif(nifField.getValue());

            Direccion dir = new Direccion();
            dir.setCalle(calleField.getValue());
            dir.setCiudad(ciudadField.getValue());
            u.setDireccion(dir);

            MetodoPago mp = new MetodoPago();
            try {
                mp.setNumeroTarjeta(
                        Long.parseLong(numeroTarjetaField.getValue()));
            } catch (NumberFormatException ex) {
                mp.setNumeroTarjeta(0L);
            }
            mp.setNombreAsociado(nombreAsociadoField.getValue());
            u.setMetodoPago(mp);

            try {
                if (esNuevo) {
                    String json = gson.toJson(u);
                    HttpRequest req = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/usuarios"))
                            .header("Content-Type","application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(json))
                            .build();
                    HttpResponse<String> res = httpClient.send(
                            req, HttpResponse.BodyHandlers.ofString());
                    if (res.statusCode()==200) {
                        Usuario creado = gson.fromJson(res.body(), Usuario.class);
                        usuarios.add(creado);
                        grid.getDataProvider().refreshAll();
                    }
                } else {
                    String json = gson.toJson(u);
                    HttpRequest req = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/usuarios/" + u.getId()))
                            .header("Content-Type","application/json")
                            .PUT(HttpRequest.BodyPublishers.ofString(json))
                            .build();
                    HttpResponse<String> res = httpClient.send(
                            req, HttpResponse.BodyHandlers.ofString());
                    if (res.statusCode()==200) {
                        Usuario actualizado = gson.fromJson(res.body(), Usuario.class);
                        usuarios.replaceAll(us -> us.getId().equals(actualizado.getId())
                                ? actualizado : us);
                        grid.getDataProvider().refreshAll();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Notification.show("Error al guardar usuario");
            }
            dialog.close();
        });

        Button btnCancelar = new Button("Cancelar", ev -> dialog.close());

        dialog.add(
                nombreField, apellidoField, nifField,
                calleField, ciudadField,
                numeroTarjetaField, nombreAsociadoField,
                new HorizontalLayout(btnGuardar, btnCancelar)
        );
        dialog.open();
    }

    private void generarPdfBackend() {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/usuarios/pdf"))
                    .GET().build();
            HttpResponse<String> res = httpClient.send(
                    req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode()==200) {
                Notification.show("PDF generado correctamente");
            } else {
                Notification.show("Error al generar PDF");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Notification.show("Error al llamar al backend");
        }
    }
}
