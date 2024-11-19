package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		if (request.getParameter("btnAdminCuentas") != null) {

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
		}
		if (request.getParameter("btnModificar") != null) {
			// captura el numero de cuenta
			String auxNro = request.getParameter("cuentaId");
			int nroCuenta = Integer.parseInt(auxNro);
			// busca la cuenta seleccionada y la guarda para listar en modificar
			Cuenta auxCuenta = listaCuentas.stream().filter(x -> (x.getNroCuenta()) == nroCuenta).findFirst()
					.orElse(null);
			request.setAttribute("cuenta", auxCuenta);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCuenta.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (request.getParameter("btnModificarCuenta") != null) {
			String aux = request.getParameter("saldo");
			BigDecimal saldo = new BigDecimal(aux);
			// primero compara el saldo ingresado para ver que sea mayor que 0 si es menor
			// redirecciona nuevamente a modificar con la cuenta a modificar
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
			// a la cuenta que se esta modificando se le setea el tipo de cuenta y el nuevo
			// saldo
			Boolean modificado = cuentaNegocioImpl.update(Integer.parseInt(request.getParameter("nroCuenta")),
					request.getParameter("tipoCuenta"), saldo);

			if (modificado) {
				session.setAttribute("respuesta", "Los cambios fueron guardados con éxito");
			} else {
				session.setAttribute("respuesta", "Error. Los cambios no han sido guardados");
			}
			// regresa a listar cuentas
			listaCuentas = cuentaNegocioImpl.list();
			request.setAttribute("listaCuentas", listaCuentas);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuentas.jsp");
			dispatcher.forward(request, response);
		}
	}

}
