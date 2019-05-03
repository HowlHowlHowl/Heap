import java.util.ArrayList;

public class Heap {
	ArrayList<Integer> Array;

	public Heap() {
		Array = new ArrayList<Integer>();
	}
	
	public int size() {
		return Array.size();
	}
	
	public boolean isEmpty() {
		return Array.isEmpty();
	}
	
	
	public void insert(int value) {
		Array.add(value);
		
		//Indice del nodo appena aggiunto
		int i = Array.size() - 1;
		//Indice del padre
		int p = (i - 1) / 2;
		while(i > 0 && Array.get(i) < Array.get(p)) {
			Integer temp = Array.get(p);
			Array.set(p, Array.get(i));
			Array.set(i, temp);
			i = p;
			p = (i - 1) / 2;
		}
	}

	//Incominica a inserire l'elemento value e ritorna la sua posizione attuale,
	//che va poi passata a a insertNextStep per continuare l'inserimento
	public int insertFirstStep(int value) {
		//Indice del nodo appena aggiunto
		Array.add(value);
		int i = Array.size() - 1;
		return i;		
	}
	
	//Continua a inserire l'elemento in posizione i e ritorna la nuova posizione.
	//Ritorna null quando l'elemento e' in posizione
	public Integer insertNextStep(int i) {
		int p = (i - 1) / 2;
		if(i > 0 && Array.get(i) < Array.get(p)) {
			Integer temp = Array.get(p);
			Array.set(p, Array.get(i));
			Array.set(i, temp);
		} else {
			return null;
		}
		
		return p;
	}
	
	public Integer getMin() {
		if(Array.isEmpty()) {
			return null;
		} else {
			return Array.get(0);
		}
	}
	
	//Rimette a posto il nodo in posizione i (O(logn))
	private void restore() {
		int i = 0;
		while(true) {
			//nuova posizione di i
			int newPos = i;
			
			//Indici dei figli
			int c1 = (i + 1) * 2 - 1;
			int c2 = (i + 1) * 2;
			
			if(c1 < Array.size() && Array.get(c1) < Array.get(newPos)) {
				newPos = c1;
			}
			if(c2 < Array.size() && Array.get(c2) < Array.get(newPos)) {
				newPos = c2;
			}
			
			if(i == newPos) {
				break;
			}
			
			Integer temp = Array.get(newPos);
			Array.set(newPos, Array.get(i));
			Array.set(i, temp);
			i = newPos;
		}
	}
	
	public Integer removeMin() {
		Integer result;
		
		if(Array.isEmpty()) {
			result = null;
		} else if(Array.size() == 1) {
			result = Array.remove(0);
		}else {
			result = Array.get(0);
			
			//Sposta l'ultimo elemento al posto del primo
			int last = Array.remove(Array.size() - 1);
			Array.set(0, last);
			//risistema il primo elemento al posto giusto
			restore();	
		}
		
		return result;
	}
	
	public Integer removeMinFirstStep() {
		if(Array.size() <= 1) {
			Array.clear();
			return null;
		}else {
			//Sposta l'ultimo elemento al posto del primo
			int last = Array.remove(Array.size() - 1);
			Array.set(0, last);
			return 0;
		}
	}
	

	public Integer removeMinNextStep(int i) {
		//nuova posizione di i
		int newPos = i;
		
		//Indici dei figli
		int c1 = (i + 1) * 2 - 1;
		int c2 = (i + 1) * 2;
		
		if(c1 < Array.size() && Array.get(c1) < Array.get(newPos)) {
			newPos = c1;
		}
		if(c2 < Array.size() && Array.get(c2) < Array.get(newPos)) {
			newPos = c2;
		}
		
		if(i != newPos) {
			Integer temp = Array.get(newPos);
			Array.set(newPos, Array.get(i));
			Array.set(i, temp);
			return newPos;
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("[");
		for(int i = 0; i < Array.size(); i++) {
			result.append(Array.get(i));
			if(i != Array.size() - 1) {
				result.append(" | ");
			}
		}
		result.append("]");
		return result.toString();
	}
	
	public ArrayList<Integer> getArray() {
		return Array;
	}
	
}
