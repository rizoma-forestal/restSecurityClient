/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.gob.ambiente.rest.restsecurityclient.cliente;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:UsuarioResource [usuario]<br>
 * USAGE:
 * <pre>
 *        UsuarioClient client = new UsuarioClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author rincostante
 */
public class UsuarioClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/restSecurity-1.0.0-SNAPSHOT/rest";

    public UsuarioClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("usuario");
    }

    public <T> T authenticateUser_XML(Class<T> responseType, String password, String user) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (password != null) {
            resource = resource.queryParam("password", password);
        }
        if (user != null) {
            resource = resource.queryParam("user", user);
        }
        resource = resource.path("login");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T authenticateUser_JSON(Class<T> responseType, String password, String user) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (password != null) {
            resource = resource.queryParam("password", password);
        }
        if (user != null) {
            resource = resource.queryParam("user", user);
        }
        resource = resource.path("login");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
