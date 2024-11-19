package dao;

import java.util.ArrayList;

import entidad.Movimiento;

public interface MovimientoDao {

	public ArrayList<Movimiento> listporNumeroCuenta(int numero_cuenta);
	
}
