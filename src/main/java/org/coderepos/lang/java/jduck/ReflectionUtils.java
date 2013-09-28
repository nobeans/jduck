package org.coderepos.lang.java.jduck;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.coderepos.lang.java.jduck.exception.MethodInvocationRuntimeException;
import org.coderepos.lang.java.jduck.exception.MethodMissingRuntimeException;


/**
 * リフレクションに関するユーティリティクラス。
 * 
 * @author nobeans
 */
public class ReflectionUtils {

    /**
     * 指定したメソッド名のメソッドを実行する。
     * <p>
     * 呼び出し元で{@code NoSuchMethodRuntimeException}をキャッチして処理をすれば、
     * 一応、Method missing的なことが実現できる。
     * </p>
     * 
     * @param <T> メソッド戻り値の型
     * @param target 実行対象のオブジェクト
     * @param methodName メソッド名
     * @param args メソッドの引数
     * @return メソッドの実行結果
     * @throws MethodInvocationRuntimeException メソッドの実行に失敗した場合。
     *          MethodMissingRuntimeException 対象メソッドが見つからない場合。
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object target, String methodName, Object... args) {
        try {
            Class<?>[] argsClasses = resolveClasses(args);
            Method m = target.getClass().getMethod(methodName, argsClasses);
            return (T) m.invoke(target, args);
        } catch (IllegalArgumentException e) {
            throw new MethodInvocationRuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new MethodInvocationRuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new MethodInvocationRuntimeException(e);
        } catch (SecurityException e) {
            throw new MethodInvocationRuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new MethodMissingRuntimeException(e);
        }
    }

    private static Class<?>[] resolveClasses(Object... args) {
        if (args == null) return new Class[0];
        List<Class<?>> argsClassesList = new ArrayList<Class<?>>();
        for (Object arg : args) {
            argsClassesList.add(arg.getClass());
        }
        return argsClassesList.toArray(new Class<?>[0]);
    }

}
