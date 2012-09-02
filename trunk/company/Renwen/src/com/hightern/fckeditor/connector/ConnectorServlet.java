/*
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net Copyright (C) 2004-2010 Frederico Caldeira Knabben == BEGIN LICENSE == Licensed under the terms of any of the following licenses
 * at your choice: - GNU General Public License Version 2 or later (the "GPL") http://www.gnu.org/licenses/gpl.html - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 * http://www.gnu.org/licenses/lgpl.html - Mozilla Public License Version 1.1 or later (the "MPL") http://www.mozilla.org/MPL/MPL-1.1.html == END LICENSE ==
 */
package com.hightern.fckeditor.connector;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hightern.fckeditor.requestcycle.ThreadLocalData;
import com.hightern.fckeditor.response.GetResponse;
import com.hightern.fckeditor.response.UploadResponse;

/**
 * Connector servlet of the File Browser. It accepts requests begins the request cycle, forwards requests to the {@link Dispatcher dispatcher} and ends the request cycle with an appropriate
 * {@link com.hightern.fckeditor.response response}.
 * 
 * @version $Id: ConnectorServlet.java 4785 2009-12-21 20:10:28Z mosipov $
 */
public class ConnectorServlet extends HttpServlet {
	private static final long serialVersionUID = -5742008970929377161L;
	private final Logger logger = LoggerFactory.getLogger(ConnectorServlet.class);
	private transient Dispatcher dispatcher;
	
	/**
	 * Initializes this servlet. It initializes the dispatcher internally.
	 * 
	 * @throws ServletException
	 *             if an exception occurs that interrupts the servlet's normal operation
	 */
	@Override
	public void init() throws ServletException {
		try {
			dispatcher = new Dispatcher(getServletContext());
		} catch (final Exception e) {
			logger.error("Dispatcher could not be initialized", e);
			throw new ServletException(e);
		}
	}
	
	/**
	 * Passes a GET request to the dispatcher.
	 * 
	 * @throws IOException
	 *             if an input or output error is detected when the servlet handles the GET request
	 * @throws ServletException
	 *             if the request for the GET could not be handled
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/xml");
		response.setHeader("Cache-Control", "no-cache");
		final PrintWriter out = response.getWriter();
		GetResponse getResponse = null;
		
		try {
			ThreadLocalData.beginRequest(request);
			getResponse = dispatcher.doGet(request);
		} catch (final Exception e) {
			throw new ServletException(e);
		} finally {
			/*
			 * call this method to prevent detached requests or else the request will probably never be garbage collected and will fill your memory
			 */
			ThreadLocalData.endRequest();
		}
		
		out.print(getResponse);
		out.flush();
		out.close();
	}
	
	/**
	 * Passes a POST request to the dispatcher.
	 * 
	 * @throws IOException
	 *             if an input or output error is detected when the servlet handles the request
	 * @throws ServletException
	 *             if the request for the POST could not be handled
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		final PrintWriter out = response.getWriter();
		UploadResponse uploadResponse = null;
		
		try {
			ThreadLocalData.beginRequest(request);
			uploadResponse = dispatcher.doPost(request);
		} catch (final Exception e) {
			throw new ServletException(e);
		} finally {
			/*
			 * call this method to prevent detached requests or else the request will probably never be garbage collected and will fill your memory
			 */
			ThreadLocalData.endRequest();
		}
		
		out.print(uploadResponse);
		out.flush();
		out.close();
	}
	
}