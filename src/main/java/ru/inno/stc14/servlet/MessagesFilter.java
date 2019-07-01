package ru.inno.stc14.servlet;

import ru.inno.stc14.util.Util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Данный фильтр очищает список сообщений который хранится
 * в сессии, если атрибут messagesprinted равен "1".
 * Атрибут проставляется из JSP.
 */

@WebFilter(urlPatterns = "/*")
public class MessagesFilter implements Filter {
  private Logger logger = Logger.getLogger(MessagesFilter.class.getName());

  @Override
  public void init(FilterConfig filterConfig) {
    logger.warning("init messagesFilter");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpSession session = request.getSession();

    // Если layout.tag проставил messagesprinted значение 1 - это значит
    // что собщения отобразились и можно список очистить.
    if (session.getAttribute("messagesprinted") != null && "1".equals(session.getAttribute("messagesprinted"))) {
      Util.clearSessionMessages(request);
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    logger.warning("destroy securityFilter");
  }
}
