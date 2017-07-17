package io.github.semhas.web.rest;

import io.github.semhas.SemhasApp;

import io.github.semhas.domain.JadwalSeminar;
import io.github.semhas.repository.JadwalSeminarRepository;
import io.github.semhas.service.JadwalSeminarService;
import io.github.semhas.service.dto.JadwalSeminarDTO;
import io.github.semhas.service.mapper.JadwalSeminarMapper;
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

/**
 * Test class for the JadwalSeminarResource REST controller.
 *
 * @see JadwalSeminarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SemhasApp.class)
public class JadwalSeminarResourceIntTest {

    private static final ZonedDateTime DEFAULT_TANGGAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TANGGAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_TERSEDIA = false;
    private static final Boolean UPDATED_TERSEDIA = true;

    @Autowired
    private JadwalSeminarRepository jadwalSeminarRepository;

    @Autowired
    private JadwalSeminarMapper jadwalSeminarMapper;

    @Autowired
    private JadwalSeminarService jadwalSeminarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJadwalSeminarMockMvc;

    private JadwalSeminar jadwalSeminar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JadwalSeminarResource jadwalSeminarResource = new JadwalSeminarResource(jadwalSeminarService);
        this.restJadwalSeminarMockMvc = MockMvcBuilders.standaloneSetup(jadwalSeminarResource)
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
    public static JadwalSeminar createEntity(EntityManager em) {
        JadwalSeminar jadwalSeminar = new JadwalSeminar()
            .tanggal(DEFAULT_TANGGAL)
            .tersedia(DEFAULT_TERSEDIA);
        return jadwalSeminar;
    }

    @Before
    public void initTest() {
        jadwalSeminar = createEntity(em);
    }

    @Test
    @Transactional
    public void createJadwalSeminar() throws Exception {
        int databaseSizeBeforeCreate = jadwalSeminarRepository.findAll().size();

        // Create the JadwalSeminar
        JadwalSeminarDTO jadwalSeminarDTO = jadwalSeminarMapper.toDto(jadwalSeminar);
        restJadwalSeminarMockMvc.perform(post("/api/jadwal-seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jadwalSeminarDTO)))
            .andExpect(status().isCreated());

        // Validate the JadwalSeminar in the database
        List<JadwalSeminar> jadwalSeminarList = jadwalSeminarRepository.findAll();
        assertThat(jadwalSeminarList).hasSize(databaseSizeBeforeCreate + 1);
        JadwalSeminar testJadwalSeminar = jadwalSeminarList.get(jadwalSeminarList.size() - 1);
        assertThat(testJadwalSeminar.getTanggal()).isEqualTo(DEFAULT_TANGGAL);
        assertThat(testJadwalSeminar.isTersedia()).isEqualTo(DEFAULT_TERSEDIA);
    }

    @Test
    @Transactional
    public void createJadwalSeminarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jadwalSeminarRepository.findAll().size();

        // Create the JadwalSeminar with an existing ID
        jadwalSeminar.setId(1L);
        JadwalSeminarDTO jadwalSeminarDTO = jadwalSeminarMapper.toDto(jadwalSeminar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJadwalSeminarMockMvc.perform(post("/api/jadwal-seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jadwalSeminarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JadwalSeminar> jadwalSeminarList = jadwalSeminarRepository.findAll();
        assertThat(jadwalSeminarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJadwalSeminars() throws Exception {
        // Initialize the database
        jadwalSeminarRepository.saveAndFlush(jadwalSeminar);

        // Get all the jadwalSeminarList
        restJadwalSeminarMockMvc.perform(get("/api/jadwal-seminars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jadwalSeminar.getId().intValue())))
            .andExpect(jsonPath("$.[*].tanggal").value(hasItem(sameInstant(DEFAULT_TANGGAL))))
            .andExpect(jsonPath("$.[*].tersedia").value(hasItem(DEFAULT_TERSEDIA.booleanValue())));
    }

    @Test
    @Transactional
    public void getJadwalSeminar() throws Exception {
        // Initialize the database
        jadwalSeminarRepository.saveAndFlush(jadwalSeminar);

        // Get the jadwalSeminar
        restJadwalSeminarMockMvc.perform(get("/api/jadwal-seminars/{id}", jadwalSeminar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jadwalSeminar.getId().intValue()))
            .andExpect(jsonPath("$.tanggal").value(sameInstant(DEFAULT_TANGGAL)))
            .andExpect(jsonPath("$.tersedia").value(DEFAULT_TERSEDIA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJadwalSeminar() throws Exception {
        // Get the jadwalSeminar
        restJadwalSeminarMockMvc.perform(get("/api/jadwal-seminars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJadwalSeminar() throws Exception {
        // Initialize the database
        jadwalSeminarRepository.saveAndFlush(jadwalSeminar);
        int databaseSizeBeforeUpdate = jadwalSeminarRepository.findAll().size();

        // Update the jadwalSeminar
        JadwalSeminar updatedJadwalSeminar = jadwalSeminarRepository.findOne(jadwalSeminar.getId());
        updatedJadwalSeminar
            .tanggal(UPDATED_TANGGAL)
            .tersedia(UPDATED_TERSEDIA);
        JadwalSeminarDTO jadwalSeminarDTO = jadwalSeminarMapper.toDto(updatedJadwalSeminar);

        restJadwalSeminarMockMvc.perform(put("/api/jadwal-seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jadwalSeminarDTO)))
            .andExpect(status().isOk());

        // Validate the JadwalSeminar in the database
        List<JadwalSeminar> jadwalSeminarList = jadwalSeminarRepository.findAll();
        assertThat(jadwalSeminarList).hasSize(databaseSizeBeforeUpdate);
        JadwalSeminar testJadwalSeminar = jadwalSeminarList.get(jadwalSeminarList.size() - 1);
        assertThat(testJadwalSeminar.getTanggal()).isEqualTo(UPDATED_TANGGAL);
        assertThat(testJadwalSeminar.isTersedia()).isEqualTo(UPDATED_TERSEDIA);
    }

    @Test
    @Transactional
    public void updateNonExistingJadwalSeminar() throws Exception {
        int databaseSizeBeforeUpdate = jadwalSeminarRepository.findAll().size();

        // Create the JadwalSeminar
        JadwalSeminarDTO jadwalSeminarDTO = jadwalSeminarMapper.toDto(jadwalSeminar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJadwalSeminarMockMvc.perform(put("/api/jadwal-seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jadwalSeminarDTO)))
            .andExpect(status().isCreated());

        // Validate the JadwalSeminar in the database
        List<JadwalSeminar> jadwalSeminarList = jadwalSeminarRepository.findAll();
        assertThat(jadwalSeminarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJadwalSeminar() throws Exception {
        // Initialize the database
        jadwalSeminarRepository.saveAndFlush(jadwalSeminar);
        int databaseSizeBeforeDelete = jadwalSeminarRepository.findAll().size();

        // Get the jadwalSeminar
        restJadwalSeminarMockMvc.perform(delete("/api/jadwal-seminars/{id}", jadwalSeminar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JadwalSeminar> jadwalSeminarList = jadwalSeminarRepository.findAll();
        assertThat(jadwalSeminarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JadwalSeminar.class);
        JadwalSeminar jadwalSeminar1 = new JadwalSeminar();
        jadwalSeminar1.setId(1L);
        JadwalSeminar jadwalSeminar2 = new JadwalSeminar();
        jadwalSeminar2.setId(jadwalSeminar1.getId());
        assertThat(jadwalSeminar1).isEqualTo(jadwalSeminar2);
        jadwalSeminar2.setId(2L);
        assertThat(jadwalSeminar1).isNotEqualTo(jadwalSeminar2);
        jadwalSeminar1.setId(null);
        assertThat(jadwalSeminar1).isNotEqualTo(jadwalSeminar2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JadwalSeminarDTO.class);
        JadwalSeminarDTO jadwalSeminarDTO1 = new JadwalSeminarDTO();
        jadwalSeminarDTO1.setId(1L);
        JadwalSeminarDTO jadwalSeminarDTO2 = new JadwalSeminarDTO();
        assertThat(jadwalSeminarDTO1).isNotEqualTo(jadwalSeminarDTO2);
        jadwalSeminarDTO2.setId(jadwalSeminarDTO1.getId());
        assertThat(jadwalSeminarDTO1).isEqualTo(jadwalSeminarDTO2);
        jadwalSeminarDTO2.setId(2L);
        assertThat(jadwalSeminarDTO1).isNotEqualTo(jadwalSeminarDTO2);
        jadwalSeminarDTO1.setId(null);
        assertThat(jadwalSeminarDTO1).isNotEqualTo(jadwalSeminarDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jadwalSeminarMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jadwalSeminarMapper.fromId(null)).isNull();
    }
}
