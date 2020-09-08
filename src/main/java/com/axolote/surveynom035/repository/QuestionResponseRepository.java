package com.axolote.surveynom035.repository;

import com.axolote.surveynom035.model.QuestionResponse;
import com.axolote.surveynom035.model.SurveyResponse;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteBatch;
import com.google.cloud.firestore.WriteResult;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class QuestionResponseRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionResponseRepository.class);

    private final Firestore firestore;


    public QuestionResponseRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public QuestionResponse save(final QuestionResponse questionResponse) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to saveQuestionResponse");
        Validate.notNull(questionResponse, "question response can't be null");

        final String questionResponseId = firestore.collection("questionResponse").document().getId();

        questionResponse.setId(questionResponseId);

        final WriteResult writeResult = firestore.collection("questionResponse").document(questionResponseId).set(questionResponse).get();

        LOGGER.trace(writeResult.getUpdateTime().toString());

        return questionResponse;
    }

    public void save(List<QuestionResponse> questionResponses, SurveyResponse surveyResponse) {
        LOGGER.trace("Request to saveQuestionResponse");
        Validate.notNull(surveyResponse, "surveyResponse can't be null");
        Validate.notEmpty(questionResponses, "Question responses can't be empty");

        final WriteBatch batch = firestore.batch();

        questionResponses.forEach(questionResponse -> {
            questionResponse.setSurveyId(surveyResponse.getSurveyId());
            questionResponse.setSurveyResponseId(surveyResponse.getId());

            String questionResponseId = firestore.collection("questionResponse").document().getId();
            final DocumentReference questionReference = firestore.collection("questionResponse").document(questionResponseId);

            batch.set(questionReference, questionResponse);

        });

        batch.commit();

    }

}
