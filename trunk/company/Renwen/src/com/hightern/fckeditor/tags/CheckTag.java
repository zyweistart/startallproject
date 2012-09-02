/*
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net Copyright (C) 2004-2010 Frederico Caldeira Knabben == BEGIN LICENSE == Licensed under the terms of any of the following licenses
 * at your choice: - GNU General Public License Version 2 or later (the "GPL") http://www.gnu.org/licenses/gpl.html - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 * http://www.gnu.org/licenses/lgpl.html - Mozilla Public License Version 1.1 or later (the "MPL") http://www.mozilla.org/MPL/MPL-1.1.html == END LICENSE ==
 */
package com.hightern.fckeditor.tags;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.hightern.fckeditor.handlers.RequestCycleHandler;
import com.hightern.fckeditor.localization.LocalizedMessages;
import com.hightern.fckeditor.tool.Compatibility;

/**
 * Displays information about browser and user capabilities.
 * <p>
 * There are four available commands:
 * <ol>
 * <li><code>CompatibleBrowser</code></li>
 * <li><code>CreateFolder</code></li>
 * <li><code>FileUpload</code></li>
 * <li><code>GetResources</code></li>
 * </ol>
 * </p>
 * Each call will emit a localized message (if available) about the command's status.
 * 
 * @version $Id: CheckTag.java 4812 2009-12-23 21:14:49Z mosipov $
 */
public class CheckTag extends TagSupport {
	
	private static final long serialVersionUID = -6834095891675681686L;
	
	private static final String FILE_UPLOAD = "FileUpload";
	private static final String GET_RESOURCES = "GetResources";
	private static final String CREATE_FOLDER = "CreateFolder";
	private static final String COMPATIBLE_BROWSER = "CompatibleBrowser";
	
	private static final Set<String> commands = new HashSet<String>(4);
	
	static {
		CheckTag.commands.add(CheckTag.FILE_UPLOAD);
		CheckTag.commands.add(CheckTag.COMPATIBLE_BROWSER);
		CheckTag.commands.add(CheckTag.GET_RESOURCES);
		CheckTag.commands.add(CheckTag.CREATE_FOLDER);
	}
	
	private String command;
	
	/**
	 * Sets the desired command.
	 * 
	 * @param command
	 *            a command to check
	 * @throws JspTagException
	 *             if the provided command does not exist
	 */
	public void setCommand(String command) throws JspTagException {
		if (!CheckTag.commands.contains(command)) { throw new JspTagException("You have to provide one of the following commands: "
				+ CheckTag.commands); }
		this.command = command;
	}
	
	@Override
	public int doStartTag() throws JspException {
		final JspWriter out = pageContext.getOut();
		
		final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		final LocalizedMessages lm = LocalizedMessages.getInstance(request);
		String response = null;
		
		if (command.equals(CheckTag.FILE_UPLOAD)) {
			if (RequestCycleHandler.isFileUploadEnabled(request)) {
				response = lm.getFileUploadEnabled();
			} else {
				response = lm.getFileUploadDisabled();
			}
		}
		
		if (command.equals(CheckTag.GET_RESOURCES)) {
			if (RequestCycleHandler.isGetResourcesEnabled(request)) {
				response = lm.getGetResourcesEnabled();
			} else {
				response = lm.getGetResourcesDisabled();
			}
		}
		
		if (command.equals(CheckTag.CREATE_FOLDER)) {
			if (RequestCycleHandler.isCreateFolderEnabled(request)) {
				response = lm.getCreateFolderEnabled();
			} else {
				response = lm.getCreateFolderDisabled();
			}
		}
		
		if (command.equals(CheckTag.COMPATIBLE_BROWSER)) {
			if (Compatibility.isCompatibleBrowser(request)) {
				response = lm.getCompatibleBrowserYes();
			} else {
				response = lm.getCompatibleBrowserNo();
			}
		}
		
		try {
			out.print(response);
		} catch (final IOException e) {
			throw new JspException("Tag response could not be written to the user!", e);
		}
		
		return Tag.SKIP_BODY;
	}
	
}