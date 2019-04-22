package hszanne.mybatis.paging;

public interface PageInfo {
    /**
     * set the total count of this select query
     * @param count
     */
    void setTotalRecordCount(int count);
    
    /**
     * get current page number
     * @return
     */
    int getPageNumber();
    
    /**
     * get current page size
     * @return
     */
    int getPageSize();

    /**
     *
     * @return
     */
    boolean needTotalRecordCount();

    /**
     *
     * @return
     */
    boolean isOnlyGetTotalRecordCount();
}
