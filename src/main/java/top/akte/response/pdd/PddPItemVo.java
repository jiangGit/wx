package top.akte.response.pdd;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PddPItemVo {

    /**
     *
     推广位名称
     */
    private String name;

    /**
     * 推广位生成时间
     */
    private Long createTime;

    /**
     * 推广位ID
     */
    private String p_id;


}
