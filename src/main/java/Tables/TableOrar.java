package Tables;

import javafx.scene.control.CheckBox;

public class TableOrar {
    private String id, zi, durata, disciplina, nume, prenume;
    private CheckBox sterge;

    public TableOrar(String id, String zi, String durata, String disciplina, String nume, String prenume) {
        this.id = id;
        this.zi = zi;
        this.durata = durata;
        this.disciplina = disciplina;
        this.nume = nume;
        this.prenume = prenume;
        this.sterge = new CheckBox();
    }

    public String getId() {
        return id;
    }

    public String getZi() {
        return zi;
    }

    public String getDurata() {
        return durata;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public CheckBox getSterge() {
        return sterge;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public void setDisciplina(String disicplina) {
        this.disciplina = disicplina;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setSterge(CheckBox sterge) {
        this.sterge = sterge;
    }

}
