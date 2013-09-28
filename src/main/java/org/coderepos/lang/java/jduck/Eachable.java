package org.coderepos.lang.java.jduck;

import java.util.ArrayList;
import java.util.List;

import org.coderepos.lang.java.jduck.Lambda.Convert;
import org.coderepos.lang.java.jduck.Lambda.Eval;

/**
 * 流れるようなインタフェースにおける中間オブジェクト。
 * <p>
 * eachメソッド等を提供する。
 * 静的型でカッチリ指定するよりも、
 * できるだけ指定する情報量を省略できる方向で実装している。
 * </p>
 * @author nobeans
 */
public class Eachable<T> {

    private Iterable<T> iterable;

    public Eachable(Iterable<T> iterable) {
        this.iterable = iterable;
    }

    /**
     * 全対象に対して、指定されたEval実装の処理を適用する。
     * 
     * @param eval
     */
    public void each(Eval<T> eval) {
        for (T it : (iterable)) {
            eval.eval(it);
        }
    }

    /**
     * 全対象に対して、指定されたオブジェクトがEvalインタフェースを実装しているとみなして処理を適用する。
     * 
     * @param eval
     */
    public void each(Object evaluator) {
        Eval<T> eval = Inject.<Eval<T>>to(evaluator);
        each(eval);
    }

    /**
     * 全対象に対して、指定されたConvert実装の処理を適用する。
     * 
     * @param <E>
     * @param conv
     * @return
     */
    public <E> List<E> collect(Convert<T, E> conv) {
        List<E> result = new ArrayList<E>();
        for (T it : (iterable)) {
            E converted = conv.convert(it);
            if (converted != null) {
                result.add(converted);
            }
        }
        return result;
    }

    /**
     * 全対象に対して、指定されたオブジェクトがConvertインタフェースを実装しているとみなして処理を適用する。
     * 
     * @param eval
     */
    public <E> List<E> collect(Object convertor) {
        Convert<T, E> conv = Inject.<Convert<T, E>>to(convertor);
        return collect(conv);
    }

}
