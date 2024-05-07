package com.example.AIdemo;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@SpringBootApplication
public class AIdemoApplication {

	private final EmbeddingStoreIngestor embeddingStoreIngester;

    public AIdemoApplication(EmbeddingStoreIngestor embeddingStoreIngester) {
        this.embeddingStoreIngester = embeddingStoreIngester;
    }

    @PostConstruct
    public void init() {
        Document document = loadDocument(toPath("AZ-900.pdf"),new ApachePdfBoxDocumentParser());
        embeddingStoreIngester.ingest(document);
    }

    private static Path toPath(String fileName){
        URL fileUrl = AIdemoApplication.class.getClassLoader().getResource(fileName);
        try {
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
		SpringApplication.run(AIdemoApplication.class, args);
	}

}
