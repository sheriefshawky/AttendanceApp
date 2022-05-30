package com.isoft.hr.log.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.hr.log.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttendanceTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceTypeDTO.class);
        AttendanceTypeDTO attendanceTypeDTO1 = new AttendanceTypeDTO();
        attendanceTypeDTO1.setId(1L);
        AttendanceTypeDTO attendanceTypeDTO2 = new AttendanceTypeDTO();
        assertThat(attendanceTypeDTO1).isNotEqualTo(attendanceTypeDTO2);
        attendanceTypeDTO2.setId(attendanceTypeDTO1.getId());
        assertThat(attendanceTypeDTO1).isEqualTo(attendanceTypeDTO2);
        attendanceTypeDTO2.setId(2L);
        assertThat(attendanceTypeDTO1).isNotEqualTo(attendanceTypeDTO2);
        attendanceTypeDTO1.setId(null);
        assertThat(attendanceTypeDTO1).isNotEqualTo(attendanceTypeDTO2);
    }
}
