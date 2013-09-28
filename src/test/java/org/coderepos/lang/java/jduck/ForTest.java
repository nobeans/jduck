package org.coderepos.lang.java.jduck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.coderepos.lang.java.jduck.Lambda.Convert;
import org.coderepos.lang.java.jduck.Lambda.Eval;

public class ForTest extends TestCase {

    private List<?> listObject;
    private List<?> listObject2;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        listObject = Arrays.asList("A", "B", "C");
        listObject2 = Arrays.asList("1", "3", "5");
        resultList_for_ダックタイピングによる実行_このテストクラス自体.clear();
        System.out.println("============");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        System.out.println();
    }

    /**
     * コレクションのそれぞれの要素に対して何らかの処理を適用する。
     * これで良いといえば十分良いのだけど。
     */
    public void testEach_普通の拡張forループ() throws Exception {
        List<String> result = new ArrayList<String>();
        for (Object it : listObject) {
            System.out.print(it);
            result.add(it.toString());
        }
        assertEquals(Arrays.asList("A", "B", "C"), result);
    }

    /** 
     * コレクションのそれぞれの要素に対して何らかの処理を適用する。
     * 使い捨てのEvalを対象に対して実行する。
     */
    public void testEach_流れるようなインタフェース_無名内部クラスの直接渡し() throws Exception {
        final List<String> result = new ArrayList<String>();
        For.in(listObject).each(new Eval<String>() {
            public void eval(String it) {
                System.out.print(it);
                result.add(it.toString());
            }
        });
        assertEquals(Arrays.asList("A", "B", "C"), result);
    }

    /**
     * コレクションのそれぞれの要素に対して何らかの処理を適用する。
     * 同じEvalを複数のコレクションに対して実行する。
     */
    public void testEach_流れるようなインタフェース_インスタンスの変数渡し() throws Exception {
        final List<String> result = new ArrayList<String>();
        Eval<String> doIt = new Eval<String>() {
            public void eval(String it) {
                System.out.print(it);
                result.add(it.toString());
            }
        };
        For.in(listObject).each(doIt);
        assertEquals(Arrays.asList("A", "B", "C"), result);
        For.in(listObject2).each(doIt);
        assertEquals(Arrays.asList("A", "B", "C", "1", "3", "5"), result);
    }

    /** 
     * コレクションのそれぞれの要素に対して何らかの処理を適用する。
     * Evalインタフェースを実装していない無名内部クラスを使って、
     * ダックタイピング的に実行する。
     */
    public void testEach_ダックタイピングによる実行_無名内部クラス() throws Exception {
        final List<String> result = new ArrayList<String>();
        For.in(listObject).each(new Object() {
            @SuppressWarnings("unused")
            public void eval(String it) { // @SuppressWarnings("unused")がないと未使用警告が出てしまう...
                System.out.print(it);
                result.add(it.toString());
            }
        });
        assertEquals(Arrays.asList("A", "B", "C"), result);
    }

    /** 
     * コレクションのそれぞれの要素に対して何らかの処理を適用する。
     * Evalインタフェースを実装していないこのテストクラス自体を使って、
     * ダックタイピング的に実行する。
     */
    public void testEach_ダックタイピングによる実行_このテストクラス自体() throws Exception {
        For.in(listObject).each(this);
        assertEquals(Arrays.asList("A", "B", "C"), resultList_for_ダックタイピングによる実行_このテストクラス自体);
    }

    /**
     * コレクションの一部を変換/除外した結果を返す。
     */
    public void testCollect() throws Exception {
        List<Integer> collected = For.in(listObject2).collect(new Convert<String, Integer>() {
            public Integer convert(String it) {
                if (it.equals("3")) return null; // nullにするとcollectされない
                return Integer.valueOf(it);
            }
        });
        System.out.print(collected);
        assertEquals(Arrays.asList(1, 5), collected);
    }

    /** 
     * コレクションの一部を変換/除外した結果を返す。
     * Evalインタフェースを実装していないこのテストクラス自体を使って、
     * ダックタイピング的に実行する。
     */
    public void testCollect_ダックタイピングによる実行_このテストクラス自体() throws Exception {
        List<Integer> collected = For.in(listObject2).collect(this);
        System.out.print(collected);
        assertEquals(Arrays.asList(1, 3), collected);
    }

    // 「ダックタイピングによる実行_このテストクラス自体」シリーズ用のフィールド/メソッド定義
    private final List<String> resultList_for_ダックタイピングによる実行_このテストクラス自体 = new ArrayList<String>();

    public void eval(String it) {
        System.out.print(it);
        resultList_for_ダックタイピングによる実行_このテストクラス自体.add(it);
    }

    public Integer convert(String it) {
        if (it.equals("5")) return null; // nullにするとcollectされない
        return Integer.valueOf(it);
    }
}
