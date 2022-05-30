package com.isoft.hr.log.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attendance.
 */
@Entity
@Table(name = "attendance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "log_date")
    private Instant logDate;

    @Column(name = "log_time_from")
    private Instant logTimeFrom;

    @Column(name = "log_time_to")
    private Instant logTimeTo;

    @Column(name = "permission_hours")
    private Integer permissionHours;

    @Column(name = "details")
    private String details;

    @OneToOne
    @JoinColumn(unique = true)
    private AttendanceType attendanceType;

    @JsonIgnoreProperties(value = { "manager", "department" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    @JsonIgnoreProperties(value = { "manager", "department" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Employee managerApprovedBy;

    @JsonIgnoreProperties(value = { "manager", "department" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Employee hrApprovedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Attendance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLogDate() {
        return this.logDate;
    }

    public Attendance logDate(Instant logDate) {
        this.setLogDate(logDate);
        return this;
    }

    public void setLogDate(Instant logDate) {
        this.logDate = logDate;
    }

    public Instant getLogTimeFrom() {
        return this.logTimeFrom;
    }

    public Attendance logTimeFrom(Instant logTimeFrom) {
        this.setLogTimeFrom(logTimeFrom);
        return this;
    }

    public void setLogTimeFrom(Instant logTimeFrom) {
        this.logTimeFrom = logTimeFrom;
    }

    public Instant getLogTimeTo() {
        return this.logTimeTo;
    }

    public Attendance logTimeTo(Instant logTimeTo) {
        this.setLogTimeTo(logTimeTo);
        return this;
    }

    public void setLogTimeTo(Instant logTimeTo) {
        this.logTimeTo = logTimeTo;
    }

    public Integer getPermissionHours() {
        return this.permissionHours;
    }

    public Attendance permissionHours(Integer permissionHours) {
        this.setPermissionHours(permissionHours);
        return this;
    }

    public void setPermissionHours(Integer permissionHours) {
        this.permissionHours = permissionHours;
    }

    public String getDetails() {
        return this.details;
    }

    public Attendance details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public AttendanceType getAttendanceType() {
        return this.attendanceType;
    }

    public void setAttendanceType(AttendanceType attendanceType) {
        this.attendanceType = attendanceType;
    }

    public Attendance attendanceType(AttendanceType attendanceType) {
        this.setAttendanceType(attendanceType);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Attendance employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public Employee getManagerApprovedBy() {
        return this.managerApprovedBy;
    }

    public void setManagerApprovedBy(Employee employee) {
        this.managerApprovedBy = employee;
    }

    public Attendance managerApprovedBy(Employee employee) {
        this.setManagerApprovedBy(employee);
        return this;
    }

    public Employee getHrApprovedBy() {
        return this.hrApprovedBy;
    }

    public void setHrApprovedBy(Employee employee) {
        this.hrApprovedBy = employee;
    }

    public Attendance hrApprovedBy(Employee employee) {
        this.setHrApprovedBy(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendance)) {
            return false;
        }
        return id != null && id.equals(((Attendance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attendance{" +
            "id=" + getId() +
            ", logDate='" + getLogDate() + "'" +
            ", logTimeFrom='" + getLogTimeFrom() + "'" +
            ", logTimeTo='" + getLogTimeTo() + "'" +
            ", permissionHours=" + getPermissionHours() +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
