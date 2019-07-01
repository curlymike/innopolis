package ru.inno.stc14.servlet;

import ru.inno.stc14.model.PersonForm;
import ru.inno.stc14.model.StatusMessage;
import ru.inno.stc14.service.PersonService;
import ru.inno.stc14.service.PersonServiceImpl;
import ru.inno.stc14.service.ServiceException;
import ru.inno.stc14.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "person", value = "/person", loadOnStartup = 1)
public class PersonServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(PersonServiceImpl.class.getName());
    private PersonService person;

    @Override
    public void init() throws ServletException {
        Connection connection = (Connection) getServletContext().getAttribute("DBConnection");
        person = new PersonServiceImpl(connection);
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PersonForm form = new PersonForm();
        req.setAttribute("form", form);
        req.setAttribute("message", "This is a test message (GET).");
        req.getRequestDispatcher("/form.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        String birth = req.getParameter("birth");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        boolean hasErrors = false;

        if (Util.isEmpty(name)) {
            Util.setMessage(req, "Поле &quot;имя&quot; является обязательным", StatusMessage.Type.ERROR);
            hasErrors = true;
        }
        if (Util.isEmpty(birth)) {
            Util.setMessage(req, "Поле &quot;дата&quot; является обязательным", StatusMessage.Type.ERROR);
            hasErrors = true;
        }
        if (Util.isEmpty(login)) {
            Util.setMessage(req, "Поле &quot;логин&quot; является обязательным", StatusMessage.Type.ERROR);
            hasErrors = true;
        }
        if (Util.isEmpty(password)) {
            Util.setMessage(req, "Поле &quot;пароль&quot; является обязательным", StatusMessage.Type.ERROR);
            hasErrors = true;
        }
        if (phone != null && !phone.matches("^[0-9]*$")) {
            Util.setMessage(req, "Неверный формат телефонного номера, указывайте 10 цифр без дополнительных знаков и пробелов.", StatusMessage.Type.ERROR);
            hasErrors = true;
        }

        // PersonDAO возвращает true/false, я менять не стал.
        // Но PersonService может выкинуть ServiceException
        try {
            if (person.addPerson(name, birth, login, email, phone, password)) {
                Util.setMessage(req, "Студент &quot;" + name + "&quot; успешно добавлен в список.", StatusMessage.Type.SUCCESS);
                resp.sendRedirect(req.getContextPath() + "/person/list");
                return;
            }
        } catch (ServiceException e) {
            logger.log(Level.SEVERE, "Ошибка при попытке сохранить данные в базу", e);
            if (e.getMessage() != null && e.getMessage().length() > 0) {
                Util.setMessage(req, e.getMessage(), StatusMessage.Type.ERROR);
            }
        }

        PersonForm form = new PersonForm(name, birth, login, email, phone, password);
        req.setAttribute("form", form);
        req.getRequestDispatcher("/form.jsp").forward(req, resp);
        return;
    }
}
