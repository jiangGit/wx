package top.akte.response.pdd;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PddGoodsOptItemVo {

    /**
     * 层级，1-一级，2-二级，3-三级，4-四级
     */
    private Integer level;

    /**
     * id所属父ID，其中，parent_id=0时为顶级节点
     */
    private Integer parentOptId;

    /**
     * 商品标签名
     */
    private String optName;

    /**
     * 商品标签ID
     */
    private Integer optId;

}
