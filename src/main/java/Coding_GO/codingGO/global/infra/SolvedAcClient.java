package Coding_GO.codingGO.global.infra;

import Coding_GO.codingGO.global.infra.data.SolvedSearchResponse;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SolvedAcClient {
    private final WebClient webClient;

    public SolvedAcClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://solved.ac/api/v3")
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0").build();
    }

    public SolvedSearchResponse search(String query, int page) {
        return webClient.get()
                .uri(uri -> uri.path("/search/problem")
                        .queryParam("query", query)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(SolvedSearchResponse.class)
                .block();
    }
}

