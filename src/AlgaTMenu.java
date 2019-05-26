
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class AlgaTMenu extends VBox {
	AlgaTMenu(double width, double height, EventHandler<ActionEvent> onOpenHeap, EventHandler<ActionEvent> onOpenHeapsort) {
		super();
		setPadding(new Insets(100,0,0,0));
		setAlignment(Pos.TOP_CENTER);
		setMinSize(1200, 700);
		Label title1 = new Label("AlgaT");
		getChildren().add(title1);
		title1.setFont(Font.font("courier", FontWeight.SEMI_BOLD, 120));
		title1.setTextFill(Color.DARKGREEN);
		
		Label title2 = new Label("Heap e Heapsort");
		getChildren().add(title2);
		title2.setFont(Font.font("courier", FontWeight.SEMI_BOLD, 85));
		title2.setTextFill(Color.DARKGREEN);
		
		Button heapLesson = new Button("Struttura dati Heap");
		heapLesson.setOnAction(onOpenHeap);
		heapLesson.setMinWidth(200);
		heapLesson.setFont(Font.font("courier", 20));
		
		Button heapsortLesson = new Button("Algoritmo Heapsort");
		heapsortLesson.setOnAction(onOpenHeapsort);
		heapsortLesson.setMinWidth(200);
		heapsortLesson.setFont(Font.font("courier", 20));
		
		VBox buttonBox = new VBox ();
		getChildren().add(buttonBox);
		buttonBox.getChildren().addAll(heapLesson, heapsortLesson);
		buttonBox.setSpacing(10);
		buttonBox.setAlignment(Pos.CENTER);
		
		Label credits = new Label ("Made by:\n "
				+ "Dario Mylonopoulos\n"
				+ "Gabriele Fogu");
		credits.setFont(Font.font("courier", FontPosture.ITALIC, 15));
		credits.setTextAlignment(TextAlignment.CENTER);
		credits.setPadding(new Insets(100,0,0,0));
		credits.setOpacity(0.7);
		getChildren().add(credits);
	}
}
