package Coding_GO.codingGO.global.infra.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {
    private final String apiKey = "AIzaSyB6x3FwsambsyvwewKNSUxUgqL5M9DxBP0";
    private final String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private final RestTemplate restTemplate;

    public String askGemini(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt))))
        );


        try {
            Map<String, Object> response = restTemplate.postForObject(url + "?key=" + apiKey, requestBody, Map.class);
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            return "{\"analysisReport\":\"AI 호출 실패\", \"quiz\":null}";
        }
    }
}
