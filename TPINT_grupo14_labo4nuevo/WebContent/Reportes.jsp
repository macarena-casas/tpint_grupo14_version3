
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%
	String MostrarForm = (String) request.getAttribute("MostrarForm");
	String textAreaReporte = (String) request.getAttribute("textAreaReporte");
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


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Reportes</title>
<link
	href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
	rel="stylesheet">
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

.textAreaReporte {
	overflow: auto;
}
</style>
<script>
	function confirmacion() {
		return confirm('¿Desea realizar el reporte?');
		if (confirmacion) {
			return true;
		} else {
			return false;
		}
	}

	function confirmacionDescarga() {
		return confirm('¿Desea descargar el reporte?');
		if (confirmacionDescarga) {
			return true;
		} else {
			return false;
		}
	}
</script>
</head>
<body>
	<jsp:include page="NavBar.jsp" />
	<div class="d-flex">
		<div class="col-1 bgLeft pt-5"></div>
		<div class="col-11 p-4 bg-white bg-opacity-80 pt-5">
			<div class="bg-white pt-5">
				<div class="container py-4"
					style="width: 100%; overflow-y: auto; min-height: 600px;">
					<section class="text-gray-600 body-font">
						<div class="container px-5 py-24 mx-auto">
							<div class="d-flex flex-column text-center w-100 mb-6">
								<h1 class="text-center font-weight-medium mb-2 text-dark">Reportes
									Prestamos</h1>
								<br>
								<%
									if (MostrarForm == "MostrarForm") {
								%>
								<form action="ServletReportes" method="post"
									onsubmit="return confirmacion();">
									<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
										<div class="form-group">
											<label class="font-weight-bold" for="fechaInicio">Fecha
												de Inicio:</label> <input type="date" id="fechaInicio"
												name="fechaInicio" class="form-control border-black"
												required>
										</div>
										<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
											<label class="block text-gray-700 text-sm font-bold mb-2"
												for="fechaFin">Fecha de Fin:</label> <input type="date"
												id="fechaFin" name="fechaFin"
												class="form-control border-black" required>
										</div>
										<div class="mb-4">
											<div class="form-group">
												<label class="font-weight-bold" for="estado">Estado
													del Prestamo:</label> <select id="estado" name="estado"
													class="form-control" required>
													<option value="Autorizado">Autorizado</option>
													<option value="Rechazado">Rechazado</option>
													<option value="En proceso">En proceso</option>
												</select>
												
											</div>
										</div>
										<div class="form-group">
											<label class="font-weight-bold" for="importe">Importe:</label>
											<div class="d-flex align-items-center">
												<label class="font-weight-bold mr-2" for="importeEntre">Entre:</label>
												<select id="importeMin" name="importeMin"
													class="form-control mr-2" required>
													<option value="500000">500000.00</option>
													<option value="1000000">1000000.00</option>
													<option value="1500000">1500000.00</option>
													<option value="2000000">2000000.00</option>
													<option value="2500000">2500000.00</option>
												</select> <label class="font-weight-bold mr-2" for="importeY">y</label>
												<select id="importeMax" name="importeMax"
													class="form-control" required>
													<option value="500000">500000.00</option>
													<option value="1000000">1000000.00</option>
													<option value="1500000">1500000.00</option>
													<option value="2000000">2000000.00</option>
													<option value="2500000">2500000.00</option>
												</select>

											</div>
										</div>

									</div>
									<div class="grid grid-cols-1 md:grid-cols-2 gap-4">
										<label class="block text-gray-700 text-sm font-bold mb-2"
											for="DNIclienteReporte">Dni Cliente:</label> <input
											type="text" id="DNIclienteReporte" minlength="8"
											maxlength="8" name="DNIclienteReporte"
											class="form-control border-black">
									</div>
									<br>
									<button type="submit" name="btnGenerarReporte"
										class="btn btn-outline-success text-dark ">Generar
										Reporte</button>
								</form>

								<%
									}
								%>
								<%
									if (MostrarForm != "MostrarForm") {
								%>
								<form action="ServletReportes" method="post"
									onsubmit="return confirmacionDescarga();">
									<div class="mb-4">
										<label class="block text-gray-700 text-sm font-bold mb-2"
											for="textoExtenso">Texto Extenso:</label>
										<textarea id="textAreaReporte" name="textAreaReporte"
											class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
											rows="30" cols="8"><%=textAreaReporte%></textarea>
									</div>
									<input type="hidden" name="reporte"
										value="<%=textAreaReporte%>">
									<button type="submit" id="btnDescargar" name="btnDescargar"
										class="btn btn-outline-success text-dark ">Descargar
										Reporte</button>
								</form>
								<form action="ServletReportes" method="post">
									<div class="flex justify-end w-full mt-4">
										<button id="btnAtras" name="btnAtras"
											class="btn btn-outline-success text-dark ">Atrás</button>
									</div>

								</form>
								<%
									}
								%>

								<div class="d-flex justify-content-end w-100 mt-4">
								<a href="MenuAdmin.jsp"
									class="btn btn-outline-success text-dark "><strong>
										Volver al menú</strong> </a>
							</div>
							</div>
						</div>
					</section>
				</div>
			</div>
			<br>
		</div>
		<div class="w-1/12 bgRight pt-24"></div>
	</div>


	<jsp:include page="Footer.jsp" />
</body>
</html>
