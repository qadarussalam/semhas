package io.github.semhas.web.rest;

import io.github.semhas.SemhasApp;

import io.github.semhas.domain.Ruang;
import io.github.semhas.repository.RuangRepository;
import io.github.semhas.service.RuangService;
import io.github.semhas.service.dto.RuangDTO;
import io.github.semhas.service.mapper.RuangMapper;
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
 * Test class for the RuangResource REST controller.
 *
 * @see RuangResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SemhasApp.class)
public class RuangResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final Integer DEFAULT_KAPASITAS = 1;
    private static final Integer UPDATED_KAPASITAS = 2;

    @Autowired
    private RuangRepository ruangRepository;

    @Autowired
    private RuangMapper ruangMapper;

    @Autowired
    private RuangService ruangService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRuangMockMvc;

    private Ruang ruang;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RuangResource ruangResource = new RuangResource(ruangService);
        this.restRuangMockMvc = MockMvcBuilders.standaloneSetup(ruangResource)
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
    public static Ruang createEntity(EntityManager em) {
        Ruang ruang = new Ruang()
            .nama(DEFAULT_NAMA)
            .kapasitas(DEFAULT_KAPASITAS);
        return ruang;
    }

    @Before
    public void initTest() {
        ruang = createEntity(em);
    }

    @Test
    @Transactional
    public void createRuang() throws Exception {
        int databaseSizeBeforeCreate = ruangRepository.findAll().size();

        // Create the Ruang
        RuangDTO ruangDTO = ruangMapper.toDto(ruang);
        restRuangMockMvc.perform(post("/api/ruangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruangDTO)))
            .andExpect(status().isCreated());

        // Validate the Ruang in the database
        List<Ruang> ruangList = ruangRepository.findAll();
        assertThat(ruangList).hasSize(databaseSizeBeforeCreate + 1);
        Ruang testRuang = ruangList.get(ruangList.size() - 1);
        assertThat(testRuang.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testRuang.getKapasitas()).isEqualTo(DEFAULT_KAPASITAS);
    }

    @Test
    @Transactional
    public void createRuangWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruangRepository.findAll().size();

        // Create the Ruang with an existing ID
        ruang.setId(1L);
        RuangDTO ruangDTO = ruangMapper.toDto(ruang);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuangMockMvc.perform(post("/api/ruangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruangDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ruang> ruangList = ruangRepository.findAll();
        assertThat(ruangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRuangs() throws Exception {
        // Initialize the database
        ruangRepository.saveAndFlush(ruang);

        // Get all the ruangList
        restRuangMockMvc.perform(get("/api/ruangs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruang.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].kapasitas").value(hasItem(DEFAULT_KAPASITAS)));
    }

    @Test
    @Transactional
    public void getRuang() throws Exception {
        // Initialize the database
        ruangRepository.saveAndFlush(ruang);

        // Get the ruang
        restRuangMockMvc.perform(get("/api/ruangs/{id}", ruang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ruang.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.kapasitas").value(DEFAULT_KAPASITAS));
    }

    @Test
    @Transactional
    public void getNonExistingRuang() throws Exception {
        // Get the ruang
        restRuangMockMvc.perform(get("/api/ruangs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRuang() throws Exception {
        // Initialize the database
        ruangRepository.saveAndFlush(ruang);
        int databaseSizeBeforeUpdate = ruangRepository.findAll().size();

        // Update the ruang
        Ruang updatedRuang = ruangRepository.findOne(ruang.getId());
        updatedRuang
            .nama(UPDATED_NAMA)
            .kapasitas(UPDATED_KAPASITAS);
        RuangDTO ruangDTO = ruangMapper.toDto(updatedRuang);

        restRuangMockMvc.perform(put("/api/ruangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruangDTO)))
            .andExpect(status().isOk());

        // Validate the Ruang in the database
        List<Ruang> ruangList = ruangRepository.findAll();
        assertThat(ruangList).hasSize(databaseSizeBeforeUpdate);
        Ruang testRuang = ruangList.get(ruangList.size() - 1);
        assertThat(testRuang.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testRuang.getKapasitas()).isEqualTo(UPDATED_KAPASITAS);
    }

    @Test
    @Transactional
    public void updateNonExistingRuang() throws Exception {
        int databaseSizeBeforeUpdate = ruangRepository.findAll().size();

        // Create the Ruang
        RuangDTO ruangDTO = ruangMapper.toDto(ruang);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRuangMockMvc.perform(put("/api/ruangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ruangDTO)))
            .andExpect(status().isCreated());

        // Validate the Ruang in the database
        List<Ruang> ruangList = ruangRepository.findAll();
        assertThat(ruangList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRuang() throws Exception {
        // Initialize the database
        ruangRepository.saveAndFlush(ruang);
        int databaseSizeBeforeDelete = ruangRepository.findAll().size();

        // Get the ruang
        restRuangMockMvc.perform(delete("/api/ruangs/{id}", ruang.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ruang> ruangList = ruangRepository.findAll();
        assertThat(ruangList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ruang.class);
        Ruang ruang1 = new Ruang();
        ruang1.setId(1L);
        Ruang ruang2 = new Ruang();
        ruang2.setId(ruang1.getId());
        assertThat(ruang1).isEqualTo(ruang2);
        ruang2.setId(2L);
        assertThat(ruang1).isNotEqualTo(ruang2);
        ruang1.setId(null);
        assertThat(ruang1).isNotEqualTo(ruang2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RuangDTO.class);
        RuangDTO ruangDTO1 = new RuangDTO();
        ruangDTO1.setId(1L);
        RuangDTO ruangDTO2 = new RuangDTO();
        assertThat(ruangDTO1).isNotEqualTo(ruangDTO2);
        ruangDTO2.setId(ruangDTO1.getId());
        assertThat(ruangDTO1).isEqualTo(ruangDTO2);
        ruangDTO2.setId(2L);
        assertThat(ruangDTO1).isNotEqualTo(ruangDTO2);
        ruangDTO1.setId(null);
        assertThat(ruangDTO1).isNotEqualTo(ruangDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ruangMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ruangMapper.fromId(null)).isNull();
    }
}
