package com.gxj.handler;

import com.gxj.VO.ResultVO;
import com.gxj.config.ProjectUrlConfig;
import com.gxj.exception.SellAuthorizeException;
import com.gxj.exception.SellException;
import com.gxj.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {


    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(value = SellAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
//        return new ModelAndView("redirect:"
//                .concat(projectUrlConfig.getWechatOpenAuthorize())
//                .concat("/sell/wechat/qrAuthorize")
//                .concat("?returnUrl=")
//                .concat(projectUrlConfig.getSell())
//                .concat("/sell/seller/login"));
        //TODO 跳转写死
        return new ModelAndView("redirect:https://open.weixin.qq.com/connect/qrconnect?appid=wx6ad144e54af67d87&redirect_uri=http%3A%2F%2Fsell.springboot.cn%2Fsell%2Fqr%2FoTgZpwaPfCy81yYGSxnWPocur3KI&response_type=code&scope=snsapi_login&state=https://mytestsell.mynatapp.cc/sell/wechat/qrUserInfo");
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
