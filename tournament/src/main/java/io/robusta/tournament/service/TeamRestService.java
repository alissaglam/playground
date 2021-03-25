package io.robusta.tournament.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.robusta.tournament.controller.payload.TeamPayload;
import io.vavr.Tuple4;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class TeamRestService {

    private final static String TEAM_SERVICE_CIRCUIT_BREAKER = "teamService";

    @Value("${teams-service-url}")
    private String teamsServiceUrl;

    @CircuitBreaker(name = TEAM_SERVICE_CIRCUIT_BREAKER, fallbackMethod = "fallback")
    public List<TeamPayload> retrieveTeamsFromTeamsService(Long teamServiceWaitInMilis) {
        String teamsUrl = teamsServiceUrl + "/teams";
        teamsUrl = teamServiceWaitInMilis != null ? teamsUrl + "?waitInMilis=" + teamServiceWaitInMilis : teamsUrl;
        RestTemplate restTemplate = createRestTemplate();
        ResponseEntity<List<TeamPayload>> response = restTemplate.exchange(
                teamsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<TeamPayload>>() {});
        return response.getBody();
    }

    private List<TeamPayload> fallback(Exception e) {
        log.error("Team service call error: ", e);
        return null;
    }

    private RestTemplate createRestTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);
        requestFactory.setConnectionRequestTimeout(3000);
        return new RestTemplate(requestFactory);
    }
}
