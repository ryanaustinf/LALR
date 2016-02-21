import java.util.ArrayList;
import java.util.HashMap;

public class State {
	private static int STATE_ID = 0;
	private int id;
	private Item[] items;
	private ArrayList<String> transKeys;
	private HashMap<String,State> transition;
	private boolean isExpanded;

	public State(Item[] items) {
		generateItems(items);
		transKeys = new ArrayList<String>();
		transition = new HashMap<String,State>();
		isExpanded = false;
	}

	public void validate() {
		id = STATE_ID;
		STATE_ID++;
	}
		
	private void generateItems(Item[] items) {
		TableGenerator tg = TableGenerator.instance;

		ArrayList<Item> lr1 = new ArrayList<Item>();
		ArrayList<Item> frontier = new ArrayList<Item>();
		for(Item i : items) {
			frontier.add(i);
		}

		while(frontier.size() > 0) {
			Item i = frontier.get(0);
			boolean isFound = false;
			for(Item item:lr1) {
				if(item.equals(i)) {
					isFound = true;
					break;
				}
			}
			if( !isFound ) {
				lr1.add(i);
				String currPart = i.currPart();
				tg.registerSymbol(currPart);
				//if next is a variable
				if( currPart != null && tg.isVariable(currPart)) {
					String[] first = tg.first(i.remains());
					Production[] prods = tg.productions(currPart);
					if( first.length == 1 && first[0].equals("")) {
						first = i.lookahead();
					}
					for(Production p: prods) {
						frontier.add(new Item(p,first));
					}
				}
			}
			frontier.remove(0);
		}

		this.items = lr1.toArray(new Item[0]);
	}

	public State[] expand() {
		if( isExpanded ) {
			return new State[0];
		} else {
			TableGenerator tg = TableGenerator.instance;

			ArrayList<State> states = new ArrayList<State>();
			for(int i = 0; i < items.length; i++) {
				Item item = items[i];
				if( !item.isReduction()) {
					String curr = item.currPart();
					if(transKeys.indexOf(curr) == -1) {
						transKeys.add(curr);
						ArrayList<Item> newItems = new ArrayList<Item>();
						newItems.add(item.moveForward());
						for(int j = i + 1; j < items.length; j++) {
							String currPart = items[j].currPart();
							if( currPart != null && currPart.equals(curr)) {
								newItems.add(items[j].moveForward());
							}
						}
						State newState = new State(newItems.toArray(new Item[0]));
						newState = tg.addState(newState);
						states.add(newState);
						transition.put(curr,newState);
					}
				}
			}
			return states.toArray(new State[0]);
		}
	}

	public String transKey(int index) {
		return transKeys.get(index);
	}

	public State transition(String key) {
		return transition.get(key);
	}

	public Item item(int i) {
		return items[i];
	}

	public int size() {
		return items.length;
	}

	public boolean equals(State s) {
		int count = 0;
		for(int i = 0; i < s.size(); i++) {
			Item curr = s.item(i);
			for(Item item : items) {
				if( item.equals(curr)) {
					count++;
					break;
				}
			}
		}
		return count == size();
	}

	public String toString() {
		String ret = "State # " + id + "\n";
		for(Item i: items) {
			ret += i.toString() + "\n";
		}
		return ret.substring(0,ret.length() - 1);
	}
}