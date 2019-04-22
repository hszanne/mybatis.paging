package hszanne.mybatis.paging;

public class PageFactory {
    /**
     * 创建分页参数
     * @param p 第几页
     * @param s 页面大小
     * @param defaultSize 默认页面记录数
     * @param maxSize 最大页面记录数
     * @return 分页参数
     */
    public static Page createPage(Integer p, Integer s, Integer defaultSize, Integer maxSize) {
        if (null == p || p < 1) {
            p = 1;
        }
        if (null == s || s <= 0) {
            s = defaultSize;
        }
        if (s > maxSize) {
            s = maxSize;
        }
        return new Page(p, s);
    }
}
