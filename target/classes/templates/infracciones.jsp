<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Infracciones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(120deg, #eef4ff, #f7f9fc); }
        .card-soft { border: 0; border-radius: 1rem; box-shadow: 0 8px 25px rgba(0,0,0,.08); }
        .table thead th { background: #0d6efd; color: #fff; }
    </style>
</head>
<body>
<div class="container py-4">
    <a href="/" class="btn btn-outline-primary btn-sm mb-3">Inicio</a>

    <div class="card card-soft mb-4">
        <div class="card-body">
            <h2 class="h4 mb-3 text-primary">Gestión de Infracciones</h2>
            <c:if test="${not empty mensaje}"><div class="alert alert-danger">${mensaje}</div></c:if>
            <c:if test="${not empty mensajeExito}"><div class="alert alert-success">${mensajeExito}</div></c:if>

            <form action="/infracciones/guardar" method="post" class="row g-2">
                <div class="col-md-2"><input class="form-control" type="date" name="fecha" required></div>
                <div class="col-md-3"><input class="form-control" name="descripcion" placeholder="Descripción" required></div>
                <div class="col-md-2"><input class="form-control" type="number" step="0.01" name="valor" placeholder="Valor" required></div>
                <div class="col-md-2">
                    <select class="form-select" name="vehiculoId" required>
                        <c:forEach var="v" items="${vehiculos}"><option value="${v.id}">${v.placa}</option></c:forEach>
                    </select>
                </div>
                <div class="col-md-1">
                    <select class="form-select" name="tipoDeteccion">
                        <c:forEach var="td" items="${tiposDeteccion}"><option value="${td}">${td}</option></c:forEach>
                    </select>
                </div>
                <div class="col-md-1">
                    <select class="form-select" name="severidad">
                        <c:forEach var="s" items="${severidades}"><option value="${s}">${s}</option></c:forEach>
                    </select>
                </div>
                <div class="col-md-1"><button class="btn btn-primary w-100" type="submit">Guardar</button></div>
            </form>
        </div>
    </div>

    <div class="card card-soft">
        <div class="card-body table-responsive">
            <table class="table table-hover align-middle">
                <thead><tr><th>ID</th><th>Fecha</th><th>Descripción</th><th>Valor</th><th>Vehículo</th><th>Detección</th><th>Severidad</th><th>Estado</th><th>Acciones</th></tr></thead>
                <tbody>
                <c:forEach var="i" items="${infracciones}">
                    <tr>
                        <td>${i.id}</td><td>${i.fecha}</td><td>${i.descripcion}</td>
                        <td>$ <fmt:formatNumber value="${i.valor}" type="number" groupingUsed="true" minFractionDigits="0" maxFractionDigits="0"/></td>
                        <td>${i.vehiculoId}</td><td>${i.tipoDeteccion}</td><td>${i.severidad}</td>
                        <td>
                            <c:choose>
                                <c:when test="${i.pagada}"><span class="badge text-bg-success">Pagada</span></c:when>
                                <c:otherwise><span class="badge text-bg-warning">Pendiente</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td class="d-flex gap-1">
                            <c:if test="${not i.pagada}">
                                <form action="/infracciones/pagar/${i.id}" method="post">
                                    <button type="submit" class="btn btn-success btn-sm">Pagada</button>
                                </form>
                            </c:if>
                            <a class="btn btn-danger btn-sm" href="/infracciones/eliminar/${i.id}">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
