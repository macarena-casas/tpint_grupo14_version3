package negocioImpl;

import java.util.ArrayList;

import daoImpl.MovimientoDaoImpl;
import entidad.Movimiento;
import negocio.MovimientosNegocio;
//import daoImpl.MovimientoDaoImpl;

public class MovimientosNegocioImpl implements MovimientosNegocio {
	
	private MovimientoDaoImpl movimientoDaoImpl = new MovimientoDaoImpl();
	
	@Override
	public ArrayList<Movimiento> listPorNumeroCuenta(int numero_cuenta) {
		return movimientoDaoImpl.listporNumeroCuenta(numero_cuenta);
	}


	


}
