package com.isoft.hr.log.service;

import com.isoft.hr.log.domain.AttendanceType;
import com.isoft.hr.log.repository.AttendanceTypeRepository;
import com.isoft.hr.log.service.dto.AttendanceTypeDTO;
import com.isoft.hr.log.service.mapper.AttendanceTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttendanceType}.
 */
@Service
@Transactional
public class AttendanceTypeService {

    private final Logger log = LoggerFactory.getLogger(AttendanceTypeService.class);

    private final AttendanceTypeRepository attendanceTypeRepository;

    private final AttendanceTypeMapper attendanceTypeMapper;

    public AttendanceTypeService(AttendanceTypeRepository attendanceTypeRepository, AttendanceTypeMapper attendanceTypeMapper) {
        this.attendanceTypeRepository = attendanceTypeRepository;
        this.attendanceTypeMapper = attendanceTypeMapper;
    }

    /**
     * Save a attendanceType.
     *
     * @param attendanceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public AttendanceTypeDTO save(AttendanceTypeDTO attendanceTypeDTO) {
        log.debug("Request to save AttendanceType : {}", attendanceTypeDTO);
        AttendanceType attendanceType = attendanceTypeMapper.toEntity(attendanceTypeDTO);
        attendanceType = attendanceTypeRepository.save(attendanceType);
        return attendanceTypeMapper.toDto(attendanceType);
    }

    /**
     * Update a attendanceType.
     *
     * @param attendanceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public AttendanceTypeDTO update(AttendanceTypeDTO attendanceTypeDTO) {
        log.debug("Request to save AttendanceType : {}", attendanceTypeDTO);
        AttendanceType attendanceType = attendanceTypeMapper.toEntity(attendanceTypeDTO);
        attendanceType = attendanceTypeRepository.save(attendanceType);
        return attendanceTypeMapper.toDto(attendanceType);
    }

    /**
     * Partially update a attendanceType.
     *
     * @param attendanceTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AttendanceTypeDTO> partialUpdate(AttendanceTypeDTO attendanceTypeDTO) {
        log.debug("Request to partially update AttendanceType : {}", attendanceTypeDTO);

        return attendanceTypeRepository
            .findById(attendanceTypeDTO.getId())
            .map(existingAttendanceType -> {
                attendanceTypeMapper.partialUpdate(existingAttendanceType, attendanceTypeDTO);

                return existingAttendanceType;
            })
            .map(attendanceTypeRepository::save)
            .map(attendanceTypeMapper::toDto);
    }

    /**
     * Get all the attendanceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttendanceTypes");
        return attendanceTypeRepository.findAll(pageable).map(attendanceTypeMapper::toDto);
    }

    /**
     * Get one attendanceType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttendanceTypeDTO> findOne(Long id) {
        log.debug("Request to get AttendanceType : {}", id);
        return attendanceTypeRepository.findById(id).map(attendanceTypeMapper::toDto);
    }

    /**
     * Delete the attendanceType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AttendanceType : {}", id);
        attendanceTypeRepository.deleteById(id);
    }
}
