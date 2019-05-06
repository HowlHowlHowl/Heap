import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class HeapMain extends Application {
	public static void main(String[] rocket) {
		launch(rocket);
	}


	@Override
	public void start(Stage primaryStage) {
		Path currentRelativePath = Paths.get("");
		String pathString = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + pathString);
		
		BorderPane root = new BorderPane();

		HeapPane centerPane = new HeapPane(900, 500);
		root.setCenter(centerPane);
		centerPane.drawHeap();

		QuestionPane questionPane = new QuestionPane(1200,200);
		
		root.setBottom(questionPane);

		VBox rightPane = new VBox();
		rightPane.setPrefSize(300, 700);
		rightPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		root.setRight(rightPane);

		Scene s = new Scene(root);
		s.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
			    if(t.getCode()==KeyCode.ESCAPE){  	
			    	Stage sb = (Stage)root.getScene().getWindow();
			    	sb.close();
			    }
			}
		});
		
		primaryStage.setScene(s);
		primaryStage.setTitle("Heap AlgaT");
		primaryStage.show();
	 }
}