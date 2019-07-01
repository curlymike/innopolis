package ru.inno.stc14.servlet;

import ru.inno.stc14.entity.Person;
import ru.inno.stc14.service.PersonService;
import ru.inno.stc14.service.PersonServiceImpl;
import ru.inno.stc14.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {

    private PersonService personService;

    @Override
    public void init() throws ServletException {
        Connection connection = (Connection) getServletContext().getAttribute("DBConnection");
        personService = new PersonServiceImpl(connection);
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("destination", Util.notEmptyOrDefault(req.getParameter("destination"), ""));
        req.getRequestDispatcher("/loginForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Person person = personService.getPerson(login, password);

        if (person != null) {
            HttpSession session = req.getSession();
            session.setAttribute("person", person);
            resp.sendRedirect(Util.notEmptyOrDefault(req.getParameter("destination"), req.getContextPath()));
        }

    }
}
