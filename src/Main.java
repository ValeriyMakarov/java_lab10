import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage primary_stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Система управления данными об абитуриентах");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(550);
        primaryStage.setScene(new Scene(root, 890, 600));
        primaryStage.show();
    }
    public void setPrimaryStage(Stage stage){
        Main.primary_stage=stage;
    }
    public static Stage getPrimaryStage(){
        return Main.primary_stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
