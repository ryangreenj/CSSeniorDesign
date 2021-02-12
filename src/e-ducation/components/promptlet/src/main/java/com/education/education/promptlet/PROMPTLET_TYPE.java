package com.education.education.promptlet;

public enum PROMPTLET_TYPE {
    OPEN_RESPONSE ("OPEN_RESPONSE"),
    MULTI_RESPONSE ("MULTI_RESPONSE"),
    MULTI_CHOICE ("MULTI_CHOICE"),
    SLIDER ("SLIDER");

    private String text;

    PROMPTLET_TYPE(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static PROMPTLET_TYPE fromString(String text) {
        for (PROMPTLET_TYPE b : PROMPTLET_TYPE.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
