package heap;

import global.*;

import java.io.*;

import chainexception.ChainException;

public class HeapScan {
	String hFName;
	PageId firstPageId;
	Page page;
	PageId currentPageId;
	HFPage currentPage;
	RID curRid;


	/**

	 * Constructs a file scan by pinning the directoy header page and initializing

	 * iterator fields.

*/

	protected HeapScan(HeapFile hf){
		if(hf != null){
			try {
				currentPageId = Minibase.DiskManager.get_file_entry(hf.name);
				page = new Page();
				} catch (Exception e) {
					try {
						throw new ChainException(e, "Heap File Exception: can't get the first page.");
					} catch (ChainException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			curRid = null;
			firstPageId = new PageId();
			firstPageId.pid = currentPageId.pid;
			try {
				Minibase.BufferManager.pinPage(currentPageId, page, false);
			} catch (Exception e) {
				try {
					throw new ChainException(e, "Heap File Buffer Manager Exception");
				} catch (ChainException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			currentPage = new HFPage(page);
		}else{
			try {
				throw new ChainException( null, "Heap File Buffer Manager Exception");
			} catch (ChainException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void finalize() throws Throwable{

	}

	/**

	 * Closes the file scan, releasing any pinned pages.

*/
	public void close(){
		currentPageId = null;
        currentPage = null;
        curRid = null;

	}

	/**

	 * Returns true if there are more records to scan, false otherwise.

*/
	public boolean hasNext(){
		if(curRid == null) {
		  return false;
		} else {
		  return true;
		}

	}



	/**

	 * Gets the next record in the file scan.

	 * @param rid output parameter that identifies the returned record

	 * @throws IllegalStateException if the scan has no more elements

*/
	public Tuple getNext(RID rid){
		
			if(hasNext() == true){
			while(currentPage.nextRecord(curRid) == null){
				PageId nextPageId = currentPage.getNextPage();
				if(firstPageId.pid == nextPageId.pid){
				Minibase.BufferManager.unpinPage(currentPageId, false);
				return null;
				}
				
				Minibase.BufferManager.unpinPage(currentPageId, false);
				currentPageId = nextPageId;
				Minibase.BufferManager.pinPage(currentPageId, page, false);
				
				currentPage = new HFPage(page);
				
				if(currentPage.firstRecord() != null){
					curRid = null;
					break;
					}

			}
			if(curRid != null){
				curRid = currentPage.nextRecord(curRid);
			}else{
				curRid = currentPage.firstRecord();
			}
		}
		else{
			curRid = currentPage.firstRecord();
		}

		Tuple ret = new Tuple(currentPage.selectRecord(curRid));
		rid.copyRID(curRid);

		return ret;
	}	
	
	
	
}

