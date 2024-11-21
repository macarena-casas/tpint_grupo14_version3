package servlets;

import java.io.IOException;
import java.math.BigDecimal;
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

import entidad.Cliente;
import entidad.Cuenta;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.CuentaNegocioImpl;

@WebServlet("/ServletAdminCuentas")
public class ServletAdminCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<Cuenta> listaCuentas = new ArrayList<Cuenta>();
	private CuentaNegocioImpl cuentaNegocioImpl = new CuentaNegocioImpl();
	private ClienteNegocioImpl clienteNegocioImpl = new ClienteNegocioImpl();

	public ServletAdminCuentas() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("btnAdminCuentas") != null || request.getParameter("btnAtras2") != null) {

			listaCuentas = cuentaNegocioImpl.list();
			request.setAttribute("listaCuentas", listaCuentas);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuentas.jsp");
			dispatcher.forward(request, response);
		} else if (request.getParameter("btnEliminar") != null) {
			int nrodecuenta = Integer.parseInt(request.getParameter("cuentaId").toString());

			if (cuentaNegocioImpl.delete(nrodecuenta)) {
				request.setAttribute("listaCuentas", cuentaNegocioImpl.list());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuentas.jsp");
				dispatcher.forward(request, response);
			}
		} else if (request.getParameter("btnDetalle") != null) {
			String nroCuentaStr = request.getParameter("cuentaId");
			int nroCuenta = Integer.parseInt(nroCuentaStr);

			Cuenta cuenta = cuentaNegocioImpl.get(nroCuenta); 

			
			if (cuenta != null) {
				
				request.setAttribute("nombreCliente", cuenta.getCliente().getNombre());
				request.setAttribute("apellidoCliente", cuenta.getCliente().getApellido());

				
				request.setAttribute("numerodecuenta", cuenta.getNroCuenta());
				request.setAttribute("tipoDeCuenta", cuenta.getTipoCuenta());
				request.setAttribute("fechaCreacion", cuenta.getFechaCreacion());
				request.setAttribute("cbu", cuenta.getCbu());
				request.setAttribute("saldo", cuenta.getSaldo());
			} else {
				request.setAttribute("error", "No se encontró la cuenta seleccionada.");
			}

			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/DetalleCuenta.jsp");
			dispatcher.forward(request, response);
		}

		else if (request.getParameter("btnModificar") != null) {
			
			String auxNro = request.getParameter("cuentaId");
			int nroCuenta = Integer.parseInt(auxNro);
			
			Cuenta auxCuenta = listaCuentas.stream().filter(x -> (x.getNroCuenta()) == nroCuenta).findFirst()
					.orElse(null);
			request.setAttribute("cuenta", auxCuenta);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCuenta.jsp");
			dispatcher.forward(request, response);
		}
		if (request.getParameter("btnAgregarCuenta") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCuenta.jsp");
			dispatcher.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (request.getParameter("btnModificarCuenta") != null) {
			String aux = request.getParameter("saldo");
			BigDecimal saldo = new BigDecimal(aux);
			
			if (saldo.compareTo(BigDecimal.ZERO) < 0) {
				session.setAttribute("respuesta", "No se aceptan saldos negativos");
				String auxNro = request.getParameter("cuentaId");
				int nroCuenta = Integer.parseInt(auxNro);
				Cuenta auxCuenta = listaCuentas.stream().filter(x -> (x.getNroCuenta()) == nroCuenta).findFirst()
						.orElse(null);
				request.setAttribute("cuenta", auxCuenta);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCuenta.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
			Boolean modificado = cuentaNegocioImpl.update(Integer.parseInt(request.getParameter("nroCuenta")),
					request.getParameter("tipoCuenta"), saldo);

			if (modificado) {
				session.setAttribute("respuesta", "Los cambios fueron guardados con éxito");
			} else {
				session.setAttribute("respuesta", "Error. Los cambios no han sido guardados");
			}
			
			listaCuentas = cuentaNegocioImpl.list();
			request.setAttribute("listaCuentas", listaCuentas);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuentas.jsp");
			dispatcher.forward(request, response);
		}

		else if (request.getParameter("btnAgregarCuentaNueva") != null) {

			String dni = request.getParameter("dni");
			List<Cliente> listaCliente = clienteNegocioImpl.list();
			List<Cliente> auxListaCliente = listaCliente.stream().filter(x -> x.getDni().equals(dni))
					.collect(Collectors.toList());

			listaCuentas = cuentaNegocioImpl.list();
			List<Cuenta> auxLista = listaCuentas.stream().filter(x -> x.getCliente().getDni().equals(dni))
					.collect(Collectors.toList());

			

			if (auxListaCliente == null || auxListaCliente.isEmpty()) {

				session.setAttribute("respuesta", "Cliente inexistente");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCuenta.jsp");
				dispatcher.forward(request, response);
				return;

			} else if (auxLista.size() == 3) {
				session.setAttribute("respuesta",
						"El cliente nro " + auxLista.get(0).getCliente().getNombre() + " "
								+ auxLista.get(0).getCliente().getApellido()
								+ " ya ha alcanzado su cantidad maxima de cuentas (3 cuentas por cliente)");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCuenta.jsp");
				dispatcher.forward(request, response);
				return;
			} else {
				System.out.println("inserto");
				String tipoCuenta = request.getParameter("tipoCuenta");
				int id = cuentaNegocioImpl.getLastId() + 1;

				String cbu = String.format("%022d", id);
				

				Cuenta newCuenta = new Cuenta();
				Cliente cliente = clienteNegocioImpl.get(dni);
				newCuenta.setCliente(cliente);
				newCuenta.setCbu(cbu);
				newCuenta.setTipoCuenta(tipoCuenta);

				boolean inserto = cuentaNegocioImpl.insert(newCuenta);
				System.out.println(inserto);
				if (inserto == false) {
					session.setAttribute("respuesta", "Error. La cuenta no se pudo agregar");
				} else {
					session.setAttribute("respuesta", "La cuenta para el DNI: " + dni + " fue agregada exitosamente");
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher("/AgregarCuenta.jsp");
				dispatcher.forward(request, response);
			}
		}

	}

}
