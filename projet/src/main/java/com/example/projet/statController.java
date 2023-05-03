package com.example.projet;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class statController implements Initializable {

    @FXML
    private TableColumn<?, ?> colb;

    @FXML
    private TableColumn<?, ?> colc;

    @FXML
    private TableColumn<?, ?> colj;

    @FXML
    private TableColumn<?, ?> colw;

    @FXML
    private TableView<etudiants> tableJava;
    @FXML
    private TableView<etudiants> tableWeb;
    @FXML
    private TableView<etudiants> tableBD;
    @FXML
    private TableView<etudiants> tableConception;



    public void getetudiants() {
        ObservableList<etudiants> etudiantsJava = FXCollections.observableArrayList();
        ObservableList<etudiants> etudiantsDB = FXCollections.observableArrayList();
        ObservableList<etudiants> etudiantsWeb = FXCollections.observableArrayList();
        ObservableList<etudiants> etudiantsConception = FXCollections.observableArrayList();

        String queryJava = "SELECT  prenom   " +
                "FROM etudiants " +
                "LEFT JOIN inscription ON etudiants.id = inscription.id_etudiants " +
                "LEFT JOIN cours ON inscription.id_cours = cours.id WHERE cours.titre='java';";

        String queryDB = "SELECT etudiants.id, nom, prenom, daten, mail, titre, note " +
                "FROM etudiants " +
                "LEFT JOIN inscription ON etudiants.id = inscription.id_etudiants " +
                "LEFT JOIN cours ON inscription.id_cours = cours.id WHERE cours.titre='base de donnees';";

        String queryWeb = "SELECT etudiants.id, nom, prenom, daten, mail, titre, note " +
                "FROM etudiants " +
                "LEFT JOIN inscription ON etudiants.id = inscription.id_etudiants " +
                "LEFT JOIN cours ON inscription.id_cours = cours.id WHERE cours.titre='programmation web';";

        String queryConception = "SELECT  prenom  " +
                "FROM etudiants " +
                "LEFT JOIN inscription ON etudiants.id = inscription.id_etudiants " +
                "LEFT JOIN cours ON inscription.id_cours = cours.id WHERE cours.titre='conception';";

        BDconnect.getCon();

        try {
            ResultSet rsJava = BDconnect.executeQuery(queryJava);
            while (rsJava.next()) {
                etudiants et = new etudiants();
                et.setPrenom(rsJava.getString("prenom"));
                etudiantsJava.add(et);
            }

            ResultSet rsDB = BDconnect.executeQuery(queryDB);
            while (rsDB.next()) {
                etudiants et = new etudiants();
                et.setPrenom(rsDB.getString("prenom"));
                etudiantsDB.add(et);
            }

            ResultSet rsWeb = BDconnect.executeQuery(queryWeb);
            while (rsWeb.next()) {
                etudiants et = new etudiants();
                et.setPrenom(rsWeb.getString("prenom"));
                etudiantsWeb.add(et);
            }

            ResultSet rsConception = BDconnect.executeQuery(queryConception);
            while (rsConception.next()) {
                etudiants et = new etudiants();
                et.setPrenom(rsConception.getString("prenom"));
                etudiantsConception.add(et);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                BDconnect.close();
            } catch (SQLException e) {
                // handle exception
            }
        }

        // Set each ObservableList to its corresponding column
        colj.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tableJava.setItems(etudiantsJava);

        colb.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        tableBD.setItems(etudiantsDB);

        colw.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        tableWeb.setItems(etudiantsWeb);

        colc.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        tableConception.setItems(etudiantsConception);
    }

    public  void Nombreetudiants()
    {
        int nbrjava = tableJava.getItems().size();
        colj.setText("Java ("+nbrjava+")");

        int nbrWeb = tableWeb.getItems().size();
        colw.setText("Progweb ("+nbrWeb+")");

        int nbrBD = tableBD.getItems().size();
        colb.setText("BD ("+nbrBD+")");

        int nbrConception = tableConception.getItems().size();
        colc.setText("Conception ("+nbrConception+")");
    }




    public void initialize(URL url, ResourceBundle resourceBundle) {

        getetudiants();
        Nombreetudiants();


    }

}
