import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AlgaTMenu extends VBox {
	AlgaTMenu(double width, double height, EventHandler<ActionEvent> onOpenHeap, EventHandler<ActionEvent> onOpenHeapsort) {
		super();
		
		setAlignment(Pos.TOP_CENTER);
		setMinSize(1200, 700);
		
		Label title1 = new Label("AlgaT");
		getChildren().add(title1);
		title1.setFont(Font.font("courier", FontWeight.SEMI_BOLD, 50));
		title1.setTextFill(Color.DARKGREEN);
		
		Label title2 = new Label("Heap e Heapsort");
		getChildren().add(title2);
		title2.setFont(Font.font("courier", FontWeight.SEMI_BOLD, 30));
		title2.setTextFill(Color.DARKGREEN);
		
		Button heapLesson = new Button("Lesson Heap");
		getChildren().add(heapLesson);
		heapLesson.setOnAction(onOpenHeap);
		heapLesson.setMinWidth(200);
		
		Button heapsortLesson = new Button("Lesson Heapsort");
		getChildren().add(heapsortLesson);
		heapsortLesson.setOnAction(onOpenHeapsort);
		heapsortLesson.setMinWidth(200);
		
	}
}
