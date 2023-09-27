<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registro e inicio de Sesion</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class = "container font-monospace">
		<div class = "row border mt-5">
			<div class = "col-6 border">
				<h2>Registrate</h2>
				<form:form action = "/registro" method = "POST" modelAttribute = "nuevoUsuario">
					<div>
						<form:label path="nombre">Nombre</form:label>
						<form:input path = "nombre" class = "form-control"></form:input>
						<form:errors path = "nombre" class = "text-danger"></form:errors>
					</div>
					<div>
						<form:label path="apellido">Apellido</form:label>
						<form:input path = "apellido" class = "form-control"></form:input>
						<form:errors path = "apellido" class = "text-danger"></form:errors>
					</div>
					<div>
						<form:label path="email">E-mail</form:label>
						<form:input path = "email" type = "email" class = "form-control"></form:input>
						<form:errors path = "email" class = "text-danger"></form:errors>
					</div>
					<div>
						<form:label path="ubicacion">Ubicacion</form:label>
						<form:input path = "ubicacion" class = "form-control"></form:input>
						<form:errors path = "ubicacion" class = "text-danger"></form:errors>
					</div>
					<div>
						<form:label path="estado">Estado</form:label>
						<form:select path="estado" class = "form-select">
							<c:forEach items = "${estados}" var = "estado">
								<form:option value="${estado}">${estado}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<div>
						<form:label path="password">Password</form:label>
						<form:password path = "password" class = "form-control"></form:password>
						<form:errors path = "password" class = "text-danger"></form:errors>
					</div>
					<div>
						<form:label path="confirmacion">Confirmacion</form:label>
						<form:password path = "confirmacion" class = "form-control"></form:password>
						<form:errors path = "confirmacion" class = "text-light"></form:errors>
					</div>
					<div class = "text-center">
						<input type = "Submit" value = "Registrarme" class = "btn btn-success mt-5 mb-3">
					</div>
				</form:form>
			</div>
			<div class = "col-6 border bg-info p-2 text-white bg-opacity-75">
				<h2>Inicio de Sesion</h2>
				<p class = "text-danger">${error_login}</p>
				<form action="/login" method = "POST">
					<div>
						<label>Email</label>
						<input type = "email" class = "form-control" name = "email">
					</div>
					<div>
						<label>Password</label>
						<input type = "password" class = "form-control" name = "password">
					</div>
					<div class = "text-center">
						<input type = "Submit" class = "btn btn-info mt-3 text-dark" value = "Iniciar sesion">
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>