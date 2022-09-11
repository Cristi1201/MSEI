package com.example.msei.managementsystemforeducationalinstitution;

import DB_Connection.ConnectionBD;
import Tables.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckComboBox;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class HelloController { // main controller
    @FXML
    private Button btnAdmin, btnProfesor, btnElev, btnLogin, btnAdaugaProfesor, btnStergeProfesor, btnModificaProfesor,
            btnAplicaModificariProfesor, btnInapoiModificaProfesor, btnAplicaClasa, btnAplicaClasaInapoi, btnAplicaOrar,
            btnBackAdminAPorar, btnStergeOrar, btnInapoiModificaDurataLectie, btnStergeDurataLectie, btnAplicaDurata,
            btnStergeClasa, btnAdaugaClasa, btnModificaClasa, btnInapoiClase, btnNoteAbsente, btnEleviParinti,
            btnIesireProfesor, btnAlegeDisciplinaClasa, btnInapoiDisicplinaClasa, btnStergeNota, btnAdaugaNota,
            btnStergeAbsenta, btnAdaugaAbsenta, btnInapoiNoteAbsente, btnInapoiElevParinti, btnAdaugaElevParinti,
            btnStergeElevParinti, btnInapoiElevNA, btnInapoiElevO, btnNoteAbsenteElev, btnOrarElev, btnInapoiOrarElev,
            btnIesireElev;
    @FXML
    private AnchorPane mainAP, loginAP, adminAP, profesorAP, elevAP, adminAPprofesori, adminAPorar;
    @FXML
    private TextField loginUser, loginPassword, insertNumeProfesor, insertPrenumeProfesor, insertTelefonProfesor,
            modificaNumeProfesor, modificaPrenumeProfesor, modificaTelefonProfesor, durataInput, clasaInput,
            insertNumeElev, insertPrenumeElev, insertTelefonElev, insertNumeMama, insertPrenumeMama, insertTelefonMama,
            insertNumeTata, insertPrenumeTata, insertTelefonTata;
    @FXML
    private Text loginWrong, adminNumePrenume, profesorNumePrenume, elevNumePrenume, insertProfesorWrong,
            modificaProfesorWrong, clasaOrar, modificaDurataLectieWrong, durataLectieWrong, numeDiriginte,
            stergeClasaWrong, modificaClasaWrong, adaugaClasaWrong, alegeDisciplinaClasaWrong, clasaNoteAbsente,
            disciplinaNoteAbsente, gestioneazaNotaWrong, gestioneazaAbsentaWrong, clasaElevParinti,
            insertElevParintiWrong, diriginteElev;
    @FXML
    private TableView<TableProfesori> tableProfesori;
    @FXML
    private TableView<TableOrar> tableOrar;
    @FXML
    private TableView<TableDurataLectie> tableDurataLectie;
    @FXML
    private TableView<TableNoteAbsente> tableNoteAbsente;
    @FXML
    private TableView<TableElevParinti> tableElevParinti;
    @FXML
    private TableView<TableNoteAbsenteElev> tableNoteAbsenteElev;
    @FXML
    private TableView<TableOrarElev> tableOrarElev;
    @FXML
    private TableColumn<TableProfesori, String> id_profesor, nume_profesor, prenume_profesor, telefon_profesor,
            clasa_profesor, discipline_profesor, sterge_profesor;
    @FXML
    private TableColumn<TableOrar, String> zi_disciplina_orar, durata_disciplina_orar, disciplina_orar, nume_prof_orar,
            prenume_prof_orar, sterge_orar;
    @FXML
    private TableColumn<TableDurataLectie, String> durata_lectie, sterge_durata_lectie;
    @FXML
    private TableColumn<TableNoteAbsente, String> elevNoteAbsente, elevNote, elevAbsente;
    @FXML
    private TableColumn<TableNoteAbsente, Double> mediaNote;
    @FXML
    private TableColumn<TableElevParinti, String> elevElevParinti, telElev, dataNastereElev, mamaElevParinti, telMama,
            tataElevParinti, telTata, stergeElevParinti;
    @FXML
    private TableColumn<TableNoteAbsenteElev, String> disciplina_elev, note_elev, absente_elev;
    @FXML
    private TableColumn<TableNoteAbsenteElev, Double> media_elev;
    @FXML
    private TableColumn<TableOrarElev, String> zi_orar_elev, disciplina_orar_elev, profesor_orar_elev, durata_orar_elev;
    @FXML
    private ComboBox<String> comboBoxModificaProfesor, comboBoxAlegeClasa, comboBoxZiua, comboBoxDurata,
            comboBoxDisciplina, comboBoxDiriginte, comboBoxClase, comboBoxSchimbaDiriginte, comboBoxAlegeDiriginte,
            comboBoxAlegeDisciplinaP, comboBoxAlegeClasaP, comboBoxElevNota, comboBoxNota, comboBoxElevAbsenta,
            comboBoxAbsenta, comboBoxAlegeParintiElev;
    @FXML
    private CheckComboBox<String> checkComboBoxDiscipline, checkComboBoxModificaDisciplineProfesor;
    @FXML
    private Pane modificaProfesor, modificaOrar, modificaDurataLectie, modificaClase, alegeClasaDisciplina,
            gestioneazaNoteAbsente, gestioneazaEleviParinti, vizualizeazaNoteAbsenteElev, vizualizeazaOrarElev;
    @FXML
    private DatePicker insertDataNastereElev;

    private int user = 0; // 1 - admin, 2 - profesor, 3 - elev

    ObservableList<TableProfesori> listTB_TP = FXCollections.observableArrayList();
    HashMap<Integer, String> dataDisciplineMap = new HashMap<>();

    // encript to MD5
    private static String MD5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashText = number.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnLoginClick() {
        loginAP.toFront();
        String login = loginUser.getText();
        String password = loginPassword.getText();
        password = MD5(password);
        Connection c = ConnectionBD.getConnection();
        ResultSet rez = null;
        switch (user) {
            case 0:
                loginWrong.setText("Eroare de logare");
                break;
            case 1:
                try {
                    rez = c.createStatement().executeQuery(
                            "SELECT nume_admin, prenume_admin, functie FROM administratie WHERE nume_de_utilizator=\""
                                    + login + "\" AND parola=\"" + password + "\" LIMIT 1");
                    loginWrong.setText("Date incorecte !");
                    while (rez.next()) {
                        loginWrong.setText("");
                        adminAnchorPanel(rez.getString(1), rez.getString(2), rez.getString(3));
                    }
                } catch (Exception e) {
                    loginWrong.setText("Eroare de logare");
                }
                break;
            case 2:
                try {
                    rez = c.createStatement().executeQuery(
                            "SELECT id_profesor, nume_profesor, prenume_profesor FROM profesori WHERE nume_de_utilizator=\""
                                    + login + "\" AND parola=\"" + password + "\" LIMIT 1");
                    loginWrong.setText("Date incorecte !");
                    while (rez.next()) {
                        loginWrong.setText("");
                        ResultSet obiectePredate = c.createStatement()
                                .executeQuery("SELECT discipline.nume_discipline FROM discipline\r\n"
                                        + "JOIN disciplina_profesor ON discipline.id_disciplina = disciplina_profesor.id_disciplina\r\n"
                                        + "JOIN profesori ON disciplina_profesor.id_profesor = profesori.id_profesor AND profesori.id_profesor = "
                                        + rez.getString(1) + ";");
                        ArrayList<String> obPred = new ArrayList<String>();
                        while (obiectePredate.next()) {
                            obPred.add(obiectePredate.getString(1));
                        }

                        ResultSet diriginte = c.createStatement().executeQuery("SELECT clase.nume_clasa FROM clase\r\n"
                                + "JOIN profesori on clase.id_diriginte = profesori.id_profesor AND profesori.id_profesor = "
                                + rez.getString(1) + ";");

                        while (diriginte.next()) {
                            profesorAnchorPanel(rez.getString(2), rez.getString(3), true, diriginte.getString(1), obPred);
                            return;
                        }
                        profesorAnchorPanel(rez.getString(2), rez.getString(3), false, "", obPred);
                    }
                    break;
                } catch (Exception e) {
                    loginWrong.setText("Eroare de logare");
                }
                break;
            case 3:
                try {
                    rez = c.createStatement()
                            .executeQuery("SELECT id_elev, nume_elev, prenume_elev FROM elev WHERE nume_de_utilizator = \""
                                    + login + "\" AND parola = \"" + password + "\" LIMIT 1;");
                    loginWrong.setText("Date incorecte !");
                    while (rez.next()) {
                        loginWrong.setText("");
                        elevAnchorPanel(rez.getInt(1), rez.getString(2), rez.getString(3));
                    }
                } catch (Exception e) {
                    loginWrong.setText("Eroare de logare");
                    System.out.println(e.getMessage());
                }

                break;
        }
    }

    public void btnBackClick(ActionEvent e) {
        mainAP.toFront();
        user = 0;
    }

    public void btnAdminClick(ActionEvent e) {
        loginUser.setText("");
        loginPassword.setText("");
        loginAP.toFront();
        user = 1;
    }

    public void btnProfesorClick(ActionEvent e) {
        loginUser.setText("");
        loginPassword.setText("");
        loginAP.toFront();
        user = 2;
    }

    public void btnElevClick(ActionEvent e) {
        loginUser.setText("");
        loginPassword.setText("");
        loginAP.toFront();
        user = 3;
    }

    // ADMINISTRATOR AP
    private void adminAnchorPanel(String nume, String prenume, String functie) {
        adminAP.toFront();
        adminNumePrenume.setText(nume + " " + prenume + ". " + functie + ".");
    }

    // PROFESOR / DIRIGINTE AP
    private void profesorAnchorPanel(String nume, String prenume, boolean diriginte, String clasa,
                                     ArrayList<String> obPred) throws SQLException {
        profesorAP.toFront();
        String obiecte = "";

        if (diriginte) {
            btnEleviParinti.setVisible(true);
            for (int i = 0; i < obPred.size(); i++) {
                obiecte += obPred.get(i);
                if (obPred.size() - 1 > i) {
                    obiecte += ", ";
                }
            }
            profesorNumePrenume
                    .setText(nume + " " + prenume + ". Diriginte a clasei " + clasa + "\nObiecte predate: " + obiecte);
        } else {
            btnEleviParinti.setVisible(false);
            for (int i = 0; i < obPred.size(); i++) {
                obiecte += obPred.get(i);
                if (obPred.size() - 1 > i) {
                    obiecte += ", ";
                }
            }
            profesorNumePrenume.setText(nume + " " + prenume + ".\nObiecte predate: " + obiecte);
        }

        EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                initializeAlegeClasaDisciplina(nume, prenume);
            }
        };
        btnNoteAbsente.setOnAction(e1);

        EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                initializeElevParinti(clasa);
            }
        };
        btnEleviParinti.setOnAction(e2);

        EventHandler<ActionEvent> e3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                mainAP.toFront();
            }
        };
        btnIesireProfesor.setOnAction(e3);
    }

    public void initializeAlegeClasaDisciplina(String nume, String prenume) {
        ResultSet disciplineProfesorRS;
        HashMap<Integer, String> disciplineProfesorMAP = new HashMap<Integer, String>();
        HashMap<Integer, String> claseDisciplinaProfesorMAP = new HashMap<Integer, String>();
        Connection c = ConnectionBD.getConnection();
        alegeDisciplinaClasaWrong.setText("");
        comboBoxAlegeDisciplinaP.getItems().clear();
        comboBoxAlegeClasaP.getItems().clear();
        alegeClasaDisciplina.setVisible(true);

        try {
            disciplineProfesorRS = c.createStatement()
                    .executeQuery("select dp.id_disciplina_profesor, d.nume_discipline\r\n" + "	from discipline d\r\n"
                            + "    JOIN disciplina_profesor dp on dp.id_disciplina = d.id_disciplina\r\n"
                            + "    JOIN profesori p on p.id_profesor = dp.id_profesor and p.nume_profesor=\"" + nume
                            + "\" AND p.prenume_profesor = \"" + prenume + "\";");
            while (disciplineProfesorRS.next()) {
                comboBoxAlegeDisciplinaP.getItems().add(disciplineProfesorRS.getString(2));
                disciplineProfesorMAP.put(disciplineProfesorRS.getInt(1), disciplineProfesorRS.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ResultSet disciplineProfesorRS;
                comboBoxAlegeClasaP.getItems().clear();

                for (Entry<Integer, String> entry : disciplineProfesorMAP.entrySet()) {
                    if (entry.getValue().equals(comboBoxAlegeDisciplinaP.getSelectionModel().getSelectedItem())) {
                        try {
                            disciplineProfesorRS = c.createStatement()
                                    .executeQuery("select orar.id_clasa, clase.nume_clasa from clase\r\n"
                                            + "join orar on orar.id_clasa = clase.id_clasa and orar.id_disciplina_profesor = "
                                            + entry.getKey() + "\r\n" + "GROUP BY orar.id_clasa;");
                            while (disciplineProfesorRS.next()) {
                                comboBoxAlegeClasaP.getItems().add(disciplineProfesorRS.getString(2));
                                claseDisciplinaProfesorMAP.put(disciplineProfesorRS.getInt(1),
                                        disciplineProfesorRS.getString(2));
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        };
        comboBoxAlegeDisciplinaP.setOnAction(e1);

        EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (comboBoxAlegeDisciplinaP.getSelectionModel().getSelectedItem() == null
                        || comboBoxAlegeClasaP.getSelectionModel().getSelectedItem() == null) {
                    alegeDisciplinaClasaWrong.setText("Alegeti disciplina si clasa");
                } else {

                    for (Entry<Integer, String> entry1 : disciplineProfesorMAP.entrySet()) {
                        if (comboBoxAlegeDisciplinaP.getSelectionModel().getSelectedItem().equals(entry1.getValue())) {

                            for (Entry<Integer, String> entry2 : claseDisciplinaProfesorMAP.entrySet()) {
                                if (comboBoxAlegeClasaP.getSelectionModel().getSelectedItem()
                                        .equals(entry2.getValue())) {
                                    gestioneazaNoteAbsenteElevi(entry1.getKey(), entry1.getValue(), entry2.getKey(),
                                            entry2.getValue());
                                    alegeDisciplinaClasaWrong.setText("");
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        };
        btnAlegeDisciplinaClasa.setOnAction(e2);

        EventHandler<ActionEvent> e3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                alegeClasaDisciplina.setVisible(false);
            }
        };
        btnInapoiDisicplinaClasa.setOnAction(e3);
    }

    public void gestioneazaNoteAbsenteElevi(int idDisciplinaProfesor, String disciplina, int idClasa, String clasa) {
        gestioneazaNoteAbsente.setVisible(true);
        clasaNoteAbsente.setText(clasa);
        disciplinaNoteAbsente.setText(disciplina);
        tableNoteAbsente.getItems().clear();
        comboBoxElevNota.getItems().clear();
        comboBoxNota.getItems().clear();
        comboBoxElevAbsenta.getItems().clear();
        comboBoxAbsenta.getItems().clear();
        gestioneazaNotaWrong.setText("");
        gestioneazaAbsentaWrong.setText("");

        Connection c = ConnectionBD.getConnection();
        ResultSet elevNoteAbsenteRS;
        ObservableList<TableNoteAbsente> elevNoteAbsenteTB_TNA = FXCollections.observableArrayList();
        elevNoteAbsenteTB_TNA.clear();
        final DecimalFormat df = new DecimalFormat("0.00");

        try {
            elevNoteAbsenteRS = c.createStatement().executeQuery(
                    "select elev.id_elev, CONCAT(elev.nume_elev, ' ', elev.prenume_elev), note.note, absente.absente from elev\r\n"
                            + "join clase on elev.id_clasa = clase.id_clasa and clase.id_clasa = " + idClasa + "\r\n"
                            + "left join (SELECT note.id_elev, GROUP_CONCAT(note.nota) as note from note where note.id_lectie = "
                            + idDisciplinaProfesor + " GROUP BY note.id_elev) note on note.id_elev = elev.id_elev\r\n"
                            + "left join (SELECT absente.id_elev, GROUP_CONCAT(absente.m_n) as absente from absente where absente.id_lectie = "
                            + idDisciplinaProfesor
                            + " GROUP BY absente.id_elev) absente on absente.id_elev = elev.id_elev\r\n"
                            + "ORDER by. elev.nume_elev;");

            while (elevNoteAbsenteRS.next()) {
                double media = 0;
                if (elevNoteAbsenteRS.getString(3) != null) {
                    String[] note = elevNoteAbsenteRS.getString(3).split("\\,");
                    double nrNote = 0;

                    for (String s : note) {
                        media += Double.parseDouble(s);
                        nrNote++;
                    }
                    media = Double.parseDouble(df.format(media / nrNote));
                }

                elevNoteAbsenteTB_TNA
                        .add(new TableNoteAbsente(elevNoteAbsenteRS.getInt(1), elevNoteAbsenteRS.getString(2),
                                elevNoteAbsenteRS.getString(3), elevNoteAbsenteRS.getString(4), media));
                comboBoxElevNota.getItems().add(elevNoteAbsenteRS.getString(2));
                comboBoxElevAbsenta.getItems().add(elevNoteAbsenteRS.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        elevNoteAbsente.setCellValueFactory(new PropertyValueFactory<TableNoteAbsente, String>("elevNoteAbsente"));
        elevNote.setCellValueFactory(new PropertyValueFactory<TableNoteAbsente, String>("elevNote"));
        elevAbsente.setCellValueFactory(new PropertyValueFactory<TableNoteAbsente, String>("elevAbsente"));
        mediaNote.setCellValueFactory(new PropertyValueFactory<TableNoteAbsente, Double>("mediaNote"));

        tableNoteAbsente.setItems(elevNoteAbsenteTB_TNA);

        // comboBoxNota
        for (int i = 10; i > 0; i--) {
            comboBoxNota.getItems().add(Integer.toString(i));
        }
        // comboBoxAbsenta
        comboBoxAbsenta.getItems().add("m");
        comboBoxAbsenta.getItems().add("n");

        // STERGE NOTA
        EventHandler<ActionEvent> stergeNota = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (comboBoxElevNota.getSelectionModel().getSelectedItem() == null
                        || comboBoxNota.getSelectionModel().getSelectedItem() == null) {
                    gestioneazaNotaWrong.setText("Alegeti elevul si nota");
                } else {
                    try {
                        for (TableNoteAbsente tna : elevNoteAbsenteTB_TNA) {
                            if (comboBoxElevNota.getSelectionModel().getSelectedItem()
                                    .equals(tna.getElevNoteAbsente())) {
                                try {
                                    c.prepareStatement("DELETE FROM note WHERE id_elev = \"" + tna.getId()
                                                    + "\" AND id_lectie = \"" + idDisciplinaProfesor + "\" AND nota = \""
                                                    + comboBoxNota.getSelectionModel().getSelectedItem() + "\" LIMIT 1")
                                            .execute();

                                    break;
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        gestioneazaNoteAbsenteElevi(idDisciplinaProfesor, disciplina, idClasa, clasa);
                    } catch (java.util.ConcurrentModificationException ex) {
                        gestioneazaNotaWrong.setText("");
                    }
                }
            }
        };
        btnStergeNota.setOnAction(stergeNota);

        // ADAUGA NOTA
        EventHandler<ActionEvent> adaugNota = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (comboBoxElevNota.getSelectionModel().getSelectedItem() == null
                        || comboBoxNota.getSelectionModel().getSelectedItem() == null) {
                    gestioneazaNotaWrong.setText("Alegeti elevul si nota");
                } else {
                    try {
                        for (TableNoteAbsente tna : elevNoteAbsenteTB_TNA) {
                            if (comboBoxElevNota.getSelectionModel().getSelectedItem()
                                    .equals(tna.getElevNoteAbsente())) {
                                try {
                                    c.prepareStatement("INSERT INTO note (id_elev, id_lectie, nota) VALUES " + "("
                                            + tna.getId() + ", " + idDisciplinaProfesor + ", \""
                                            + comboBoxNota.getSelectionModel().getSelectedItem() + "\")").execute();
                                    break;
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        gestioneazaNoteAbsenteElevi(idDisciplinaProfesor, disciplina, idClasa, clasa);
                    } catch (java.util.ConcurrentModificationException ex) {
                        gestioneazaNotaWrong.setText("");
                    }
                }
            }
        };
        btnAdaugaNota.setOnAction(adaugNota);

        EventHandler<ActionEvent> stergeAbsenta = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (comboBoxElevAbsenta.getSelectionModel().getSelectedItem() == null
                        || comboBoxAbsenta.getSelectionModel().getSelectedItem() == null) {
                    gestioneazaAbsentaWrong.setText("Alegeti elevul si tipul la absenta");
                } else {
                    try {
                        for (TableNoteAbsente tna : elevNoteAbsenteTB_TNA) {
                            if (comboBoxElevAbsenta.getSelectionModel().getSelectedItem()
                                    .equals(tna.getElevNoteAbsente())) {
                                try {
                                    c.prepareStatement("DELETE FROM absente WHERE id_elev = \"" + tna.getId()
                                                    + "\" AND id_lectie = \"" + idDisciplinaProfesor + "\" AND m_n = \""
                                                    + comboBoxAbsenta.getSelectionModel().getSelectedItem() + "\" LIMIT 1")
                                            .execute();

                                    break;
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        gestioneazaNoteAbsenteElevi(idDisciplinaProfesor, disciplina, idClasa, clasa);
                    } catch (java.util.ConcurrentModificationException ex) {
                        gestioneazaAbsentaWrong.setText("");
                    }
                }
            }
        };
        btnStergeAbsenta.setOnAction(stergeAbsenta);

        EventHandler<ActionEvent> adaugaAbsenta = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (comboBoxElevAbsenta.getSelectionModel().getSelectedItem() == null
                        || comboBoxAbsenta.getSelectionModel().getSelectedItem() == null) {
                    gestioneazaAbsentaWrong.setText("Alegeti elevul si tipul la absenta");
                } else {
                    try {
                        for (TableNoteAbsente tna : elevNoteAbsenteTB_TNA) {
                            if (comboBoxElevAbsenta.getSelectionModel().getSelectedItem()
                                    .equals(tna.getElevNoteAbsente())) {
                                try {
                                    c.prepareStatement("INSERT INTO absente (id_elev, id_lectie, m_n) VALUES " + "("
                                            + tna.getId() + ", " + idDisciplinaProfesor + ", \""
                                            + comboBoxAbsenta.getSelectionModel().getSelectedItem() + "\")").execute();
                                    break;
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        gestioneazaNoteAbsenteElevi(idDisciplinaProfesor, disciplina, idClasa, clasa);
                    } catch (java.util.ConcurrentModificationException ex) {
                        gestioneazaAbsentaWrong.setText("");
                    }
                }
            }
        };
        btnAdaugaAbsenta.setOnAction(adaugaAbsenta);

        EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                gestioneazaNoteAbsente.setVisible(false);
            }
        };
        btnInapoiNoteAbsente.setOnAction(e1);
    }

    public void initializeElevParinti(String clasa) {
        clasaElevParinti.setText(clasa);
        tableElevParinti.getItems().clear();
        Connection c = ConnectionBD.getConnection();
        ResultSet dataElevParintiRS;
        int idClasa1 = 0;
        insertElevParintiWrong.setText("");
        insertNumeElev.clear();
        insertPrenumeElev.clear();
        insertTelefonElev.clear();
        comboBoxAlegeParintiElev.getItems().clear();
        insertNumeMama.clear();
        insertPrenumeMama.clear();
        insertTelefonMama.clear();
        insertNumeTata.clear();
        insertPrenumeTata.clear();
        insertTelefonTata.clear();

        try {
            dataElevParintiRS = c.createStatement()
                    .executeQuery("SELECT id_clasa from clase where nume_clasa = \"" + clasa + "\" LIMIT 1");
            while (dataElevParintiRS.next()) {
                idClasa1 = dataElevParintiRS.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        final int idClasa = idClasa1;

        gestioneazaEleviParinti.setVisible(true);
        ObservableList<TableElevParinti> elevParintiTB_TEP = FXCollections.observableArrayList();
        elevParintiTB_TEP.clear();

        try {
            dataElevParintiRS = c.createStatement().executeQuery(
                    "SELECT elev.id_elev, concat(elev.nume_elev, ' ', elev.prenume_elev) as elev, elev.telefon, elev.data_nastere, "
                            + "parinti.id_parinti, concat(parinti.nume_mama, ' ', parinti.prenume_mama) as mama, parinti.telefon_mama, "
                            + "concat(parinti.nume_tata, ' ', parinti.prenume_tata) as tata, parinti.telefon_tata\r\n"
                            + "from elev\r\n"
                            + "join parinti on elev.id_parinti = parinti.id_parinti and elev.id_clasa = " + idClasa
                            + "\r\n" + "ORDER by elev.nume_elev;");

            while (dataElevParintiRS.next()) {
                elevParintiTB_TEP.add(new TableElevParinti(dataElevParintiRS.getInt(1), dataElevParintiRS.getString(2),
                        dataElevParintiRS.getString(3), dataElevParintiRS.getString(4), dataElevParintiRS.getInt(5),
                        dataElevParintiRS.getString(6), dataElevParintiRS.getString(7), dataElevParintiRS.getString(8),
                        dataElevParintiRS.getString(9)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        elevElevParinti.setCellValueFactory(new PropertyValueFactory<TableElevParinti, String>("elev"));
        telElev.setCellValueFactory(new PropertyValueFactory<TableElevParinti, String>("tel_elev"));
        dataNastereElev.setCellValueFactory(new PropertyValueFactory<TableElevParinti, String>("dataNastereElev"));
        mamaElevParinti.setCellValueFactory(new PropertyValueFactory<TableElevParinti, String>("mama"));
        telMama.setCellValueFactory(new PropertyValueFactory<TableElevParinti, String>("tel_mama"));
        tataElevParinti.setCellValueFactory(new PropertyValueFactory<TableElevParinti, String>("tata"));
        telTata.setCellValueFactory(new PropertyValueFactory<TableElevParinti, String>("tel_tata"));
        stergeElevParinti.setCellValueFactory(new PropertyValueFactory<TableElevParinti, String>("sterge"));

        tableElevParinti.setItems(elevParintiTB_TEP);

        // ADAUGA ELEV ( + PARINTI)
        // populare comboBoxAlegeParintiElev
        HashMap<Integer, String> parintiMAP = new HashMap<Integer, String>();

        try {
            dataElevParintiRS = c.createStatement().executeQuery(
                    "SELECT id_parinti, CONCAT(\"M: \", nume_mama,' ', prenume_mama, ' T: ', nume_tata, ' ', prenume_tata) as parinti \r\n"
                            + "FROM `parinti`\r\n" + "ORDER BY nume_mama, nume_tata;");
            while (dataElevParintiRS.next()) {
                comboBoxAlegeParintiElev.getItems().add(dataElevParintiRS.getString(2));
                parintiMAP.put(dataElevParintiRS.getInt(1), dataElevParintiRS.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // butonul Adauga Elev
        EventHandler<ActionEvent> adaugaElevParinti = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                if (insertNumeElev.getText().length() == 0 || insertPrenumeElev.getText().length() == 0
                        || insertTelefonElev.getText().length() == 0 || insertDataNastereElev.getValue() == null) {
                    insertElevParintiWrong.setText("Verificati datele introduse");
                } else {
                    insertElevParintiWrong.setText("");
                    if (comboBoxAlegeParintiElev.getSelectionModel().getSelectedItem() != null) {

                        for (Entry<Integer, String> entry : parintiMAP.entrySet()) {
                            if (comboBoxAlegeParintiElev.getSelectionModel().getSelectedItem()
                                    .equals(entry.getValue())) {
                                try {
                                    Integer.parseInt(insertTelefonElev.getText());
                                    if (insertTelefonElev.getText().length() == 9) {
                                        try {
                                            c.prepareStatement(
                                                            "INSERT into elev (nume_elev, prenume_elev, data_nastere, id_clasa, id_parinti, telefon, nume_de_utilizator, parola) values "
                                                                    + "(\"" + insertNumeElev.getText() + "\", \""
                                                                    + insertPrenumeElev.getText() + "\", " + "\""
                                                                    + insertDataNastereElev.getValue().toString() + "\", "
                                                                    + idClasa + ", " + "" + entry.getKey() + ", \""
                                                                    + insertTelefonElev.getText() + "\", \""
                                                                    + insertNumeElev.getText().toLowerCase() + "."
                                                                    + insertPrenumeElev.getText().toLowerCase() + "\", \""
                                                                    + MD5(insertNumeElev.getText().toLowerCase() + "."
                                                                    + insertPrenumeElev.getText().toLowerCase())
                                                                    + "\" )")
                                                    .execute();
                                            break;
                                        } catch (SQLException ex) {
                                            ex.printStackTrace();
                                        }
                                    } else {
                                        insertElevParintiWrong.setText("Verificati datele introduse");
                                    }
                                } catch (NumberFormatException ex) {
                                    insertElevParintiWrong.setText("Verificati datele introduse");
                                }

                            }
                        }
                        initializeElevParinti(clasa);
                    } else if (insertNumeMama.getText().length() != 0 && insertPrenumeMama.getText().length() != 0
                            && insertTelefonMama.getText().length() != 0 && insertNumeTata.getText().length() != 0
                            && insertPrenumeTata.getText().length() != 0 && insertTelefonTata.getText().length() != 0) {

                        insertElevParintiWrong.setText("");

                        try {
                            Integer.parseInt(insertTelefonElev.getText());
                            Integer.parseInt(insertTelefonMama.getText());
                            Integer.parseInt(insertTelefonTata.getText());
                            if (insertTelefonElev.getText().length() == 9 && insertTelefonMama.getText().length() == 9
                                    && insertTelefonTata.getText().length() == 9) {
                                ResultSet dataElevParintiRS;
                                try {
                                    c.prepareStatement(
                                                    "INSERT INTO parinti (nume_mama, prenume_mama, telefon_mama, nume_tata, prenume_tata, telefon_tata) VALUES "
                                                            + "(\"" + insertNumeMama.getText() + "\", \""
                                                            + insertPrenumeMama.getText() + "\", \""
                                                            + insertTelefonMama.getText() + "\"," + "\""
                                                            + insertNumeTata.getText() + "\", \"" + insertPrenumeTata.getText()
                                                            + "\", \"" + insertTelefonTata.getText() + "\")")
                                            .execute();

                                    dataElevParintiRS = c.createStatement()
                                            .executeQuery("SELECT id_parinti FROM parinti WHERE " + "nume_mama = \""
                                                    + insertNumeMama.getText() + "\" AND prenume_mama = \""
                                                    + insertPrenumeMama.getText() + "\" AND telefon_mama = \""
                                                    + insertTelefonMama.getText() + "\" AND " + "nume_tata = \""
                                                    + insertNumeTata.getText() + "\" AND prenume_tata = \""
                                                    + insertPrenumeTata.getText() + "\" AND telefon_tata = \""
                                                    + insertTelefonTata.getText() + "\" LIMIT 1");

                                    while (dataElevParintiRS.next()) {
                                        try {
                                            c.prepareStatement(
                                                            "INSERT into elev (nume_elev, prenume_elev, data_nastere, id_clasa, id_parinti, telefon, nume_de_utilizator, parola) values "
                                                                    + "(\"" + insertNumeElev.getText() + "\", \""
                                                                    + insertPrenumeElev.getText() + "\", " + "\""
                                                                    + insertDataNastereElev.getValue().toString() + "\", "
                                                                    + idClasa + ", " + "" + dataElevParintiRS.getInt(1) + ", \""
                                                                    + insertTelefonElev.getText() + "\", \""
                                                                    + insertNumeElev.getText().toLowerCase() + "."
                                                                    + insertPrenumeElev.getText().toLowerCase() + "\", \""
                                                                    + MD5(insertNumeElev.getText().toLowerCase() + "."
                                                                    + insertPrenumeElev.getText().toLowerCase())
                                                                    + "\" )")
                                                    .execute();
                                            break;
                                        } catch (SQLException ex) {
                                            ex.printStackTrace();
                                        }
                                    }

                                    initializeElevParinti(clasa);
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                insertElevParintiWrong.setText("Verificati datele introduse");
                            }
                        } catch (NumberFormatException ex) {
                            insertElevParintiWrong.setText("Verificati datele introduse");
                        }

                    } else {
                        insertElevParintiWrong.setText("Verificati datele introduse");
                    }
                }
            }
        };
        btnAdaugaElevParinti.setOnAction(adaugaElevParinti);

        // butonul Sterge Elev Parinti
        EventHandler<ActionEvent> stergeElevParinti = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                for (TableElevParinti tep : elevParintiTB_TEP) {
                    if (tep.getSterge().isSelected()) {
                        try {
                            c.prepareStatement("delete from elev where id_elev = " + tep.getId_elev()).execute();
                            c.prepareStatement("delete from parinti where id_parinti = " + tep.getId_parinti())
                                    .execute();
                        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
                            continue;
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                initializeElevParinti(clasa);
            }
        };
        btnStergeElevParinti.setOnAction(stergeElevParinti);

        EventHandler<ActionEvent> inapoiElevParinti = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                gestioneazaEleviParinti.setVisible(false);
            }
        };
        btnInapoiElevParinti.setOnAction(inapoiElevParinti);
    }

    // ELEV AP
    private void elevAnchorPanel(int idElev, String nume, String prenume) {
        elevAP.toFront();
        elevNumePrenume.setText(nume + " " + prenume + ".");

        Connection c = ConnectionBD.getConnection();
        ResultSet dataElevRS;
        // NOTE ABSENTE
        try {
            dataElevRS = c.createStatement().executeQuery(
                    "select CONCAT(profesori.nume_profesor, ' ', profesori.prenume_profesor), profesori.telefon from elev\r\n"
                            + "join clase on clase.id_clasa = elev.id_clasa and elev.id_elev = " + idElev + "\r\n"
                            + "join profesori on clase.id_diriginte = profesori.id_profesor LIMIT 1");
            while (dataElevRS.next()) {
                diriginteElev
                        .setText("Diriginte: " + dataElevRS.getString(1) + "\nNr telefon: " + dataElevRS.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ObservableList<TableNoteAbsenteElev> dataElevTB_TNA = FXCollections.observableArrayList();
        final DecimalFormat df = new DecimalFormat("0.00");

        try {
            dataElevRS = c.createStatement()
                    .executeQuery("select discipline.nume_discipline as disciplina, note.nota, absente.m_n\r\n"
                            + "	from orar\r\n"
                            + "    join disciplina_profesor on orar.id_disciplina_profesor = disciplina_profesor.id_disciplina_profesor \r\n"
                            + "    join discipline on discipline.id_disciplina = disciplina_profesor.id_disciplina\r\n"
                            + "    join clase on orar.id_clasa = clase.id_clasa \r\n"
                            + "    join elev on elev.id_clasa = clase.id_clasa and elev.id_elev = " + idElev + "\r\n"
                            + "    left join (SELECT note.id_elev, note.id_lectie, GROUP_CONCAT(note.nota) as nota from note where note.id_elev = "
                            + idElev + " GROUP by note.id_lectie) \r\n"
                            + "    	note on note.id_elev = elev.id_elev and note.id_lectie = disciplina_profesor.id_disciplina_profesor\r\n"
                            + "    left join (SELECT absente.id_elev, absente.id_lectie, GROUP_CONCAT(absente.m_n) as m_n from absente where absente.id_elev = "
                            + idElev + " GROUP by absente.id_lectie) \r\n"
                            + "    	absente on absente.id_elev = elev.id_elev and absente.id_lectie = disciplina_profesor.id_disciplina_profesor\r\n"
                            + "GROUP BY discipline.id_disciplina;");
            while (dataElevRS.next()) {
                double media = 0;
                if (dataElevRS.getString(2) != null) {
                    String[] note = dataElevRS.getString(2).split("\\,");
                    double nrNote = 0;

                    for (String s : note) {
                        media += Double.parseDouble(s);
                        nrNote++;
                    }
                    media = Double.parseDouble(df.format(media / nrNote));
                }
                dataElevTB_TNA.add(new TableNoteAbsenteElev(dataElevRS.getString(1), dataElevRS.getString(2),
                        dataElevRS.getString(3), media));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        disciplina_elev.setCellValueFactory(new PropertyValueFactory<TableNoteAbsenteElev, String>("disciplina"));
        note_elev.setCellValueFactory(new PropertyValueFactory<TableNoteAbsenteElev, String>("note"));
        absente_elev.setCellValueFactory(new PropertyValueFactory<TableNoteAbsenteElev, String>("absente"));
        media_elev.setCellValueFactory(new PropertyValueFactory<TableNoteAbsenteElev, Double>("media"));

        tableNoteAbsenteElev.setItems(dataElevTB_TNA);

        // ORAR
        ObservableList<TableOrarElev> dataElevTB_TOE = FXCollections.observableArrayList();
        try {
            dataElevRS = c.createStatement().executeQuery(
                    "select zi_saptamana.nume_zi as zi, discipline.nume_discipline as disciplina, concat(profesori.nume_profesor, ' ', profesori.prenume_profesor) as profesor, durata_lectie.durata \r\n"
                            + "	from orar\r\n"
                            + "    join disciplina_profesor on orar.id_disciplina_profesor = disciplina_profesor.id_disciplina_profesor \r\n"
                            + "    join discipline on discipline.id_disciplina = disciplina_profesor.id_disciplina\r\n"
                            + "    join profesori on profesori.id_profesor = disciplina_profesor.id_profesor\r\n"
                            + "    join clase on orar.id_clasa = clase.id_clasa\r\n"
                            + "    join elev on elev.id_clasa = clase.id_clasa and elev.id_elev = " + idElev + "\r\n"
                            + "    join durata_lectie on orar.id_durata = durata_lectie.id_durata_lectie\r\n"
                            + "    join zi_saptamana on orar.ziua =zi_saptamana.id_zi\r\n"
                            + "order by orar.ziua, durata_lectie.id_durata_lectie;");
            while (dataElevRS.next()) {
                dataElevTB_TOE.add(new TableOrarElev(dataElevRS.getString(1), dataElevRS.getString(2),
                        dataElevRS.getString(3), dataElevRS.getString(4)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        zi_orar_elev.setCellValueFactory(new PropertyValueFactory<TableOrarElev, String>("zi"));
        disciplina_orar_elev.setCellValueFactory(new PropertyValueFactory<TableOrarElev, String>("disciplina"));
        profesor_orar_elev.setCellValueFactory(new PropertyValueFactory<TableOrarElev, String>("profesor"));
        durata_orar_elev.setCellValueFactory(new PropertyValueFactory<TableOrarElev, String>("durata"));

        tableOrarElev.setItems(dataElevTB_TOE);

        EventHandler<ActionEvent> elevNoteAbsente = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                vizualizeazaNoteAbsenteElev.setVisible(true);
                vizualizeazaOrarElev.setVisible(false);
            }
        };
        btnNoteAbsenteElev.setOnAction(elevNoteAbsente);

        EventHandler<ActionEvent> elevOrar = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                vizualizeazaNoteAbsenteElev.setVisible(false);
                vizualizeazaOrarElev.setVisible(true);
            }
        };
        btnOrarElev.setOnAction(elevOrar);

        EventHandler<ActionEvent> inapoiElevNA = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                vizualizeazaNoteAbsenteElev.setVisible(false);
            }
        };
        btnInapoiElevNA.setOnAction(inapoiElevNA);

        EventHandler<ActionEvent> inapoiElevO = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                vizualizeazaOrarElev.setVisible(false);
            }
        };
        btnInapoiElevO.setOnAction(inapoiElevO);

        EventHandler<ActionEvent> iesireElev = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                mainAP.toFront();
            }
        };
        btnIesireElev.setOnAction(iesireElev);

    }

    public void btnAProfesorClick() {
        adminAPprofesori.toFront();
        initializeTableProfesori();
    }

    public void btnIesireClick(ActionEvent e) {
        mainAP.toFront();
    }

    public void initializeTableProfesori() {
        Connection c = ConnectionBD.getConnection();
        ResultSet dataProfBasic;
        listTB_TP.clear();

        try {
            dataProfBasic = c.createStatement().executeQuery(
                    "SELECT profesori.id_profesor, profesori.nume_profesor, profesori.prenume_profesor, profesori.telefon, clase.nume_clasa, GROUP_CONCAT(discipline.nume_discipline) AS discipline \r\n"
                            + "FROM profesori\r\n"
                            + "JOIN disciplina_profesor ON profesori.id_profesor=disciplina_profesor.id_profesor\r\n"
                            + "LEFT JOIN discipline ON disciplina_profesor.id_disciplina = discipline.id_disciplina\r\n"
                            + "LEFT JOIN clase ON clase.id_diriginte = profesori.id_profesor\r\n"
                            + "GROUP BY profesori.id_profesor;");

            while (dataProfBasic.next()) {
                listTB_TP.add(new TableProfesori(dataProfBasic.getString(1), dataProfBasic.getString(2),
                        dataProfBasic.getString(3), dataProfBasic.getString(4), dataProfBasic.getString(5),
                        dataProfBasic.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        id_profesor.setCellValueFactory(new PropertyValueFactory<TableProfesori, String>("id"));
        nume_profesor.setCellValueFactory(new PropertyValueFactory<TableProfesori, String>("nume"));
        prenume_profesor.setCellValueFactory(new PropertyValueFactory<TableProfesori, String>("prenume"));
        telefon_profesor.setCellValueFactory(new PropertyValueFactory<TableProfesori, String>("telefon"));
        clasa_profesor.setCellValueFactory(new PropertyValueFactory<TableProfesori, String>("clasa"));
        discipline_profesor.setCellValueFactory(new PropertyValueFactory<TableProfesori, String>("discipline"));
        sterge_profesor.setCellValueFactory(new PropertyValueFactory<TableProfesori, String>("sterge"));

        tableProfesori.setItems(listTB_TP);

//		INSERARE PROFESOR
        insertNumeProfesor.setText("");
        insertPrenumeProfesor.setText("");
        insertTelefonProfesor.setText("");

        ResultSet dataDiscipline;

        try {
            dataDiscipline = c.createStatement().executeQuery("SELECT * FROM  discipline");
            int i = 1;
            dataDisciplineMap.clear(); // daca lipseste da eroare
            while (dataDiscipline.next()) {
                dataDisciplineMap.put(dataDiscipline.getInt(1), dataDiscipline.getString(2));
                checkComboBoxDiscipline.getItems().add(dataDisciplineMap.get(i));
                i++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String numeP = insertNumeProfesor.getText();
                String prenumeP = insertPrenumeProfesor.getText();
                String telefonP = insertTelefonProfesor.getText();
                boolean insertProfesorValid = true;
                ObservableList<String> disciplineP = checkComboBoxDiscipline.getCheckModel().getCheckedItems();
                List<Integer> disciplineID = new ArrayList<Integer>();

                for (Object obj : disciplineP) {
                    insertProfesorWrong.setText("");
                    for (Entry<Integer, String> entry : dataDisciplineMap.entrySet()) {
                        if (entry.getValue() == obj.toString()) {
                            disciplineID.add(entry.getKey());
                            break;
                        }
                    }
                }
                if (disciplineID.size() == 0) {
                    insertProfesorWrong.setText("Nu ati introdus obiectele predate !");
                    insertProfesorValid = false;
                }
                try {
                    Integer.parseInt(telefonP);
                } catch (NumberFormatException ex) {
                    insertProfesorWrong.setText("Nu ati introdus corect nr de telefon");
                    insertProfesorValid = false;
                }
                if (telefonP.length() != 9) {
                    insertProfesorWrong.setText("Nu ati introdus corect nr de telefon");
                    insertProfesorValid = false;
                }
                if (prenumeP.length() == 0) {
                    insertProfesorWrong.setText("Nu ati introdus corect prenumele");
                    insertProfesorValid = false;
                }
                if (numeP.length() == 0) {
                    insertProfesorWrong.setText("Nu ati introdus corect numele");
                    insertProfesorValid = false;
                }

                if (insertProfesorValid) {
                    String nume_de_utilizator = numeP.toLowerCase() + "." + prenumeP.toLowerCase();
                    String password = MD5(nume_de_utilizator);
                    try {
                        String query = "INSERT INTO profesori (nume_profesor, prenume_profesor, telefon, nume_de_utilizator, parola)"
                                + " VALUES (\"" + numeP + "\", \"" + prenumeP + "\", \"" + telefonP + "\", \""
                                + nume_de_utilizator + "\", \"" + password + "\")";
                        c.prepareStatement(query).execute();

                        ResultSet idProfesorInserted;
                        idProfesorInserted = c.createStatement()
                                .executeQuery("SELECT id_profesor FROM profesori WHERE nume_de_utilizator=\""
                                        + nume_de_utilizator + "\" AND parola=\"" + password + "\" LIMIT 1;");

                        int idProfInserted = 0;

                        while (idProfesorInserted.next()) {
                            idProfInserted = Integer.parseInt(idProfesorInserted.getString(1));
                        }

                        while (!disciplineID.isEmpty()) {
                            query = "INSERT INTO disciplina_profesor (id_profesor, id_disciplina)" + " VALUES ("
                                    + idProfInserted + ", " + disciplineID.get(0) + ");";
                            c.prepareStatement(query).execute();
                            disciplineID.remove(0);
                        }

                        checkComboBoxDiscipline.getItems().clear();
                        initializeTableProfesori();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        btnAdaugaProfesor.setOnAction(e1);

        // STERGERE PROFESOR
        EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                for (TableProfesori tp : listTB_TP) {
                    if (tp.getSterge().isSelected()) {
                        String id = tp.getId(), query;
                        try {
                            query = "delete from disciplina_profesor where id_profesor=" + id;
                            c.prepareStatement(query).execute();
                            query = "delete from profesori where id_profesor=" + id;
                            c.prepareStatement(query).execute();
                            initializeTableProfesori();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        };
        btnStergeProfesor.setOnAction(e2);

        // MODIFICA PROFESOR
        EventHandler<ActionEvent> e3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                modificaProfesor.toFront();
                modificaProfesor.setVisible(true);
                modificaNumeProfesor.setText("");
                modificaPrenumeProfesor.setText("");
                modificaTelefonProfesor.setText("");
                comboBoxModificaProfesor.getItems().clear();
                checkComboBoxModificaDisciplineProfesor.getItems().clear();
                checkComboBoxModificaDisciplineProfesor.getCheckModel().clearChecks();

                for (TableProfesori tp : listTB_TP) {
                    comboBoxModificaProfesor.getItems().add(tp.getNume() + " " + tp.getPrenume());
                }

                EventHandler<ActionEvent> e5 = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        modificaProfesor.setVisible(false);
                    }
                };
                btnInapoiModificaProfesor.setOnAction(e5);
            }
        };
        btnModificaProfesor.setOnAction(e3);
    }

    public void comboBoxModificaProfesorChecked(ActionEvent e) {
        String[] nume_prenume = comboBoxModificaProfesor.getSelectionModel().getSelectedItem().toString().split("\\s+");
        Connection c = ConnectionBD.getConnection();
        int idProfesor = 0;
        for (TableProfesori tp : listTB_TP) {
            if (tp.getNume().equals(nume_prenume[0]) && (tp.getPrenume().equals(nume_prenume[1]))) {
                idProfesor = Integer.parseInt(tp.getId());
                modificaNumeProfesor.setText(tp.getNume().toString());
                modificaPrenumeProfesor.setText(tp.getPrenume());
                modificaTelefonProfesor.setText(tp.getTelefon());
                for (Entry<Integer, String> entry : dataDisciplineMap.entrySet()) { // parcurg toate disicplinele si le
                    // inserez
                    checkComboBoxModificaDisciplineProfesor.getItems().add(entry.getValue());
                }
            }
        }
        final int idProf = idProfesor;
        EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ObservableList<String> discChecked = checkComboBoxModificaDisciplineProfesor.getCheckModel()
                        .getCheckedItems();
                boolean validModificaProfesor = true;
                modificaProfesorWrong.setText("");
                if (discChecked.size() == 0) {
                    modificaProfesorWrong.setText("Nu ati ales disciplinele predate");
                    validModificaProfesor = false;
                }

                try {
                    Integer.parseInt(modificaTelefonProfesor.getText());
                } catch (NumberFormatException ex) {
                    modificaProfesorWrong.setText("Nu ati introdus corect nr de telefon");
                    validModificaProfesor = false;
                }
                if (modificaTelefonProfesor.getText().length() != 9) {
                    modificaProfesorWrong.setText("Nu ati introdus corect nr de telefon");
                    validModificaProfesor = false;
                }
                if (modificaPrenumeProfesor.getText().length() == 0) {
                    modificaProfesorWrong.setText("Nu ati introdus corect prenumele");
                    validModificaProfesor = false;
                }
                if (modificaNumeProfesor.getText().length() == 0) {
                    modificaProfesorWrong.setText("Nu ati introdus corect numele");
                    validModificaProfesor = false;
                }

                if (validModificaProfesor) {

                    try {
                        String query = "UPDATE PROFESORI SET nume_profesor = \"" + modificaNumeProfesor.getText()
                                + "\", prenume_profesor = \"" + modificaPrenumeProfesor.getText() + "\", telefon = \""
                                + modificaTelefonProfesor.getText() + "\", nume_de_utilizator = \""
                                + modificaNumeProfesor.getText().toLowerCase() + "."
                                + modificaPrenumeProfesor.getText().toLowerCase() + "\", parola = \""
                                + MD5(modificaNumeProfesor.getText().toLowerCase() + "."
                                + modificaPrenumeProfesor.getText().toLowerCase())
                                + "\" WHERE id_profesor = " + idProf + ";";
                        c.prepareStatement(query).execute();

                        query = "DELETE FROM disciplina_profesor where id_profesor = " + idProf + ";";
                        c.prepareStatement(query).execute();

                        for (Object obj : discChecked) {
                            for (Entry<Integer, String> entry : dataDisciplineMap.entrySet()) {
                                if (obj.equals(entry.getValue())) {
                                    query = "INSERT INTO disciplina_profesor (id_profesor, id_disciplina) VALUES ("
                                            + idProf + ", " + entry.getKey() + ");";
                                    c.prepareStatement(query).execute();
                                }
                            }

                        }
                        for (Entry<Integer, String> entry : dataDisciplineMap.entrySet()) {
                            checkComboBoxModificaDisciplineProfesor.getItems().remove(entry.getValue());
                        }
                        modificaProfesor.setVisible(false);
                        initializeTableProfesori();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }

            }
        };
        btnAplicaModificariProfesor.setOnAction(e1);
    }

    public void btnBackAdminAPProfesoriClick(ActionEvent e) {
        adminAP.toFront();
    }

    public void btnAOrarClick(ActionEvent e) {
        adminAPorar.toFront();
        comboBoxAlegeClasa.getItems().clear();
        Connection c = ConnectionBD.getConnection();
        ResultSet claseRS;
        HashMap<Integer, String> claseMAP = new HashMap<Integer, String>();

        try {
            claseRS = c.createStatement().executeQuery("SELECT * FROM clase");
            while (claseRS.next()) {
                claseMAP.put(Integer.parseInt(claseRS.getString(1)), claseRS.getString(2));
                comboBoxAlegeClasa.getItems().add(claseRS.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (!(comboBoxAlegeClasa.getSelectionModel().getSelectedItem() == null)) {
                    for (Entry<Integer, String> entry : claseMAP.entrySet()) {
                        if (entry.getValue()
                                .equals(comboBoxAlegeClasa.getSelectionModel().getSelectedItem().toString())) {

                            orar(entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
        };
        btnAplicaClasa.setOnAction(e1);

        EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                adminAP.toFront();
            }
        };
        btnAplicaClasaInapoi.setOnAction(e2);
    }

    private void orar(int clasaID, String clasaNume) {
        modificaOrar.setVisible(true);
        clasaOrar.setText("Clasa " + clasaNume);

        Connection c = ConnectionBD.getConnection();
        ResultSet dataOrar;
        ObservableList<TableOrar> orarTB_TO = FXCollections.observableArrayList();
        orarTB_TO.clear();

        try {
            dataOrar = c.createStatement().executeQuery(
                    "SELECT id_lectie, zi_saptamana.nume_zi, durata_lectie.durata, discipline.nume_discipline, profesori.nume_profesor, profesori.prenume_profesor\r\n"
                            + "FROM orar\r\n" + "JOIN clase on clase.id_clasa=orar.id_clasa\r\n"
                            + "JOIN zi_saptamana on zi_saptamana.id_zi = orar.ziua\r\n"
                            + "JOIN durata_lectie on durata_lectie.id_durata_lectie = orar.id_durata\r\n"
                            + "JOIN disciplina_profesor on disciplina_profesor.id_disciplina_profesor = orar.id_disciplina_profesor\r\n"
                            + "JOIN discipline on discipline.id_disciplina = disciplina_profesor.id_disciplina\r\n"
                            + "JOIN profesori on profesori.id_profesor = disciplina_profesor.id_profesor\r\n"
                            + "AND orar.id_clasa = " + clasaID + "\r\n" + "ORDER BY orar.ziua, orar.id_durata;");

            while (dataOrar.next()) {
                orarTB_TO.add(new TableOrar(dataOrar.getString(1), dataOrar.getString(2), dataOrar.getString(3),
                        dataOrar.getString(4), dataOrar.getString(5), dataOrar.getString(6)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        zi_disciplina_orar.setCellValueFactory(new PropertyValueFactory<TableOrar, String>("zi"));
        durata_disciplina_orar.setCellValueFactory(new PropertyValueFactory<TableOrar, String>("durata"));
        disciplina_orar.setCellValueFactory(new PropertyValueFactory<TableOrar, String>("disciplina"));
        nume_prof_orar.setCellValueFactory(new PropertyValueFactory<TableOrar, String>("nume"));
        prenume_prof_orar.setCellValueFactory(new PropertyValueFactory<TableOrar, String>("prenume"));
        sterge_orar.setCellValueFactory(new PropertyValueFactory<TableOrar, String>("sterge"));

        tableOrar.setItems(orarTB_TO);

        // comboBoxZiua
        HashMap<Integer, String> ziSaptamanaMap = new HashMap<Integer, String>();
        comboBoxZiua.getItems().clear();
        try {
            dataOrar = c.createStatement().executeQuery("SELECT * FROM zi_saptamana");
            while (dataOrar.next()) {
                comboBoxZiua.getItems().add(dataOrar.getString(2));
                ziSaptamanaMap.put(dataOrar.getInt(1), dataOrar.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // comboBoxDurata
        HashMap<Integer, String> durataMap = new HashMap<Integer, String>();
        comboBoxDurata.getItems().clear();
        try {
            dataOrar = c.createStatement().executeQuery("SELECT * FROM durata_lectie");
            while (dataOrar.next()) {
                comboBoxDurata.getItems().add(dataOrar.getString(2));
                durataMap.put(dataOrar.getInt(1), dataOrar.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // comboBoxDisciplina
        HashMap<Integer, String> disciplineMap = new HashMap<Integer, String>();
        comboBoxDisciplina.getItems().clear();
        try {
            dataOrar = c.createStatement().executeQuery(
                    "SELECT dp.id_disciplina_profesor, CONCAT(d.nume_discipline,' - ', p.nume_profesor, ' ', p.prenume_profesor)\r\n"
                            + "	from discipline d\r\n"
                            + "    JOIN disciplina_profesor dp on dp.id_disciplina = d.id_disciplina\r\n"
                            + "    JOIN profesori p on p.id_profesor = dp.id_profesor\r\n"
                            + "    GROUP BY dp.id_disciplina_profesor ORDER BY d.nume_discipline;");
            while (dataOrar.next()) {
                comboBoxDisciplina.getItems().add(dataOrar.getString(2));
                disciplineMap.put(dataOrar.getInt(1), dataOrar.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // APLICA MODIFICARI ORAR
        EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (comboBoxZiua.getSelectionModel().getSelectedItem() != null
                        && comboBoxDurata.getSelectionModel().getSelectedItem() != null
                        && comboBoxDisciplina.getSelectionModel().getSelectedItem() != null) {
                    boolean lectie = false;
                    for (TableOrar to : orarTB_TO) {
                        if (to.getZi().equals(comboBoxZiua.getSelectionModel().getSelectedItem())
                                && to.getDurata().equals(comboBoxDurata.getSelectionModel().getSelectedItem())) {
                            lectie = true;
                            int id_noua_disciplina = 0;

                            for (Entry<Integer, String> entry : disciplineMap.entrySet()) {
                                if (entry.getValue().equals(comboBoxDisciplina.getSelectionModel().getSelectedItem())) {
                                    id_noua_disciplina = entry.getKey();
                                    break;
                                }
                            }

                            try {
                                String query = "UPDATE orar SET id_disciplina_profesor = " + id_noua_disciplina
                                        + " WHERE id_lectie = " + to.getId();
                                c.prepareStatement(query).execute();
                                orar(clasaID, clasaNume);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    if (!lectie) {
                        int idZi = 0, idDurata = 0, idDisciplina = 0;
                        for (Entry<Integer, String> entry : ziSaptamanaMap.entrySet()) {
                            if (entry.getValue().equals(comboBoxZiua.getSelectionModel().getSelectedItem())) {
                                idZi = entry.getKey();
                                break;
                            }
                        }
                        for (Entry<Integer, String> entry : durataMap.entrySet()) {
                            if (entry.getValue().equals(comboBoxDurata.getSelectionModel().getSelectedItem())) {
                                idDurata = entry.getKey();
                                break;
                            }
                        }
                        for (Entry<Integer, String> entry : disciplineMap.entrySet()) {
                            if (entry.getValue().equals(comboBoxDisciplina.getSelectionModel().getSelectedItem())) {
                                idDisciplina = entry.getKey();
                                break;
                            }
                        }

                        try {
                            String query = "INSERT INTO orar (id_clasa, ziua, id_disciplina_profesor, id_durata) VALUES "
                                    + "(" + clasaID + ", " + idZi + ", " + idDisciplina + ", " + idDurata + ")";
                            c.prepareStatement(query).execute();
                            orar(clasaID, clasaNume);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        };
        btnAplicaOrar.setOnAction(e1);

        // STERGERE ORAR
        EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                for (TableOrar to : orarTB_TO) {
                    if (to.getSterge().isSelected()) {
                        String id = to.getId(), query;
                        try {
                            query = "delete from orar where id_lectie=" + id;
                            c.prepareStatement(query).execute();
                            orar(clasaID, clasaNume);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        };
        btnStergeOrar.setOnAction(e2);
    }

    public void btnBackAdminAPorarClick(ActionEvent e) {
        modificaOrar.setVisible(false);
    }

    // POPULAREA TABELULUI DURATA LECTIE
    public void btnDurataLectieClick(ActionEvent e) {
        durataLectie();
    }

    public void durataLectie() {
        modificaDurataLectieWrong.setText("");
        durataLectieWrong.setText("");
        durataInput.setText("");
        modificaDurataLectie.setVisible(true);

        Connection c = ConnectionBD.getConnection();
        ResultSet dataDurataLectie;
        ObservableList<TableDurataLectie> durataLectieTB_TDL = FXCollections.observableArrayList();
        durataLectieTB_TDL.clear();

        try {
            dataDurataLectie = c.createStatement().executeQuery("SELECT * FROM durata_lectie");

            while (dataDurataLectie.next()) {
                durataLectieTB_TDL
                        .add(new TableDurataLectie(dataDurataLectie.getString(1), dataDurataLectie.getString(2)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        durata_lectie.setCellValueFactory(new PropertyValueFactory<TableDurataLectie, String>("durata"));
        sterge_durata_lectie.setCellValueFactory(new PropertyValueFactory<TableDurataLectie, String>("sterge"));

        tableDurataLectie.setItems(durataLectieTB_TDL);

        tableDurataLectie.setEditable(true);
        durata_lectie.setCellFactory(TextFieldTableCell.forTableColumn());
        durata_lectie.setOnEditCommit(new EventHandler<CellEditEvent<TableDurataLectie, String>>() {
            @Override
            public void handle(CellEditEvent<TableDurataLectie, String> event) {
                TableDurataLectie durata = event.getRowValue();
                durata.setDurata(event.getNewValue());
                System.out.println(durata.getId());

                if (Pattern.matches("[0-9]{1,2}\\:[0-9]{1,2}\\-[0-9]{1,2}\\:[0-9]{1,2}", event.getNewValue())) {
                    try {
                        String query = "UPDATE durata_lectie SET durata = \"" + event.getNewValue()
                                + "\" WHERE id_durata_lectie = " + durata.getId();
                        c.prepareStatement(query).execute();
                        durataLectie();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    durataLectieWrong.setText("Revizuiti datele introduse");
                }
            }
        });

        // INTRODU DURATA NOUA
        EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (Pattern.matches("[0-9]{1,2}\\:[0-9]{1,2}\\-[0-9]{1,2}\\:[0-9]{1,2}", durataInput.getText())) {
                    try {
                        c.prepareStatement(
                                        "INSERT INTO durata_lectie (durata) VALUES (\"" + durataInput.getText() + "\");")
                                .execute();
                        durataLectie();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    durataLectieWrong.setText("Revizuiti datele introduse");
                }
            }
        };
        btnAplicaDurata.setOnAction(e1);

        // STERGE DURATA
        EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                for (TableDurataLectie tdl : durataLectieTB_TDL) {
                    if (tdl.getSterge().isSelected()) {
                        String id = tdl.getId(), query;
                        try {
                            query = "delete from orar where id_durata = " + id;
                            c.prepareStatement(query).execute();
                            query = "delete from durata_lectie where id_durata_lectie = " + id;
                            c.prepareStatement(query).execute();
                            durataLectie();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        };
        btnStergeDurataLectie.setOnAction(e2);

        // INAPOI
        EventHandler<ActionEvent> e3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                modificaDurataLectie.setVisible(false);
            }
        };
        btnInapoiModificaDurataLectie.setOnAction(e3);
    }

    public void btnClaseClick(ActionEvent e) {
        clase();
    }

    public void clase() {
        comboBoxClase.getItems().clear();
        comboBoxSchimbaDiriginte.getItems().clear();
        comboBoxAlegeDiriginte.getItems().clear();
        numeDiriginte.setText("");
        clasaInput.setText("");
        stergeClasaWrong.setText("");
        modificaClasaWrong.setText("");
        adaugaClasaWrong.setText("");
        LinkedHashMap<Integer, String> claseMAP = new LinkedHashMap<Integer, String>();
        LinkedHashMap<Integer, String> profesoriMAP = new LinkedHashMap<Integer, String>();

        modificaClase.setVisible(true);

        Connection c = ConnectionBD.getConnection();

        ResultSet claseRS;

        // POPULAREA comboBoxClase
        try {
            claseRS = c.createStatement()
                    .executeQuery("SELECT clase.id_clasa, clase.nume_clasa, profesori.id_profesor, "
                            + "CONCAT(profesori.nume_profesor, ' ', profesori.prenume_profesor) AS PROFESOR FROM profesori "
                            + "LEFT JOIN clase ON clase.id_diriginte = profesori.id_profesor ORDER by clase.id_clasa DESC;");
            while (claseRS.next()) {
                if (claseRS.getString(2) != null) {
                    comboBoxClase.getItems().add(claseRS.getString(2));
                    claseMAP.put(claseRS.getInt(1), claseRS.getString(2));
                }
                if (claseRS.getString(2) == null) {
                    comboBoxSchimbaDiriginte.getItems().add(claseRS.getString(4));
                    comboBoxAlegeDiriginte.getItems().add(claseRS.getString(4));
                }
                profesoriMAP.put(claseRS.getInt(3), claseRS.getString(4));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        EventHandler<ActionEvent> e4 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ResultSet diriginteClasa;
                try {
                    diriginteClasa = c.createStatement().executeQuery(
                            "SELECT CONCAT(profesori.nume_profesor, ' ', profesori.prenume_profesor) FROM profesori "
                                    + "JOIN clase on clase.id_diriginte = profesori.id_profesor AND clase.nume_clasa = \""
                                    + comboBoxClase.getSelectionModel().getSelectedItem() + "\" LIMIT 1");
                    while (diriginteClasa.next()) {
                        numeDiriginte.setText(diriginteClasa.getString(1));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        };
        comboBoxClase.setOnAction(e4);

        // STERGE CLASA
        EventHandler<ActionEvent> e1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (comboBoxClase.getSelectionModel().getSelectedItem() != null) {
                    for (Entry<Integer, String> entry : claseMAP.entrySet()) {
                        if (entry.getValue().equals(comboBoxClase.getSelectionModel().getSelectedItem())) {
                            try {
                                c.prepareStatement("DELETE FROM elev where id_clasa = " + entry.getKey()).execute();
                                c.prepareStatement("DELETE FROM orar where id_clasa = " + entry.getKey()).execute();
                                c.prepareStatement("DELETE FROM clase where id_clasa = " + entry.getKey()).execute();
                                clase();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } else {
                    stergeClasaWrong.setText("Alegeti clasa");
                }
            }
        };
        btnStergeClasa.setOnAction(e1);

        // MODIFICA CLASA
        EventHandler<ActionEvent> e2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (comboBoxClase.getSelectionModel().getSelectedItem() != null
                        && comboBoxSchimbaDiriginte.getSelectionModel().getSelectedItem() != null) {

                    for (Entry<Integer, String> entry : profesoriMAP.entrySet()) {
                        if (comboBoxSchimbaDiriginte.getSelectionModel().getSelectedItem().toString()
                                .equals(entry.getValue())) {
                            try {
                                c.prepareStatement(
                                                "UPDATE clase set id_diriginte = " + entry.getKey() + " WHERE nume_clasa = \""
                                                        + comboBoxClase.getSelectionModel().getSelectedItem() + "\"")
                                        .execute();
                                break;
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    clase();
                } else {
                    modificaClasaWrong.setText("Alegeti clasa si noul diriginte");
                }
            }
        };
        btnModificaClasa.setOnAction(e2);

        // CLASA NOUA
        EventHandler<ActionEvent> e3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (clasaInput.getText() != null
                        && comboBoxAlegeDiriginte.getSelectionModel().getSelectedItem() != null) {
                    boolean clasaExistenta = false;
                    for (Entry<Integer, String> entry : claseMAP.entrySet()) {
                        if (clasaInput.getText().equals(entry.getValue())) {
                            adaugaClasaWrong.setText("Nu pot fi 2 clase cu acelasi nume");
                            clasaExistenta = true;
                        }
                    }
                    if (!clasaExistenta) {
                        int profesorKey = 0;
                        for (Entry<Integer, String> entry : profesoriMAP.entrySet()) {
                            if (comboBoxAlegeDiriginte.getSelectionModel().getSelectedItem().equals(entry.getValue())) {
                                profesorKey = entry.getKey();
                                break;
                            }
                        }
                        System.out.println(profesorKey);
                        try {
                            c.prepareStatement("INSERT INTO clase (nume_clasa, id_diriginte) VALUES (\""
                                    + clasaInput.getText() + "\", " + profesorKey + ");").execute();
                            clase();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    adaugaClasaWrong.setText("Completati campurile");
                }
            }
        };
        btnAdaugaClasa.setOnAction(e3);

        // INAPOI
        EventHandler<ActionEvent> e5 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                modificaClase.setVisible(false);
                initializeTableProfesori();
            }
        };
        btnInapoiClase.setOnAction(e5);
    }
}
