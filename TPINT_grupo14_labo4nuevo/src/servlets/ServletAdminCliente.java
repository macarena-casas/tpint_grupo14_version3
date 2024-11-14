package servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daoImpl.Conexion;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;
import negocioImpl.LocalidadNegocioImpl;
import negocioImpl.ProvinciaNegocioImpl;
import negocioImpl.TipoUsuarioNegocioImpl;
import negocioImpl.PaisNegocioImpl;

import entidad.Cliente;
import entidad.Localidad;
import entidad.Pais;
import entidad.Provincia;
import entidad.TipoUsuario;
import entidad.Usuario;



@WebServlet("/ServletAdminCliente")
public class ServletAdminCliente extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ArrayList<Cliente> listaClientes1 = new ArrayList<Cliente>();
    private ArrayList<Localidad> listaLocalidad = new ArrayList<Localidad>();
    private ArrayList<Provincia> listaProvincia = new ArrayList<Provincia>();    
    private ArrayList<Pais> listaPais = new ArrayList<Pais>();

	private ClienteNegocioImpl clienteNegocioImpl = new ClienteNegocioImpl();
	private UsuarioNegocioImpl usuarioNegocioImpl = new UsuarioNegocioImpl();
	private LocalidadNegocioImpl localidadNegocioImpl = new LocalidadNegocioImpl();
	private ProvinciaNegocioImpl provinciaNegocioImpl = new ProvinciaNegocioImpl();
	private PaisNegocioImpl paisNegocioImpl = new PaisNegocioImpl();
	
    public ServletAdminCliente() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();  
    	
    	/*if(session == null") {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
    		dispatcher.forward(request, response);
    	}
    	
    	*/
    	// condicion cuando este logeado
    	if (request.getParameter("btnAgregarCliente") != null) {
    		cargarDesplegables(request);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
            dispatcher.forward(request, response);
            
        }else if (request.getParameter("btnAdminClientes") != null) {
			listaClientes1 = clienteNegocioImpl.list();
            request.setAttribute("Lista_Clientes", listaClientes1);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarClientes.jsp");
            dispatcher.forward(request, response);
            
        }else if (request.getParameter("btnDetalle") != null) {
            String dni = request.getParameter("dni");
            Cliente auxCliente = (Cliente)listaClientes1.stream().filter(x -> x.getDni().equals(dni)).findFirst().orElse(null);
            request.setAttribute("ClienteDetalle", auxCliente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DetalleCliente.jsp");
            dispatcher.forward(request, response);
            
        }else if (request.getParameter("btnModificar") != null) {
            String dni = request.getParameter("dni");
            Cliente auxCliente = clienteNegocioImpl.get(dni);
            request.setAttribute("ClienteDetalle", auxCliente);
            cargarDesplegables(request);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCliente.jsp");
            dispatcher.forward(request, response);
            
        }else if(request.getParameter("btnEliminar") != null) {
        	String dni = request.getParameter("dni");
			String respuesta = clienteNegocioImpl.delete(clienteNegocioImpl.get(dni));
            session.setAttribute("respuesta", respuesta);
        	listaClientes1 = clienteNegocioImpl.list();
            request.setAttribute("Lista_Clientes", listaClientes1);   
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarClientes.jsp");
        	dispatcher.forward(request, response);
        }    
    }
             
            

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	HttpSession session = request.getSession(false);
    	if (session == null) {
    	    // Si la sesi�n no existe, redirige al login
    	    System.out.println("Sesi�n no encontrada. Redirigiendo a Login.jsp.");
    	    request.getRequestDispatcher("/Login.jsp").forward(request, response);
    	    
    	}

    	if (session != null) {
    	    // Si la sesi�n existe, obtenemos el userId
    	    Integer userId = (Integer) session.getAttribute("userId");
    	    if (userId != null) {
    	        System.out.println("Usuario autenticado. userId: " + userId);
    	        
    	        Conexion conexion = new Conexion();
    	        try {
    	            // Ejecutamos la consulta con el userId
    	            System.out.println("Preparando consulta para obtener datos del cliente.");
    	            conexion.setearConsulta("SELECT * FROM clientes WHERE usuario_id = ?");
    	            conexion.setearParametros(1, userId);
    	            ResultSet rs = conexion.ejecutarLectura();
    	            
    	            if (rs.next()) {
    	                // Si se encuentra un cliente, procesamos la informaci�n
    	                System.out.println("Cliente encontrado. Procesando datos...");
    	                
    	                String nombreCliente = rs.getString("nombre");
    	                String apellidoCliente = rs.getString("apellido");
    	                String dni = rs.getString("dni");
    	                String cuil = rs.getString("cuil");
    	                String sexo = rs.getString("sexo");
    	                String nacionalidad = rs.getString("nacionalidad");
    	                Date fechanacimiento = rs.getDate("fecha_nacimiento");
    	                String direccion = rs.getString("direccion");
    	                String correo = rs.getString("correo_electronico");
    	                String telefono = rs.getString("telefono");

    	                // Establecemos los atributos para la JSP
    	                request.setAttribute("nombreCliente", nombreCliente);
    	                request.setAttribute("apellidoCliente", apellidoCliente);
    	                request.setAttribute("dni", dni);
    	                request.setAttribute("cuil", cuil);
    	                request.setAttribute("sexo", sexo);
    	                request.setAttribute("nacionalidad", nacionalidad);
    	                request.setAttribute("fechanacimiento", fechanacimiento);
    	                request.setAttribute("direccion", direccion);
    	                request.setAttribute("correo", correo);
    	                request.setAttribute("telefono", telefono);

    	                // Redirigimos a la p�gina DetalleCliente.jsp
    	                System.out.println("Datos del cliente preparados. Redirigiendo a DetalleCliente.jsp.");
    	                request.getRequestDispatcher("DetalleCliente.jsp").forward(request, response);
    	               
    	            } else {
    	                // Si no se encuentra el cliente, mostrar error y evitar forward
    	                System.out.println("No se encontr� la cuenta asociada al usuario.");
    	                request.setAttribute("error", "No se encontr� la cuenta asociada al usuario.");
    	                request.getRequestDispatcher("errorPage.jsp").forward(request, response);  // Redirigir a una p�gina de error
    	            }

    	            rs.close();
    	            conexion.cerrarConexion();

    	        } catch (Exception e) {
    	            // Manejo de excepciones
    	            System.out.println("Error al obtener los datos de la cuenta.");
    	            e.printStackTrace();
    	            request.setAttribute("error", "Error al obtener los datos de la cuenta.");
    	            request.getRequestDispatcher("errorPage.jsp").forward(request, response);  // Redirigir a una p�gina de error
    	        }
    	    } else {
    	        // Si no hay userId en la sesi�n
    	        System.out.println("Usuario no autenticado.");
    	        request.setAttribute("error", "Usuario no autenticado.");
    	        request.getRequestDispatcher("errorPage.jsp").forward(request, response);  // Redirigir a una p�gina de error
    	    }
    	} else {
    	    // Si la sesi�n es nula, redirige al login
    	    System.out.println("Sesi�n expirada o no encontrada. Redirigiendo a Login.jsp.");
    	    response.sendRedirect("Login.jsp");  // Este s� es un redireccionamiento
    	}
    	
    	if(request.getParameter("btnGuardarCambios") != null) {
			try{
				String dni = request.getParameter("dni");
				Cliente auxCliente = (Cliente)listaClientes1.stream().filter(x -> x.getDni().equals(dni)).findFirst().orElse(null);
				
				Provincia provincia = provinciaNegocioImpl.get(Integer.parseInt(request.getParameter("provincia")));
				Localidad localidad = localidadNegocioImpl.get(Integer.parseInt(request.getParameter("localidad")));

				if(localidad.getProvincia().getIdprovincia() != provincia.getIdprovincia()){
					throw new Exception("La localidad no pertenece a la provincia seleccionada");
				}
				String regex = "[a-zA-Z������������\\s]+"; //Solo admite caracteres alfabeticos
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(request.getParameter("nombre"));
				if(!matcher.matches()) {
					throw new Exception("El nombre debe contener exclusivamente caracteres alfabeticos");
				}

				Cliente modCliente = new Cliente(
            			request.getParameter("dni"),
            			request.getParameter("cuil"),
            			request.getParameter("nombre"),
            			request.getParameter("apellido"),
            			request.getParameter("sexo").charAt(0),
            			paisNegocioImpl.get(Integer.parseInt(request.getParameter("nacionalidad"))).getNombre(),
            			Date.valueOf(request.getParameter("fecha-nacimiento")),
            			request.getParameter("direccion"),
            			localidadNegocioImpl.get(Integer.parseInt(request.getParameter("localidad"))),
            			request.getParameter("Email"),
            			request.getParameter("telefonos"),
            			new Usuario()
            			);
            	
        		Boolean cli_modificado = clienteNegocioImpl.update(modCliente);       		
        		Cliente cliente = clienteNegocioImpl.get(request.getParameter("dni"));
        		Usuario modUser = usuarioNegocioImpl.get(cliente.getUsuario().getIdusuario());
        		modUser.setContrase�a((String) request.getParameter("contrasena"));
        		Boolean user_modificado = usuarioNegocioImpl.update(modUser);        		
        		cliente.setUsuario(modUser);
        		
        		if(cli_modificado && user_modificado) {
        		session.setAttribute("respuesta", "Los cambios se guardaron exitosamente");
        		listaClientes1 = clienteNegocioImpl.list();
	            request.setAttribute("Lista_Clientes", listaClientes1);   
        		 RequestDispatcher dispatcher = request.getRequestDispatcher("/ClientesListar.jsp");
                dispatcher.forward(request, response);
        		}
			}catch(Exception e){
				session.setAttribute("respuesta", "Error al guardar los cambios: " + e.getMessage());
				listaClientes1 = clienteNegocioImpl.list();
	            request.setAttribute("Lista_Clientes", listaClientes1);   
        		RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarClientes.jsp");
        		dispatcher.forward(request, response);
			}        	
         	
         }
    	 else if(request.getParameter("btnAgregarClienteNuevo") != null) { 
    		       		  
    		   Provincia auxProv = (Provincia)listaProvincia.stream().filter(x -> x.getIdprovincia() ==  Integer.parseInt(request.getParameter("provincia"))).findFirst().orElse(null);
    		  Localidad auxLoc = (Localidad)listaLocalidad.stream().filter(x -> x.getIdlocalidad() ==  Integer.parseInt(request.getParameter("localidad"))).findFirst().orElse(null);
   		    
    		    if(auxProv.getIdprovincia() != auxLoc.getProvincia().getIdprovincia()) {
	  	        	session.setAttribute("respuesta", "La localidad no pertenece a la provincia seleccionada");
	  	        	cargarDesplegables(request);
	  	            RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
	  	            dispatcher.forward(request, response);
    		    }
    		    else {
	                String auxDni = request.getParameter("dni");       			
	                String  auxCuil = request.getParameter("cuil");
	                String auxEmail = request.getParameter("email"); 
	                String auxUsuario = request.getParameter("usuario");
	                
	                List<Cliente> auxLista = null;
	                listaClientes1 = clienteNegocioImpl.list();
	                auxLista = listaClientes1.stream().filter(x -> x.getDni().equals(auxDni) || x.getCuil().equals(auxCuil) || x.getUsuario().getNombreusuario().equals(auxUsuario) || x.getCorreo().equals(auxEmail)).collect(Collectors.toList());
	      	
	               if(auxLista==null || auxLista.isEmpty()) {
	      		    
	      		   int idpais =Integer.parseInt( request.getParameter("nacionalidad1")); 
	              Pais cpais = paisNegocioImpl.get(idpais); 
	               int idprovincia = Integer.parseInt(request.getParameter("provincia"));
	   		       Provincia cProvincia = provinciaNegocioImpl.get(idprovincia);
	               int idlocalidad = Integer.parseInt(request.getParameter("localidad"));
	               Localidad cLocalidad = localidadNegocioImpl.get(idlocalidad);
	              cProvincia.setPais(cpais); 
	               cLocalidad.setProvincia(cProvincia);        
	               String contrase�a = request.getParameter("contrasena");
	               TipoUsuarioNegocioImpl tipoUsuarioNegocioImpl = new TipoUsuarioNegocioImpl();
	               TipoUsuario tipoUsuario = tipoUsuarioNegocioImpl.get(2); //Valor por cliente       
	               Usuario newUsuario = new Usuario();
	               newUsuario.setNombreusuario(auxUsuario);
	               newUsuario.setContrase�a(contrase�a);
	               newUsuario.setTipoUsuario(tipoUsuario);
	               usuarioNegocioImpl.insert(newUsuario);
	               int idusuario =usuarioNegocioImpl.list().size();
	               newUsuario = usuarioNegocioImpl.get(idusuario); 
	      	       Cliente Clientenuevo = new Cliente(
	            			auxDni,         			
	            			auxCuil,
	            			request.getParameter("nombre"),
	            			request.getParameter("apellido"),
	            			request.getParameter("sexo").charAt(0),
	            			paisNegocioImpl.get(Integer.parseInt(request.getParameter("nacionalidad1"))).getNombre(),
	            			Date.valueOf(request.getParameter("fechanacimiento")),
	            			request.getParameter("direccion"),
	            			cLocalidad,
	            	     	auxEmail,
	            			request.getParameter("telefono"),
	            			newUsuario
	            	);
	      	     clienteNegocioImpl.insert(Clientenuevo);
	      	    	
    		
	                 session.setAttribute("respuesta", "El cliente fue agregado exitosamente");
	                 cargarDesplegables(request);
	                RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
	                dispatcher.forward(request, response);
	         
             
             }else {
            	session.setAttribute("respuesta", "Error. El cliente ya se encuentra ingresado.");
            	cargarDesplegables(request);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
                dispatcher.forward(request, response);

           }
    		    
    	 } 
        
       }
    }
    
    
    
    private void cargarDesplegables(HttpServletRequest request) {
    	listaPais = paisNegocioImpl.list();   	
      	request.setAttribute("Lista_Paises", listaPais);
      listaLocalidad = localidadNegocioImpl.list();
      	request.setAttribute("Lista_Localidades", listaLocalidad);
      	listaProvincia = provinciaNegocioImpl.list();
      	request.setAttribute("Lista_Provincias", listaProvincia);
	}
}

