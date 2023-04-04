package com.example.pharmacymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class PharmacistDashboard implements Initializable {
    @FXML
    private Button Logout_Btn;

    @FXML
    private Button Profle_Btn;

    @FXML
    private Label UserName_Lable;

    @FXML
    private DatePicker add_Medicine_Expiry_tf;

    @FXML
    private TextField add_Medicine_Name_tf;

    @FXML
    private TextField add_Medicine_Price_tf;

    @FXML
    private TextField add_Medicine_Quantity_tf;

    @FXML
    private Button add_medicine_btn;

    @FXML
    private AnchorPane add_medicine_form;

    @FXML
    private TableColumn<MedicineDataModel, String> medicine_table_col_header_Id;

    @FXML
    private TableColumn<MedicineDataModel, String> medicine_table_col_header_expiry;

    @FXML
    private TableColumn<MedicineDataModel, String> medicine_table_col_header_name;

    @FXML
    private TableColumn<MedicineDataModel, String> medicine_table_col_header_price;

    @FXML
    private TableColumn<MedicineDataModel, String> medicine_table_col_header_qty;

    @FXML
    private TableView<MedicineDataModel> medicine_table_view;

    @FXML
    private Button sell_medicine_Btn;

    @FXML
    private Button update_medicine_delete_btn;

    @FXML
    private TextField update_medicine_id_tf;

    @FXML
    private TextField update_medicine_name_tf;

    @FXML
    private TextField update_medicine_price_tf;

    @FXML
    private TextField update_medicine_qty_tf;

    @FXML
    private TextField update_medicine_search_tf;

    @FXML
    private Button update_medicine_update_btn;

    @FXML
    private Button view_bill_btn;

    @FXML
    private Button view_medicine_Btn;

    @FXML
    private AnchorPane view_medicine_form;
    @FXML
    private DatePicker update_medicine_expiry_tf;
    @FXML
    private Label profile_FirstName_Lable;
    @FXML
    private Label profile_LastName_Lable;
    @FXML
    private TextField profile_Address_tf;
    @FXML
    private TextField profile_Email_tf;
    @FXML
    private TextField profile_Mobile_tf;
    @FXML
    private TextField profile_Password_tf;
    @FXML
    private TextField profile_Role_tf;
    @FXML
    private AnchorPane Profile_Form;
    @FXML
    private AnchorPane Sell_Medicine_Form;
    @FXML
    private AnchorPane View_Bill_Form;
    @FXML
    private TableColumn<MedicineDataModel, String> sell_medList_Col_Id;

    @FXML
    private TableColumn<MedicineDataModel, String> sell_medList_Col_Name;

    @FXML
    private TableColumn<MedicineDataModel, String> sell_medList_Col_Quantity;

    @FXML
    private TableColumn<MedicineDataModel, String> sell_medList_Col_Price;

    @FXML
    private TableColumn<MedicineDataModel, String> sell_medList_Col_Expiry;

    @FXML
    private TableView<MedicineDataModel> sell_med_table_View;
    @FXML
    private TextField sell_med_Search_tf;
    @FXML
    private TextField sell_med_name_tf;
    @FXML
    private TextField sell_med_qty_tf;
    @FXML
    private TextField sell_med_price_tf;
    @FXML
    private TextField sell_med_noOfUnits_tf;
    @FXML
    private TextField sell_med_totalPrice_tf;
    @FXML
    private TextField sell_med_Id_tf;

    @FXML
    private Button add_to_cart_btn;
    @FXML
    private Button purchase_bill_btn;
    @FXML
    private Label Total_Bill_Payment_lb;
    @FXML
    private TableColumn<SellMedicineDataModel, String> sell_medList_Col_Id_final;

    @FXML
    private TableColumn<SellMedicineDataModel, String> sell_medList_Col_Name_final;

    @FXML
    private TableColumn<SellMedicineDataModel, String> sell_medList_Col_Qty_final;

    @FXML
    private TableColumn<SellMedicineDataModel, String> sell_medList_Col_Price_final;

    @FXML
    private TableColumn<SellMedicineDataModel, String> sell_medList_Col_NoOfUnits_Final;
    @FXML
    private TableColumn<SellMedicineDataModel, String> sell_medList_Col_TotalPrice_Final;

    @FXML
    private TableView<SellMedicineDataModel> sell_med_table_final_View;

    @FXML
    private TableColumn<BillDataModel, String> bill_col_Id;

    @FXML
    private TableColumn<BillDataModel, String> bill_col_TotalPaid;

    @FXML
    private TableColumn<BillDataModel, String> bill_col_Date;

    @FXML
    private TableColumn<BillDataModel, String> bill_col_GeneratedBy;

    @FXML
    private TableView<BillDataModel> bill_table_view;

    @FXML
    private TextField bill_search_tf;
    // For Database
    private PreparedStatement prepare;
    private Connection connect;
    private ResultSet result;
    private Statement statement;

    public ObservableList<MedicineDataModel> addMedicineListData() {
        ObservableList<MedicineDataModel> listData = FXCollections.observableArrayList();
        connect = DbConnection.connectDb();
        String sql = "SELECT * FROM MEDICINE";
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            MedicineDataModel medicineData;
            while (result.next()) {
                medicineData = new MedicineDataModel(result.getInt("Id"), result.getDouble("PricePerUnit"), result.getInt("Quantity"), result.getString("Name"), result.getDate("Expiry"));
                listData.add(medicineData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listData;
    }

    public ObservableList<SellMedicineDataModel> addSellMedicineListDataForPurchase() {
        ObservableList<SellMedicineDataModel> listData = FXCollections.observableArrayList();
        SellMedicineDataModel data = new SellMedicineDataModel(Integer.parseInt(sell_med_Id_tf.getText()), sell_med_name_tf.getText(), Integer.parseInt(sell_med_qty_tf.getText()),
                Double.parseDouble(sell_med_price_tf.getText()), Integer.parseInt(sell_med_noOfUnits_tf.getText()),
                Double.parseDouble(sell_med_totalPrice_tf.getText()));
        listData.add(data);
        return listData;
    }

    public void medicineShowListData() {
        medicineList = addMedicineListData();

        medicine_table_col_header_Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        medicine_table_col_header_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        medicine_table_col_header_qty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        medicine_table_col_header_price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        medicine_table_col_header_expiry.setCellValueFactory(new PropertyValueFactory<>("Expiry"));

        medicine_table_view.setItems(medicineList);
        // for sale medicine page

        sell_medList_Col_Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        sell_medList_Col_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sell_medList_Col_Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        sell_medList_Col_Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        sell_medList_Col_Expiry.setCellValueFactory(new PropertyValueFactory<>("Expiry"));

        sell_med_table_View.setItems(medicineList);
    }

    private ObservableList<MedicineDataModel> medicineList;
    private ObservableList<SellMedicineDataModel> sellMedicineList;

    public void addMedicineToCart() {
        String query = "Select * from medicine Where Id= " + sell_med_Id_tf.getText();
        connect = DbConnection.connectDb();

        Alert alert;

        try {
            prepare = connect.prepareStatement((query));
            result = prepare.executeQuery();
            if (result.next()) {
                Integer qty = result.getInt("Quantity");
                if (Integer.parseInt(sell_med_noOfUnits_tf.getText()) > qty) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Only " + result.getInt("Quantity") + " Are Left");
                    alert.showAndWait();
                } else {
                    String sql = "INSERT INTO cart (Id,Name,Quantity,Price,NoOfUnits,TotalPrice)"
                            + "VALUES(?,?,?,?,?,?)";
                    connect = DbConnection.connectDb();
                    try {
                        if (sell_med_Id_tf.getText().isEmpty() || sell_med_name_tf.getText().isEmpty() || sell_med_price_tf.getText().isEmpty()
                                || sell_med_qty_tf.getText().isEmpty() || sell_med_noOfUnits_tf.getText().isEmpty()
                                || sell_med_totalPrice_tf.getText().isEmpty()) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Please Fill All Blank Fields");
                            alert.showAndWait();
                        } else {
                            prepare = connect.prepareStatement((sql));
                            prepare.setString(1, sell_med_Id_tf.getText());
                            prepare.setString(2, sell_med_name_tf.getText());
                            prepare.setString(3, sell_med_qty_tf.getText());
                            prepare.setString(4, sell_med_price_tf.getText());
                            prepare.setString(5, sell_med_noOfUnits_tf.getText());
                            prepare.setString(6, sell_med_totalPrice_tf.getText());
                            int rows = prepare.executeUpdate();
                            if (rows > 0) {
                                showDataOfCartTable();
                                displayTotalPrice();
                                sellMedicineFieldsReset();
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are You Sure You Want To Logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // Hide the Admin Dashboard Form
                Logout_Btn.getScene().getWindow().hide();
                // Setup login form
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getUserName(String email) {
        String sql = "Select * from user where email=?";
        connect = DbConnection.connectDb();

        try {
            prepare = connect.prepareStatement((sql));
            prepare.setString(1, email);

            result = prepare.executeQuery();
            if (result.next()) {
                String user = result.getString("FirstName");
                UserName_Lable.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
                profile_FirstName_Lable.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
                profile_LastName_Lable.setText(result.getString("LastName").substring(0, 1).toUpperCase() + result.getString("LastName").substring(1));
                profile_Address_tf.setText(result.getString("Address"));
                profile_Email_tf.setText(result.getString("email"));
                profile_Mobile_tf.setText(result.getString("Phone"));
                profile_Password_tf.setText(result.getString("Password"));
                profile_Role_tf.setText(result.getString("Role"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == add_medicine_btn) {
            add_medicine_form.setVisible(true);
            view_medicine_form.setVisible(false);
            Profile_Form.setVisible(false);
            Sell_Medicine_Form.setVisible(false);
            View_Bill_Form.setVisible(false);

            add_medicine_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c)");
            sell_medicine_Btn.setStyle("-fx-background-color:transparent");
            view_medicine_Btn.setStyle("-fx-background-color:transparent");
            view_bill_btn.setStyle("-fx-background-color:transparent");
            Profle_Btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == view_medicine_Btn) {
            add_medicine_form.setVisible(false);
            view_medicine_form.setVisible(true);
            Profile_Form.setVisible(false);
            Sell_Medicine_Form.setVisible(false);
            View_Bill_Form.setVisible(false);

            view_medicine_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c)");
            sell_medicine_Btn.setStyle("-fx-background-color:transparent");
            add_medicine_btn.setStyle("-fx-background-color:transparent");
            view_bill_btn.setStyle("-fx-background-color:transparent");
            Profle_Btn.setStyle("-fx-background-color:transparent");
            medicineShowListData();
            medicineSearch();

        } else if (event.getSource() == sell_medicine_Btn) {
            add_medicine_form.setVisible(false);
            view_medicine_form.setVisible(false);
            Profile_Form.setVisible(false);
            Sell_Medicine_Form.setVisible(true);
            View_Bill_Form.setVisible(false);

            sell_medicine_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c)");
            add_medicine_btn.setStyle("-fx-background-color:transparent");
            view_medicine_Btn.setStyle("-fx-background-color:transparent");
            view_bill_btn.setStyle("-fx-background-color:transparent");
            Profle_Btn.setStyle("-fx-background-color:transparent");
            medicineShowListData();
            medicineSearchForSellTable();

        } else if (event.getSource() == view_bill_btn) {

            add_medicine_form.setVisible(false);
            view_medicine_form.setVisible(false);
            Profile_Form.setVisible(false);
            Sell_Medicine_Form.setVisible(false);
            View_Bill_Form.setVisible(true);

            view_bill_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c)");
            Profle_Btn.setStyle("-fx-background-color:transparent");
            view_medicine_Btn.setStyle("-fx-background-color:transparent");
            add_medicine_btn.setStyle("-fx-background-color:transparent");
            sell_medicine_Btn.setStyle("-fx-background-color:transparent");
            showBillDataList();
            searchBillDataList();
        } else if (event.getSource() == Profle_Btn) {

            add_medicine_form.setVisible(false);
            view_medicine_form.setVisible(false);
            Profile_Form.setVisible(true);
            Sell_Medicine_Form.setVisible(false);
            View_Bill_Form.setVisible(false);

            Profle_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c)");
            view_medicine_Btn.setStyle("-fx-background-color:transparent");
            add_medicine_btn.setStyle("-fx-background-color:transparent");
            sell_medicine_Btn.setStyle("-fx-background-color:transparent");
            view_bill_btn.setStyle("-fx-background-color:transparent");
            getUserName(GetDataForUser.email);
        }

    }

    public void addMedicine() {
        String sql = "INSERT INTO MEDICINE (Name,Quantity,PricePerUnit,Expiry)"
                + "VALUES(?,?,?,?)";
        connect = DbConnection.connectDb();

        try {

            Alert alert;
            if (add_Medicine_Name_tf.getText().isEmpty() || add_Medicine_Quantity_tf.getText().isEmpty() || add_Medicine_Price_tf.getText().isEmpty()
                    || add_Medicine_Expiry_tf.getValue() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Fill All Blank Fields");
                alert.showAndWait();
            } else {
                String isExist = "SELECT EXISTS(SELECT * FROM medicine WHERE Name = '" + add_Medicine_Name_tf.getText() + "')";
                statement = connect.createStatement();
                result = statement.executeQuery(isExist);
                if (result.next()) {
                    if (result.getInt(1) == 1) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Medicine " + add_Medicine_Name_tf.getText() + " is Already Exist");
                        alert.showAndWait();
                    } else {
                        prepare = connect.prepareStatement((sql));
                        prepare.setString(1, add_Medicine_Name_tf.getText());
                        prepare.setString(2, add_Medicine_Quantity_tf.getText());
                        prepare.setString(3, add_Medicine_Price_tf.getText());
                        prepare.setDate(4, Date.valueOf(add_Medicine_Expiry_tf.getValue()));
                        int rows = prepare.executeUpdate();
                        if (rows > 0) {
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Medicine Added Successfully");
                            alert.showAndWait();
                            addMedicineReset();
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addMedicineReset() {
        add_Medicine_Name_tf.setText("");
        add_Medicine_Price_tf.setText("");
        add_Medicine_Quantity_tf.setText("");
        add_Medicine_Expiry_tf.setValue(null);
    }

    private void updateMedicineReset() {
        update_medicine_expiry_tf.setValue(null);
        update_medicine_qty_tf.setText("");
        update_medicine_price_tf.setText("");
        update_medicine_name_tf.setText("");
        update_medicine_id_tf.setText("");
    }

    public void addMedicineSelect() {
        MedicineDataModel medicineData = medicine_table_view.getSelectionModel().getSelectedItem();
        int num = medicine_table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        update_medicine_id_tf.setText(String.valueOf(medicineData.getId()));
        update_medicine_name_tf.setText(String.valueOf(medicineData.getName()));
        update_medicine_qty_tf.setText(String.valueOf(medicineData.getQuantity()));
        update_medicine_price_tf.setText(String.valueOf(medicineData.getPrice()));
        update_medicine_expiry_tf.setValue(LocalDate.parse(medicineData.getExpiry().toString()));
    }

    public void addMedicineSelectForSellTable() {
        MedicineDataModel medicineData = sell_med_table_View.getSelectionModel().getSelectedItem();
        int num = sell_med_table_View.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        sell_med_name_tf.setText(String.valueOf(medicineData.getName()));
        sell_med_qty_tf.setText(String.valueOf(medicineData.getQuantity()));
        sell_med_price_tf.setText(String.valueOf(medicineData.getPrice()));
        sell_med_Id_tf.setText(String.valueOf(medicineData.getId()));
    }

    public void medicineSearch() {
        FilteredList<MedicineDataModel> filter = new FilteredList<>(medicineList, e -> true);
        update_medicine_search_tf.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateUserData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateUserData.getId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getQuantity().toString().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getPrice().toString().contains(searchKey)) {
                    return true;
                } else return predicateUserData.getExpiry().toString().contains(searchKey);
            });
        });
        SortedList<MedicineDataModel> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(medicine_table_view.comparatorProperty());
        medicine_table_view.setItems(sortedList);
    }

    public void medicineSearchForSellTable() {
        FilteredList<MedicineDataModel> filter = new FilteredList<>(medicineList, e -> true);
        sell_med_Search_tf.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateUserData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateUserData.getId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getQuantity().toString().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getPrice().toString().contains(searchKey)) {
                    return true;
                } else return predicateUserData.getExpiry().toString().contains(searchKey);
            });
        });
        SortedList<MedicineDataModel> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(sell_med_table_View.comparatorProperty());
        sell_med_table_View.setItems(sortedList);
    }

    public void updateMedicine() {
        String sql = "UPDATE medicine SET Name= '"
                + update_medicine_name_tf.getText() + "',Quantity='"
                + update_medicine_qty_tf.getText() + "',PricePerUnit='"
                + update_medicine_price_tf.getText() + "',Expiry='"
                + update_medicine_expiry_tf.getValue()
                + "'WHERE Id='" + update_medicine_id_tf.getText() + "'";

        connect = DbConnection.connectDb();
        try {
            Alert alert;
            if (update_medicine_name_tf.getText().isEmpty() || update_medicine_price_tf.getText().isEmpty() || update_medicine_qty_tf.getText().isEmpty()
                    || update_medicine_expiry_tf.getValue() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Fill All Blank Fields");
                alert.showAndWait();
            } else {
                statement = connect.createStatement();
                statement.executeUpdate(sql);
                medicineShowListData();
                updateMedicineReset();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteMedicine() {
        String sql = "DELETE FROM medicine WHERE Id= '" + update_medicine_id_tf.getText() + "'";

        connect = DbConnection.connectDb();
        try {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to Delete " + update_medicine_name_tf.getText());
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Medicine Deleted Successfully");
                alert.showAndWait();

                medicineShowListData();
                updateMedicineReset();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sellMedicineFieldsReset() {
        sell_med_name_tf.setText("");
        sell_med_qty_tf.setText("");
        sell_med_price_tf.setText("");
        sell_med_noOfUnits_tf.setText("");
        sell_med_totalPrice_tf.setText("");
    }

    public void onNumberOfUnitsFieldChanges() {
        Double value = Double.parseDouble(sell_med_price_tf.getText()) * Integer.parseInt(sell_med_noOfUnits_tf.getText());
        sell_med_totalPrice_tf.setText(value.toString());
    }

    public ObservableList<SellMedicineDataModel> addCardListData() {
        ObservableList<SellMedicineDataModel> listData = FXCollections.observableArrayList();
        connect = DbConnection.connectDb();
        String sql = "SELECT * FROM Cart";
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            SellMedicineDataModel medicineData;
            while (result.next()) {
                medicineData = new SellMedicineDataModel(result.getInt("Id"), result.getString("Name"), result.getInt("Quantity"), result.getDouble("Price"), result.getInt("NoOfUnits"), result.getDouble("TotalPrice"));
                listData.add(medicineData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listData;
    }

    public void showDataOfCartTable() {
        sellMedicineList = addCardListData();

        sell_medList_Col_Id_final.setCellValueFactory(new PropertyValueFactory<>("Id"));
        sell_medList_Col_Name_final.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sell_medList_Col_Qty_final.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        sell_medList_Col_Price_final.setCellValueFactory(new PropertyValueFactory<>("Price"));
        sell_medList_Col_NoOfUnits_Final.setCellValueFactory(new PropertyValueFactory<>("NoOfUnits"));
        sell_medList_Col_TotalPrice_Final.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));

        sell_med_table_final_View.setItems(sellMedicineList);
    }

    public void displayTotalPrice() {
        connect = DbConnection.connectDb();
        String sql = "SELECT Sum(TotalPrice) FROM Cart";
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                Double value = result.getDouble("Sum(TotalPrice)");
                Total_Bill_Payment_lb.setText(((Long) Math.round(value)).toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onCartCancel() {
        connect = DbConnection.connectDb();
        String sql = "DELETE FROM Cart";
        try {
            prepare = connect.prepareStatement(sql);
            prepare.executeUpdate();
            showDataOfCartTable();
            displayTotalPrice();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ResultSet resultForMed;

    public void onPurchase() {
        String medicineQuery = "SELECT Id, SUM(NoOfUnits) AS total_noOfUnits FROM CART GROUP BY Id";
        String query = "SELECT SUM(TotalPrice) FROM cart";
        String sql = "INSERT INTO bill (Date,TotalPaid,GeneratedBy)"
                + "VALUES(?,?,?)";
        connect = DbConnection.connectDb();
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(medicineQuery);
            while (result.next()) {
                Integer medId = result.getInt("Id");
                Integer totalMedNoOfUnits = result.getInt("total_noOfUnits");
                statement = connect.createStatement();
                resultForMed = statement.executeQuery("SELECT Quantity from medicine where Id=" + medId);
                Integer tempQty = 0;
                if (resultForMed.next()) {
                    tempQty = resultForMed.getInt("Quantity");
                }
                statement = connect.createStatement();
                statement.executeUpdate("UPDATE medicine SET Quantity= '"
                        + (tempQty - totalMedNoOfUnits) + "'WHERE Id='" + medId + "'");
            }
            statement = connect.createStatement();
            result = statement.executeQuery(query);
            if (result.next()) {
                prepare = connect.prepareStatement((sql));
                Double totalPaid = result.getDouble("SUM(TotalPrice)");
                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                prepare.setString(1, myFormat.format(cal.getTime()));
                prepare.setString(2, ((Long) Math.round(totalPaid)).toString());
                prepare.setString(3, GetDataForUser.email);
                int rows = prepare.executeUpdate();
                if (rows > 0) {
                    onCartCancel();
                    showDataOfCartTable();
                    displayTotalPrice();
                    medicineShowListData();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public ObservableList<BillDataModel> billListData() {
        ObservableList<BillDataModel> listData = FXCollections.observableArrayList();
        connect = DbConnection.connectDb();
        String sql = "SELECT * FROM bill";
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            BillDataModel billData;
            while (result.next()) {
                billData = new BillDataModel(result.getInt("Id"), result.getDate("Date"),result.getDouble("TotalPaid"),result.getString("GeneratedBy"));
                listData.add(billData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listData;
    }
    private ObservableList<BillDataModel> billList;
    public void showBillDataList() {
        billList = billListData();

        bill_col_Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        bill_col_TotalPaid.setCellValueFactory(new PropertyValueFactory<>("TotalPaid"));
        bill_col_Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        bill_col_GeneratedBy.setCellValueFactory(new PropertyValueFactory<>("GeneratedBy"));


        bill_table_view.setItems(billList);
    }
    public void searchBillDataList(){
        FilteredList<BillDataModel> filter = new FilteredList<>(billList, e -> true);
        bill_search_tf.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateUserData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateUserData.getId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getDate().toString().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getTotalPaid().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else return (predicateUserData.getGeneratedBy().toLowerCase().contains(searchKey));
            });
        });
        SortedList<BillDataModel> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(bill_table_view.comparatorProperty());
        bill_table_view.setItems(sortedList);
    }
    public void initialize(URL location, ResourceBundle resources) {
        getUserName(GetDataForUser.email);
        addMedicineListData();
        showBillDataList();
        displayTotalPrice();
    }
}
