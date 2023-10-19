package com.basejava.webapp;

import com.basejava.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    public TestSingleton() {
    }

    public static void main(String[] args) {
        TestSingleton.getInstance().toString();
        Singleton instance1 = Singleton.valueOf("INSTANCE");
        System.out.println(instance1.ordinal());

        for (SectionType type: SectionType.values()){
            System.out.println(type.getTitle());
        }
    }

    public enum Singleton{
        INSTANCE
    }
}
