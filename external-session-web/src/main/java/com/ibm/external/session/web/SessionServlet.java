package com.ibm.external.session.web;

import com.ibm.external.session.ejb.SessionEJB;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Date;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {

    @EJB
    private SessionEJB sessionEJB;

    public void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        final HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();
        final String title = "Simple HttpSession Demo";
        final String heading = session.isNew() ? "Hello stranger" : "Welcome back friend";
        final Integer accessCount = this.getAccessAccount(session);
        session.setAttribute("accessCount", accessCount);
        final InetAddress localhost = InetAddress.getLocalHost();
        this.printOutput(out, title, heading, session, accessCount, localhost);
    }

    private void printOutput(final PrintWriter out, final String title, final String heading,
                             final HttpSession session, final Integer accessCount, final InetAddress localhost) {
        out.println("<HTML><HEAD><TITLE>" + title + "</TITLE>\n" +
                "<meta http-equiv=\"Cache-Control\" content=\"no-cache, no-store, must-revalidate\">\n" +
                "<meta http-equiv=\"Pragma\" content=\"no-cache\">\n" +
                "<meta http-equiv=\"Expires\" content=\"0\">\n" +
                "</HEAD>\n" +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=\"CENTER\">" + heading + "</H1>\n" +
                "<H2>Information on your HTTPSession:</H2>\n" +
                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                "<TR BGCOLOR=\"#FFAD00\">\n" +
                "<TH>Field name</TH><TH>Value</TH>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>ID</TD>\n" +
                "  <TD>" + session.getId() + "</TD>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>Creation time</TD>\n" +
                "  <TD>" + new Date(session.getCreationTime()) + "</TD>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>Time of last access</TD>\n" +
                "  <TD>" + new Date(session.getLastAccessedTime()) + "</TD>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>Number of previous accesses</TD>\n" +
                "  <TD>" + accessCount + "</TD>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>POD Name</TD>\n" +
                "  <TD>" + localhost.getHostName() + "</TD>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>POD IP address</TD>\n" +
                "  <TD>" + localhost.getHostAddress().trim() + "</TD>\n" +
                "</TR>\n" +
                "</TABLE>\n" +
                "</BODY></HTML>");
        out.println("Value from EJB " + sessionEJB.count());
    }

    private Integer getAccessAccount(final HttpSession session) {
        Integer accessCount = new Integer(0);
        if (!session.isNew()) {
            final Integer oldAccessCount = (Integer) session.getAttribute("accessCount");
            if (oldAccessCount != null) {
                accessCount = new Integer(oldAccessCount.intValue() + 1);
            }
        }
        return accessCount;
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
