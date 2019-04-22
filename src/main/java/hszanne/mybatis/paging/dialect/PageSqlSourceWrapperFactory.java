package hszanne.mybatis.paging.dialect;

import hszanne.mybatis.paging.PageInfo;
import org.apache.ibatis.mapping.SqlSource;


public class PageSqlSourceWrapperFactory {
    public static PageSqlSourceWrapper getPageSqlSource(String dialect, SqlSource sqlSource, PageInfo page ) {
        if( "mysql".equals( dialect ) ){
            return new MySqlPageSqlSourceWrapper( sqlSource, page );
        }else if( "oracle".equals(dialect) ){
            return new OraclePageSqlSourceWrapper(sqlSource, page);
        }
        return null;
    }
}
