package com.techbow.notification.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class NotificationSequenceService {
    @Autowired
    private MongoOperations mongoOperations;

    public Long getNextSequence(String sequenceName) {
        NotificationSequence sequence = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(sequenceName)),
                new Update().inc("seq", 1),
                FindAndModifyOptions.options().returnNew(true).returnNew(true).upsert(true),
                NotificationSequence.class
        );
        return !Objects.isNull(sequence) ? sequence.getSeq() : 1;
    }
}
