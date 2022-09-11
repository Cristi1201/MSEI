package Tables;

public class TableNoteAbsenteElev {
    private String disciplina, note, absente;
    private double media;

    public TableNoteAbsenteElev(String disciplina, String note, String absente, double media) {
        this.disciplina = disciplina;
        this.note = note;
        this.absente = absente;
        this.media = media;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getNote() {
        return note;
    }

    public String getAbsente() {
        return absente;
    }

    public double getMedia() {
        return media;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setAbsente(String absente) {
        this.absente = absente;
    }

    public void setMedia(double media) {
        this.media = media;
    }

}
