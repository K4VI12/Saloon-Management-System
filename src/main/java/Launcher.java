import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/LoginpageForm.fxml"))));
        stage.setTitle("GSN - Global Saloon Network");
        stage.centerOnScreen();
        stage.show();
        stage.centerOnScreen();

        Image img = new Image("image/GSN.1png.png");
        stage.getIcons().add(img);

    }
}
