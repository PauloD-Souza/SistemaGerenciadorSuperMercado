package com.sistema_repositorio.sistema_supermercado.sequenceMongodb;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


@Service
public class sequenceGeneratorService {

    private MongoOperations mongoOperations;

    @Autowired
    public sequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
        databaseSequence counter = mongoOperations.findAndModify(
            query(where("_id").is(seqName)), 
            new Update().set("_id", seqName).inc("seq", 1), 
            options().returnNew(true).upsert(true), 
            databaseSequence.class
        );
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
    
}