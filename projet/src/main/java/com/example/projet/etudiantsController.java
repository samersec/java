package com.example.projet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;

public class etudiantsController implements Initializable {

    @FXML
    public Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showEtudiants();
    }
    //*****************************************************************//
    @FXML
    private Button btnajouter;
    public void Ajouter(ActionEvent event) throws SQLException {
        if (tnom.getText().isEmpty() || tprenom.getText().isEmpty() || tmail.getText().isEmpty() || tcours.getValue() == null || tdate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("information requise");
            alert.setContentText("remplissez toute les informations pour ajouter un Etudiants.");
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
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, etud.getNom()); // set the values from the etud object
            stmt.setString(2, etud.getPrenom());
            stmt.setString(3, etud.getDaten());
            stmt.setString(4, etud.getMail());
            stmt.setString(5, etud.getCours());
            stmt.executeUpdate();

// retrieve the generated id value
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                etud.setId(generatedKeys.getInt(1));
            }

            PreparedStatement stmt2 = con.prepareStatement("INSERT INTO inscription (id_etudiants, id_cours) VALUES (?, ?)");
            stmt2.setInt(1, etud.getId());
            stmt2.setInt(2, Integer.parseInt(etud.getCours()));
            stmt2.executeUpdate();
        }
        showEtudiants();
    }
    @FXML
    public void AjouterCours(ActionEvent event) throws SQLException
    {
        if (tid.getText().isEmpty() ||  tcours2.getValue() == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreurr");
            alert.setHeaderText("information requise");
            alert.setContentText("il faut donner l'id de l'Etudiant et le cours a ajouter.");
            alert.showAndWait();
        }else {
            String id = tid.getText();
            String cours = tcours2.getValue().toString();
            etudiants etud = new etudiants();
            etud.setId(Integer.parseInt(id));
            etud.setCours(cours);
            Connection con = BDconnect.getCon();
            PreparedStatement stmt3 = con.prepareStatement("INSERT INTO inscription (id_etudiants, id_cours) VALUES (?, ?)");
            stmt3.setInt(1, etud.getId());
            stmt3.setInt(2, Integer.parseInt(etud.getCours()));
            stmt3.executeUpdate();

        }
        showEtudiants();

    }

    //*****************************************************************//
    @FXML
    private Button btnmodifier;

    @FXML
    void ModifierWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Modifier.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();


    }
    //*****************************************************************//
    @FXML
    private Button btnsupprimer;

    public void Supprimer(ActionEvent event) throws SQLException {
        etudiants selectedData = table.getSelectionModel().getSelectedItem();
        String deleteEtudiantSql = "DELETE FROM etudiants WHERE id = ?";
        String deleteInscriptionSql = "DELETE FROM inscription WHERE id_etudiants = ?";

        Connection con = BDconnect.getCon();

        try {
            con.setAutoCommit(false);

            // delete from etudiants table
            PreparedStatement deleteEtudiantStmt = con.prepareStatement(deleteEtudiantSql);
            deleteEtudiantStmt.setInt(1, selectedData.getId());
            int etudiantRows = deleteEtudiantStmt.executeUpdate();

            // delete from inscription table
            PreparedStatement deleteInscriptionStmt = con.prepareStatement(deleteInscriptionSql);
            deleteInscriptionStmt.setInt(1, selectedData.getId());
            int inscriptionRows = deleteInscriptionStmt.executeUpdate();

            if (etudiantRows > 0 && inscriptionRows > 0) {
                con.commit(); // commit the transaction if both deletions were successful
                table.getItems().remove(selectedData);
                table.refresh();
            } else {
                con.rollback(); // rollback the transaction if any deletion failed
            }
        } catch (SQLException ex) {
            con.rollback(); // rollback the transaction if an exception was thrown
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }


//*****************************************************************//


    @FXML
    void statwindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Stat.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();


    }

    @FXML
    private Button btnstat;


    @FXML
    private TextField tid;

    @FXML
    private ComboBox<?> tcours2;

    @FXML
    private ComboBox<?> tcours;
    @FXML
    private DatePicker recherchedate;

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
    private TableColumn<etudiants, Integer> colnote;

    @FXML
    private TextField recherche;

    @FXML
    private Button search;


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




    @FXML
    void recherche(ActionEvent event) {
        String searchText = recherche.getText().trim();

        if (searchText.isEmpty()) {
            table.setItems(getEtudiants());
            return;
        }

        ObservableList<etudiants> filteredList = FXCollections.observableArrayList();
        for (etudiants et : getEtudiants()) {
            if (
                    String.valueOf(et.getId()).contains(searchText)
                            || et.getPrenom().toLowerCase().contains(searchText.toLowerCase())
                             || et.getNom().toLowerCase().contains(searchText.toLowerCase())

                              || et.getMail().toLowerCase().contains(searchText.toLowerCase())
                              || et.getCours().toLowerCase().contains(searchText.toLowerCase())
                               || String.valueOf(et.getNote()).contains(searchText)) {
                filteredList.add(et);
            }
        }

        table.setItems(filteredList);
    }
    @FXML
    void recherchedate(ActionEvent event) {
        String searchText = String.valueOf(recherchedate.getValue());

        if (searchText.isEmpty()) {
            table.setItems(getEtudiants());
            return;
        }

        ObservableList<etudiants> filteredList = FXCollections.observableArrayList();
        for (etudiants et : getEtudiants()) {
            if (

                             et.getDaten().toLowerCase().contains(searchText.toLowerCase())

                            ) {
                filteredList.add(et);
            }
        }

        table.setItems(filteredList);
    }
    public ObservableList<etudiants> getEtudiants() {
        ObservableList<etudiants> etudiants = FXCollections.observableArrayList();
        String query = " SELECT etudiants.id, nom, prenom, daten, mail, titre, note\n" +
                "FROM etudiants \n" +
                "LEFT JOIN inscription ON etudiants.id = inscription.id_etudiants\n" +
                "LEFT JOIN cours ON inscription.id_cours = cours.id;";
         /*select etudiants.id,nom,prenom,daten,mail,titre,note from etudiants , inscription,cours where etudiants.id=inscription.id_etudiants and inscription.id_cours=cours.id;*/

        BDconnect.getCon();
        try {

            ResultSet rs = BDconnect.executeQuery(query);
            while (rs.next()) {
                etudiants et = new etudiants();

                et.setId(rs.getInt("id"));
                et.setPrenom(rs.getString("prenom"));
                et.setNom(rs.getString("nom"));
                et.setDaten(rs.getString("Daten"));
                et.setMail(rs.getString("mail"));
                et.setCours(rs.getString("titre"));
                et.setNote(rs.getInt("note"));


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
        colnote.setCellValueFactory(new PropertyValueFactory<>("note"));

    }




}
