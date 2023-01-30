package org.jupiterhub.pipu.chatdir.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DirectoryResourceTest {

    @Test
    public void testRedisOperations() {
        // verify that we have nothing
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/directory")
                .then()
                .statusCode(200)
                .body("size()", is(0));

        // create a new directory
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"userId\":\"jdoe\",\"connString\":\"1.2.3.4\"}")
                .when()
                .post("/directory")
                .then()
                .statusCode(200)
                .body("userId", is("jdoe"))
                .body("connString", is("1.2.3.4"));

        // lookup directory
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/directory/jdoe")
                .then()
                .statusCode(200)
                .body("userId", is("jdoe"))
                .body("connString", is("1.2.3.4"));

        // update ip of jdoe
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"userId\":\"jdoe\",\"connString\":\"10.20.30.40\"}")
                .when()
                .put("/directory/jdoe")
                .then()
                .statusCode(200)
                .body("userId", is("jdoe"))
                .body("connString", is("10.20.30.40"));


        // test not found for unexisting id
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/directory/does-not-exist")
                .then()
                .statusCode(404);

        // create another user
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"userId\":\"mk2\",\"connString\":\"1.2.3.5\"}")
                .when()
                .post("/directory")
                .then()
                .statusCode(200)
                .body("userId", is("mk2"))
                .body("connString", is("1.2.3.5"));

        // ensure that we have the data
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/directory/mk2")
                .then()
                .statusCode(200)
                .body("userId", is("mk2"))
                .body("connString", is("1.2.3.5"));

        // Delete first user
        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/directory/jdoe")
                .then()
                .statusCode(204);

        // verify that we have one key left after deletion
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/directory")
                .then()
                .statusCode(200)
                .body("size()", is(1));

        // delete second key
        given()
                .accept(ContentType.JSON)
                .when()
                .delete("/directory/mk2")
                .then()
                .statusCode(204);

        // verify that there is no key left
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/directory")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }
}