package org.example;
//
// Say hello.
//

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        // Very simple - just return some plain text
	response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
	writer.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
                                        "Transitional//EN\">\n" +
                "<HTML>\n" +
                "<HEAD><TITLE>Hello WWW</TITLE></HEAD>\n" +
                "<BODY>\n" +
                "<H1>Hello World!</H1>\n" +
                "<H2>Hello WWW</H2>\n" +
                "<H3>Hello WWW</H3>\n" +
                "<H4><font color=\"blue\">Hello WWW</font></H4>\n" +
                "<H4><font color=\"red\">Hello WWW</font></H4>\n" +
                "</BODY></HTML>");

    }
}
