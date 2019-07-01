package ru.inno.stc14.servlet;

import ru.inno.stc14.model.StatusMessage;
import ru.inno.stc14.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Тестовый сервлет, для визуального тестирования информационных сообщений и некоторых переменных.
 */

@WebServlet(value = "/test")
public class TestServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Util.setMessage(req, "This is a test INFO message");
        Util.setMessage(req, "This is a test SUCCESS message", StatusMessage.Type.SUCCESS);
        Util.setMessage(req, "This is a test WARNING message", StatusMessage.Type.WARNING);
        Util.setMessage(req, "This is a test ERROR message", StatusMessage.Type.ERROR);

        req.setAttribute("test", "This is a test attribute!");
        req.getRequestDispatcher("/test.jsp").forward(req, resp);
    }

}
