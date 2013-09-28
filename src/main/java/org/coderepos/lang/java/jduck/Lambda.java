package org.coderepos.lang.java.jduck;

/**
 * jduckで使用するLambda系インタフェース定義。
 * <p>
 * Lambdaインタフェースを名前空間というか構造をあらわすためだけに使用している。
 * rubyのモジュールっぽい感じ。変態的。
 * </p>
 * @author nobeans
 */
interface Lambda {

    interface Eval<T> extends Lambda {
        void eval(T it);
    }

    interface Convert<T1, T2> extends Lambda {
        T2 convert(T1 it);
    }
}
