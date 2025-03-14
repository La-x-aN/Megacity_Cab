package com.MegaCity_Cab.servlets;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.security.Principal;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.junit.jupiter.api.Test;

class LogoutServletTest {

    static class TestRequest implements HttpServletRequest {
        private HttpSession session;
        private String contextPath = "/test";
        
        @Override
        public HttpSession getSession(boolean create) {
            return session;
        }
        
        public void setSession(HttpSession session) {
            this.session = session;
        }
        
        @Override
        public String getContextPath() {
            return contextPath;
        }

        // Implemented methods
        @Override public String getAuthType() { return null; }
        @Override public Cookie[] getCookies() { return new Cookie[0]; }
        @Override public long getDateHeader(String name) { return 0; }
        @Override public String getHeader(String name) { return null; }
        @Override public Enumeration<String> getHeaders(String name) { return Collections.emptyEnumeration(); }
        @Override public Enumeration<String> getHeaderNames() { return Collections.emptyEnumeration(); }
        @Override public int getIntHeader(String name) { return 0; }
        @Override public String getMethod() { return "GET"; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return null; }
        @Override public String getQueryString() { return null; }
        @Override public String getRemoteUser() { return null; }
        @Override public boolean isUserInRole(String role) { return false; }
        @Override public Principal getUserPrincipal() { return null; }
        @Override public String getRequestedSessionId() { return null; }
        @Override public String getRequestURI() { return null; }
        @Override public StringBuffer getRequestURL() { return null; }
        @Override public String getServletPath() { return null; }
        @Override public HttpSession getSession() { return session; }
        @Override public boolean isRequestedSessionIdValid() { return false; }
        @Override public boolean isRequestedSessionIdFromCookie() { return false; }
        @Override public boolean isRequestedSessionIdFromURL() { return false; }
        @Override public boolean isRequestedSessionIdFromUrl() { return false; }
        @Override public Object getAttribute(String name) { return null; }
        @Override public Enumeration<String> getAttributeNames() { return Collections.emptyEnumeration(); }
        @Override public String getCharacterEncoding() { return null; }
        @Override public void setCharacterEncoding(String env) {}
        @Override public int getContentLength() { return 0; }
        @Override public long getContentLengthLong() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public ServletInputStream getInputStream() { return null; }
        @Override public String getLocalAddr() { return "127.0.0.1"; }
        @Override public String getLocalName() { return "localhost"; }
        @Override public int getLocalPort() { return 8080; }
        @Override public Locale getLocale() { return Locale.getDefault(); }
        @Override public Enumeration<Locale> getLocales() { return Collections.emptyEnumeration(); }
        @Override public String getParameter(String name) { return null; }
        @Override public Map<String, String[]> getParameterMap() { return Collections.emptyMap(); }
        @Override public Enumeration<String> getParameterNames() { return Collections.emptyEnumeration(); }
        @Override public String[] getParameterValues(String name) { return new String[0]; }
        @Override public String getProtocol() { return "HTTP/1.1"; }
        @Override public String getRemoteAddr() { return "127.0.0.1"; }
        @Override public String getRemoteHost() { return "localhost"; }
        @Override public int getRemotePort() { return 8080; }
        @Override public RequestDispatcher getRequestDispatcher(String path) { return null; }
        @Override public String getScheme() { return "http"; }
        @Override public String getServerName() { return "localhost"; }
        @Override public int getServerPort() { return 8080; }
        @Override public boolean isSecure() { return false; }
        @Override public void removeAttribute(String name) {}
        @Override public void setAttribute(String name, Object value) {}
        @Override public ServletContext getServletContext() { return null; }
        @Override public AsyncContext startAsync() { return null; }
        @Override public AsyncContext startAsync(ServletRequest req, ServletResponse res) { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public AsyncContext getAsyncContext() { return null; }
        @Override public DispatcherType getDispatcherType() { return DispatcherType.REQUEST; }
        @Override public String getRealPath(String path) { return null; }
        @Override public boolean authenticate(HttpServletResponse response) { return false; }
        @Override public void login(String username, String password) {}
        @Override public void logout() {}
        @Override public Collection<Part> getParts() { return Collections.emptyList(); }
        @Override public Part getPart(String name) { return null; }
        @Override public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
        @Override public String changeSessionId() { return "new-session-id"; }
        @Override public BufferedReader getReader() throws IOException { 
            return new BufferedReader(new StringReader("")); 
        }
    }

    static class TestResponse implements HttpServletResponse {
        private String redirectUrl;
        private final Map<String, String> headers = new HashMap<>();
        
        @Override
        public void sendRedirect(String location) {
            this.redirectUrl = location;
        }
        
