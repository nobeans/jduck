package org.coderepos.lang.java.jduck;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.coderepos.lang.java.jduck.exception.InterfaceNotFoundRuntimeException;

/**
 * 外部からインタフェースを注入したかのようにみせて、ダックタイピングを実現する。
 * これが真のインタフェース・インジェクション。
 * 注入したインタフェースが宣言するメソッドが実際に対象オブジェクトで実行できるかどうかは、
 * 実行してみるまでわからない。
 * <pre>
 * 例:
 *  Inject.&lt;HogeMethod&gt;to(pojo).hoge();
 *  
 *  MixMethod d = Inject.&lt;HogeMethod&gt;to(pojo);
 *  d.hoge();
 *  d.mix();
 * </pre>
 * @author nobeans
 */
public class Inject {

    /**
     * 対象に指定したインタフェースを強制注入して返す。
     * 
     * @param <T>
     * @param target
     * @param dummy トリック用のダミー。指定しちゃダメ、絶対。
     * @return
     * @throws InterfaceNotFoundRuntimeException 注入対象のインタフェースが見つからなかった場合
     */
    @SuppressWarnings("unchecked")
    public static <T> T to(Object target) {
        //assert (dummy.length == 0) : "0じゃないとダメ、絶対。";
        //Class<?> clazz = dummy.getClass().getComponentType();

        Box box = new Box();
        T dummy = estimateClass(box);
        System.out.println("★" + box.clazz);
        Class<?> clazz = box.clazz;

        Class<?>[] interfaces = getInterfaces(clazz);
        if (interfaces.length == 0) {
            throw new InterfaceNotFoundRuntimeException(clazz.getName());
        }
        return (T) createProxy(target, interfaces);
    }

    private static class Box {
        Class<?> clazz;
    }

    private static <V> V estimateClass(Box box, V... dummy) {
        assert (dummy.length == 0) : "0じゃないとダメ、絶対。";
        Class<?> clazz = dummy.getClass().getComponentType();
        box.clazz = clazz;
        return null;
    }

    private static Object createProxy(final Object obj, final Class<?>[] interfaces) {
        InvocationHandler invoc = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return ReflectionUtils.invoke(obj, method.getName(), args);
            }
        };
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), interfaces, invoc);
    }

    private static Class<?>[] getInterfaces(final Class<?> clazz) {
        List<Class<?>> interfaceList = new ArrayList<Class<?>>();
        if (clazz.isInterface()) {
            interfaceList.add(clazz);
        }
        interfaceList.addAll(Arrays.asList(clazz.getInterfaces()));
        return interfaceList.toArray(new Class<?>[0]);
    }

}
