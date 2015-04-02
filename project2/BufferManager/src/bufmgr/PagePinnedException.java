package bufmgr;

import chainexception.*;

public class PagePinnedException extends ChainException {
          public PagePinnedException(Exception exce, String st)
            {
              super(exce, st);
            }

}

