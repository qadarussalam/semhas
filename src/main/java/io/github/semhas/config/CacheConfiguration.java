package io.github.semhas.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.semhas.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Jurusan.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Jurusan.class.getName() + ".listMahasiswas", jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Mahasiswa.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Mahasiswa.class.getName() + ".listPesertaSeminars", jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Dosen.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.PesertaSeminar.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.PesertaSeminar.class.getName() + ".listSeminars", jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Seminar.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Seminar.class.getName() + ".listPesertaSeminars", jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.JadwalSeminar.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Ruang.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Ruang.class.getName() + ".listJadwalSeminars", jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Sesi.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.semhas.domain.Sesi.class.getName() + ".listJadwalSeminars", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
