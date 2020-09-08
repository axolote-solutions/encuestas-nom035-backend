package com.axolote.surveynom035.service;

import com.axolote.surveynom035.model.Survey;
import com.axolote.surveynom035.repository.SurveyRepository;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class SurveyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyService.class);

    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public Survey getSurveyById(String id) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to getSurveyById");
        Validate.notBlank(id);

        return surveyRepository.getSurveyById(id);
    }

    public Survey saveSurvey(Survey survey) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to saveSurvey");
        Validate.notNull(survey, "survey can't be null");

        return surveyRepository.saveSurvey(survey);
    }
}
