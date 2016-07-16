package com.example.gaomin.invokedemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gaomin on 2016/6/28.
 */
//@Override只是在编译期间保证重写了父类的OnCreate的方法,在运行期间是不需要的

//注解使用在哪个地方,类上面
@Target(ElementType.TYPE)
//编译程序将注解存储在class文件中.让虚拟机通过反射机制读入
@Retention(RetentionPolicy.RUNTIME)

public @interface ContentView {
   int value();
}
