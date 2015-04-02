package relop;

/**
 * The projection operator extracts columns from a relation; unlike in
 * relational algebra, this operator does NOT eliminate duplicate tuples.
 */
public class Projection extends Iterator {
	private Iterator it;
	private Integer [] fld;
	
  /**
   * Constructs a projection, given the underlying iterator and field numbers.
   */
  public Projection(Iterator iter, Integer... fields) {
	  it = iter;
      fld = fields;
      schema = new Schema(fld.length);
    
      for(int i = 0;i < fld.length;i++){
              schema.initField(i,it.schema.fieldType(fields[i]),
            		  it.schema.fieldLength(fields[i]),it.schema.fieldName(fields[i]));            
      }
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
   // throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
	  it.restart();
  }

  /**
   * Returns true if the iterator is open; false otherwise.
   */
  public boolean isOpen() {
	  return it.isOpen();
  }

  /**
   * Closes the iterator, releasing any resources (i.e. pinned pages).
   */
  public void close() {
	  it.close();
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
	  return it.hasNext();
  }

  /**
   * Gets the next tuple in the iteration.
   * 
   * @throws IllegalStateException if no more tuples
   */
  public Tuple getNext() {
	  Tuple tupRes = new Tuple(schema);
      int fldcnt = schema.getCount();
      Tuple origTuple = it.getNext();
      
      for(int i=0;i<fldcnt;i++){
              tupRes.setField(i, origTuple.getField(fld[i]));
      }
      return tupRes;
  }

} // public class Projection extends Iterator
