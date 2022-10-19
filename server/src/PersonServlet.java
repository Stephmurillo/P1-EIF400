/**
A trivial servlet. Configured by annotation
@author loriacarlos@gmail.com
@since 2022
*/

package com.eif400.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;

import java.util.stream.Collectors;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletContext;

import java.util.Properties;


@WebServlet(
        name = "PersonServlet",
        urlPatterns = {"/person"}
)
public class PersonServlet extends HttpServlet {
    
    private static String PERSONS_FILE_PROPERTY = "persons";
    private static String PROPS_FILE = "/WEB-INF/props/configuration.properties";
    private static String ERROR_PAGE = "404.html";
    
    private String readPersonsJSON(InputStream is) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.lines().collect(Collectors.joining(""));
                   
    }
    private String getPersonsData(ServletContext context) throws IOException{
        var propsFile = context.getResourceAsStream(PROPS_FILE);
        var props = new Properties();
        props.load(propsFile);
        return props.getProperty(PERSONS_FILE_PROPERTY);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameter. For now it is ignored. To do: use it to filter
        var id = request.getParameter("id");
        System.out.println("PersonServlet::doGet: id=" + id);
        // Get data
        String json;
        try{
            ServletContext context = getServletContext();
            var persons_data = getPersonsData(context);
            json = readPersonsJSON(context.getResourceAsStream(persons_data));
        } catch (Throwable e){
            request.getRequestDispatcher(ERROR_PAGE)
                   .forward(request, response);
            return;
        }
        // Build response and reply
        PrintWriter writer = response.getWriter();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(json);
        writer.flush();
        
    }
}