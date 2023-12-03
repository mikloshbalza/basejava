package com.basejava.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"){
        @Override
        protected String toHtml0(String value) {
            return super.toHtml0(value);
        }
    },
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value){
        return title + ": " + value;
    }
    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
