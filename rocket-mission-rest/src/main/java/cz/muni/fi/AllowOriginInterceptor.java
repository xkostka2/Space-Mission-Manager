package cz.muni.fi;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Martin Kazimir
 */
public class AllowOriginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             HttpServletResponse response, @NotNull Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS");
        return true;
    }
}
