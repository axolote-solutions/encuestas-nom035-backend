package com.axolote.surveynom035.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.Timestamp;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class SurveyDto extends RepresentationModel<SurveyDto> {

    private String Id;

    private Boolean active;

    private String description;

    private String name;

    private Timestamp creationDate;
}
