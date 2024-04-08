package Controllers.Persona;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Models.Entities.Persona;
import Models.Crud.PersonaJpaController;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author USUARIO
 */
@WebServlet(name = "PersonaController", urlPatterns = {"/persona"})
public class PersonaController extends HttpServlet {

    private final UserTransaction utx;
    private final EntityManagerFactory emf;

    public PersonaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion) {
            case "Guardar":
                guardar(request, response);
                break;
            case "Buscar":
                buscar(request, response);
                break;
            case "Eliminar":
                eliminar(request, response);
                break;
            case "ListarTodo":
                listarTodo(request, response);
                break;
            default:
                response.sendRedirect("view/error.jsp?msg=Accion incorrecta");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ejecutarAccion(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ejecutarAccion(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Persona Controller";
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        // Continúa recogiendo otros campos necesarios...

  

        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellidos(apellidos);
        persona.setFechaNacimiento(fechaNacimiento);

     
        // Continúa configurando otros campos...

        PersonaJpaController personaController = new PersonaJpaController(utx, emf);

        try {
            personaController.create(persona);
            response.sendRedirect("view/personas/guardar.jsp?msg=Persona guardada con éxito");
        } catch (Exception error) {
            response.sendRedirect("view/personas/guardar.jsp?msg=" + error.getMessage());
        }
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer personaID = Integer.valueOf(request.getParameter("id"));
        PersonaJpaController personaController = new PersonaJpaController(utx, emf);

        try {
            Persona persona = personaController.findPersona(personaID);
            HttpSession sesion = request.getSession();
            sesion.setAttribute("persona.buscar", persona);
            request.getRequestDispatcher("/view/personas/buscar.jsp").forward(request, response);
        } catch (IOException | ServletException error) {
            error.printStackTrace();
            response.sendRedirect("view/personas/buscar.jsp?msg=" + error.getMessage());
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Integer personaID = Integer.valueOf(request.getParameter("id"));
        PersonaJpaController personaController = new PersonaJpaController(utx, emf);

        try {
            personaController.destroy(personaID);
            response.sendRedirect("view/personas/buscar.jsp?msg=Persona eliminada con éxito");
        } catch (Exception error) {
            error.printStackTrace();
            response.sendRedirect("view/personas/buscar.jsp?msg=" + error.getMessage());
        }
    }

    private void listarTodo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PersonaJpaController personaController = new PersonaJpaController(utx, emf);

        try {
            List<Persona> lista = personaController.findPersonaEntities();
            HttpSession sesion = request.getSession();
            sesion.setAttribute("persona.all", lista);
            request.getRequestDispatcher("/view/personas/listar_todo.jsp").forward(request, response);
        } catch (Exception error) {
            error.printStackTrace();
            response.sendRedirect("view/personas/listar_todo.jsp?msg=" + error.getMessage());
        }
    }
}