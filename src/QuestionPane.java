import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private Label question;
	private Text q1;
	private VBox dxQuest;
	private VBox sxQuest;
	private HBox toggleLayout;
	private Integer questionsIndex = 0;
	private Integer activeIndex = 0;
	private ArrayList<String> answers;
	private ArrayList<Boolean> risCor;
	private ArrayList<ToggleButton> buttonList;


	public TextField answerField;


	public QuestionPane(double width, double height){
		super();
		setWidth(width);
		setHeight(height);
		setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		question = new Label("Here is the question...");
		question.setFont(Font.font("courier", 20));
		question.setMinWidth(600);
		question.setMaxWidth(600);
		question.setMinHeight(150);
		question.setWrapText(true);
		
		q1 = new Text("Click anywhere.");


		q1.setFont(Font.font("courier", 20));
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
		prevNextLayout.getChildren().addAll(prev,next);
		prevNextLayout.setPadding(new Insets(20,40,10,30));


		answerField = new TextField();
		answerField.setFont(Font.font("courier",FontWeight.BOLD,30));
		final HBox txtSub = new HBox();

		dxQuest = new VBox();
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
		
		sxQuest = new VBox();
		sxQuest.setPadding(new Insets(20,0,0,20));
		sxQuest.getChildren().addAll(question);

		getChildren().addAll(sxQuest,dxQuest);
		setPadding(new Insets(0,0,0,0));

		setLesson(Lesson.HEAP);
		
		EventHandler<ActionEvent> answrd =new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent e){
				if(answerField.getText() != null && !answerField.getText().isEmpty()) {
					String A = new String(answers.get(activeIndex).trim());
					if(A.equalsIgnoreCase(answerField.getText())) {
						questionsIndex++;
						risCor.set(activeIndex, true);
						q1.setText("Right");
						q1.setFill(Color.GREEN);
						buttonList.get(activeIndex).setTextFill(Color.LIMEGREEN);
						boolean T = true;
						for(int k = 0; k < risCor.size(); k++) {
							if(risCor.get(k).equals(false))
								T = false;
						}
						if(T == true) {
							question.setText( "You answered correctly to all the questions.\nCongratulations!\nYou can now advance to the next lesson");
						}
					}
					else if (risCor.get(activeIndex)!=true){
						q1.setText("Wrong");
						q1.setFill(Color.RED);
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
		
		if(answers.size() != questions.size()) {
			System.out.println("Invalid questions");
			System.exit(1);
		}
		toggleLayout.setDisable(true);

		disableQuestions();
		//System.out.println(answers.size());
		//System.out.println(questions.size());
	}

	void readQuestions(Lesson lesson) {
		String fileName = "questions_" + lesson.toString() + ".txt";
		//read questions
		Scanner jerry = null;
		try {
			File file = new File("src/",fileName);
			jerry = new Scanner(file);
		} catch(Exception e1){
			System.out.println("Exception sul file");
			System.exit(1);
		}

		jerry.useDelimiter("[?.]");
		questions = new ArrayList<String>();
		answers = new ArrayList<String>();
		risCor = new ArrayList<Boolean>();
		buttonList =new ArrayList<ToggleButton>();
		while(jerry.hasNext()){
			String qString = jerry.next().trim();
			if(qString != "")
				questions.add(qString);
			if(jerry.hasNext()) {
				answers.add(jerry.next());
				risCor.add(false);
			}
		}
		jerry.close();
		if(toggleLayout != null) {
			dxQuest.getChildren().remove(toggleLayout);
		}
		toggleLayout = new HBox();
		toggleLayout.setPadding(new Insets (5,0,0,0));
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

		dxQuest.getChildren().add(toggleLayout);
		
	}
	void setLesson(Lesson lesson) {
		questionsIndex=0;
		readQuestions(lesson);
		setActiveQuestion(0);
		q1.setText("");
		answerField.clear();
		answerField.setPromptText("Write your answere here");
	}
	
	void disableQuestions() {
		question.setText("Questions will appear after you finish the lesson");
		setDisable(true);
	}
	
	void enableQuestions() {
		setActiveQuestion(0);
		setDisable(false);
		toggleLayout.setDisable(false);
	}

	void setActiveQuestion(int i) {
		if(i<=questionsIndex){
			answerField.clear();
			if(i >= 0 && i < answers.size()) {
				activeIndex = i;
				if(i > 0) {
					prev.setDisable(false);
				}
				if(i < answers.size() - 1) {
					next.setDisable(false);
				}
				if(i == 0) {
					prev.setDisable(true);
				}
				if(i == answers.size() - 1) {
					next.setDisable(true);
				}
	
				for(int j = 0; j < buttonList.size(); j++) {
					buttonList.get(j).setSelected(false);
				}
				buttonList.get(i).setSelected(true);
	
				question.setText(questions.get(i));
				q1.setText("Domanda n°"+ (i + 1));
				q1.setFill(Color.BLACK);
			}
		} else {
			q1.setFill(Color.BLACK);
			q1.setText("You have to answer this question before...");
		}
	}
}