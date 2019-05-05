import java.nio.file.Path;
import java.nio.file.Paths;

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

		HeapPane centerPane = new HeapPane(900, 700);
		root.setCenter(centerPane);
		centerPane.drawHeap();

		HBox questionPane = new HBox();
		questionPane.setPrefSize(1200, 200);
		questionPane.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		root.setBottom(questionPane);
		
		final Text q1 = new Text("");
		
		q1.setFont(Font.font("courier", 20));
		
		Button prev = new Button("<< Prev");
		prev.setFont(Font.font(15));
		
		Button next = new Button("Next >>");
		next.setFont(Font.font(15));
		
		TilePane prevNextLayout = new TilePane(Orientation.HORIZONTAL);
		prevNextLayout.setHgap(50.0);
		prevNextLayout.getChildren().addAll(prev,next);
		prevNextLayout.setPadding(new Insets(20,40,10,30));
	
		
		final TextField answerField = new TextField();
		final HBox txtSub = new HBox();
		
		VBox dxQuest = new VBox();
		dxQuest.getChildren().addAll(prevNextLayout, q1,answerField,txtSub);
		dxQuest.setPadding(new Insets(0,10,0,0));
		dxQuest.setSpacing(0.0);
		answerField.setPromptText("Write your answere here");
		EventHandler<ActionEvent> answrd =new EventHandler<ActionEvent>(){		
			@Override
			public void handle(ActionEvent e){
				if(answerField.getText() != null && !answerField.getText().isEmpty()){
					String word = "Ok";
					
					if(word.equalsIgnoreCase(answerField.getText())){
						q1.setText("GOOD");
						q1.setFill(Color.GREEN);
						//TODO: add application
						
					}
					else {
						q1.setText("Wrong");
						q1.setFill(Color.RED);
					}
				}
				else{
					answerField.setPromptText("Please type a valid answer");
				}
				
			}
		};
		
		answerField.setOnAction(answrd);
		
		Button submit = new Button("Submit");
		submit.setFont(Font.font(30));
		submit.setOnAction(answrd);
		submit.setMinWidth(txtSub.getPrefWidth());
		submit.setMinHeight(txtSub.getPrefHeight());
		
		txtSub.getChildren().addAll(answerField, submit);
		txtSub.setPrefWidth(100);
		txtSub.setPrefHeight(60);
		txtSub.setSpacing(10.0);
		//Hardcode setSpacing ma bisogna creare un ViewTreeObserver per accedere
		//alle dimensioni dei nodi che vengono disegnati 
		Text Q = new Text("Here is the question...");
		Q.setFont(Font.font("courier", 20));
		
		HBox r1 = new HBox();
		r1.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		r1.getChildren().add(Q);
		r1.setMinWidth(600);
		r1.setMinHeight(150);
		r1.setPadding(new Insets(3,6,3,6));
		r1.setStyle("-fx-background-color: #FFFFFF;");
		answerField.setFont(Font.font("courier",FontWeight.BOLD,30));
		
		VBox sxQuest = new VBox();
		sxQuest.setPadding(new Insets(20,0,0,20));
		sxQuest.getChildren().addAll(r1);
		
		questionPane.getChildren().addAll(sxQuest,dxQuest);
		questionPane.setSpacing(10);
		questionPane.setPadding(new Insets(0,0,0,0));
		

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