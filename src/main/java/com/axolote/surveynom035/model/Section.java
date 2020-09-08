package com.axolote.surveynom035.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.List;

@Document(collectionName = "section")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section {

    @DocumentId
    private String Id;

    private Integer sectionNumber;

    private List<Question> questions;

}
