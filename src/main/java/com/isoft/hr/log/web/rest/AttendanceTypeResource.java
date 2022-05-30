package com.isoft.hr.log.web.rest;

import com.isoft.hr.log.repository.AttendanceTypeRepository;
import com.isoft.hr.log.service.AttendanceTypeService;
import com.isoft.hr.log.service.dto.AttendanceTypeDTO;
import com.isoft.hr.log.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.isoft.hr.log.domain.AttendanceType}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceTypeResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceTypeResource.class);

    private static final String ENTITY_NAME = "attendanceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceTypeService attendanceTypeService;

    private final AttendanceTypeRepository attendanceTypeRepository;

    public AttendanceTypeResource(AttendanceTypeService attendanceTypeService, AttendanceTypeRepository attendanceTypeRepository) {
        this.attendanceTypeService = attendanceTypeService;
        this.attendanceTypeRepository = attendanceTypeRepository;
    }

    /**
     * {@code POST  /attendance-types} : Create a new attendanceType.
     *
     * @param attendanceTypeDTO the attendanceTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendanceTypeDTO, or with status {@code 400 (Bad Request)} if the attendanceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendance-types")
    public ResponseEntity<AttendanceTypeDTO> createAttendanceType(@RequestBody AttendanceTypeDTO attendanceTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttendanceType : {}", attendanceTypeDTO);
        if (attendanceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendanceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceTypeDTO result = attendanceTypeService.save(attendanceTypeDTO);
        return ResponseEntity
            .created(new URI("/api/attendance-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendance-types/:id} : Updates an existing attendanceType.
     *
     * @param id the id of the attendanceTypeDTO to save.
     * @param attendanceTypeDTO the attendanceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the attendanceTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendanceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendance-types/{id}")
    public ResponseEntity<AttendanceTypeDTO> updateAttendanceType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttendanceTypeDTO attendanceTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttendanceType : {}, {}", id, attendanceTypeDTO);
        if (attendanceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attendanceTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attendanceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttendanceTypeDTO result = attendanceTypeService.update(attendanceTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendanceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attendance-types/:id} : Partial updates given fields of an existing attendanceType, field will ignore if it is null
     *
     * @param id the id of the attendanceTypeDTO to save.
     * @param attendanceTypeDTO the attendanceTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceTypeDTO,
     * or with status {@code 400 (Bad Request)} if the attendanceTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attendanceTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attendanceTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attendance-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttendanceTypeDTO> partialUpdateAttendanceType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttendanceTypeDTO attendanceTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttendanceType partially : {}, {}", id, attendanceTypeDTO);
        if (attendanceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attendanceTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attendanceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttendanceTypeDTO> result = attendanceTypeService.partialUpdate(attendanceTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendanceTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attendance-types} : get all the attendanceTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendanceTypes in body.
     */
    @GetMapping("/attendance-types")
    public ResponseEntity<List<AttendanceTypeDTO>> getAllAttendanceTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AttendanceTypes");
        Page<AttendanceTypeDTO> page = attendanceTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attendance-types/:id} : get the "id" attendanceType.
     *
     * @param id the id of the attendanceTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendanceTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendance-types/{id}")
    public ResponseEntity<AttendanceTypeDTO> getAttendanceType(@PathVariable Long id) {
        log.debug("REST request to get AttendanceType : {}", id);
        Optional<AttendanceTypeDTO> attendanceTypeDTO = attendanceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendanceTypeDTO);
    }

    /**
     * {@code DELETE  /attendance-types/:id} : delete the "id" attendanceType.
     *
     * @param id the id of the attendanceTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendance-types/{id}")
    public ResponseEntity<Void> deleteAttendanceType(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceType : {}", id);
        attendanceTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
