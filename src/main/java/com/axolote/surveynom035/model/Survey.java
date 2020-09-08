package com.axolote.surveynom035.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.Document;


@NoArgsConstructor
@AllArgsConstructor
@Document(collectionName = "survey")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Survey {

    @DocumentId
    private String Id;

    private Boolean active;

    private String description;

    private String name;

    private Timestamp creationDate;
}
