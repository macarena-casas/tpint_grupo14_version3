<%@page import="negocioImpl.CuentaNegocioImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entidad.Cuenta"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Datos de Cuenta</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f9f9f9;
	color: #333;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

h1 {
	font-size: 3rem !important;
	color: black;
	margin-bottom: 3rem !important;
	text-align: center;
}

.container {
	max-width: 1200px;
	display: flex;
	flex-wrap: wrap;
	gap: 30px;
	justify-content: center;
	text-align: center;
}

.card {
	background-color: #fff;
	border-radius: 15px;
	box-shadow: 0 6px 10px rgba(0, 0, 0, 0.15);
	padding: 30px;
	width: 350px;
	border: 3px solid #4caf50; /* Borde verde */
}

.card img {
	width: 60px;
	height: 60px;
	margin-bottom: 20px;
}

.card h3 {
	font-size: 22px;
	margin: 10px 0;
	color: #333;
}

.card p {
	font-size: 18px;
	color: #555;
	margin: 10px 0;
}

.card button {
	margin-top: 20px;
	background: linear-gradient(90deg, #4caf50, #81c784);
	color: #fff;
	border: none;
	padding: 15px 20px;
	border-radius: 10px;
	cursor: pointer;
	font-size: 16px;
}

.card button:hover {
	background: #388e3c;
}

.back-button {
	margin-top: 40px;
	background: linear-gradient(90deg, #4caf50, #81c784);
	color: #fff;
	border: none;
	padding: 15px 20px;
	border-radius: 10px;
	cursor: pointer;
	font-size: 18px;
	text-decoration: none;
	text-align: center;
}

.back-button:hover {
	background: #388e3c !important;
}
</style>
</head>
<body>
	<%
		List<Cuenta> listacuenta = new ArrayList<Cuenta>();
		CuentaNegocioImpl ctneg = new CuentaNegocioImpl();
		listacuenta = (List<Cuenta>) request.getAttribute("listadeCuentas");
	%>
	<jsp:include page="NavBar.jsp" />
	<h1>Cuentas</h1>
	<form class="container" action="ServletCliente" method="get">
		<%
			for (Cuenta objCuenta : listacuenta) {
		%>
		<!-- Tarjeta 1 -->
		<div class="card">
			<img
				src="https://play-lh.googleusercontent.com/QB2eXpmsR9ihxgoFCgETxvzIGd2Rf9Yzfh1w5DCxEUf1h2BYh1i3kb05uzuawoqjxnJ9"
				alt="Cuenta">
			<h3>
				CBU:
				<%=objCuenta.getCbu()%>
			</h3>
			<p>
				<strong>Tipo de Cuenta:</strong>
				<%=objCuenta.getTipoCuenta()%>
			</p>
			<p>
				<strong>Saldo:</strong> $<%=objCuenta.getSaldo()%>
			</p>
			<button name="btnMovimientosCuenta"
				value=<%=objCuenta.getNroCuenta()%>>Movimientos</button>
		</div>
		<%
			}
		%>

	</form>
	<div class="d-flex justify-content-end w-100 mt-4">
		<a href="MenuCliente.jsp" class="btn btn-outline-success text-dark "><strong>
				Volver al menú</strong> </a>
	</div>
</body>
</html>




