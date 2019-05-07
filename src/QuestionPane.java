

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class QuestionPane extends HBox {
	private Button prev;
	private Button next;
	private Button submit;
	private ArrayList<String> questions;
	private Integer i=0;
	private ArrayList<String> answers;
	private ArrayList<Boolean> risCor;
	private ArrayList<ToggleButton> buttonList;
	int n=i+1;
	private Boolean touch=false;
	
	
	public final TextField answerField;
	
	
	public QuestionPane(double width, double height){
		super();
		setWidth(width);
		setHeight(height);
		setPrefSize(1200, 200);
		setMinSize(width, height);
		setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		Text Q = new Text("Here is the question...");
		Q.setFont(Font.font("courier", 20));
		final Text q1 = new Text("Click anywhere.");
		
		
		q1.setFont(Font.font("courier", 20));
		//Prev e Next Scene
		prev = new Button("<< Prev");
		prev.setFont(Font.font(15));
	
		EventHandler<ActionEvent> prevQuestion = new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent e){
				if(touch==true){
					if(i>0){
						next.setDisable(false);
						i--;
						n--;
						Q.setText(questions.get(i));
						q1.setText("Domanda n°"+n);
						q1.setFill(Color.BLACK);
					}
					else {prev.setDisable(true);}
				}
			}
		};
		
		prev.setOnAction(prevQuestion);
		
		
		next = new Button("Next >>");
		next.setFont(Font.font(15));
		EventHandler<ActionEvent> nextQuestion = new EventHandler<ActionEvent>(){
			@Override 
			public void handle(ActionEvent e){
				if(touch==true){
					if(i<questions.size()-1 && i<answers.size()-1){
					prev.setDisable(false);
					i++;	
					n++;
					Q.setText(questions.get(i));
					q1.setText("Domanda n°"+n);
					q1.setFill(Color.BLACK);
					}
					else {
						next.setDisable(true);
					}
				} 
			}
		};
		next.setOnAction(nextQuestion);
		
		
		TilePane prevNextLayout = new TilePane(Orientation.HORIZONTAL);
		prevNextLayout.setHgap(50.0);
		prevNextLayout.getChildren().addAll(prev,next);
		prevNextLayout.setPadding(new Insets(20,40,10,30));
	
		
		answerField = new TextField();
		final HBox txtSub = new HBox();
		
		VBox dxQuest = new VBox();
		dxQuest.getChildren().addAll(prevNextLayout,q1,answerField,txtSub);
		dxQuest.setPadding(new Insets(0,10,0,10));
		dxQuest.setSpacing(0.0);
		answerField.setPromptText("Write your answere here");
		
		submit = new Button("Submit");
		submit.setFont(Font.font(30));
		
		submit.setMinWidth(txtSub.getPrefWidth());
		submit.setMinHeight(txtSub.getPrefHeight());
		
		txtSub.getChildren().addAll(answerField, submit);
		txtSub.setPrefWidth(100);
		txtSub.setPrefHeight(60);
		txtSub.setSpacing(10.0);
		//Hardcode setSpacing ma bisogna creare un ViewTreeObserver per accedere
		//alle dimensioni dei nodi che vengono disegnati 
		
		
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
		
		getChildren().addAll(sxQuest,dxQuest);
		setPadding(new Insets(0,0,0,0));
		
		//read questions
		
		Scanner jerry = null;
		
		try {
			File file = new File("src/","quest.txt");
			jerry = new Scanner(file);
		} catch(Exception e1){
			System.out.println("Exception sul file");
			System.exit(1);
		}
		
		jerry.useDelimiter("[?.]");
		questions = new ArrayList<String>();
		answers =new ArrayList<String>();
		risCor = new ArrayList<Boolean>();
		buttonList =new ArrayList<ToggleButton>();
		while(jerry.hasNext()){
			questions.add(jerry.next().trim());
			if(jerry.hasNext())
			answers.add(jerry.next());
			risCor.add(false);
		
		}
		jerry.close();
		HBox enume = new HBox();
		enume.setPadding(new Insets (5,0,0,0));
		int j=0;
		ToggleGroup domande = new ToggleGroup();
		
		while(questions.size()>j+1){
			 ToggleButton choice = new ToggleButton(""+(j+1));
			 choice.setToggleGroup(domande);
			 buttonList.add(choice);
			 choice.setOnAction(new EventHandler<ActionEvent>(){
				 @Override
				 public void handle(ActionEvent e){
					if(touch==true){ 	
						i=Integer.parseInt(choice.getText().trim())-1;
						n=i+1;
						Q.setText(questions.get(i));
						q1.setText("Domanda n°"+n);
						q1.setFill(Color.BLACK);
						if(i==0){
							prev.setDisable(true);
							next.setDisable(false);
						}
						if(i==answers.size()-1||i==questions.size()-1){
							prev.setDisable(false);
							next.setDisable(true);
						}
							
					}
					else
						choice.setSelected(false);
				 }
			 });
			 enume.getChildren().add(choice);
			j++;
		}
		dxQuest.getChildren().add(enume);
		EventHandler<ActionEvent> answrd =new EventHandler<ActionEvent>(){		
			@Override
			public void handle(ActionEvent e){
			
					if(answerField.getText() != null && !answerField.getText().isEmpty()){
					String A = new String(answers.get(i).trim());
					if(A.equalsIgnoreCase(answerField.getText())){
						risCor.set(i, true);
						q1.setText("Right");
						q1.setFill(Color.GREEN);
						buttonList.get(i).setTextFill(Color.LIMEGREEN);
						Boolean T=true;
						int k=0;
						while(risCor.size()>k+1){
							if(risCor.get(k).equals(false))
								T = false;
							k++;
						}
						if(T.booleanValue()==true){
							Q.setFont(Font.font("cancun", 30));
							Q.setFill(Color.LIMEGREEN);
							Q.setStrokeWidth(0.5);
							Q.setStroke(Color.BLACK);
							Q.setText( "You answered correctly to all the questions."+"\n"+"Congratulation!");
						}
					}
					else {
						if(risCor.get(i)!=true){
							q1.setText("Wrong");
							q1.setFill(Color.RED);
							buttonList.get(i).setTextFill(Color.RED);;
						}
					}
				}
				else{
					answerField.setPromptText("Please type a valid answer");
				}
				
			}
		};
		submit.setOnAction(answrd);
		answerField.setOnAction(answrd);
		EventHandler<MouseEvent> getReady = new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e){
				if(touch==false){
					enume.setDisable(false);
					touch=true;
					q1.setText("Domanda n°"+n);
					q1.setFill(Color.BLACK);
					Q.setText(questions.get(i));
					prev.setDisable(true);
				}
			}
		};
		addEventHandler(MouseEvent.MOUSE_CLICKED,getReady);
		addEventFilter(MouseEvent.MOUSE_CLICKED,getReady);
		
		if(touch==false)
			enume.setDisable(true);
		
	
	
	}
}