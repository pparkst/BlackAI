package dev.pparkst.blackAI.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.RestController;

import dev.pparkst.blackAI.service.RagService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@AllArgsConstructor
@RestController
public class ChatController {

    private final OllamaChatModel chatModel;
    private final RagService ragService;


    @GetMapping("/ai/rag/ingestPdf")
    public String ingestForPdf() {
        ragService.ingestPdf();
        return "PDF 데이터 임베딩 및 저장 완료!";
    }

    @GetMapping("/ai/rag/ingestTxt")
    public String ingestForTxt() {
        ragService.ingestTxt();
        return "TXT 데이터 임베딩 및 저장 완료!";
    }
    

    @GetMapping("/ai/rag/search")
    public List<String> getMethodName(@RequestParam String query) {
        return ragService.search(query).stream()
                .map(doc -> doc.getText())
                .toList();
    }
    


    @GetMapping("/ai/rag/check-chunks-txt")
    public List<String> checkChunksOfTxt() {
        return ragService.loadAndSplitDocuments().stream()
                .map(doc -> doc.getText())
                .toList();
    }

    @GetMapping("/ai/rag/check-chunks-pdf")
    public List<String> checkChunksOfPdf() {
        return ragService.loadPdfAndSplitDocuments().stream()
                .map(doc -> doc.getText())
                .toList();
    }


    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message") String message) {
        String response = chatModel.call(message);
        return Map.of("generation", response);
    }

    @GetMapping("/ai/stream")
    public Flux<String> stream(@RequestParam(value = "message") String message) {
        return chatModel.stream(message);
    }
    
    
}
