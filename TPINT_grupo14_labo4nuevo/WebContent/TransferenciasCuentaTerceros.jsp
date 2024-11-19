<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entidad.*"%>
<%
	ArrayList<Cuenta> cuentasPorTerceros = (ArrayList<Cuenta>) request.getAttribute("Lista_Cuentas_cliente");
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
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Transferencias</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
.bgLeft {
	background: linear-gradient(45deg, lightgreen, limegreen, darkgreen, limegreen,
		mediumseagreen);
	background-size: cover;
}

.bgRight {
	background: linear-gradient(45deg, lightgreen, limegreen, darkgreen, limegreen,
		lightgreen);
	background-size: cover
}

.menu-container {
	background-image: url('imagenes/fondo.png');
	padding: 2rem;
	border-radius: 0.5rem;
	margin-top: 20%;
}
</style>
</head>
<body>
	<jsp:include page="NavBar.jsp" />
	<div class="d-flex">
		<div class="col-1 bgLeft pt-5"></div>
		<div class="col-10 p-4 bg-white bg-opacity-80 pt-5">
			<div
				class="content-background min-vh-100 d-flex align-items-center justify-content-center">


				<section
					class="text-gray-600 body-font min-vh-100 d-flex align-items-center justify-content-center w-80"
					style="min-width: 550px;">
					<div
						class="container px-5 py-5 mx-auto d-flex flex-column align-items-center">
						<div class="menu-container w-100 md:w-50 lg:w-33">
							<h2 class="text-center font-weight-bold mb-4">Transferencias</h2>
							<br>
							<form class="d-flex flex-column align-items-center w-100"
								action="ServletTransferenciasClientes" method="post"
								onsubmit="return confirmacion();">
								<div class="form-group ancho">
									<label for="CuentaOrigen" class="font-weight-bold">CBU
										de Origen:</label> <select id="CuentaOrigen" name="CuentaOrigen"
										class="form-control">
										<%
											if (cuentasPorTerceros != null && !cuentasPorTerceros.isEmpty()) {
										%>
										<%
											for (Cuenta cuenta : cuentasPorTerceros) {
										%>
										<option value="<%=cuenta.getCbu()%>">
											<%=cuenta.getCbu()%></option>
										<%
											}
										%>
										<%
											} else {
										%>
										<option value="">No tiene cuentas disponibles</option>
										<%
											}
										%>
									</select>
								</div>

								<div class="form-group ancho">
									<label for="CuentaDestino" class="font-weight-bold">CBU
										de Destino:</label> <input type="number" required id="CuentaDestino"
										max="9999999999999999999999" name="CuentaDestino"
										class="form-control">
								</div>
								<div class="form-group ancho">
									<label for="detalleTransferencia2" class="font-weight-bold">Detalle:</label>
									<input type="text" required minlength="3" maxlength="150"
										id="detalleTransferencia2" name="detalleTransferencia2"
										class="form-control">
								</div>
								<div class="form-group ancho">
									<label for="importe" class="font-weight-bold">Importe:</label>
									<input required type="number" max="9999999999" step="any"
										min="1" id="importe" name="importe" class="form-control">
								</div>
								<div class="d-flex flex-column align-items-center w-100">
									<button type="submit" name="btnTransferir2"
										class="btn btn-outline-success text-dark "><strong>Transferir</strong></button>
								</div>
							</form>
						</div>
						<br>
						<div class="d-flex justify-content-end w-100 mt-4">
							<a href="TransferenciasCliente.jsp" class="btn btn-outline-success text-dark "><strong>Atrás</strong></a>
						</div>

					</div>
				</section>
			</div>
		</div>
		<div class="col-1 bgRight pt-5"></div>
	</div>
	<jsp:include page="Footer.jsp" />

	<script>
		$(document).ready(function() {
			$('#tablaCuentas').DataTable();
		});

		function confirmacion() {
			return confirm('¿Desea realizar la transferencia?');
		}
	</script>
</body>
</html>
