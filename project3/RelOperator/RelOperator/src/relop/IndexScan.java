package relop;

import global.RID;
import global.SearchKey;
import heap.HeapFile;
import index.BucketScan;
import index.HashIndex;

/**
 * Wrapper for bucket scan, an index access method.
 */
public class IndexScan extends Iterator {
	private BucketScan scan = null;
    private HashIndex hidx = null;
    private HeapFile hf = null;
    
  /**
   * Constructs an index scan, given the hash index and schema.
   */
  public IndexScan(Schema schema, HashIndex index, HeapFile file) {
	  this.schema = schema;
	  hidx = index;
	  hf = file;
	  scan = hidx.openScan();
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
    		scan = hidx.openScan();
    	}
    	else{
    		scan.close();
    		scan = hidx.openScan();
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
		  scan = null;
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
          byte[] array = hf.selectRecord(rid);
          Tuple tuple = new Tuple(schema, array);
          return tuple;
  }

  /**
   * Gets the key of the last tuple returned.
   */
  public SearchKey getLastKey() {
    return scan.getLastKey();
  }

  /**
   * Returns the hash value for the bucket containing the next tuple, or maximum
   * number of buckets if none.
   */
  public int getNextHash() {
    return scan.getNextHash();
  }

} // public class IndexScan extends Iterator
