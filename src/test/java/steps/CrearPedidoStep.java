package steps;

import io.restassured.response.Response;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.Matchers.equalTo;

public class CrearPedidoStep {

    private static String CREATE_STORE = "https://petstore.swagger.io/v2/store/order";
    private Response response;
    private String bodyNuevoPedido;


    public void preparaBodyStore(int id, int petId, int quantity, String shipDate, String status, boolean complete){

        bodyNuevoPedido = "{\n" +
                "\"id\": " + id + ",\n" +
                "\"petId\": " + petId + ",\n" +
                "\"quantity\": " + quantity + ",\n" +
                "\"shipDate\": \"" + shipDate + "\",\n" +
                "\"status\": \"" + status + "\",\n" +
                "\"complete\": " + (complete ? "true" : "false") + "\n" +
                "}";
    }
    public void createStore(){
        response = given().baseUri(CREATE_STORE)
                .header("Content-Type", "application/json")
                .body(bodyNuevoPedido)
                .when()
                .post()
                .then()
                .extract()
                .response();
    }

    public void validateResponse(int statusCode) {
        response.then().statusCode(statusCode);
    }

    public void getOrder(int id) {
        response = given()
                .baseUri("https://petstore.swagger.io/v2/store/order")
                .pathParam("orderId", id)
                .when()
                .get("/{orderId}")
                .then()
                .extract()
                .response();
    }
    public void validateOrderContent(int id, int petId, int quantity, String status, boolean complete) {
        response.then().assertThat()
                .body("id", equalTo(id))
                .body("petId", equalTo(petId))
                .body("quantity", equalTo(quantity))
                .body("status", equalTo(status))
                .body("complete", equalTo(complete));
    }
}
