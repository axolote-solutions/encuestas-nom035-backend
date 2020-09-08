package com.axolote.surveynom035.model.dto;

import com.axolote.surveynom035.model.QuestionResponse;
import com.axolote.surveynom035.model.SurveyResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class SurveyResponseDto {

    private final SurveyResponse surveyResponse;

    private final List<QuestionResponse> questionResponses;
}
