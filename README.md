# mybatis.paging
Mybatis 分页轮子

### 配置分页插件
```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource" />
    <property name="plugins">
        <bean class="hszanne.mybatis.paging.PageInterceptor">
            <property name="properties">
                <util:properties location="/WEB-INF/conf/mybatis-page.properties"/>
            </property>
            
            <!--或者-->
            <property name="dialect" value="mysql or oracle"/>
        </bean>
    </property>
</bean>
```

### mybatis-page.properties (可选)
```properties
dialect=mysql or oracle
```

### SQL Mapper映射文件
```java
import hlns.mybatis.paging.Page; 
class SelectByPaging{
    /**
    * 在接口函数中增加一个Page参数即可
    * @param page
    * @return 
    */
    List selectSomeThingsPaging(Page page);    
}

```
