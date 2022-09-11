package Tables;

import javafx.scene.control.CheckBox;

public class TableElevParinti {
    private int id_elev, id_parinti;
    private String elev, tel_elev, dataNastereElev, mama, tel_mama, tata, tel_tata;
    private CheckBox sterge;

    public TableElevParinti(int id_elev, String elev, String tel_elev, String dataNastereElev, int id_parinti,
                            String mama, String tel_mama, String tata, String tel_tata) {
        this.id_elev = id_elev;
        this.id_parinti = id_parinti;
        this.elev = elev;
        this.tel_elev = tel_elev;
        this.dataNastereElev = dataNastereElev;
        this.mama = mama;
        this.tel_mama = tel_mama;
        this.tata = tata;
        this.tel_tata = tel_tata;
        this.sterge = new CheckBox();
    }

    public int getId_elev() {
        return id_elev;
    }

    public int getId_parinti() {
        return id_parinti;
    }

    public String getElev() {
        return elev;
    }

    public String getTel_elev() {
        return tel_elev;
    }

    public String getDataNastereElev() {
        return dataNastereElev;
    }

    public String getMama() {
        return mama;
    }

    public String getTel_mama() {
        return tel_mama;
    }

    public String getTata() {
        return tata;
    }

    public String getTel_tata() {
        return tel_tata;
    }

    public CheckBox getSterge() {
        return sterge;
    }

    public void setId_elev(int id_elev) {
        this.id_elev = id_elev;
    }

    public void setId_parinti(int id_parinti) {
        this.id_parinti = id_parinti;
    }

    public void setElev(String elev) {
        this.elev = elev;
    }

    public void setTel_elev(String tel_elev) {
        this.tel_elev = tel_elev;
    }

    public void setDataNastereElev(String dataNastereElev) {
        this.dataNastereElev = dataNastereElev;
    }

    public void setMama(String mama) {
        this.mama = mama;
    }

    public void setTel_mama(String tel_mama) {
        this.tel_mama = tel_mama;
    }

    public void setTata(String tata) {
        this.tata = tata;
    }

    public void setTel_tata(String tel_tata) {
        this.tel_tata = tel_tata;
    }

    public void setSterge(CheckBox sterge) {
        this.sterge = sterge;
    }

}
