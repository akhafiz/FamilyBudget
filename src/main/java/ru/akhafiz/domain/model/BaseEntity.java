package ru.akhafiz.domain.model;

/**
 *
 * @author akhafiz
 *
 */
abstract public class BaseEntity {

    abstract public Long getId();

    abstract public void setId(Long id);

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || this.getClass() != obj.getClass())
            return false;

        BaseEntity other = (BaseEntity) obj;

        return other.getId() != null && other.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [id=" + this.getId() + "]";
    }
}
