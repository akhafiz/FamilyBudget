package ru.akhafiz.domain.model;


import ru.akhafiz.domain.model.annotations.Column;
import ru.akhafiz.domain.model.annotations.PrimaryKey;
import ru.akhafiz.domain.model.annotations.Table;

import java.util.Date;

/**
 * <p></p>
 *
 * @author akhafiz
 */
@Table(name = "FB_USER",alias = "fb_user")
public class User extends BaseEntity {

    @PrimaryKey
    @Column(name = "ID")
    private Long id;

    /**
     * max length 50
     */
    @Column(name = "LOGIN")
    private String login;

    /**
     * max length 100
     */
    @Column(name = "EMAIL")
    private String email;

    /**
     * max length 255
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * max length 255
     */
    @Column(name = "FIRST_NAME")
    private String firstName;

    /**
     * max length 255
     */
    @Column(name = "SECOND_NAME")
    private String secondName;

    /**
     * max length 255
     */
    @Column(name = "LAST_NAME")
    private String lastName;

    /**
     * max length 1
     */
    @Column(name = "GENDER")
    private String gender;

    /**
     * max length 1
     */
    @Column(name = "ROLE")
    private String role;

    @Column(name = "IS_ACTIVE")
    private boolean active;

    @Column(name = "REGISTRY_DATE")
    private Date registryDate;

    @Column(name = "LAST_ENTER_DATE")
    private Date lastEnterDate;

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public Date getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(Date registryDate) {
        this.registryDate = registryDate;
    }

    public Date getLastEnterDate() {
        return lastEnterDate;
    }

    public void setLastEnterDate(Date lastEnterDate) {
        this.lastEnterDate = lastEnterDate;
    }
}
