package com.isoft.hr.log.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.hr.log.domain.AttendanceType} entity.
 */
public class AttendanceTypeDTO implements Serializable {

    private Long id;

    private String code;

    private String descEn;

    private String descAr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getDescAr() {
        return descAr;
    }

    public void setDescAr(String descAr) {
        this.descAr = descAr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceTypeDTO)) {
            return false;
        }

        AttendanceTypeDTO attendanceTypeDTO = (AttendanceTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attendanceTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceTypeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", descEn='" + getDescEn() + "'" +
            ", descAr='" + getDescAr() + "'" +
            "}";
    }
}
