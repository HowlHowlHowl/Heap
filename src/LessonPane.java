import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LessonPane extends VBox {
	String[] heapLessonStrings = {
			//Paragrafo 1
			"L' \"Heap\" � una struttura dati caratterizzata dalla propriet� di mantenere un ordinamento parziale dei suoi elementi.\n"
			+ "Per questa ragione � spesso utilizzata nell'implementazione delle Priority Queues.\n"
			+ "Inoltre � alla base dell'algoritmo di ordinamento Heapsort.\n\n"
			+ "L'Heap � un albero binario completo, ovvero tutte le foglie sono all'ultimo o al penultimo livello dell'albero e quelle sull'ultimo si trovano alla sinistra dell'albero. Inoltre la profondit� dell'albero � sempre log n, dove n � il numero di nodi.",
			//Paragrafo 2
			"Un albero binario completo ha la caratteristica di essere rappresentabile attraverso un vettore seguendo queste propriet�: "
			+ "il padre del nodo in posizione i si trova in posizione (i - 1) / 2, di conseguenza il figlio destro di i si trova in posizione 2i + 1 mentre il sinistro 2i + 2.\n\n"
			+ "Per comodit� nel pannello sulla destra � raffigurato sia come albero che come vettore, ricordiamoci per� che non sono necessari puntatori ma � sufficiente un array per rappresentarlo.", 
			//Paragrafo 3
			"L'Heap si dice parzialmente ordinato perch� il valore di ogni suo nodo � ordinato rispetto al valore del padre, cio� nel caso di un Min-Heap ogni padre � minore dei suoi figli, mentre nel caso di un Max-Heap ogni padre � maggiore\n\n"
			+ "Una diretta conseguenza molto importante di questa propriet� � che l'elemento minore (o maggiore) dell'Heap si trova sempre alla radice.\n"
			+ "Per semplicit� tratteremo solamente il Max-Heap poich� � quello utilizzato per l'algoritmo di ordinamento Heapsort.\n"
			+ "Infatti nel nostro albero il nodo alla radice ha il valore massimo.",
			//Paragrafo 4
			"Per mantenere queste sue propriet� in caso di inserimento e rimozione di elementi bisogna eseguire delle operazione aggiuntive.\n"
			+ "Vediamo ora il caso dell'inserimento. L'elemento viene inserito in coda al vettore, cio� come ultima foglia. A questo punto bisogna ripristinare l'ordinamento parziale scambiando il nuovo nodo con il padre finch� non � minore di esso.\n"
			+ "Poich� questa operazione pu� essere ripetuta tante volte quanto � profondo l'albero la complessit� in inserimento � O(log n).\n"
			+ "Puoi provare ad aggiungere nodi attravarso l'interfaccia prestando attenzione allo spostamento del nodo fino ad arrivare alla posizione corretta.",
			//Paragrafo 5
			"La rimozione di elementi avviene solamente per il nodo alla radice. Anche in questo caso � necessario come prima eseguire il riordinamento parziale.\n"
			+ "Dopo la rimozione della radice l'ultima foglia viene messa al suo posto, e viene poi scambiata con uno dei suoi figli se minore di esso.\n"
			+ "Nuovamente puoi provare a eliminare la radice dell'Heap e notare come l'ultima foglia viene spostata.",
			//Paragrafo 6
			"Rispondi alle domande per verificare le conoscenze acquisite, puoi aiutarti attraverso l'interfaccia!"
	};
		
	String[] heapsortLessonStrings = {
		"sorty", "sort", "sortsort"
	};
		
	Label lessonTitle;
	String[] lessonParagraphs;
	int paragraphIndex;
	
	Button prevButton;
	Button nextButton;
	Label navLabel;
	Label paragraph;
	
	EventHandler<ActionEvent> onLessonFinished;
	
	LessonPane(double width, double height, EventHandler<ActionEvent> onLessonFinished, EventHandler<ActionEvent> onReturnToMenu) {
		super();
		setMinSize(width, height);
		setMaxWidth(width);
		setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
		setSpacing(30);
		setPadding(new Insets(10));
		setAlignment(Pos.TOP_LEFT);
		
		HBox topBar = new HBox();
		getChildren().add(topBar);
		topBar.setSpacing(60);
		
		lessonTitle = new Label();
		topBar.getChildren().add(lessonTitle);
		lessonTitle.setFont(Font.font("courier", 25));
		
		Button returnToMenu = new Button("Menu");
		topBar.getChildren().add(returnToMenu);
		returnToMenu.setOnAction(onReturnToMenu);
		onReturnToMenu.handle(new ActionEvent());
		
		paragraph = new Label("Hello from the other side wooooooooooo this is such a long messagerino bro what ya gonna do bout that");
		getChildren().add(paragraph);
		paragraph.setFont(Font.font(16));
		paragraph.setWrapText(true);
		paragraph.setMinHeight(400);
		paragraph.setAlignment(Pos.TOP_LEFT);
		
		HBox navBox = new HBox();
		getChildren().add(navBox);
		
		prevButton = new Button("<< Prev");
		navBox.getChildren().add(prevButton);
		
		HBox navLabelPane = new HBox();
		navBox.getChildren().add(navLabelPane);
		navLabelPane.setMinWidth(200);
		navLabelPane.setAlignment(Pos.CENTER);
		
		navLabel = new Label();
		navLabelPane.getChildren().add(navLabel);
		
		nextButton = new Button("Next >>");
		navBox.getChildren().add(nextButton);

		prevButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setParagraphIndex(paragraphIndex - 1);
			}
		});
		
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setParagraphIndex(paragraphIndex + 1);
			}
		});
		
		this.onLessonFinished = onLessonFinished;
		/*
		Button lessonFinished = new Button("Lesson finished");
		getChildren().add(lessonFinished);
		lessonFinished.setOnAction(onLessonFinished);
		*/

	}
	
	void setLesson(Lesson lesson) {
		String string = "";
		switch(lesson) {
		case HEAP:
			string = "Struttura dati heap:";
			lessonParagraphs = heapLessonStrings;
		break;
		case HEAPSORT:
			string = "Algoritmo heapsort:";
			lessonParagraphs = heapsortLessonStrings;
		}
		lessonTitle.setText(string);
		setParagraphIndex(0);
	}
	
	void setParagraphIndex(int i) {
		if(i >= 0 && i < lessonParagraphs.length) {
			prevButton.setDisable(i == 0);
			nextButton.setDisable(i == lessonParagraphs.length - 1);
			navLabel.setText((i + 1) + " / " + lessonParagraphs.length);
			paragraph.setText(lessonParagraphs[i]);
			
			paragraphIndex = i;
			if(paragraphIndex == lessonParagraphs.length - 1) {
				onLessonFinished.handle(new ActionEvent());
			}
		}
	}
}
