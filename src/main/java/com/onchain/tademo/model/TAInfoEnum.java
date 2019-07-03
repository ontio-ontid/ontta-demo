package com.onchain.tademo.model;

/**
 * @author zhouq
 * @version 1.0
 * @date 2018/11/6
 */
public enum TAInfoEnum {

    Ontology("Ontology"),

    CFCA("CFCA"),

    SENSETIME("Sensetime"),

    IDM("IdentityMind"),

    SHUFTIPRO("Shuftipro");

    private String name;

    TAInfoEnum(String name) {
        this.name = name;
    }

    public String myName() {
        return name;
    }

}
