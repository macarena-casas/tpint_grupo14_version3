package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocioImpl.CuentaNegocioImpl;

@WebServlet("/ServletAdminCuentas")
public class ServletAdminCuentas extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletAdminCuentas() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("btnAdminCuentas") != null){
        	CuentaNegocioImpl cuentaNegocioimpl = new CuentaNegocioImpl();
        	request.setAttribute("listaCuentas", cuentaNegocioimpl.list());
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuentas.jsp");
        	dispatcher.forward(request, response);
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
