package com.isoft.hr.log.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.hr.log.IntegrationTest;
import com.isoft.hr.log.domain.Attendance;
import com.isoft.hr.log.repository.AttendanceRepository;
import com.isoft.hr.log.service.dto.AttendanceDTO;
import com.isoft.hr.log.service.mapper.AttendanceMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link AttendanceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AttendanceResourceIT {

    private static final Instant DEFAULT_LOG_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOG_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOG_TIME_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOG_TIME_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOG_TIME_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOG_TIME_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PERMISSION_HOURS = 1;
    private static final Integer UPDATED_PERMISSION_HOURS = 2;

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/attendances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendanceMockMvc;

    private Attendance attendance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendance createEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .logDate(DEFAULT_LOG_DATE)
            .logTimeFrom(DEFAULT_LOG_TIME_FROM)
            .logTimeTo(DEFAULT_LOG_TIME_TO)
            .permissionHours(DEFAULT_PERMISSION_HOURS)
            .details(DEFAULT_DETAILS);
        return attendance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendance createUpdatedEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .logDate(UPDATED_LOG_DATE)
            .logTimeFrom(UPDATED_LOG_TIME_FROM)
            .logTimeTo(UPDATED_LOG_TIME_TO)
            .permissionHours(UPDATED_PERMISSION_HOURS)
            .details(UPDATED_DETAILS);
        return attendance;
    }

    @BeforeEach
    public void initTest() {
        attendance = createEntity(em);
    }

    @Test
    @Transactional
    void createAttendance() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();
        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);
        restAttendanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate + 1);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getLogDate()).isEqualTo(DEFAULT_LOG_DATE);
        assertThat(testAttendance.getLogTimeFrom()).isEqualTo(DEFAULT_LOG_TIME_FROM);
        assertThat(testAttendance.getLogTimeTo()).isEqualTo(DEFAULT_LOG_TIME_TO);
        assertThat(testAttendance.getPermissionHours()).isEqualTo(DEFAULT_PERMISSION_HOURS);
        assertThat(testAttendance.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    void createAttendanceWithExistingId() throws Exception {
        // Create the Attendance with an existing ID
        attendance.setId(1L);
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttendances() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList
        restAttendanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].logDate").value(hasItem(DEFAULT_LOG_DATE.toString())))
            .andExpect(jsonPath("$.[*].logTimeFrom").value(hasItem(DEFAULT_LOG_TIME_FROM.toString())))
            .andExpect(jsonPath("$.[*].logTimeTo").value(hasItem(DEFAULT_LOG_TIME_TO.toString())))
            .andExpect(jsonPath("$.[*].permissionHours").value(hasItem(DEFAULT_PERMISSION_HOURS)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }

    @Test
    @Transactional
    void getAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get the attendance
        restAttendanceMockMvc
            .perform(get(ENTITY_API_URL_ID, attendance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendance.getId().intValue()))
            .andExpect(jsonPath("$.logDate").value(DEFAULT_LOG_DATE.toString()))
            .andExpect(jsonPath("$.logTimeFrom").value(DEFAULT_LOG_TIME_FROM.toString()))
            .andExpect(jsonPath("$.logTimeTo").value(DEFAULT_LOG_TIME_TO.toString()))
            .andExpect(jsonPath("$.permissionHours").value(DEFAULT_PERMISSION_HOURS))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }

    @Test
    @Transactional
    void getNonExistingAttendance() throws Exception {
        // Get the attendance
        restAttendanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance
        Attendance updatedAttendance = attendanceRepository.findById(attendance.getId()).get();
        // Disconnect from session so that the updates on updatedAttendance are not directly saved in db
        em.detach(updatedAttendance);
        updatedAttendance
            .logDate(UPDATED_LOG_DATE)
            .logTimeFrom(UPDATED_LOG_TIME_FROM)
            .logTimeTo(UPDATED_LOG_TIME_TO)
            .permissionHours(UPDATED_PERMISSION_HOURS)
            .details(UPDATED_DETAILS);
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(updatedAttendance);

        restAttendanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getLogDate()).isEqualTo(UPDATED_LOG_DATE);
        assertThat(testAttendance.getLogTimeFrom()).isEqualTo(UPDATED_LOG_TIME_FROM);
        assertThat(testAttendance.getLogTimeTo()).isEqualTo(UPDATED_LOG_TIME_TO);
        assertThat(testAttendance.getPermissionHours()).isEqualTo(UPDATED_PERMISSION_HOURS);
        assertThat(testAttendance.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void putNonExistingAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttendanceWithPatch() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance using partial update
        Attendance partialUpdatedAttendance = new Attendance();
        partialUpdatedAttendance.setId(attendance.getId());

        partialUpdatedAttendance.logTimeTo(UPDATED_LOG_TIME_TO).permissionHours(UPDATED_PERMISSION_HOURS);

        restAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttendance))
            )
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getLogDate()).isEqualTo(DEFAULT_LOG_DATE);
        assertThat(testAttendance.getLogTimeFrom()).isEqualTo(DEFAULT_LOG_TIME_FROM);
        assertThat(testAttendance.getLogTimeTo()).isEqualTo(UPDATED_LOG_TIME_TO);
        assertThat(testAttendance.getPermissionHours()).isEqualTo(UPDATED_PERMISSION_HOURS);
        assertThat(testAttendance.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    void fullUpdateAttendanceWithPatch() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance using partial update
        Attendance partialUpdatedAttendance = new Attendance();
        partialUpdatedAttendance.setId(attendance.getId());

        partialUpdatedAttendance
            .logDate(UPDATED_LOG_DATE)
            .logTimeFrom(UPDATED_LOG_TIME_FROM)
            .logTimeTo(UPDATED_LOG_TIME_TO)
            .permissionHours(UPDATED_PERMISSION_HOURS)
            .details(UPDATED_DETAILS);

        restAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttendance))
            )
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getLogDate()).isEqualTo(UPDATED_LOG_DATE);
        assertThat(testAttendance.getLogTimeFrom()).isEqualTo(UPDATED_LOG_TIME_FROM);
        assertThat(testAttendance.getLogTimeTo()).isEqualTo(UPDATED_LOG_TIME_TO);
        assertThat(testAttendance.getPermissionHours()).isEqualTo(UPDATED_PERMISSION_HOURS);
        assertThat(testAttendance.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void patchNonExistingAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attendanceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();
        attendance.setId(count.incrementAndGet());

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(attendanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        int databaseSizeBeforeDelete = attendanceRepository.findAll().size();

        // Delete the attendance
        restAttendanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, attendance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
