package com.study.xl.tymh.util;

import java.util.UUID;

/**
 * @ClassName UuidUtil
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 14:19
 * @Version 1.0
 **/
public class UuidUtil {

    public static String get32UUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
