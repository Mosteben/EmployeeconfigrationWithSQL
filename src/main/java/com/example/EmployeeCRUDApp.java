import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import com.example.dao.EmployeeRepository;
import com.example.model.Employee;
import com.example.model.Developer;

import java.sql.SQLException;
import java.util.List;

public class EmployeeCRUDApp extends Application {

    private TableView<Employee> table;
    private TextField nameField, emailField, positionField, salaryField;
    private Button addButton, cancelButton;
    private EmployeeRepository repository;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        repository = new EmployeeRepository();

        primaryStage.setTitle("Employee CRUD Operations");

        table = new TableView<>();
        loadTableData();
        table.getColumns().addAll(
                createColumn("ID", "id", 50),
                createColumn("Name", "name", 150),
                createColumn("Email", "email", 200),
                createColumn("Position", "position", 100),
                createColumn("Salary", "salary", 100),
                createActionColumn()
        );

        nameField = new TextField();
        nameField.setPromptText("Enter name");
        nameField.setMinWidth(200);

        emailField = new TextField();
        emailField.setPromptText("Enter email");
        emailField.setMinWidth(200);

        positionField = new TextField();
        positionField.setPromptText("Enter position");
        positionField.setMinWidth(200);

        salaryField = new TextField();
        salaryField.setPromptText("Enter salary");
        salaryField.setMinWidth(200);

        addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addButton.setMinWidth(120);
        addButton.setOnAction(e -> addEmployee());

        cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        cancelButton.setMinWidth(120);
        cancelButton.setOnAction(e -> clearFields());

        HBox buttonBox = new HBox(10, addButton, cancelButton);

        VBox formLayout = new VBox(15,
                createFormRow("Name:", nameField),
                createFormRow("Email:", emailField),
                createFormRow("Position:", positionField),
                createFormRow("Salary:", salaryField),
                buttonBox);
        formLayout.setPadding(new Insets(25));
        formLayout.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; -fx-border-width: 1px;");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(table);
        mainLayout.setRight(formLayout);
        mainLayout.setPadding(new Insets(25));
        mainLayout.setStyle("-fx-background-color: white;");
        BorderPane.setMargin(table, new Insets(0, 25, 0, 0));

        Scene scene = new Scene(mainLayout, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private HBox createFormRow(String labelText, TextField textField) {
        Label label = new Label(labelText);
        label.setMinWidth(100);
        HBox row = new HBox(10, label, textField);
        row.setStyle("-fx-alignment: center-left;");
        return row;
    }

    private TableColumn<Employee, String> createColumn(String title, String property, int width) {
        TableColumn<Employee, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setMinWidth(width);
        return column;
    }

    private TableColumn<Employee, Void> createActionColumn() {
        TableColumn<Employee, Void> col = new TableColumn<>("Actions");
        col.setCellFactory(param -> new TableCell<>() {
            final Button editButton = new Button("Edit");
            final Button deleteButton = new Button("Delete");

            {
                editButton.setStyle("-fx-background-color: yellow;");
                deleteButton.setStyle("-fx-background-color: red;");
                editButton.setOnAction(e -> editEmployee(getIndex()));
                deleteButton.setOnAction(e -> deleteEmployee(getIndex()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actions = new HBox(5, editButton, deleteButton);
                    setGraphic(actions);
                }
            }
        });
        return col;
    }

    private void loadTableData() {
        try {
            table.setItems(getEmployeeList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Employee> getEmployeeList() throws SQLException {
        List<Employee> employees = repository.getAllEmployees();
        return FXCollections.observableArrayList(employees);
    }

    private void addEmployee() {
        Employee emp = new Developer(
                nameField.getText(),
                emailField.getText(),
                positionField.getText(),
                Double.parseDouble(salaryField.getText())
        );
        try {
            repository.addEmployee(emp);
            table.getItems().add(emp);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editEmployee(int index) {
        Employee selectedEmployee = table.getItems().get(index);
        nameField.setText(selectedEmployee.getName());
        emailField.setText(selectedEmployee.getEmail());
        positionField.setText(selectedEmployee.getPosition());
        salaryField.setText(String.valueOf(selectedEmployee.getSalary()));

        addButton.setText("Update");
        addButton.setOnAction(e -> {
            selectedEmployee.setName(nameField.getText());
            selectedEmployee.setEmail(emailField.getText());
            selectedEmployee.setPosition(positionField.getText());
            selectedEmployee.setSalary(Double.parseDouble(salaryField.getText()));
            try {
                repository.updateEmployee(selectedEmployee);
                table.refresh();
                clearFields();
                addButton.setText("Add");
                addButton.setOnAction(event -> addEmployee());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void deleteEmployee(int index) {
        Employee selectedEmployee = table.getItems().get(index);
        try {
            repository.deleteEmployee(selectedEmployee.getId());
            table.getItems().remove(index);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        positionField.clear();
        salaryField.clear();
        addButton.setText("Add");
        addButton.setOnAction(event -> addEmployee());
    }
}
