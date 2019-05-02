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

	public int intPow(int a, int b) {
		int result = 1;
		while(b > 0) {
			result *= a;
			b--;
		}

		return result;
	}

	public int intLog2(int a) {
		System.out.print("Log2 of " + a);
		int result = 0;
		while(a > 1) {
			a = a >> 1;
			result++;
		}
		System.out.println(": " + result);

		return result;
	}

	@Override
	public void start(Stage primaryStage) {



		BorderPane root = new BorderPane();

		VBox questionPane = new VBox();
		questionPane.setPrefSize(1200, 200);
		questionPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		root.setBottom(questionPane);

		int centerWidth = 900;
		Pane centerPane = new Pane();
		centerPane.setMinSize(centerWidth, 700);
		centerPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		root.setCenter(centerPane);

		//Genera un heap random
		Heap heap = new Heap();
		Random random = new Random();
		random.setSeed(3);
		for(int i = 0; i < 31; i++ ){
			heap.insert(Math.abs(random.nextInt(100)));
		}

		//Disegno dell'heap
		int childNumber = 0;
		int depth = 0;
		ArrayList<Integer> heapArray = heap.getArray();
		for(int i = 0; i < heapArray.size(); i++) {
			int newDepth = intLog2(i + 1);
			if(newDepth > depth) {
				depth = newDepth;
				childNumber = 0;
			}

			int numChildrenAtDepth = intPow(2, depth);
			double childSpacing = (double)centerWidth / (double)numChildrenAtDepth;
			double x = childSpacing / 2 + childNumber * childSpacing;
			double y = depth* 100 + 100;

			System.out.println("x: " + x + " | " + y);
			Circle rect = new Circle(x, y, 25);
			rect.setFill(Color.RED);
			centerPane.getChildren().add(rect);

			//-12 e + 6 sono belli hardcoded perche' i geni che hanno fatto JavaFx non hanno ancora
			// scoperto che magari uno il testo lo vuole centrato, PORCA MADONNA
			Text nodeText = new Text(x - 12, y + 6, heapArray.get(i).toString());
			nodeText.setFont(Font.font(20));
			centerPane.getChildren().add(nodeText);

			childNumber++;
		}


		VBox rightPane = new VBox();
		rightPane.setPrefSize(300, 700);
		rightPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		root.setRight(rightPane);

		Scene s = new Scene(root);
		primaryStage.setScene(s);
		primaryStage.show();
	 }
}