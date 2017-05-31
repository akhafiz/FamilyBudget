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
@Table(name = "FB_FAMILY")
public class Family extends BaseEntity {

    @PrimaryKey
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
