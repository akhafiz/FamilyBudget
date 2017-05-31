package ru.akhafiz.domain.enumerations;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public enum Gender {

    M("Муж"),
    W("Жен")
    ;

    Gender(String title) {
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title;
    }

}
