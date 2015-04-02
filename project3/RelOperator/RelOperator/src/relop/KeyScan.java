package relop;

import global.RID;
import global.SearchKey;
import heap.HeapFile;
import index.HashIndex;
import index.HashScan;

/**
 * Wrapper for hash scan, an index access method.
 */
public class KeyScan extends Iterator {
	
	private HashScan scan = null;    
    private HashIndex hidx = null;
    private SearchKey key = null;
    private HeapFile hf = null;
   

  /**
   * Constructs an index scan, given the hash index and schema.
   */
  public KeyScan(Schema schema, HashIndex index, SearchKey key, HeapFile file) {
	  this.schema = schema;
	  hidx = index;
	  this.key = key;
	  hf = file;
	  scan = hidx.openScan(this.key);
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
	  if(scan == null){
		  scan = hidx.openScan(key);
	  }
	  else{
		  scan.close();
		  scan = hidx.openScan(key);
	  }
  }

  /**
   * Returns true if the iterator is open; false otherwise.
   */
  public boolean isOpen() {
	  if(scan == null){
		  return false;
	  }
	  else{
		  return true;
	  }
  }

  /**
   * Closes the iterator, releasing any resources (i.e. pinned pages).
   */
  public void close() {
	  if(scan != null){
		  scan.close();
		  scan=null;
	  }
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
	  
		  return scan.hasNext();
	  
  }

  /**
   * Gets the next tuple in the iteration.
   * 
   * @throws IllegalStateException if no more tuples
   */
  public Tuple getNext() {

		  RID rid = scan.getNext();
		  byte [] data = hf.selectRecord(rid);
		  Tuple tuple = new Tuple(schema,data);
		  return tuple;
	 
	  
  }

} // public class KeyScan extends Iterator
