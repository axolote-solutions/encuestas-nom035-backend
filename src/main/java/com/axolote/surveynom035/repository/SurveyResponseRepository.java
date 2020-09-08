package com.axolote.surveynom035.repository;

import com.axolote.surveynom035.model.SurveyResponse;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class SurveyResponseRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyResponseRepository.class);

    private final Firestore firestore;

    public SurveyResponseRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public SurveyResponse saveSurveyResponse(final SurveyResponse surveyResponse) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to saveSurveyResponse");
        Validate.notNull(surveyResponse, "surveyResponse can't be null");

        final String surveyResponseId = firestore.collection("surveyResponse").document().getId();

        surveyResponse.setId(surveyResponseId);
        surveyResponse.setDateTime(Timestamp.now());

        firestore.collection("surveyResponse").document(surveyResponseId).set(surveyResponse).get();

        return surveyResponse;
    }
}
