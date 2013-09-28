package org.coderepos.lang.java.jduck;

import org.coderepos.lang.java.jduck.ReflectionUtils;

import junit.framework.TestCase;

public class ReflectionUtilsTest extends TestCase {

    /**
     * 指定したメソッド名のメソッドを実行するテスト。
     * <p>
     * チラシの裏：
     * こんなのは全然ダックタイピングじゃないよね。
     * 今回みたくウラの仕組みとして使わせるのはええんよ。うん。
     * だって、一般的にJavaで動的にやるには文字列でやるしかないんよ。
     * 型的に書くとそんなん実装して無いーとかコンパイルエラーになっちゃうからね。
     * </p>
     */
    public void testInvoke() throws Exception {
        PojoObject pojo = new PojoObject();
        assertEquals("return:hoge()", ReflectionUtils.invoke(pojo, "hoge"));
        assertEquals("return:foo(FOO!, 1234)", ReflectionUtils.invoke(pojo, "foo", "FOO!", 1234));
        assertEquals(null, ReflectionUtils.invoke(pojo, "mix"));
    }

}