        @Override
        public void setHeader(String name, String value) {
            headers.put(name, value);
        }
        
        @Override
        public void setDateHeader(String name, long date) {
            headers.put(name, String.valueOf(date));
        }

        // Response implementation
        @Override public void addCookie(Cookie cookie) {}
        @Override public boolean containsHeader(String name) { return false; }
        @Override public String encodeURL(String url) { return url; }
        @Override public String encodeRedirectURL(String url) { return url; }
        @Override public String encodeUrl(String url) { return url; }
        @Override public String encodeRedirectUrl(String url) { return url; }
        @Override public void sendError(int sc, String msg) {}
        @Override public void sendError(int sc) {}
        @Override public void setStatus(int sc) {}
        @Override public int getStatus() { return 200; }
        @Override public String getHeader(String name) { return null; }
        @Override public Collection<String> getHeaders(String name) { return Collections.emptyList(); }
        @Override public Collection<String> getHeaderNames() { return Collections.emptyList(); }
        @Override public void addHeader(String name, String value) {}
        @Override public void setIntHeader(String name, int value) {}
        @Override public void addIntHeader(String name, int value) {}
        @Override public void setContentType(String type) {}
        @Override public void setBufferSize(int size) {}
        @Override public int getBufferSize() { return 0; }
        @Override public void flushBuffer() {}
        @Override public void resetBuffer() {}
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void setLocale(Locale loc) {}
        @Override public Locale getLocale() { return Locale.getDefault(); }
        @Override public String getCharacterEncoding() { return "UTF-8"; }
        @Override public void setCharacterEncoding(String charset) {}
        @Override public String getContentType() { return "text/html"; }
        @Override public ServletOutputStream getOutputStream() { return null; }
        @Override public PrintWriter getWriter() { return new PrintWriter(System.out); }
        @Override public void setContentLength(int len) {}
        @Override public void setContentLengthLong(long len) {}
        @Override public void setStatus(int sc, String sm) { setStatus(sc); }
        @Override public void addDateHeader(String name, long date) { headers.put(name, String.valueOf(date)); }
    }

    static class TestSession implements HttpSession {
        private boolean invalidated = false;
        private final Map<String, Object> attributes = new HashMap<>();
        
        @Override
        public void invalidate() {
            invalidated = true;
        }
        
        public boolean isInvalidated() {
            return invalidated;
        }

        // Session implementation
        @Override public long getCreationTime() { return 0; }
        @Override public String getId() { return "test-session"; }
        @Override public long getLastAccessedTime() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public void setMaxInactiveInterval(int interval) {}
        @Override public int getMaxInactiveInterval() { return 0; }
        @Override public Object getAttribute(String name) { return attributes.get(name); }
        @Override public Enumeration<String> getAttributeNames() { 
            return Collections.enumeration(attributes.keySet()); 
        }
        @Override public void setAttribute(String name, Object value) { 
            attributes.put(name, value); 
        }
        @Override public void removeAttribute(String name) { 
            attributes.remove(name); 
        }
        @Override public boolean isNew() { return false; }
        
        // Deprecated methods
        @SuppressWarnings("deprecation")
		@Override public HttpSessionContext getSessionContext() { return null; }
        @Override public Object getValue(String name) { return getAttribute(name); }
        @Override public String[] getValueNames() { 
            return attributes.keySet().toArray(new String[0]); 
        }
        @Override public void putValue(String name, Object value) { 
            setAttribute(name, value); 
        }
        @Override public void removeValue(String name) { 
            removeAttribute(name); 
        }
    }

    @Test
    void logoutWithActiveSession() throws Exception {
        LogoutServlet servlet = new LogoutServlet();
        TestRequest request = new TestRequest();
        TestResponse response = new TestResponse();
        TestSession session = new TestSession();
        
        request.setSession(session);
        servlet.doGet(request, response);

        // Verify session invalidation
        assertTrue(session.isInvalidated());
        
        // Verify cache headers
        assertEquals("no-cache, no-store, must-revalidate", response.headers.get("Cache-Control"));
        assertEquals("no-cache", response.headers.get("Pragma"));
        assertNotNull(response.headers.get("Expires"));
        
        // Verify redirect
        assertEquals("/test/index.jsp", response.redirectUrl);
    }

    @Test
    void logoutWithoutSession() throws Exception {
        LogoutServlet servlet = new LogoutServlet();
        TestRequest request = new TestRequest();
        TestResponse response = new TestResponse();
        
        servlet.doGet(request, response);

        // Verify headers still set
        assertEquals("no-cache, no-store, must-revalidate", response.headers.get("Cache-Control"));
        
        // Verify redirect
        assertEquals("/test/index.jsp", response.redirectUrl);
    }
}