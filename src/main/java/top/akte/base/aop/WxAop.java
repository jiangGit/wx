package top.akte.base.aop;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.akte.base.config.RedisConnection;
import top.akte.entity.vo.WxUserInfoVo;
import top.akte.request.common.WxRequest;
import top.akte.response.common.ResponseCodeConstant;
import top.akte.response.common.WxResponse;
import top.akte.util.IdGen;
import top.akte.util.ValidationException;
import top.akte.util.Validator;
import top.akte.util.WxException;

import java.util.concurrent.TimeUnit;

@Log4j
@Aspect
@Component
public class WxAop {
    @Autowired
    private RedisConnection redisConnection;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final long T = 30;

    @Autowired
    private Validator validator;

    @Around("within(top.akte.controller.wx.*)")
    public Object sessionCheck(ProceedingJoinPoint proceedingJoinPoint){
        log.info(proceedingJoinPoint.getTarget());
        try {
            //强制所有controller的参数是继承WxRequest，返回是继承WxResponse
            WxRequest req = (WxRequest) proceedingJoinPoint.getArgs()[0];
            String sessionId = req.getSessionId();
            if (StringUtils.isBlank(sessionId)){
                sessionId = IdGen.uuid();
                redisConnection.setEx(sessionId,new WxUserInfoVo(),T, TimeUnit.MINUTES);
                req.setOpenId(null);
                req.setSessionId(sessionId);
            }else {
                WxUserInfoVo userInfo =  (WxUserInfoVo) redisConnection.get(sessionId);
                if (userInfo != null){
                    req.setOpenId(userInfo.getOpenId());
                    redisTemplate.expire(sessionId,T,TimeUnit.MINUTES);
                }else {
                    sessionId = IdGen.uuid();
                    redisConnection.setEx(sessionId,new WxUserInfoVo(),T, TimeUnit.MINUTES);
                    req.setOpenId(null);
                    req.setSessionId(sessionId);
                }
            }
            WxResponse res = (WxResponse) proceedingJoinPoint.proceed();
            res.setSessionId(sessionId);
            return res;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        WxResponse res = new WxResponse();
        res.setSessionId(IdGen.uuid());
        res.setCode(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode());
        res.setContent(ResponseCodeConstant.SYS_EXCEPTION.getResponseDesc());
        return res;
    }

    @Around("within(top.akte.service.wx.*)")
    public Object wxServiceTemplate(ProceedingJoinPoint proceedingJoinPoint){
        WxResponse res = new WxResponse();
        try {
            validator.validate(proceedingJoinPoint.getArgs()[0]);
            res = (WxResponse) proceedingJoinPoint.proceed();
            res.setCode(ResponseCodeConstant.SUCCESS.getResponseCode());
            res.setContent(ResponseCodeConstant.SUCCESS.getResponseDesc());
        }catch (ValidationException e) {
            res.setCode(ResponseCodeConstant.REQUEST_ILLEGAL.getResponseCode());
            res.setContent(ResponseCodeConstant.REQUEST_ILLEGAL.getResponseDesc() + ":" + e.getMessage());
        }catch (WxException e) {
            res.setCode(e.getCode());
            res.setContent(e.getMessage());
        }catch (Throwable e){
            res.setCode(ResponseCodeConstant.SYS_EXCEPTION.getResponseCode());
            res.setContent(ResponseCodeConstant.SYS_EXCEPTION.getResponseDesc());
            log.error(e.getMessage(),e);
        }
        return res;
    }
}
