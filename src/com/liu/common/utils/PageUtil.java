package com.liu.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * activiti查询分页数据工具类
 */
public class PageUtil {

    public static int PAGE_SIZE = 15;

    /**
     * 
     * <p>Title: init</p>
     * <p>Description:初始化分页参数 </p>
     * @param page 分页参数
     * @param request 
     * @return
     */
    public static int[] init(Page<?> page, HttpServletRequest request) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(request.getParameter("page_index"), "1"));
        page.setPageNo(pageNumber);
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(request.getParameter("page_size"), String.valueOf(PAGE_SIZE)));
        page.setPageSize(pageSize);
        int firstResult = page.getFirst() - 1;
        int maxResults = page.getPageSize();
        return new int[]{firstResult, maxResults};
    }

}
