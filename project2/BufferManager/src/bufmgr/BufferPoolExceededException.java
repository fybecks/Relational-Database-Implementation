package bufmgr;

import chainexception.*;

public class BufferPoolExceededException extends ChainException {
          public BufferPoolExceededException(Exception e, String st)
            {
              super(e, st);
            }

}

