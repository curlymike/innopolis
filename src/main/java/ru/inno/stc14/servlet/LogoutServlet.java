package ru.inno.stc14.servlet;

import ru.inno.stc14.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Данный сервлет "разлогинивает" пользователя
 */

@WebServlet(value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("person");
        // destination обычно содержит путь страницы с которой был вызван данный сервлет
        // и я перенаправляю пользователя обратно на этот destination, но т.к. он теперь не
        // залогинен его перебросит на главную страницу с формой логина и тем же значением
        // destination, таким образом после успешной авторизации, авторизированный пользователь
        // будет перенаправлен на страницу путь которой содержится в GET параметре destination.
        // TODO: перефразировать покороче?
        resp.sendRedirect(Util.notEmptyOrDefault(req.getParameter("destination"), req.getContextPath()));
    }

}
