package io.github.semhas.web.rest;

import io.github.semhas.SemhasApp;

import io.github.semhas.domain.Seminar;
import io.github.semhas.repository.SeminarRepository;
import io.github.semhas.service.SeminarService;
import io.github.semhas.service.dto.SeminarDTO;
import io.github.semhas.service.mapper.SeminarMapper;
import io.github.semhas.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.github.semhas.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.semhas.domain.enumeration.StatusSeminar;
/**
 * Test class for the SeminarResource REST controller.
 *
 * @see SeminarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SemhasApp.class)
public class SeminarResourceIntTest {

    private static final String DEFAULT_JUDUL = "AAAAAAAAAA";
    private static final String UPDATED_JUDUL = "BBBBBBBBBB";

    private static final String DEFAULT_ABSTRAK = "AAAAAAAAAA";
    private static final String UPDATED_ABSTRAK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE_ACC_SEMINAR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_ACC_SEMINAR = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_ACC_SEMINAR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_ACC_SEMINAR_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_RUANGAN = "AAAAAAAAAA";
    private static final String UPDATED_RUANGAN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_JAM_MULAI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JAM_MULAI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_JAM_SELESAI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JAM_SELESAI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final StatusSeminar DEFAULT_STATUS = StatusSeminar.MENUNGGU_PERSETUJUAN;
    private static final StatusSeminar UPDATED_STATUS = StatusSeminar.DISETUJUI;

    @Autowired
    private SeminarRepository seminarRepository;

    @Autowired
    private SeminarMapper seminarMapper;

    @Autowired
    private SeminarService seminarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeminarMockMvc;

    private Seminar seminar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SeminarResource seminarResource = new SeminarResource(seminarService);
        this.restSeminarMockMvc = MockMvcBuilders.standaloneSetup(seminarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seminar createEntity(EntityManager em) {
        Seminar seminar = new Seminar()
            .judul(DEFAULT_JUDUL)
            .abstrak(DEFAULT_ABSTRAK)
            .fileAccSeminar(DEFAULT_FILE_ACC_SEMINAR)
            .fileAccSeminarContentType(DEFAULT_FILE_ACC_SEMINAR_CONTENT_TYPE)
            .ruangan(DEFAULT_RUANGAN)
            .jamMulai(DEFAULT_JAM_MULAI)
            .jamSelesai(DEFAULT_JAM_SELESAI)
            .status(DEFAULT_STATUS);
        return seminar;
    }

    @Before
    public void initTest() {
        seminar = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeminar() throws Exception {
        int databaseSizeBeforeCreate = seminarRepository.findAll().size();

        // Create the Seminar
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);
        restSeminarMockMvc.perform(post("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isCreated());

        // Validate the Seminar in the database
        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeCreate + 1);
        Seminar testSeminar = seminarList.get(seminarList.size() - 1);
        assertThat(testSeminar.getJudul()).isEqualTo(DEFAULT_JUDUL);
        assertThat(testSeminar.getAbstrak()).isEqualTo(DEFAULT_ABSTRAK);
        assertThat(testSeminar.getFileAccSeminar()).isEqualTo(DEFAULT_FILE_ACC_SEMINAR);
        assertThat(testSeminar.getFileAccSeminarContentType()).isEqualTo(DEFAULT_FILE_ACC_SEMINAR_CONTENT_TYPE);
        assertThat(testSeminar.getJamMulai()).isEqualTo(DEFAULT_JAM_MULAI);
        assertThat(testSeminar.getJamSelesai()).isEqualTo(DEFAULT_JAM_SELESAI);
        assertThat(testSeminar.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createSeminarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seminarRepository.findAll().size();

        // Create the Seminar with an existing ID
        seminar.setId(1L);
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeminarMockMvc.perform(post("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkJudulIsRequired() throws Exception {
        int databaseSizeBeforeTest = seminarRepository.findAll().size();
        // set the field null
        seminar.setJudul(null);

        // Create the Seminar, which fails.
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);

        restSeminarMockMvc.perform(post("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isBadRequest());

        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAbstrakIsRequired() throws Exception {
        int databaseSizeBeforeTest = seminarRepository.findAll().size();
        // set the field null
        seminar.setAbstrak(null);

        // Create the Seminar, which fails.
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);

        restSeminarMockMvc.perform(post("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isBadRequest());

        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileAccSeminarIsRequired() throws Exception {
        int databaseSizeBeforeTest = seminarRepository.findAll().size();
        // set the field null
        seminar.setFileAccSeminar(null);

        // Create the Seminar, which fails.
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);

        restSeminarMockMvc.perform(post("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isBadRequest());

        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeminars() throws Exception {
        // Initialize the database
        seminarRepository.saveAndFlush(seminar);

        // Get all the seminarList
        restSeminarMockMvc.perform(get("/api/seminars?status=&dosenId=&sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seminar.getId().intValue())))
            .andExpect(jsonPath("$.[*].judul").value(hasItem(DEFAULT_JUDUL.toString())))
            .andExpect(jsonPath("$.[*].abstrak").value(hasItem(DEFAULT_ABSTRAK.toString())))
            .andExpect(jsonPath("$.[*].fileAccSeminarContentType").value(hasItem(DEFAULT_FILE_ACC_SEMINAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileAccSeminar").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_ACC_SEMINAR))))
            .andExpect(jsonPath("$.[*].ruangan").value(hasItem(DEFAULT_RUANGAN.toString())))
            .andExpect(jsonPath("$.[*].jamMulai").value(hasItem(sameInstant(DEFAULT_JAM_MULAI))))
            .andExpect(jsonPath("$.[*].jamSelesai").value(hasItem(sameInstant(DEFAULT_JAM_SELESAI))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getSeminar() throws Exception {
        // Initialize the database
        seminarRepository.saveAndFlush(seminar);

        // Get the seminar
        restSeminarMockMvc.perform(get("/api/seminars/{id}", seminar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seminar.getId().intValue()))
            .andExpect(jsonPath("$.judul").value(DEFAULT_JUDUL.toString()))
            .andExpect(jsonPath("$.abstrak").value(DEFAULT_ABSTRAK.toString()))
            .andExpect(jsonPath("$.fileAccSeminarContentType").value(DEFAULT_FILE_ACC_SEMINAR_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileAccSeminar").value(Base64Utils.encodeToString(DEFAULT_FILE_ACC_SEMINAR)))
            .andExpect(jsonPath("$.ruangan").value(DEFAULT_RUANGAN.toString()))
            .andExpect(jsonPath("$.jamMulai").value(sameInstant(DEFAULT_JAM_MULAI)))
            .andExpect(jsonPath("$.jamSelesai").value(sameInstant(DEFAULT_JAM_SELESAI)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeminar() throws Exception {
        // Get the seminar
        restSeminarMockMvc.perform(get("/api/seminars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeminar() throws Exception {
        // Initialize the database
        seminarRepository.saveAndFlush(seminar);
        int databaseSizeBeforeUpdate = seminarRepository.findAll().size();

        // Update the seminar
        Seminar updatedSeminar = seminarRepository.findOne(seminar.getId());
        updatedSeminar
            .judul(UPDATED_JUDUL)
            .abstrak(UPDATED_ABSTRAK)
            .fileAccSeminar(UPDATED_FILE_ACC_SEMINAR)
            .fileAccSeminarContentType(UPDATED_FILE_ACC_SEMINAR_CONTENT_TYPE)
            .ruangan(UPDATED_RUANGAN)
            .jamMulai(UPDATED_JAM_MULAI)
            .jamSelesai(UPDATED_JAM_SELESAI)
            .status(UPDATED_STATUS);
        SeminarDTO seminarDTO = seminarMapper.toDto(updatedSeminar);

        restSeminarMockMvc.perform(put("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isOk());

        // Validate the Seminar in the database
        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeUpdate);
        Seminar testSeminar = seminarList.get(seminarList.size() - 1);
        assertThat(testSeminar.getJudul()).isEqualTo(UPDATED_JUDUL);
        assertThat(testSeminar.getAbstrak()).isEqualTo(UPDATED_ABSTRAK);
        assertThat(testSeminar.getFileAccSeminar()).isEqualTo(UPDATED_FILE_ACC_SEMINAR);
        assertThat(testSeminar.getFileAccSeminarContentType()).isEqualTo(UPDATED_FILE_ACC_SEMINAR_CONTENT_TYPE);
        assertThat(testSeminar.getJamMulai()).isEqualTo(UPDATED_JAM_MULAI);
        assertThat(testSeminar.getJamSelesai()).isEqualTo(UPDATED_JAM_SELESAI);
        assertThat(testSeminar.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingSeminar() throws Exception {
        int databaseSizeBeforeUpdate = seminarRepository.findAll().size();

        // Create the Seminar
        SeminarDTO seminarDTO = seminarMapper.toDto(seminar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeminarMockMvc.perform(put("/api/seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seminarDTO)))
            .andExpect(status().isCreated());

        // Validate the Seminar in the database
        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeminar() throws Exception {
        // Initialize the database
        seminarRepository.saveAndFlush(seminar);
        int databaseSizeBeforeDelete = seminarRepository.findAll().size();

        // Get the seminar
        restSeminarMockMvc.perform(delete("/api/seminars/{id}", seminar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seminar> seminarList = seminarRepository.findAll();
        assertThat(seminarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seminar.class);
        Seminar seminar1 = new Seminar();
        seminar1.setId(1L);
        Seminar seminar2 = new Seminar();
        seminar2.setId(seminar1.getId());
        assertThat(seminar1).isEqualTo(seminar2);
        seminar2.setId(2L);
        assertThat(seminar1).isNotEqualTo(seminar2);
        seminar1.setId(null);
        assertThat(seminar1).isNotEqualTo(seminar2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeminarDTO.class);
        SeminarDTO seminarDTO1 = new SeminarDTO();
        seminarDTO1.setId(1L);
        SeminarDTO seminarDTO2 = new SeminarDTO();
        assertThat(seminarDTO1).isNotEqualTo(seminarDTO2);
        seminarDTO2.setId(seminarDTO1.getId());
        assertThat(seminarDTO1).isEqualTo(seminarDTO2);
        seminarDTO2.setId(2L);
        assertThat(seminarDTO1).isNotEqualTo(seminarDTO2);
        seminarDTO1.setId(null);
        assertThat(seminarDTO1).isNotEqualTo(seminarDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seminarMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seminarMapper.fromId(null)).isNull();
    }
}
