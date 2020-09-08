package com.axolote.surveynom035.service;

import com.axolote.surveynom035.model.SurveyResponse;
import com.axolote.surveynom035.model.dto.SurveyResponseDto;
import com.axolote.surveynom035.repository.QuestionResponseRepository;
import com.axolote.surveynom035.repository.SurveyResponseRepository;
import com.google.cloud.Timestamp;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class SurveyResponseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyResponseService.class);

    private final SurveyResponseRepository surveyResponseRepository;

    private final QuestionResponseRepository questionResponseRepository;

    public SurveyResponseService(SurveyResponseRepository surveyResponseRepository, QuestionResponseRepository questionResponseRepository) {
        this.surveyResponseRepository = surveyResponseRepository;
        this.questionResponseRepository = questionResponseRepository;
    }


    public void respondSurvey(final SurveyResponseDto surveyResponseDto) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to respondSurvey");

        final SurveyResponse surveyResponse = surveyResponseDto.getSurveyResponse();

        final SurveyResponse response = surveyResponseRepository.saveSurveyResponse(surveyResponse);

        questionResponseRepository.save(surveyResponseDto.getQuestionResponses(), response);

    }

    public SurveyResponse save(final SurveyResponse surveyResponse) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to save");
        Validate.notNull(surveyResponse, "survey response can't be null");

        return surveyResponseRepository.saveSurveyResponse(surveyResponse);
    }

    public SurveyResponse save(final String surveyId) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to save");
        Validate.notBlank(surveyId, "surveyId can't be null");

        SurveyResponse surveyResponse = new SurveyResponse();

        surveyResponse.setSurveyId(surveyId);
        surveyResponse.setDateTime(Timestamp.now());

        return surveyResponseRepository.saveSurveyResponse(surveyResponse);

    }
}
