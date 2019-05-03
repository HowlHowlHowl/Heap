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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HeapPane extends Pane {

	private Heap heap;
	private Group heapGroup;
	
	private Integer stepIndex;
	private Integer doneIndex;
	
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
		commandBar.setLayoutX(10);
		commandBar.setLayoutY(height - 100);
		getChildren().add(commandBar);
		
		Label commandLabel = new Label("Heap commands:");
		commandLabel.setFont(Font.font(14));
		commandBar.getChildren().add(commandLabel);
		
		HBox stepBar = new HBox();
		commandBar.getChildren().add(stepBar);
		

		Button stepButton = new Button("Add");
		Button removeButton = new Button("Remove min");
		TextField enterNodeField = new TextField();
		enterNodeField.setMaxWidth(30);
		
		stepButton.setDisable(true);
		stepButton.setMinWidth(70);
		stepButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//If we are not stepping we init a stepping
				if(doneIndex != null) {
					doneIndex = null;
					enterNodeField.setText("");
					stepButton.setText("Add");
					enterNodeField.setDisable(false);
					removeButton.setDisable(false);
				}
				else if(stepIndex == null) {
					if(heap.size() >= 31) {
						return;
					}
					String valueString = enterNodeField.getText();
					stepIndex = heap.insertFirstStep(Integer.parseInt(valueString));
					stepButton.setText("Step");
					enterNodeField.setDisable(true);
					removeButton.setDisable(true);
				} else {
					Integer newStepIndex = heap.insertNextStep(stepIndex);
					if(newStepIndex == null) {
						doneIndex = stepIndex;
						stepButton.setText("Done");
					}
					stepIndex = newStepIndex;
				}
				drawHeap();
			}
		});
		
		commandBar.getChildren().add(removeButton);
		removeButton.setMinWidth(100);
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(doneIndex != null) {
					doneIndex = null;
					removeButton.setText("Remove min");
					stepButton.setDisable(false);
					enterNodeField.setDisable(false);
				} else if(stepIndex == null) {
					stepIndex = heap.removeMinFirstStep();
					if(stepIndex != null) {
						removeButton.setText("Step remove");
						stepButton.setDisable(true);
						enterNodeField.setDisable(true);
					}
				} else {
					Integer newStepIndex = heap.removeMinNextStep(stepIndex);
					if(newStepIndex == null) {
						doneIndex = stepIndex;
						removeButton.setText("Done removing");
					}
					stepIndex = newStepIndex;
				}
				
				drawHeap();
			}
		});
		
		enterNodeField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				stepButton.setDisable(!newValue.matches("^[0-9]{1,2}$"));
			}
		});
		
		stepBar.getChildren().add(stepButton);
		stepBar.getChildren().add(enterNodeField);
		
		widthProperty().addListener((a, b, c) -> { drawHeap(); });
	}
	

	public Point2D getPositionOfHeapIndex(int i) {
		int depth = Utils.intLog2(i + 1);
		int numChildrenAtDepth = Utils.intPow(2, depth);
		int childNumber = (i + 1) % numChildrenAtDepth;

		double childSpacing = getWidth() / (double)numChildrenAtDepth;
		double x = childSpacing / 2 + childNumber * childSpacing;
		double y = depth* 100 + 100;

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
			if(doneIndex != null && i == doneIndex) {
				circle.setStroke(Color.BLUE);
			}
			else if(stepIndex != null && i == stepIndex) {
				circle.setStroke(Color.ORANGERED);	
			} else {
				circle.setStroke(Color.DARKGREEN);
				
			}
			circle.setFill(Color.WHITE);
			heapGroup.getChildren().add(circle);

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

	}

	
}
