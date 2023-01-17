package com.accenture.codingtest.springbootcodingtest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootCodingTestApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static UUID user_id;
    private static UUID user_id_2;
    private static UUID project_id;
    private static UUID task_id;
    private static UUID task_id_2;

    @Test
    @Order(1)
    @DisplayName("Create 2 users")
    void createUser() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //User 1
        JSONObject requestJson1 = new JSONObject();
        requestJson1.put("username", "user1");
        requestJson1.put("password", "user123");
        HttpEntity<String> request1 = new HttpEntity<String>(requestJson1.toString(), headers);
        ResponseEntity<String> response1 = testRestTemplate
                .withBasicAuth("admin", "admin123")
                .postForEntity("/api/v1/users", request1, String.class);
        String jsonBody1 = response1.getBody();
        JsonNode responseJson1 = new ObjectMapper().readTree(jsonBody1);

        // User 2
        JSONObject requestJson2 = new JSONObject();
        requestJson2.put("username", "user2");
        requestJson2.put("password", "user456");
        HttpEntity<String> request2 = new HttpEntity<String>(requestJson2.toString(), headers);
        ResponseEntity<String> response2 = testRestTemplate
                .withBasicAuth("admin", "admin123")
                .postForEntity("/api/v1/users", request2, String.class);
        String jsonBody2 = response2.getBody();
        JsonNode responseJson2 = new ObjectMapper().readTree(jsonBody2);

        user_id = UUID.fromString(responseJson1.get("id").toString().replace("\"", ""));
        user_id_2 = UUID.fromString(responseJson2.get("id").toString().replace("\"", ""));

        //Verify request succeed
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("Create a project")
    void createProject() throws Exception {
        JSONObject requestJson = new JSONObject();
        requestJson.put("name", "Spring Boot Coding Test");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<String>(requestJson.toString(), headers);
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("p_owner1", "p_owner123")
                .postForEntity("/api/v1/projects", request, String.class);
        String jsonBody = response.getBody();
        JsonNode responseJson = new ObjectMapper().readTree(jsonBody);

        project_id = UUID.fromString(responseJson.get("id").toString().replace("\"", ""));

        //Verify request succeed
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("Create 2 tasks and assign to 2 users")
    void createTask() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // Task 1
        JSONObject requestJson1 = new JSONObject();
        requestJson1.put("title", "Create user APIs");
        requestJson1.put("description", "Testing Spring Boot Coding");
        requestJson1.put("status", "fgfg");
        requestJson1.put("project_id", project_id);
        requestJson1.put("user_id", user_id);
        HttpEntity<String> request1 = new HttpEntity<String>(requestJson1.toString(), headers);
        ResponseEntity<String> response1 = testRestTemplate
                .withBasicAuth("p_owner1", "p_owner123")
                .postForEntity("/api/v1/tasks", request1, String.class);
        String jsonBody1 = response1.getBody();
        JsonNode responseJson1 = new ObjectMapper().readTree(jsonBody1);

        // Task 2
        JSONObject requestJson2 = new JSONObject();
        requestJson2.put("title", "Create user APIs");
        requestJson2.put("description", "Testing Spring Boot Coding");
        requestJson2.put("status", "fgfg");
        requestJson2.put("project_id", project_id);
        requestJson2.put("user_id", user_id_2);
        HttpEntity<String> request2 = new HttpEntity<String>(requestJson2.toString(), headers);
        ResponseEntity<String> response2 = testRestTemplate
                .withBasicAuth("p_owner1", "p_owner123")
                .postForEntity("/api/v1/tasks", request2, String.class);
        String jsonBody2 = response2.getBody();
        JsonNode responseJson2 = new ObjectMapper().readTree(jsonBody2);

        task_id = UUID.fromString(responseJson1.get("id").toString().replace("\"", ""));
        task_id_2 = UUID.fromString(responseJson2.get("id").toString().replace("\"", ""));

        System.out.println(responseJson1);
        System.out.println(responseJson2);
        System.out.println(responseJson2.get("id").toString().replace("\"", ""));

        //Verify request succeed
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());

    }

    @Test
    @Order(4)
    @DisplayName("Change status")
    void changeStatus() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // User 1 change status
        JSONObject requestJson1 = new JSONObject();
        requestJson1.put("title", "Test 1");
        requestJson1.put("description", "Testing Spring Boot Coding");
        requestJson1.put("status", "IN_PROGRESS");
        requestJson1.put("project_id", project_id);
        requestJson1.put("user_id", user_id);
        HttpEntity<String> request1 = new HttpEntity<String>(requestJson1.toString(), headers);
        ResponseEntity<String> response1 = testRestTemplate
                .withBasicAuth("user1", "user123")
                .exchange("/api/v1/tasks/" + task_id, HttpMethod.PUT, request1, String.class);
        String jsonBody1 = response1.getBody();
        JsonNode responseJson1 = new ObjectMapper().readTree(jsonBody1);

        // User 2 change status
        JSONObject requestJson2 = new JSONObject();
        requestJson2.put("title", "Test 1");
        requestJson2.put("description", "Testing Spring Boot Coding");
        requestJson2.put("status", "READY_FOR_TEST");
        requestJson2.put("project_id", project_id);
        requestJson2.put("user_id", user_id_2);
        HttpEntity<String> request2 = new HttpEntity<String>(requestJson2.toString(), headers);
        ResponseEntity<String> response2 = testRestTemplate
                .withBasicAuth("user2", "user456")
                .exchange("/api/v1/tasks/" + task_id_2, HttpMethod.PUT, request2, String.class);
        String jsonBody2 = response2.getBody();
        JsonNode responseJson2 = new ObjectMapper().readTree(jsonBody2);

        System.out.println(responseJson1);
        System.out.println(responseJson2);

        //Verify request succeed
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
    }
}