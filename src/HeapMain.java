import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class HeapMain extends Application {
	public static void main(String[] rocket) {
		launch(rocket);
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		root.setPrefSize(800, 600);

		VBox questionPane = new VBox();
		questionPane.setPrefSize(800, 200);
		questionPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		//questionPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
		root.setBottom(questionPane);

		VBox centerPane = new VBox();
		centerPane.setPrefSize(600, 400);
		centerPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		//centerPane.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		root.setCenter(centerPane);

		VBox rightPane = new VBox();
		rightPane.setPrefSize(200, 400);
		rightPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		//rightPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		root.setRight(rightPane);

		Scene s = new Scene(root);
		primaryStage.setScene(s);
		primaryStage.show();
	 }
}