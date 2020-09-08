package com.axolote.surveynom035.repository;

import com.axolote.surveynom035.model.Survey;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class SurveyRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyRepository.class);

    private final Firestore firestore;

    public SurveyRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public Survey getSurveyById(String id) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to getSurveyById");
        Validate.notBlank(id);

        String surveyId = "survey/" + id;
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = firestore.document(surveyId).get();

        return documentSnapshotApiFuture.get().toObject(Survey.class);

    }

    public Survey saveSurvey(Survey survey) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to saveSurvey");
        Validate.notNull(survey, "Survey can't be null");

        survey.setCreationDate(Timestamp.now());

        final String surveyId = firestore.collection("survey").document().getId();

        survey.setId(surveyId);

        firestore.collection("survey").document(surveyId).set(survey).get();

        return survey;
    }
}
