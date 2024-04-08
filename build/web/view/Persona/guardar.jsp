<%-- 
    Document   : agregar
    Created on : 7/04/2024, 11:48:08 AM
    Author     : USUARIO
--%>
<%-- 
    Document   : agregar
    Created on : 7/04/2024, 11:48:08 AM
    Author     : USUARIO
--%>
<%@page import="Models.Crud.PersonaJpaController"%>
<%@page import="Models.Entities.Persona"%>
<%@page import="Controllers.Persona.PersonaController"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.transaction.UserTransaction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Persona a la Base de Datos</title>
    </head>
    <body>
        <h1>Agregar Nueva Persona</h1>
        <form action="<%=request.getContextPath()%>/persona" method="POST">
            <input type="hidden" name="accion" value="Guardar"> <!-- Campo oculto para la acción -->
            Nombre: <input type="text" name="nombre" required><br>
            Apellido: <input type="text" name="apellidos" required><br> <!-- Cambia a 'apellidos' -->
            ID Situación Militar: <input type="number" name="situacionMilitarID" required><br>
            ID Lugar de Nacimiento: <input type="number" name="lugarNacimientoID" required><br>
            Fecha de Nacimiento: <input type="text" name="fechaNacimiento"><br>
            <!-- Botones para diferentes acciones -->
            <button type="submit" name="accion" value="Guardar">Agregar Persona</button>
            <button type="submit" name="accion" value="Editar">Editar</button>
            <button type="submit" name="accion" value="Eliminar">Eliminar</button>
            <button type="submit" name="accion" value="ListarTodo">Ver Lista</button>

        </form>

    </body>
</html>


