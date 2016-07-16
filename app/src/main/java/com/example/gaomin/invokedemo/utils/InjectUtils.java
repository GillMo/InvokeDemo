package com.example.gaomin.invokedemo.utils;

import android.app.Activity;
import android.view.View;

import com.example.gaomin.invokedemo.annotation.ContentView;
import com.example.gaomin.invokedemo.annotation.EventBase;
import com.example.gaomin.invokedemo.annotation.ViewInject;
import com.example.gaomin.invokedemo.proxy.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by gaomin on 2016/6/28.
 */
public class InjectUtils {

    private static final String method_set_contenView = "setContentView";
    private static final String method_find_view_by_id = "findViewById";

    public static void inject(Activity activity){
        injectContentView(activity);
        injectViews(activity);
        injectEvents(activity);
    }

    /*
     *注入布局
     */
    private static void injectContentView(Activity activity) {
        //拿到字节码
        Class<? extends Activity> clazz = activity.getClass();
        //拿到这个类型的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        //拿到布局的ID
        int layoutId = contentView.value();

        //activity.setContentView(layoutId);
        //另外一种方法
        try {
            //拿到setContentView方法,通过反转
            Method method = clazz.getMethod(method_set_contenView,int.class);
            method.invoke(activity,layoutId);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    /*
    *注入控件
    */
    private static void injectViews(Activity activity) {
       Class<? extends Activity> clazz = activity.getClass();
        //拿到所以属性
        Field[] fields = clazz.getDeclaredFields();
        //遍历有注解的属性
        for(Field field :fields){
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if(viewInject != null){
                //获取注解控件ID
                int viewId = viewInject.value();
                //第一种方法
               // activity.findViewById(viewId);
                try {
                    //获取findViewById方法
                    Method method = clazz.getMethod(method_find_view_by_id,int.class);
                    //执行findViewById方法,得到控件
                    Object view = method.invoke(activity,viewId);
                    //控件给属性
                    //设置私有属性可以访问
                    field.setAccessible(true);
                    field.set(activity,view);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 注入事件
     */
    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method:methods){
            Annotation[] annotations = method.getAnnotations();
            for(Annotation annotation:annotations){
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //拿到注解上的注解
                if(annotationType!=null){
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if(eventBase != null){
                        String listenerSetter = eventBase.listenerSetter();
                        Class<?> listenerType = eventBase.listenerType();
                        String methodName = eventBase.CallBackMethod();

                        //拿到数组
                        //OnClick onClick = (OnClick)annotation;
                        //onClick.value();
                        try {
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            int[] viewIds = (int[]) valueMethod.invoke(annotation,null);
                            //设置代理对象  保证在重新回调方法之前持行我们自己的方法 代理模式
                           ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            //onClick ->toast
                            handler.addMethod(methodName,method);
                            //通过Proxy产生一个listenerType类型的代理对象
                            //listener 是一个OnClickListener对象
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),new Class<?>[]{listenerType},handler);
                            //执行我们的setOnClickListener
                            //有多个按钮需要注册监听
                            for (int viewId:viewIds){
                                View view = activity.findViewById(viewId);
                                //获取方法
                                Method setter = view.getClass().getMethod(listenerSetter,listenerType);
                                //执行
                                setter.invoke(view,listener);
                            }

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


}
