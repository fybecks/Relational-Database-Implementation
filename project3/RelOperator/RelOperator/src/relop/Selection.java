package relop;

/**
 * The selection operator specifies which tuples to retain under a condition; in
 * Minibase, this condition is simply a set of independent predicates logically
 * connected by OR operators.
 */
public class Selection extends Iterator {
	private Iterator it = null;
	private Predicate [] pr = null;
	private Tuple tu = null;
	private boolean closed = true;
  /**
   * Constructs a selection, given the underlying iterator and predicates.
   */
  public Selection(Iterator iter, Predicate... preds) {
	  
	  it = iter;
	  schema = it.schema;
	  pr = preds;
	  //tu = null;
	  it.restart();
	  closed = false;
  }

  /**
   * Gives a one-line explaination of the iterator, repeats the call on any
   * child iterators, and increases the indent depth along the way.
   */
  public void explain(int depth) {
	 // System.out.println("Selection");
	 // indent(depth);
  }

  /**
   * Restarts the iterator, i.e. as if it were just constructed.
   */
  public void restart() {
	  it.restart();
	  tu = null;
	  closed = false;
  }

  /**
   * Returns true if the iterator is open; false otherwise.
   */
  public boolean isOpen() {
	  boolean open = !closed;
	  return open;
  }

  /**
   * Closes the iterator, releasing any resources (i.e. pinned pages).
   */
  public void close() {
	  it.close();
	  tu = null;
	  closed = true;
  }

  /**
   * Returns true if there are more tuples, false otherwise.
   */
  public boolean hasNext() {
	  if(tu != null){
          return true;
	  }
	  else{
          	if(it.hasNext() == false){
                  return false;
          	}
          	else{
                  	boolean isTupFound = false;
                  
                  	while(it.hasNext()){
                          Tuple tuple = it.getNext();
                          boolean predMatch = true;
                          
                          for(Predicate p : pr){
                                  if(p.evaluate(tuple) == false){
                                          predMatch = false;                                          
                                          break;
                                  }
                          }
                          
                          if(predMatch){
                                  tu = tuple;
                                  isTupFound = true;                                  
                                  break;
                          }
                  }                  
                  return isTupFound;
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
	  else{
          while(it.hasNext()){
                  Tuple tuple = it.getNext();
                  boolean predMatch = false;
                  
                  for(Predicate p : pr){
                          if(p.evaluate(tuple) == true){
                                  predMatch = true;                                  
                                  break;
                          }
                  }                  
                  if(predMatch){                         
                          return tuple;
                  }
          }
          throw new IllegalStateException("No qualifying tuple found.");
  	}
  }

} // public class Selection extends Iterator
