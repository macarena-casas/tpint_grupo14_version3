<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidad.*"%>
<!DOCTYPE html>
<html lang="es">
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
<title>Detalles de la cuenta</title>
</head>
<body>
	<br>
	<br>
	<jsp:include page="NavBar.jsp" />
	<div class="d-flex">
		<div class="w-10 bgLeft pt-5"></div>
		<div class="w-90 p-4 bg-white bg-opacity-80 pt-5">
			<div class="container mx-auto px-4 py-8">
				<div class="w-66 mx-auto">
					<center>
						<h2 class="text-3xl font-medium title-font mb-2 text-gray-900">Detalles
							de la cuenta</h2>
					</center>
					<br>
					<form action="ServletAdminCuenta" method="post">
						<!-- dato cliente-->
						<div class="row">
							<div class="col-md-12 mb-4">
								<label class="form-label" for="cliente">Cliente:</label> <input
									type="text" id="cliente" name="cliente"
									value="<%=request.getAttribute("nombreCliente") != null ? request.getAttribute("nombreCliente") : ""%> <%=request.getAttribute("apellidoCliente") != null ? request.getAttribute("apellidoCliente") : ""%>"
									class="form-control" disabled>
							</div>
							<div class="col-md-12 mb-4">
								<label class="form-label" for="nroCuenta">Número de
									cuenta:</label> <input type="text" id="nroCuenta" name="nroCuenta"
									value="<%=request.getAttribute("numerodecuenta") != null ? request.getAttribute("numerodecuenta") : ""%>"
									class="form-control" disabled>
							</div>
						</div>

						<!-- Datos cta -->
						<div class="row">
							<div class="col-md-12 mb-4">
								<label class="form-label" for="tipoDeCuenta">Tipo de
									Cuenta:</label> <input type="text" id="tipoDeCuenta"
									name="tipoDeCuenta"
									value="<%=request.getAttribute("tipoDeCuenta") != null ? request.getAttribute("tipoDeCuenta") : ""%>"
									class="form-control" disabled>
							</div>
							<div class="col-md-12 mb-4">
								<label class="form-label" for="fechaCreacion">Fecha de
									Creación:</label> <input type="text" id="fechaCreacion"
									name="fechaCreacion"
									value="<%=request.getAttribute("fechaCreacion") != null ? request.getAttribute("fechaCreacion") : ""%>"
									class="form-control" disabled>
							</div>
							<div class="col-md-12 mb-4">
								<label class="form-label" for="cbu">CBU:</label> <input
									type="text" id="cbu" name="cbu"
									value="<%=request.getAttribute("cbu") != null ? request.getAttribute("cbu") : ""%>"
									class="form-control" disabled>
							</div>
							<div class="col-md-12 mb-4">
								<label class="form-label" for="saldo">Saldo:</label> <input
									type="text" id="saldo" name="saldo"
									value="<%=request.getAttribute("saldo") != null ? request.getAttribute("saldo") : ""%>"
									class="form-control" disabled>
							</div>
						</div>
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

	<jsp:include page="Footer.jsp" />
</body>
</html>
