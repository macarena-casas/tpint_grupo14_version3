package daoImpl;

import java.math.BigDecimal;
import java.sql.SQLException;

import dao.TransferenciaDao;

public class TransferenciaDaoImpl implements TransferenciaDao {

	private static final String call = "call SP_TRANSFERENCIAS (?,?,?,?)";
	private Conexion conexion;

	public TransferenciaDaoImpl() {
		this.conexion = new Conexion();
	}

	@Override
	public int insert(String cbuOrigen, String cbuDestino, String detalle, BigDecimal importe) {

		int filasafectadas = 0;

		try {
			conexion.setearConsulta(call);
			conexion.setearParametros(1, cbuOrigen);
			conexion.setearParametros(2, cbuDestino);
			conexion.setearParametros(3, importe);
			conexion.setearParametros(4, detalle);
 
			filasafectadas=conexion.ejecutarAccion();
			
			System.out.println("Filas afectadas: " + filasafectadas);
			
			
			/*if (conexion.ejecutarAccion() > 0) {
				conexion.commit();
				filasafectadas = 0; // Operación exitosa
			}*/
		} catch (SQLException e) {
			e.printStackTrace();

			if (e.getSQLState().equals("45000")) {
				filasafectadas = 45000; // Saldo insuficiente
			}
			conexion.rollback();

		} finally {
			conexion.cerrarConexion();
		}
		return filasafectadas;

	}
}