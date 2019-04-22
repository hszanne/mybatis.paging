package hszanne.mybatis.paging.dialect;

import hszanne.mybatis.paging.PageInfo;
import hszanne.mybatis.paging.utils.SqlUtil;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

public abstract class PageSqlSourceWrapper implements SqlSource{

    protected PageInfo page = null;
    private SqlSource sqlSource;

    public PageSqlSourceWrapper(SqlSource sqlSource, PageInfo page ){
        this.sqlSource = sqlSource;
        this.page = page;
    }

    public SqlSource getSqlSrouce(){
        return this.sqlSource;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        if( null == this.page ){
            return getCountBoundSql(parameterObject);
        }else{
            return getPageBoundSql(parameterObject);
        }
    }

    public BoundSql getPageBoundSql(Object parameterObject) {
        BoundSql boundSql = getSqlSrouce().getBoundSql(parameterObject);
        MetaObject moBoundSql = SystemMetaObject.forObject(boundSql);
        String sql = boundSql.getSql( );

        moBoundSql.setValue("sql",  getPageBoundSql(sql) );
        return boundSql;
    }

    public BoundSql getCountBoundSql(Object parameterObject) {
        BoundSql boundSql = getSqlSrouce().getBoundSql(parameterObject);
        MetaObject moBoundSql = SystemMetaObject.forObject(boundSql);

        moBoundSql.setValue("sql", getCountBoundSqlStatement(boundSql.getSql( )));
        return boundSql;
    }

    public abstract String getPageBoundSqlStatement(String sql);

    public String getCountBoundSqlStatement(String sql) {
        return SqlUtil.sqlToCountSql(sql);
    }
}
