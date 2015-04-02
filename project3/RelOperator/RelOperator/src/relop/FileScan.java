package relop;

import global.RID;
import heap.HeapFile;
import heap.HeapScan;
/**
 * Wrapper for heap file scan, the most basic access method. This "iterator"
 * version takes schema into consideration and generates real tuples.
 */
public class FileScan extends Iterator {

	private HeapFile hf = null;
	private HeapScan scan = null;
	private RID rid = null;
	private boolean open = false;


  /**
   * Constructs a file scan, given the schema and heap file.
   */
  public FileScan(Schema schema, HeapFile file) {
	  this.schema = schema;
	  hf = file;
	  scan = hf.openScan();
	  rid = new RID();
	  open = true;
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
	 scan.close();
	 open = false;
	 scan = hf.openScan();
	 open = true;
  }

  /**
   * Returns true if the iterator is open; false otherwise.
   */
  public boolean isOpen() {
	  return open;
  }

  /**
   * Closes the iterator, releasing any resources (i.e. pinned pages).
   */
  public void close() {
	  rid = null;
	  scan.close();
	  open = false;
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
	  if(scan != null){
		  byte [] data = scan.getNext(rid);
		  Tuple tuple = new Tuple(this.schema, data);
		  return tuple;
	  }
	  else{
		  return null;
		 
	  }
  }

  /**
   * Gets the RID of the last tuple returned.
   */
  public RID getLastRID() {
	  return rid;
  }

} // public class FileScan extends Iterator
