package org.acme.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@QuarkusTest
public class IncomeControllerTest {

  @BeforeEach
  public void setup() {
    RestAssured.basePath = "/income";
  }

  @Test
  public void testAddIncomeSuccess() {
    String incomeJson = "{\"amount\":5000,\"description\":\"Salary\",\"date\":\"2024-05-20\"}";

    given()
            .contentType(ContentType.JSON)
            .body(incomeJson)
            .when().post()
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("amount", equalTo(5000))
            .body("description", equalTo("Salary"))
            .body("date", equalTo("2024-05-20"));
  }

  @Test
  public void testAddIncomeFailure() {
    String incomeJson = "{\"amount\":-100.00,\"description\":\"\",\"date\":\"2024-03-15\"}";
    given()
            .contentType(ContentType.JSON)
            .body(incomeJson)
            .when().post()
            .then()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
  }

  @Test
  public void testGetIncomeNotFound() {
    given()
            .when().delete("/{id}", 9999)
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
  }

  @Test
  public void testUpdateIncomeSuccess() {
    String updatedIncomeJson = "{\"amount\":2000.00,\"description\":\"Updated Freelance Work\",\"date\":\"2024-03-20\"}";
    given()
            .contentType(ContentType.JSON)
            .body(updatedIncomeJson)
            .when().put()
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
  }

  @Test
  public void testGetAllIncomes() {
    given()
            .when().get()
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("$", hasSize(greaterThan(0)));
  }
}
