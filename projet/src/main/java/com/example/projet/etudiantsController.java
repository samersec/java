package com.example.projet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class etudiantsController implements Initializable {

    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showEtudiants();
    }
    @FXML
    private Button btnajouter;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnsupprimer;

    @FXML
    private CheckBox JV;

    @FXML
    private CheckBox BD;

    @FXML
    private CheckBox PW;

    @FXML
    private CheckBox CP;

    @FXML
    private TableColumn<etudiants, Integer> colid;

    @FXML
    private TableColumn<etudiants, String> coln;

    @FXML
    private TableColumn<etudiants, String> colp;

    @FXML
    private TableView<etudiants> table;

    @FXML
    private DatePicker tdate;

    @FXML
    private TextField tmail;

    @FXML
    private TextField tnom;

    @FXML
    private TextField tprenom;

    public ObservableList<etudiants> getEtudiants() {
        ObservableList<etudiants> etudiants = FXCollections.observableArrayList();
        String query = "select * from etudiants";
        con = BDconnect.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                etudiants et = new etudiants();
                et.setId(rs.getInt("id"));
                et.setPrenom(rs.getString("prenom"));
                et.setNom(rs.getString("nom"));
                etudiants.add(et);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return etudiants;
    }

    public void showEtudiants() {
        ObservableList<etudiants> list = getEtudiants();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colp.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        coln.setCellValueFactory(new PropertyValueFactory<>("Nom"));
    }

    @FXML
    void createEtudiant(ActionEvent event) {

    }

    @FXML
    void modifierEtudiant(ActionEvent event) {

    }

    @FXML
    void supprimerEtudiant(ActionEvent event) {

    }


}
