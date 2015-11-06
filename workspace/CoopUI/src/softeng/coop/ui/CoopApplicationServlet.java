package softeng.coop.ui;

import java.io.*;
import java.security.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.vaadin.*;
import com.vaadin.terminal.gwt.server.*;

import softeng.coop.business.*;
import softeng.coop.business.contextjpa.*;

public class CoopApplicationServlet extends ApplicationServlet
{
	private static final long serialVersionUID = 1L;
	
	private static ThreadLocal<CoopApplication> currentApplication =
		new ThreadLocal<CoopApplication>();

	private static class RequestContext
	{
		private CoopApplicationServlet servlet;
		
		private HttpServletRequest request;
		
		public RequestContext(CoopApplicationServlet servlet, HttpServletRequest request)
		{
			if (servlet == null) 
				throw new IllegalArgumentException("Argument 'servlet' must not be null.");
			
			if (request == null) 
				throw new IllegalArgumentException("Argument 'request' must not be null.");
			
			this.servlet = servlet;
			this.request = request;
		}

		public CoopApplicationServlet getServlet()
		{
			return servlet;
		}

		public HttpServletRequest getRequest()
		{
			return request;
		}
	}
	
	private static ThreadLocal<RequestContext> currentRequestContext =
		new ThreadLocal<RequestContext>();
	
	private static final String[] emptyValues = new String[] { "" }; 
	
	private class RestartHttpServletRequest implements HttpServletRequest
	{
		private HttpServletRequest decoratedRequest;
		
		private Hashtable<Object, Object> parametersMap;
		
		private static final String restartApplicationParameter = "restartApplication";
		
		@SuppressWarnings("unchecked")
		public RestartHttpServletRequest(HttpServletRequest decoratedRequest)
		{
			if (decoratedRequest == null) 
				throw new IllegalArgumentException("Argument 'decoratedRequest' must not be null.");
			
			this.decoratedRequest = decoratedRequest;
			this.parametersMap = new Hashtable<Object, Object>(decoratedRequest.getParameterMap());
			
			if (!this.parametersMap.containsKey(restartApplicationParameter))
			{
				this.parametersMap.put(restartApplicationParameter, emptyValues);
			}
		}

		@Override
		public Object getAttribute(String arg0)
		{
			return decoratedRequest.getAttribute(arg0);
		}

		@Override
		public Enumeration<?> getAttributeNames()
		{
			return decoratedRequest.getAttributeNames();
		}

		@Override
		public String getCharacterEncoding()
		{
			return decoratedRequest.getCharacterEncoding();
		}

		@Override
		public int getContentLength()
		{
			return decoratedRequest.getContentLength();
		}

		@Override
		public String getContentType()
		{
			return decoratedRequest.getContentType();
		}

		@Override
		public ServletInputStream getInputStream() throws IOException
		{
			return decoratedRequest.getInputStream();
		}

		@Override
		public String getLocalAddr()
		{
			return decoratedRequest.getLocalAddr();
		}

		@Override
		public String getLocalName()
		{
			return decoratedRequest.getLocalName();
		}

		@Override
		public int getLocalPort()
		{
			return decoratedRequest.getLocalPort();
		}

		@Override
		public Locale getLocale()
		{
			return decoratedRequest.getLocale();
		}

		@Override
		public Enumeration<?> getLocales()
		{
			return decoratedRequest.getLocales();
		}

		@Override
		public String getParameter(String arg0)
		{
			String[] values = (String[])parametersMap.get(arg0);
			
			if (values == null) return null;
			
			if (values.length == 0) return null;
			
			return values[0];
		}

		@Override
		public Map<?, ?> getParameterMap()
		{
			return parametersMap;
		}

		@Override
		public Enumeration<?> getParameterNames()
		{
			return parametersMap.keys();
		}

		@Override
		public String[] getParameterValues(String arg0)
		{
			return (String[])parametersMap.get(arg0);
		}

		@Override
		public String getProtocol()
		{
			return decoratedRequest.getProtocol();
		}

		@Override
		public BufferedReader getReader() throws IOException
		{
			return decoratedRequest.getReader();
		}

		@SuppressWarnings("deprecation")
		@Override
		public String getRealPath(String arg0)
		{
			return decoratedRequest.getRealPath(arg0);
		}

		@Override
		public String getRemoteAddr()
		{
			return decoratedRequest.getRemoteAddr();
		}

		@Override
		public String getRemoteHost()
		{
			return decoratedRequest.getRemoteHost();
		}

		@Override
		public int getRemotePort()
		{
			return decoratedRequest.getRemotePort();
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String arg0)
		{
			return decoratedRequest.getRequestDispatcher(arg0);
		}

		@Override
		public String getScheme()
		{
			return decoratedRequest.getScheme();
		}

		@Override
		public String getServerName()
		{
			return decoratedRequest.getServerName();
		}

		@Override
		public int getServerPort()
		{
			return decoratedRequest.getServerPort();
		}

		@Override
		public boolean isSecure()
		{
			return decoratedRequest.isSecure();
		}

