package bufmgr;

import global.PageId;

public class FrameDescriptor {

	public int pinCount;
	public PageId pageno;
	public boolean isDirty;
	
	public FrameDescriptor(int pinCount, PageId pageno, boolean isDirty) {
		this.pinCount = pinCount;
		this.pageno = pageno;
		this.isDirty = isDirty;
	}
	
}
