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
	
	
	public void insert(Integer value) {
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
	
	public Integer removeMin() {
		if(Array.isEmpty()) {
			return null;
		} else {
			return Array.get(0);
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
