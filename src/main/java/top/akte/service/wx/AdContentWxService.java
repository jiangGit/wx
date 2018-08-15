package top.akte.service.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.akte.dao.AdContentMapper;
import top.akte.entity.AdContent;
import top.akte.entity.vo.AdContentVo;
import top.akte.request.ad.AdListWxRequest;
import top.akte.response.common.PageRes;
import top.akte.response.common.WxResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdContentWxService {

    @Autowired
    private AdContentMapper adContentMapper;

    public WxResponse<PageRes<AdContentVo>> adList(AdListWxRequest req){
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        Example example = new Example(AdContent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("adModuleId",req.getAdModuleId());
        criteria.andLessThanOrEqualTo("timeStart",new Date());
        criteria.andGreaterThanOrEqualTo("timeEnd",new Date());
        criteria.andEqualTo("status",2);
        example.setOrderByClause("sort_weight desc");
        List<AdContent> list = adContentMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        PageRes<AdContentVo> pageRes = new PageRes<AdContentVo>();
        pageRes.fillPageInfo(pageInfo);
        List<AdContentVo> vos = new ArrayList<>();
        list.forEach(ad ->{
            AdContentVo vo = new AdContentVo();
            BeanUtils.copyProperties(ad,vo);
            vos.add(vo);
        });
        pageRes.setList(vos);
        WxResponse response = new WxResponse();
        response.setResult(pageRes);
        return response;
    }

}
