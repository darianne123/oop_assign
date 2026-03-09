package org.example.oop_assign;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StudentController {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> idColumn;
    @FXML private TableColumn<Student, String> firstNameColumn;
    @FXML private TableColumn<Student, String> lastNameColumn;
    @FXML private TableColumn<Student, String> departmentColumn;
    @FXML private TableColumn<Student, String> majorColumn;
    @FXML private TableColumn<Student, String> emailColumn;

    @FXML private TextField idField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField departmentField;
    @FXML private TextField majorField;
    @FXML private TextField emailField;
    @FXML private TextField imageUrlField;

    @FXML private Button clearButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;

    @FXML private MenuItem exitMenuItem;
    @FXML private MenuItem aboutMenuItem;

    @FXML private ImageView profileImageView;

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        studentTable.setItems(studentList);

        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, selectedStudent) -> {
            if (selectedStudent != null) {
                idField.setText(String.valueOf(selectedStudent.getId()));
                firstNameField.setText(selectedStudent.getFirstName());
                lastNameField.setText(selectedStudent.getLastName());
                departmentField.setText(selectedStudent.getDepartment());
                majorField.setText(selectedStudent.getMajor());
                emailField.setText(selectedStudent.getEmail());
            }
        });

        clearButton.setOnAction(e -> clearFields());
        addButton.setOnAction(e -> addStudent());
        deleteButton.setOnAction(e -> deleteStudent());
        editButton.setOnAction(e -> editStudent());

        exitMenuItem.setOnAction(e -> System.exit(0));
        aboutMenuItem.setOnAction(e -> showAlert("About", "JavaFX Student UI Project"));

        try {
            Image image = new Image(getClass().getResourceAsStream("/org.example.oop_assign/default-profile.png"));
            profileImageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Image not found.");
        }
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());

            Student student = new Student(
                    id,
                    firstNameField.getText().trim(),
                    lastNameField.getText().trim(),
                    departmentField.getText().trim(),
                    majorField.getText().trim(),
                    emailField.getText().trim()
            );

            studentList.add(student);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "ID must be a number.");
        }
    }

    private void deleteStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            studentList.remove(selectedStudent);
            clearFields();
        } else {
            showAlert("No Selection", "Please select a student to delete.");
        }
    }

    private void editStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            try {
                selectedStudent.setId(Integer.parseInt(idField.getText().trim()));
                selectedStudent.setFirstName(firstNameField.getText().trim());
                selectedStudent.setLastName(lastNameField.getText().trim());
                selectedStudent.setDepartment(departmentField.getText().trim());
                selectedStudent.setMajor(majorField.getText().trim());
                selectedStudent.setEmail(emailField.getText().trim());

                studentTable.refresh();
                clearFields();
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "ID must be a number.");
            }
        } else {
            showAlert("No Selection", "Please select a student to edit.");
        }
    }

    private void clearFields() {
        idField.clear();
        firstNameField.clear();
        lastNameField.clear();
        departmentField.clear();
        majorField.clear();
        emailField.clear();
        imageUrlField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}