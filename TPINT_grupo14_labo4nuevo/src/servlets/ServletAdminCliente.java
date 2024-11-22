package servlets;

import java.io.IOException;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    	
    	if (session == null || session.getAttribute("tipoUsuario") != "admin" ) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
    		dispatcher.forward(request, response);
    	}
    	
    	
    	
    	if (request.getParameter("btnAgregarCliente") != null) {
    		cargarDesplegables(request);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCliente.jsp");
            dispatcher.forward(request, response);
            
        }else if (request.getParameter("btnAdminClientes") != null || request.getParameter("btnAtras1") != null) {
			listaClientes1 = clienteNegocioImpl.list();
            request.setAttribute("Lista_Clientes", listaClientes1);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarClientes.jsp");
            dispatcher.forward(request, response);
            
        }else if (request.getParameter("btnDetalle") != null) {
			String dni = request.getParameter("dni");

			Cliente auxCliente = (Cliente) listaClientes1.stream().filter(x -> x.getDni().equals(dni)).findFirst()
					.orElse(null);
			Usuario auxUsu = auxCliente.getUsuario();
			
			request.setAttribute("ClienteDetalle", auxCliente);
			request.setAttribute("dni", dni);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/DetalleCliente.jsp");
			dispatcher.forward(request, response);
        } else if (request.getParameter("btnModificar") != null) {
			String dni = request.getParameter("dni");
			Cliente auxCliente = clienteNegocioImpl.get(dni);
			request.setAttribute("ClienteDetalle", auxCliente);
			
			cargarDesplegables(request);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCliente.jsp");
			dispatcher.forward(request, response);
        } 
        else if(request.getParameter("btnEliminar") != null) {
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
    	    
    	    
    	    request.getRequestDispatcher("/Login.jsp").forward(request, response);
    	    
    	}
    	
    	

    	
    	if (request.getParameter("btnGuardarCambios") != null) {
			Provincia auxProv = (Provincia) listaProvincia.stream()
					.filter(x -> x.getIdprovincia() == Integer.parseInt(request.getParameter("provincia"))).findFirst()
					.orElse(null);
			Localidad auxLoc = (Localidad) listaLocalidad.stream()
					.filter(x -> x.getIdlocalidad() == Integer.parseInt(request.getParameter("localidad"))).findFirst()
					.orElse(null);

			if (auxProv.getIdprovincia() != auxLoc.getProvincia().getIdprovincia()) {
				session.setAttribute("respuesta", "La localidad no pertenece a la provincia seleccionada");
				cargarDesplegables(request);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCliente.jsp");
				dispatcher.forward(request, response);
			} else {
				String auxDni = request.getParameter("dni");
				String auxCuil = request.getParameter("cuil");
				String auxEmail = request.getParameter("email");
				String auxUsuario = request.getParameter("usuario");
				String auxNombre = request.getParameter("nombre");
				String auxApellido = request.getParameter("apellido");
				char auxSexo = request.getParameter("sexo").charAt(0);
				String auxPais = request.getParameter("nacionalidad1");
				Date auxFecha = Date.valueOf(request.getParameter("fechanacimiento"));
				String auxDireccion = request.getParameter("direccion");
				String auxTelefono = request.getParameter("telefono");

				int idprovincia = Integer.parseInt(request.getParameter("provincia"));
				Provincia cProvincia = provinciaNegocioImpl.get(idprovincia);
				int idlocalidad = Integer.parseInt(request.getParameter("localidad"));
				Localidad cLocalidad = localidadNegocioImpl.get(idlocalidad);

				cLocalidad.setProvincia(cProvincia);
				String contraseña = request.getParameter("contrasena");

				Cliente cliente = clienteNegocioImpl.get(request.getParameter("dni"));
				Usuario modUser = usuarioNegocioImpl.get(cliente.getUsuario().getIdusuario());

				modUser.setNombreusuario(auxUsuario);
				modUser.setContraseña(contraseña);

				Cliente Clientenuevo = new Cliente(auxDni, auxCuil, auxNombre, auxApellido, auxSexo, auxPais, auxFecha,
						auxDireccion, cLocalidad, auxEmail, auxTelefono);

				Boolean user_modificado = usuarioNegocioImpl.update(modUser);

				Boolean cli_modificado = clienteNegocioImpl.update(Clientenuevo);

				if (cli_modificado == true && user_modificado == true) {
					session.setAttribute("respuesta", "El cliente fue modificado exitosamente");
					listaClientes1 = clienteNegocioImpl.list();
					request.setAttribute("Lista_Clientes", listaClientes1);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarClientes.jsp");
					dispatcher.forward(request, response);
				} else {
					session.setAttribute("respuesta", "Error, al modificar el cliente");
					cargarDesplegables(request);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCliente.jsp");
					dispatcher.forward(request, response);

				}

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
	               String contraseña = request.getParameter("contrasena");
	               TipoUsuarioNegocioImpl tipoUsuarioNegocioImpl = new TipoUsuarioNegocioImpl();
	               TipoUsuario tipoUsuario = tipoUsuarioNegocioImpl.get(2);  
	               Usuario newUsuario = new Usuario();
	               newUsuario.setNombreusuario(auxUsuario);
	               newUsuario.setContraseña(contraseña);
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
    
    private Date validarFechaNacimiento(Date fechaNacVal) throws Exception {
        try {
            
            if (fechaNacVal == null) {
                throw new Exception("La fecha de nacimiento no puede ser nula.");
            }
            
            
            return new java.sql.Date(fechaNacVal.getTime());
        } catch (Exception e) {
            throw new Exception("Fecha de nacimiento inválida.");
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

