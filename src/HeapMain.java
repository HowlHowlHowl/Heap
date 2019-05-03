import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

		VBox questionPane = new VBox();
		questionPane.setPrefSize(1200, 200);
		questionPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		root.setBottom(questionPane);
	
		HeapPane centerPane = new HeapPane(900, 700);
		root.setCenter(centerPane);
		centerPane.drawHeap();

		

		VBox rightPane = new VBox();
		rightPane.setPrefSize(300, 700);
		rightPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		root.setRight(rightPane);

		Scene s = new Scene(root);
		primaryStage.setScene(s);
		primaryStage.setTitle("Heap AlgaT");
		primaryStage.show();
	 }
}