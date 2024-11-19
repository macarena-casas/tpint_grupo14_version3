package servlets;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cliente;
import entidad.Cuenta;
import entidad.Movimiento;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.MovimientosNegocioImpl;

@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CuentaNegocioImpl cuentaNegocioImpl = new CuentaNegocioImpl();
	private ArrayList<Cuenta> cuentasPorCliente = new ArrayList<Cuenta>();
	private MovimientosNegocioImpl movimientosNegocioImpl = new MovimientosNegocioImpl();
	private ClienteNegocioImpl clientenegocioimpl = new ClienteNegocioImpl();

	public ServletCliente() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (request.getParameter("btnPerfil") != null) {

			Cliente clientePerfil = (Cliente) session.getAttribute("cliente");
			request.setAttribute("Cliente_Perfil", clientePerfil);
			cargarCuentas(request, clientePerfil);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/PerfilCliente.jsp");
			dispatcher.forward(request, response);
			
		} else if (request.getParameter("btnCuentas") != null) {
			int userId = (int) session.getAttribute("userId");
			List<Cuenta> listacuenta = new ArrayList<Cuenta>();
			listacuenta = cuentaNegocioImpl.listCuentasPorCliente(clientenegocioimpl.getPorIdUsuario(userId).getDni());
			request.setAttribute("listadeCuentas", listacuenta);
			request.getRequestDispatcher("/CuentasClientes.jsp").forward(request, response);
			
		} else if (request.getParameter("btnMovimientosCuenta") != null) {
			int nroCuenta = Integer.parseInt(request.getParameter("btnMovimientosCuenta"));
			ArrayList<Movimiento> movimientos = movimientosNegocioImpl.listPorNumeroCuenta(nroCuenta);
			request.setAttribute("movimientos", movimientos);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarMovimientos.jsp");
			dispatcher.forward(request, response);
		}
		String dni = request.getParameter("dni"); // Obtener DNI de los parámetros de la URL
		// Lógica para obtener los datos del cliente usando el DNI
		Cliente cliente = clientenegocioimpl.get(dni); // Ejemplo de método que obtiene el cliente
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
		request.setAttribute("usuario", cliente.getUsuario().getNombreusuario());
		request.setAttribute("contra", cliente.getUsuario().getContraseña());
		// Redirigir a la página JSP
		request.getRequestDispatcher("/DetalleCliente.jsp").forward(request, response);
	}

	private void cargarCuentas(HttpServletRequest request, Cliente cliente) {
		cuentasPorCliente = cuentaNegocioImpl.listCuentasPorCliente(cliente.getDni());
		request.setAttribute("Lista_Cuentas_cliente", cuentasPorCliente);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
		if (session != null) {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId != null) {
				Cliente clienteaux = clientenegocioimpl.getPorIdUsuario(userId);
				request.setAttribute("nombreCliente", clienteaux.getNombre());
				request.setAttribute("apellidoCliente", clienteaux.getApellido());
				request.setAttribute("dni", clienteaux.getDni());
				request.setAttribute("cuil", clienteaux.getCuil());
				request.setAttribute("sexo", clienteaux.getSexo());
				request.setAttribute("nacionalidad", clienteaux.getNacionalidad());
				request.setAttribute("direccion", clienteaux.getDireccion());
				request.setAttribute("correo", clienteaux.getCorreo());
				request.setAttribute("telefono", clienteaux.getTelefono());

				request.getRequestDispatcher("DetalleCliente.jsp").forward(request, response);
			} else {

				response.sendRedirect("Login.jsp");
			}
		}

	}
}
