package JavaFX;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

import static modelo.DataAcces.*;

public class menuBD extends Application {
    // PreparedStatement for executing queries
    private PreparedStatement preparedStatement;
    private TextField tfUsuario = new TextField();
    private TextField tfPassword = new TextField();
    private Label lblStatus = new Label();

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Initialize database connection and create a Statement object


        Button btShowGrade = new Button("Iniciar sesion");
        HBox hBox = new HBox(5);
        HBox butonBox = new HBox(5);
        hBox.getChildren().addAll(new Label("Usuario"), tfUsuario,
                new Label("Contraseña"), tfPassword, (btShowGrade));
        hBox.setAlignment(Pos.CENTER);
        butonBox.getChildren().addAll(btShowGrade);
        butonBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(hBox, butonBox, lblStatus);

        tfUsuario.setPrefColumnCount(6);
        tfPassword.setPrefColumnCount(6);
        btShowGrade.setOnAction(e -> usuarioValido(tfUsuario.getText(),tfPassword.getText())); //validar usuario

        // Create a scene and place it in the stage
        Scene scene = new Scene(vBox, 420, 120);
        vBox.setAlignment(Pos.CENTER);
        primaryStage.setTitle("Registro, inserte sus datos"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /*
    private void showGrade() {
        String usuario = tfUsuario.getText();
        String password = tfPassword.getText();
        try {
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, password);
            ResultSet rset = preparedStatement.executeQuery();

            if (rset.next()) {
                String lastName = rset.getString(1);
                String mi = rset.getString(2);
                String firstName = rset.getString(3);
                String title = rset.getString(4);
                String grade = rset.getString(5);

                // Display result in a label
                lblStatus.setText(firstName + " " + mi +
                        " " + lastName + "'s grade on course " + title + " is " +
                        grade);
            } else {
                lblStatus.setText("Not found");
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }*/

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
