package com.axolote.surveynom035.repository;

import com.axolote.surveynom035.model.Section;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class SectionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyRepository.class);

    private final Firestore firestore;

    public SectionRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public Section getSectionByIdAndSurvey(String surveyId, String sectionId) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to getSurveyById");
        Validate.notBlank(surveyId);
        Validate.notBlank(sectionId);

        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = firestore
                .collection("survey")
                .document(surveyId)
                .collection("section")
                .document(sectionId)
                .get();

        return documentSnapshotApiFuture.get().toObject(Section.class);
    }

    public Section save(String surveyId, Section section) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to save");
        Validate.notNull(section, "section can't be null");
        Validate.notBlank(surveyId, "surveyId can't be null");

        final String sectionId = firestore.collection("section").document().getId();

        section.setId(sectionId);

        final WriteResult writeResult = firestore.collection("survey").document(surveyId)
                .collection("section").document(sectionId).set(section).get();

        LOGGER.trace(writeResult.getUpdateTime().toString());

        return section;
    }

    public List<Section> getAllSections(final String surveyId) throws ExecutionException, InterruptedException {
        LOGGER.trace("Request to getAllSections");
        Validate.notBlank(surveyId, "survey Id can't be null");

        final Iterable<CollectionReference> sectionsBySurvey = firestore.collection("survey")
                .document(surveyId)
                .listCollections();

        List<Section> sections = new ArrayList<>();

        for (CollectionReference reference: sectionsBySurvey) {
            final Section section = reference.document().get().get().toObject(Section.class);

            sections.add(section);
        }

        return sections;
    }
}
