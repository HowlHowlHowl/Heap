import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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

	Lesson currentLesson = Lesson.HEAP;
	Scene menuScene;
	Scene lessonScene;
	
	public void setLesson(Lesson lesson) {
	}
	
	@Override
	public void start(Stage primaryStage) {
		Path currentRelativePath = Paths.get("");
		String pathString = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + pathString);

		BorderPane root = new BorderPane();

		HeapPane centerPane = new HeapPane(900, 550);
		root.setCenter(centerPane);

		QuestionPane questionPane = new QuestionPane(900,150);
		root.setBottom(questionPane);

		VBox rightPane = new VBox();
		rightPane.setPrefSize(300, 550);
		rightPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		Label lessonLabel = new Label("HEAP");
		lessonLabel.setFont(Font.font(20));
		rightPane.getChildren().add(lessonLabel);
		Button changeLessonButton = new Button("Change lesson");
		changeLessonButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(currentLesson == Lesson.HEAP) {
					currentLesson = Lesson.HEAPSORT;
				} else {
					currentLesson = Lesson.HEAP;
				}
				lessonLabel.setText(currentLesson.toString());
				centerPane.setLesson(currentLesson);
				questionPane.setLesson(currentLesson);
				questionPane.disableQuestions();
			}
		});
		rightPane.getChildren().add(changeLessonButton);
		Button lessonFinished = new Button("Lesson finished");
		lessonFinished.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				questionPane.enableQuestions();
			}
		});
		
		rightPane.getChildren().add(lessonFinished);
		root.setRight(rightPane);

		//Menu
		VBox menuRoot = new VBox();
		menuRoot.setMinSize(1200, 700);
		Button heapLesson = new Button("Lesson Heap");
		menuRoot.getChildren().add(heapLesson);
		heapLesson.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentLesson = Lesson.HEAP;
				lessonLabel.setText(currentLesson.toString());
				centerPane.setLesson(currentLesson);
				questionPane.setLesson(currentLesson);
				questionPane.disableQuestions();
				primaryStage.setScene(lessonScene);
			}
		});
		
		Button heapsortLesson = new Button("Lesson Heapsort");
		menuRoot.getChildren().add(heapsortLesson);
		heapsortLesson.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentLesson = Lesson.HEAPSORT;
				lessonLabel.setText(currentLesson.toString());
				centerPane.setLesson(currentLesson);
				questionPane.setLesson(currentLesson);
				questionPane.disableQuestions();
				primaryStage.setScene(lessonScene);
			}
		});
		
		
		EventHandler<KeyEvent> ExitOnEsc = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
			    if(t.getCode()==KeyCode.ESCAPE){
			    	Stage sb = (Stage)root.getScene().getWindow();
			    	sb.close();
			    }
			}
		};
		lessonScene = new Scene(root);
		lessonScene.addEventHandler(KeyEvent.KEY_PRESSED, ExitOnEsc);
		menuScene = new Scene(menuRoot);
		menuScene.addEventHandler(KeyEvent.KEY_PRESSED, ExitOnEsc);

		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Heap AlgaT");
		primaryStage.show();
	 }
}