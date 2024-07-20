package com.kalin.pojo.vo;

import lombok.Data;

/**
 * 接收小程序数据
 */

@Data
public class WeChatModel {

    /**
     * wx.login返回值
     */
    private String code;

    /**
     * 用户唯一标识
     */
    private String openid;
}
