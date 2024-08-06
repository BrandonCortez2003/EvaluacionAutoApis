package glue;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import steps.CrearPedidoStep;

import java.util.List;
import java.util.Map;

public class StoreStepFed {

    @Steps
    CrearPedidoStep crearPedidoStep;

    @Steps



    private int id;
    private int petId;
    private int quantity;
    private String shipDate;
    private String status;
    private boolean complete;


    @Given("que tengo un pedido con los siguientes datos:")
    public void queTengoUnPedidoConLosSiguientesDatos(DataTable dataTable) {
        List<Map<String, String>> pedidos = dataTable.asMaps(String.class, String.class);

        // Solo se toma el primer conjunto de datos (o se podría modificar para manejar múltiples)
        Map<String, String> pedido = pedidos.get(0);

        id = Integer.parseInt(pedido.get("id"));
        petId = Integer.parseInt(pedido.get("petId"));
        quantity = Integer.parseInt(pedido.get("quantity"));
        shipDate = pedido.get("shipDate");
        status = pedido.get("status");
        complete = Boolean.parseBoolean(pedido.get("complete"));

        // Prepara el cuerpo del pedido en el paso Given
        crearPedidoStep.preparaBodyStore(id, petId, quantity, shipDate, status, complete);
    }
    @When("envio una peticion POST al endpoint store")
    public void envioUnaPeticionPOSTAlEndpointStore() {
        crearPedidoStep.createStore();
    }

    @Then("la respuesta debe ser el codigo de estado {int}")
    public void laRespuestaDebeSerElCodigoDeEstado(int arg0) {
        crearPedidoStep.validateResponse(arg0);
    }





    @Given("que he creado un pedido con el id {int}")
    public void queHeCreadoUnPedidoConElId(int id) {
        //crearPedidoStep.getOrder(id);
    }

    @When("envio solicitud GET al endpoint de consulta de pedidos con el id {int}")
    public void envioSolicitudGETAlEndpointDeConsultaDePedidosConElId(int id) {
    crearPedidoStep.getOrder(id);
    }

    @Then("la respuesta debe ser el codigo  {int}")
    public void laRespuestaDebeSerElCodigo(int arg0) {
        crearPedidoStep.validateResponse(arg0);
    }

    @And("el contenido del pedido debe ser como se espera")
    public void elContenidoDelPedidoDebeSerComoSeEspera(DataTable dataTable) {

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            int id = Integer.parseInt(row.get("id"));
            int petId = Integer.parseInt(row.get("petId"));
            int quantity = Integer.parseInt(row.get("quantity"));
            String shipDate = row.get("shipDate");
            String status = row.get("status");
            boolean complete = Boolean.parseBoolean(row.get("complete"));

            // Llama al método para validar el contenido del pedido
            crearPedidoStep.validateOrderContent(id, petId, quantity, status, complete);
        }
    }

}
