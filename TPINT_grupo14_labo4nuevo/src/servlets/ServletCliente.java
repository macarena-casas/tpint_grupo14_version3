package servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daoImpl.ClienteDaoImpl;
import daoImpl.Conexion;
import entidad.Cliente;
import entidad.Cuenta;
import negocioImpl.CuentaNegocioImpl;
//import negocioImpl.MovimientosNegocioImpl;

/**
 * Servlet implementation class ServletCliente
 */
@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CuentaNegocioImpl cuentaNegocioImpl = new CuentaNegocioImpl();
	 private ArrayList<Cuenta> cuentasPorCliente = new ArrayList<Cuenta>();
	//private MovimientosNegocioImpl movimientosNegocioImpl = new MovimientosNegocioImpl();
      

   public ServletCliente() {
       super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String dni = request.getParameter("dni"); // Obtener DNI de los parámetros de la URL

       // Lógica para obtener los datos del cliente usando el DNI
       ClienteDaoImpl clienteDao = new ClienteDaoImpl();
       Cliente cliente = clienteDao.get(dni); // Ejemplo de método que obtiene el cliente
       // Guardar los datos en la solicitud (request)
       request.setAttribute("nombreCliente", cliente.getNombre());
       request.setAttribute("apellidoCliente", cliente.getApellido());
       request.setAttribute("dni", cliente.getDni());
       request.setAttribute("cuil", cliente.getCuil());
       request.setAttribute("sexo", cliente.getSexo());
       request.setAttribute("nacionalidad", cliente.getNacionalidad());
       request.setAttribute("fechanacimiento", cliente.getFechaNacimiento());
       request.setAttribute("direccion", cliente.getDireccion());
       request.setAttribute("correo", cliente.getCorreo());
       request.setAttribute("telefono", cliente.getTelefono());

       // Redirigir a la página JSP
       request.getRequestDispatcher("/DetalleCliente.jsp").forward(request, response);
   }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    	    
    	    
    	    request.getRequestDispatcher("/Login.jsp").forward(request, response);
    	    
    	}

    	if (session != null) {
    	    
    	    Integer userId = (Integer) session.getAttribute("userId");
    	    if (userId != null) {
    	      
    	        
    	        Conexion conexion = new Conexion();
    	        try {
    	            
    	           
    	            conexion.setearConsulta("SELECT * FROM clientes WHERE usuario_id = ?");
    	            conexion.setearParametros(1, userId);
    	            ResultSet rs = conexion.ejecutarLectura();
    	            
    	            if (rs.next()) {
    	                
    	                
    	                
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

    	               
    	               
    	                request.getRequestDispatcher("DetalleCliente.jsp").forward(request, response);
    	               
    	            } else {
    	                
    	                
    	                request.setAttribute("error", "No se encontró la cuenta asociada al usuario.");
    	                
    	            }

    	            rs.close();
    	            conexion.cerrarConexion();

    	        } catch (Exception e) {
    	            
    	            
    	            e.printStackTrace();
    	           
    	        }
    	    }
    	} else {
    	  
    	   
    	    response.sendRedirect("Login.jsp");  
    	}
	}

}
