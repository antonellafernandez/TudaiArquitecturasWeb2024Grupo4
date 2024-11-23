package com.example.microservicio_parada.repository;

import com.example.microservicio_parada.entity.Parada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ParadaRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    // Habilitar Parada
    public void habilitar(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("habilitado", true);
        mongoTemplate.updateFirst(query, update, Parada.class);
    }

    // Deshabilitar Parada
    public void deshabilitar(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("habilitado", false);
        mongoTemplate.updateFirst(query, update, Parada.class);
    }
}
