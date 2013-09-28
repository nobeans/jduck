package org.coderepos.lang.java.jduck;

import junit.framework.TestCase;

public class HogeTest extends TestCase {

    public void test() throws Exception {
        Box<String> box = BoxImpl.make();
        box.put("HOGE");
        System.out.println(box.get());
        System.out.println(box.get().getClass());
    }
}

interface Box<T> {
    public T get();

    public void put(T element);
}

class BoxImpl<T> implements Box<T> {

    public static <V> Box<V> make() {
        return new BoxImpl<V>();
    }

    private T element;

    public T get() {
        return element;
    }

    public void put(T element) {
        this.element = element;
    }

}