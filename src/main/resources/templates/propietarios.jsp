<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Propietarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #eef4ff, #f9fbff); }
        .card-soft { border: 0; border-radius: 1rem; box-shadow: 0 8px 25px rgba(13,110,253,.12); }
        .table thead th { background: #0d6efd; color: #fff; }
    </style>
</head>
<body>
<div class="container py-4">
    <a href="/" class="btn btn-outline-primary btn-sm mb-3">Inicio</a>

    <c:if test="${not empty propietariosSuspendidos}">
        <div class="alert alert-danger border-0 shadow-sm" role="alert">
            <h5 class="alert-heading mb-2">⚠ Licencias suspendidas</h5>
            <p class="mb-1">Los siguientes propietarios tienen la licencia suspendida por exceso de infracciones:</p>
            <ul class="mb-0">
                <c:forEach var="s" items="${propietariosSuspendidos}">
                    <li><strong>${s.nombre}</strong> - ${s.identificacion}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <div class="card card-soft mb-4">
        <div class="card-body">
            <h2 class="h4 mb-3 text-primary">Gestión de Propietarios</h2>
            <form action="/propietarios/guardar" method="post" class="row g-2">
                <input type="hidden" name="id" value="${propietario.id}">
                <div class="col-md-3"><input class="form-control" name="identificacion" placeholder="Identificación" value="${propietario.identificacion}" required></div>
                <div class="col-md-3"><input class="form-control" name="nombre" placeholder="Nombre" value="${propietario.nombre}" required></div>
                <div class="col-md-4"><input class="form-control" name="direccion" placeholder="Dirección" value="${propietario.direccion}" required></div>
                <div class="col-md-2"><button class="btn btn-primary w-100" type="submit">Guardar</button></div>
            </form>
            <small class="text-muted d-block mt-2">Los propietarios nuevos se crean automáticamente con 20 puntos y estos no se editan manualmente.</small>
        </div>
    </div>

    <div class="card card-soft">
        <div class="card-body table-responsive">
            <table class="table table-hover align-middle">
                <thead><tr><th>ID</th><th>Identificación</th><th>Nombre</th><th>Dirección</th><th>Estado licencia</th><th>Puntos</th><th>Acciones</th></tr></thead>
                <tbody>
                <c:forEach var="p" items="${propietarios}">
                    <tr>
                        <td>${p.id}</td><td>${p.identificacion}</td><td>${p.nombre}</td><td>${p.direccion}</td>
                        <td>
                            <c:choose>
                                <c:when test="${p.puntosLicencia == 0}"><span class="badge text-bg-danger">Suspendida</span></c:when>
                                <c:when test="${p.puntosLicencia <= 6}"><span class="badge text-bg-warning">En riesgo</span></c:when>
                                <c:otherwise><span class="badge text-bg-success">Activa</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td><span class="badge bg-secondary fs-6">${p.puntosLicencia}</span></td>
                        <td>
                            <a class="btn btn-warning btn-sm" href="/propietarios/editar/${p.id}">Editar</a>
                            <a class="btn btn-danger btn-sm" href="/propietarios/eliminar/${p.id}">Eliminar</a>
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
