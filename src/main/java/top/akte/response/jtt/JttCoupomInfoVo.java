package top.akte.response.jtt;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class JttCoupomInfoVo {

    private Long beginTime;//券有效使用开始时间

    private Long endTime;//券有效使用结束时间

    private Float discount;//券面额

    private String link;//券链接

    private Integer num	;//券总张数

    private Integer remainNum;//券剩余张数

    private String platform;//券使用平台

    private Integer quota;//券消费限额

    private Long takeBeginTime;//券领取开始时间

    private Long takeEndTime;//券领取结束时间

    private String yn;//券有效状态
}
