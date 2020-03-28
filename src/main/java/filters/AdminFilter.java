package filters;

import entities.UserRole;
import services.FilterService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter({"/addProduct.jsp"})
public class AdminFilter implements Filter {

    private FilterService filterService = FilterService.getInstance();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        filterService.doFilterValidation(request, response, chain, Collections.singletonList(UserRole.ADMIN));
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

}