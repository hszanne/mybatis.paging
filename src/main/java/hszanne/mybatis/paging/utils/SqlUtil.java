package hszanne.mybatis.paging.utils;

import hszanne.mybatis.paging.PageInfo;

import java.text.MessageFormat;

public class SqlUtil {

    public static String sqlToCountSql( String sql ){
        int firstFromPos = sql.toLowerCase().indexOf(" from ");
        return MessageFormat.format("select count(*) {0}", sql.substring(firstFromPos));
    }

    /**
     *
     * @param sql
     * @param begin include
     * @param end exclude
     * @return
     */
    public static String sqlToOraclePageSql(String sql, int begin, int end){
        String lowerSql = sql.toLowerCase();
        int firstSelectPos = lowerSql.indexOf("select ");
        int firstFromPos = sql.toLowerCase().indexOf(" from ");

        String columns = sql.substring(firstSelectPos + "select ".length(), firstFromPos);
        String table = sql.substring(firstFromPos+ " from ".length());

        return MessageFormat.format(
                "select * from ( select {0}, rownum as _rn_ from {1} where rownum < {2,number,#} ) as _jcl_ where _rn_ >= {3,number,#}",
                columns,
                table,
                end,
                begin);
    }
}
