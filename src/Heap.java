import java.util.ArrayList;
import javafx.util.Pair;

public class Heap {
	ArrayList<Integer> array;

	public Heap() {
		array = new ArrayList<Integer>();
	}
	
	public int size() {
		return array.size();
	}
	
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	public void clear() {
		array.clear();
	}
	
	public void insert(int value) {
		array.add(value);
		
		//Indice del nodo appena aggiunto
		int i = array.size() - 1;
		//Indice del padre
		int p = (i - 1) / 2;
		while(i > 0 && array.get(i) > array.get(p)) {
			Integer temp = array.get(p);
			array.set(p, array.get(i));
			array.set(i, temp);
			i = p;
			p = (i - 1) / 2;
		}
	}

	//Incominica a inserire l'elemento value e ritorna la sua posizione attuale,
	//che va poi passata a a insertNextStep per continuare l'inserimento
	public int insertFirstStep(int value) {
		//Indice del nodo appena aggiunto
		array.add(value);
		int i = array.size() - 1;
		return i;		
	}
	
	//Continua a inserire l'elemento in posizione i e ritorna la nuova posizione.
	//Ritorna null quando l'elemento e' in posizione
	public Integer insertNextStep(int i) {
		int p = (i - 1) / 2;
		if(i > 0 && array.get(i) > array.get(p)) {
			Integer temp = array.get(p);
			array.set(p, array.get(i));
			array.set(i, temp);
		} else {
			return null;
		}
		
		return p;
	}
	
	public Integer getMax() {
		if(array.isEmpty()) {
			return null;
		} else {
			return array.get(0);
		}
	}
	
	//Rimette a posto il nodo in posizione i (O(logn)) e ritorna la nuova posizione (usata solo per disegnare)
	private int restore(int i, int n) {
		while(true) {
			//nuova posizione di i
			int newPos = i;
			
			//Indici dei figli
			int c1 = (i + 1) * 2 - 1;
			int c2 = (i + 1) * 2;
			
			if(c1 < n && array.get(c1) > array.get(newPos)) {
				newPos = c1;
			}
			if(c2 < n && array.get(c2) > array.get(newPos)) {
				newPos = c2;
			}
			
			if(i == newPos) {
				return i;
			}
			
			Integer temp = array.get(newPos);
			array.set(newPos, array.get(i));
			array.set(i, temp);
			i = newPos;
		}
	}
	
	public Integer removeMax() {
		Integer result;
		
		if(array.isEmpty()) {
			result = null;
		} else if(array.size() == 1) {
			result = array.remove(0);
		}else {
			result = array.get(0);
			
			//Sposta l'ultimo elemento al posto del primo
			int last = array.remove(array.size() - 1);
			array.set(0, last);
			//risistema il primo elemento al posto giusto
			restore(0, array.size());	
		}
		
		return result;
	}
	
	public Integer removeMaxFirstStep() {
		if(array.size() <= 1) {
			array.clear();
			return null;
		}else {
			//Sposta l'ultimo elemento al posto del primo
			int last = array.remove(array.size() - 1);
			array.set(0, last);
			return 0;
		}
	}
	

	public Integer removeMaxNextStep(int i) {
		//nuova posizione di i
		int newPos = i;
		
		//Indici dei figli
		int c1 = (i + 1) * 2 - 1;
		int c2 = (i + 1) * 2;
		
		if(c1 < array.size() && array.get(c1) > array.get(newPos)) {
			newPos = c1;
		}
		if(c2 < array.size() && array.get(c2) > array.get(newPos)) {
			newPos = c2;
		}
		
		if(i != newPos) {
			Integer temp = array.get(newPos);
			array.set(newPos, array.get(i));
			array.set(i, temp);
			return newPos;
		} else {
			return null;
		}
	}
	
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("[");
		for(int i = 0; i < array.size(); i++) {
			result.append(array.get(i));
			if(i != array.size() - 1) {
				result.append(" | ");
			}
		}
		result.append("]");
		return result.toString();
	}
	
	public ArrayList<Integer> getArray() {
		return array;
	}
	

	public void setArray(ArrayList<Integer> a) {
		array = a;
	}
	
	public void makeHeap() {
		for(int i = array.size() / 2 - 1; i >= 0; i--) {
			restore(i, array.size());
		}
	}
	
	public Pair<Integer, Integer> makeHeapNextStep(int i) {
		int newPos = restore(i, array.size());
		if(i > 0) {
			return new Pair<Integer, Integer>(i - 1, newPos);	
		} else {
			return new Pair<Integer, Integer>(null, newPos);
		}
	}
	
	public Integer makeHeapFirstStep() {
		int i = array.size() / 2 - 1;
		return i;
	}
	
	public Integer heapsortNextStep(int i) {
		i--;
		Integer temp = array.get(i);
		array.set(i, array.get(0));
		array.set(0, temp);
		restore(0, i);
		
		if(i > 0) {
			return i;
		} else {
			return null;
		}
	}
	
	public Integer heapsortFirstStep() {
		int i = array.size();
		return i;
	}
	public void heapsort() {
		makeHeap();
		for(int i = array.size() - 1; i > 0; i--) {
			Integer temp = array.get(i);
			array.set(i, array.get(0));
			array.set(0, temp);
			
			restore(0, i);
		}
	}
}
