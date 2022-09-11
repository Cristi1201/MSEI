package Tables;

public class TableOrarElev {
    private String zi, disciplina, profesor, durata;

    public TableOrarElev(String zi, String disciplina, String profesor, String durata) {
        this.zi = zi;
        this.disciplina = disciplina;
        this.profesor = profesor;
        this.durata = durata;
    }

    public String getZi() {
        return zi;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getProfesor() {
        return profesor;
    }

    public String getDurata() {
        return durata;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

}
