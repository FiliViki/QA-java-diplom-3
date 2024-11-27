package Api;

import Data.Credentials;
import Data.User;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static Config.Environment.HOST;
import static io.restassured.RestAssured.given;

public class LoginApi {
    private static final String ENDPOINT_CREATE = "/api/auth/register";
    private static final String ENDPOINT_LOGIN = "/api/auth/login";
    private static final String ENDPOINT_USER = "/api/auth/user";

    public ValidatableResponse createUser(User user) {
        return given()
            .contentType(ContentType.JSON)
            .body(user)
            .when()
            .post(HOST + ENDPOINT_CREATE)
            .then();
    }

    public ValidatableResponse deleteUser(String token) {
        return given()
            .contentType(ContentType.JSON)
            .auth()
            .oauth2(token)
            .when()
            .delete(HOST + ENDPOINT_USER)
            .then();
    }

    public ValidatableResponse loginUser(Credentials userLogin) {
        return given()
            .contentType(ContentType.JSON)
            .body(userLogin)
            .when()
            .post(HOST + ENDPOINT_LOGIN)
            .then();
    }
}