package com.globant.autoTraningBackend.restAssured;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class JsonPlaceHolderRestTest {
	
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

		Assert.assertEquals(holderRest.getCodeResponse(resource), HttpStatus.SC_OK);
	}
	
	@Test
	public void testGetResponse() {
		JsonPlaceHolderRest holderRest = new JsonPlaceHolderRest();

		System.out.println(holderRest.getResponse("/users"));
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

		holderRest.validateSchema(resource,RESOURCES_PATH + nameSchema);
	}
	
  	@DataProvider(name = "providerGetItem")
	public Object[][] createData3() {
		return new Object[][] { 
				{ "/posts/{id}", "id", "20", 2,  20,  "doloribus ad provident suscipit at", "qui consequuntur ducimus possimus quisquam amet similique\nsuscipit porro ipsam amet\neos veritatis officiis exercitationem vel fugit aut necessitatibus totam\nomnis rerum consequatur expedita quidem cumque explicabo"},
				{ "/posts/{id}", "id", "50", 5,  50,  "repellendus qui recusandae incidunt voluptates tenetur qui omnis exercitationem","error suscipit maxime adipisci consequuntur recusandae\nvoluptas eligendi et est et voluptates\nquia distinctio ab amet quaerat molestiae et vitae\nadipisci impedit sequi nesciunt quis consectetur"},
				{ "/posts/{id}", "id", "100",10, 100, "at nam consequatur ea labore ea harum", "cupiditate quo est a modi nesciunt soluta\nipsa voluptas error itaque dicta in\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut"},
				};
	}
	
	@Test(dataProvider="providerGetItem")
	public void testGetItem(String resource, String parameterName, String parameterValue, 
			int expectedUsedIdValue,int expectedIdValue,String expectedTitleValue,String expectedBodyValue) {
		JsonPlaceHolderRest holderRest = new JsonPlaceHolderRest();

		JsonPath jsonPath = holderRest.getItem(resource, parameterName, parameterValue);
		
		Assert.assertEquals(jsonPath.get("userId"), expectedUsedIdValue);
		Assert.assertEquals(jsonPath.get("id"), expectedIdValue);
		Assert.assertEquals(jsonPath.get("title"), expectedTitleValue);
		Assert.assertEquals(jsonPath.get("body"), expectedBodyValue);
	}

	@DataProvider(name = "providerTestImplementMethods")
	public Object[][] createData4() {
		return new Object[][] { 
				{ "comments", "postId", "1"},
				{ "posts", "userId", "1"},
				};
	}
	
	@Test(dataProvider= "providerTestImplementMethods")
	public void testImplementMethods(String resource, String paramName, String paramValue) {
		JsonPlaceHolderRest holderRest = new JsonPlaceHolderRest();
		
		JsonPath jsonPath = holderRest.callImplementMethods(resource, paramName, paramValue);
		
		Assert.assertNotNull(jsonPath.get(paramName));
		
	}
}
