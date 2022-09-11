package Tables;

public class TableNoteAbsente {
    private int id;
    private String elevNoteAbsente, elevNote, elevAbsente;
    private double mediaNote;

    public TableNoteAbsente(int id, String elevNoteAbsente, String elevNote, String elevAbsente, double media) {
        this.id = id;
        this.elevNoteAbsente = elevNoteAbsente;
        this.elevNote = elevNote;
        this.elevAbsente = elevAbsente;
        this.mediaNote = media;
    }

    public int getId() {
        return id;
    }

    public String getElevNoteAbsente() {
        return elevNoteAbsente;
    }

    public String getElevNote() {
        return elevNote;
    }

    public String getElevAbsente() {
        return elevAbsente;
    }

    public double getMediaNote() {
        return mediaNote;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setElevNoteAbsente(String elevNoteAbsente) {
        this.elevNoteAbsente = elevNoteAbsente;
    }

    public void setElevNote(String elevNote) {
        this.elevNote = elevNote;
    }

    public void setElevAbsente(String elevAbsente) {
        this.elevAbsente = elevAbsente;
    }

    public void setMediaNote(float mediaNote) {
        this.mediaNote = mediaNote;
    }

}
