package org.coderepos.lang.java.jduck;

/**
 * eachやcollectメソッドを実行するための起点となるユーティリティクラス。
 * 
 * @author nobeans
 */
public class For {

    public static <T> Eachable<T> in(Iterable<T> iterable) {
        return new Eachable<T>(iterable);
    }

}
