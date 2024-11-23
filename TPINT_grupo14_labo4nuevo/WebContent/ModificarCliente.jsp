<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidad.Cliente"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entidad.Localidad"%>
<%@ page import="entidad.Provincia"%>
<%@ page import="entidad.Pais"%>

<%
	Cliente auxCliente = (Cliente) request.getAttribute("ClienteDetalle");
	ArrayList<Localidad> localidades = (ArrayList<Localidad>) request.getAttribute("Lista_Localidades");
	ArrayList<Provincia> provincias = (ArrayList<Provincia>) request.getAttribute("Lista_Provincias");
	ArrayList<Pais> paises = (ArrayList<Pais>) request.getAttribute("Lista_Paises");

	String respuesta = null;
	if (session != null && session.getAttribute("respuesta") != null) {
		respuesta = (String) session.getAttribute("respuesta");
		session.removeAttribute("respuesta");
%>

<script> 
	  alert('<%=respuesta%>');
	</script>

<%
	respuesta = null;
	}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.bgLeft {
	background: linear-gradient(45deg, mediumseagreen, seagreen, forestgreen,
		forestgreen, seagreen, mediumseagreen);
	background-size: cover;
}

.bgRight {
	background: linear-gradient(45deg, mediumseagreen, seagreen, forestgreen,
		forestgreen, seagreen, mediumseagreen);
	background-size: cover;
}
</style>

<script>

    function validarFormulario(event) {
        const contrasena = document.getElementById('contrasena').value;
        const confirmarContrasena = document.getElementById('ConfirmarContrasena').value;
        const dniInput = document.getElementById('dni');
        const dniValue = dniInput.value.trim();
        const cuilInput = document.getElementById('cuil');
        const cuilValue = cuilInput.value.trim();
        const telefonoInput = document.getElementById('telefono');
        const telefonoValue = telefonoInput.value.trim();

        
        const nombreInput = document.getElementById('nombre');
        const nombreValue = nombreInput.value.trim();
        const usuarioInput = document.getElementById('usuario').value;
        const apellidoInput = document.getElementById('apellido');
        const apellidoValue = apellidoInput.value.trim();

        const textoValido = /^[a-zA-Z\s]+$/;
        const textoValidoUsuario = /^[a-zA-Z0-9]+$/;
        
        if (contrasena !== confirmarContrasena) {
            alert('Las contraseñas no coinciden.');
            return false; 
        }
        
        if (!textoValidoUsuario.test(usuarioInput) || usuarioInput === '') {
            alert('Usuario incorrecto. El usuario no debe contener espacios.');
            usuarioInput.focus();
            event.preventDefault();
            return false;
        }
        
        if (!textoValido.test(nombreValue) || nombreValue === '') {
            alert('Nombre incorrecto. El nombre no debe contener números ni estar vacío.');
            nombreInput.focus();
            event.preventDefault();
            return false;
        }

        if (!textoValido.test(apellidoValue) || apellidoValue === '') {
            alert('Apellido incorrecto. El apellido no debe contener números ni estar vacío.');
            apellidoInput.focus();
            event.preventDefault();
            return false;
        }
        
  
        
        
        if (telefonoValue.length > 15 || telefonoValue.length < 8) {
            alert('Telefono incorrecto. El telefono ingresado debe contener de 8 a 15 dígitos.');
            telefonoInput.focus(); 
            event.preventDefault(); 
            return false;
        }
        
        
        const confirmacion = confirm('¿Desea agregar el cliente?');
        if (confirmacion) {
            return true; 
        } else {
        	event.preventDefault();
            return false; 
        }
     
    }
</script>
<head>

