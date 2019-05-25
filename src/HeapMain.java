import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HeapMain extends Application {
	public static void main(String[] rocket) {
		launch(rocket);
	}

	Lesson currentLesson = Lesson.HEAP;
	Scene menuScene;
	Scene lessonScene;

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

		EventHandler<ActionEvent> onLessonFinished = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				questionPane.enableQuestions();
			}
		};
		
		EventHandler<ActionEvent> onReturnToMenu = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(menuScene);
			}
		};
		
		LessonPane lessonPane = new LessonPane(350, 550, onLessonFinished, onReturnToMenu);
		root.setLeft(lessonPane);

		//Menu
		EventHandler<ActionEvent> onOpenHeap = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentLesson = Lesson.HEAP;
				lessonPane.setLesson(currentLesson);
				centerPane.setLesson(currentLesson);
				questionPane.setLesson(currentLesson);
				questionPane.disableQuestions();
				primaryStage.setScene(lessonScene);
			}
		};
		
		EventHandler<ActionEvent> onOpenHeapsort = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentLesson = Lesson.HEAPSORT;
				lessonPane.setLesson(currentLesson);
				centerPane.setLesson(currentLesson);
				questionPane.setLesson(currentLesson);
				questionPane.disableQuestions();
				primaryStage.setScene(lessonScene);
			}
		};
		
		AlgaTMenu menu = new AlgaTMenu(1200, 700, onOpenHeap, onOpenHeapsort);
		


		final EventHandler<KeyEvent> ExitOnEsc = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
			    if(t.getCode()==KeyCode.ESCAPE){
			    	primaryStage.close();
			    }
			}
		};
		lessonScene = new Scene(root);
		lessonScene.addEventHandler(KeyEvent.KEY_PRESSED, ExitOnEsc);
		menuScene = new Scene(menu);
		menuScene.addEventHandler(KeyEvent.KEY_PRESSED, ExitOnEsc);

		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Heap AlgaT");
		primaryStage.show();
	 }
}