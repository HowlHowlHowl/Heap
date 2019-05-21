import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LessonPane extends VBox {
	String[] heapLessonStrings = {
			"Hello", "hi", "wadup"	
	};
		
	String[] heapsortLessonStrings = {
		"sorty", "sort", "sortsort"
	};
		
	Label lessonTitle;
	String[] lessonParagraphs;
	int paragraphIndex;
	
	Button prevButton;
	Button nextButton;
	Label navLabel;
	Label paragraph;
	
	EventHandler<ActionEvent> onLessonFinished;
	
	LessonPane(double width, double height, EventHandler<ActionEvent> onLessonFinished, EventHandler<ActionEvent> onReturnToMenu) {
		super();
		setMinSize(width, height);
		setMaxWidth(width);
		setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		setSpacing(30);
		setPadding(new Insets(10));
		setAlignment(Pos.TOP_LEFT);
		
		HBox topBar = new HBox();
		getChildren().add(topBar);
		topBar.setSpacing(60);
		
		lessonTitle = new Label();
		topBar.getChildren().add(lessonTitle);
		lessonTitle.setFont(Font.font("courier", 25));
		
		Button returnToMenu = new Button("Menu");
		topBar.getChildren().add(returnToMenu);
		returnToMenu.setOnAction(onReturnToMenu);
		onReturnToMenu.handle(new ActionEvent());
		
		paragraph = new Label("Hello from the other side wooooooooooo this is such a long messagerino bro what ya gonna do bout that");
		getChildren().add(paragraph);
		paragraph.setFont(Font.font(16));
		paragraph.setWrapText(true);
		paragraph.setMinHeight(400);
		paragraph.setAlignment(Pos.TOP_LEFT);
		
		HBox navBox = new HBox();
		getChildren().add(navBox);
		
		prevButton = new Button("<< Prev");
		navBox.getChildren().add(prevButton);
		
		HBox navLabelPane = new HBox();
		navBox.getChildren().add(navLabelPane);
		navLabelPane.setMinWidth(200);
		navLabelPane.setAlignment(Pos.CENTER);
		
		navLabel = new Label();
		navLabelPane.getChildren().add(navLabel);
		
		nextButton = new Button("Next >>");
		navBox.getChildren().add(nextButton);

		prevButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setParagraphIndex(paragraphIndex - 1);
			}
		});
		
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setParagraphIndex(paragraphIndex + 1);
			}
		});
		
		this.onLessonFinished = onLessonFinished;
		/*
		Button lessonFinished = new Button("Lesson finished");
		getChildren().add(lessonFinished);
		lessonFinished.setOnAction(onLessonFinished);
		*/

	}
	
	void setLesson(Lesson lesson) {
		String string = "";
		switch(lesson) {
		case HEAP:
			string = "Struttura dati heap:";
			lessonParagraphs = heapLessonStrings;
		break;
		case HEAPSORT:
			string = "Algoritmo heapsort:";
			lessonParagraphs = heapsortLessonStrings;
		}
		lessonTitle.setText(string);
		setParagraphIndex(0);
	}
	
	void setParagraphIndex(int i) {
		if(i >= 0 && i < lessonParagraphs.length) {
			prevButton.setDisable(i == 0);
			nextButton.setDisable(i == lessonParagraphs.length - 1);
			navLabel.setText((i + 1) + " / " + lessonParagraphs.length);
			paragraph.setText(lessonParagraphs[i]);
			
			paragraphIndex = i;
			if(paragraphIndex == lessonParagraphs.length - 1) {
				onLessonFinished.handle(new ActionEvent());
			}
		}
	}
}
