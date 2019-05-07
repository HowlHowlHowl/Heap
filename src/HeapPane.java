import java.util.ArrayList;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HeapPane extends Pane {

	final static int MAX_HEAP_SIZE = 15;
	
	public enum State {
		DEFAULT,
		REMOVING,
		ADDING,
		DONE,
	}
	
	//Heap controls
	private State state;
	public TextField enterNodeField;
	private Button stepButton;
	private Button finishButton;
	private Button removeButton;
	private Button addButton;
	private Integer stepIndex;
	//private Integer hoverIndex = 0;

	//Heap drawing
	private Heap heap;
	private Group heapGroup;	

	public HeapPane(double width, double height) {
		super();
		
		//Genera un heap random
		heap = new Heap();
		Random random = new Random();
		random.setSeed(3);
		for(int i = 0; i < 10; i++ ){
			heap.insert(Math.abs(random.nextInt(100)));
		}

		setWidth(width);
		setHeight(height);
		setMinSize(width, height);
		setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

		heapGroup = new Group();
		getChildren().add(heapGroup);
 
		VBox commandBar = new VBox();
		commandBar.setLayoutX(20);
		commandBar.setLayoutY(height - 75);
		getChildren().add(commandBar);
		Label commandLabel = new Label("Heap commands:");
		commandLabel.setFont(Font.font(14));
		commandBar.getChildren().add(commandLabel);

		//Add commands
		HBox addBar = new HBox();
		commandBar.getChildren().add(addBar);
		addButton = new Button("Add");
		addButton.setMinWidth(70);
		enterNodeField = new TextField();
		enterNodeField.setMaxWidth(30);
		addBar.getChildren().addAll(addButton, enterNodeField);

		//Remove commands
		removeButton = new Button("Remove min");
		removeButton.setMinWidth(100);
		commandBar.getChildren().add(removeButton);
		
		//Step commands
		VBox stepBar = new VBox();
		stepBar.setLayoutX(200);
		stepBar.setLayoutY(height - 75);
		getChildren().add(stepBar);
		Label stepLabel = new Label("Step commands:");
		stepLabel.setFont(Font.font(14));
		stepButton = new Button("Step");
		stepButton.setMinWidth(100);
		finishButton = new Button("Finish");
		finishButton.setMinWidth(100);
		stepBar.getChildren().addAll(stepLabel, stepButton, finishButton);
		
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(heap.size() >= MAX_HEAP_SIZE) {
					return;
				}
				String valueString = enterNodeField.getText();
				stepIndex = heap.insertFirstStep(Integer.parseInt(valueString));
				setState(State.ADDING);
				
				drawHeap();
			}
		});
		
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stepIndex = heap.removeMinFirstStep();
				if(stepIndex != null) {
					setState(State.REMOVING);
				}

				drawHeap();
			}
		});
		
		stepButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				step();
				drawHeap();
			}
		});
		
		finishButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				while(stepIndex != null) {
					step();
				}
				drawHeap();
			}
		});

		enterNodeField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				addButton.setDisable(!(newValue.matches("^[0-9]{1,2}$") && heap.size() < MAX_HEAP_SIZE));
			}
		});

		widthProperty().addListener((a, b, c) -> { drawHeap(); });
		
		setState(State.DEFAULT);
	}
	
	public void setState(State s) {
		state = s;
		switch(state) {
			case DEFAULT:
				stepButton.setText("Step");
				enterNodeField.setDisable(false);
				addButton.setDisable(!(enterNodeField.getText().matches("^[0-9]{1,2}$") && heap.size() < MAX_HEAP_SIZE));
				removeButton.setDisable(false);
				stepButton.setDisable(true);
				finishButton.setDisable(true);
			break;
			
			case REMOVING:
			case ADDING:
				enterNodeField.setDisable(true);
				addButton.setDisable(true);
				removeButton.setDisable(true);
				stepButton.setDisable(false);
				finishButton.setDisable(false);
			break;

			case DONE:
				stepButton.setText("Done");
			break;
		}
	}
	
	public void step() {
		if(state == State.DONE) {
			setState(State.DEFAULT);
			stepIndex = null;
		}
		else if(state == State.ADDING) {
			Integer newStepIndex = heap.insertNextStep(stepIndex);
			if(newStepIndex == null) {
				setState(State.DONE);
			} else {
				stepIndex = newStepIndex;
			}
		} else if(state == State.REMOVING) {
			Integer newStepIndex = heap.removeMinNextStep(stepIndex);
			if(newStepIndex == null) {
				setState(State.DONE);
			} else {
				stepIndex = newStepIndex;
			}
		}
	}


	public Point2D getPositionOfHeapIndex(int i) {
		int depth = Utils.intLog2(i + 1);
		int numChildrenAtDepth = Utils.intPow(2, depth);
		int childNumber = (i + 1) % numChildrenAtDepth;

		double childSpacing = getWidth() / (double)numChildrenAtDepth;
		double x = childSpacing / 2 + childNumber * childSpacing;
		double y = depth* 100 + 50;

		return new Point2D(x, y);
	}

	public void setHeap(Heap h) {
		heap = h;
	}

	public void drawHeap() {
		heapGroup.getChildren().clear();
		if(heap == null) {
			return;
		}

		ArrayList<Integer> heapArray = heap.getArray();
		for(int i = 0; i < heapArray.size(); i++) {
			Point2D pos = getPositionOfHeapIndex(i);
			double x = pos.getX();
			double y = pos.getY();

			//Disegna edge to parent
			if(i > 0) {
				int p = (i - 1) / 2;
				Point2D parentPos = getPositionOfHeapIndex(p);
				Point2D lineVec = parentPos.subtract(pos);
				Point2D dir = lineVec.normalize();
				parentPos = parentPos.subtract(dir.multiply(25 + 4));

				Line line = new Line(x, y, parentPos.getX(), parentPos.getY());
				line.setStrokeWidth(4.0);
				line.setStroke(Color.BLACK);
				heapGroup.getChildren().add(line);
			}

			Circle circle = new Circle(x, y, 25);
			circle.setStrokeWidth(4);
			if(stepIndex != null && i == stepIndex) {
				Color c = state == State.DONE ? Color.BLUE : Color.ORANGERED; 
				circle.setStroke(c);
			} else {
				circle.setStroke(Color.DARKGREEN);

			}
			circle.setFill(Color.WHITE);
			
			
			heapGroup.getChildren().add(circle);
			
			Text indexText = new Text(x - 5, y - 30, Integer.toString(i));
			indexText.setFont(Font.font(14));
			heapGroup.getChildren().add(indexText);
			

			//-12, -6 e + 6 sono belli hardcoded perche' i geni che hanno fatto JavaFx non hanno ancora
			// scoperto che magari uno il testo lo vuole centrato, PORCA MADONNA
			int textOffset;
			Integer value = heapArray.get(i);
			if(value > 9) {
				textOffset = -12;
			} else {
				textOffset = -6;
			}
			Text nodeText = new Text(x + textOffset, y + 6, heapArray.get(i).toString());
			nodeText.setFont(Font.font(20));
			heapGroup.getChildren().add(nodeText);
		}
		
		for(int i = 0; i < MAX_HEAP_SIZE; i++) {
			double x = 70 + 50 * i;
			double y = 450;
			
			StackPane stack = new StackPane();
			Rectangle rect = new Rectangle(50, 50);
			rect.setFill(Color.WHITE);
			rect.setStroke(Color.DARKGREEN);
			rect.setStrokeWidth(3);
			stack.getChildren().add(rect);
			
			if(i < heapArray.size()) {
				Text text = new Text(heapArray.get(i).toString());
				text.setFont(Font.font(20));
				stack.getChildren().add(text);
			}
			
			stack.setLayoutX(x);
			stack.setLayoutY(y);
			
			Text indexText = new Text(x + 20, y - 7, Integer.toString(i));
			indexText.setFont(Font.font(14));
			
			heapGroup.getChildren().add(indexText);
			heapGroup.getChildren().add(stack);
		}

	}


}
