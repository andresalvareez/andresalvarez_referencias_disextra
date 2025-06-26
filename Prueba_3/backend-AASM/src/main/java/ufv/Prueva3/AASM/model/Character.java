package ufv.Prueva3.AASM.model;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Arrays;
import java.util.Date;

public class Character {

    private String name;
    private String height;
    private String mass;
    private String hair_color;
    private String skin_color;
    private String eye_color;
    private String birth_year;
    private String gender;
    private String homeworld;
    private String[] films;
    private String[] species;
    private String[] vehicles;
    private String[] starships;
    private String  created;
    private String edited;
    private String url;
    private Date date;

//    public Date getDate() { return date; }
//    Este metodo debe ser definido por el alumno acorde a la especificación de fecha solicitada
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) { this.date = date; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {
        this.hair_color = hair_color;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public void setSkin_color(String skin_color) {
        this.skin_color = skin_color;
    }

    public String getEye_color() {
        return eye_color;
    }

    public void setEye_color(String eye_color) {
        this.eye_color = eye_color;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public String[] getFilms() {
        return films;
    }

    public void setFilms(String[] films) {
        this.films = films;
    }

    public String[] getSpecies() {
        return species;
    }

    public void setSpecies(String[] species) {
        this.species = species;
    }

    public String[] getVehicles() {
        return vehicles;
    }

    public void setVehicles(String[] vehicles) {
        this.vehicles = vehicles;
    }

    public String[] getStarships() {
        return starships;
    }

    public void setStarships(String[] starships) {
        this.starships = starships;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Character(String name, String height, String mass, String hair_color, String skin_color, String eye_color, String birth_year, String gender, String homeworld, String[] films, String[] species, String[] vehicles, String[] starships, String created, String edited, String url) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.hair_color = hair_color;
        this.skin_color = skin_color;
        this.eye_color = eye_color;
        this.birth_year = birth_year;
        this.gender = gender;
        this.homeworld = homeworld;
        this.films = films;
        this.species = species;
        this.vehicles = vehicles;
        this.starships = starships;
        this.created = created;
        this.edited = edited;
        this.url = url;
    }

    public Character() {
    }

    public String getFormattedDate() {
        PrettyTime pt = new PrettyTime();
        // Para español: new PrettyTime(new Locale("es"))
        return pt.format(date);
    }

    @Override
    public String toString() {
//        Es necesario gestionar la devolución de la fecha en el formato solicitado
        return "Character{" +
                "name:'" + name + '\'' +
                ", height:'" + height + '\'' +
                ", mass:'" + mass + '\'' +
                ", hair_color:'" + hair_color + '\'' +
                ", skin_color:'" + skin_color + '\'' +
                ", eye_color:'" + eye_color + '\'' +
                ", birth_year:'" + birth_year + '\'' +
                ", gender:'" + gender + '\'' +
                ", homeworld:'" + homeworld + '\'' +
                ", films:" + Arrays.toString(films) +
                ", species:" + Arrays.toString(species) +
                ", vehicles:" + Arrays.toString(vehicles) +
                ", starships:" + Arrays.toString(starships) +
                ", created:'" + created + '\'' +
                ", edited:'" + edited + '\'' +
                ", url:'" + url + '\'' +
                ", date='" + getFormattedDate() + '\'' +
                '}';
    }
}
