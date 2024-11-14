package daoImpl;

import java.sql.ResultSet;

import java.util.ArrayList;

import dao.PaisDao;
import entidad.Pais;

public class PaisDaoImpl implements PaisDao {
	private static final String list = "SELECT * FROM paises";
	private static final String get = "SELECT * FROM paises WHERE pais_id = ?";

	@Override
	public Pais get(int idPais) {
		Conexion conexion = new Conexion();
		Pais pais = null;

		try {
			conexion.setearConsulta(get);
			conexion.setearParametros(1, idPais);
			ResultSet resultSet = conexion.ejecutarLectura();

			if (resultSet.next()) {
				String nombrePais = resultSet.getString("nombre");
				int paisId = resultSet.getInt("pais_id");
				pais = new Pais(paisId, nombrePais);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}

		return pais;
	}

	@Override
	public ArrayList<Pais> list() {
		Conexion conexion = new Conexion();
		ArrayList<Pais> list_paises = new ArrayList<Pais>();
		try {
			conexion.setearConsulta(list);
			ResultSet result_set = conexion.ejecutarLectura();
			while(result_set.next()) {
				list_paises.add(new Pais(result_set.getInt("pais_id"),result_set.getString("nombre")));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			conexion.cerrarConexion();
		}
		return list_paises;

	}
}
