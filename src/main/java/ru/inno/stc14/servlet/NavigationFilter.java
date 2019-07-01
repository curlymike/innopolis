package ru.inno.stc14.servlet;

import ru.inno.stc14.servlet.navigation.Item;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Данный фильтр помещает в атрибут "navigation" объекта ServletRequest
 * список пунктов меню, в JSP остаётся их напечатать. Одна из "фишек"
 * элементы путь которых соответствует текущему URI помечаются как активные.
 */

@WebFilter(urlPatterns = "/*")
public class NavigationFilter implements Filter {
  private Logger logger = Logger.getLogger(NavigationFilter.class.getName());

  @Override
  public void init(FilterConfig filterConfig) {
    logger.warning("init securityFilter");
  }

  private String servletPath(HttpServletRequest request) {
    String path = request.getServletPath();
    return path.length() > 0 && path.charAt(0) == '/' ? path.substring(1) : path;
  }

  /**
   * Проставляет item.setActive(true) элементу путь которого соответствует пути текущего сервлета
   * @param menuItems
   * @param currentPath
   */

  private void setActivePath(List<Item> menuItems, String currentPath) {
    for (Item item : menuItems) {
      if (currentPath.equals(item.getPath()) || currentPath.equals(item.getAlias())) {
        item.setActive(true);
      }
    }
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;

    String servletPath = servletPath(request);

    request.setAttribute("servletPath", servletPath);
    request.setAttribute("requestURI", request.getRequestURI());

    List<Item> list = new ArrayList<>();

    list.add(new Item("Main page", "").withAlias("index.jsp"));
    list.add(new Item("List students", "person/list"));
    list.add(new Item("New student", "person"));
    list.add(new Item("Test", "test"));

    setActivePath(list, servletPath);

    request.setAttribute("navigation", list);

    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    logger.warning("destroy securityFilter");
  }
}
