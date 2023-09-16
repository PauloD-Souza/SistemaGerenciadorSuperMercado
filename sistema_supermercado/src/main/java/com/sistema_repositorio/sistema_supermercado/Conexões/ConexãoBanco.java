package com.sistema_repositorio.sistema_supermercado.Conexões;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class ConexãoBanco {
    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
        MongoDatabase database = mongoClient.getDatabase("gerenciadorSupermercado");
        MongoCollection<Document> collection = database.getCollection("cliente");

        Document query = collection.find().first(); // Você pode adicionar um critério de consulta aqui se
                                                    // necessário
        if (query != null) {
            Object campo = query.get("cpf");


        }
    }
    }
}
