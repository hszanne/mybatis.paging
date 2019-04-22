package hszanne.mybatis.paging;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import hszanne.mybatis.paging.dialect.PageSqlSourceWrapper;
import hszanne.mybatis.paging.dialect.PageSqlSourceWrapperFactory;
import hszanne.mybatis.paging.utils.MappedStatementUtil;


@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PageInterceptor implements Interceptor{

    private String dialect = "mysql"; // 数据库类型(默认为mysql)

    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mStatement = ( MappedStatement )invocation.getArgs()[0];
        if( !SqlCommandType.SELECT.equals(mStatement.getSqlCommandType()) ) {
            // 将执行权交给下一个拦截器
            return invocation.proceed();
        }

        Executor executor = ( Executor ) invocation.getTarget();
        PageInfo page = null;
        
        Object paramObject = invocation.getArgs()[1];
        
        if( null == paramObject ){
            return invocation.proceed();
        }

        if( Map.class.isAssignableFrom( paramObject.getClass() ) ){
            for (Map.Entry<String, Object> element : ((Map<String, Object>)paramObject).entrySet()) {
                if( element.getValue() instanceof PageInfo ){
                    page = (PageInfo)element.getValue();
                    break;
                }
            }
        }else if( paramObject instanceof PageInfo ){
            page = (PageInfo) paramObject;
        }else{
            return invocation.proceed();
        }

        if( page == null ){
            return invocation.proceed();
        }

        MappedStatement countMappedStatement = getPageMapStatement("count", this.dialect, mStatement, null);
        
        if( page.needTotalRecordCount() || page.isOnlyGetTotalRecordCount() ) {
            List<Integer> count = executor.query(countMappedStatement, paramObject, RowBounds.DEFAULT, (ResultHandler<?>)invocation.getArgs()[3]);
            page.setTotalRecordCount( count.get(0) );
        }

        if( page.isOnlyGetTotalRecordCount() ){
            return Collections.emptyList();
        }

        MappedStatement pageMappedStatement = getPageMapStatement("page", this.dialect, mStatement, page);
        invocation.getArgs()[0] = pageMappedStatement;

        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是Executor类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public synchronized MappedStatement getPageMapStatement( String id, String dialect, MappedStatement srcMapStatement, PageInfo page ){
        MappedStatement diaMapSta;
        if( "page".equals( id ) ){
            PageSqlSourceWrapper pageSqlSource = PageSqlSourceWrapperFactory.getPageSqlSource(dialect, srcMapStatement.getSqlSource(), page);
            diaMapSta = MappedStatementUtil.copyPageMappedStatement(srcMapStatement, "page", pageSqlSource);
            return diaMapSta;
        }else if( "count".equals( id ) ){
            PageSqlSourceWrapper countSqlSource = PageSqlSourceWrapperFactory.getPageSqlSource(dialect, srcMapStatement.getSqlSource(), null);
            diaMapSta = MappedStatementUtil.copyCountMappedStatement(srcMapStatement, "count", countSqlSource);
            return diaMapSta;
        }
        return null;
    }

    @Override
    public void setProperties(Properties properties) {
        String dialect = properties.getProperty("dialect");
        if( null != dialect ){
            this.dialect = dialect.toLowerCase();
        }
    }
}
