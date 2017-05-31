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
@Table(name = "FB_FAMILY_MEMBER")
public class FamilyMember extends BaseEntity {

    @PrimaryKey
    @Column(name = "ID")
    private Long id;

    @Column(name = "FAMILY_ID")
    private Long familyId;

    @Column(name = "USER_ID")
    private Long userId;

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
     * max length 100
     */
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_USER_LOGIN")
    private String createdUserLogin;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUserLogin() {
        return createdUserLogin;
    }

    public void setCreatedUserLogin(String createdUserLogin) {
        this.createdUserLogin = createdUserLogin;
    }
}
