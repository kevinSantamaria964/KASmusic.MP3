/*
 * Programa creado por kevin Andres Santamaria
 * Universidad de Cundinamarca, Programacion ll
 */
package KASmusic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class KASmusic extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLoguin.fxml"));
        Scene scene = new Scene(root, 300, 400);
        stage.setTitle("KASMusic - Inicio de sesión");
        stage.getIcons().add(new Image(UPMusic.class.getResourceAsStream("app_icon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
