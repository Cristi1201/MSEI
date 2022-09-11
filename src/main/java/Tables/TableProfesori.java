package Tables;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class TableProfesori {
    private String id, nume, prenume, telefon, clasa, discipline;
    @FXML
    private CheckBox sterge;

    public TableProfesori(String id, String nume, String prenume, String telefon, String clasa, String discipline) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.clasa = clasa;
        this.discipline = discipline;
        this.sterge = new CheckBox();
    }

    public String getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getClasa() {
        return clasa;
    }

    public String getDiscipline() {
        return discipline;
    }

    public CheckBox getSterge() {
        return sterge;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setClasa(String clasa) {
        this.clasa = clasa;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public void setSterge(CheckBox sterge) {
        this.sterge = sterge;
    }
}
