package bufmgr;

import global.*;
import java.util.*;
import chainexception.*;

public class BufMgr {
	private int numBufs;	// number of buffers in buffer pool;
	private int lookAhead = 0;		// look-ahead size;
	private int capacity;      // modulo of a prime number;
    private int scale;			// scale of a prime number;
    private int shift;			// shift of a prime number;
    
    private String replacementPolicy = "abracadabra";   // policy type;
	 
	private Page[] bufPool;			// class variable for buffer pool of type Page
	private FrameDescriptor[] ArrBufDesc;        // descriptor array
	private LinkedList<HashEntry>[] hashArrList;    //create a hash map of linked lists.
	private Queue<Integer> availableFrameInPool;	
		
/**
 * Create the BufMgr object.
 * Allocate pages (frames) for the buffer pool in main memory and
 * make the buffer manage aware that the replacement policy is
 * specified by replacerArg (i.e. LH, Clock, LRU, MRU etc.).
 *
 * @param numbufs number of buffers in the buffer pool
 * @param prefetchSize number of pages to be prefetched
 * @param replacementPolicy Name of the replacement policy
 */
public BufMgr(int numbufs, int prefetchSize, String replacementPolicy) {
	numBufs = numbufs;
	lookAhead = prefetchSize;
	this.replacementPolicy = replacementPolicy;
	
	bufPool = new Page[numBufs];
	ArrBufDesc = new FrameDescriptor[numBufs];
	
	capacity = 2087;
	scale = 47;
	shift = 71;	
	
	initHashList(capacity);
    initPool(numBufs);    
	}

/** 
* Pin a page.
* First check if this page is already in the buffer pool.
* If it is, increment the pin_count and return a pointer to this 
* page.
* If the pin_count was 0 before the call, the page was a 
* replacement candidate, but is no longer a candidate.
* If the page is not in the pool, choose a frame (from the 
* set of replacement candidates) to hold this page, read the 
* page (using the appropriate method from {\em diskmgr} package) and pin it.
* Also, must write out the old page in chosen frame if it is dirty 
* before reading new page.__ (You can assume that emptyPage==false for
* this assignment.)
*
* @param pageno page number in the Minibase.
* @param page the pointer point to the page.
* @param emptyPage true (empty page); false (non-empty page)
*/
 public void pinPage(PageId pageno, Page page, boolean emptyPage) throws BufferPoolExceededException,
 PagePinnedException,ChainException{

	// System.out.println("added "+pageno.pid);
	 int flag = 0;
	 
	 boolean found = contains(pageno.pid);
	 if(found)
	 {
		 int pageIdx = search(pageno.pid);
		 flag = pageIdx;
		 
		 if(ArrBufDesc[pageIdx].pinCount == 0){
			 availableFrameInPool.remove(pageIdx);
		 }
		 
		 byte[] s = bufPool[pageIdx].getpage();
		 page.setpage(s);
		 ArrBufDesc[pageIdx].pinCount++;
	 }
	 else if(flag != -1){
		 int pageIdx = -1;
		 
		 if(availableFrameInPool.size() != 0)
		 {
			 pageIdx = availableFrameInPool.remove();
			 if(ArrBufDesc[pageIdx] != null)
			 {
				 if (ArrBufDesc[pageIdx].isDirty){
					 flushPage(ArrBufDesc[pageIdx].pageno);				 
				 }
				 
				 remove(ArrBufDesc[pageIdx].pageno.pid);
			 }
		 }
		 else {
			 //System.out.println("is Full");	
			 throw new BufferPoolExceededException(null,"buffer pool number exceeded.");
		 }
			 //else
				// throw new BufferPoolExceededException(null,
                        // "BUFMGR:PAGE_PIN_FAILED");			 

			 //System.err.println("added "+pageno.pid);	
			// System.err.println("Frame size: "+availableFrameInPool.remove());	 
			 // if(availableFrameInPool.size() == 1){ throw new BufferPoolExceededException(null,"buffer pool number exceeded.");}
		 Page onepage = new Page();
		 try{
				 PageId np = new PageId(pageno.pid);
				 Minibase.DiskManager.read_page(np, onepage);
		 }
		 catch (Exception e){
				 throw new ChainException();
		 }
			 		
		 bufPool[pageIdx] = onepage;
		 byte [] arr = bufPool[pageIdx].getpage();
		 page.setpage(arr);
 
		 ArrBufDesc[pageIdx] = new FrameDescriptor(1, new PageId(pageno.pid), false);
		 insert(pageno.pid, pageIdx);
		 //System.err.println("added "+pageno.pid);			 
	 	}
	 	else {
	 			throw new HashEntryNotFoundException(null, "hash entry is not found.");
	 	}
 	}
 
/**
* Unpin a page specified by a pageId.
* This method should be called with dirty==true if the client has
* modified the page.
* If so, this call should set the dirty bit 
* for this frame.
* Further, if pin_count>0, this method should 
* decrement it. 
*If pin_count=0 before this call, throw an exception
* to report error.
*(For testing purposes, we ask you to throw
* an exception named PageUnpinnedException in case of error.)
*
* @param pageno page number in the Minibase.
* @param dirty the dirty bit of the frame
*/
public void unpinPage(PageId pageno, boolean dirty) throws PageUnpinnedException, HashEntryNotFoundException{
	int flag = 0;
	if (contains(pageno.pid))
    {
            int index = search(pageno.pid);
            // System.err.println("pin index: " + index);
            flag = index;
            if (ArrBufDesc[index].pinCount == 0) {
                throw new PageUnpinnedException(null, "page unpin operation fails.");
            }
            else if(flag != -1)
            {
                ArrBufDesc[index].isDirty = dirty;
                ArrBufDesc[index].pinCount--;
                
                if (ArrBufDesc[index].pinCount == 0){
                        availableFrameInPool.add(index);
                       // System.err.println("frame size: "+availableFrameInPool.size());
                }
            }
    }
	else{
			throw new HashEntryNotFoundException(null, "page unpin operation fails.");
	}
	//System.err.println("added "+pageno.pid);
}

/** 
* Allocate new pages.
* Call DB object to allocate a run of new pages and 
* find a frame in the buffer pool for the first page
* and pin it. (This call allows a client of the Buffer Manager
* to allocate pages on disk.) If buffer is full, i.e., you 
* can't find a frame for the first page, ask DB to deallocate 
* all these pages, and return null.
*
* @param firstpage the address of the first page.
* @param howmany total number of allocated new pages.
*
* @return the first page id of the new pages.__ null, if error.
*/
public PageId newPage(Page firstpage, int howmany)throws BufferPoolExceededException,
PagePinnedException, ChainException {
	PageId id = new PageId();
	
	if(availableFrameInPool.size() == 0){
		 return null;
	}
			
   // System.out.println("size of frame: " + availableFrameInPool.size());
    //if(howmany > availableFrameInPool.size()){ throw new BufferPoolExceededException(null,"buffer pool number exceeded.");}
    
    try {    		
    	 Minibase.DiskManager.allocate_page(id, howmany);
    	// if(howmany > availableFrameInPool.size()){ throw new BufferPoolExceededException(null,"buffer pool number exceeded.");}
    } catch (Exception e) {
            throw new ChainException(e, "allocating a page operation failed");
    }
    
    pinPage(id, firstpage, false);    
    
    return id;

	}
 
/**
* This method should be called to delete a page that is on disk.
* This routine must call the method in diskmgr package to 
* deallocate the page. 
*
* @param globalPageId the page number in the data base.
*/
public void freePage(PageId globalPageId)throws ChainException {
	if (contains(globalPageId.pid)) {
        int frameNo = -1;
        try {
        	// System.out.println("pid: "+ globalPageId.pid);
           	frameNo = search(globalPageId.pid);
        	                                
            if (ArrBufDesc[frameNo].pinCount > 1) {
                    throw new PagePinnedException(null,"Pin count greater than 1, free page error. ");
            }
                
            if (ArrBufDesc[frameNo].pinCount == 1){
                    unpinPage(ArrBufDesc[frameNo].pageno, ArrBufDesc[frameNo].isDirty);
            }
                
            if (ArrBufDesc[frameNo].isDirty == true){
                        try {
                                flushPage(globalPageId);
                        } catch (Exception e) {
                                throw new ChainException(null,"free page operation fails. ");
                        }
            }
            
            bufPool[frameNo] = null;
            ArrBufDesc[frameNo] = null;    
            remove(globalPageId.pid);
                        
            PageId startPageId = new PageId(globalPageId.pid);
            Minibase.DiskManager.deallocate_page(startPageId);
                
        } catch (Exception e) {
                throw new PagePinnedException(null, "free page operation fails. ");
        }

	} else{		
			PageId startPageId = new PageId(globalPageId.pid);
			Minibase.DiskManager.deallocate_page(startPageId);
			//throw new HashEntryNotFoundException(null, "buffer manager: no such page with this pid, free page fails.");
	}
}
 
/**
* Used to flush a particular page of the buffer pool to disk.
* This method calls the write_page method of the diskmgr package.
*
* @param pageid the page number in the database.
*/
public void flushPage(PageId pageid) throws HashEntryNotFoundException,ChainException {
	Page apage = null;
	int frameNo = -1;
	
	if (contains(pageid.pid)){
        frameNo = search(pageid.pid);
	}
	else {
        throw new HashEntryNotFoundException(null, "hash entry not found");
	}
	
    if (bufPool[frameNo] != null){
    		byte [] bArr = bufPool[frameNo].getpage().clone();
            apage = new Page(bArr);
    }
   
    try {
            if (apage == null) {
            	throw new HashEntryNotFoundException(null, "page not flush id!");            	
            } else {
            	ArrBufDesc[frameNo].isDirty = false;
            	Minibase.DiskManager.write_page(pageid, apage);                
            }
    } catch (Exception e) {
            throw new ChainException(e, "flushPage operation is failed");
    }

}
 
/**
* Used to flush all dirty pages in the buffer poll to disk
*
*/
public void flushAllPages() throws HashEntryNotFoundException,ChainException {
	for (int i = numBufs - 1; i >= 0; i--) {
        if ((ArrBufDesc[i] != null))
                flushPage(ArrBufDesc[i].pageno);
	}	
}
 
/**
* Gets the total number of buffer frames.
*/
public int getNumBuffers() {
	return numBufs;
}
 
/**
* Gets the total number of unpinned buffer frames.
*/
public int getNumUnpinned() {
	 return availableFrameInPool.size();
}

private void initPool(int cap){
	availableFrameInPool = new LinkedList<Integer>();
	for(int i = cap - 1; i >= 0 ; i--)
		availableFrameInPool.add(i);
}

private void initHashList(int cap){
	hashArrList = (LinkedList<HashEntry>[]) new LinkedList[cap];    // hash table with its entry of linked list. 
	for (int i = hashArrList.length - 1 ; i >= 0 ; i--){
   	 hashArrList[i] = new LinkedList<HashEntry>();
   	}
}

private int hashValue(int k) {   // get hash value
    return (scale * k + shift) % capacity;
}    

private void insert(int k, int v) {  // insert an entry in hash table
	 boolean found = false;
	 HashEntry e = null;
	 int hashIdx = hashValue(k);
     Iterator<HashEntry> it = hashArrList[hashIdx].iterator();   
    
     while (it.hasNext() && !found) {
            e = it.next();
            if (e.key == k) {
                    found = true;
                    if(e.value == v){
                    	;
                    }
                    else{
                    	HashEntry n = new HashEntry(k,v);
                   	 	hashArrList[hashIdx].push(n);                   
                    }                   
            }
    }
    if (!found) {
    	HashEntry m = new HashEntry(k,v);
    	hashArrList[hashIdx].push(m);
    }
}

private int search(int k) {	// search for an entry in the hash table
	HashEntry e = null;
    int i = hashValue(k);
    Iterator<HashEntry> it = hashArrList[i].iterator();
    
    while (it.hasNext()) {
            e = it.next();
            if (e.key == k) {
                    return e.value;
            }
    }
    return -1;
}

private boolean contains(int k) {  // check if hash table contains given key
	 if(search(k) == -1){
		 return false;
	 }
	 else    		 
		 return true;
}

private void remove(int k) {         // remove an entry in hash table with key k, if any
	int i = hashValue(k);
    HashEntry e = null;
    Iterator<HashEntry> it = hashArrList[i].iterator(); 
    int count = -1;
	boolean found = false;
    
    while (it.hasNext() && !found) {
            count++;
            e = it.next();
            if (e.key == k) {
           	 hashArrList[i].remove(count);
                    found = true;
            }            
    	}
	} 
}



 