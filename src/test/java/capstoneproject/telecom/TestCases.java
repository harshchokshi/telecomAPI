package capstoneproject.telecom;

import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TestCases {
	
  private String token;
  private User user;
  private Contact contact;
	
  @Test(priority=1)
  public void addNewUser() {
	  user = new User();
	  user.setFirstName("Kenna");
	  user.setLastName("James");
	  user.setEmail("kennaj@fff.com");
	  user.setPassword("ashley123");
	  Response response = given().contentType("application/json").body(user)
			  .when().post("https://thinking-tester-contact-list.herokuapp.com/users")
			  .then().statusCode(201)
			  .statusLine(containsString("Created"))
			  .log().all()
			  .extract().response();
	  token = response.jsonPath().getString("token");
	  user.setToken(token);
	  String id = response.jsonPath().getString("user._id");
	  user.setId(id);
  }
  
  @Test(priority=2)
  public void getUserProfile() {
	  given().header("Authorization", "Bearer "+ user.getToken()).contentType("application/json")
	  .when().get("https://thinking-tester-contact-list.herokuapp.com/users/me")
	  .then().statusCode(200)
	  .statusLine(containsString("OK"))
	  .log().all()
	  .extract().response();
  }
  
  @Test(priority=3)
  public void updateUser() {
	  user.setFirstName("Alex");
	  user.setLastName("Gray");
	  user.setEmail("alexg@fff.com");
	  user.setPassword("ashley123");
	  given().header("Authorization", "Bearer "+ user.getToken()).contentType("application/json").body(user)
	  .when().patch("https://thinking-tester-contact-list.herokuapp.com/users/me")
	  .then().statusCode(200)
	  .statusLine(containsString("OK"))
	  .log().all()
	  .extract().response();
  }
  
  @Test(priority=4)
  public void logInUser() {
	  user.getEmail();
	  user.getPassword();
	  Response response = given().contentType("application/json").body(user)
	  .when().post("https://thinking-tester-contact-list.herokuapp.com/users/login")
	  .then().statusCode(200)
	  .statusLine(containsString("OK"))
	  .log().all()
	  .extract().response();
	  token = response.jsonPath().getString("token");
	  user.setToken(token);
  }
  
  @Test(priority=5)
  public void addContact() {
	  contact = new Contact();
	  contact.setFirstName("John");
	  contact.setLastName("Doe");
	  contact.setBirthdate("1990-01-01");
	  contact.setEmail("jdoe@fake.com");
	  contact.setPhone("8005555555");
	  contact.setStreet1("1 Main St.");
	  contact.setStreet2("Apartment A");
	  contact.setCity("Anytown");	
	  contact.setStateProvince("KS");
	  contact.setPostalCode("12345");
	  contact.setCountry("USA");
	  Response response = given().header("Authorization", "Bearer "+ user.getToken()).contentType("application/json").body(contact)
	  .when().post("https://thinking-tester-contact-list.herokuapp.com/contacts")
	  .then().statusCode(201)
	  .statusLine(containsString("Created"))
	  .log().all()
	  .extract().response();
	  String id = response.jsonPath().getString("_id");
	  contact.setId(id);
  }
  
  @Test(priority=6)
  public void getContactList() {
	  given().header("Authorization", "Bearer "+ user.getToken()).contentType("application/json")
	  .when().get("https://thinking-tester-contact-list.herokuapp.com/contacts")
	  .then().statusCode(200)
	  .statusLine(containsString("OK"))
	  .log().all();
  }
  
  @Test(priority=7)
  public void getContact() {
	  given().header("Authorization", "Bearer "+ user.getToken()).contentType("application/json")
	  .when().get("https://thinking-tester-contact-list.herokuapp.com/contacts/")
	  .then().statusCode(200)
	  .statusLine(containsString("OK"))
	  .log().all();
  }
  
  @Test(priority=8)
  public void updateContact() {
	  contact.setFirstName("Amy");
	  contact.setLastName("Miller");
	  contact.setBirthdate("1990-02-02");
	  contact.setEmail("amiller@fake.com");
	  contact.setPhone("8005554242");
	  contact.setStreet1("13 School St.");
	  contact.setStreet2("Apt. 5");
	  contact.setCity("Washington");	
	  contact.setStateProvince("QC");
	  contact.setPostalCode("A1A1A1");
	  contact.setCountry("Canada");
	  given().header("Authorization", "Bearer "+ user.getToken()).contentType("application/json").body(contact)
	  .when().put("https://thinking-tester-contact-list.herokuapp.com/contacts/"+ contact.getId())
	  .then().statusCode(200)
	  .statusLine(containsString("OK"))
	  .body("email", equalTo("amiller@fake.com"))
	  .log().all();
  }
  
  @Test(priority=9)
  public void updateContactFirstName() {
	  contact.setFirstName("Anna");
	  given().header("Authorization", "Bearer "+ user.getToken()).contentType("application/json").body(contact)
	  .when().patch("https://thinking-tester-contact-list.herokuapp.com/contacts/"+ contact.getId())
	  .then().statusCode(200)
	  .statusLine(containsString("OK"))
	  .body("email", equalTo("amiller@fake.com"))
	  .log().all();
  }
  
  @Test(priority=10)
  public void logOutUser() {
	  given().header("Authorization", "Bearer "+ user.getToken()).contentType("application/json")
	  .when().post("https://thinking-tester-contact-list.herokuapp.com/users/logout")
	  .then().statusCode(200)
	  .statusLine(containsString("OK"))
	  .log().all();
  }
  
  
} 