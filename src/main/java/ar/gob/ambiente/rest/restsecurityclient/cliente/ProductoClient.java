
package ar.gob.ambiente.rest.restsecurityclient.cliente;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:ProductoFacadeREST
 * [productos]<br>
 * USAGE:
 * <pre>
 *        ProductoClient client = new ProductoClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author rincostante
 */
public class ProductoClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/restSecurity-1.0.0-SNAPSHOT/rest";

    public ProductoClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("productos");
    }
    /**
     * A los métodos securizados les agrego el parámetro "token"
     * y a los que escriben les agrego un Response como retorno
     * @param requestEntity = Entidad JSON que contiene los datos del producto a editar
     * @param id = id del producto a editar en formato String
     * @param token = token recibido por el server para la autenticación
     * @return = Entidad JSON con los datos actualizados y el resultado de la operación
     */ 
    
    public Response edit_JSON(Object requestEntity, String id, String token) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                // entre el .request y el .get le agrego el header
                .header(HttpHeaders.AUTHORIZATION, token)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public <T> T find_JSON(Class<T> responseType, String id, String token) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                // entre el .request y el .get le agrego el header
                .header(HttpHeaders.AUTHORIZATION, token)
                .get(responseType);
    }

    public Response create_JSON(Object requestEntity, String token) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                // le agrego el header antes del .post
                .header(HttpHeaders.AUTHORIZATION, token)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public <T> T findAll_JSON(Class<T> responseType, String token) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                // entre el .request y el .get le agrego el header
                .header(HttpHeaders.AUTHORIZATION, token)
                .get(responseType);
    }

    public void close() {
        client.close();
    }
}
