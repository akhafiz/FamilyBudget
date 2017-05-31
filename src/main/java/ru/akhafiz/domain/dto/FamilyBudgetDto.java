package ru.akhafiz.domain.dto;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class FamilyBudgetDto {

    private Long id;

    private Long familyId;

    public Long getId() {
        return id;
    }

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
