package com.isoft.hr.log.repository;

import com.isoft.hr.log.domain.AttendanceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AttendanceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceTypeRepository extends JpaRepository<AttendanceType, Long> {}
