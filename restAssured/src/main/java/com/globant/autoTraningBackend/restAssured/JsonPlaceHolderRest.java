/**
 * 
 */
package com.globant.autoTraningBackend.restAssured;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;



/**
 * @author jose.negrete
 *
 */
public class JsonPlaceHolderRest {
	public static String BASE_URL = "https://jsonplaceholder.typicode.com";

	public int getCodeResponse(String url) {
		return get(BASE_URL + url).getStatusCode();
	}

	public String getResponse(String url) {
		return get(BASE_URL + url).getBody().asString();
	}

	public void validateSchema(String url, String nameSchema) {
		// When
		get(BASE_URL + url).then().assertThat().body(matchesJsonSchema(Paths.get(nameSchema).toUri()));
	}

	/**
	 * Método que obtiene un valor a partir de un parametro de búsqueda
	 * @param url
	 * @param parameterName
	 * @param parameterValue
	 * @return
	 */
	public JsonPath getItem(String url, String parameterName, String parameterValue) {
		return	given().pathParam(parameterName, parameterValue).when().get(BASE_URL + url).jsonPath();
	}

	/**
	 * Método que obtiene un recurso con un parametro definido
	 * @param resource
	 * @param paramName
	 * @param paramValue
	 */
	 public JsonPath callImplementMethods(String resource, String paramName, String paramValue) {
	        Response response = given()
	                .baseUri(BASE_URL)
	                .queryParam(paramName, paramValue)
	                .get(resource);
	        
	        return response.jsonPath();
	    }
}
