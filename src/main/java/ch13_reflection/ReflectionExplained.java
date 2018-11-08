package ch13_reflection;

import com.google.common.base.Function;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.*;

/**
 * Guava 的 Java 反射机制工具类
 *
 * @author yuxuan
 * @create 2018-07-04 下午9:39
 **/
public class ReflectionExplained {
    public static void main(String[] args) {
        // 获取与使用TypeToken
        typeToken();


        TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {};
    }

    /**
     * 获取与使用TypeToken
     * 可以在运行时都能够操作和查询泛型类型
     */
    private static void typeToken() {
        /**
         * 获取一个基本的、原始类的TypeToken
         */
        TypeToken<String> stringTok = TypeToken.of(String.class);
        TypeToken<Integer> intTok = TypeToken.of(Integer.class);

        /**
         * 获得一个含有泛型的类型的TypeToken
         */
        TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {};

        /**
         * 指向一个通配符类型
         */
        TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {};


        /**
         * TypeToken提供了一种方法来动态的解决泛型类型参数
         */
        TypeToken<Map<String, BigInteger>> mapToken = mapToken(
                TypeToken.of(String.class),
                TypeToken.of(BigInteger.class)
        );
        TypeToken<Map<Integer, Queue<String>>> complexToken = mapToken(
                TypeToken.of(Integer.class),
                new TypeToken<Queue<String>>() {}
        );

        /**
         * 查询
         * TypeToken支持很多种类能支持的查询，但是也会把通用的查询约束考虑在内
         *
         * 支持的查询操作包括：
         * 方法	                    描述
         * getType()	            获得包装的java.lang.reflect.Type
         * getRawType()	            返回大家熟知的运行时类
         * getSubtype(Class<?>)	    返回那些有特定原始类的子类型。举个例子，如果这有一个Iterable并且参数是List.class，那么返回将是List
         * getSupertype(Class<?>)	产生这个类型的超类，这个超类是指定的原始类型。举个例子，如果这是一个Set并且参数是Iterable.class，结果将会是Iterable
         * isAssignableFrom(type)	如果这个类型是 assignable from 指定的类型，并且考虑泛型参数，返回true。List<? extends Number>是assignable from List，但List没有
         * getTypes()	            返回一个Set，包含了这个所有接口，子类和类是这个类型的类。返回的Set同样提供了classes()和interfaces()方法允许你只浏览超类和接口类
         * isArray()	            检查某个类型是不是数组，甚至是<? extends A[]>
         * getComponentType()	    返回组件类型数组
         */

        // 获得包装的java.lang.reflect.Type
        Type mapTokenType = mapToken.getType();
        // type: java.util.Map<java.lang.String, java.math.BigInteger>
        println("type", mapTokenType);

        // 返回大家熟知的运行时类
        Class<? super Map<String, BigInteger>> rawType = mapToken.getRawType();
        // rawType: interface java.util.Map
        println("rawType", rawType);

        // 返回那些有特定原始类的子类型
        TypeToken<? extends Map<String, BigInteger>> subtype = mapToken.getSubtype(TreeMap.class);
        // subtype: java.util.TreeMap<java.lang.String, java.math.BigInteger>
        println("subtype", subtype);

        // 产生这个类型的超类，这个超类是指定的原始类型
        TypeToken<? super Map<String, BigInteger>> supertype = mapToken.getSupertype(Map.class);
        // supertype: java.util.Map<java.lang.String, java.math.BigInteger>
        println("supertype", supertype);

        // 包含所有接口，子类和类是这个类型的类
        TypeToken<Map<String, BigInteger>>.TypeSet typeSet = mapToken.getTypes();
        // typeSet: [java.util.Map<java.lang.String, java.math.BigInteger>]
        println("typeSet", typeSet);

        // 检查某个类型是不是数组
        boolean isArray = mapToken.isArray();
        // isArray: false
        println("isArray", isArray);

        TypeToken<?> componentType = stringListTok.getComponentType();

        println("componentType", componentType.toString());


        /**
         * resolveType
         */
        TypeToken<Function<Integer, String>> funToken = new TypeToken<Function<Integer, String>>() {};

        TypeToken<?> funResultToken = funToken.resolveType(Function.class.getTypeParameters()[1]);

        println("funResultToken", funResultToken);

        try {
            TypeToken<?> entrySetToken = funToken.resolveType(Map.class.getMethod("entrySet").getGenericReturnType());
            println("entrySetToken", entrySetToken);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        /**
         * Invokable
         */
//        Modifier.isPublic(method.getModifiers();
//        invokable.isPublic()
//        !(Modifier.isPrivate(method.getModifiers()) || Modifier.isPublic(method.getModifiers()))
//        invokable.isPackagePrivate()

        // 方法是否能够被子类重写
//        !(Modifier.isFinal(method.getModifiers())
//                || Modifiers.isPrivate(method.getModifiers())
//                || Modifiers.isStatic(method.getModifiers())
//                || Modifiers.isFinal(method.getDeclaringClass().getModifiers()))
//        invokable.isOverridable()

//        for (Annotation annotation : method.getParameterAnnotations[0]) {
//            if (annotation instanceof Nullable) {
//                return true;
//            }
//        }
//        return false;

//        invokable.getParameters().get(0).isAnnotationPresent(Nullable.class)

//        invokable.isPublic();
//        invokable.getParameters();
//        invokable.invoke(object, args);
//
//        Invokable<List<String>, ?> invokable = new TypeToken<List<String>>()        {}.method(getMethod);
//        invokable.getReturnType(); // String.class
    }

    private static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
        return new TypeToken<Map<K, V>>() {}
                .where(new TypeParameter<K>() {}, keyToken)
                .where(new TypeParameter<V>() {}, valueToken);
    }

    private static void println(String describe, Object o) {
        System.out.println(describe + ": " + o.toString());
    }
}
