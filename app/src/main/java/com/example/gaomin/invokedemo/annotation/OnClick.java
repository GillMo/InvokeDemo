package com.example.gaomin.invokedemo.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gaomin on 2016/6/28.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnClickListener"
        ,listenerType = View.OnClickListener.class
        ,CallBackMethod = "onClick")
public @interface OnClick {

    int[] value();
}
