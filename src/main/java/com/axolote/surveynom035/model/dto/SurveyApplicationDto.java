package com.axolote.surveynom035.model.dto;

import com.axolote.surveynom035.model.Section;
import com.axolote.surveynom035.model.SurveyResponse;
import lombok.Data;


@Data
public class SurveyApplicationDto {

    private SurveyResponse surveyResponse;

    private Section section;

    private Integer sectionCount;

}
