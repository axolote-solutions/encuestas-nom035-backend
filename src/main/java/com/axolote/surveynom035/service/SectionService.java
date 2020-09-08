package com.axolote.surveynom035.service;

import com.axolote.surveynom035.model.Section;
import com.axolote.surveynom035.repository.SectionRepository;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class SectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SectionService.class);

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public Section getSectionByIdAndSurvey(String surveyId, String sectionId) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to getSectionById");
        Validate.notBlank(surveyId);
        Validate.notBlank(sectionId);

        return sectionRepository.getSectionByIdAndSurvey(surveyId, sectionId);
    }

    public Section saveSection(final String surveyId, final Section section)
            throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to saveSection");
        Validate.notBlank(surveyId, "surveyId can't be null");
        Validate.notNull(section, "section can't be null");

        return sectionRepository.save(surveyId, section);
    }

    public List<Section> getAllSections(final String surveyId) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to getAllSections");
        Validate.notBlank(surveyId, "surveyId can't be null");

        return sectionRepository.getAllSections(surveyId);
    }
}
