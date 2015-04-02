package relop;

/**
 * The simplest of all join algorithms: nested loops (see textbook, 3rd edition,
 * section 14.4.1, page 454).
 */
public class SimpleJoin extends Iterator {
	private Iterator lit = null;
	private Iterator rit = null;
	private Predicate [] pr;
	private Tuple tu = null;
	
	private boolean open;
	private Tuple clTuple = null;
    private Tuple crTuple = null;

  /**
   * Constructs a join, given the left and right iterators and join predicates
   * (relative to the combined schema).
   */
  public SimpleJoin(Iterator left, Iterator right, Predicate... preds) {
	  lit = left;
	  rit = right;
	  pr = preds;
	  this.schema = Schema.join(lit.schema, rit.schema);
	  lit.restart();
	  rit.restart();
	  open = true;
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
	  lit.restart();
	  rit.restart();
	  open = true;
	  tu = null;
	  clTuple = null;
      crTuple = null;
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
	  lit.close();
	  rit.close();
	  open = false;
	  tu = null;
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
	  if(tu != null){
          return true;
	  }
	  else{
		 
          if(lit.hasNext() == false)
          {
        	 
                  return false;
          }
          else{
                  if(clTuple == null || rit.hasNext() == false){
                          if(lit.hasNext() == false){
                                  return false;
                          }
                          else{
                                  clTuple = lit.getNext();
                          }
                  }
                          
                  while(true){
                          if(rit.hasNext() == false){
                                  rit.restart();
                          }
                          
                          while(rit.hasNext()){
                                  
                                  crTuple = rit.getNext();
                                  Tuple tupleJoined = Tuple.join(clTuple, crTuple, schema);
                                  boolean predMatch = true;

                                  for(Predicate predicate : pr){
                                          if(predicate.evaluate(tupleJoined) == false){
                                                  predMatch = false;

                                                  break;
                                          }
                                  }

                                  if(predMatch == true){
                                          tu = tupleJoined;

                                          return true;
                                  }
                          }
                          
                          if(lit.hasNext() == false){
                                  break;
                          }else{
                                  clTuple = lit.getNext();
                          }
                  }
                  
                  return false;
          	}
          }
  }

  /**
   * Gets the next tuple in the iteration.
   * 
   * @throws IllegalStateException if no more tuples
   */
  public Tuple getNext() {
	  if(tu != null){
          Tuple tuple = tu;
          
          tu = null;
          
          return tuple;
	  	}
	  else
	  {
          
          if(lit.hasNext() != false)
          {
        	  if(clTuple == null || rit.hasNext() == false){
                  if(lit.hasNext() != false){
                	   clTuple = lit.getNext();                         
                  }else{          
                	   throw new IllegalStateException("tuple not found.");
                  }
          }
                  
          while(true){
                  
        	  if(rit.hasNext() == false){
                          rit.restart();
                  }
                  
                  while(rit.hasNext()){
                          
                          crTuple = rit.getNext();
                          Tuple tupleJoined = Tuple.join(clTuple, crTuple, schema);
                          boolean predMatch = true;

                          for(Predicate predicate : pr){
                                  if(predicate.evaluate(tupleJoined) == false){
                                          predMatch = false;
                                          break;
                                  }
                          }

                          if(predMatch == true){
                                  return tupleJoined;
                          }
                  }
                  
                  if(lit.hasNext() == false){
                          break;
                  }
                  else{
                          clTuple = lit.getNext();
                  }
          	}       
          
          	throw new IllegalStateException("Not found.");
                 
          }
          else{                  
        	  throw new IllegalStateException("tuple not found.");
          }
  }
  }
  

} // public class SimpleJoin extends Iterator
