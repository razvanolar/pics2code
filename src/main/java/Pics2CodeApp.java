import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Pics2CodeApp extends Application {

  private Scene scene;

  @Override
  public void init() throws Exception {
    BorderPane pane = new BorderPane();

    scene = new Scene(pane, 450, 300);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setScene(scene);
    primaryStage.setMaximized(true);
    primaryStage.show();
  }

  public static void main(String[] args) {
    try {
      Pic2Code.init(args);
      Pic2Code.getInstance().handle();
//      launch(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
