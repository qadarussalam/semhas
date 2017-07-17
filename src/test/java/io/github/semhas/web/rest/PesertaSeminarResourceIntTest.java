package io.github.semhas.web.rest;

import io.github.semhas.SemhasApp;

import io.github.semhas.domain.PesertaSeminar;
import io.github.semhas.repository.PesertaSeminarRepository;
import io.github.semhas.service.PesertaSeminarService;
import io.github.semhas.service.dto.PesertaSeminarDTO;
import io.github.semhas.service.mapper.PesertaSeminarMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.semhas.domain.enumeration.AbsensiSeminar;
/**
 * Test class for the PesertaSeminarResource REST controller.
 *
 * @see PesertaSeminarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SemhasApp.class)
public class PesertaSeminarResourceIntTest {

    private static final AbsensiSeminar DEFAULT_ABSENSI = AbsensiSeminar.HADIR;
    private static final AbsensiSeminar UPDATED_ABSENSI = AbsensiSeminar.ABSEN;

    @Autowired
    private PesertaSeminarRepository pesertaSeminarRepository;

    @Autowired
    private PesertaSeminarMapper pesertaSeminarMapper;

    @Autowired
    private PesertaSeminarService pesertaSeminarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPesertaSeminarMockMvc;

    private PesertaSeminar pesertaSeminar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PesertaSeminarResource pesertaSeminarResource = new PesertaSeminarResource(pesertaSeminarService);
        this.restPesertaSeminarMockMvc = MockMvcBuilders.standaloneSetup(pesertaSeminarResource)
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
    public static PesertaSeminar createEntity(EntityManager em) {
        PesertaSeminar pesertaSeminar = new PesertaSeminar()
            .absensi(DEFAULT_ABSENSI);
        return pesertaSeminar;
    }

    @Before
    public void initTest() {
        pesertaSeminar = createEntity(em);
    }

    @Test
    @Transactional
    public void createPesertaSeminar() throws Exception {
        int databaseSizeBeforeCreate = pesertaSeminarRepository.findAll().size();

        // Create the PesertaSeminar
        PesertaSeminarDTO pesertaSeminarDTO = pesertaSeminarMapper.toDto(pesertaSeminar);
        restPesertaSeminarMockMvc.perform(post("/api/peserta-seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pesertaSeminarDTO)))
            .andExpect(status().isCreated());

        // Validate the PesertaSeminar in the database
        List<PesertaSeminar> pesertaSeminarList = pesertaSeminarRepository.findAll();
        assertThat(pesertaSeminarList).hasSize(databaseSizeBeforeCreate + 1);
        PesertaSeminar testPesertaSeminar = pesertaSeminarList.get(pesertaSeminarList.size() - 1);
        assertThat(testPesertaSeminar.getAbsensi()).isEqualTo(DEFAULT_ABSENSI);
    }

    @Test
    @Transactional
    public void createPesertaSeminarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pesertaSeminarRepository.findAll().size();

        // Create the PesertaSeminar with an existing ID
        pesertaSeminar.setId(1L);
        PesertaSeminarDTO pesertaSeminarDTO = pesertaSeminarMapper.toDto(pesertaSeminar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPesertaSeminarMockMvc.perform(post("/api/peserta-seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pesertaSeminarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PesertaSeminar> pesertaSeminarList = pesertaSeminarRepository.findAll();
        assertThat(pesertaSeminarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPesertaSeminars() throws Exception {
        // Initialize the database
        pesertaSeminarRepository.saveAndFlush(pesertaSeminar);

        // Get all the pesertaSeminarList
        restPesertaSeminarMockMvc.perform(get("/api/peserta-seminars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pesertaSeminar.getId().intValue())))
            .andExpect(jsonPath("$.[*].absensi").value(hasItem(DEFAULT_ABSENSI.toString())));
    }

    @Test
    @Transactional
    public void getPesertaSeminar() throws Exception {
        // Initialize the database
        pesertaSeminarRepository.saveAndFlush(pesertaSeminar);

        // Get the pesertaSeminar
        restPesertaSeminarMockMvc.perform(get("/api/peserta-seminars/{id}", pesertaSeminar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pesertaSeminar.getId().intValue()))
            .andExpect(jsonPath("$.absensi").value(DEFAULT_ABSENSI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPesertaSeminar() throws Exception {
        // Get the pesertaSeminar
        restPesertaSeminarMockMvc.perform(get("/api/peserta-seminars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePesertaSeminar() throws Exception {
        // Initialize the database
        pesertaSeminarRepository.saveAndFlush(pesertaSeminar);
        int databaseSizeBeforeUpdate = pesertaSeminarRepository.findAll().size();

        // Update the pesertaSeminar
        PesertaSeminar updatedPesertaSeminar = pesertaSeminarRepository.findOne(pesertaSeminar.getId());
        updatedPesertaSeminar
            .absensi(UPDATED_ABSENSI);
        PesertaSeminarDTO pesertaSeminarDTO = pesertaSeminarMapper.toDto(updatedPesertaSeminar);

        restPesertaSeminarMockMvc.perform(put("/api/peserta-seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pesertaSeminarDTO)))
            .andExpect(status().isOk());

        // Validate the PesertaSeminar in the database
        List<PesertaSeminar> pesertaSeminarList = pesertaSeminarRepository.findAll();
        assertThat(pesertaSeminarList).hasSize(databaseSizeBeforeUpdate);
        PesertaSeminar testPesertaSeminar = pesertaSeminarList.get(pesertaSeminarList.size() - 1);
        assertThat(testPesertaSeminar.getAbsensi()).isEqualTo(UPDATED_ABSENSI);
    }

    @Test
    @Transactional
    public void updateNonExistingPesertaSeminar() throws Exception {
        int databaseSizeBeforeUpdate = pesertaSeminarRepository.findAll().size();

        // Create the PesertaSeminar
        PesertaSeminarDTO pesertaSeminarDTO = pesertaSeminarMapper.toDto(pesertaSeminar);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPesertaSeminarMockMvc.perform(put("/api/peserta-seminars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pesertaSeminarDTO)))
            .andExpect(status().isCreated());

        // Validate the PesertaSeminar in the database
        List<PesertaSeminar> pesertaSeminarList = pesertaSeminarRepository.findAll();
        assertThat(pesertaSeminarList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePesertaSeminar() throws Exception {
        // Initialize the database
        pesertaSeminarRepository.saveAndFlush(pesertaSeminar);
        int databaseSizeBeforeDelete = pesertaSeminarRepository.findAll().size();

        // Get the pesertaSeminar
        restPesertaSeminarMockMvc.perform(delete("/api/peserta-seminars/{id}", pesertaSeminar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PesertaSeminar> pesertaSeminarList = pesertaSeminarRepository.findAll();
        assertThat(pesertaSeminarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PesertaSeminar.class);
        PesertaSeminar pesertaSeminar1 = new PesertaSeminar();
        pesertaSeminar1.setId(1L);
        PesertaSeminar pesertaSeminar2 = new PesertaSeminar();
        pesertaSeminar2.setId(pesertaSeminar1.getId());
        assertThat(pesertaSeminar1).isEqualTo(pesertaSeminar2);
        pesertaSeminar2.setId(2L);
        assertThat(pesertaSeminar1).isNotEqualTo(pesertaSeminar2);
        pesertaSeminar1.setId(null);
        assertThat(pesertaSeminar1).isNotEqualTo(pesertaSeminar2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PesertaSeminarDTO.class);
        PesertaSeminarDTO pesertaSeminarDTO1 = new PesertaSeminarDTO();
        pesertaSeminarDTO1.setId(1L);
        PesertaSeminarDTO pesertaSeminarDTO2 = new PesertaSeminarDTO();
        assertThat(pesertaSeminarDTO1).isNotEqualTo(pesertaSeminarDTO2);
        pesertaSeminarDTO2.setId(pesertaSeminarDTO1.getId());
        assertThat(pesertaSeminarDTO1).isEqualTo(pesertaSeminarDTO2);
        pesertaSeminarDTO2.setId(2L);
        assertThat(pesertaSeminarDTO1).isNotEqualTo(pesertaSeminarDTO2);
        pesertaSeminarDTO1.setId(null);
        assertThat(pesertaSeminarDTO1).isNotEqualTo(pesertaSeminarDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pesertaSeminarMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pesertaSeminarMapper.fromId(null)).isNull();
    }
}
