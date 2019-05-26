import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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
		//Scena per la lezione
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

		//Scena per il menu
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

		AlgaTMenu menu = new AlgaTMenu(1250, 700, onOpenHeap, onOpenHeapsort);

		lessonScene = new Scene(root);
		menuScene = new Scene(menu);

		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Heap AlgaT");
		primaryStage.show();
	 }
}