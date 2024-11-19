package daoImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.CuotaDao;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.Cuota;
import entidad.Prestamo;
import entidad.TipoPrestamo;

public class CuotaDaoImpl implements CuotaDao {

	private static final String call = "call SP_PAGO_CUOTA(?,?)";
	private static final String list = "SELECT * FROM cuotas WHERE prestamo_id =?";

	private Conexion conexion;

	public CuotaDaoImpl() {
		this.conexion = new Conexion();
	}

	@Override
	public ArrayList<Cuota> listPorIdPrestamo(Prestamo prestamo) {
		ArrayList<Cuota> cuotalistPrestamos = new ArrayList<>();
		try {
			conexion.setearConsulta(list);
			conexion.setearParametros(1, prestamo.getIdPrestamo());
			ResultSet resultSet = conexion.ejecutarLectura();

			while (resultSet.next()) {
				int cuotaId = resultSet.getInt("cuota_id");
				Date fechavencimiento = resultSet.getDate("fecha_vencimiento");
				Date fechapago = resultSet.getDate("fecha_pago");
				int nrocuota = resultSet.getInt("numero_cuota");
				BigDecimal importe = resultSet.getBigDecimal("importe");
				int prestamoId = resultSet.getInt("prestamo_id");

				Cuota cuota = new Cuota(cuotaId, nrocuota, fechavencimiento, importe, fechapago);
				cuotalistPrestamos.add(cuota);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return cuotalistPrestamos;
	}

	
	public int update(int idCuota, String nroCuenta) {
	    int resultado = 0;
	    Conexion conexion = new Conexion(); 
	    String call = "{CALL SP_PAGO_CUOTA(?, ?)}";

	    try {
	        conexion.setearConsulta(call);
	        conexion.setearParametros(1, idCuota);
	        conexion.setearParametros(2, nroCuenta);

	        if (conexion.ejecutarAccion() > 0) {
	            conexion.commit();
	            resultado = 0; // Operación exitosa
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	  
	            if (e.getSQLState().equals("45000")) {
	                resultado = 45000; // Saldo insuficiente
	            }
	            conexion.rollback();
	         
	    } finally {
	        conexion.cerrarConexion();
	    }
	    return resultado;
	}

	

}
