package com.mycompany.impresoraweb.domain;


public class Printer {
    private static Printer instance = null;
    private static String name;
    
    private Printer() {}
    
    public static Printer getInstance() {
        if (instance == null)
            instance = new Printer();
        
        return instance;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Printer.name = name;
    }
    
    
}
