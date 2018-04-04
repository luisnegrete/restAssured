package com.globant.autoTraningBackend.restAssured;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class JsonPlaceHolderRestTest {
	public static String BASE_URL = "https://jsonplaceholder.typicode.com";
	public static String RESOURCES_PATH = "src/test/resources/";

	@DataProvider(name = "providerTestGetCodeResponse")
	public Object[][] createData1() {
		return new Object[][] { 
				{ "/posts"},
				{ "/comments"},
				{ "/albums"},
				{ "/photos"},
				{ "/todos"},
				{ "/users"},
				};
	}

	@Test(dataProvider="providerTestGetCodeResponse")
	public void testGetCodeResponse(String resource) {
		JsonPlaceHolderRest holderRest = new JsonPlaceHolderRest();

		Assert.assertEquals(holderRest.getCodeResponse(BASE_URL + resource), 200);
	}
	
	@Test
	public void testGetResponse() {
		JsonPlaceHolderRest holderRest = new JsonPlaceHolderRest();

		System.out.println(holderRest.getResponse(BASE_URL+"/users"));
	}
	
	@DataProvider(name = "providerTestValidateSchema")
	public Object[][] createData2() {
		return new Object[][] { 
				{ "/posts", "posts_shcema.json"},
				{ "/comments","comments_schema.json"},
				{ "/albums","albums_schema.json"},
				{ "/photos","posts_shcema.json"},
				{ "/todos","todos_schema.json"},
				{ "/users","todos_schema.json"},
				};
	}
	
	@Test(dataProvider="providerTestValidateSchema")
	public void testValidateSchema(String resource, String nameSchema) {
		JsonPlaceHolderRest holderRest = new JsonPlaceHolderRest();

		holderRest.validateSchema(BASE_URL+ resource,RESOURCES_PATH + nameSchema);
	}
	
	@DataProvider(name = "providerGetItem")
	public Object[][] createData3() {
		return new Object[][] { 
				{ "/posts/{id}", "id", "20", "title", "doloribus ad provident suscipit at"},
				{ "/posts/{id}", "id", "50", "body", "error suscipit maxime adipisci consequuntur recusandae\nvoluptas eligendi et est et voluptates\nquia distinctio ab amet quaerat molestiae et vitae\nadipisci impedit sequi nesciunt quis consectetur"},
				{ "/posts/{id}", "id", "100", "userId", "10"},
				};
	}
	
	@Test(dataProvider="providerGetItem")
	public void testGetItem(String resource, String parameterName, String parameterValue, String fieldName, String expectedValue) {
		JsonPlaceHolderRest holderRest = new JsonPlaceHolderRest();

		Assert.assertEquals(holderRest.getItem(BASE_URL + resource, parameterName, parameterValue, fieldName), expectedValue);
	}
}
