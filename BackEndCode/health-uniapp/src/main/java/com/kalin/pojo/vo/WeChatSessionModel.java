package com.kalin.pojo.vo;

import lombok.Data;

/**
 * 接收微信服务器返回参数
 */
@Data
public class WeChatSessionModel {

    /**
     * 微信服务器上辨识用户唯一id
     */
    private String openid;

    /**
     * 身份凭证 会话校验
     */
    private String session_key;

    /**
     * 错误代码
     */
    private String errcode;

    /**
     * 错误信息
     */
    private String errmsg;
}
