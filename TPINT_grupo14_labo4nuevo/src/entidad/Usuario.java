package entidad;

public class Usuario {
	
		private int Idusuario;
		private String Nombreusuario;
		private String Contrase�a;
		private TipoUsuario tipoUsuario;

		public Usuario() {
			Idusuario = -1;
			Nombreusuario = "";
			Contrase�a= "";
			tipoUsuario = new TipoUsuario();
		}
		
		public Usuario(int usuarioId, String nombreUsuario, String contrase�a, TipoUsuario tipoUsuario) {
			Idusuario = usuarioId;
			Nombreusuario = nombreUsuario;
			Contrase�a = contrase�a;
			tipoUsuario = tipoUsuario;
		}

		public int getIdusuario() {
			return Idusuario;
		}

		public void setIdusuario(int idusuario) {
			Idusuario = idusuario;
		}

		public String getNombreusuario() {
			return Nombreusuario;
		}

		public void setNombreusuario(String nombreusuario) {
			Nombreusuario = nombreusuario;
		}

		public String getContrase�a() {
			return Contrase�a;
		}

		public void setContrase�a(String contrase�a) {
			Contrase�a = contrase�a;
		}

		public TipoUsuario getTipoUsuario() {
			return tipoUsuario;
		}

		public void setTipoUsuario(TipoUsuario tipoUsuario) {
			this.tipoUsuario = tipoUsuario;
		}
		
		
}

		


