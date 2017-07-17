package io.github.semhas.web.rest;

import io.github.semhas.SemhasApp;

import io.github.semhas.domain.Sesi;
import io.github.semhas.repository.SesiRepository;
import io.github.semhas.service.SesiService;
import io.github.semhas.service.dto.SesiDTO;
import io.github.semhas.service.mapper.SesiMapper;
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
 * Test class for the SesiResource REST controller.
 *
 * @see SesiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SemhasApp.class)
public class SesiResourceIntTest {

    private static final Integer DEFAULT_URUTAN = 1;
    private static final Integer UPDATED_URUTAN = 2;

    private static final ZonedDateTime DEFAULT_JAM_MULAI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JAM_MULAI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_JAM_SELESAI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JAM_SELESAI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SesiRepository sesiRepository;

    @Autowired
    private SesiMapper sesiMapper;

    @Autowired
    private SesiService sesiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSesiMockMvc;

    private Sesi sesi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SesiResource sesiResource = new SesiResource(sesiService);
        this.restSesiMockMvc = MockMvcBuilders.standaloneSetup(sesiResource)
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
    public static Sesi createEntity(EntityManager em) {
        Sesi sesi = new Sesi()
            .urutan(DEFAULT_URUTAN)
            .jamMulai(DEFAULT_JAM_MULAI)
            .jamSelesai(DEFAULT_JAM_SELESAI);
        return sesi;
    }

    @Before
    public void initTest() {
        sesi = createEntity(em);
    }

    @Test
    @Transactional
    public void createSesi() throws Exception {
        int databaseSizeBeforeCreate = sesiRepository.findAll().size();

        // Create the Sesi
        SesiDTO sesiDTO = sesiMapper.toDto(sesi);
        restSesiMockMvc.perform(post("/api/sesis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sesiDTO)))
            .andExpect(status().isCreated());

        // Validate the Sesi in the database
        List<Sesi> sesiList = sesiRepository.findAll();
        assertThat(sesiList).hasSize(databaseSizeBeforeCreate + 1);
        Sesi testSesi = sesiList.get(sesiList.size() - 1);
        assertThat(testSesi.getUrutan()).isEqualTo(DEFAULT_URUTAN);
        assertThat(testSesi.getJamMulai()).isEqualTo(DEFAULT_JAM_MULAI);
        assertThat(testSesi.getJamSelesai()).isEqualTo(DEFAULT_JAM_SELESAI);
    }

    @Test
    @Transactional
    public void createSesiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sesiRepository.findAll().size();

        // Create the Sesi with an existing ID
        sesi.setId(1L);
        SesiDTO sesiDTO = sesiMapper.toDto(sesi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSesiMockMvc.perform(post("/api/sesis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sesiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Sesi> sesiList = sesiRepository.findAll();
        assertThat(sesiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSesis() throws Exception {
        // Initialize the database
        sesiRepository.saveAndFlush(sesi);

        // Get all the sesiList
        restSesiMockMvc.perform(get("/api/sesis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sesi.getId().intValue())))
            .andExpect(jsonPath("$.[*].urutan").value(hasItem(DEFAULT_URUTAN)))
            .andExpect(jsonPath("$.[*].jamMulai").value(hasItem(sameInstant(DEFAULT_JAM_MULAI))))
            .andExpect(jsonPath("$.[*].jamSelesai").value(hasItem(sameInstant(DEFAULT_JAM_SELESAI))));
    }

    @Test
    @Transactional
    public void getSesi() throws Exception {
        // Initialize the database
        sesiRepository.saveAndFlush(sesi);

        // Get the sesi
        restSesiMockMvc.perform(get("/api/sesis/{id}", sesi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sesi.getId().intValue()))
            .andExpect(jsonPath("$.urutan").value(DEFAULT_URUTAN))
            .andExpect(jsonPath("$.jamMulai").value(sameInstant(DEFAULT_JAM_MULAI)))
            .andExpect(jsonPath("$.jamSelesai").value(sameInstant(DEFAULT_JAM_SELESAI)));
    }

    @Test
    @Transactional
    public void getNonExistingSesi() throws Exception {
        // Get the sesi
        restSesiMockMvc.perform(get("/api/sesis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSesi() throws Exception {
        // Initialize the database
        sesiRepository.saveAndFlush(sesi);
        int databaseSizeBeforeUpdate = sesiRepository.findAll().size();

        // Update the sesi
        Sesi updatedSesi = sesiRepository.findOne(sesi.getId());
        updatedSesi
            .urutan(UPDATED_URUTAN)
            .jamMulai(UPDATED_JAM_MULAI)
            .jamSelesai(UPDATED_JAM_SELESAI);
        SesiDTO sesiDTO = sesiMapper.toDto(updatedSesi);

        restSesiMockMvc.perform(put("/api/sesis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sesiDTO)))
            .andExpect(status().isOk());

        // Validate the Sesi in the database
        List<Sesi> sesiList = sesiRepository.findAll();
        assertThat(sesiList).hasSize(databaseSizeBeforeUpdate);
        Sesi testSesi = sesiList.get(sesiList.size() - 1);
        assertThat(testSesi.getUrutan()).isEqualTo(UPDATED_URUTAN);
        assertThat(testSesi.getJamMulai()).isEqualTo(UPDATED_JAM_MULAI);
        assertThat(testSesi.getJamSelesai()).isEqualTo(UPDATED_JAM_SELESAI);
    }

    @Test
    @Transactional
    public void updateNonExistingSesi() throws Exception {
        int databaseSizeBeforeUpdate = sesiRepository.findAll().size();

        // Create the Sesi
        SesiDTO sesiDTO = sesiMapper.toDto(sesi);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSesiMockMvc.perform(put("/api/sesis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sesiDTO)))
            .andExpect(status().isCreated());

        // Validate the Sesi in the database
        List<Sesi> sesiList = sesiRepository.findAll();
        assertThat(sesiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSesi() throws Exception {
        // Initialize the database
        sesiRepository.saveAndFlush(sesi);
        int databaseSizeBeforeDelete = sesiRepository.findAll().size();

        // Get the sesi
        restSesiMockMvc.perform(delete("/api/sesis/{id}", sesi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sesi> sesiList = sesiRepository.findAll();
        assertThat(sesiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sesi.class);
        Sesi sesi1 = new Sesi();
        sesi1.setId(1L);
        Sesi sesi2 = new Sesi();
        sesi2.setId(sesi1.getId());
        assertThat(sesi1).isEqualTo(sesi2);
        sesi2.setId(2L);
        assertThat(sesi1).isNotEqualTo(sesi2);
        sesi1.setId(null);
        assertThat(sesi1).isNotEqualTo(sesi2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SesiDTO.class);
        SesiDTO sesiDTO1 = new SesiDTO();
        sesiDTO1.setId(1L);
        SesiDTO sesiDTO2 = new SesiDTO();
        assertThat(sesiDTO1).isNotEqualTo(sesiDTO2);
        sesiDTO2.setId(sesiDTO1.getId());
        assertThat(sesiDTO1).isEqualTo(sesiDTO2);
        sesiDTO2.setId(2L);
        assertThat(sesiDTO1).isNotEqualTo(sesiDTO2);
        sesiDTO1.setId(null);
        assertThat(sesiDTO1).isNotEqualTo(sesiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sesiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sesiMapper.fromId(null)).isNull();
    }
}
