package com.daim.assignment.v1.controllers;

import com.daim.assignment.repositories.DiffRepository;
import com.daim.assignment.repositories.models.DiffEntity;
import com.daim.assignment.v1.controllers.models.DiffRequest;
import com.daim.assignment.v1.controllers.models.DiffResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiffControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private DiffRepository diffRepository;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/v1/diff");
        this.diffRepository.deleteAll();
    }

    @Test
    public void putDiffLeft() {

        DiffRequest diffRequest = new DiffRequest("data");
        String putLeftUrl = String.format("%s/%s/%s", this.base.toString(), "555", "left");

        HttpEntity<DiffRequest> request = new HttpEntity<>(diffRequest);

        ResponseEntity response = template.exchange(putLeftUrl, HttpMethod.PUT, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void putDiffRight() {

        DiffRequest diffRequest = new DiffRequest("data");
        String putLeftUrl = String.format("%s/%s/%s", this.base.toString(), "555", "right");

        HttpEntity<DiffRequest> request = new HttpEntity<>(diffRequest);

        ResponseEntity response = template.exchange(putLeftUrl, HttpMethod.PUT, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void returnNotFoundWhenGettingNot() {

        DiffEntity diffEntity = new DiffEntity(555L, "QQ==", "QQ==");

        this.diffRepository.save(diffEntity);

        String getDiffUrl = String.format("%s/%s", this.base.toString(), "555");

        ResponseEntity<DiffResponse> response = template.getForEntity(getDiffUrl, DiffResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getDiff()).isEqualTo("Left and Right data are equal");
    }
}
