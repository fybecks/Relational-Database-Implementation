package heap;

import global.*;
import diskmgr.*;
import java.io.*;
import java.util.*;
import chainexception.ChainException;

public class HeapFile implements GlobalConst{
	public String name;
	/**

	   * If the given name already denotes a file, this opens it; otherwise, this

	   * creates a new empty file. A null name produces a temporary heap file which

	   * requires no DB entry.

	   */

	  public HeapFile(String name){
		  
		  boolean neoRecordEntryMade = false;
          boolean isPagePin = false;
          boolean succeed = false;         
          
          if(name == null)
			try {
				throw new ChainException(null, "Heapfile name should not be null!");
			} catch (ChainException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
          this.name = new String(name);
          PageId headerPageId = null;
        
                          if((headerPageId = Minibase.DiskManager.get_file_entry(name)) != null)
                                  return;
                         
                          
                          headerPageId = new PageId();
                          headerPageId = Minibase.DiskManager.allocate_page();
                          Minibase.DiskManager.add_file_entry(name, headerPageId);                
                 
                  neoRecordEntryMade = true;
                 
                  Page headerPage = new Page();
               
                	  	Minibase.BufferManager.pinPage(headerPageId, headerPage, false);
                                
                  isPagePin = true;
                 
                  HFPage hfPage = new HFPage(headerPage);
            
                          hfPage.initDefaults();
                          hfPage.setCurPage(headerPageId);
                          hfPage.setPrevPage(headerPageId);
                          hfPage.setNextPage(headerPageId);
                          
                               
                  succeed = true;
       
                          if(isPagePin) {
                        	  Minibase.BufferManager.unpinPage(headerPageId, true);
                          }
              
                          if((neoRecordEntryMade) && (!succeed)) {
                           
                                	  		{
                                	  			Minibase.DiskManager.delete_file_entry(name);
                                	  			Minibase.DiskManager.deallocate_page(headerPageId);}
                      
                          }
    
	  }

	 

	/**

	   * Deletes the heap file from the database, freeing all of its pages.

	   */

	  public void deleteFile() {
		  return ;
	  }

	 

	  /**

	   * Inserts a new record into the file and returns its RID.

	   *

	   * @throws IllegalArgumentException if the record is too large

	   */

	  public RID insertRecord(byte[] record){
		  if (record.length > PAGE_SIZE - HFPage.HEADER_SIZE) {
			  throw new IllegalArgumentException("Record too large");
			  }
		    
		  PageId pidOfCurPage = new PageId();
		  PageId nextPageId = new PageId();
		  PageId headerPageId = null;
		  Page curPage = new Page();
		  HFPage hfPage = null;
		  RID frid = null;
		  boolean isCurPagePin = false;
		  boolean curPageDirty = false;
		  boolean isNewPageCreat = false;
		  boolean succeed = false;
		  try {
			  	try {
                      headerPageId = Minibase.DiskManager.get_file_entry(name);
			  	} catch (Exception e) {
                   
			  	}
             
              if (headerPageId == null)
                      System.out.println("The heapfile has been deleted already.");
             
              pidOfCurPage.copyPageId(headerPageId);
              
              do {
                      
                      try {
                    	  	Minibase.BufferManager.pinPage(pidOfCurPage, curPage, false);
                      } catch (Exception e) {
                        
                      }
                      isCurPagePin = true;

                     
                      hfPage = new HFPage(curPage);
                      try {
                              frid = hfPage.insertRecord(record);
                             
                              
                              if(frid != null) {
                                      succeed = true;
                                      curPageDirty = true;
                                      return frid;
                              }
                             
                              nextPageId.copyPageId(hfPage.getNextPage());
                      } catch (Exception e) {
                         
                      }

                     
                      try {
                    	  	Minibase.BufferManager.unpinPage(pidOfCurPage, false);
                      } catch (Exception e) {
                          
                      }
                      isCurPagePin = false;
                      pidOfCurPage.copyPageId(nextPageId);
              } while (pidOfCurPage.pid != headerPageId.pid);
             
              
            	  	pidOfCurPage = Minibase.DiskManager.allocate_page();
           
              isNewPageCreat = true;
             
         
            	  	Minibase.BufferManager.pinPage(pidOfCurPage, curPage, false);
          
              isCurPagePin = true;
             
              hfPage = new HFPage(curPage);
              try {
                      hfPage.initDefaults();
                      hfPage.setCurPage(pidOfCurPage);
                      frid = hfPage.insertRecord(record);
              } catch (Exception e) {
                   
              }
             
              
              if(frid == null)
				try {
					throw new SpaceNotAvailableException(null, "failed to insert record in a new page");
				} catch (SpaceNotAvailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
             
              
              try {
            	 
				heapCreate(headerPageId, pidOfCurPage, hfPage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
              curPageDirty = true;
              succeed = true;
      }
      finally {
              try {
                      // unpin the page if it has been isPagePin
                      if(isCurPagePin) {
                    	  	Minibase.BufferManager.unpinPage(pidOfCurPage, curPageDirty);
                      }
              } catch (Exception e) {
                    
              }
              finally {
                      
                      if((isNewPageCreat) && (!succeed)) {
                              try {
                            	  Minibase.BufferManager.freePage(pidOfCurPage);
                              }
                              catch(Exception e) {
                                   
                              }
                      }
              }
      }
     
      return frid;


	  }

	 

	  /**

	   * Reads a record from the file, given its id.

	   *

	   * @throws IllegalArgumentException if the rid is invalid

	   */

	  public byte[] selectRecord(RID rid){
		  return null;
	  } 

	 

	/**

	   * Updates the specified record in the heap file.

	   *

	   * @throws IllegalArgumentException if the rid or new record is invalid

	   */

	  public boolean updateRecord(RID rid, Tuple newtuple)
             
{      
      return false;
   
}
 

	 

	  /**

	   * Deletes the specified record from the heap file.

	   *

	   * @throws IllegalArgumentException if the rid is invalid

	   */

	  public boolean deleteRecord(RID rid)
      {
		  return false;
      }

	  


	 

	/**

	   * Gets the number of records in the file.

	   */

	  public int getRecCnt(){
		  int recCnt = 0;
          PageId pidOfCurPage = new PageId();
          PageId nextPageId = new PageId();
          PageId headerPageId = null;
          Page curPage = new Page();
          HFPage hfPage = null;
          boolean isCurPagePin = false;
          try {
                  try {
                          headerPageId = Minibase.DiskManager.get_file_entry(name);
                  } catch (Exception e) {
                          try {
							throw new ChainException(e, "getRecCnt() Failed");
						} catch (ChainException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                  }
                 
                  if (headerPageId == null)
					try {
						throw new ChainException(null, "The heapfile has been deleted already.");
					} catch (ChainException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                 
                  pidOfCurPage.copyPageId(headerPageId);
                  do {
                          
                          try {
                        	  	Minibase.BufferManager.pinPage(pidOfCurPage, curPage, false);
                          } catch (Exception e) {
                                  try {
									throw new ChainException(e, "getRecCnt() Failed");
								} catch (ChainException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                          }
                          isCurPagePin = true;

                          
                          hfPage = new HFPage(curPage);
                          try {
                                  RID curRID = hfPage.firstRecord();
                                  while(curRID != null) {
                                          recCnt ++;
                                          curRID = hfPage.nextRecord(curRID);
                                  }
                                  nextPageId.copyPageId(hfPage.getNextPage());
                          } catch (Exception e) {
                                  try {
									throw new ChainException(e, "getRecCnt() Failed");
								} catch (ChainException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                          }

                          // Unpin current page and go next
                          try {
                        	  	Minibase.BufferManager.unpinPage(pidOfCurPage, false);
                          } catch (Exception e) {
                                  try {
									throw new ChainException(e, "getRecCnt() Failed");
								} catch (ChainException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                          }
                          isCurPagePin = false;
                          pidOfCurPage.copyPageId(nextPageId);
                  } while (pidOfCurPage.pid != headerPageId.pid);
          }
          finally {
                  
                  if(isCurPagePin) {
                          try {
                        	  	Minibase.BufferManager.unpinPage(pidOfCurPage, false);
                          } catch (Exception e) {
                                  try {
									throw new ChainException(e, "getRecCnt() Failed and page not unisPagePin");
								} catch (ChainException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                          }
                  }
          }
         
          return recCnt;

		  
	  }

	 

	  /**

	   * Searches the directory for a data page with enough free space to store a

	   * record of the given size. If no suitable page is found, this creates a new

	   * data page.

	   */

	  protected PageId getAvailPage(int reclen){
		return null;  
	  }
	  
	  private void heapCreate(PageId headerPageId, PageId newPageId, HFPage newPage) throws IOException
      {
      
      boolean isHeaderPin = false;
      boolean isTailPin = false;
      boolean headerDirty = false;
      boolean tailDirty = false;
      PageId tailPageId = new PageId();
      Page headerPage = new Page();
      Page tailPage = new Page();
      try {
              try {
            	  	Minibase.BufferManager.pinPage(headerPageId, headerPage, false);
              } catch (Exception e) {
                      try {
						throw new ChainException(e, "linkHFPage() Failed");
					} catch (ChainException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
              }
             
              isHeaderPin = true;
             
              HFPage headerHFPage = new HFPage(headerPage);
              HFPage tailHFPage = null;
              try {
                      tailPageId.copyPageId(headerHFPage.getPrevPage());
              } catch (Exception e) {
                      try {
						throw new ChainException(e, "linkHFPage() Failed");
					} catch (ChainException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
              }
              if(tailPageId.pid == headerPageId.pid) {
                      tailHFPage = headerHFPage;
              }
              else {
                      try {
                    	  	Minibase.BufferManager.pinPage(tailPageId, tailPage, false);
                      } catch (Exception e) {
                              try {
								throw new ChainException(e, "linkHFPage() Failed");
							} catch (ChainException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                      }
                     
                      isTailPin = true;
                      tailHFPage = new HFPage(tailPage);
              }
             
              newPage.setNextPage(headerPageId);
			  newPage.setPrevPage(tailPageId);
			  headerHFPage.setPrevPage(newPageId);
			  headerDirty = true;
			  tailHFPage.setNextPage(newPageId);
			  tailDirty = true;
      }
      finally {
              try {
                      
                      if(isHeaderPin) {
                    	  	Minibase.BufferManager.unpinPage(headerPageId, headerDirty);
                      }
              } catch (Exception e) {
                      try {
						throw new ChainException(e, "linkHFPage() Failed and page not unisPagePin");
					} catch (ChainException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
              }
              finally {
                      if(isTailPin) {
                              try {
                            	  	Minibase.BufferManager.unpinPage(tailPageId, tailDirty);
                              }
                              catch(Exception e) {
                                      try {
										throw new ChainException(e, "linkHFPage() Failed and page not unisPagePin");
									} catch (ChainException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
                              }
                      }
              }
      	}
	  }
		
	public HeapScan openScan() {
		
            return new HeapScan(this);
    

	}


}
