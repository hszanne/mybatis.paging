package hszanne.mybatis.paging.dialect;

import hszanne.mybatis.paging.PageInfo;
import hszanne.mybatis.paging.utils.SqlUtil;
import org.apache.ibatis.mapping.SqlSource;

public class OraclePageSqlSourceWrapper extends PageSqlSourceWrapper {
    public OraclePageSqlSourceWrapper(SqlSource sqlSource, PageInfo page) {
        super(sqlSource, page);
    }

    @Override
    public String getPageBoundSqlStatement(String sql) {
        int begin = (this.page.getPageNumber() - 1) * this.page.getPageSize() + 1;
        int end = begin + page.getPageSize();

        return SqlUtil.sqlToOraclePageSql(sql, begin, end);
    }
}
