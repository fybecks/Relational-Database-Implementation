package bufmgr;
import chainexception.*;

public class PageUnpinnedException extends ChainException {
          public PageUnpinnedException(Exception e, String s)
            {
              super(e, s);
            }

}


