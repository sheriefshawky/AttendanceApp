package com.isoft.hr.log.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AttendanceType.
 */
@Entity
@Table(name = "attendance_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttendanceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "desc_en")
    private String descEn;

    @Column(name = "desc_ar")
    private String descAr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AttendanceType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public AttendanceType code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescEn() {
        return this.descEn;
    }

    public AttendanceType descEn(String descEn) {
        this.setDescEn(descEn);
        return this;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getDescAr() {
        return this.descAr;
    }

    public AttendanceType descAr(String descAr) {
        this.setDescAr(descAr);
        return this;
    }

    public void setDescAr(String descAr) {
        this.descAr = descAr;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceType)) {
            return false;
        }
        return id != null && id.equals(((AttendanceType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", descEn='" + getDescEn() + "'" +
            ", descAr='" + getDescAr() + "'" +
            "}";
    }
}
