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
import java.util.Date;
import java.util.ResourceBundle;

public class etudiantsController implements Initializable {




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showEtudiants();
    }
    @FXML
    private Button btnajouter;
    public void Ajouter(ActionEvent event) throws SQLException {
        if (tnom.getText().isEmpty() || tprenom.getText().isEmpty() || tmail.getText().isEmpty() || tcours.getValue() == null || tdate.getValue() == null) {
            // If any of the required fields are empty, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Required fields are empty");
            alert.setContentText("Please fill all the required fields.");
            alert.showAndWait();
        }
        else {
            String nom = tnom.getText();
            String prenom = tprenom.getText();
            String mail = tmail.getText();
            String cours = tcours.getValue().toString();
            String date = tdate.getValue().toString();
            etudiants etud = new etudiants();
            etud.setNom(nom);
            etud.setPrenom(prenom);
            etud.setMail(mail);
            etud.setCours(cours);
            etud.setDaten(date);

            String sql = "INSERT INTO etudiants (nom, prenom, daten, mail, cours) " +
                    "VALUES (?, ?, ?, ?, ?)";
            Connection con = BDconnect.getCon();
// Create a PreparedStatement object to execute the statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, etud.getNom()); // set the values from the etud object
            stmt.setString(2, etud.getPrenom());
            stmt.setString(3, etud.getDaten());
            stmt.setString(4, etud.getMail());
            stmt.setString(5, etud.getCours());

// Execute the statement
            int rows = stmt.executeUpdate();
        }
        showEtudiants();
    }
    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnsupprimer;

    public void Supprimer(ActionEvent event) throws SQLException {

        etudiants selectedData = table.getSelectionModel().getSelectedItem();

        // Delete the row from the data source
        table.getItems().remove(selectedData);

        // Refresh the table view
        table.refresh();
    }




    @FXML
    private ComboBox<?> tcours;

    @FXML
    private TableColumn<etudiants, Integer> colid;

    @FXML
    private TableColumn<etudiants, String> coln;

    @FXML
    private TableColumn<etudiants, String> colp;
    @FXML
    private TableColumn<etudiants, String> cold;

    @FXML
    private TableColumn<etudiants, String> cole;

    @FXML
    private TableColumn<etudiants, String> colc;

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
        Connection con = BDconnect.getCon();
        try {

            ResultSet rs = BDconnect.executeQuery(query);
            while (rs.next()) {
                etudiants et = new etudiants();
                et.setId(rs.getInt("id"));
                et.setPrenom(rs.getString("prenom"));
                et.setNom(rs.getString("nom"));
                et.setDaten(rs.getString("Daten"));
                et.setMail(rs.getString("mail"));
                et.setCours(rs.getString("cours"));

                etudiants.add(et);
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
        return etudiants;
    }

    public void showEtudiants() {
        ObservableList<etudiants> list = getEtudiants();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colp.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        coln.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        cold.setCellValueFactory(new PropertyValueFactory<>("Daten"));
        cole.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colc.setCellValueFactory(new PropertyValueFactory<>("cours"));
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
