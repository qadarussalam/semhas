package io.github.semhas.web.rest;

import io.github.semhas.SemhasApp;

import io.github.semhas.domain.Jurusan;
import io.github.semhas.repository.JurusanRepository;
import io.github.semhas.service.JurusanService;
import io.github.semhas.service.dto.JurusanDTO;
import io.github.semhas.service.mapper.JurusanMapper;
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

/**
 * Test class for the JurusanResource REST controller.
 *
 * @see JurusanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SemhasApp.class)
public class JurusanResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    @Autowired
    private JurusanRepository jurusanRepository;

    @Autowired
    private JurusanMapper jurusanMapper;

    @Autowired
    private JurusanService jurusanService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJurusanMockMvc;

    private Jurusan jurusan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JurusanResource jurusanResource = new JurusanResource(jurusanService);
        this.restJurusanMockMvc = MockMvcBuilders.standaloneSetup(jurusanResource)
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
    public static Jurusan createEntity(EntityManager em) {
        Jurusan jurusan = new Jurusan()
            .nama(DEFAULT_NAMA);
        return jurusan;
    }

    @Before
    public void initTest() {
        jurusan = createEntity(em);
    }

    @Test
    @Transactional
    public void createJurusan() throws Exception {
        int databaseSizeBeforeCreate = jurusanRepository.findAll().size();

        // Create the Jurusan
        JurusanDTO jurusanDTO = jurusanMapper.toDto(jurusan);
        restJurusanMockMvc.perform(post("/api/jurusans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jurusanDTO)))
            .andExpect(status().isCreated());

        // Validate the Jurusan in the database
        List<Jurusan> jurusanList = jurusanRepository.findAll();
        assertThat(jurusanList).hasSize(databaseSizeBeforeCreate + 1);
        Jurusan testJurusan = jurusanList.get(jurusanList.size() - 1);
        assertThat(testJurusan.getNama()).isEqualTo(DEFAULT_NAMA);
    }

    @Test
    @Transactional
    public void createJurusanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jurusanRepository.findAll().size();

        // Create the Jurusan with an existing ID
        jurusan.setId(1L);
        JurusanDTO jurusanDTO = jurusanMapper.toDto(jurusan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJurusanMockMvc.perform(post("/api/jurusans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jurusanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Jurusan> jurusanList = jurusanRepository.findAll();
        assertThat(jurusanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = jurusanRepository.findAll().size();
        // set the field null
        jurusan.setNama(null);

        // Create the Jurusan, which fails.
        JurusanDTO jurusanDTO = jurusanMapper.toDto(jurusan);

        restJurusanMockMvc.perform(post("/api/jurusans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jurusanDTO)))
            .andExpect(status().isBadRequest());

        List<Jurusan> jurusanList = jurusanRepository.findAll();
        assertThat(jurusanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJurusans() throws Exception {
        // Initialize the database
        jurusanRepository.saveAndFlush(jurusan);

        // Get all the jurusanList
        restJurusanMockMvc.perform(get("/api/jurusans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jurusan.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())));
    }

    @Test
    @Transactional
    public void getJurusan() throws Exception {
        // Initialize the database
        jurusanRepository.saveAndFlush(jurusan);

        // Get the jurusan
        restJurusanMockMvc.perform(get("/api/jurusans/{id}", jurusan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jurusan.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJurusan() throws Exception {
        // Get the jurusan
        restJurusanMockMvc.perform(get("/api/jurusans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJurusan() throws Exception {
        // Initialize the database
        jurusanRepository.saveAndFlush(jurusan);
        int databaseSizeBeforeUpdate = jurusanRepository.findAll().size();

        // Update the jurusan
        Jurusan updatedJurusan = jurusanRepository.findOne(jurusan.getId());
        updatedJurusan
            .nama(UPDATED_NAMA);
        JurusanDTO jurusanDTO = jurusanMapper.toDto(updatedJurusan);

        restJurusanMockMvc.perform(put("/api/jurusans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jurusanDTO)))
            .andExpect(status().isOk());

        // Validate the Jurusan in the database
        List<Jurusan> jurusanList = jurusanRepository.findAll();
        assertThat(jurusanList).hasSize(databaseSizeBeforeUpdate);
        Jurusan testJurusan = jurusanList.get(jurusanList.size() - 1);
        assertThat(testJurusan.getNama()).isEqualTo(UPDATED_NAMA);
    }

    @Test
    @Transactional
    public void updateNonExistingJurusan() throws Exception {
        int databaseSizeBeforeUpdate = jurusanRepository.findAll().size();

        // Create the Jurusan
        JurusanDTO jurusanDTO = jurusanMapper.toDto(jurusan);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJurusanMockMvc.perform(put("/api/jurusans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jurusanDTO)))
            .andExpect(status().isCreated());

        // Validate the Jurusan in the database
        List<Jurusan> jurusanList = jurusanRepository.findAll();
        assertThat(jurusanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJurusan() throws Exception {
        // Initialize the database
        jurusanRepository.saveAndFlush(jurusan);
        int databaseSizeBeforeDelete = jurusanRepository.findAll().size();

        // Get the jurusan
        restJurusanMockMvc.perform(delete("/api/jurusans/{id}", jurusan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Jurusan> jurusanList = jurusanRepository.findAll();
        assertThat(jurusanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jurusan.class);
        Jurusan jurusan1 = new Jurusan();
        jurusan1.setId(1L);
        Jurusan jurusan2 = new Jurusan();
        jurusan2.setId(jurusan1.getId());
        assertThat(jurusan1).isEqualTo(jurusan2);
        jurusan2.setId(2L);
        assertThat(jurusan1).isNotEqualTo(jurusan2);
        jurusan1.setId(null);
        assertThat(jurusan1).isNotEqualTo(jurusan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JurusanDTO.class);
        JurusanDTO jurusanDTO1 = new JurusanDTO();
        jurusanDTO1.setId(1L);
        JurusanDTO jurusanDTO2 = new JurusanDTO();
        assertThat(jurusanDTO1).isNotEqualTo(jurusanDTO2);
        jurusanDTO2.setId(jurusanDTO1.getId());
        assertThat(jurusanDTO1).isEqualTo(jurusanDTO2);
        jurusanDTO2.setId(2L);
        assertThat(jurusanDTO1).isNotEqualTo(jurusanDTO2);
        jurusanDTO1.setId(null);
        assertThat(jurusanDTO1).isNotEqualTo(jurusanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jurusanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jurusanMapper.fromId(null)).isNull();
    }
}
