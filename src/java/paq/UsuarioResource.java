/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paq;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author xido_
 */
@Path("usuario")
public class UsuarioResource extends HttpServlet {

    @Context
    private UriInfo context;
    
    // Manajo de identificadores de los usuarios.
    private static String idUsa = "";
    private static Map<String, Usuario> listaUsuarios = new ConcurrentHashMap <String, Usuario>();
    UtilDB util = new UtilDB();

    public UsuarioResource() {
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() throws ClassNotFoundException  { 
        List<Usuario> listaRegistrada = util.cargaListaUsuarios();
        for(Usuario cadaUsuario: listaRegistrada){
            idUsa = String.valueOf(cadaUsuario.getId());
            listaUsuarios.put(idUsa, cadaUsuario);
        }
        return listaUsuarios.toString();   
    }
    
    /*@GET
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getHtml(@PathParam("id") String id){
        final Usuario elUsuario = listaUsuarios.get(id);
        System.out.println(elUsuario.toString());
        if (elUsuario == null) 
        {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return elUsuario.toString();
    }*/
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHtml(@PathParam("id") String id){
        final Usuario elUsuario = listaUsuarios.get(id);
        System.out.println(elUsuario.toString());
        if (elUsuario == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.ok(elUsuario).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postNuevoCliente(
            @FormParam("nombre") String nombre, 
            @FormParam("apellidoP") String apellidoP, 
            @FormParam("apellidoM") String apellidoM, 
            @FormParam("edad") int edad, 
            @FormParam("genero") String genero, 
            @FormParam("correo") String correo, 
            @FormParam("userU") String userU, 
            @FormParam("passwordU") String passwordU ) throws ClassNotFoundException, URISyntaxException, NoSuchAlgorithmException {
        Usuario usuarioNuevo = new Usuario(nombre, apellidoP, apellidoM, edad, genero, correo, userU, passwordU);
        util.registraUsuario(usuarioNuevo);
        
        
        System.out.println("Cliente nuevo creado " + usuarioNuevo.toString());
        Response.created(URI.create("/cliente" + usuarioNuevo.getId())).build();
        java.net.URI location = new java.net.URI("../login.html");
        return Response.temporaryRedirect(location).build();

    }
    
    @POST
    @Path("/Login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postLogin(
            @FormParam("txtPass") String password, 
            @FormParam("txtUsu") String usuario ) throws ClassNotFoundException, URISyntaxException, NoSuchAlgorithmException, SQLException {
        
        String resultado = util.validarLoginUsuario(usuario, password);
        Usuario miUsuario = util.encontrarDatosUsuario(usuario);
       
        //String nombre = "Giovanni"//miUsuario.getNombre();
        String nombre = miUsuario.getNombre().replaceAll("\\s+","");
        String id = String.valueOf(miUsuario.getId());
        if(resultado.equalsIgnoreCase("Encontrado")){
            java.net.URI location = new java.net.URI("../menu.html?q=" + id + "&n=" + nombre );
            return Response.temporaryRedirect(location).build();
        }else {
            java.net.URI location = new java.net.URI("../login.html");
            return Response.temporaryRedirect(location).build();
        }   
    }
    
    
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
}
