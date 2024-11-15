<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entidad.*" %>
<%@ page import="entidad.Localidad" %>
<%@ page import="entidad.Provincia" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<style>
    .bgLeft { background: linear-gradient(45deg, mediumseagreen, seagreen, forestgreen, forestgreen, seagreen, mediumseagreen); background-size: cover; }
    .bgRight { background: linear-gradient(45deg, mediumseagreen, seagreen, forestgreen, forestgreen, seagreen, mediumseagreen); background-size: cover; }
</style>

</head>
<body>
    <br><br>
    <title>Modificar Cliente</title>

    <jsp:include page="NavBar.jsp" />
    
    <div class="d-flex justify-content-center align-items-center min-vh-100">
        <div class="w-100 w-md-75 p-4 bg-white bg-opacity-80">
            <div class="container mx-auto px-4 py-8">
                <div class="w-66 mx-auto">
                    <center>
                        <h2 class="text-3xl font-medium title-font mb-2 text-gray-900">Modificar Cliente</h2>
                    </center>
                    <center>
                        <div class="col-12 border-bottom pb-4">
                            <h3 class="text-lg font-medium title-font mb-2 text-gray-700">Datos Personales</h3>
                        </div>
                    </center>
                    <br>
                    <form action="ServletAdminCliente" method="post" onsubmit="return validarFormulario(event);">
                        <div class="row">
                            <div class="col-md-6 mb-4">
                                <label class="form-label" for="nombre">Nombre:</label>
                                <input type="text" id="nombre" name="nombre" value="<%= request.getAttribute("nombreCliente") %>" maxlength="100" class="form-control" required>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label" for="apellido">Apellido:</label>
                                <input type="text" id="apellido" name="apellido" value="<%= request.getAttribute("apellidoCliente") %>" maxlength="100" class="form-control" required>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label" for="dni">DNI:</label>
                                <input type="number" id="dni" name="dni" value="<%= request.getAttribute("dni") %>" class="form-control" required>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label" for="cuil">CUIL:</label>
                                <input type="number" id="cuil" name="cuil" value="<%= request.getAttribute("cuil") %>" class="form-control" maxlength="13" required>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label" for="sexo">Sexo:</label>
                                <select id="sexo" name="sexo" class="form-control">
                                    <option value="M" <%= "M".equals(request.getAttribute("sexo")) ? "selected" : "" %>>M</option>
                                    <option value="F" <%= "F".equals(request.getAttribute("sexo")) ? "selected" : "" %>>F</option>
                                    <option value="X" <%= "X".equals(request.getAttribute("sexo")) ? "selected" : "" %>>X</option>
                                </select>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label" for="nacionalidad1">Nacionalidad:</label>
                                <input id="nacionalidad" name="nacionalidad" class="form-control" value="<%= request.getAttribute("nacionalidad") %>" required>                            
                            </div>
                            <div class="col-md-6 mb-4">
    							<label class="block text-gray-700 text-sm font-bold mb-2" for="fechanacimiento">Fecha de Nacimiento:</label>
    							<input type="date" id="fechanacimiento" name="fechanacimiento" class="date" value="<%= request.getAttribute("fechanacimiento")  != null ? request.getAttribute("fechanacimiento") : "" %>" required>
							</div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label" for="direccion">Dirección:</label>
                                <input type="text" id="direccion" name="direccion" value="<%= request.getAttribute("direccion") %>" class="form-control" required>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label" for="localidad">Localidad:</label>
                                <select id="localidad" name="localidad" class="form-control" required>
                                    <% List<Localidad> localidades = (List<Localidad>) request.getAttribute("localidades"); %>
                                    <%
                                    if (localidades != null) {
                                        for (Localidad localidad : localidades) {
                                            String selected = (request.getAttribute("idLocalidad") != null && localidad.getIdlocalidad() == Integer.parseInt(request.getAttribute("idLocalidad").toString())) ? "selected" : "";
                                    %>
                                        <option value="<%= localidad.getIdlocalidad() %>" <%= selected %>><%= localidad.getNombre() %></option>
                                    <% } } %>
                                </select>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="block text-gray-700 text-sm font-bold mb-2" for="provincia">Provincia:</label>
                                <select id="provincia" name="provincia" class="form-control" required>
                                    <% List<Provincia> provincias = (List<Provincia>) request.getAttribute("provincias"); %>
                                    <%
                                    if (provincias != null) {
                                        for (Provincia provincia : provincias) {
                                            String selected = (request.getAttribute("idProvincia") != null && provincia.getIdprovincia() == Integer.parseInt(request.getAttribute("idProvincia").toString())) ? "selected" : "";
                                    %>
                                        <option value="<%= provincia.getIdprovincia() %>" <%= selected %>><%= provincia.getNombre() %></option>
                                    <% } } %>
                                </select>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label" for="telefono">Teléfono:</label>
                                <input type="number" id="telefono" name="telefono" value="<%= request.getAttribute("telefono") %>" class="form-control" required>
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label" for="email">Correo:</label>
                                <input type="email" id="correo" name="correo" value="<%= request.getAttribute("correo") %>" class="form-control" required>
                            </div>
                            <div class="col-12">
                                <button type="submit" name="btnGuardarCambios" class="btn btn-success w-100">Guardar Cambios</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
