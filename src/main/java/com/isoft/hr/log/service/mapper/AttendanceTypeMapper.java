package com.isoft.hr.log.service.mapper;

import com.isoft.hr.log.domain.AttendanceType;
import com.isoft.hr.log.service.dto.AttendanceTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttendanceType} and its DTO {@link AttendanceTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttendanceTypeMapper extends EntityMapper<AttendanceTypeDTO, AttendanceType> {}
