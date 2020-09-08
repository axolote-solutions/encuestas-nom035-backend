package com.axolote.surveynom035.model.dto;

import com.axolote.surveynom035.model.Section;
import com.axolote.surveynom035.model.SurveyResponse;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SurveyApplicationQuestionsDto {

    private SurveyResponse surveyResponse;

    private List<Section> sections;
}
