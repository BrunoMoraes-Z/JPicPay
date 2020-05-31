<h1 align="center">
    <img alt="PicPay" width="200px" src="https://www.picpay.com/static/images/new/home/ppay-icon.png">
</h1>

<h1 align="center">
JPicPay
</h1>

## Como Utilizar

Adicione os código im importe o [Jar](https://github.com/BrunoMoraes-Z/JPicPay/releases) em seu projeto e crie a variavel da api.

 ```java
 JPicPay api = new JPicPay();
 ```
 #### Criar Pagamento 
 Para criar um pagamento precisa de.
  - Objeto `consumidor` do tipo `Buyer`
  - `referenceCode` - código para o picpay identifar o pedido.
  - `value` - valor do pedido.
  - `expireDate` - data máxima para realizar o pagamento no `Formato ISO 8601. Exemplo: 2022-05-01T16:00:00-03:00`.
 
 ```java
 Buyer consumidor = new Buyer();
 consumidor.setFirstName("Bruno");
 consumidor.setLastName("Moraes");
 consumidor.setContact("+55 27 12345-6789");
 consumidor.setCpf("123.456.789-10");
 consumidor.setEmail("teste@gmail.com");

 consumidor = new Buyer("Bruno", "Moraes", "123.456.789-10", "teste@gmail.com", "+55 27 12345-6789");

 String referenceCode = UUID.randomUUID().toString();
        
 Date expire = new Date();

 JSONObject paymentReponse = api.createPayment(referenceCode, 55.20, expire, consumidor);
 ```
 #### Resposta com sucesso ao criar um pagamento.
 
 ```json
 {
    "statusCode": 200,
    "message": {
      "referenceId": "102030",
      "paymentUrl": "https://app.picpay.com/checkout/NWFmMGRjNmViZDc0Y2EwMDMwNzZlYzEw",
      "expiresAt": "2022-05-01T16:00:00-03:00",
      "qrcode": {
        "content": "https://app.picpay.com/checkout/NWNlYzMxOTM1MDg1NGEwMDIwMzUxODcy",
        "base64": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAPAAAADwCAYAAAA+VemSAAAgAEl...="
      }
    }
 }
 ```
 
 #### Criar Pagamento
 Basta informar o código de referencia que foi utilizado para criar o pagamento.
 
 ```java
 JSONObject statusResponse = api.getPaymentStatus(referenceCode);
 ```
 
 #### Resposta com sucesso ao verificar o status.
 
 ```json
 {
    "statusCode": 200,
    "message": {
      "authorizationId": "555008cef7f321d00ef236333",
      "referenceId": "102030",
      "status": "paid"
    }
 }
 ```
 
 #### Cancelar Compra
 Obrigátoriamente deve informar novamente o código de referencia que foi utilizado para criar o pagamento.
 caso o venda ja esteja paga deve informar o `authorizationId` retornado pelo Status.
 
 ```java
 JSONObject cancelResponse = api.cancelPayment(referenceCode, "authorizationId");
 JSONObject cancel_Response = api.cancelPayment(referenceCode, null);
 ```
 
 #### Resposta com sucesso ao cancelar uma venda.
 
 ```json
 {
    "statusCode": 200,
    "message": {
      "referenceId": "102030",
      "cancellationId": "5b008cef7f321d00ef236444"
    }
 }
 ```
 
 ---
 # Criar Conta E-Commerce
 [PicPay E-Commerce](https://ecommerce.picpay.com)
 
 # Documentação Oficial
 - [PicPay](https://ecommerce.picpay.com/doc/#)
