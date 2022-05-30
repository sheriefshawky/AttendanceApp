package com.isoft.hr.log.service.mapper;

import com.isoft.hr.log.domain.Attendance;
import com.isoft.hr.log.domain.AttendanceType;
import com.isoft.hr.log.domain.Employee;
import com.isoft.hr.log.service.dto.AttendanceDTO;
import com.isoft.hr.log.service.dto.AttendanceTypeDTO;
import com.isoft.hr.log.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attendance} and its DTO {@link AttendanceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttendanceMapper extends EntityMapper<AttendanceDTO, Attendance> {
    @Mapping(target = "attendanceType", source = "attendanceType", qualifiedByName = "attendanceTypeId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "managerApprovedBy", source = "managerApprovedBy", qualifiedByName = "employeeId")
    @Mapping(target = "hrApprovedBy", source = "hrApprovedBy", qualifiedByName = "employeeId")
    AttendanceDTO toDto(Attendance s);

    @Named("attendanceTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttendanceTypeDTO toDtoAttendanceTypeId(AttendanceType attendanceType);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
