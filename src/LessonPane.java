import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
			"L' Heap è una struttura dati caratterizzata dalla proprietà di mantenere un ordinamento parziale dei suoi elementi.\n"
			+ "Per questa ragione è spesso utilizzata nell'implementazione delle Priority Queues.\n"
			+ "Inoltre è alla base dell'algoritmo di ordinamento Heapsort.\n\n"
			+ "L'Heap è un albero binario completo, ovvero tutte le foglie sono all'ultimo o al penultimo livello dell'albero e quelle sull'ultimo si trovano alla sinistra dell'albero. Inoltre la profondità dell'albero è sempre log n, dove n è il numero di nodi.",
			//Paragrafo 2
			"Un albero binario completo ha la caratteristica di essere rappresentabile attraverso un vettore seguendo queste proprietà: "
			+ "il padre del nodo in posizione i si trova in posizione (i - 1) / 2, di conseguenza il figlio destro di i si trova in posizione 2i + 1 mentre il sinistro 2i + 2.\n\n"
			+ "Per comodità nel pannello sulla destra l'Heap è raffigurato sia come albero che come vettore, ricordiamoci però che non sono necessari puntatori ma è sufficiente un array per rappresentarlo.", 
			//Paragrafo 3
			"L'Heap si dice parzialmente ordinato perchè il valore di ogni suo nodo è ordinato rispetto al valore del padre, cioè nel caso di un Min-Heap ogni padre è minore dei suoi figli, mentre nel caso di un Max-Heap ogni padre è maggiore.\n\n"
			+ "Una diretta conseguenza molto importante di questa proprietà è che l'elemento minore (o maggiore) dell'Heap si trova sempre alla radice.\n"
			+ "Per semplicità tratteremo solamente il Max-Heap poichè è quello utilizzato per l'algoritmo di ordinamento Heapsort.\n"
			+ "Infatti nel nostro albero il nodo alla radice ha il valore massimo.",
			//Paragrafo 4
			"Per mantenere queste sue proprietà in caso di inserimento e rimozione di elementi bisogna eseguire delle operazione aggiuntive.\n"
			+ "Vediamo ora il caso dell'inserimento. L'elemento viene inserito in coda al vettore, cioè come ultima foglia. A questo punto bisogna ripristinare l'ordinamento parziale scambiando il nuovo nodo con il padre finchè non è minore di esso.\n"
			+ "Poichè questa operazione può essere ripetuta tante volte quanto è profondo l'albero la complessità dell'inserimento è O(log n).\n"
			+ "Puoi provare ad aggiungere nodi attravarso l'interfaccia prestando attenzione a come il nodo viene spostato fino ad arrivare alla posizione corretta.",
			//Paragrafo 5
			"La rimozione di elementi avviene solamente per il nodo alla radice. Anche in questo caso è necessario eseguire il riordinamento parziale.\n"
			+ "Dopo la rimozione della radice l'ultima foglia viene messa al suo posto, e viene poi scambiata con uno dei suoi figli se minore di esso.\n"
			+ "Questa operazione è nota come \"restore\", poichè ripristina le proprietà dell'Heap."
			+ "Nuovamente puoi provare a eliminare la radice dell'Heap e notare come l'ultima foglia viene spostata.",
			//Paragrafo 6
			"Rispondi alle domande per verificare le conoscenze acquisite, puoi aiutarti attraverso l'interfaccia!"
	};
		
	String[] heapsortLessonStrings = {
		//Paragrafo 1
		"L'algoritmo Heapsort è un algoritmo di ordinamento basato sui confronti di complessità ottima O(n log n).\n"
		+ "Come suggerisce il nome è basato sulla struttura dati Heap, che come abbiamo visto nella lezione precedente è implementata su un semplice array.\n"
		+ "I vari passaggi necessari per ordinare un vettore di elementi con l'Heapsort sono descritti in questa lezione.",
		//Paragrafo 2
		"Per prima cosa è necessario generare un Heap dall'array di partenza, questo è il passaggio più complesso poichè dobbiamo stabilire l'ordinamento parziale tra i vari elementi.\n"
		+ "Si parte quindi dal nodo in posizione (n / 2) - 1, poichè è il primo ad avere dei figli, e, come nel caso della rimozione della radice, lo si riporta alla posizione corretta scambiandolo con i figli se necessario.\n"
		+ "Questa operazione deve essere ripetuta per ogni nodo precedente fino alla radice. Bisogna quindi operare n / 2 \"restore\" ciascuno con complessità O(log n) per un totale di O(n log n) per la costruzione completa.",
		//Paragrafo 3
		"Una volta generato l'Heap l'algoritmo di ordinamento è piuttosto semplice, si comincia rimuovendo il nodo alla radice scambiandolo con quello in ultima posizione e, in modo analogo a come accade per la rimozione l'Heap viene ripristinato. "
		+ "Prima di procedere però la dimensione dell'heap viene ridotta di 1, cioè non si tiene più presente il nodo appena rimosso. "
		+ "In questo modo il nodo del valore massimo che si trovava alla radice è ora in ultima posizione, proprio come ci aspettiamo per l'ordinamento.\n"
		+ "Ora è sufficiente ripetere questa procedura finchè l'heap non ha dimensione 1, a questo punto ogni elemento è al suo posto e l'array di partenza risulta ordinato!",
		//Paragrafo 4
		"Concludiamo analizzando la complessità totale dell'algoritmo, come abbiamo visto la costruzione dell'Heap richiede tempo O(n log n), successivamente vengo eseguite n rimozioni, ciascuna di tempo O(log n), pertanto l'algoritmo ha come tempo totale O(n log n).\n\n"
		+ "Prova ora ad applicare l'algoritmo attraverso l'interfaccia, prestando attenzione alle sue due fasi. Quando sei pronto prova a rispondere alle domande!"
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
		addEventHandler(KeyEvent.KEY_PRESSED, Arrow);
		addEventHandler(KeyEvent.KEY_PRESSED, Arrow);
		HBox topBar = new HBox();
		getChildren().add(topBar);
		topBar.setSpacing(50);

		
		lessonTitle = new Label();
		topBar.getChildren().add(lessonTitle);
		lessonTitle.setFont(Font.font("courier", 25));
		
		Button returnToMenu = new Button("Menu");
		topBar.getChildren().add(returnToMenu);
		returnToMenu.setOnAction(onReturnToMenu);
		onReturnToMenu.handle(new ActionEvent());
		
		paragraph = new Label();
		getChildren().add(paragraph);
		paragraph.setFont(Font.font(16));
		paragraph.setWrapText(true);
		paragraph.setMinHeight(400);
		paragraph.setAlignment(Pos.TOP_LEFT);
		
		HBox navBox = new HBox();
		getChildren().add(navBox);
		
		prevButton = new Button("<< Prev");
		navBox.getChildren().add(prevButton);
		prevButton.setFocusTraversable(true);
		HBox navLabelPane = new HBox();
		navBox.getChildren().add(navLabelPane);
		navLabelPane.setMinWidth(200);
		navLabelPane.setAlignment(Pos.CENTER);
		
		navLabel = new Label();
		navLabelPane.getChildren().add(navLabel);
		
		nextButton = new Button("Next >>");
		navBox.getChildren().add(nextButton);
		nextButton.setFocusTraversable(true);
		
		prevButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Left();
			}
		});
		
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Right();
			}
		});
		
		this.onLessonFinished = onLessonFinished;
	}
	
	void setLesson(Lesson lesson) {
		String string = "";
		switch(lesson) {
		case HEAP:
			string = "Struttura dati Heap:";
			lessonParagraphs = heapLessonStrings;
		break;
		case HEAPSORT:
			string = "Algoritmo Heapsort:";
			lessonParagraphs = heapsortLessonStrings;
		}
		lessonTitle.setText(string);
		setParagraphIndex(0);
	}
	
	final EventHandler <KeyEvent> Arrow = new EventHandler<KeyEvent>(){
		@Override 
		public void handle (KeyEvent e){
			if (e.getCode()==KeyCode.RIGHT){
				Right();
				nextButton.requestFocus();
				if(paragraphIndex==lessonParagraphs.length);
					prevButton.requestFocus();
				e.consume();
			}
			if (e.getCode()==KeyCode.LEFT){
				Left();
				prevButton.requestFocus();
				if(paragraphIndex==1);
					nextButton.requestFocus();
				e.consume();
			}
		}
	};
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
	
	void Left(){
		setParagraphIndex(paragraphIndex - 1);
		
	}
	void Right(){
		setParagraphIndex(paragraphIndex + 1);
		
	}
}
