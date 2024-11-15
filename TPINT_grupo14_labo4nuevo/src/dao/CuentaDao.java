package dao;

import java.math.BigDecimal;
import java.util.ArrayList;

import entidad.Cuenta;

public interface CuentaDao {
	
	public boolean insert(Cuenta cuenta);
	public boolean update(int nroCuenta,String tipoCuenta, BigDecimal saldo);
	
	public ArrayList<Cuenta> listCuentasPorCliente(String dni);
	
	public Cuenta get(int nroCuenta);
	public ArrayList<Cuenta> list();
	public boolean delete(int nrodecuenta);
	public boolean prestamosPorCuenta(int nroCuenta);
	public int getLastId();
}
