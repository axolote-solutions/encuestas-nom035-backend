package com.axolote.surveynom035.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.Document;


@NoArgsConstructor
@AllArgsConstructor
@Document(collectionName = "questionResponse")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionResponse {

    private String id;

    private String surveyResponseId;

    private String surveyId;

    private Integer questionNumber;

    private String questionText;

    private String questionResponse;

}
