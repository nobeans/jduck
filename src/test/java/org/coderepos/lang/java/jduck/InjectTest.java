package org.coderepos.lang.java.jduck;

import junit.framework.TestCase;

import org.coderepos.lang.java.jduck.exception.MethodMissingRuntimeException;

public class InjectTest extends TestCase {

    // =======================================================
    // POJOにインジェクションするインタフェース群

    interface HogeMethod {
        String hoge(); // 引数無し、戻り値有り
    }

    interface FooMethod {
        String foo(String a, Integer b); // 引数2つ、戻り値有り
    }

    interface MixMethod extends FooMethod {
        String mix(); // POJOではvoidなmixにStringを戻すインタフェースでアクセスしてみると...

        void hoge(); // 引数無し、戻り値無し！
    }

    interface NotImplementedMethod {
        String notImplmented();
    }

    // =======================================================

    // 実行対象のPOJOオブジェクト。
    // あえてObject型の変数宣言とする。
    private Object pojo;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        pojo = new PojoObject();
        System.out.println("============");
    }

    /**
     * やっぱこれだよね！
     * 呼び出し側でこのような記述をコンパイルエラー無しでできるので、
     * あとはInject#to()の向こう側のがんばり次第。
     * バイトコードエンハンスメントとかしたら結構色々できると思う。
     */
    public void testインタフェースをインジェクトする_1行で() throws Exception {
        Inject.<HogeMethod>to(pojo).hoge();
        Inject.<FooMethod>to(pojo).foo("FOO!", 1234);
        Inject.<MixMethod>to(pojo).mix();
        Inject.<MixMethod>to(pojo).hoge();
        Inject.<MixMethod>to(pojo).foo("FOO!", 1234);
    }

    /**
     * 変数に受けてみる。上と同じでこうもかける。
     */
    public void testインタフェースをインジェクトする_変数で_javac用() throws Exception {
        HogeMethod h = Inject.<HogeMethod>to(pojo);
        h.hoge();

        FooMethod f = Inject.<FooMethod>to(pojo);
        f.foo("FOO!", 5555);

        MixMethod d = Inject.<MixMethod>to(pojo);
        d.mix();
        d.hoge();
        d.foo("Mixed FOO!", 5555);
    }

    /**
     * こうかくと、なんかほら、画期的な香りがしない？
     * ただし、Eclipseでコンパイルすると動くけど、
     * 正規のjavacだと型エラーになっちゃうみたい...。残念。
     */
    public void testインタフェースをインジェクトする_変数で_EclipseのコンパイラならこれでOK() throws Exception {
        HogeMethod h = Inject.to(pojo);
        h.hoge();

        FooMethod f = Inject.to(pojo);
        f.foo("FOO!", 5555);

        MixMethod d = Inject.to(pojo);
        d.mix();
        d.hoge();
        d.foo("Mixed FOO!", 5555);
    }

    /**
     * POJOとインタフェースの戻り値の宣言によってどのように結果が変わるのかを確認する。
     */
    public void testインタフェースをインジェクトする_戻り値の検証() throws Exception {
        assertEquals("return:hoge()", Inject.<HogeMethod>to(pojo).hoge());
        assertEquals("return:foo(FOO!, 1234)", Inject.<FooMethod>to(pojo).foo("FOO!", 1234));
        assertEquals(null, Inject.<MixMethod>to(pojo).mix()); // POJOがvoidなら戻り値はnull
        Inject.<MixMethod>to(pojo).hoge(); // インタフェースの戻り値がvoidなら、実際にPOJOがreturnしていても戻り値はvoid
        assertEquals("return:foo(FOO!, 1234)", Inject.<MixMethod>to(pojo).foo("FOO!", 1234));
    }

    /**
     * Method misssingの場合は例外が発生するので、catchしてどうにかすればよい。
     */
    public void test未実装のメソッドを呼んだ場合() throws Exception {
        try {
            Inject.<NotImplementedMethod>to(pojo).notImplmented();
            fail();
        } catch (MethodMissingRuntimeException e) {
            assertTrue(e.getCause() instanceof NoSuchMethodException);
            assertEquals("java.lang.NoSuchMethodException: org.coderepos.lang.java.jduck.PojoObject.notImplmented()", e.getMessage());
        }
    }
}
