package externalSort;

import relop.FileScan;
import relop.Iterator;
import relop.Schema;
import relop.SimpleJoin;
import relop.Tuple;
import global.AttrType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.Iterator;

public class SortMergeJoin extends Iterator {
	private Iterator iter1 = null;
	private Iterator iter2 = null;
	
	private int col1;
	private int col2;
	//private FileScan scan;
	Schema sortMerge;
	// Tuple tup;
	// Tuple tup1;
	ArrayList <Tuple> joined;
	java.util.Iterator<Tuple> iter;
	//Comparator<Tuple> SortMergeComp; 
	
	public SortMergeJoin(Iterator it1,Iterator it2,int col1,int col2){
		iter1 = it1;
		iter2 = it2;
		this.col1 = col1;
		this.col2 = col2;
		Schema schema1 = iter1.getSchema();
		Schema schema2 = iter2.getSchema();
		setSchema(Schema.join(schema1,schema2));
		
		ArrayList <Tuple> a = new ArrayList<Tuple>();
		ArrayList <Tuple> b = new ArrayList<Tuple>();
		
		joined = new ArrayList<Tuple>();
		
		Comparator<Tuple> smComp = new SortMergeComp();
		
		while(iter1.hasNext()){
			a.add(iter1.getNext());			
		}
		while(iter2.hasNext()){
			b.add(iter2.getNext());
		}
		// sort according to a specific field.
		Collections.sort(a,smComp);
		Collections.sort(b,smComp);
		
		for(Tuple one:a){
			for(Tuple two:b){
				if(smComp.compare(one, two) == 0){
					Tuple comb = Tuple.join(one, two, getSchema());
					joined.add(comb);
				}
			}
		}
		
		 iter = joined.iterator();
		// iter1.restart();
		// iter2.restart();
		
	}
	//public SortMergeJoin(SortMergeJoin join,FileScan scan,int col1, int col2){		
		
	//}
//	public class SortMergeIt implements java.util.Iterator<Tuple>{
	//	Tuple next(){
			
	//	}
//	}
	public class SortMergeComp implements Comparator<Tuple>{
		public int compare(Tuple t1,Tuple t2){
		
		if(iter1.getSchema().fieldType(col1)==AttrType.FLOAT){
			// System.err.Println(t1.getFloatFld(col1));
			if((t1.getFloatFld(col1)-t2.getFloatFld(col2))>0){
				return 1;
			}
			else if((t1.getFloatFld(col1)-t2.getFloatFld(col2))<0){
				return -1;
			}
			else{
				return 0;
			}
		}		
		else if(iter1.getSchema().fieldType(col1)==AttrType.STRING){
			return t1.getStringFld(col1).compareTo(t2.getStringFld(col2));
		}
		else if(iter1.getSchema().fieldType(col1)==AttrType.INTEGER){
			return t1.getIntFld(col1)-t2.getIntFld(col2);
		}		
		else{
			throw new IllegalStateException("type error");
			}
		}	
	}
		
	@Override
	public void explain(int depth) {
		throw new UnsupportedOperationException("Not implemented");
	}
	@Override
	public void restart() {
		iter2 = null;
		iter1 = null;
	}
	@Override
	public boolean isOpen() {
		return true;
	}
	@Override
	public void close() {
		
	}
	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}
	@Override
	public Tuple getNext() {
		
		return iter.next();
	}	
}



