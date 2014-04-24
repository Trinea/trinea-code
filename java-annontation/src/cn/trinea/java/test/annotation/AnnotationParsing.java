/*
 * Copyright 2014 trinea.cn All right reserved. This software is the
 * confidential and proprietary information of trinea.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with trinea.cn.
 */
package cn.trinea.java.test.annotation;

import java.lang.reflect.Method;

/**
 * AnnotationParsing
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014年4月24日
 */
public class AnnotationParsing {

    public static void main(String[] args) {

        try {
            Class cls = AnnotationParsing.class.getClassLoader().loadClass("cn.trinea.java.test.annotation.AnnotationExample");
            for (Method method : cls.getMethods()) {
                MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
                if (methodInfo != null) {
                    System.out.println(methodInfo.author());
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
