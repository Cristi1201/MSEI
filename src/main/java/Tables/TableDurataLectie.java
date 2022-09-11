package Tables;

import javafx.scene.control.CheckBox;

public class TableDurataLectie {
    private String id, durata;
    private CheckBox sterge;

    public TableDurataLectie(String id, String durata) {
        this.id = id;
        this.durata = durata;
        this.sterge = new CheckBox();
    }

    public String getId() {
        return id;
    }

    public String getDurata() {
        return durata;
    }

    public CheckBox getSterge() {
        return sterge;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public void setSterge(CheckBox sterge) {
        this.sterge = sterge;
    }

}