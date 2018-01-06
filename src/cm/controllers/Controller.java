package cm.controllers;


import cm.daemons.AniversaryChecker;
import cm.interfaces.impls.PersonneMenagerEngine;
import cm.objects.Personne;
import cm.start.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Controller {
    PersonneMenagerEngine personneMenagerEngine;
    @FXML
    Label prLabel;

    @FXML
    GridPane gridPaneTab;
    @FXML
    TableView tabViewPersonneList;

    @FXML
    TableColumn<Personne, String> tabColNom;
    @FXML
    TableColumn<Personne, String> tabColPrenom;
    @FXML
    TableColumn<Personne, String> tabColNiveau;
    @FXML
    TableColumn<Personne, Integer> tabColAge;

    @FXML
    TextField txtFieldDernieresInfos, txtFieldNom, txtFieldPrenom, txtFieldAge, txtFieldtel1, txtFieldtel2, txtFieldEmail, txtFieldAdresse, txtFieldemploi, txtFieldNiveau, txtFieldInteresses;

    @FXML
    DatePicker dnPicker;

    @FXML
    Button btnAdd, btnEdit, btnRmv, btnew, btnBack;

    boolean creatingNewPersonne = false;

    public void buttonController(ActionEvent event) {
        if (gridPaneTab.isDisabled()) {
            gridPaneTab.setDisable(false);
        }
        Object source = event.getSource();
        if (!(source instanceof Button))
            return;

        Button button = (Button) source;
        ObservableList<Personne> choisedPersonnes = tabViewPersonneList.getSelectionModel().getSelectedItems();
        switch (button.getId()) {
            case "btnAdd":
                if (!creatingNewPersonne) {
                    createNewPersonne();
                } else {
                    stopCreateNewPersonne();
                    loadNewPersonneInfo();

                }

                break;

            case "btnEdit":

                if (choisedPersonnes.size() != 0)
                    updatePersonne(choisedPersonnes.get(0));
                else
                    tabViewPersonneList.getSelectionModel().select(0);

                break;

            case "btnRmv":
                if (!creatingNewPersonne) {
                    personneMenagerEngine.deletePersonnes(choisedPersonnes);
                } else {
                    stopCreateNewPersonne();
                }
                break;

            case "btnBack":
                Main.globalStage.setScene(Main.mainScene);
                Main.globalStage.show();
                break;

        }


    }

    private void stopCreateNewPersonne() {

        creatingNewPersonne = false;
        tabViewPersonneList.setDisable(false);
        btnEdit.setDisable(false);
        btnAdd.setText("Rajouter");
        btnRmv.setText("Supprimer");
    }

    private void clearFields() {
        txtFieldNom.clear();
        txtFieldPrenom.clear();
        dnPicker.setValue(null);
        txtFieldAge.clear();
        txtFieldtel1.clear();
        txtFieldtel2.clear();
        txtFieldEmail.clear();
        txtFieldAdresse.clear();
        txtFieldemploi.clear();
        txtFieldInteresses.clear();
        txtFieldNiveau.clear();
        txtFieldDernieresInfos.clear();
    }

    public void createNewPersonne() {
        clearFields();
        txtFieldNom.requestFocus();
        btnAdd.setText("Sauvegarder");
        btnRmv.setText("Annuler");
        creatingNewPersonne = true;
        tabViewPersonneList.setDisable(true);
        btnEdit.setDisable(true);
    }

    public void loadNewPersonneInfo() {
        String nom = txtFieldNom.getText();
        String prnom = txtFieldPrenom.getText();
        if (nom.length() == 0 || prnom.length() == 0) {
            return;
        }
        String date = dnPicker.toString();
        Date dateNaissance = null;
        String tel1, tel2;
        tel1 = txtFieldtel1.getText();
        tel2 = txtFieldtel2.getText();
        try {
            dateNaissance = java.sql.Date.valueOf(dnPicker.getValue());
        } catch (NumberFormatException e) {
            date = null;
        }
        String email = txtFieldEmail.getText();
        String adresse = txtFieldAdresse.getText();
        String emploi = txtFieldemploi.getText();
        String interesses = txtFieldInteresses.getText();
        String niveau = txtFieldNiveau.getText();
        String dirnierInfos = txtFieldDernieresInfos.getText();
        Personne newpers = new Personne(0, nom, prnom, adresse, email, emploi, interesses, dateNaissance, tel1, tel2, niveau, dirnierInfos);

        if (!(nom.isEmpty() || prnom.isEmpty())) {
            personneMenagerEngine.addPersonne(newpers);
            personneMenagerEngine.loadPersonList();

        } else {
            {
                if (nom.isEmpty()) {
                    txtFieldNom.requestFocus();
                }
                if (prnom.isEmpty()) {
                    txtFieldPrenom.requestFocus();
                }
            }
        }
    }

    public void updatePersonne(Personne pers) {
        if (txtFieldNom.getText().length() == 0 || txtFieldPrenom.getText().length() == 0) {
            return;
        }

        pers.setNom(txtFieldNom.getText());
        pers.setPrenom(txtFieldPrenom.getText());
        try {
            pers.setAge(Integer.parseInt(txtFieldAge.getText()));
        } catch (NumberFormatException numEx) {
            pers.setAge(0);
        }
        pers.setDateNaissance(java.sql.Date.valueOf(dnPicker.getValue()));
        pers.setTelephone1(txtFieldtel1.getText());
        pers.setTelephone2(txtFieldtel1.getText());
        pers.setEmail(txtFieldEmail.getText());
        pers.setEmploi(txtFieldemploi.getText());
        pers.setInteresses(txtFieldInteresses.getText());
        pers.setNiveau(txtFieldNiveau.getText());
        pers.setDernierInfos(txtFieldDernieresInfos.getText());
        tabViewPersonneList.refresh();
        personneMenagerEngine.updatePersonne(pers);

    }

    public void showDialog(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../fxmls/confirmActionWindow.fxml"));
            stage.setResizable(false);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setMinWidth(200);
            stage.setMaxHeight(100);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        tabColNom.setCellValueFactory(new PropertyValueFactory<Personne, String>("Nom"));
        tabColPrenom.setCellValueFactory(new PropertyValueFactory<Personne, String>("Prenom"));
        tabColAge.setCellValueFactory(new PropertyValueFactory<Personne, Integer>("Age"));
        tabColNiveau.setCellValueFactory(new PropertyValueFactory<Personne, String>("Niveau"));
        personneMenagerEngine = Main.personneMenagerEngine;
        personneMenagerEngine.loadPersonList();
        tabViewPersonneList.setItems(personneMenagerEngine.getPersonnes());
        tabViewPersonneList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tabViewPersonneList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (gridPaneTab.isDisabled()) {
                    gridPaneTab.setDisable(false);
                }
                try {
                    clearFields();
                    Personne pers = (Personne) tabViewPersonneList.getSelectionModel().getSelectedItems().get(0);
                    txtFieldNom.setText(pers.getNom());
                    txtFieldPrenom.setText(pers.getPrenom());
                    dnPicker.setValue(LocalDate.parse(pers.getDateNaissance().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    txtFieldAge.setText(pers.getAge() + "");
                    txtFieldtel1.setText(pers.getTelephone1() + "");
                    txtFieldtel2.setText(pers.getTelephone2() + "");
                    txtFieldEmail.setText(pers.getEmail());
                    txtFieldAdresse.setText(pers.getAdresse());
                    txtFieldemploi.setText(pers.getEmploi());
                    txtFieldInteresses.setText(pers.getInteresses());
                    txtFieldNiveau.setText(pers.getNiveau());
                    txtFieldDernieresInfos.setText(pers.getDernierInfos());
                } catch (NullPointerException e) {

                }

            }
        });
        gridPaneTab.setDisable(true);
        personneMenagerEngine.getPersonnes().addListener(new ListChangeListener<Personne>() {
            @Override
            public void onChanged(Change<? extends Personne> c) {
                personneMenagerEngine.getPersonnes();
            }
        });
    }


}
