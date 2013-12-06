package com.ajjpj.amapper.javabean;

import com.ajjpj.amapper.core.tpe.AType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


/**
 * @author arno
 */
public class JavaBeanTypes {
    private static final Map<Class<?>, Class<?>> boxedEquivalents = new HashMap<Class<?>, Class<?>>();
    static {
        boxedEquivalents.put(Boolean.  TYPE, Boolean.class);
        boxedEquivalents.put(Character.TYPE, Character.class);
        boxedEquivalents.put(Byte.     TYPE, Byte.class);
        boxedEquivalents.put(Short.    TYPE, Short.class);
        boxedEquivalents.put(Integer.  TYPE, Integer.class);
        boxedEquivalents.put(Long.     TYPE, Long.class);
        boxedEquivalents.put(Float.    TYPE, Float.class);
        boxedEquivalents.put(Double.   TYPE, Double.class);
    }

    public static <T> JavaBeanType<T> create(Class<T> cls) {
        return new JavaBeanType<T>(cls);
    }
    public static <T,P> SingleParamBeanType<T,P> create (Class<T> cls, Class<P> param) {
        return new SingleParamBeanType<T,P>(cls, param);
    }
    public static JavaBeanType<?> create(Type javaType) {
        if(javaType instanceof Class<?>) {
            return create ((Class<?>) javaType);
        }

        final ParameterizedType pt = (ParameterizedType) javaType;
        if(pt.getActualTypeArguments().length == 1) {
            return create(rawType(pt), rawType(pt.getActualTypeArguments()[0]));
        }
        return create(rawType(pt));
    }

    public static boolean isSubtypeOrSameOf(AType tpe, Class<?> cls) {
        return tpe instanceof JavaBeanType && cls.isAssignableFrom(((JavaBeanType) tpe).cls);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> normalized (Class<T> cls) {
        final Class<?> boxed = boxedEquivalents.get(cls);
        return boxed != null ? (Class<T>) boxed : cls;
    }

    public static Class<?> rawType(Type javaType) {
        if(javaType instanceof Class<?>) {
            return (Class<?>) javaType;
        }

        return rawType(((ParameterizedType) javaType).getRawType());
    }
}