package top.akte.response.common;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class PageRes<T>  {
    private List<T> list;
    private long totalCount;
    private int totalPage;
    private int currentPage;

    public void fillPageInfo(PageInfo pageInfo){
        this.totalCount = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
        this.currentPage = pageInfo.getPageNum();
    }
}
