package ru.akhafiz.domain.dto;

import ru.akhafiz.domain.enumerations.Gender;

import java.util.Calendar;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class FamilyMemberDto {

    private Long id;

    private Long userId;

    private Long familyId;

    private FioDto fio;

    private String email;

    private Gender gender;

    private Calendar createdDate;

    private String createdUserLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public FioDto getFio() {
        return fio;
    }

    public void setFio(FioDto fio) {
        this.fio = fio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
