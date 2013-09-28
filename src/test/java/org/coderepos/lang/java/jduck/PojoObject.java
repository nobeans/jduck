package org.coderepos.lang.java.jduck;

class PojoObject {
    public String hoge() {
        System.out.println("method:hoge()");
        return "return:hoge()";
    }

    public String foo(String message, Integer number) {
        System.out.printf("method:foo(%s, %d)\n", message, number);
        return String.format("return:foo(%s, %d)", message, number);
    }

    public void mix() {
        System.out.println("method:mix()");
    }

}