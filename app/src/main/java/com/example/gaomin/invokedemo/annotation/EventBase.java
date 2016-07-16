package com.example.gaomin.invokedemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gaomin on 2016/6/28.
 */

//给OnClick注解使用
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    //btn.setOnClickListener(listener)

    /**
     * setOnClickListener方法名称
     * @return
     */
    String listenerSetter();

    /**
     * listener类型
     * @return
     */
    Class<?> listenerType();

    /**
     * listener中的回调方法名称
     * @return
     */
    String CallBackMethod();
}
