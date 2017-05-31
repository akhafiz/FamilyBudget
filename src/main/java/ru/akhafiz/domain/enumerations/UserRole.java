package ru.akhafiz.domain.enumerations;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public enum  UserRole {
    A("Администратор"),
    U("Пользователь")
    ;

    private String title;


    UserRole(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
