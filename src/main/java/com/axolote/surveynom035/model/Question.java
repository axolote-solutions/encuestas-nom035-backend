package com.axolote.surveynom035.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {

    private String questionText;

    private List<String> responseOptions;

    private Integer questionNumber;
}
