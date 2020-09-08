package com.axolote.surveynom035.model.dto;

import com.axolote.surveynom035.model.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class SectionDto extends RepresentationModel<SectionDto> {

    private String id;

    private Integer sectionNumber;

    private List<Question> questions;
}
