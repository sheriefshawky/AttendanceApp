package com.isoft.hr.log.service;

import com.isoft.hr.log.domain.Attendance;
import com.isoft.hr.log.repository.AttendanceRepository;
import com.isoft.hr.log.service.dto.AttendanceDTO;
import com.isoft.hr.log.service.mapper.AttendanceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Attendance}.
 */
@Service
@Transactional
public class AttendanceService {

    private final Logger log = LoggerFactory.getLogger(AttendanceService.class);

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
    }

    /**
     * Save a attendance.
     *
     * @param attendanceDTO the entity to save.
     * @return the persisted entity.
     */
    public AttendanceDTO save(AttendanceDTO attendanceDTO) {
        log.debug("Request to save Attendance : {}", attendanceDTO);
        Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
        attendance = attendanceRepository.save(attendance);
        return attendanceMapper.toDto(attendance);
    }

    /**
     * Update a attendance.
     *
     * @param attendanceDTO the entity to save.
     * @return the persisted entity.
     */
    public AttendanceDTO update(AttendanceDTO attendanceDTO) {
        log.debug("Request to save Attendance : {}", attendanceDTO);
        Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
        attendance = attendanceRepository.save(attendance);
        return attendanceMapper.toDto(attendance);
    }

    /**
     * Partially update a attendance.
     *
     * @param attendanceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AttendanceDTO> partialUpdate(AttendanceDTO attendanceDTO) {
        log.debug("Request to partially update Attendance : {}", attendanceDTO);

        return attendanceRepository
            .findById(attendanceDTO.getId())
            .map(existingAttendance -> {
                attendanceMapper.partialUpdate(existingAttendance, attendanceDTO);

                return existingAttendance;
            })
            .map(attendanceRepository::save)
            .map(attendanceMapper::toDto);
    }

    /**
     * Get all the attendances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attendances");
        return attendanceRepository.findAll(pageable).map(attendanceMapper::toDto);
    }

    /**
     * Get one attendance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttendanceDTO> findOne(Long id) {
        log.debug("Request to get Attendance : {}", id);
        return attendanceRepository.findById(id).map(attendanceMapper::toDto);
    }

    /**
     * Delete the attendance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Attendance : {}", id);
        attendanceRepository.deleteById(id);
    }
}
