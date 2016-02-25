package util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * author: Hao 
 * date:Jan 21, 2016
 * time:3:58:24 PM
 * purpose:
 */
public class Alphabet {
	/**
	 * map: store elements by bit set
	 * inverseIndex: use to find elements with common item
	 * sourceIndex: use to update the occurrence information
	 * sourceList: store all natural element by list
	 * heuristicList: use to speed up the process of mining top-k DSP
	 */
	private Map<BitSet, Element> map;
	private Map<Integer, List<Element>> inverseIndex;
	private Map<Integer, List<Element>> sourceIndex;
	private List<Element> sourceList;
	private List<Element> heuristicList;
	
	public Alphabet() {
		this.map = new HashMap<>();
		this.inverseIndex = new HashMap<>();
		this.sourceIndex = new HashMap<>();
		this.sourceList = new ArrayList<>();
		this.heuristicList = new ArrayList<>();
	}
	
	/**
	 * add new natural element into alphabet
	 */
	public void addNaturalElement(Element addOne){
		map.put(addOne.getValue().getClosure(), addOne);

		buildSourceIndex(addOne);
		buildInverseIndex(addOne);
		heuristicList.add(addOne);
	}
	
	/**
	 * add new element which is generated by two elements
	 */
	public void addElement(Element addOne){
		map.put(addOne.getValue().getClosure(), addOne);
		
		buildInverseIndex(addOne);
		heuristicList.add(addOne);
	}
	
	/**
	 * build the inverse index, in order to reduce the time cost of later process
	 */
	private void buildInverseIndex(Element addOne) {
		 for (int i = addOne.getValue().getClosure().nextSetBit(0); i >= 0; i = addOne.getValue().getClosure().nextSetBit(i+1)) {
		     Integer key = new Integer(i);
		     List<Element> set = inverseIndex.get(key);
		     if(set == null){
		    	 set = new ArrayList<>();
		    	 inverseIndex.put(key, set);
		     }
		     set.add(addOne);
		 }
	}
	
	/**
	 * build the source index, in order to reduce the time cost of update elements
	 */
	private void buildSourceIndex(Element addOne) {
		 for (int i = addOne.getValue().getClosure().nextSetBit(0); i >= 0; i = addOne.getValue().getClosure().nextSetBit(i+1)) {
		     Integer key = new Integer(i);
		     List<Element> set = sourceIndex.get(key);
		     if(set == null){
		    	 set = new ArrayList<>();
		    	 sourceIndex.put(key, set);
		     }
		     set.add(addOne);
		 }
		 sourceList.add(addOne);
	}
	
	/**
	 * build heuristic List
	 * since use the new feature in Java8, the require JDK 1.8
	 */
	public void buildHeuristicListJ18(){
		/** at first, calculate the posSup, negSup and cRatio **/
		heuristicList.stream().forEach(new Consumer<Element>() {
			@Override
			public void accept(Element t) {
				t.calculate();
			}
		});
		
		/** sort the heuristic List **/
		ElementComparator ec = new ElementComparator();
		Collections.sort(heuristicList, ec);
	}

	/**
	 * get the list natural elements which has the item (index is key) from sourceIndex
	 */
	public List<Element> getNaturalListByItem(Integer key) {
		return sourceIndex.get(key);
	}
	
	/** 
	 * get the element list for baseline
	 */
	public List<Element> getBaselineList(){
		List<Element> ceList = new ArrayList<>();
		ceList.addAll(map.values());
		return ceList;
	}
	
	/**
	 * toString method
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Alphabet:\n");
		/** element map **/
		sb.append(" Map:\n");
		for(BitSet key : map.keySet()){
			sb.append(map.get(key).toString());
		}
		
		/** inverseIndex **/
//		sb.append(" Inverse Index:\n");
//		for(Integer item : inverseIndex.keySet()){
//			sb.append("	Item: " + ItemMap.iDecode(item) + "\n");
//			for(Element e: inverseIndex.get(item)){
//				sb.append("			" + e.getValue().toString());
//			}
//		}
		
		/** sourceIndex **/
//		sb.append(" Source Index:\n");
//		for(Integer item : sourceIndex.keySet()){
//			sb.append("	Item: " + ItemMap.iDecode(item) + "\n");
//			for(Element e: sourceIndex.get(item)){
//				sb.append("			" + e.getValue().toString());
//			}
//		}
		
		/** heuristicList **/
		sb.append(" Heuristic List:\n");
		for(Element h : heuristicList){
			sb.append(h.simpleDetail());
		}
		
		return sb.toString();
	}
	
	/** 
	 * clear useless object 
	 * **/
	public void clearUO() {
		this.map = null;
		this.sourceIndex = null;
		this.sourceList = null;
		this.inverseIndex = null;
	}
	
	public void clear(){
		this.map = new HashMap<>();
		this.inverseIndex = new HashMap<>();
		this.sourceIndex = new HashMap<>();
		this.sourceList = new ArrayList<>();
		this.heuristicList = new ArrayList<>();
	}
	
	/************************************************
	 * Getter and Setter
	 ************************************************/
	public Element getElementByBitSet(BitSet bs){
		return map.get(bs);
	}

	public Map<BitSet, Element> getMap() {
		return map;
	}

	public void setMap(HashMap<BitSet, Element> map) {
		this.map = map;
	}

	public Map<Integer, List<Element>> getInverseIndex() {
		return inverseIndex;
	}

	public void setInverseIndex(Map<Integer, List<Element>> inverseIndex) {
		this.inverseIndex = inverseIndex;
	}

	public Map<Integer, List<Element>> getSourceIndex() {
		return sourceIndex;
	}

	public void setSourceIndex(Map<Integer, List<Element>> sourceIndex) {
		this.sourceIndex = sourceIndex;
	}

	public void setMap(Map<BitSet, Element> map) {
		this.map = map;
	}

	public void setHeuristicList(List<Element> heuristicList) {
		this.heuristicList = heuristicList;
	}

	public List<Element> getHeuristicList() {
		return heuristicList;
	}

	public void setHeuristicList(ArrayList<Element> heuristicList) {
		this.heuristicList = heuristicList;
	}

	public List<Element> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<Element> sourceList) {
		this.sourceList = sourceList;
	}
	
}
