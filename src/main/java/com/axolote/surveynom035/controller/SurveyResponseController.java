package com.axolote.surveynom035.controller;

import com.axolote.surveynom035.model.dto.SurveyResponseDto;
import com.axolote.surveynom035.service.SurveyResponseService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/nom035/v1/")
public class SurveyResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyResponseController.class);

    private final SurveyResponseService surveyResponseService;

    public SurveyResponseController(SurveyResponseService surveyResponseService) {
        this.surveyResponseService = surveyResponseService;
    }

    @PostMapping("survey/response")
    public ResponseEntity<?> respondSurvey(@RequestBody final SurveyResponseDto surveyResponseDto) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to responseSurvey");
        Validate.notNull(surveyResponseDto, "surveyResponseDto can't be null");
        Validate.notNull(surveyResponseDto.getSurveyResponse(), "surveyResponse can't be null");
        Validate.notEmpty(surveyResponseDto.getQuestionResponses(), "questionResponse can't be empty");

        surveyResponseService.respondSurvey(surveyResponseDto);

        return null;
    }
}
