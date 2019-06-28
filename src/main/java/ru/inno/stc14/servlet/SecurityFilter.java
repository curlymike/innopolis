package ru.inno.stc14.servlet;

import ru.inno.stc14.entity.Person;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {
  private Logger logger = Logger.getLogger(SecurityFilter.class.getName());

  @Override
  public void init(FilterConfig filterConfig) {
    logger.warning("init securityFilter");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String servletPath = request.getServletPath();

    if (servletPath.equals("/login")) {
      filterChain.doFilter(request, response);
      return;
    }

    HttpSession session = request.getSession();

    Person person = (Person) session.getAttribute("person");

    if (person == null) {
      response.sendRedirect(request.getContextPath() + "/login?destination=" + request.getRequestURI());
      return;
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    logger.warning("destroy securityFilter");
  }
}
