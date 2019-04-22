package hszanne.mybatis.paging.dialect;

import hszanne.mybatis.paging.PageInfo;
import org.apache.ibatis.mapping.SqlSource;

public class MySqlPageSqlSourceWrapper extends PageSqlSourceWrapper {

    public MySqlPageSqlSourceWrapper(SqlSource sqlSource, PageInfo page) {
        super(sqlSource, page);
    }

    @Override
    public String getPageBoundSqlStatement(String sql) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginRow = String.valueOf( (this.page.getPageNumber() - 1) * this.page.getPageSize());

        pageSql.append(sql);
        pageSql.append(" limit " + beginRow + "," + this.page.getPageSize() + " ");
        return pageSql.toString();
    }
}
