Feature: Pruebas de servicios de la API Swagger Store

  @crearPedido
  Scenario Outline: Crear un pedido con el metodo POST
    Given que tengo un pedido con los siguientes datos:
      | id   | petId   | quantity   | shipDate   | status   | complete   |
      | <id> | <petId> | <quantity> | <shipDate> | <status> | <complete> |
    When envio una peticion POST al endpoint store
    Then la respuesta debe ser el codigo de estado 200

    Examples:

      | id   | petId | quantity | shipDate                 | status    | complete |
      | 1000 | 1     | 3        | 2024-08-06T18:23:02.254Z | placed    | true     |
      | 1001 | 2     | 3        | 2024-08-07T18:23:02.254Z | shipped   | false    |
      | 1002 | 3     | 1        | 2024-08-08T18:23:02.254Z | delivered | true     |


  @consultarPedido
  Scenario Outline: Consultar un pedido con el metodo GET
    Given que he creado un pedido con el id <id>
    When envio solicitud GET al endpoint de consulta de pedidos con el id <id>
    Then la respuesta debe ser el codigo  200
    And el contenido del pedido debe ser como se espera
      | id   | petId   | quantity   | shipDate   | status   | complete   |
      | <id> | <petId> | <quantity> | <shipDate> | <status> | <complete> |
    Examples:
      | id   | petId | quantity |  status    | complete |
      | 1000 | 1     | 3        |  placed    | true     |
      | 1001 | 2     | 3        |  shipped   | false    |
      | 1002 | 3     | 1        |  delivered | true     |