package ru.akhafiz.domain.dto;

import ru.akhafiz.domain.enumerations.Gender;
import ru.akhafiz.domain.enumerations.UserRole;

import java.util.Calendar;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class UserDto {

    private Long id;

    private String login;

    private String email;

    private String password;

    private FioDto fio;

    private Gender gender;

    private UserRole role;

    private Boolean active = Boolean.FALSE;

    private Calendar registryDate;

    private Calendar lastEnterDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FioDto getFio() {
        return fio;
    }

    public void setFio(FioDto fio) {
        this.fio = fio;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Calendar getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(Calendar registryDate) {
        this.registryDate = registryDate;
    }

    public Calendar getLastEnterDate() {
        return lastEnterDate;
    }

    public void setLastEnterDate(Calendar lastEnterDate) {
        this.lastEnterDate = lastEnterDate;
    }
}
