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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.html.Option;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class AdminDashboard implements Initializable {

    @FXML
    private Button AddUser_Btn;

    @FXML
    private AnchorPane Add_User_Form;

    @FXML
    private Button Add_User_Save_Btn;


    @FXML
    private AreaChart<?, ?> Dashboard_Chart;

    @FXML
    private AnchorPane Dashboard_Chart_Pane;

    @FXML
    private AnchorPane Dashboard_Form;

    @FXML
    private Button Dashboard_btn;

    @FXML
    private Button Logout_Btn;

    @FXML
    private AnchorPane MedicineCount_Pane;

    @FXML
    private Button Profle_Btn;

    @FXML
    private AnchorPane TotalCustomer_Pane;

    @FXML
    private AnchorPane TotalIncome_Pane;

    @FXML
    private Label Total_User_Count;

    @FXML
    private Label Total_Income_Count;

    @FXML
    private Label Total_Medicine_Count;

    @FXML
    private Label UserName_Lable;

    @FXML
    private Button ViewUser_Btn;

    @FXML
    private ComboBox<?> combo_Role;

    @FXML
    private TextField tf_Address;

    @FXML
    private TextField tf_Email;

    @FXML
    private TextField tf_FirstName;

    @FXML
    private TextField tf_LastName;

    @FXML
    private TextField tf_MobileNumber;

    @FXML
    private PasswordField tf_Password;
    @FXML
    private AnchorPane View_User_Form;

    @FXML
    private AnchorPane Profile_Form;
    @FXML
    private Label profile_FirstName_Lable;
    @FXML
    private Label profile_LastName_Lable;
    @FXML
    private TextField profile_Email_tf;
    @FXML
    private TextField profile_Address_tf;
    @FXML
    private TextField profile_Mobile_tf;
    @FXML
    private TextField profile_Password_tf;
    @FXML
    private TextField profile_Role_tf;

    @FXML
    private TextField update_User_Address;

    @FXML
    private TextField update_User_Email;

    @FXML
    private TextField update_User_First;

    @FXML
    private TextField update_User_Last;

    @FXML
    private TextField update_User_Password;

    @FXML
    private TextField update_User_Phone;

    @FXML
    private ComboBox<String> update_User_Role;

    @FXML
    private TableView<UserDataModel> User_Table_View;
    @FXML
    private TableColumn<UserDataModel, String> userList_Col_Address;

    @FXML
    private TableColumn<UserDataModel, String> userList_Col_Email;

    @FXML
    private TableColumn<UserDataModel, String> userList_Col_First;

    @FXML
    private TableColumn<UserDataModel, String> userList_Col_Id;

    @FXML
    private TableColumn<UserDataModel, String> userList_Col_Last;

    @FXML
    private TableColumn<UserDataModel, String> userList_Col_Password;

    @FXML
    private TableColumn<UserDataModel, String> userList_Col_Phone;

    @FXML
    private TableColumn<UserDataModel, String> userList_Col_Role;

    @FXML
    private TextField update_User_Id;
    @FXML
    private TextField User_Search_tf;
    // For Database
    private PreparedStatement prepare;
    private Connection connect;
    private ResultSet result;
    private Statement statement;

    public ObservableList<UserDataModel> addUserListData() {
        ObservableList<UserDataModel> listData = FXCollections.observableArrayList();
        connect = DbConnection.connectDb();
        String sql = "SELECT * FROM USER";
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            UserDataModel userData;
            while (result.next()) {
                userData = new UserDataModel(result.getInt("Id"), result.getString("FirstName"),
                        result.getString("LastName"), result.getString("Phone"), result.getString("email"),
                        result.getString("Password"), result.getString("Role"), result.getString("Address"));
                listData.add(userData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listData;
    }

    private ObservableList<UserDataModel> userList;

    public void userShowListData() {
        userList = addUserListData();

        userList_Col_Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        userList_Col_First.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        userList_Col_Last.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        userList_Col_Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        userList_Col_Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        userList_Col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        userList_Col_Password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        userList_Col_Role.setCellValueFactory(new PropertyValueFactory<>("Role"));

        User_Table_View.setItems(userList);
    }

    public void updateUserSelect() {
        UserDataModel userData = User_Table_View.getSelectionModel().getSelectedItem();
        int num = User_Table_View.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        update_User_Id.setText(String.valueOf(userData.getId()));
        update_User_First.setText(String.valueOf(userData.getFirstName()));
        update_User_Last.setText(String.valueOf(userData.getLastName()));
        update_User_Address.setText(String.valueOf(userData.getAddress()));
        update_User_Email.setText(String.valueOf(userData.getEmail()));
        update_User_Password.setText(String.valueOf(userData.getPassword()));
        update_User_Phone.setText(String.valueOf(userData.getPhone()));
        update_User_Role.setValue(String.valueOf(userData.getRole()));
    }

    public void updateUser() {
        String sql = "UPDATE user SET FirstName= '"
                + update_User_First.getText() + "',LastName='"
                + update_User_Last.getText() + "',email='"
                + update_User_Email.getText() + "',Address='"
                + update_User_Address.getText() + "',Phone='"
                + update_User_Phone.getText() + "',Password='"
                + update_User_Password.getText() + "',Role='"
                + update_User_Role.getSelectionModel().getSelectedItem() + "'WHERE Id='"
                + update_User_Id.getText() + "'";

        connect = DbConnection.connectDb();
        try {
            Alert alert;
            if (update_User_First.getText().isEmpty() || update_User_Last.getText().isEmpty() || update_User_Phone.getText().isEmpty()
                    || update_User_Email.getText().isEmpty() || update_User_Password.getText().isEmpty()
                    || update_User_Role.getSelectionModel().getSelectedItem() == null
                    || update_User_Address.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Fill All Blank Fields");
                alert.showAndWait();
            } else {
                statement = connect.createStatement();
                statement.executeUpdate(sql);
                userShowListData();
                updateUserReset();
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

        if (event.getSource() == Dashboard_btn) {
            Dashboard_Form.setVisible(true);
            Add_User_Form.setVisible(false);
            View_User_Form.setVisible(false);
            Profile_Form.setVisible(false);

            Dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c)");
            AddUser_Btn.setStyle("-fx-background-color:transparent");
            ViewUser_Btn.setStyle("-fx-background-color:transparent");
            Profle_Btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == AddUser_Btn) {
            Dashboard_Form.setVisible(false);
            Add_User_Form.setVisible(true);
            View_User_Form.setVisible(false);
            Profile_Form.setVisible(false);

            AddUser_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c)");
            Dashboard_btn.setStyle("-fx-background-color:transparent");
            ViewUser_Btn.setStyle("-fx-background-color:transparent");
            Profle_Btn.setStyle("-fx-background-color:transparent");


        } else if (event.getSource() == ViewUser_Btn) {
            Dashboard_Form.setVisible(false);
            Add_User_Form.setVisible(false);
            View_User_Form.setVisible(true);
            Profile_Form.setVisible(false);

            ViewUser_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c)");
            Dashboard_btn.setStyle("-fx-background-color:transparent");
            AddUser_Btn.setStyle("-fx-background-color:transparent");
            Profle_Btn.setStyle("-fx-background-color:transparent");
            userShowListData();
            userSearch();
        } else if (event.getSource() == Profle_Btn) {
            Dashboard_Form.setVisible(false);
            Add_User_Form.setVisible(false);
            View_User_Form.setVisible(false);
            Profile_Form.setVisible(true);

            Profle_Btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #41b170, #8a418c)");
            Dashboard_btn.setStyle("-fx-background-color:transparent");
            AddUser_Btn.setStyle("-fx-background-color:transparent");
            ViewUser_Btn.setStyle("-fx-background-color:transparent");
            getUserName(GetDataForUser.email);
        }

    }

    private final String[] Roles = {"Admin", "Pharmacist"};

    public void addRoleList() {
        List<String> listT = new ArrayList<>();
        Collections.addAll(listT, Roles);
        ObservableList listData = FXCollections.observableArrayList(listT);
        combo_Role.setItems(listData);
        update_User_Role.setItems(listData);
    }

    public void addUser() {
        String sql = "INSERT INTO USER (FirstName,LastName,Phone,Email,Password,Role,Address)"
                + "VALUES(?,?,?,?,?,?,?)";
        connect = DbConnection.connectDb();

        try {

            Alert alert;
            if (tf_FirstName.getText().isEmpty() || tf_LastName.getText().isEmpty() || tf_MobileNumber.getText().isEmpty()
                    || tf_Email.getText().isEmpty() || tf_Password.getText().isEmpty()
                    || combo_Role.getSelectionModel().getSelectedItem() == null
                    || tf_Address.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Fill All Blank Fields");
                alert.showAndWait();
            } else {
                String isExist = "SELECT EXISTS(SELECT * FROM user WHERE email = '" + tf_Email.getText() + "')";
                statement = connect.createStatement();
                result = statement.executeQuery(isExist);
                if (result.next()) {
                    if (result.getInt(1) == 1) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Email " + tf_Email.getText() + " is Already Exist");
                        alert.showAndWait();
                    } else {
                        prepare = connect.prepareStatement((sql));
                        prepare.setString(1, tf_FirstName.getText());
                        prepare.setString(2, tf_LastName.getText());
                        prepare.setString(3, tf_MobileNumber.getText());
                        prepare.setString(4, tf_Email.getText());
                        prepare.setString(5, tf_Password.getText());
                        prepare.setString(6, (String) combo_Role.getSelectionModel().getSelectedItem());
                        prepare.setString(7, tf_Address.getText());
                        int rows = prepare.executeUpdate();
                        if (rows > 0) {
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("User Created Successfully");
                            alert.showAndWait();
                            addUserReset();
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUserReset() {
        tf_FirstName.setText("");
        tf_LastName.setText("");
        tf_MobileNumber.setText("");
        tf_Email.setText("");
        tf_Password.setText("");
        combo_Role.getSelectionModel().clearSelection();
        tf_Address.setText("");
    }

    private void updateUserReset() {
        update_User_Address.setText("");
        update_User_Last.setText("");
        update_User_Password.setText("");
        update_User_Email.setText("");
        update_User_Phone.setText("");
        update_User_Role.getSelectionModel().clearSelection();
        update_User_First.setText("");
        update_User_Id.setText("");
    }

    public void deleteUser() {
        String sql = "DELETE FROM USER WHERE Id= '" + update_User_Id.getText() + "'";

        connect = DbConnection.connectDb();
        try {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to Delete " + update_User_Email.getText());
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("User Deleted Successfully");
                alert.showAndWait();

                userShowListData();
                updateUserReset();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void userSearch() {
        FilteredList<UserDataModel> filter = new FilteredList<>(userList, e -> true);
        User_Search_tf.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateUserData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateUserData.getId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getFirstName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getLastName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getAddress().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getEmail().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getPassword().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getPhone().toLowerCase().contains(searchKey)) {
                    return true;
                } else return predicateUserData.getRole().toLowerCase().contains(searchKey);
            });
        });
        SortedList<UserDataModel> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(User_Table_View.comparatorProperty());
        User_Table_View.setItems(sortedList);
    }

    public void displayTotalMedicineCount() {
        String sql = "SELECT COUNT(*) FROM user";
        connect = DbConnection.connectDb();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if (result.next()) {
                Integer count = result.getInt("COUNT(*)");
                Total_Medicine_Count.setText(count.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void displayTotalIncomeCount() {
        String sql = "SELECT SUM(TotalPaid) FROM bill";
        connect = DbConnection.connectDb();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if (result.next()) {
                Double count = result.getDouble("SUM(TotalPaid)");
                Total_Income_Count.setText("$ " + count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void displayTotalUsersCount() {
        String sql = "SELECT COUNT(*) FROM user";
        connect = DbConnection.connectDb();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if (result.next()) {
                Integer count = result.getInt("COUNT(*)");
                Total_User_Count.setText(count.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void chart() {
        Dashboard_Chart.getData().clear();
        String sql = "SELECT Expiry,COUNT(*) FROM medicine"
                + " GROUP BY Expiry ORDER BY TIMESTAMP(Expiry) ASC LIMIT 9";
        connect = DbConnection.connectDb();

        try {

            XYChart.Series chart = new XYChart.Series();

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()){
                chart.getData().add(new XYChart.Data(result.getString("Expiry"),result.getInt("COUNT(*)")));
            }
            Dashboard_Chart.getData().add(chart);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        getUserName(GetDataForUser.email);
        addRoleList();
        chart();
        displayTotalMedicineCount();
        displayTotalIncomeCount();
        displayTotalUsersCount();
    }
}
