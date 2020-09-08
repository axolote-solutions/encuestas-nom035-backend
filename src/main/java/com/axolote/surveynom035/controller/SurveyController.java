package com.axolote.surveynom035.controller;

import com.axolote.surveynom035.model.Survey;
import com.axolote.surveynom035.model.dto.SurveyDto;
import com.axolote.surveynom035.service.SurveyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.concurrent.ExecutionException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/nom035/v1/")
public class SurveyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyController.class);

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("surveys/{id}")
    public ResponseEntity<SurveyDto> getSurveyById(@PathVariable @Validated String id) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to getSurveyById");

        Survey survey = surveyService.getSurveyById(id);

        final SurveyDto surveyDto = SurveyDto.builder()
                .active(survey.getActive())
                .Id(survey.getId())
                .description(survey.getDescription())
                .name(survey.getName())
                .creationDate(survey.getCreationDate())
                .build();

        surveyDto.add(linkTo(methodOn(SurveyController.class).getSurveyById(id)).withSelfRel());

        return ResponseEntity.ok(surveyDto);

    }

    @PostMapping("surveys")
    public ResponseEntity<SurveyDto> createSurvey(@RequestBody final Survey survey )
            throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to createSurvey");

        surveyService.saveSurvey(survey);

        final SurveyDto surveyDto = SurveyDto.builder()
                .active(survey.getActive())
                .Id(survey.getId())
                .description(survey.getDescription())
                .name(survey.getName())
                .creationDate(survey.getCreationDate())
                .build();

        final URI uri = linkTo(methodOn(SurveyController.class).getSurveyById(surveyDto.getId())).toUri();

        return ResponseEntity.created(uri).body(surveyDto);
    }
}
