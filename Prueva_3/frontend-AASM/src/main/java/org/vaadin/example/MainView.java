package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

@Route("")  // http://localhost:8081/
public class MainView extends VerticalLayout {

    public MainView() {
        // —– PEOPLE —–
        IntegerField peopleId   = new IntegerField("ID Personaje");
        Button       btnPeople  = new Button("Consultar People");
        TextArea     outPeople  = new TextArea("Resultado Personaje");
        outPeople.setWidthFull();
        outPeople.setReadOnly(true);

        // Layout para People
        VerticalLayout peopleLayout = new VerticalLayout(peopleId, btnPeople, outPeople);
        peopleLayout.setPadding(false);

        btnPeople.addClickListener(e -> {
            Integer id = peopleId.getValue();
            if (id == null) {
                outPeople.setValue("❗ Introduce un ID válido.");
                return;
            }
            try {
                String url = "http://localhost:8080/api/people/" + id;
                HttpClient client = HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(5))
                        .build();
                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();
                HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
                Character c = new Gson().fromJson(resp.body(), Character.class);
                // Si el backend no hubiese puesto la fecha, la añadimos aquí:
                c.setDate(new Date());
                // Formatear con PrettyTime
                String when = new PrettyTime().format(c.getDate());
                outPeople.setValue(
                        "Nombre: " + c.getName() + "\n" +
                                "Altura: " + c.getHeight() + "\n" +
                                "Género: " + c.getGender() + "\n" +
                                "Última consulta: " + when
                );
            } catch (Exception ex) {
                outPeople.setValue("Error al consultar People: " + ex.getMessage());
            }
        });

        // —– STARSHIPS —–
        IntegerField shipId   = new IntegerField("ID Nave");
        Button       btnShip  = new Button("Consultar Ship");
        TextArea     outShip  = new TextArea("Resultado Nave");
        outShip.setWidthFull();
        outShip.setReadOnly(true);

        VerticalLayout shipLayout = new VerticalLayout(shipId, btnShip, outShip);
        shipLayout.setPadding(false);

        btnShip.addClickListener(e -> {
            Integer id = shipId.getValue();
            if (id == null) {
                outShip.setValue("❗ Introduce un ID válido.");
                return;
            }
            try {
                String url = "http://localhost:8080/api/starships/" + id;
                HttpClient client = HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(5))
                        .build();
                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();
                HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
                Character c = new Gson().fromJson(resp.body(), Character.class);
                c.setDate(new Date());
                String when = new PrettyTime().format(c.getDate());
                outShip.setValue(
                        "Nombre: " + c.getName() + "\n" +
                                "URL: "    + c.getUrl()  + "\n" +
                                "Última consulta: " + when
                );
            } catch (Exception ex) {
                outShip.setValue("Error al consultar Ship: " + ex.getMessage());
            }
        });

        // —– TABS —–
        Tab tabPeople = new Tab("People");
        Tab tabShips  = new Tab("Ships");
        Tabs tabs     = new Tabs(tabPeople, tabShips);

        // Contenedor de páginas
        Div pages = new Div(peopleLayout, shipLayout);
        peopleLayout.setVisible(true);
        shipLayout.setVisible(false);

        tabs.addSelectedChangeListener(event -> {
            boolean showPeople = event.getSelectedTab() == tabPeople;
            peopleLayout.setVisible(showPeople);
            shipLayout.setVisible(!showPeople);
        });

        // Añadimos todo a la vista
        add(tabs, pages);
        setSizeFull();
    }
}
