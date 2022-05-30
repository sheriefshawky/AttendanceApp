package com.isoft.hr.log.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendanceTypeMapperTest {

    private AttendanceTypeMapper attendanceTypeMapper;

    @BeforeEach
    public void setUp() {
        attendanceTypeMapper = new AttendanceTypeMapperImpl();
    }
}
