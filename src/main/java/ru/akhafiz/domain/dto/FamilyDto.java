package ru.akhafiz.domain.dto;

import java.util.Calendar;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class FamilyDto {

    private Long id;

    private String name;

    private Calendar createdDate;

    private String createdUserLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUserLogin() {
        return createdUserLogin;
    }

    public void setCreatedUserLogin(String createdUserLogin) {
        this.createdUserLogin = createdUserLogin;
    }
}
