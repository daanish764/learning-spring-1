package com.example.learningspring1;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*") // Intercept all requests; you can customize the URL pattern as needed
public class SessionValidationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if required
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            System.out.println(httpRequest.getContextPath().toString());
            if(!httpRequest.getRequestURI().equals("/auth/login")) {
                // Extract the JSESSIONID cookie
                String jsessionId = getSessionIdFromCookies(httpRequest);
                if (jsessionId == null) {
                    // No JSESSIONID provided
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpResponse.getWriter().write("Missing JSESSIONID. Unauthorized access.");
                    return;
                }

                // Validate session existence
                HttpSession session = httpRequest.getSession(false); // Prevent session creation
                if (session == null || !jsessionId.equals(session.getId())) {
                    // Session does not exist or IDs do not match
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpResponse.getWriter().write("Invalid session. Unauthorized access.");
                    return;
                }
            }

        }

        // Allow the request to proceed if the session exists and is valid
        chain.doFilter(request, response);
    }


    /**
     * Extracts the JSESSIONID from the cookies in the request.
     *
     * @param request HttpServletRequest
     * @return JSESSIONID value or null if not found
     */
    private String getSessionIdFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "JSESSIONID".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public void destroy() {
        // Cleanup logic if required
    }
}
