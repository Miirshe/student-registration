package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import  java.sql.*;
import java.util.ResourceBundle;

public class Controller  implements Initializable {
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school" , "root" , "");
    Statement statement = con.createStatement();
    ResultSet resultSet = statement.executeQuery("select * from student");
    @FXML
    private TableView<student> TableData;
    ObservableList<student> list = FXCollections.observableArrayList();
    @FXML
    private TableColumn<student, String> address;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<student, Integer> id;

    @FXML
    private TableColumn<student, String> name;

    @FXML
    private TableColumn<student, String> title;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTitle;

    public Controller() throws SQLException {
    }

    @FXML
    void OnTableView(MouseEvent event) throws SQLException {
        student student = TableData.getSelectionModel().getSelectedItem();
        txtName.setText(student.getName());
        txtAddress.setText(student.getAddress());
        txtTitle.setText(student.getTitle());
    }

    @FXML
    void onDelete(ActionEvent event) throws SQLException {

        int myIndex = TableData.getSelectionModel().getFocusedIndex();
        int id = Integer.parseInt(String.valueOf(TableData.getItems().get(myIndex).getId()));
        PreparedStatement preparedStatement = con.prepareStatement("delete from student where id = ? ");
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DELETE");
        alert.setContentText("Successfully Deleted ...");
        alert.show();
        BindData();
        ClearData();



    }

    @FXML
    void onEdit(ActionEvent event) throws SQLException {
        if(txtName.getText().equals("") || txtAddress.getText().equals("") || txtTitle.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(" Input fields are not empty  ...");
            alert.show();

        }
        else{
            int myIndex = TableData.getSelectionModel().getFocusedIndex();
            int id = Integer.parseInt(String.valueOf(TableData.getItems().get(myIndex).getId()));
            String name = txtName.getText();
            String address = txtAddress.getText();
            String title = txtTitle.getText();

            PreparedStatement preparedStatement = con.prepareStatement("update student set name = ? , address = ? , title = ?  where id = ? ");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, title);
            preparedStatement.setInt(4,id);
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("EDIT");
            alert.setContentText("Successfully Updated ...");
            alert.show();
            BindData();
            ClearData();
        }

    }

    @FXML
    void onSave(ActionEvent event) throws SQLException {
        try{
            if(txtName.getText().equals("") || txtAddress.getText().equals("") || txtTitle.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText(" Input fields are not empty  ...");
                alert.show();

            }else {
                String name = txtName.getText();
                String address = txtAddress.getText();
                String title = txtTitle.getText();
                PreparedStatement preparedStatement = con.prepareStatement("insert into student values( default , ? , ? , ? )");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, address);
                preparedStatement.setString(3, title);
                preparedStatement.executeUpdate();
                BindData();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SAVE");
                alert.setContentText("Successfully Saved ...");
                alert.show();
                ClearData();

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            BindData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void BindData() throws SQLException {
        TableData.getItems().clear();
        TableData.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        id.setCellValueFactory(new PropertyValueFactory<student , Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<student , String>("name"));
        address.setCellValueFactory(new PropertyValueFactory<student , String>("address"));
        title.setCellValueFactory(new PropertyValueFactory<student , String>("title"));
        ResultSet resultSet = statement.executeQuery("select * from student");
        while (resultSet.next()){
            list.addAll(new student(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("title")
            ));
            TableData.setItems(list);
        }

    }
    void ClearData (){
        txtName.clear();
        txtAddress.clear();
        txtTitle.clear();
    }
    @FXML
    void OnRefresh(ActionEvent event) throws SQLException {
        BindData();
        ClearData();
    }
}
