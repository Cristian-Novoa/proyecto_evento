<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class = "container">
		<nav class = "d-flex justify-content-between align-items-center">
			<h1>Bienvenido ${usuarioEnSesion.nombre}</h1>
			<a href = "/logout" class = "btn btn-danger">Cerrar sesion</a>
		</nav>
		<div class = "row">
			<!-- Tabla de eventos-->
			<h2>Eventos Mi Estado</h2>
			<table class = "table table-striped">
				<thead>
					<tr>
						<th>Evento</th>
						<th>Fecha</th>
						<th>Ubicacion</th>
						<th>Estado</th>
						<th>Host</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items = "${eventosMiEstado}" var = "evento">
						<tr>
							<td><a href = "/evento/${evento.id}" class="link-info link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">${evento.nombre}</a></td>
							<td>${evento.fecha }</td>
							<td>${evento.ubicacion }</td>
							<td>${evento.estado }</td>
							<td>${evento.host.nombre }</td>
							<td>
								<!-- Botones de editar y borrar si son mis eventos -->
								<c:if test = "${evento.host.id == usuario.id}">
									<a href = "/editar/${evento.id}" class = "btn btn-warning">Editar</a>
									<form action = "/borrar/${evento.id}" method = "POST">
										<input type = "hidden" name = "_method" value = "DELETE"/>
										<input type = "Submit" class = "btn btn-danger" value = "Borrar"/>
									</form>								
								</c:if>
								<!-- Botones para unirme al evento -->
								<c:if test="${evento.host.id != usuario.id }">
									<c:choose> 
										<c:when test = "${evento.asistentes.contains(usuario)}">
											<span>Voy a ir al Evento</span>
											<a href = "/quitar/${evento.id}" class = "btn btn-danger">Cancelar asistencia</a>
										</c:when>
										<c:otherwise>
										<a href = "/unir/${evento.id}" class = "btn btn-primary">Asistir al evento</a>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class = "row">
			<!-- Tabla de eventos-->
			<h2>Eventos en otro Estado</h2>
			<table class = "table table-striped">
				<thead>
					<tr>
						<th>Evento</th>
						<th>Fecha</th>
						<th>Ubicacion</th>
						<th>Estado</th>
						<th>Host</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items = "${eventosEnOtroEstado}" var = "evento">
						<tr>
							<td><a href = "/evento/${evento.id}" class="link-info link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">${evento.nombre}</a></td>
							<td>${evento.fecha }</td>
							<td>${evento.ubicacion }</td>
							<td>${evento.estado }</td>
							<td>${evento.host.nombre }</td>
							<td>
							<!-- Botones de editar y borrar si son mis eventos -->
								<c:if test = "${evento.host.id == usuario.id}">
									<a href = "/editar/${evento.id}" class = "btn btn-warning">Editar</a>
									<form action = "/borrar/${evento.id}" method = "POST">
										<input type = "hidden" name = "_method" value = "DELETE"/>
										<input type = "Submit" class = "btn btn-danger" value = "Borrar"/>
									</form>								
								</c:if>
								<!-- Botones para unirme al evento -->
								<c:if test="${evento.host.id != usuario.id }">
									<c:choose> 
										<c:when test = "${evento.asistentes.contains(usuario)}">
											<span>Voy a ir al Evento</span>
											<a href = "/quitar/${evento.id}" class = "btn btn-danger">Cancelar asistencia</a>
										</c:when>
										<c:otherwise>
											<a href = "/unir/${evento.id}" class = "btn btn-primary">Asistir al evento</a>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class = "row">
			<h2>Crear evento</h2>
			<form:form action = "/crear" method = "POST" modelAttribute = "nuevoEvento">
				<div>
					<form:label path = "nombre">Nombre:</form:label>
					<form:input path = "nombre" class = "form-control"/>
					<form:errors path ="nombre" class = "text-danger"/>
				</div>
				<div>
					<form:label path = "fecha">Fecha:</form:label>
					<form:input type = "date" path = "fecha" class = "form-control"/>
					<form:errors path ="fecha" class = "text-danger"/>
				</div>
				<div>
					<form:label path = "ubicacion">Ubicacion:</form:label>
					<form:input path = "ubicacion" class = "form-control"/>
					<form:errors path ="ubicacion" class = "text-danger"/>
				</div>
				<div>
					<form:label path="estado">Estado</form:label>
					<form:select path="estado" class = "form-select">
						<c:forEach items = "${estados}" var = "estado">
							<form:option value="${estado}">${estado}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<form:hidden path = "host" value = "${usuarioEnSesion.id}"/>
				<input type = "Submit" class = "btn btn-success mt-3" value = "Crear evento"/>
			</form:form>
		</div>
	</div>
</body>
</html>