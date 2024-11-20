<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<%
	Cuenta auxCuenta = new Cuenta();
	auxCuenta = (Cuenta) request.getAttribute("cuenta");
	DecimalFormat formato = new DecimalFormat("#0.0");
	String saldoFormateado = formato.format(auxCuenta.getSaldo());
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
<head>

</head>
<body>
	<br>
	<br>
	<title>Modificar Cuenta</title>

	<jsp:include page="NavBar.jsp" />
	<%
		String respuesta = null;
		if (session != null && session.getAttribute("respuesta") != null) {
			respuesta = (String) session.getAttribute("respuesta");
			session.removeAttribute("respuesta");
	%>
	<script> 
	        alert('<%=respuesta%>
		');
	</script>
	<%
		respuesta = null;
		}
	%>
	<div class="d-flex">
		<div class="w-10 bgLeft pt-5"></div>
		<div class="w-90 p-4 bg-white bg-opacity-80 pt-5">
			<div class="container mx-auto px-4 py-8">
				<div class="w-66 mx-auto">
					<center>
						<h2 class="text-3xl font-medium title-font mb-2 text-gray-900">Modificar
							Cuenta</h2>
					</center>
					<br>
					<form action="ServletAdminCuentas" method="post"
						onsubmit="return validarFormulario();">
						<div class="row">

							<div class="col-md-6 mb-4">
								<label class="form-label" for="nombre">Numero de cuenta:</label>
								<input type="text" id="nroCuenta" name="nroCuenta"
									maxlength="100" value="<%=auxCuenta.getNroCuenta()%>"
									class="form-control" readonly>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="nombre">Nombre:</label> <input
									type="text" id="nombre" name="nombre" maxlength="100"
									class="form-control"
									value="<%=auxCuenta.getCliente().getNombre()%>" readonly>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="apellido">Apellido:</label> <input
									type="text" id="apellido" name="apellido" maxlength="100"
									class="form-control"
									value="<%=auxCuenta.getCliente().getApellido()%>" readonly>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="dni">DNI:</label> <input
									type="number" id="dni" name="dni" class="form-control"
									value="<%=auxCuenta.getCliente().getDni()%>" readonly>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="cuil">CUIL:</label> <input
									type="number" id="cuil" name="cuil"
									value="<%=auxCuenta.getCliente().getCuil()%>"
									class="form-control" maxlength="13" readonly>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="tipoCuenta">Tipo de
									Cuenta:</label> <select id="tipoCuenta" name="tipoCuenta"
									class="form-control">
									<option value="caja de ahorro"
										<%="caja de ahorro".equals(auxCuenta.getTipoCuenta()) ? "selected" : ""%>>Caja
										de Ahorro</option>
									<option value="cuenta corriente"
										<%="cuenta corriente".equals(auxCuenta.getTipoCuenta()) ? "selected" : ""%>>Cuenta
										Corriente</option>
								</select>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="cbu1">CBU:</label> <input
									type="text" id="cbu1" name="cbu1" class="form-control"
									value="<%=auxCuenta.getCbu()%>" readonly>
							</div>
							<div class="col-md-6 mb-4">
								<label class="form-label" for="saldo">Saldo:</label> <input
									required type="number" max="9999999999" min="0" step="any"
									pattern="[0-9]+([,\.][0-9]+)?" id="saldo" name="saldo"
									class="form-control" value="<%=auxCuenta.getSaldo()%>">
							</div>

						</div>

						<center>
							<button type="submit" id="btnModificarCuenta"
								name="btnModificarCuenta" value="ModificarCuenta"
								class="btn btn-outline-success ">Modificar Cuenta</button>
						</center>
					</form>
					<div class="d-flex justify-content-end w-100 mt-4">
						<form action="ServletAdminCuentas" method="get">

							<button type="submit" name="btnAtras2" value="Atras"
								class="btn btn-outline-success text-dark">Atrás</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		function validarFormulario() {
			return confirm('¿Desea guardar los cambios?');
			if (confirmacion) {
				return true;
			} else {
				return false;
			}
		}
	</script>

	<jsp:include page="Footer.jsp" />

</body>
</html>