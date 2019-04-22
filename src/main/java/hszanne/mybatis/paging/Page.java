package hszanne.mybatis.paging;

public class Page implements PageInfo{
    public static final int DEFAULT_PAGE_SIZE = 10;

    private int pageSize    = DEFAULT_PAGE_SIZE;
    private int pageNumber  = 1;
    private int totalPageCount  = 0;
    private int totalRecordCount = 0;
    private boolean needTotalRecordCount = true;
    private boolean onlyGetTotalRecordCount = false;

    public Page() {
        
    }

    public Page(Integer page, Integer size) {
        setPageNumber(page);
        setPageSize(size);
        
        needTotalRecordCount = true;
    }
    
    public Page(Integer page, Integer size, boolean needTotalRecordCount) {
        setPageNumber(page);
        setPageSize(size);
        
        this.needTotalRecordCount = needTotalRecordCount;
    }

    @Override
    public void setTotalRecordCount(int count) {
        this.totalRecordCount = count;
        this.totalPageCount = (totalRecordCount / this.pageSize) + ((totalRecordCount % this.pageSize == 0) ? 0 : 1);
    }
    
    public int getTotalRecordCount() {
        return totalRecordCount;
    }
    
    public void setPageNumber(Integer pageNumber) {
        if( null == pageNumber ) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
        if( this.pageNumber < 1 ){
            this.pageNumber = 1;
        }
    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        if( null == pageSize ) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
        if( this.pageSize <= 0 ){
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
    }
    
    @Override
    public int getPageSize() {
        return this.pageSize;
    }
    
    public int getTotalPageCount() {
        return totalPageCount;
    }
    
    /**
     * next page
     * @return
     */
    public boolean nextPage(){
        if( this.totalPageCount > this.pageNumber ){
            this.pageNumber++;
            return true;
        }
        return false;
    }

    @Override
    public boolean needTotalRecordCount() {
        return this.needTotalRecordCount;
    }

    public boolean isNeedTotalRecordCount() {
        return needTotalRecordCount;
    }

    public void setNeedTotalRecordCount(boolean needTotalRecordCount) {
        this.needTotalRecordCount = needTotalRecordCount;
    }

    public boolean isOnlyGetTotalRecordCount() {
        return onlyGetTotalRecordCount;
    }

    public void setOnlyGetTotalRecordCount(boolean onlyGetTotalRecordCount) {
        this.onlyGetTotalRecordCount = onlyGetTotalRecordCount;
    }
}
