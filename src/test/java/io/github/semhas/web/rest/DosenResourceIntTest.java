package io.github.semhas.web.rest;

import io.github.semhas.SemhasApp;

import io.github.semhas.domain.Dosen;
import io.github.semhas.repository.DosenRepository;
import io.github.semhas.service.DosenService;
import io.github.semhas.service.dto.DosenDTO;
import io.github.semhas.service.mapper.DosenMapper;
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
 * Test class for the DosenResource REST controller.
 *
 * @see DosenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SemhasApp.class)
public class DosenResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_NIP = "AAAAAAAAAA";
    private static final String UPDATED_NIP = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMOR_TELEPON = "AAAAAAAAAA";
    private static final String UPDATED_NOMOR_TELEPON = "BBBBBBBBBB";

    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private DosenMapper dosenMapper;

    @Autowired
    private DosenService dosenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDosenMockMvc;

    private Dosen dosen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DosenResource dosenResource = new DosenResource(dosenService);
        this.restDosenMockMvc = MockMvcBuilders.standaloneSetup(dosenResource)
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
    public static Dosen createEntity(EntityManager em) {
        Dosen dosen = new Dosen()
            .nama(DEFAULT_NAMA)
            .nip(DEFAULT_NIP)
            .email(DEFAULT_EMAIL)
            .nomorTelepon(DEFAULT_NOMOR_TELEPON);
        return dosen;
    }

    @Before
    public void initTest() {
        dosen = createEntity(em);
    }

    @Test
    @Transactional
    public void createDosen() throws Exception {
        int databaseSizeBeforeCreate = dosenRepository.findAll().size();

        // Create the Dosen
        DosenDTO dosenDTO = dosenMapper.toDto(dosen);
        restDosenMockMvc.perform(post("/api/dosens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosenDTO)))
            .andExpect(status().isCreated());

        // Validate the Dosen in the database
        List<Dosen> dosenList = dosenRepository.findAll();
        assertThat(dosenList).hasSize(databaseSizeBeforeCreate + 1);
        Dosen testDosen = dosenList.get(dosenList.size() - 1);
        assertThat(testDosen.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testDosen.getNip()).isEqualTo(DEFAULT_NIP);
        assertThat(testDosen.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDosen.getNomorTelepon()).isEqualTo(DEFAULT_NOMOR_TELEPON);
    }

    @Test
    @Transactional
    public void createDosenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dosenRepository.findAll().size();

        // Create the Dosen with an existing ID
        dosen.setId(1L);
        DosenDTO dosenDTO = dosenMapper.toDto(dosen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDosenMockMvc.perform(post("/api/dosens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Dosen> dosenList = dosenRepository.findAll();
        assertThat(dosenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dosenRepository.findAll().size();
        // set the field null
        dosen.setNama(null);

        // Create the Dosen, which fails.
        DosenDTO dosenDTO = dosenMapper.toDto(dosen);

        restDosenMockMvc.perform(post("/api/dosens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosenDTO)))
            .andExpect(status().isBadRequest());

        List<Dosen> dosenList = dosenRepository.findAll();
        assertThat(dosenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNipIsRequired() throws Exception {
        int databaseSizeBeforeTest = dosenRepository.findAll().size();
        // set the field null
        dosen.setNip(null);

        // Create the Dosen, which fails.
        DosenDTO dosenDTO = dosenMapper.toDto(dosen);

        restDosenMockMvc.perform(post("/api/dosens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosenDTO)))
            .andExpect(status().isBadRequest());

        List<Dosen> dosenList = dosenRepository.findAll();
        assertThat(dosenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = dosenRepository.findAll().size();
        // set the field null
        dosen.setEmail(null);

        // Create the Dosen, which fails.
        DosenDTO dosenDTO = dosenMapper.toDto(dosen);

        restDosenMockMvc.perform(post("/api/dosens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosenDTO)))
            .andExpect(status().isBadRequest());

        List<Dosen> dosenList = dosenRepository.findAll();
        assertThat(dosenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomorTeleponIsRequired() throws Exception {
        int databaseSizeBeforeTest = dosenRepository.findAll().size();
        // set the field null
        dosen.setNomorTelepon(null);

        // Create the Dosen, which fails.
        DosenDTO dosenDTO = dosenMapper.toDto(dosen);

        restDosenMockMvc.perform(post("/api/dosens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosenDTO)))
            .andExpect(status().isBadRequest());

        List<Dosen> dosenList = dosenRepository.findAll();
        assertThat(dosenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDosens() throws Exception {
        // Initialize the database
        dosenRepository.saveAndFlush(dosen);

        // Get all the dosenList
        restDosenMockMvc.perform(get("/api/dosens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosen.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].nip").value(hasItem(DEFAULT_NIP.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].nomorTelepon").value(hasItem(DEFAULT_NOMOR_TELEPON.toString())));
    }

    @Test
    @Transactional
    public void getDosen() throws Exception {
        // Initialize the database
        dosenRepository.saveAndFlush(dosen);

        // Get the dosen
        restDosenMockMvc.perform(get("/api/dosens/{id}", dosen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dosen.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.nip").value(DEFAULT_NIP.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.nomorTelepon").value(DEFAULT_NOMOR_TELEPON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDosen() throws Exception {
        // Get the dosen
        restDosenMockMvc.perform(get("/api/dosens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDosen() throws Exception {
        // Initialize the database
        dosenRepository.saveAndFlush(dosen);
        int databaseSizeBeforeUpdate = dosenRepository.findAll().size();

        // Update the dosen
        Dosen updatedDosen = dosenRepository.findOne(dosen.getId());
        updatedDosen
            .nama(UPDATED_NAMA)
            .nip(UPDATED_NIP)
            .email(UPDATED_EMAIL)
            .nomorTelepon(UPDATED_NOMOR_TELEPON);
        DosenDTO dosenDTO = dosenMapper.toDto(updatedDosen);

        restDosenMockMvc.perform(put("/api/dosens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosenDTO)))
            .andExpect(status().isOk());

        // Validate the Dosen in the database
        List<Dosen> dosenList = dosenRepository.findAll();
        assertThat(dosenList).hasSize(databaseSizeBeforeUpdate);
        Dosen testDosen = dosenList.get(dosenList.size() - 1);
        assertThat(testDosen.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testDosen.getNip()).isEqualTo(UPDATED_NIP);
        assertThat(testDosen.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDosen.getNomorTelepon()).isEqualTo(UPDATED_NOMOR_TELEPON);
    }

    @Test
    @Transactional
    public void updateNonExistingDosen() throws Exception {
        int databaseSizeBeforeUpdate = dosenRepository.findAll().size();

        // Create the Dosen
        DosenDTO dosenDTO = dosenMapper.toDto(dosen);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDosenMockMvc.perform(put("/api/dosens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dosenDTO)))
            .andExpect(status().isCreated());

        // Validate the Dosen in the database
        List<Dosen> dosenList = dosenRepository.findAll();
        assertThat(dosenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDosen() throws Exception {
        // Initialize the database
        dosenRepository.saveAndFlush(dosen);
        int databaseSizeBeforeDelete = dosenRepository.findAll().size();

        // Get the dosen
        restDosenMockMvc.perform(delete("/api/dosens/{id}", dosen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dosen> dosenList = dosenRepository.findAll();
        assertThat(dosenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dosen.class);
        Dosen dosen1 = new Dosen();
        dosen1.setId(1L);
        Dosen dosen2 = new Dosen();
        dosen2.setId(dosen1.getId());
        assertThat(dosen1).isEqualTo(dosen2);
        dosen2.setId(2L);
        assertThat(dosen1).isNotEqualTo(dosen2);
        dosen1.setId(null);
        assertThat(dosen1).isNotEqualTo(dosen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DosenDTO.class);
        DosenDTO dosenDTO1 = new DosenDTO();
        dosenDTO1.setId(1L);
        DosenDTO dosenDTO2 = new DosenDTO();
        assertThat(dosenDTO1).isNotEqualTo(dosenDTO2);
        dosenDTO2.setId(dosenDTO1.getId());
        assertThat(dosenDTO1).isEqualTo(dosenDTO2);
        dosenDTO2.setId(2L);
        assertThat(dosenDTO1).isNotEqualTo(dosenDTO2);
        dosenDTO1.setId(null);
        assertThat(dosenDTO1).isNotEqualTo(dosenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dosenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dosenMapper.fromId(null)).isNull();
    }
}
