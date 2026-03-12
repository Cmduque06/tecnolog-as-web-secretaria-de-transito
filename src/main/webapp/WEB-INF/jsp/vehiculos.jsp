<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Vehículos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(120deg, #eef4ff, #f7f9fc); }
        .card-soft { border: 0; border-radius: 1rem; box-shadow: 0 8px 25px rgba(0,0,0,.08); }
        .table thead th { background: #0d6efd; color: #fff; }
    </style>
</head>
<body>
<div class="container mt-4">
    <a href="/" class="btn btn-secondary btn-sm mb-3">Inicio</a>
    <h2>Gestión de Vehículos</h2>

    <form action="/vehiculos/guardar" method="post" class="row g-2 mb-4">
        <input type="hidden" name="id" value="${vehiculo.id}">
        <div class="col-md-2"><input class="form-control" name="placa" placeholder="Placa" value="${vehiculo.placa}" required></div>
        <div class="col-md-2"><input class="form-control" name="marca" placeholder="Marca" value="${vehiculo.marca}" required></div>
        <div class="col-md-2"><input class="form-control" type="date" name="fechaMatricula" value="${vehiculo.fechaMatricula}" required></div>
        <div class="col-md-2">
            <select class="form-select" name="tipo" required>
                <c:forEach var="tipo" items="${tipos}">
                    <option value="${tipo}" ${vehiculo.tipo == tipo ? 'selected' : ''}>${tipo}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-2">
            <select class="form-select" name="propietarioId" required>
                <c:forEach var="p" items="${propietarios}">
                    <option value="${p.id}" ${vehiculo.propietarioId == p.id ? 'selected' : ''}>${p.nombre}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-2"><button class="btn btn-primary w-100" type="submit">Guardar</button></div>
    </form>

    <table class="table table-bordered table-striped">
        <thead><tr><th>ID</th><th>Placa</th><th>Marca</th><th>Fecha</th><th>Tipo</th><th>ID Propietario</th><th>Acciones</th></tr></thead>
        <tbody>
        <c:forEach var="v" items="${vehiculos}">
            <tr>
                <td>${v.id}</td><td>${v.placa}</td><td>${v.marca}</td><td>${v.fechaMatricula}</td><td>${v.tipo}</td><td>${v.propietarioId}</td>
                <td>
                    <a class="btn btn-warning btn-sm" href="/vehiculos/editar/${v.id}">Editar</a>
                    <a class="btn btn-danger btn-sm" href="/vehiculos/eliminar/${v.id}">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</body>
</html>
