package io.github.semhas.service.impl;

import io.github.semhas.service.KpsService;
import io.github.semhas.service.MahasiswaService;
import io.github.semhas.service.dto.KpsDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Locale;

@Service
@Transactional
public class KpsServiceImpl implements KpsService {

    private final MahasiswaService mahasiswaService;
    private final SpringTemplateEngine templateEngine;

    public KpsServiceImpl(MahasiswaService mahasiswaService, SpringTemplateEngine templateEngine) {
        this.mahasiswaService = mahasiswaService;
        this.templateEngine = templateEngine;
    }

    @Override
    public String getPrintableKpsMahasiswa(Long id) {
        KpsDTO kpsMahasiswa = mahasiswaService.findKpsMahasiswa(id);
        if (kpsMahasiswa != null) {
            Locale locale = Locale.getDefault();
            Context context = new Context(locale);
            context.setVariable("kps", kpsMahasiswa);
            return templateEngine.process("../reports/kps", context);
        }
        return null;
    }
}
