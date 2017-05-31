package ru.akhafiz.dao.metadata;

import java.lang.reflect.Method;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class MetaDataPersistField {

    private String fieldName;

    private Class<?> fieldClass;

    private Method getter;

    private Method setter;

    public MetaDataPersistField(String fieldName, Class<?> fieldClass, Method getter, Method setter) {
        this.fieldName = fieldName;
        this.fieldClass = fieldClass;
        this.getter = getter;
        this.setter = setter;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<?> getFieldClass() {
        return fieldClass;
    }

    public Method getGetter() {
        return getter;
    }

    public Method getSetter() {
        return setter;
    }

    @Override
    public int hashCode() {
        return fieldName == null ? 0 : fieldName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        MetaDataPersistField other = (MetaDataPersistField) obj;

        return other.getFieldName() != null && other.getFieldName().equals(this.fieldName);
    }
}
