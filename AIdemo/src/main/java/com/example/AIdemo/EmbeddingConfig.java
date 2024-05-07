package com.example.AIdemo;

import com.dtsx.astra.sdk.AstraDB;
import com.dtsx.astra.sdk.AstraDBCollection;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.astradb.AstraDbEmbeddingStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingConfig {

    private static final String COLLECTION_NAME = "azure_collection";
    private AstraDBCollection collection = null;

    @Bean
    //@ConditionalOnMissingBean(AllMiniLmL6V2EmbeddingModel.class)
    public EmbeddingModel embeddingModel() {
        //return new AllMiniLmL6V2EmbeddingModel();
        return OpenAiEmbeddingModel.withApiKey("OPENAI_API_KEY");
    }

    @Bean
    public AstraDbEmbeddingStore embeddingStore() {

        String astraDbId = "ASTRA_DB_ID";
        String astraDbToken = "ASTRA_DB_TOKEN";

        String apiEndPoint = "ASTRA_DB_API_ENDPOINT";


        AstraDB astraDB = new AstraDB(astraDbToken, apiEndPoint);

        if (!astraDB.isCollectionExists(COLLECTION_NAME)) {
            //collection = astraDB.createCollection(COLLECTION_NAME, 384);//for AllminiLmL6V2EmbeddingModel
            collection = astraDB.createCollection(COLLECTION_NAME, 1536);//for OpenAiEmbeddingModel
        } else {
            collection = astraDB.collection(COLLECTION_NAME);
        }

        return new AstraDbEmbeddingStore(collection);

    }

    @Bean
    public EmbeddingStoreIngestor embeddingStoreIngester() {
        return EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 0))
                .embeddingModel(embeddingModel())
                .embeddingStore(embeddingStore())
                .build();
    }

    @Bean
    RetrievalAugmentorExample retrievalAugmentorExample() {
        return new RetrievalAugmentorExample(embeddingStore(), embeddingModel());
    }

    @Bean
    public ConversationalRetrievalChain conversationalRetrievalChain() {
            return ConversationalRetrievalChain.builder()
                .chatLanguageModel(OpenAiChatModel.withApiKey("OPENAI_API_KEY"))
                .retrievalAugmentor(retrievalAugmentorExample().get())
                .build();
    }



}
