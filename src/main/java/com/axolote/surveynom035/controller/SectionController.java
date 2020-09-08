package com.axolote.surveynom035.controller;

import com.axolote.surveynom035.model.Section;
import com.axolote.surveynom035.model.SurveyResponse;
import com.axolote.surveynom035.model.dto.SectionDto;
import com.axolote.surveynom035.model.dto.SurveyApplicationDto;
import com.axolote.surveynom035.model.dto.SurveyApplicationQuestionsDto;
import com.axolote.surveynom035.service.SectionService;
import com.axolote.surveynom035.service.SurveyResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/nom035/v1/")
public class SectionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SectionController.class);

    private final SectionService sectionService;

    private final SurveyResponseService surveyResponseService;

    public SectionController(SectionService sectionService, SurveyResponseService surveyResponseService) {
        this.sectionService = sectionService;
        this.surveyResponseService = surveyResponseService;
    }

    @GetMapping("surveys/{surveyId}/section/{sectionId}")
    public ResponseEntity<SectionDto> getSectionById(
            @PathVariable @Validated String surveyId, @PathVariable @Validated String sectionId)
            throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to getSectionById");

        final Section section = sectionService.getSectionByIdAndSurvey(surveyId, sectionId);

        final SectionDto sectionDto = SectionDto.builder()
                .id(section.getId())
                .sectionNumber(section.getSectionNumber())
                .questions(section.getQuestions())
                .build();

        sectionDto.add(linkTo(methodOn(SectionController.class).getSectionById(surveyId, sectionId)).withSelfRel());

        return ResponseEntity.ok(sectionDto);
    }

    @PostMapping("surveys/{surveyId}/section")
    public ResponseEntity<SectionDto> createSection(
            @RequestBody final Section section, @PathVariable @Validated String surveyId)
            throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to createSection");

        sectionService.saveSection(surveyId, section);

        final SectionDto sectionDto = SectionDto.builder()
                .id(section.getId())
                .sectionNumber(section.getSectionNumber())
                .questions(section.getQuestions())
                .build();

        final URI uri = linkTo(methodOn(SectionController.class).getSectionById(surveyId, section.getId())).toUri();

        return ResponseEntity.created(uri).body(sectionDto);
    }

    @GetMapping("surveys/{surveyId}/section/initial")
    public ResponseEntity<SurveyApplicationDto> initialSection(@PathVariable String surveyId)
            throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to initialSection");

        final SurveyResponse surveyResponse = surveyResponseService.save(surveyId);

        final List<Section> sections = sectionService.getAllSections(surveyId);

        final Optional<Section> firstSection = sections.stream()
                .filter(section -> section.getSectionNumber() == 1)
                .findFirst();

        if (firstSection.isPresent()) {
            final Section section = firstSection.get();

            SurveyApplicationDto surveyApplicationDto = new SurveyApplicationDto();

            surveyApplicationDto.setSurveyResponse(surveyResponse);
            surveyApplicationDto.setSection(section);
            surveyApplicationDto.setSectionCount(sections.size());

            return ResponseEntity.ok(surveyApplicationDto);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("surveys/{surveyId}/section")
    public ResponseEntity<SurveyApplicationQuestionsDto> getSections(@PathVariable String surveyId)
            throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to initialSection");

        final SurveyResponse surveyResponse = surveyResponseService.save(surveyId);

        final List<Section> sections = sectionService.getAllSections(surveyId);

        if (sections.size() > 0) {
            SurveyApplicationQuestionsDto fullSurvey = new SurveyApplicationQuestionsDto(surveyResponse, sections);

            return ResponseEntity.ok(fullSurvey);
        }

        return ResponseEntity.notFound().build();
    }
}
