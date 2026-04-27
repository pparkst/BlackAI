package dev.pparkst.blackAI.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
public class RagService {

    private final VectorStore vectorStore;
    private final EmbeddingModel embeddingModel;
    private final String vectorStorePath = "vectorstore.json";
    private final ChatClient chatClient;

    public RagService(EmbeddingModel embeddingModel, VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        this.embeddingModel = embeddingModel;
        this.vectorStore = vectorStore;

        SearchRequest searchRequest = SearchRequest.builder()
                .topK(4)
                .similarityThreshold(0.7)
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();
        //this.vectorStore = SimpleVectorStore.builder(embeddingModel).build(); vectorStore -> pgVector로 변경
    }
    
    @Value("file:D:/Java/AI Training Data/khpp_cs.txt")
    private Resource faqResource;

    @Value("file:D:/Java/AI Training Data/붙임1.6-명지대학교 공사현장 작업 안전 수칙.pdf")
    private Resource pdfResource;

    public List<Document> loadAndSplitDocuments() {
        TextReader textReader = new TextReader(faqResource);
        textReader.setCharset(StandardCharsets.UTF_8);
        List<Document> documents = textReader.get();

        TokenTextSplitter splitter = new TokenTextSplitter();

        return splitter.apply(documents);
    }

    public List<Document> loadPdfAndSplitDocuments() {
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource,
                PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0)
                        .withPageBottomMargin(0)
                        .build());
        List<Document> documents = pdfReader.get();

        // 텍스트 전처리
        List<Document> cleanedDocuemnts = documents.stream()
                .map(doc -> {
                    String content = doc.getText()
                            .replaceAll("\\s+", "")
                            .replaceAll("\\n+", "")
                            .trim();
                    return new Document(content, doc.getMetadata());
                })
                .toList();

        TokenTextSplitter splitter = new TokenTextSplitter();
        return splitter.apply(cleanedDocuemnts);
    }

    public void ingestPdf() {
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource,
                    PdfDocumentReaderConfig.builder().build());
        
        List<Document> documents = pdfReader.get();

        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> chunks = splitter.apply(documents);

        vectorStore.add(chunks);

        ((SimpleVectorStore) vectorStore).save(new File(vectorStorePath));
    }

    public void ingestTxt() {
        TextReader textReader = new TextReader(faqResource);
        textReader.setCharset(StandardCharsets.UTF_8);
        List<Document> documents = textReader.get();

        TokenTextSplitter splitter = new TokenTextSplitter();

        List<Document> chunks = splitter.apply(documents);

        vectorStore.add(chunks);

        ((SimpleVectorStore) vectorStore).save(new File(vectorStorePath));
    }

    public List<Document> search(String query) {
        return vectorStore.similaritySearch(query);
    }
}
