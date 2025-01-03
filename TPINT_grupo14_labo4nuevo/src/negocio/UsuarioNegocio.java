package negocio;

import java.util.ArrayList;

import entidad.Usuario;

public interface UsuarioNegocio {
	
	public boolean insert(Usuario usuario_a_agregar);
	public boolean update(Usuario usuario_a_modificar);
	
	public Usuario get(int idUsuario);
	public ArrayList<Usuario> list();
	Usuario buscarUsuario(String nombreUsuario);
	boolean existeNombreUsuario(String nombre);
	public int getultimoId();
}

