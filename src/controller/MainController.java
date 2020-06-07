package controller;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
//import org.controlsfx.control.Notifications;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
//import library.AlertBox;
import library.Registers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.time.LocalDate;
//import java.awt.event.ActionEvent;
import javafx.event.ActionEvent;
// # onaction ın çözümü bu
//import java.awt.event.MouseEvent;
import java.sql.*;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Convert;
import org.testng.Converter;

import java.net.URL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public TextField departmentField;
    @FXML
    private TableColumn<Registers, Date> dateColumn2;
    @FXML
    public ImageView image1;
    @FXML
    public Circle mycircle;
    @FXML
    public DatePicker datePicker2;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private Pane pane;

    @FXML
    private Label uyarılabel;

    @FXML
    private TextField mailField;

    @FXML
    private DatePicker datePicker;



    @FXML
    private TextField filteredField;

    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button button;

    @FXML
    private TableView<Registers> TableView;

    @FXML
    private TableColumn<Registers, Integer> idColumn;

    @FXML
    private TableColumn<Registers, String> nameColumn;

    @FXML
    private TableColumn<Registers, String> surnameColumn;

    @FXML
    private TableColumn<Registers, String> departmentColumn;

    @FXML
    private TableColumn<Registers, String> mailColumn;

    @FXML
    private TableColumn<Registers, Date> dateColumn;






    @FXML
    public void insertButton() {
      insert(Integer.parseInt(idField.getText()),nameField.getText(),surnameField.getText(),departmentField.getText(),mailField.getText(),datePicker.getValue(),datePicker2.getValue());
      showRegisters();
    }

    public void insert(int id, String name, String surname, String department, String mail, LocalDate date1, LocalDate date2){
        idField.getText().equals(id);
        nameField.getText().equals(name);
        surnameField.getText().equals(surname);
        departmentField.getText().equals(department);
        mailField.getText().equals(mail);
        datePicker.getValue().equals(date1);
        datePicker2.getValue().equals(date2);
        String query = "insert into registers values('" + id+ "','" + name + "','" + surname + "','" + department + "','" + mail + "','" + date1+ "','"+date2+"')";
        executeQuery(query);


   }

    @FXML
    private void updateButton() {
        String query = "UPDATE registers SET Name='" + nameField.getText() + "',Surname='" + surnameField.getText() + "',Department='" + departmentField.getText() + "',Mail='" + mailField.getText() + "',Date='"+datePicker.getValue()+"',Date2='"+datePicker2.getValue()+"' WHERE Number='" + idField.getText() + "'";
        executeQuery(query);
        showRegisters();
    }

    @FXML
    private void deleteButton() {
        String query = "DELETE FROM registers WHERE Number='" + idField.getText() + "'";
        executeQuery(query);
        showRegisters();
    }

    @FXML
    private void alert1(ActionEvent event) {
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Basic Alert");
        a1.setContentText("You have a very simple message");
        a1.setHeaderText(null);
        a1.showAndWait();
        //WARNING yerine INFORMATION da yazabilirsin
    }



    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        giveAlert();
        showRegisters();
        mycircle.setStroke(Color.SEAGREEN);
        Image im=new Image("src/indir.png",false);
        mycircle.setFill(new ImagePattern(im));
        mycircle.setEffect((new DropShadow(+25d, +0d ,+2d,Color.DARKCYAN)));

    }


    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje_sgk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "1794eliz");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<Registers> getRegistersList() {
        ObservableList<Registers> registerList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM registers ";
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Registers registers;
            while (rs.next()) {
                registers = new Registers(rs.getInt("Number"), rs.getString("Name"), rs.getString("Surname"), rs.getString("Department"), rs.getString("Mail"), rs.getDate("Date"),rs.getDate("Date2"));
                registerList.add(registers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registerList;
    }

    // I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
    public void showRegisters() {
        ObservableList<Registers> list = getRegistersList();

        idColumn.setCellValueFactory(new PropertyValueFactory<Registers, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Registers, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Registers, String>("surname"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<Registers, String>("department"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<Registers, String>("mail"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Registers, Date>("date"));
        dateColumn2.setCellValueFactory(new PropertyValueFactory<Registers, Date>("date2"));
        TableView.setItems(list);
//*************
        TableView.setEditable(true);

        TableColumn<Object, String> idColumn = new TableColumn<>("Number"); // Kolon Başlığı
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idColumn")); // Kolona yüklenecek veri alanı adı
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // Edit Yapacaksak alanı bir textfield olarak tanımladık


        TableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }


    @FXML
    public void clickItem(MouseEvent event)
    {
        if (event.getClickCount() == 2) //Checking double click
        {
            idField.setText(String.valueOf(TableView.getSelectionModel().getSelectedItem().getId()));
            nameField.setText(TableView.getSelectionModel().getSelectedItem().getName());
            surnameField.setText(TableView.getSelectionModel().getSelectedItem().getSurname());
            departmentField.setText(TableView.getSelectionModel().getSelectedItem().getDepartment());
            mailField.setText(TableView.getSelectionModel().getSelectedItem().getMail());
            datePicker.setValue(TableView.getSelectionModel().getSelectedItem().getDate().toLocalDate());
            datePicker2.setValue(TableView.getSelectionModel().getSelectedItem().getDate2().toLocalDate());



        }
    }
    public void ClearButton(){
        idField.clear();
        nameField.clear();
        surnameField.clear();
        departmentField.clear();
        mailField.clear();
        datePicker.getEditor().clear();
        datePicker2.getEditor().clear();
    }

    public void Filtering(){
        ObservableList<Registers> list = getRegistersList();
        FilteredList<Registers> filteredList=new FilteredList<>(list,p->true);
        filteredField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(p -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (p.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (p.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
         SortedList<Registers> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(TableView.comparatorProperty());
        TableView.setItems(sortedData);

    }

    public void giveAlert() {
        ObservableList<Registers> list = getRegistersList();
        SortedList<Registers> alertList = new SortedList<Registers>(list,
                (Registers date, Registers date2) -> {//Sürekli kontrol edip saatte 1 tekrar uygulama açılsın.
                    if (date.getDate().toLocalDate().isEqual(LocalDate.now().plusDays(1))) {
                        uyarılabel.textProperty().setValue("\tStaj tarihleri yaklaşan kayıtlar bulundu! \n\t" + date.getDate().toString());
                        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        dateColumn.setCellFactory(column -> new TableCell<Registers, Date>() {
                            @Override
                            protected void updateItem(Date item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item == null || empty) {
                                    dateColumn.setCellValueFactory(new PropertyValueFactory<Registers, Date>("date"));
                                    setText(null);
                                    setStyle("");
                                } else {
                                    setText(item.toString());
                                    // Style all dates in March with a different color.
                                    if (item.equals(date.getDate())) {
                                        setTextFill(Color.BLACK);
                                        setStyle("-fx-background-color: red");

                                    } else {
                                        setTextFill(Color.BLACK);
                                        setStyle("");
                                    }
                                }
                            }
                        });
                    }
                    return 0;
                });

    }
}



