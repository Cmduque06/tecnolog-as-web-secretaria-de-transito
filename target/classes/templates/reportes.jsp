<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reportes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(120deg, #eef4ff, #f9fbff); }
        .card-soft { border: 0; border-radius: 1rem; box-shadow: 0 8px 25px rgba(0,0,0,.06); }
    </style>
</head>
<body>
<div class="container py-4">
    <a href="/" class="btn btn-outline-primary btn-sm mb-3">Inicio</a>

    <div class="card card-soft mb-3">
        <div class="card-body">
            <form action="/reportes" method="get" class="row g-2 align-items-end">
                <div class="col-12"><h2 class="h5 text-primary">Elige qué reportes deseas ver</h2></div>
                <div class="col-md-3 form-check">
                    <input class="form-check-input" type="checkbox" name="mostrar" value="porVehiculo" <c:if test="${mostrar.contains('porVehiculo')}">checked</c:if>>
                    <label class="form-check-label">Infracciones por vehículo</label>
                </div>
                <div class="col-md-3 form-check">
                    <input class="form-check-input" type="checkbox" name="mostrar" value="porPropietario" <c:if test="${mostrar.contains('porPropietario')}">checked</c:if>>
                    <label class="form-check-label">Propietarios con más infracciones</label>
                </div>
                <div class="col-md-3 form-check">
                    <input class="form-check-input" type="checkbox" name="mostrar" value="vehiculosTop" <c:if test="${mostrar.contains('vehiculosTop')}">checked</c:if>>
                    <label class="form-check-label">Vehículos con más multas</label>
                </div>
                <div class="col-md-3 form-check">
                    <input class="form-check-input" type="checkbox" name="mostrar" value="resueltas" <c:if test="${mostrar.contains('resueltas')}">checked</c:if>>
                    <label class="form-check-label">Infracciones resueltas</label>
                </div>
                <div class="col-12 mt-2"><button class="btn btn-primary" type="submit">Aplicar filtros</button></div>
            </form>
        </div>
    </div>

    <div class="alert alert-info">Total en multas: <strong>$ <fmt:formatNumber value="${totalMultas}" type="number" groupingUsed="true" minFractionDigits="0" maxFractionDigits="0"/></strong></div>

    <c:if test="${mostrar.contains('porVehiculo')}">
        <div class="card card-soft mb-3"><div class="card-body">
            <h4 class="h5 text-primary">Infracciones por vehículo</h4>
            <table class="table table-hover"><tr><th>Vehículo</th><th>Total</th></tr>
                <c:forEach var="e" items="${infraccionesPorVehiculo}"><tr><td>${e.key}</td><td>${e.value}</td></tr></c:forEach>
            </table>
        </div></div>
    </c:if>

    <c:if test="${mostrar.contains('porPropietario')}">
        <div class="card card-soft mb-3"><div class="card-body">
            <h4 class="h5 text-primary">Propietarios con más infracciones</h4>
            <table class="table table-hover"><tr><th>Propietario</th><th>Total</th></tr>
                <c:forEach var="e" items="${propietariosConMasInfracciones}"><tr><td>${e.key}</td><td>${e.value}</td></tr></c:forEach>
            </table>
        </div></div>
    </c:if>

    <c:if test="${mostrar.contains('vehiculosTop')}">
        <div class="card card-soft mb-3"><div class="card-body">
            <h4 class="h5 text-primary">Vehículos con más multas</h4>
            <table class="table table-hover"><tr><th>Vehículo</th><th>Total</th></tr>
                <c:forEach var="e" items="${vehiculosConMasMultas}"><tr><td>${e.key}</td><td>${e.value}</td></tr></c:forEach>
            </table>
        </div></div>
    </c:if>

    <c:if test="${mostrar.contains('resueltas')}">
        <div class="card card-soft"><div class="card-body">
            <h4 class="h5 text-primary">Infracciones resueltas</h4>
            <p class="mb-1">Total resueltas: <strong>${totalResueltas}</strong></p>
            <p class="mb-1">Total pendientes: <strong>${totalPendientes}</strong></p>
            <p class="mb-1">Resueltas por agente: <strong>${resueltasAgente}</strong></p>
            <p class="mb-0">Resueltas por cámara: <strong>${resueltasCamara}</strong></p>
        </div></div>
    </c:if>
</div>
</body>
</html>
