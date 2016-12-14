package xyz.mrseng.fasttranslate.service.protocol;

import xyz.mrseng.fasttranslate.domain.TranslateBean;
import xyz.mrseng.fasttranslate.global.exception.NetworkDisabledException;

/**
 * Created by MrSeng on 2016/12/14.
 * 翻译协议的基类
 */

public abstract class BaseTransProtocol {
    /** 外界根据对应协议获取翻译信息的方法 */
    public abstract TranslateBean getData(String text, String from, String to) throws NetworkDisabledException;
}
