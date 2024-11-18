package daoImpl;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sun.jmx.snmp.Timestamp;

import dao.TransferenciaDao;
import entidad.Transferencia;

public class TransferenciaDaoImpl implements TransferenciaDao {

	private Conexion conexion;

	public TransferenciaDaoImpl() {
		this.conexion = new Conexion();
	}
	@Override
	public List<Transferencia> getTodasMisTransferencias(String cbu) {
		List<Transferencia> listaTransf = new ArrayList<Transferencia>();
		String consulta = "select * from transferencias where cbu_origen='"+ cbu +"' or cbu_destino= '"+cbu+"'"; 
		try {
			conexion.setearConsulta(consulta);
			ResultSet rs = conexion.ejecutarLectura();
			while (rs.next()) {
				Transferencia transferencia = new Transferencia();
				transferencia.setCbuDestino(rs.getString("cbu_destino"));
				java.sql.Timestamp timestamp = rs.getTimestamp("fecha");
				Date fechatransf = new Date(timestamp.getTime());
				transferencia.setFecha(fechatransf);
				transferencia.setCbuOrigen(rs.getString("cbu_origen"));
				
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	

}
