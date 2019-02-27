import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AskingWindow {
    private static boolean answer=false;
    private static String label;
    private static byte text_field;
    private static Stage stage;
    private static boolean is_visible_text_field=false;

    private static boolean is_visible_no_button=true;
    private static boolean is_visible_yes_button=true;
    private static boolean is_visible_undo_button=false;

    @FXML private TextField asking_window_text_field;
    @FXML private Label asking_window_label;
    @FXML private Button asking_window_no_button;
    @FXML private Button asking_window_yes_button;
    @FXML private Button asking_window_undo_button;
    @FXML void initialize(){
        asking_window_text_field.setStyle("-fx-focus-color: gray;");
        asking_window_no_button.setStyle("-fx-focus-color: gray;");
        asking_window_yes_button.setStyle("-fx-focus-color: gray;");
        asking_window_undo_button.setStyle("-fx-focus-color: gray;");

        asking_window_label.setText(label);
        asking_window_no_button.setVisible(is_visible_no_button);
        asking_window_yes_button.setVisible(is_visible_yes_button);
        asking_window_undo_button.setVisible(is_visible_undo_button);
        asking_window_text_field.setVisible(is_visible_text_field);

        asking_window_text_field.setPromptText("Введите средний балл");

        asking_window_no_button.setOnAction(event -> {
            setAnswer(false);
            stage.close();
        });
        asking_window_yes_button.setOnAction(event -> {
            setAnswer(true);
            stage.close();
        });
        asking_window_text_field.setOnAction(event -> {
                if (asking_window_text_field.getText().matches("[0-9]+") &&
                    Long.parseLong(asking_window_text_field.getText())<=100 &&
                    Long.parseLong(asking_window_text_field.getText())>0) {
                    AskingWindow.text_field = Byte.parseByte(asking_window_text_field.getText());
                    answer = true;
                    stage.close();
                }
            asking_window_text_field.setText(null);
        });
        asking_window_undo_button.setOnAction(event -> {
            answer = false;
            stage.close();
        });
    }
    private void setAnswer(boolean answer){
        AskingWindow.answer = answer;
    }
    public static boolean getAnswer(){
        return answer;
    }

    public static void setLabel(String label){
        AskingWindow.label = label;
    }
    public static String getLabel(){
        return label;
    }

    public static void setText_field(byte text_field){
        AskingWindow.text_field = text_field;
    }
    public static byte getText_field(){
        return text_field;
    }

    public static void setStage(Stage stage){
        AskingWindow.stage = stage;
    }

    public static void setTextFieldVisible(boolean is_visible){
        AskingWindow.is_visible_text_field = is_visible;
    }

    public static void setNoButtonVisible(boolean is_visible){
        AskingWindow.is_visible_no_button=is_visible;
    }
    public static void setYesButtonVisible(boolean is_visible){
        AskingWindow.is_visible_yes_button=is_visible;
    }
    public static void setUndoButtonVisible(boolean is_visible){
        AskingWindow.is_visible_undo_button=is_visible;
    }
}
