import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
	private ArrayList<String> answers;
	private ArrayList<String> questions;
	private ArrayList<Boolean> risCor;
	
	private HBox toggleLayout;
	private ArrayList<ToggleButton> buttonList;
	private Button prev;
	private Button next;
	private Button submit;
	
	private Label question;
	private Text questionResult;
	public TextField answerField;
	
	public boolean isActive;
	private Integer questionsIndex = 0;
	private Integer activeIndex = 0;
	

	public QuestionPane(double width, double height){
		super();
		setWidth(width);
		setHeight(height);
		setSpacing(90);
		setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		question = new Label("");
		question.setFont(Font.font("courier", 20));
		question.setMinWidth(600);
		question.setMaxWidth(600);
		question.setMinHeight(150);
		question.setWrapText(true);

		questionResult = new Text("");
		questionResult.setFont(Font.font("courier", 20));
		//Prev e Next Scene
		prev = new Button("<< Prev");
		prev.setFont(Font.font(15));

		EventHandler<ActionEvent> prevQuestion = new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
					setActiveQuestion(activeIndex - 1);
			}
		};

		prev.setOnAction(prevQuestion);


		next = new Button("Next >>");
		next.setFont(Font.font(15));
		EventHandler<ActionEvent> nextQuestion = new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				setActiveQuestion(activeIndex + 1);
			}
		};
		next.setOnAction(nextQuestion);


		TilePane prevNextLayout = new TilePane(Orientation.HORIZONTAL);
		prevNextLayout.setHgap(50.0);
		prevNextLayout.getChildren().addAll(prev, next);
		prevNextLayout.setPadding(new Insets(20,20,0,30));


		answerField = new TextField();
		answerField.setFont(Font.font("courier", FontWeight.BOLD,30));
		final HBox txtSub = new HBox();

		VBox dxQuest = new VBox();
		dxQuest.getChildren().addAll(prevNextLayout, questionResult, answerField, txtSub);
		dxQuest.setPadding(new Insets(0,10,0,20));
		dxQuest.setSpacing(0.0);
		answerField.setPromptText("Rispondi qui");

		toggleLayout = new HBox();
		toggleLayout.setPadding(new Insets (5,0,15,0));
		dxQuest.getChildren().add(toggleLayout);

		
		submit = new Button("Submit");
		submit.setFont(Font.font(30));
		submit.setMinWidth(txtSub.getPrefWidth());
		submit.setMinHeight(txtSub.getPrefHeight());

		txtSub.getChildren().addAll(answerField, submit);
		txtSub.setPrefWidth(100);
		txtSub.setPrefHeight(20);
		txtSub.setSpacing(10.0);
		
		VBox sxQuest = new VBox();
		sxQuest.setPadding(new Insets(20,0,0,20));
		sxQuest.getChildren().addAll(question);

		getChildren().addAll(sxQuest,dxQuest);
		setPadding(new Insets(0,0,0,0));

		setLesson(Lesson.HEAP);

		EventHandler<ActionEvent> answrd =new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
				if(answerField.getText() != null && !answerField.getText().isEmpty()) {
					String A = new String(answers.get(activeIndex).replaceAll("\\s", ""));
					String B = new String (answerField.getText().replaceAll("\\s",""));
					if(A.equalsIgnoreCase(B)) {
						questionsIndex++;
						risCor.set(activeIndex, true);
						questionResult.setText("Right");
						questionResult.setFill(Color.GREEN);
						buttonList.get(activeIndex).setTextFill(Color.LIMEGREEN);
						if(risCor.get(answers.size()-1)==true)
							question.setText( "Congratulazioni hai risposto a tutte le domande correttamente! Torna al menu per scegliere un altra lezione!");
					}
					else if (risCor.get(activeIndex)!=true){
						questionResult.setText("Wrong");
						questionResult.setFill(Color.RED);
						buttonList.get(activeIndex).setTextFill(Color.RED);;
					}
				}
				else{
					answerField.setPromptText("Please type a valid answer");
				}
			}
		};
		submit.setOnAction(answrd);
		answerField.setOnAction(answrd);
		toggleLayout.setDisable(true);

		disableQuestions();
	}

	void readQuestions(Lesson lesson) {
		String fileName = "questions_" + lesson.toString() + ".txt";
		//Leggi domande
		Scanner jerry = null;
		try {
			File file = new File(fileName);
			jerry = new Scanner(file);
		} catch(Exception e1){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("AlgaT Error");
			alert.setHeaderText("File error");
			alert.setContentText("Impossibile aprire il file con le domande!");
			alert.showAndWait();
			System.exit(1);
		}

		jerry.useDelimiter("[|.]");
		questions = new ArrayList<String>();
		answers = new ArrayList<String>();
		risCor = new ArrayList<Boolean>();
		buttonList =new ArrayList<ToggleButton>();
		while(jerry.hasNext()){
			String qString = jerry.next().trim();
			if(qString.isEmpty()) {
				break;
			}
			questions.add(qString);
			if(jerry.hasNext()) {
				answers.add(jerry.next());
				risCor.add(false);
			}
		}
		jerry.close();
		
		if(answers.size() != questions.size()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("AlgaT Error");
			alert.setHeaderText("File error");
			alert.setContentText("Il file con le domande non è corretto!");
			alert.showAndWait();
			System.exit(1);
		}
		
		toggleLayout.getChildren().clear();
		ToggleGroup domande = new ToggleGroup();
		for(int j = 0; j < answers.size(); j++) {
			 ToggleButton choice = new ToggleButton(""+(j+1));
			 choice.setToggleGroup(domande);
			 buttonList.add(choice);
			 choice.setOnAction(new EventHandler<ActionEvent>(){
				 @Override
				 public void handle(ActionEvent e){
					int i =Integer.parseInt(choice.getText().trim())-1;
					setActiveQuestion(i);
				 }
			 });
			 toggleLayout.getChildren().add(choice);
		}
	}
	void setLesson(Lesson lesson) {
		isActive = false;
		questionsIndex=0;
		readQuestions(lesson);
		setActiveQuestion(0);
		questionResult.setText("");
		answerField.clear();
		answerField.setPromptText("Rispondi qui");
	}

	void disableQuestions() {
		question.setText("Le domande appariranno dopo che hai concluso la lezione");
		setDisable(true);
	}

	void enableQuestions() {
		if(!isActive) {
			isActive = true;
			setActiveQuestion(0);
			setDisable(false);
			toggleLayout.setDisable(false);
			answerField.requestFocus();
		}
	}

	void setActiveQuestion(int i) {
		if(i<=questionsIndex){
			answerField.clear();
			if(i==questionsIndex){
				answerField.setFocusTraversable(true);
				answerField.requestFocus();
			} else{
				buttonList.get(i).setFocusTraversable(true);
				buttonList.get(i).requestFocus();
			}
			
			activeIndex = i;
			prev.setDisable(i == 0);
			next.setDisable(i == answers.size() - 1);
			if(risCor.get(i)==true) {
				answerField.setPromptText(answers.get(i));
			} else {

				answerField.setPromptText("Rispondi qui");
			}

			for(int j = 0; j < buttonList.size(); j++) {
				buttonList.get(j).setSelected(false);
			}
			buttonList.get(i).setSelected(true);

			question.setText(questions.get(i));
			questionResult.setFill(Color.BLACK);
			questionResult.setText("Domanda n°"+ (i + 1));			
		} else {
			questionResult.setFill(Color.BLACK);
			questionResult.setText("Rispondi prima alle domande precedenti!");
		}
	}
	
}