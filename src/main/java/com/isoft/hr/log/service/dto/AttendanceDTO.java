package com.isoft.hr.log.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.hr.log.domain.Attendance} entity.
 */
public class AttendanceDTO implements Serializable {

    private Long id;

    private Instant logDate;

    private Instant logTimeFrom;

    private Instant logTimeTo;

    private Integer permissionHours;

    private String details;

    private AttendanceTypeDTO attendanceType;

    private EmployeeDTO employee;

    private EmployeeDTO managerApprovedBy;

    private EmployeeDTO hrApprovedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLogDate() {
        return logDate;
    }

    public void setLogDate(Instant logDate) {
        this.logDate = logDate;
    }

    public Instant getLogTimeFrom() {
        return logTimeFrom;
    }

    public void setLogTimeFrom(Instant logTimeFrom) {
        this.logTimeFrom = logTimeFrom;
    }

    public Instant getLogTimeTo() {
        return logTimeTo;
    }

    public void setLogTimeTo(Instant logTimeTo) {
        this.logTimeTo = logTimeTo;
    }

    public Integer getPermissionHours() {
        return permissionHours;
    }

    public void setPermissionHours(Integer permissionHours) {
        this.permissionHours = permissionHours;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public AttendanceTypeDTO getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(AttendanceTypeDTO attendanceType) {
        this.attendanceType = attendanceType;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public EmployeeDTO getManagerApprovedBy() {
        return managerApprovedBy;
    }

    public void setManagerApprovedBy(EmployeeDTO managerApprovedBy) {
        this.managerApprovedBy = managerApprovedBy;
    }

    public EmployeeDTO getHrApprovedBy() {
        return hrApprovedBy;
    }

    public void setHrApprovedBy(EmployeeDTO hrApprovedBy) {
        this.hrApprovedBy = hrApprovedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceDTO)) {
            return false;
        }

        AttendanceDTO attendanceDTO = (AttendanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attendanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceDTO{" +
            "id=" + getId() +
            ", logDate='" + getLogDate() + "'" +
            ", logTimeFrom='" + getLogTimeFrom() + "'" +
            ", logTimeTo='" + getLogTimeTo() + "'" +
            ", permissionHours=" + getPermissionHours() +
            ", details='" + getDetails() + "'" +
            ", attendanceType=" + getAttendanceType() +
            ", employee=" + getEmployee() +
            ", managerApprovedBy=" + getManagerApprovedBy() +
            ", hrApprovedBy=" + getHrApprovedBy() +
            "}";
    }
}
