package io.github.semhas.web.rest;

import io.github.semhas.SemhasApp;

import io.github.semhas.domain.Mahasiswa;
import io.github.semhas.repository.MahasiswaRepository;
import io.github.semhas.service.MahasiswaService;
import io.github.semhas.service.dto.MahasiswaDTO;
import io.github.semhas.service.mapper.MahasiswaMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MahasiswaResource REST controller.
 *
 * @see MahasiswaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SemhasApp.class)
public class MahasiswaResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_NIM = "AAAAAAAAAA";
    private static final String UPDATED_NIM = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEMESTER = 1;
    private static final Integer UPDATED_SEMESTER = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMOR_TELEPON = "AAAAAAAAAA";
    private static final String UPDATED_NOMOR_TELEPON = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private MahasiswaMapper mahasiswaMapper;

    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMahasiswaMockMvc;

    private Mahasiswa mahasiswa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MahasiswaResource mahasiswaResource = new MahasiswaResource(mahasiswaService);
        this.restMahasiswaMockMvc = MockMvcBuilders.standaloneSetup(mahasiswaResource)
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
    public static Mahasiswa createEntity(EntityManager em) {
        Mahasiswa mahasiswa = new Mahasiswa()
            .nama(DEFAULT_NAMA)
            .nim(DEFAULT_NIM)
            .semester(DEFAULT_SEMESTER)
            .email(DEFAULT_EMAIL)
            .nomorTelepon(DEFAULT_NOMOR_TELEPON)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return mahasiswa;
    }

    @Before
    public void initTest() {
        mahasiswa = createEntity(em);
    }

    @Test
    @Transactional
    public void createMahasiswa() throws Exception {
        int databaseSizeBeforeCreate = mahasiswaRepository.findAll().size();

        // Create the Mahasiswa
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);
        restMahasiswaMockMvc.perform(post("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isCreated());

        // Validate the Mahasiswa in the database
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeCreate + 1);
        Mahasiswa testMahasiswa = mahasiswaList.get(mahasiswaList.size() - 1);
        assertThat(testMahasiswa.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMahasiswa.getNim()).isEqualTo(DEFAULT_NIM);
        assertThat(testMahasiswa.getSemester()).isEqualTo(DEFAULT_SEMESTER);
        assertThat(testMahasiswa.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMahasiswa.getNomorTelepon()).isEqualTo(DEFAULT_NOMOR_TELEPON);
        assertThat(testMahasiswa.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testMahasiswa.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMahasiswaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mahasiswaRepository.findAll().size();

        // Create the Mahasiswa with an existing ID
        mahasiswa.setId(1L);
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMahasiswaMockMvc.perform(post("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = mahasiswaRepository.findAll().size();
        // set the field null
        mahasiswa.setNama(null);

        // Create the Mahasiswa, which fails.
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);

        restMahasiswaMockMvc.perform(post("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isBadRequest());

        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNimIsRequired() throws Exception {
        int databaseSizeBeforeTest = mahasiswaRepository.findAll().size();
        // set the field null
        mahasiswa.setNim(null);

        // Create the Mahasiswa, which fails.
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);

        restMahasiswaMockMvc.perform(post("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isBadRequest());

        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSemesterIsRequired() throws Exception {
        int databaseSizeBeforeTest = mahasiswaRepository.findAll().size();
        // set the field null
        mahasiswa.setSemester(null);

        // Create the Mahasiswa, which fails.
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);

        restMahasiswaMockMvc.perform(post("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isBadRequest());

        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = mahasiswaRepository.findAll().size();
        // set the field null
        mahasiswa.setEmail(null);

        // Create the Mahasiswa, which fails.
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);

        restMahasiswaMockMvc.perform(post("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isBadRequest());

        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFotoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mahasiswaRepository.findAll().size();
        // set the field null
        mahasiswa.setFoto(null);

        // Create the Mahasiswa, which fails.
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);

        restMahasiswaMockMvc.perform(post("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isBadRequest());

        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMahasiswas() throws Exception {
        // Initialize the database
        mahasiswaRepository.saveAndFlush(mahasiswa);

        // Get all the mahasiswaList
        restMahasiswaMockMvc.perform(get("/api/mahasiswas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mahasiswa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].nim").value(hasItem(DEFAULT_NIM.toString())))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].nomorTelepon").value(hasItem(DEFAULT_NOMOR_TELEPON.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    public void getMahasiswa() throws Exception {
        // Initialize the database
        mahasiswaRepository.saveAndFlush(mahasiswa);

        // Get the mahasiswa
        restMahasiswaMockMvc.perform(get("/api/mahasiswas/{id}", mahasiswa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mahasiswa.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.nim").value(DEFAULT_NIM.toString()))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.nomorTelepon").value(DEFAULT_NOMOR_TELEPON.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingMahasiswa() throws Exception {
        // Get the mahasiswa
        restMahasiswaMockMvc.perform(get("/api/mahasiswas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMahasiswa() throws Exception {
        // Initialize the database
        mahasiswaRepository.saveAndFlush(mahasiswa);
        int databaseSizeBeforeUpdate = mahasiswaRepository.findAll().size();

        // Update the mahasiswa
        Mahasiswa updatedMahasiswa = mahasiswaRepository.findOne(mahasiswa.getId());
        updatedMahasiswa
            .nama(UPDATED_NAMA)
            .nim(UPDATED_NIM)
            .semester(UPDATED_SEMESTER)
            .email(UPDATED_EMAIL)
            .nomorTelepon(UPDATED_NOMOR_TELEPON)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(updatedMahasiswa);

        restMahasiswaMockMvc.perform(put("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isOk());

        // Validate the Mahasiswa in the database
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeUpdate);
        Mahasiswa testMahasiswa = mahasiswaList.get(mahasiswaList.size() - 1);
        assertThat(testMahasiswa.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMahasiswa.getNim()).isEqualTo(UPDATED_NIM);
        assertThat(testMahasiswa.getSemester()).isEqualTo(UPDATED_SEMESTER);
        assertThat(testMahasiswa.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMahasiswa.getNomorTelepon()).isEqualTo(UPDATED_NOMOR_TELEPON);
        assertThat(testMahasiswa.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testMahasiswa.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMahasiswa() throws Exception {
        int databaseSizeBeforeUpdate = mahasiswaRepository.findAll().size();

        // Create the Mahasiswa
        MahasiswaDTO mahasiswaDTO = mahasiswaMapper.toDto(mahasiswa);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMahasiswaMockMvc.perform(put("/api/mahasiswas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mahasiswaDTO)))
            .andExpect(status().isCreated());

        // Validate the Mahasiswa in the database
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMahasiswa() throws Exception {
        // Initialize the database
        mahasiswaRepository.saveAndFlush(mahasiswa);
        int databaseSizeBeforeDelete = mahasiswaRepository.findAll().size();

        // Get the mahasiswa
        restMahasiswaMockMvc.perform(delete("/api/mahasiswas/{id}", mahasiswa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mahasiswa> mahasiswaList = mahasiswaRepository.findAll();
        assertThat(mahasiswaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mahasiswa.class);
        Mahasiswa mahasiswa1 = new Mahasiswa();
        mahasiswa1.setId(1L);
        Mahasiswa mahasiswa2 = new Mahasiswa();
        mahasiswa2.setId(mahasiswa1.getId());
        assertThat(mahasiswa1).isEqualTo(mahasiswa2);
        mahasiswa2.setId(2L);
        assertThat(mahasiswa1).isNotEqualTo(mahasiswa2);
        mahasiswa1.setId(null);
        assertThat(mahasiswa1).isNotEqualTo(mahasiswa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MahasiswaDTO.class);
        MahasiswaDTO mahasiswaDTO1 = new MahasiswaDTO();
        mahasiswaDTO1.setId(1L);
        MahasiswaDTO mahasiswaDTO2 = new MahasiswaDTO();
        assertThat(mahasiswaDTO1).isNotEqualTo(mahasiswaDTO2);
        mahasiswaDTO2.setId(mahasiswaDTO1.getId());
        assertThat(mahasiswaDTO1).isEqualTo(mahasiswaDTO2);
        mahasiswaDTO2.setId(2L);
        assertThat(mahasiswaDTO1).isNotEqualTo(mahasiswaDTO2);
        mahasiswaDTO1.setId(null);
        assertThat(mahasiswaDTO1).isNotEqualTo(mahasiswaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mahasiswaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mahasiswaMapper.fromId(null)).isNull();
    }
}
