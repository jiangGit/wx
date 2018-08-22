package top.akte.response.pdd;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PddGoodsCatItemVo {

    /**
     * 层级，1-一级，2-二级，3-三级，4-四级
     */
    private Integer level;

    /**
     * id所属父ID，其中，parent_id=0时为顶级节点
     */
    private Integer parentCatId;

    /**
     * 商品类目名
     */
    private String catName;

    /**
     * 商品类目ID
     */
    private Integer catId;

}
