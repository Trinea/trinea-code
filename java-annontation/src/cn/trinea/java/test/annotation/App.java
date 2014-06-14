/*
 * Copyright 2014 trinea.cn All right reserved. This software is the
 * confidential and proprietary information of trinea.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with trinea.cn.
 */
package cn.trinea.java.test.annotation;

/**
 * AnnotationExample
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014年4月24日
 */
public class App {

    @MethodInfo(
                author = "gengxinwu@anjuke.com",
                date = "2014/02/14",
                version = 2)
    public String getAppName() {
        return "anjuke";
    }
}
