package negocio;

import java.math.BigDecimal;
import java.util.ArrayList;

import entidad.Cuenta;

public interface CuentaNegocio {
	public boolean insert(Cuenta cuenta);
	
	public boolean update(int nroCuenta, String tipoCuenta, BigDecimal saldo);
	public ArrayList<Cuenta> listCuentasPorCliente(String dni);
	public boolean delete(int nrodecuenta);
	public Cuenta get(int nroCuenta);
	public ArrayList<Cuenta> list();	
	public int getLastId();
}
