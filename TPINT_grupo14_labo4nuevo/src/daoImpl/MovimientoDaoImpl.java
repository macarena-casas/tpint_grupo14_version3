package daoImpl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dao.MovimientoDao;
import entidad.Cuenta;
import entidad.Movimiento;

public class MovimientoDaoImpl implements MovimientoDao {
	private static final String get = "SELECT * FROM movimientos WHERE numero_cuenta = ?";
	private Conexion conexion;

	public MovimientoDaoImpl() {
		this.conexion = new Conexion();
	}

	@Override
	public ArrayList<Movimiento> listporNumeroCuenta(int numero_cuenta) {
		ArrayList<Movimiento> listMovimiento = new ArrayList<>();
		try {
			conexion.setearConsulta(get);
			conexion.setearParametros(1, numero_cuenta);
			ResultSet resultSet = conexion.ejecutarLectura();

			while (resultSet.next()) {
				int movimientoId = resultSet.getInt("movimiento_id");
				Date fecha = resultSet.getDate("fecha");
				String detalle= resultSet.getString("detalle");
				BigDecimal importe = resultSet.getBigDecimal("importe");
				String tipomovimiento = resultSet.getString("tipo_movimiento");
				
				Cuenta cuenta = new Cuenta();
				cuenta.setNroCuenta(resultSet.getInt("numero_cuenta"));
			

				Movimiento movimiento = new Movimiento(movimientoId, cuenta, fecha,detalle, importe, tipomovimiento);
				listMovimiento.add(movimiento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return listMovimiento;
	}

}
