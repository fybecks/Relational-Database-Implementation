package bufmgr;
import chainexception.*;

public class HashEntryNotFoundException extends ChainException {
        public HashEntryNotFoundException(Exception exce, String st)
    {
      super(exce, st);
    }

}

