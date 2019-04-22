package hszanne.mybatis.paging.test;

import hszanne.mybatis.paging.utils.SqlUtil;

public class SqlUtilTest {
    public static void main(String[] args){
        System.out.println(SqlUtil.sqlToOraclePageSql("select abceeffdk ,ll , pp.*  From a", 1, 2));
        System.out.println(SqlUtil.sqlToCountSql("select abceeffdk ,ll , pp.*  From a left join b "));
    }
}
