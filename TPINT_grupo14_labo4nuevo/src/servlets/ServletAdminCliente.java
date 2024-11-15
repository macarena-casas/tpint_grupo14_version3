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

import daoImpl.ClienteDaoImpl;
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
    	
    	if (session == null || session.getAttribute("tipoUsuario") != "admin" ) {
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
    		dispatcher.forward(request, response);
    	}
    	
    	
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
            Usuario auxUsu = auxCliente.getUsuario();
            request.setAttribute("ClienteDetalle", auxCliente);
            request.setAttribute("dni", dni);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DetalleCliente.jsp");
            dispatcher.forward(request, response);
        } else if (request.getParameter("btnModificar") != null) {
            String dni = request.getParameter("dni");
            

            
            Cliente auxCliente = clienteNegocioImpl.get(dni);
            if (auxCliente != null) {
                System.out.println("Cliente encontrado: " + auxCliente.getNombre() + " " + auxCliente.getApellido());
            } else {
                System.out.println("Cliente no encontrado");
            }

            request.setAttribute("ClienteDetalle", auxCliente);
            cargarDesplegables(request);
            session.setAttribute("idusu", auxCliente.getUsuario().getIdusuario());
            
            
            List<Provincia> provincias = provinciaNegocioImpl.list();
            List<Localidad> localidades = localidadNegocioImpl.list();
            if (provincias != null && !provincias.isEmpty()) {
                System.out.println("Provincias cargadas correctamente.");
            } else {
                System.out.println("No se encontraron provincias.");
            }
            if (localidades != null && !localidades.isEmpty()) {
                System.out.println("Localidades cargadas correctamente.");
            } else {
                System.out.println("No se encontraron localidades.");
            }
        
            
            
            request.setAttribute("provincias", provincias);
            request.setAttribute("localidades", localidades);
            request.setAttribute("nombreCliente", auxCliente.getNombre());
            request.setAttribute("apellidoCliente", auxCliente.getApellido());
            request.setAttribute("dni", auxCliente.getDni());
            request.setAttribute("cuil", auxCliente.getCuil());
            request.setAttribute("sexo", auxCliente.getSexo());
            request.setAttribute("nacionalidad", auxCliente.getNacionalidad());
            request.setAttribute("fechanacimiento", auxCliente.getFechaNacimiento());
            request.setAttribute("direccion", auxCliente.getDireccion());
            request.setAttribute("telefono", auxCliente.getTelefono());
            request.setAttribute("correo", auxCliente.getCorreo());
            
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
    	    System.out.println("Botón 'Guardar Cambios' presionado.");

    	    try {
    	        // Obtener DNI
    	        String dni = request.getParameter("dni");
    	        String cuil = request.getParameter("cuil");
    	        String direccion = request.getParameter("direccion");
    	        String nacionalidad = request.getParameter("nacionalidad");
    	        System.out.println("DNI recibido: " + dni);
    	        
    	        // Buscar cliente en la lista
    	        Cliente auxCliente = (Cliente) listaClientes1.stream().filter(x -> x.getDni().equals(dni)).findFirst().orElse(null);
    	        System.out.println("Cliente encontrado: " + (auxCliente != null ? auxCliente.getNombre() : "No encontrado"));

    	        // Validar ID de provincia y localidad
    	        String provinciaStr = request.getParameter("provincia");
    	        String localidadStr = request.getParameter("localidad");
    	        System.out.println("ID de provincia recibido: " + provinciaStr);
    	        System.out.println("ID de localidad recibido: " + localidadStr);

    	        if (provinciaStr == null || provinciaStr.isEmpty()) {
    	            throw new Exception("ID de provincia no recibido.");
    	        }
    	        if (localidadStr == null || localidadStr.isEmpty()) {
    	            throw new Exception("ID de localidad no recibido.");
    	        }

    	        int idProvincia = Integer.parseInt(provinciaStr);
    	        int idLocalidad = Integer.parseInt(localidadStr);

    	        // Obtener la provincia y localidad
    	        Provincia provincia = provinciaNegocioImpl.get(idProvincia);
    	        Localidad localidad = localidadNegocioImpl.get(idLocalidad);
    	        System.out.println("Provincia: " + provincia.getNombre());
    	        System.out.println("Localidad: " + localidad.getNombre());

    	        // Verificar si la localidad pertenece a la provincia
    	        if (localidad.getProvincia().getIdprovincia() != provincia.getIdprovincia()) {
    	            throw new Exception("La localidad no pertenece a la provincia seleccionada.");
    	        }
    	        System.out.println("Localidad pertenece a la provincia seleccionada.");

    	        // Validar el nombre
    	        String regex = "[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+";
    	        Pattern pattern = Pattern.compile(regex);
    	        String nombre = request.getParameter("nombre");
    	        Matcher matcher = pattern.matcher(nombre);
    	        System.out.println("Nombre recibido: " + nombre);

    	        if (!matcher.matches()) {
    	            throw new Exception("El nombre debe contener exclusivamente caracteres alfabéticos.");
    	        }
    	        
    	        // Obtener la fecha de nacimiento como String desde el formulario
    	        String fechaNacimientoStr = request.getParameter("fechanacimiento");
    	        System.out.println("Fecha de nacimiento recibida: " + fechaNacimientoStr);

    	        // Validar y convertir la fecha de nacimiento
    	        Date fechaNacimiento = null;
    	        if (fechaNacimientoStr != null && !fechaNacimientoStr.isEmpty()) {
    	            try {
    	                // Convertir la cadena recibida a java.util.Date
    	                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	                java.util.Date parsedDate = format.parse(fechaNacimientoStr);

    	                // Si es necesario, convertirlo a java.sql.Date
    	                fechaNacimiento = new java.sql.Date(parsedDate.getTime());
    	            } catch (ParseException e) {
    	                // Si no se puede parsear la fecha, lanzar excepción
    	                throw new Exception("Fecha de nacimiento inválida.");
    	            }
    	        } else {
    	            throw new Exception("Fecha de nacimiento no recibida.");
    	        }
    	        
    	        
    	        // Crear el cliente modificado
    	        
    	        String apellido = request.getParameter("apellido");
    	        char sexo = request.getParameter("sexo").charAt(0); 
    	        String correo = request.getParameter("correo");
    	        String telefono = request.getParameter("telefons");
    	        System.out.println("DNI recibido: " + request.getParameter("dni"));
    	        System.out.println("Cuil recibido: " + request.getParameter("cuil"));
    	        System.out.println("Nombre recibido: " + request.getParameter("nombre"));
    	        System.out.println("Apellido recibido: " + request.getParameter("apellido"));
    	        System.out.println("Sexo recibido: " + request.getParameter("sexo"));
    	        System.out.println("Fecha de nacimiento recibida: " + fechaNacimiento);
    	        System.out.println("Nacionalidad recibida: " + request.getParameter("nacionalidad"));
    	        System.out.println("ID Localidad recibido: " + request.getParameter("localidad"));
    	        System.out.println("ID Provincia recibido: " + request.getParameter("provincia"));
    	        System.out.println("Correo recibido: " + request.getParameter("correo"));
    	        System.out.println("Telefono recibido: " + request.getParameter("telefono"));
    	        System.out.println("Direccion recibido: " + request.getParameter("direccion"));

    	        // Validar que todos los parámetros sean correctos
    	        if (dni != null && cuil!=null && nombre != null && direccion != null && apellido != null && sexo != 0 && nacionalidad != null && fechaNacimiento != null && localidad != null && correo != null && telefono != null) {
    	            Cliente modCliente = new Cliente(
    	                dni,
    	                cuil,
    	                nombre,
    	                apellido,
    	                sexo,
    	                nacionalidad,
    	                fechaNacimiento,
    	                direccion,
    	                localidad,
    	                correo,
    	                telefono,
    	                new Usuario()
    	            );
    	            // Actualizar cliente
        	        Boolean cli_modificado = clienteNegocioImpl.update(modCliente);
        	        System.out.println("Cliente modificado: " + cli_modificado);
    	            System.out.println("Cliente modificado correctamente...");
    	            // Obtener usuario y actualizar contraseña si es necesario
    	            Cliente cliente = clienteNegocioImpl.get(dni);
    	            Usuario modUser = usuarioNegocioImpl.get(cliente.getUsuario().getIdusuario());
    	            System.out.println("Usuario encontrado: " + (modUser != null ? modUser.getIdusuario() : "No encontrado"));
    	            
    	            String nuevaContrasena = request.getParameter("contrasena");
    	            if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
    	            	modUser.setContraseña(nuevaContrasena);
    	            }
    	            Boolean user_modificado = usuarioNegocioImpl.update(modUser);
    	            System.out.println("Usuario modificado: " + user_modificado);
    	            
    	            // Verificar actualización
    	            if (cli_modificado && user_modificado) {
    	            	request.setAttribute("respuesta", "Los cambios se guardaron exitosamente");
    	            } else {
    	            	throw new Exception("No se pudo guardar todos los cambios.");
    	            }
    	        } else {
    	            System.out.println("Error: Uno o más parámetros son inválidos o nulos.");
    	        }
    	       
    	        

    	    } catch (Exception e) {
    	        System.out.println("Error al guardar los cambios: " + e.getMessage());
    	        e.printStackTrace(); // Esto ayudará a ver más detalles del error
    	        request.setAttribute("respuesta", "Error al guardar los cambios: " + e.getMessage());
    	    } finally {
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
	               String contraseña = request.getParameter("contrasena");
	               TipoUsuarioNegocioImpl tipoUsuarioNegocioImpl = new TipoUsuarioNegocioImpl();
	               TipoUsuario tipoUsuario = tipoUsuarioNegocioImpl.get(2); //Valor por cliente       
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
            // Asegúrate de que la fecha no es nula
            if (fechaNacVal == null) {
                throw new Exception("La fecha de nacimiento no puede ser nula.");
            }
            
            // Convertir a java.sql.Date
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

