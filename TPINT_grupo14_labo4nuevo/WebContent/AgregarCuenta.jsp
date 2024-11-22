<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Agregar Cuenta</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.bgLeft {
	background: linear-gradient(45deg, lightgreen, limegreen, darkgreen, limegreen,
		lightgreen);
	background-size: cover;
}

.bgRight {
	background: linear-gradient(45deg, lightgreen, limegreen, darkgreen, limegreen,
		lightgreen);
	background-size: cover;
}
</style>
</head>

<body>
	<jsp:include page="NavBar.jsp" />
	<%
		String respuesta = null;
		if (session != null && session.getAttribute("respuesta") != null) {
			respuesta = (String) session.getAttribute("respuesta");
			session.removeAttribute("respuesta");
	%>
	<script> alert('<%=respuesta%>');
	</script>
	<%
		respuesta = null;
		}
	%>
	<div class="d-flex">
		<div class="col-1 bgLeft pt-5"></div>
		<div class="col-10 p-4 bg-white bg-opacity-80 pt-5">
			<div class="container"
				style="width: 80%; overflow-y: auto; min-height: 400px;margin-top: 10%">
				
					<h2 class="text-center text-dark mt-3">Nueva Cuenta</h2>
					<br>
					<form action="ServletAdminCuentas" method="post"
						onsubmit="validarFormulario(event)">
						<div class="form-row">
							<div class="form-group col-md-6">
								<label for="dni">DNI Cliente:</label> <input type="text"
									id="dni" name="dni" class="form-control">
							</div>
							<div class="form-group col-md-6">
								<label for="tipoCuenta">Tipo de Cuenta</label> <select
									id="tipoCuenta" name="tipoCuenta" class="form-control">
									<option value="caja de ahorro">Caja de Ahorro</option>
									<option value="cuenta corriente">Cuenta Corriente</option>
								</select>
							</div>
						</div>
						<button type="submit" id="btnAgregarCuentaNueva"
							name="btnAgregarCuentaNueva" value="AgregarCuentaNueva"
							class="btn btn-outline-success text-dark ">Agregar</button>
					</form>
					<br>
					<div class="d-flex justify-content-end w-100 mt-4">
						<a href="MenuAdmin.jsp"
							class="btn btn-outline-success text-dark "><strong>
								Volver al menú</strong> </a>
					</div>
				
			</div>
		</div>
		<div class="col-1 bgRight pt-5"></div>
	</div>
	<script>
		function validarFormulario(event) {
			const dniInput = document.getElementById('dni');
			const dniValue = dniInput.value.trim();
			if (dniValue.length !== 8) {
				alert('DNI incorrecto. El DNI debe contener 8 números.');
				dniInput.focus();
				event.preventDefault();
				return false;
			}
			const confirmacion = confirm('¿Desea agregar la cuenta?');
			if (confirmacion) {
				return true;
			} else {
				event.preventDefault();
				return false;
			}
		}
	</script>
	<jsp:include page="Footer.jsp" />
</body>
</html>