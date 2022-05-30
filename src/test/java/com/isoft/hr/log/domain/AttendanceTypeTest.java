package com.isoft.hr.log.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.isoft.hr.log.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttendanceTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceType.class);
        AttendanceType attendanceType1 = new AttendanceType();
        attendanceType1.setId(1L);
        AttendanceType attendanceType2 = new AttendanceType();
        attendanceType2.setId(attendanceType1.getId());
        assertThat(attendanceType1).isEqualTo(attendanceType2);
        attendanceType2.setId(2L);
        assertThat(attendanceType1).isNotEqualTo(attendanceType2);
        attendanceType1.setId(null);
        assertThat(attendanceType1).isNotEqualTo(attendanceType2);
    }
}