</head>
<body>
	<br>
	<br>
	<title>Modificar Cliente</title>


	<jsp:include page="NavBar.jsp" />
	<div class="d-flex">
		<div class="w-10 bgLeft pt-5"></div>
		<div class="w-90 p-4 bg-white bg-opacity-80 pt-5">
			<div class="container mx-auto px-4 py-8">
				<div class="w-66 mx-auto">
					<center>
						<h2 class="text-3xl font-medium title-font mb-2 text-gray-900">Modificar
							Cliente</h2>
					</center>
					<br>
					<form action="ServletAdminCliente" method="post"
						onsubmit="return validarFormulario(event);">
						<div class="row">
							<center>
								<div class="col-12 border-bottom pb-4">
									<h3 class="text-lg font-medium title-font mb-2 text-gray-700">Datos
										Personales</h3>
								</div>
							</center>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="nombre">Nombre:</label> <input
									type="text" id="nombre" name="nombre"
									value="<%=auxCliente.getNombre()%>" maxlength="100"
									class="form-control" required>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="apellido">Apellido:</label> <input
									type="text" id="apellido" name="apellido"
									value="<%=auxCliente.getApellido()%>" maxlength="100"
									class="form-control" required>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="dni">DNI:</label> <input
									type="number" id="dni" name="dni" class="form-control"
									value="<%=auxCliente.getDni()%>" readonly>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="cuil">CUIL:</label> <input
									type="number" id="cuil" name="cuil"
									value="<%=auxCliente.getCuil()%>" class="form-control"
									maxlength="13" readonly>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="sexo">Sexo:</label> <select
									id="sexo" name="sexo" class="form-control">
									<option value="M"
										<%="M".equals(String.valueOf(auxCliente.getSexo())) ? "selected" : ""%>>M</option>
									<option value="F"
										<%="F".equals(String.valueOf(auxCliente.getSexo())) ? "selected" : ""%>>F</option>
									<option value="X"
										<%="X".equals(String.valueOf(auxCliente.getSexo())) ? "selected" : ""%>>X</option>
								</select>



							</div>

							<div class="col-md-6 mb-4">
								<label class="form-label" for="nacionalidad1">Nacionalidad:</label>
								<input type="text" id="nacionalidad1" maxlength="100"
									name="nacionalidad1" value="<%=auxCliente.getNacionalidad()%>"
									class="form-control" readonly>

							</div>

							<div class="col-md-6 mb-4">
								<label class="block text-gray-700 text-sm font-bold mb-2"
									for="fechanacimiento">Fecha de Nacimiento:</label> <input
									type="date" max="2006-01-01" min="1930-01-01"
									id="fechanacimiento" name="fechanacimiento"
									value="<%=auxCliente.getFechaNacimiento()%>" class="date"
									readonly max="2005-01-01">
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="direccion">Dirección:</label> <input
									type="text" id="direccion" maxlength="100" name="direccion"
									value="<%=auxCliente.getDireccion()%>" class="form-control"
									maxlength="13" required>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="localidad">Localidad:</label> <select
									id="localidad" name="localidad" class="form-control" required>

									<div
										class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
										<i class="bi bi-arrow-down-short"></i>
									</div>
									<%
										for (Localidad localidad : localidades) {
									%>
									<%
										if (localidad.getIdlocalidad() == auxCliente.getLocalidad().getIdlocalidad()) {
									%>
									<option selected value=<%=localidad.getIdlocalidad()%>><%=localidad.getNombre()%></option>
									<%
										} else {
									%>
									<option value=<%=localidad.getIdlocalidad()%>><%=localidad.getNombre()%></option>
									<%
										}
									%>
									<%
										}
									%>
								</select>
							</div>
							<div class="col-md-6 mb-4">
								<label class="block text-gray-700 text-sm font-bold mb-2"
									for="provincia">Provincia:</label> <select id="provincia"
									name="provincia" class="form-control" required>

									<div
										class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
										<i class="bi bi-arrow-down-short"></i>

									</div>
									<%
										for (Provincia provincia : provincias) {
									%>
									<%
										if (provincia.getIdprovincia() == auxCliente.getLocalidad().getProvincia().getIdprovincia()) {
									%>
									<option value=<%=provincia.getIdprovincia()%> selected><%=provincia.getNombre()%></option>
									<%
										} else {
									%>
									<option value=<%=provincia.getIdprovincia()%>><%=provincia.getNombre()%></option>
									<%
										}
									%>
									<%
										}
									%>
								</select>

							</div>

							<div class="col-md-6 mb-4">
								<label class="form-label" for="telefono">Teléfono:</label> <input
									type="number" id="telefono" name="telefono"
									value="<%=auxCliente.getTelefono()%>" class="form-control"
									required>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="email">Email:</label> <input
									type="email" id="email" maxlength="100" name="email"
									value="<%=auxCliente.getCorreo()%>" class="form-control"
									required>
							</div>
						</div>
						<center>
							<h3 class="text-lg font-medium title-font mb-2 text-gray-700">Datos
								de la Cuenta</h3>
							<div class="mb-4">
								<label class="block text-gray-700 text-sm font-bold mb-2"
									for="usuario">Nombre de Usuario:</label> <input type="text"
									id="usuario" maxlength="15" name="usuario"
									value="<%=auxCliente.getUsuario().getNombreusuario()%>"
									class="form-control" readonly>
							</div>
							<div class="mb-4">
								<label class="block text-gray-700 text-sm font-bold mb-2"
									for="contrasena">Contraseña:</label> <input type="password"
									id="contrasena" maxlength="15" minlength="8" name="contrasena"
									value="<%=auxCliente.getUsuario().getContraseña()%>"
									class="form-control" required>
							</div>
							<div class=" mb-4">
								<label class="block text-gray-700 text-sm font-bold mb-2"
									for="ConfirmarContrasena">Confirmar contraseña:</label> <input
									type="password" id="ConfirmarContrasena" maxlength="15"
									minlength="8" name="ConfirmarContrasena"
									value="<%=auxCliente.getUsuario().getContraseña()%>"
									class="form-control" required>
							</div>
				</div>
				</center>
				<center>

					<button type="submit" id="btnGuardarCambios"
						name="btnGuardarCambios" value="guardarCambios"
						class="btn btn-outline-success ">Modificar</button>
				</center>
				</form>
				<div class="d-flex justify-content-end w-100 mt-4">
					<form action="ServletAdminCliente" method="get">

						<button type="submit" name="btnAtras1" value="Atras"
							class="btn btn-outline-success text-dark">Atrás</button>
					</form>
				</div>
				<%
					String message = (String) request.getAttribute("message");
					if (message != null) {
				%>
				<script>
                            alert('<%=message%>');
				</script>
				<%
					}
				%>
			</div>
		</div>
	</div>

	<jsp:include page="Footer.jsp" />

</body>
</html>