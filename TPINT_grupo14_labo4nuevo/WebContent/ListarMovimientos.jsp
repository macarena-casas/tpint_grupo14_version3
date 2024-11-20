<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.Movimiento" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de Movimientos</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.4/css/dataTables.bootstrap4.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.js"></script>
    <script src="https://cdn.datatables.net/1.11.4/js/dataTables.bootstrap4.min.js"></script>
    <link rel="icon" type="image/png" href="Images/logo.png">
</head>
<body>

<%
    ArrayList<Movimiento> movimientos = (ArrayList<Movimiento>) request.getAttribute("movimientos");
%>

<jsp:include page="NavBar.jsp" />
<div class="bg-white pt-5">
    <div class="container" style="width: 80%; overflow-y: auto; min-height: 600px;">
        <h2 class="text-center text-dark mt-3">Historial de Movimientos: N° <%= movimientos.get(0).getCuentaMov().getNroCuenta() %></h2>
        <br><br>
        <table id="tablaCuentas" class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>Fecha</th>
                    <th>Tipo de Movimiento</th>
                    <th>Concepto</th>
                    <th>Importe</th>
                </tr>
            </thead>
            <tbody>
                <% if (movimientos.isEmpty()) { %>
                    <tr>
                        <td colspan="4" class="text-center">No hay movimientos para esta cuenta</td>
                    </tr>
                <% } else { %>
                    <% for (Movimiento movimiento : movimientos) { %>
                        <tr>
                            <td><%= movimiento.getFechaMov() %></td>
                            <td><%= movimiento.getTipoMov() %></td>
                            <td><%= movimiento.getDetalleMov() %></td>
                            <td>$<%= movimiento.getImporteMov() %></td>
                        </tr>
                    <% } %>
                <% } %>
            </tbody>
        </table>
        <br>
 <div class="d-flex justify-content-end w-100 mt-4">
				<a href="MenuCliente.jsp" class="btn btn-outline-success text-dark "><strong>
						Volver al menú</strong> </a>
			</div>
    </div>
</div>


<jsp:include page="Footer.jsp" />
<script>
	$('#tablaCuentas').DataTable({
	    "paging": true,
	    "lengthChange": true,
	    "searching": true,
	    "ordering": true,
	    "info": true,
	    "autoWidth": false,
	    "responsive": true,
	    "language": {
	        "paginate": {
	            "first": "Primero",
	            "last": "Último",
	            "next": "Siguiente",
	            "previous": "Anterior"
	        },
	        "lengthMenu": "Mostrar _MENU_ registros por página", // Reemplaza "MENU" por "_MENU_"
	        "zeroRecords": "No se encontraron resultados",
	        "info": "Mostrando página _PAGE_ de _PAGES_", // Reemplaza "PAGE" por "_PAGE_"
	        "infoEmpty": "No hay registros disponibles",
	        "infoFiltered": "(filtrado de _MAX_ registros totales)" // Reemplaza "MAX" por "_MAX_"
	    },
	    "pageLength": 10 // Esto define el número de registros por página, por defecto 10
	});

	</script>

</body>
</html>