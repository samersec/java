package com.example.projet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class modifierController {

    @FXML
    public Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    private TextField tid;

    @FXML
    private ComboBox<?> tcours;

    @FXML
    private DatePicker tdate;

    @FXML
    private TextField tmail;

    @FXML
    private TextField tnom;

    @FXML
    private TextField tprenom;
    @FXML
    private TextField tnote;



    @FXML
    private Button Modifier;


    @FXML
    void Modifier(ActionEvent event) throws SQLException  {
        if (tid.getText().isEmpty() || tnom.getText().isEmpty() || tprenom.getText().isEmpty() || tmail.getText().isEmpty() || tnote.getText().isEmpty() || tcours.getValue() == null || tdate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Required fields are empty");
            alert.setContentText("Please fill all the required fields.");
            alert.showAndWait();
        } else {
            etudiants etud = new etudiants();
            etud.setId(Integer.parseInt(tid.getText()));
            etud.setNote(Integer.parseInt(tnote.getText()));
            etud.setNom(tnom.getText());
            etud.setPrenom(tprenom.getText());
            etud.setMail(tmail.getText());
            etud.setCours(tcours.getValue().toString());
            etud.setDaten(tdate.getValue().toString());

            String sql = "UPDATE etudiants SET nom=?, prenom=?, daten=?, mail=?, cours=? WHERE id=?";
            Connection con = BDconnect.getCon();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, etud.getNom());
            stmt.setString(2, etud.getPrenom());
            stmt.setString(3, etud.getDaten());
            stmt.setString(4, etud.getMail());
            stmt.setString(5, etud.getCours());
            stmt.setInt(6, etud.getId());
            stmt.executeUpdate();

            PreparedStatement stmt2 = con.prepareStatement("UPDATE inscription SET id_cours=?, note=? WHERE id_etudiants=?");
            stmt2.setInt(1, Integer.parseInt(etud.getCours()));
            stmt2.setInt(2, etud.getNote());
            stmt2.setInt(3, etud.getId());
            stmt2.executeUpdate();

        }
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    void Annuler(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();


    }
}