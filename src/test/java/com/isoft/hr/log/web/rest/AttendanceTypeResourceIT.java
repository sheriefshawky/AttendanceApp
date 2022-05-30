package com.isoft.hr.log.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.hr.log.IntegrationTest;
import com.isoft.hr.log.domain.AttendanceType;
import com.isoft.hr.log.repository.AttendanceTypeRepository;
import com.isoft.hr.log.service.dto.AttendanceTypeDTO;
import com.isoft.hr.log.service.mapper.AttendanceTypeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AttendanceTypeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AttendanceTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_EN = "AAAAAAAAAA";
    private static final String UPDATED_DESC_EN = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_AR = "AAAAAAAAAA";
    private static final String UPDATED_DESC_AR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/attendance-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttendanceTypeRepository attendanceTypeRepository;

    @Autowired
    private AttendanceTypeMapper attendanceTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendanceTypeMockMvc;

    private AttendanceType attendanceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceType createEntity(EntityManager em) {
        AttendanceType attendanceType = new AttendanceType().code(DEFAULT_CODE).descEn(DEFAULT_DESC_EN).descAr(DEFAULT_DESC_AR);
        return attendanceType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceType createUpdatedEntity(EntityManager em) {
        AttendanceType attendanceType = new AttendanceType().code(UPDATED_CODE).descEn(UPDATED_DESC_EN).descAr(UPDATED_DESC_AR);
        return attendanceType;
    }

    @BeforeEach
    public void initTest() {
        attendanceType = createEntity(em);
    }

    @Test
    @Transactional
    void createAttendanceType() throws Exception {
        int databaseSizeBeforeCreate = attendanceTypeRepository.findAll().size();
        // Create the AttendanceType
        AttendanceTypeDTO attendanceTypeDTO = attendanceTypeMapper.toDto(attendanceType);
        restAttendanceTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AttendanceType testAttendanceType = attendanceTypeList.get(attendanceTypeList.size() - 1);
        assertThat(testAttendanceType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAttendanceType.getDescEn()).isEqualTo(DEFAULT_DESC_EN);
        assertThat(testAttendanceType.getDescAr()).isEqualTo(DEFAULT_DESC_AR);
    }

    @Test
    @Transactional
    void createAttendanceTypeWithExistingId() throws Exception {
        // Create the AttendanceType with an existing ID
        attendanceType.setId(1L);
        AttendanceTypeDTO attendanceTypeDTO = attendanceTypeMapper.toDto(attendanceType);

        int databaseSizeBeforeCreate = attendanceTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttendanceTypes() throws Exception {
        // Initialize the database
        attendanceTypeRepository.saveAndFlush(attendanceType);

        // Get all the attendanceTypeList
        restAttendanceTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].descEn").value(hasItem(DEFAULT_DESC_EN)))
            .andExpect(jsonPath("$.[*].descAr").value(hasItem(DEFAULT_DESC_AR)));
    }

    @Test
    @Transactional
    void getAttendanceType() throws Exception {
        // Initialize the database
        attendanceTypeRepository.saveAndFlush(attendanceType);

        // Get the attendanceType
        restAttendanceTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, attendanceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendanceType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.descEn").value(DEFAULT_DESC_EN))
            .andExpect(jsonPath("$.descAr").value(DEFAULT_DESC_AR));
    }

    @Test
    @Transactional
    void getNonExistingAttendanceType() throws Exception {
        // Get the attendanceType
        restAttendanceTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttendanceType() throws Exception {
        // Initialize the database
        attendanceTypeRepository.saveAndFlush(attendanceType);

        int databaseSizeBeforeUpdate = attendanceTypeRepository.findAll().size();

        // Update the attendanceType
        AttendanceType updatedAttendanceType = attendanceTypeRepository.findById(attendanceType.getId()).get();
        // Disconnect from session so that the updates on updatedAttendanceType are not directly saved in db
        em.detach(updatedAttendanceType);
        updatedAttendanceType.code(UPDATED_CODE).descEn(UPDATED_DESC_EN).descAr(UPDATED_DESC_AR);
        AttendanceTypeDTO attendanceTypeDTO = attendanceTypeMapper.toDto(updatedAttendanceType);

        restAttendanceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendanceTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeUpdate);
        AttendanceType testAttendanceType = attendanceTypeList.get(attendanceTypeList.size() - 1);
        assertThat(testAttendanceType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAttendanceType.getDescEn()).isEqualTo(UPDATED_DESC_EN);
        assertThat(testAttendanceType.getDescAr()).isEqualTo(UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void putNonExistingAttendanceType() throws Exception {
        int databaseSizeBeforeUpdate = attendanceTypeRepository.findAll().size();
        attendanceType.setId(count.incrementAndGet());

        // Create the AttendanceType
        AttendanceTypeDTO attendanceTypeDTO = attendanceTypeMapper.toDto(attendanceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendanceTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttendanceType() throws Exception {
        int databaseSizeBeforeUpdate = attendanceTypeRepository.findAll().size();
        attendanceType.setId(count.incrementAndGet());

        // Create the AttendanceType
        AttendanceTypeDTO attendanceTypeDTO = attendanceTypeMapper.toDto(attendanceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttendanceType() throws Exception {
        int databaseSizeBeforeUpdate = attendanceTypeRepository.findAll().size();
        attendanceType.setId(count.incrementAndGet());

        // Create the AttendanceType
        AttendanceTypeDTO attendanceTypeDTO = attendanceTypeMapper.toDto(attendanceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttendanceTypeWithPatch() throws Exception {
        // Initialize the database
        attendanceTypeRepository.saveAndFlush(attendanceType);

        int databaseSizeBeforeUpdate = attendanceTypeRepository.findAll().size();

        // Update the attendanceType using partial update
        AttendanceType partialUpdatedAttendanceType = new AttendanceType();
        partialUpdatedAttendanceType.setId(attendanceType.getId());

        partialUpdatedAttendanceType.descAr(UPDATED_DESC_AR);

        restAttendanceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendanceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttendanceType))
            )
            .andExpect(status().isOk());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeUpdate);
        AttendanceType testAttendanceType = attendanceTypeList.get(attendanceTypeList.size() - 1);
        assertThat(testAttendanceType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAttendanceType.getDescEn()).isEqualTo(DEFAULT_DESC_EN);
        assertThat(testAttendanceType.getDescAr()).isEqualTo(UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void fullUpdateAttendanceTypeWithPatch() throws Exception {
        // Initialize the database
        attendanceTypeRepository.saveAndFlush(attendanceType);

        int databaseSizeBeforeUpdate = attendanceTypeRepository.findAll().size();

        // Update the attendanceType using partial update
        AttendanceType partialUpdatedAttendanceType = new AttendanceType();
        partialUpdatedAttendanceType.setId(attendanceType.getId());

        partialUpdatedAttendanceType.code(UPDATED_CODE).descEn(UPDATED_DESC_EN).descAr(UPDATED_DESC_AR);

        restAttendanceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendanceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttendanceType))
            )
            .andExpect(status().isOk());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeUpdate);
        AttendanceType testAttendanceType = attendanceTypeList.get(attendanceTypeList.size() - 1);
        assertThat(testAttendanceType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAttendanceType.getDescEn()).isEqualTo(UPDATED_DESC_EN);
        assertThat(testAttendanceType.getDescAr()).isEqualTo(UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    void patchNonExistingAttendanceType() throws Exception {
        int databaseSizeBeforeUpdate = attendanceTypeRepository.findAll().size();
        attendanceType.setId(count.incrementAndGet());

        // Create the AttendanceType
        AttendanceTypeDTO attendanceTypeDTO = attendanceTypeMapper.toDto(attendanceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attendanceTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendanceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttendanceType() throws Exception {
        int databaseSizeBeforeUpdate = attendanceTypeRepository.findAll().size();
        attendanceType.setId(count.incrementAndGet());

        // Create the AttendanceType
        AttendanceTypeDTO attendanceTypeDTO = attendanceTypeMapper.toDto(attendanceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendanceTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttendanceType() throws Exception {
        int databaseSizeBeforeUpdate = attendanceTypeRepository.findAll().size();
        attendanceType.setId(count.incrementAndGet());

        // Create the AttendanceType
        AttendanceTypeDTO attendanceTypeDTO = attendanceTypeMapper.toDto(attendanceType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendanceTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttendanceType in the database
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttendanceType() throws Exception {
        // Initialize the database
        attendanceTypeRepository.saveAndFlush(attendanceType);

        int databaseSizeBeforeDelete = attendanceTypeRepository.findAll().size();

        // Delete the attendanceType
        restAttendanceTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, attendanceType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        assertThat(attendanceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
