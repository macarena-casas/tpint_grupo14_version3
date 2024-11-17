<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
							<div
								class="d-flex flex-column align-items-center w-100 space-y-4">
								<form action="ServletTransferenciasClientes" method="get">
									<button type="submit" name="btnTransferencias2"
										class="btn btn-outline-success text-dark py-2 px-2 rounded w-100 mb-2">
										<strong>Cuentas propias</strong>
									</button>
									<br> <br>
									<button type="submit" name="btnTransferencias1"
										class="btn btn-outline-success text-dark py-2 px-2  rounded w-100 mb-2">
										<strong>Cuentas de Terceros</strong>
									</button>
								</form>
							</div>
						</div>
						<br> <br>
						<div class="d-flex justify-content-end w-100 mt-4">
							<a href="MenuCliente.jsp"
								class="btn btn-outline-success text-dark "><strong>
									Volver al menú</strong> </a>
						</div>
					</div>
				</section>
			</div>
		</div>
		<div class="col-1 bgRight pt-5"></div>
	</div>
	<jsp:include page="Footer.jsp" />

</body>
</html>
