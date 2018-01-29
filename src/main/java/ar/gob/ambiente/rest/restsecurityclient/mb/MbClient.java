
package ar.gob.ambiente.rest.restsecurityclient.mb;

import ar.gob.ambiente.rest.paqsecurityrest.Producto;
import ar.gob.ambiente.rest.restsecurityclient.cliente.ProductoClient;
import ar.gob.ambiente.rest.restsecurityclient.cliente.UsuarioClient;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author rincostante
 */
public class MbClient implements Serializable {

    private String token;
    private Producto prod;
    private String nombreCientifico;
    private String nombreVulgar;
    private String clase;
    private String unidad;
     private float total;
    private List<Producto> productos;
    private ProductoClient prodClient;
    private UsuarioClient usuarioClient;
    
    public MbClient() {
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public String getNombreVulgar() {
        return nombreVulgar;
    }

    public void setNombreVulgar(String nombreVulgar) {
        this.nombreVulgar = nombreVulgar;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Producto getProd() {
        return prod;
    }

    public void setProd(Producto prod) {
        this.prod = prod;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    @PostConstruct
    public void init(){
        prod = new Producto();
        actualizarListado();
    }         

    public void save(){
        Response response;
        boolean result = true;
        try{
            // obtengo el token
            getToken();
            // seteo el producto
            prod.setClase(clase);
            prod.setNombreCientifico(nombreCientifico);
            prod.setNombreVulgar(nombreVulgar);
            prod.setTotal(total);
            prod.setUnidad(unidad);
            
            prodClient = new ProductoClient();
            // edito o inserto según el producto tenga id > 0
            if(prod.getId() != null){
                // edito
                response = prodClient.edit_JSON(prod, String.valueOf(prod.getId()), token);
                // verifico el resultado
                if(response.getStatus() != 200){
                    result = false;
                }
            }else{
                // inserto
                response = prodClient.create_JSON(prod, token);
                // verifico el resultado
                if(response.getStatus() != 201){
                    result = false;
                }
            }
            prodClient.close();
            
            if(!result){
                System.out.println("Hubo un error en el servidor al procesar el producto: " + response.getStatus());
            }else{
                actualizarListado();
            }
            
        }catch(ClientErrorException ex){
            System.out.println("Hubo un error procesando el método save: " + ex.getMessage());
        }
    }
    
    private void getToken(){
        try{
            usuarioClient = new UsuarioClient();
            Response responseUs = usuarioClient.authenticateUser_JSON(Response.class, "zorba", "zorbas");
            MultivaluedMap<String, Object> headers = responseUs.getHeaders();
            List<Object> lstHeaders = headers.get("Authorization");
            token = (String)lstHeaders.get(0); 
            usuarioClient.close();
        }catch(ClientErrorException ex){
            System.out.println("Hubo un error obteniendo el token: " + ex.getMessage());
        }
    }
    
    private void actualizarListado(){
        try{
            // obtengo que token
            getToken();
            // obtengo el listado de productos
            prodClient = new ProductoClient();
            GenericType<List<Producto>> gType = new GenericType<List<Producto>>() {};
            Response response = prodClient.findAll_JSON(Response.class, token);
            productos = response.readEntity(gType);
            prodClient.close();
        }catch(ClientErrorException ex){
            System.out.println("Hubo un error obteniendo los productos: " + ex.getMessage());
        }
    }
}