		@Override
		public void removeAttribute(String arg0)
		{
			decoratedRequest.removeAttribute(arg0);
		}

		@Override
		public void setAttribute(String arg0, Object arg1)
		{
			decoratedRequest.setAttribute(arg0, arg1);
		}

		@Override
		public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException
		{
			decoratedRequest.setCharacterEncoding(arg0);
		}

		@Override
		public String getAuthType()
		{
			return decoratedRequest.getAuthType();
		}

		@Override
		public String getContextPath()
		{
			return decoratedRequest.getContextPath();
		}

		@Override
		public Cookie[] getCookies()
		{
			return decoratedRequest.getCookies();
		}

		@Override
		public long getDateHeader(String arg0)
		{
			return decoratedRequest.getDateHeader(arg0);
		}

		@Override
		public String getHeader(String arg0)
		{
			return decoratedRequest.getHeader(arg0);
		}

		@Override
		public Enumeration<?> getHeaderNames()
		{
			return decoratedRequest.getHeaderNames();
		}

		@Override
		public Enumeration<?> getHeaders(String arg0)
		{
			return decoratedRequest.getHeaders(arg0);
		}

		@Override
		public int getIntHeader(String arg0)
		{
			return decoratedRequest.getIntHeader(arg0);
		}

		@Override
		public String getMethod()
		{
			return decoratedRequest.getMethod();
		}

		@Override
		public String getPathInfo()
		{
			return decoratedRequest.getPathInfo();
		}

		@Override
		public String getPathTranslated()
		{
			return decoratedRequest.getPathTranslated();
		}

		@Override
		public String getQueryString()
		{
			return decoratedRequest.getQueryString();
		}

		@Override
		public String getRemoteUser()
		{
			return decoratedRequest.getRemoteUser();
		}

		@Override
		public String getRequestURI()
		{
			return decoratedRequest.getRequestURI();
		}

		@Override
		public StringBuffer getRequestURL()
		{
			return decoratedRequest.getRequestURL();
		}

		@Override
		public String getRequestedSessionId()
		{
			return decoratedRequest.getRequestedSessionId();
		}

		@Override
		public String getServletPath()
		{
			return decoratedRequest.getServletPath();
		}

		@Override
		public HttpSession getSession()
		{
			return decoratedRequest.getSession();
		}

		@Override
		public HttpSession getSession(boolean arg0)
		{
			return decoratedRequest.getSession(arg0);
		}

		@Override
		public Principal getUserPrincipal()
		{
			return decoratedRequest.getUserPrincipal();
		}

		@Override
		public boolean isRequestedSessionIdFromCookie()
		{
			return decoratedRequest.isRequestedSessionIdFromCookie();
		}

		@Override
		public boolean isRequestedSessionIdFromURL()
		{
			return decoratedRequest.isRequestedSessionIdFromURL();
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean isRequestedSessionIdFromUrl()
		{
			return decoratedRequest.isRequestedSessionIdFromUrl();
		}

		@Override
		public boolean isRequestedSessionIdValid()
		{
			return decoratedRequest.isRequestedSessionIdValid();
		}

		@Override
		public boolean isUserInRole(String arg0)
		{
			return decoratedRequest.isUserInRole(arg0);
		}
		
	}
	
	public CoopApplicationServlet()
	{
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		currentRequestContext.set(new RequestContext(this, request));

		switch (getRequestType(request))
		{
			case OTHER:
				request = new RestartHttpServletRequest(request);
				break;
		}

		try
		{
			CoopApplication application;
			
			try
			{
				application = (CoopApplication)
					this.getExistingApplication(request);
			}
			catch (Exception ex)
			{
				application = null;
			}
			
			if (application != null)
			{
				currentApplication.set(application);
				
				Session session = application.getSession();
			
				if (session != null)
				{
					CurrentContext.set(session);
				}
			}

			super.service(request, response);
			
		}
		finally
		{
			CurrentContext.set(null);
			currentApplication.set(null);
			currentRequestContext.set(null);
		}
	}
	
	/**
	 * Get the application associated with this server thread, if any,
	 * else null.
	 */
	public static CoopApplication getCurrentApplication()
	{
		RequestContext requestContext = currentRequestContext.get();
		
		if (requestContext == null) return null;
		
		return requestContext.getServlet()
			.getExistingApplication(requestContext.getRequest());
	}
	
	public CoopApplication getExistingApplication(HttpServletRequest request) 
	{
		try
		{
			WebApplicationContext context = this.getApplicationContext(request.getSession());
			
			for (Application application : context.getApplications())
			{
	      String applicationPath = application.getURL().getPath();
	      	
	      String requestPath = getApplicationUrl(request).getPath();
	      
	      if (requestPath.equals(applicationPath))
	      	return (CoopApplication)application;
			}
		}
		catch (Exception ex)
		{
		}
		
		return null;
	}
	
	public Application getNewApplication(HttpServletRequest request)
	throws ServletException
	{
		return super.getNewApplication(request);
	}
}
