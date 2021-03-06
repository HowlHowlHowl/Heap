import java.util.ArrayList;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class HeapPane extends Pane {

	final static int MAX_HEAP_SIZE = 15;

	public enum StepState {
		DEFAULT,
		DONE,
		//Heap states
		REMOVING,
		ADDING,
		//Heapsort states
		MAKING,
		MAKING_NEXT,
		HEAPSORTING,
	}

	//step controls
	private Integer stepIndex;
	private StepState state;
	private Button stepButton;
	private Button finishButton;

	//Heap controls
	public TextField enterNodeField;
	private Button removeButton;
	private Button addButton;

	//Heapsort controls
	private Integer makeNextIndex;
	private Integer heapsortIndex;
	private Button heapsortButton;
	private Button randomizeButton;

	//Heap drawing
	private Heap heap;
	private Group heapGroup;
	private Group lessonHeapControls;
	private Group lessonHeapsortControls;

	public HeapPane(double width, double height) {
		super();

		heap = new Heap();

		setWidth(width);
		setHeight(height);
		setMinSize(width, height);
		setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

		heapGroup = new Group();
		getChildren().add(heapGroup);


		//Lesson heap controls
		lessonHeapControls = new Group();
		getChildren().add(lessonHeapControls);
		VBox lessonHeapBar = new VBox();
		lessonHeapControls.getChildren().add(lessonHeapBar);
		lessonHeapBar.setLayoutX(20);
		lessonHeapBar.setLayoutY(height - 75);
		Label lessonHeapLabel = new Label("Comandi Heap:");
		lessonHeapBar.getChildren().add(lessonHeapLabel);
		lessonHeapLabel.setFont(Font.font(14));

		//Add controls
		HBox addBar = new HBox();
		lessonHeapBar.getChildren().add(addBar);
		addButton = new Button("Aggiungi");
		addButton.setMinWidth(70);
		enterNodeField = new TextField();
		enterNodeField.setMaxWidth(30);
		addBar.getChildren().addAll(addButton, enterNodeField);

		//Remove controls
		removeButton = new Button("Rimuovi max");
		removeButton.setMinWidth(100);
		lessonHeapBar.getChildren().add(removeButton);

		//Lesson heapsort controls
		lessonHeapsortControls = new Group();
		getChildren().add(lessonHeapsortControls);
		VBox lessonHeapsortBar = new VBox();
		lessonHeapsortControls.getChildren().add(lessonHeapsortBar);
		lessonHeapsortBar.setLayoutX(20);
		lessonHeapsortBar.setLayoutY(height - 75);
		Label lessonHeapsortLabel = new Label("Comandi Heapsort:");
		lessonHeapsortBar.getChildren().add(lessonHeapsortLabel);
		lessonHeapsortLabel.setFont(Font.font(14));

		heapsortButton = new Button("Heapsort");
		lessonHeapsortBar.getChildren().add(heapsortButton);
		heapsortButton.setMinWidth(100);
		randomizeButton = new Button("Randomize");
		lessonHeapsortBar.getChildren().add(randomizeButton);
		randomizeButton.setMinWidth(100);


		//Step controls
		VBox stepBar = new VBox();
		stepBar.setLayoutX(200);
		stepBar.setLayoutY(height - 75);
		getChildren().add(stepBar);
		Label stepLabel = new Label("Comandi di step:");
		stepLabel.setFont(Font.font(14));
		stepButton = new Button("Step");
		stepButton.setMinWidth(100);
		finishButton = new Button("Finisci");
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
				setState(StepState.ADDING);

				drawHeap();
			}
		});

		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stepIndex = heap.removeMaxFirstStep();
				if(stepIndex != null) {
					setState(StepState.REMOVING);
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

		heapsortButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stepIndex = heap.makeHeapFirstStep();
				setState(StepState.MAKING);
				drawHeap();
			}
		});

		randomizeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				randomizeArray();
				drawHeap();
			}
		});

		widthProperty().addListener((a, b, c) -> { drawHeap(); });

		setState(StepState.DEFAULT);
		setLesson(Lesson.HEAP);
	}

	public void setState(StepState s) {
		state = s;
		switch(state) {
			case DEFAULT:
				stepIndex = null;
				heapsortIndex = Integer.MAX_VALUE;
				stepButton.setText("Step");
				stepButton.setDisable(true);
				finishButton.setDisable(true);
				//heap
				enterNodeField.setDisable(false);
				addButton.setDisable(!(enterNodeField.getText().matches("^[0-9]{1,2}$") && heap.size() < MAX_HEAP_SIZE));
				removeButton.setDisable(false);
				//heapsort
				randomizeButton.setDisable(false);
				heapsortButton.setDisable(false);
			break;

			case REMOVING:
			case ADDING:
				stepButton.setDisable(false);
				finishButton.setDisable(false);
				//heap
				enterNodeField.setDisable(true);
				addButton.setDisable(true);
				removeButton.setDisable(true);
			break;

			case DONE:
				stepButton.setText("Done");
			break;

			case MAKING:
				stepButton.setText("Restore");
				stepButton.setDisable(false);
				finishButton.setDisable(false);

				randomizeButton.setDisable(true);
				heapsortButton.setDisable(true);
			break;

			case MAKING_NEXT:
				stepButton.setText("Next");
			break;

			case HEAPSORTING:
				stepIndex = 0;
				stepButton.setText("Swap root");
			break;
		}
	}

	public void step() {
		switch(state) {
			case REMOVING: {
				Integer newStepIndex = heap.removeMaxNextStep(stepIndex);
				if(newStepIndex == null) {
					setState(StepState.DONE);
				} else {
					stepIndex = newStepIndex;
				}
			} break;

			case ADDING: {
				Integer newStepIndex = heap.insertNextStep(stepIndex);
				if(newStepIndex == null) {
					setState(StepState.DONE);
				} else {
					stepIndex = newStepIndex;
				}
			} break;

			case DONE: {
				setState(StepState.DEFAULT);
			} break;

			case MAKING: {
				Pair<Integer, Integer> results = heap.makeHeapNextStep(stepIndex);
				makeNextIndex = results.getKey();
				stepIndex = results.getValue();
				setState(StepState.MAKING_NEXT);
			} break;

			case MAKING_NEXT: {
				if(makeNextIndex != null) {
					setState(StepState.MAKING);
					stepIndex = makeNextIndex;
				} else {
					setState(StepState.HEAPSORTING);
					heapsortIndex = heap.heapsortFirstStep();
				}
			} break;

			case HEAPSORTING: {
				heapsortIndex = heap.heapsortNextStep(heapsortIndex);
				if(heapsortIndex == 1) {
					heapsortIndex = 0;
					setState(StepState.DONE);
				}
			} break;

			case DEFAULT: {

			}break;
		}
	}

	public void setLesson(Lesson lesson) {
		switch(lesson) {
		case HEAP: {
			heapsortIndex = Integer.MAX_VALUE;
			lessonHeapControls.setVisible(true);
			lessonHeapsortControls.setVisible(false);
			//Genera un heap
			heap.clear();
			Random random = new Random();
			for(int i = 0; i < 5; i++ ){
				heap.insert(Math.abs(random.nextInt(100)));
			}
		} break;

		case HEAPSORT: {
			lessonHeapsortControls.setVisible(true);
			lessonHeapControls.setVisible(false);
			randomizeArray();
			heapsortIndex = heap.size();
		}break;
		}

		setState(StepState.DEFAULT);
		drawHeap();
	}

	public void randomizeArray() {
		//Genera un array con contenuti random (non e' un heap)
		heap.clear();
		ArrayList<Integer> heapArray = new ArrayList<Integer>();
		Random random = new Random();
		for(int i = 0; i < MAX_HEAP_SIZE; i++ ){
			heapArray.add(Math.abs(random.nextInt(100)));
		}
		heap.setArray(heapArray);
	}

	//Ritorna un punto in base all'indice di un elemento dell'heap passato
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
			if(i != 0 && i < heapsortIndex) {
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
				Color c = state == StepState.DONE || state == StepState.MAKING_NEXT ? Color.BLUE : Color.ORANGERED;
				circle.setStroke(c);
			} else {
				circle.setStroke(Color.DARKGREEN);

			}
			if(i >= heapsortIndex) {
				circle.setStroke(Color.BLUE);
			}
			circle.setFill(Color.WHITE);


			heapGroup.getChildren().add(circle);

			Text indexText = new Text(x - 5, y - 30, Integer.toString(i));
			indexText.setFont(Font.font(14));
			heapGroup.getChildren().add(indexText);


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
			double y = 410;

			StackPane stack = new StackPane();
			Rectangle rect = new Rectangle(50, 50);
			rect.setFill(Color.WHITE);
			Color c = i >= heapsortIndex ? Color.BLUE : Color.DARKGREEN;
			rect.setStroke(c);
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
