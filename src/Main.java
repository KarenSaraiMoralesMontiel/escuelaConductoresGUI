import gov.peru.escuela.conducir.bean.Escuela;
import gov.peru.escuela.conducir.persistencia.EscuelaDBImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private TableView<Escuela> tableView;
    private ObservableList<Escuela> escuelaData;
    private EscuelaDBImpl escuelaDB;

    @Override
    public void start(Stage primaryStage) {
        escuelaDB = new EscuelaDBImpl();
        escuelaData = FXCollections.observableArrayList();

        // Create the main layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Create title and button section
        VBox topSection = createTopSection();
        root.setTop(topSection);

        // Create table
        tableView = createTableView();
        root.setCenter(tableView);

        // Create bottom section with status
        Label statusLabel = new Label("Total de establecimientos: 0");
        statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        root.setBottom(statusLabel);

        // Load initial data
        loadData();
        statusLabel.setText("Total de establecimientos: " + escuelaData.size());

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Sistema de Escuelas de Conductores - Perú");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private VBox createTopSection() {
        VBox topSection = new VBox(15);
        topSection.setPadding(new Insets(10));
        topSection.setAlignment(Pos.CENTER);

        // Title
        Label title = new Label("ESCUELAS DE CONDUCTORES REGISTRADAS");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button btnAdd = createButton("Nueva Escuela", "#28a745");
        Button btnRefresh = createButton("Actualizar Datos", "#17a2b8");

        btnAdd.setOnAction(e -> showAddForm());
        btnRefresh.setOnAction(e -> refreshData());

        buttonBox.getChildren().addAll(btnAdd, btnRefresh);

        topSection.getChildren().addAll(title, buttonBox);
        return topSection;
    }

    private TableView<Escuela> createTableView() {
        TableView<Escuela> table = new TableView<>();
        table.setPlaceholder(new Label("No hay establecimientos registrados"));

        // Create columns
        TableColumn<Escuela, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id_establecimiento"));
        idCol.setPrefWidth(60);

        TableColumn rucCol = new TableColumn<>("RUC");
        rucCol.setCellValueFactory(new PropertyValueFactory<>("no_ruc"));
        rucCol.setPrefWidth(120);

        TableColumn nombreCol = new TableColumn<>("Nombre Centro");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre_centro"));
        nombreCol.setPrefWidth(200);

        TableColumn direccionCol = new TableColumn<>("Dirección");
        direccionCol.setCellValueFactory(new PropertyValueFactory<>("direccion_centro"));
        direccionCol.setPrefWidth(250);

        TableColumn escuelaDatos = new TableColumn("Datos de escuela");
        escuelaDatos.getColumns().addAll(rucCol,nombreCol,direccionCol);

        TableColumn departamentoCol = new TableColumn<>("Departamento");
        departamentoCol.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        departamentoCol.setPrefWidth(120);

        TableColumn provinciaCol = new TableColumn<>("Provincia");
        provinciaCol.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        provinciaCol.setPrefWidth(120);

        TableColumn distritoCol = new TableColumn<>("Distrito");
        distritoCol.setCellValueFactory(new PropertyValueFactory<>("distrito"));
        distritoCol.setPrefWidth(120);

        TableColumn ubigeoCol = new TableColumn("Ubigeo");
        ubigeoCol.getColumns().addAll(departamentoCol, provinciaCol, distritoCol);


        TableColumn emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(150);

        TableColumn phoneCol = new TableColumn<>("Teléfono");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setPrefWidth(120);

        TableColumn contactoCol = new TableColumn("Contacto");
        contactoCol.getColumns().addAll(emailCol, phoneCol);

        TableColumn estadoCol = new TableColumn("Estado");
        estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado_autorizacion"));
        estadoCol.setPrefWidth(80);
        estadoCol.setCellFactory(col -> new TableCell<Escuela, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "CON AUTORIZACIÓN" : "SIN AUTORIZACIÓN");
                    setStyle(item ? "-fx-text-fill: green; -fx-font-weight: bold;" : "-fx-text-fill: red;");
                }
            }
        });

        table.getColumns().addAll(idCol, ubigeoCol,escuelaDatos,contactoCol, estadoCol);

        table.setItems(escuelaData);
        return table;
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setPrefSize(180, 40);
        button.setStyle(String.format(

                "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 5;",
                color
                ));
        return button;
    }

    private void showAddForm() {
        Stage formStage = new Stage();
        formStage.setTitle("Registro de Nueva Escuela");

        try {
            // Create the add form programmatically
            VBox form = createAddForm(formStage);
            Scene scene = new Scene(form, 500, 600);
            formStage.setScene(scene);
            formStage.centerOnScreen();
            formStage.showAndWait(); // This will wait until the form is closed

            // Refresh data after form is closed
            refreshData();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo abrir el formulario: " + e.getMessage());
        }
    }

    private VBox createAddForm(Stage stage) {
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setStyle("-fx-background-color: #f8f9fa;");

        // Title
        Label title = new Label("NUEVA ESCUELA");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Form container
        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(25));
        formContainer.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 10;");

        // Grid for form fields
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));

        // Create form fields
        TextField txtRuc = createTextField();
        TextField txtNombre = createTextField();
        TextField txtDireccion = createTextField();
        TextField txtEmail = createTextField();
        TextField txtPhone = createTextField();
        TextField txtIdDistrito = createTextField();
        CheckBox chkEstado = new CheckBox("Autorizado");
        chkEstado.setSelected(true);

        // Add fields to grid
        addFormField(grid, "RUC *", txtRuc, 0);
        addFormField(grid, "Nombre Centro *", txtNombre, 1);
        addFormField(grid, "Dirección Centro *", txtDireccion, 2);
        addFormField(grid, "Email Contacto", txtEmail, 3);
        addFormField(grid, "Teléfono Contacto", txtPhone, 4);
        addFormField(grid, "ID Distrito *", txtIdDistrito, 5);

        Label lblEstado = new Label("Estado Autorización:");
        lblEstado.setStyle("-fx-font-weight: bold;");
        grid.add(lblEstado, 0, 6);
        grid.add(chkEstado, 1, 6);

        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button btnSave = createButton("GUARDAR", "#28a745");
        Button btnCancel = createButton("CANCELAR", "#6c757d");

        btnSave.setOnAction(e -> {
            if (saveEstablishment(txtRuc, txtNombre, txtDireccion, txtEmail, txtPhone, txtIdDistrito, chkEstado)) {
                stage.close();
            }
        });

        btnCancel.setOnAction(e -> stage.close());

        buttonBox.getChildren().addAll(btnSave, btnCancel);

        // Status label
        Label lblStatus = new Label();
        lblStatus.setStyle("-fx-text-fill: red; -fx-alignment: center;");

        // Add everything to containers
        formContainer.getChildren().addAll(grid, buttonBox, lblStatus);
        mainContainer.getChildren().addAll(title, formContainer);

        return mainContainer;
    }

    private boolean saveEstablishment(TextField txtRuc, TextField txtNombre, TextField txtDireccion,
                                      TextField txtEmail, TextField txtPhone, TextField txtIdDistrito, CheckBox chkEstado) {
        try {
            Escuela escuela = new Escuela();
            escuela.setNo_ruc(txtRuc.getText().trim());
            escuela.setNombre_centro(txtNombre.getText().trim());
            escuela.setDireccion_centro(txtDireccion.getText().trim());
            escuela.setEmail(txtEmail.getText().trim());
            escuela.setPhone(txtPhone.getText().trim());
            escuela.setId_distrito(txtIdDistrito.getText().trim());
            escuela.setEstado_autorizacion(chkEstado.isSelected());

            boolean success = escuelaDB.insertEscuela(escuela);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Establecimiento guardado correctamente");
                return true;
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No se pudo guardar el establecimiento");
                return false;
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error al guardar: " + e.getMessage());
            return false;
        }
    }

    private TextField createTextField() {
        TextField field = new TextField();
        field.setPrefWidth(250);
        field.setPrefHeight(35);
        field.setStyle("-fx-font-size: 14px;");
        return field;
    }

    private void addFormField(GridPane grid, String labelText, TextField field, int row) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(label, 0, row);
        grid.add(field, 1, row);
    }

    private void loadData() {
        try {
            escuelaData.clear();
            escuelaData.addAll(escuelaDB.findAll());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los datos: " + e.getMessage());
        }
    }

    private void refreshData() {
        loadData();
        showAlert(Alert.AlertType.INFORMATION, "Actualizado", "Datos actualizados correctamente");
    }
    

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}