package me.iliasse.tpjavafx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import me.iliasse.tpjavafx.entities.Product;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private TextField txtProductName;
    @FXML
    private TextField txtProductPrice;
    @FXML
    private TextField txtSearchProduct;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private ListView<Product> listviewProducts;


    Alert alert = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listviewProducts.setItems(products);
        listviewProducts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void addProduct(){
        if(txtProductName.getText().trim().equals("") || txtProductPrice.getText().trim().equals("")){
            alert.setContentText("Veuillez renseigner tous les champs");
            alert.show();
        }
        else{
            try{
                Product p = new Product(txtProductName.getText(), Double.parseDouble(txtProductPrice.getText()));
                products.add(p);

                clearFields();
            }
            catch(NumberFormatException ex){
                alert.setContentText("Le prix doit etre une valeur numérique");
                alert.show();
            }
        }
    }
    public void deleteProduct(){
        int index = listviewProducts.getSelectionModel().getSelectedIndex();
        if(index == -1){
            alert.setContentText("Veuillez selectionner la ligne à supprimé");
            alert.show();
        }
        else{
            products.remove(index);
            clearFields();
        }
    }
    public void updateProduct(){
        if(txtProductName.getText().trim().equals("") || txtProductPrice.getText().trim().equals("")){
            alert.setContentText("Veuillez renseigner tous les champs");
            alert.show();
        }
        else{
            try{

                Product p = listviewProducts.getSelectionModel().getSelectedItem();
                p.setName(txtProductName.getText());
                p.setPrice(Double.parseDouble(txtProductPrice.getText()));

                listviewProducts.refresh();

                clearFields();
            }
            catch(NumberFormatException ex){
                alert.setContentText("Le prix doit etre une valeur numérique");
                alert.show();
            }
        }
    }


    public void textChange(KeyEvent keyEvent) {
        String key_word = txtSearchProduct.getText();

        FilteredList<Product> filteredList = products.filtered(p -> p.getName().contains(key_word)
                || Double.toString(p.getPrice()).contains(key_word));
        listviewProducts.setItems(filteredList);
    }

    public void cellSelection(MouseEvent mouseEvent) {
        Product p = listviewProducts.getSelectionModel().getSelectedItem();
        txtProductName.setText(p.getName());
        txtProductPrice.setText(Double.toString(p.getPrice()));
    }

    private void clearFields(){
        txtProductName.clear();
        txtProductPrice.clear();
    }
}
