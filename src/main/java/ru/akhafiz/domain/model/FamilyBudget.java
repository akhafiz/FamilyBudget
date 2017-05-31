package ru.akhafiz.domain.model;

import ru.akhafiz.domain.model.annotations.Column;
import ru.akhafiz.domain.model.annotations.PrimaryKey;
import ru.akhafiz.domain.model.annotations.Table;

/**
 * <p></p>
 *
 * @author akhafiz
 */
@Table(name = "FB_FAMILY_BUDGET")
public class FamilyBudget extends BaseEntity {

    @PrimaryKey
    @Column(name = "ID")
    private Long id;

    @Column(name = "FAMILY_ID")
    private Long familyId;

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
}
