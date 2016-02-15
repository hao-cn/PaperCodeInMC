package util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

/**
 * author: Hao 
 * date:2015��3��19��
 * time:����4:26:23
 * purpose: Clone some object
 */
public enum MyClone {
	INSTANCE;
	
	/** clone the HashMap<Integer, BitSet> **/
	public HashMap<Integer, BitSet> clone(HashMap<Integer, BitSet> origin){
		HashMap<Integer, BitSet> clone = new HashMap<Integer, BitSet>();
		for(Integer key: origin.keySet()){
			BitSet cloneBS = (BitSet)origin.get(key).clone();
			clone.put(key, cloneBS);
		}
		return clone;
	}
	
	/** clone the ArrayList<String> **/
	public ArrayList<String> clone(ArrayList<String> origin){
		ArrayList<String> clone = new ArrayList<String>();
		for(String e:origin){
			clone.add(e);
		}
		return clone;
	}
}
