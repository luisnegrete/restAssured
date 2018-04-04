/**
 * 
 */
package com.globant.autoTraningBackend.restAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;



/**
 * @author jose.negrete
 *
 */
public class JsonPlaceHolderRest {

	public int getCodeResponse(String url) {
		return get(url).getStatusCode();
	}

	public String getResponse(String url) {
		return get(url).getBody().asString();
	}

	public void validateSchema(String url, String nameSchema) {
		// When
		get(url).then().assertThat().body(matchesJsonSchema(Paths.get(nameSchema).toUri()));
	}

	/**
	 * Método que obtiene un valor a partir de un parametro de búsqueda
	 * @param url
	 * @param parameterName
	 * @param parameterValue
	 * @return
	 */
	public String getItem(String url, String parameterName, String parameterValue, String fieldName) {
		JsonPath jsPath = //get(url).jsonPath();
				given().pathParam(parameterName, parameterValue).when().get(url).jsonPath();
		
		return jsPath.getString(fieldName);
		
	}

}
