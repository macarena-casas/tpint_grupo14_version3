<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.DecimalFormat"%>

<%
	String usuario = null;
	String tipoUsuario = null;
	Cliente auxCliente = new Cliente();
	auxCliente = (Cliente) request.getAttribute("Cliente_Perfil");
	ArrayList<Cuenta> cuentasPorCliente = (ArrayList<Cuenta>) request.getAttribute("Lista_Cuentas_cliente");
	
	DecimalFormat formato = new DecimalFormat("#0.00");
	String saldoFormateado;
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Perfil del Cliente</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f9;
	margin: 0;
	padding: 0;
}

.profile-container {
	width: 100%;
	max-width: 600px;
	background-color: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	margin: 80px auto 40px auto; /* Ajuste de margen superior */
}

h2 {
	text-align: center;
	color: #333;
}

.section {
	margin-bottom: 20px;
}

.section h3 {
	color: #4CAF50;
	border-bottom: 2px solid #4CAF50;
	padding-bottom: 5px;
}

.info {
	display: flex;
	justify-content: space-between;
	margin-bottom: 10px;
}

.info label {
	font-weight: bold;
	color: #555;
}

.info span {
	color: #333;
}
</style>
</head>
<body>

	<jsp:include page="NavBar.jsp" />

	<div class="profile-container">
		<h2>Perfil del Cliente</h2>

		<!-- Información Básica -->
		<div class="section">
			<h3>Información Básica</h3>
			<div class="info">
				<label>DNI:</label> <input type="text" id="dni" name="dni"
					class="font-weight-bold border none rounded"
					value="<%=auxCliente.getDni()%>" readonly>

			</div>
			<div class="info">
				<label>Nombre:</label> <input type="text" id="nombre" name="nombre"
					class="font-weight-bold border none rounded"
					value="<%=auxCliente.getNombre()%>" readonly>
			</div>
			<div class="info">
				<label>Apellido:</label> <input type="text" id="apellido"
					name="apellido" class="font-weight-bold border none rounded"
					value="<%=auxCliente.getApellido()%>" readonly>
			</div>
			<div class="info">
				<label>Sexo:</label> <input type="text" id="sexo" name="sexo"
					class="font-weight-bold border none rounded"
					value="<%=auxCliente.getSexo()%>" readonly>

			</div>
			<div class="info">
				<label>Fecha de Nacimiento:</label> <input type="text"
					id="fechanacimiento" name="fechanacimiento"
					class="font-weight-bold border none rounded"
					value="<%=auxCliente.getFechaNacimiento()%>" readonly>
			</div>
			<div class="info">
				<label>Nacionalidad:</label> <input type="text" id="nacionalidad"
					name="nacionalidad" class="font-weight-bold border none rounded"
					value="<%=auxCliente.getNacionalidad()%>" readonly>
			</div>
		</div>

		<!-- Información de la Cuenta -->
		<div class="form-group ancho">
			<div class="section">
				<h3>Información de la Cuenta</h3>

				<table id="tablaCuentas" class="table table-striped table-none-bordered">
					<thead>
						<tr>
							<th>Nro de Cuenta</th>
							<th>Tipo de Cuenta</th>
							<th>CBU</th>
							<th>Saldo Actual</th>
						</tr>
					</thead>
					<tbody>

						<!-- se agregarán dinámicamente las filas de cuentas desde el servidor -->
						<%
							if (cuentasPorCliente != null && !cuentasPorCliente.isEmpty()) {
						%>
						<%
							for (Cuenta cuenta : cuentasPorCliente) {
								saldoFormateado = formato.format(cuenta.getSaldo());
						%>

						<tr>

							<td class="text-center"><%=cuenta.getNroCuenta()%></td>
							<td class="text-center"><%=cuenta.getTipoCuenta()%></td>
							<td class="text-center"><%=cuenta.getCbu()%></td>
							<td class="text-center">$<%=saldoFormateado%></td>
							<td>
								<%
									}
								%> <%
 	} else {
 %>
								<option value="">No tiene cuentas disponibles</option> <%
 	}
 %>
							
					</tbody>
				</table>
			</div>
		</div>
		<!-- Información de Contacto -->
		<div class="section">
			<h3>Información de Contacto</h3>
			<div class="info">
				<label>Correo Electrónico:</label> <input type="text" id="correo"
					name="correo" class="font-weight-bold border none rounded"
					value="<%=auxCliente.getCorreo()%>" readonly>
			</div>
			<div class="info">
				<label>Teléfono:</label> <input type="text" id="telefono"
					name="telefono" class="font-weight-bold border none rounded"
					value="<%=auxCliente.getTelefono()%>" readonly>
			</div>
			<div class="info">
				<label>Dirección:</label> <input type="text" id="direccion"
					name="direccion" class="font-weight-bold border none rounded"
					value="<%=auxCliente.getDireccion()%>" readonly>
			</div>
			<div class="info">
				<label>Localidad:</label> <input type="text" id="localidad"
					name="localidad" class="font-weight-bold border none rounded"
					value="<%=auxCliente.getLocalidad().getNombre()%>" readonly>
			</div>
			<div class="info">
				<label>Provincia:</label> <input type="text" id="provincia"
					name="provincia" class="font-weight-bold border none rounded"
					value="<%=auxCliente.getLocalidad().getProvincia().getNombre()%>"
					readonly>
			</div>
		</div>
	</div>

	<jsp:include page="Footer.jsp" />

</body>
</html>