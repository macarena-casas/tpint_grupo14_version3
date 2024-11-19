package negocio;

import java.util.ArrayList;

import entidad.Movimiento;

public interface MovimientosNegocio {

	public ArrayList<Movimiento> listPorNumeroCuenta(int numero_cuenta);
	
}
