package hszanne.mybatis.paging.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;

public class MappedStatementUtil {
    private static MappedStatement.Builder copy( MappedStatement src, String id, SqlSource sqlSource ){
        MappedStatement.Builder builder = new MappedStatement.Builder(
                src.getConfiguration(), id, sqlSource, src.getSqlCommandType() );

        builder.resource(src.getResource())
        .fetchSize(src.getFetchSize())
        //private Configuration configuration;
        //private String id;
        .timeout(src.getTimeout())
        //private StatementType statementType;
        .resultSetType(src.getResultSetType())
        //private SqlSource sqlSource;
        .cache(src.getCache())
        .parameterMap(src.getParameterMap())
        .resultMaps(src.getResultMaps())
        .flushCacheRequired( src.isFlushCacheRequired() )
        .useCache(src.isUseCache())
        .resultOrdered( src.isResultOrdered() )
        .keyGenerator( src.getKeyGenerator() )
        .keyProperty( toSplitString(src.getKeyProperties(), ",") )
        .keyColumn( toSplitString(src.getKeyColumns(), ",") )
        .databaseId(src.getDatabaseId())
        .lang(src.getLang())
        .resultSets( toSplitString(src.getResultSets(), ",") )
        ;
        return builder;
    }

    public static MappedStatement copyPageMappedStatement( MappedStatement src, String id, SqlSource sqlSource ){
        return copy(src, src.getId()+"."+id, sqlSource).build();
    }

    public static MappedStatement copyCountMappedStatement( MappedStatement src, String id, SqlSource sqlSource ){
        String countId = src.getId()+"."+id;
        List<ResultMap> resultMaps = new ArrayList<>();
        ResultMap inlineResultMap = new ResultMap.Builder(
                src.getConfiguration(),
                countId + "-count",
                Integer.class,
                new ArrayList<>(),
                null).build();
        resultMaps.add(inlineResultMap);

        return copy(src, countId, sqlSource)
                .resultMaps(resultMaps)
                .build()
                ;
    }

    public static String toSplitString( String[] arr, String split ){
        if( null == arr || arr.length == 0 ){
            return "";
        }
        StringBuffer buffer = new StringBuffer(100);
        for (int i = 0; i < arr.length; i++) {
            buffer.append(arr[i]);
            if( i != arr.length - 1 ){
                buffer.append(split);
            }
        }
        return buffer.toString();
    }
}
