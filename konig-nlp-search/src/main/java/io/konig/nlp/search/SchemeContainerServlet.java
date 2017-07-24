package io.konig.nlp.search;

/*
 * #%L
 * Konig Content System, Google App Engine implementation
 * %%
 * Copyright (C) 2015 - 2017 Gregory McFall
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import io.konig.yaml.Yaml;
import io.konig.yaml.YamlParseException;

public class SchemeContainerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	  
		try {
		    ServletFileUpload upload = new ServletFileUpload();
		    FileItemIterator iter = upload.getItemIterator(req);
		    FileItemStream item = iter.next();
		    InputStream stream = item.openStream();
		    
		    NaturalLanguageScheme scheme = Yaml.read(NaturalLanguageScheme.class, stream);
		    
		    NaturalLanguageIndex index = new NaturalLanguageIndex();
		    
		    index.put(scheme);
		   
		    // respond to query
		    res.setContentType("text/html");
		    PrintWriter out = res.getWriter();
		    out.println("<html>");
		    out.println("<body>");
		    out.print("OK!");
		    out.println("<p>");
		    res.getOutputStream().write("OK!".getBytes());
		} catch (FileUploadException | YamlParseException e) {
			throw new ServletException(e);
		}
	}
}
