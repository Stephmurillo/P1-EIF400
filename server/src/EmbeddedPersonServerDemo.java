/**
Embedded Tomcat Server Demo
@author
Based on https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat
Enhanced by loriacarlos@gmail.com
@since 2022
*/

package com.eif400.web;

import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.descriptor.web.ErrorPage;

public class EmbeddedPersonServerDemo {

    public static void main(String[] args) throws Exception {

        String webappsDirLocation = "";
        String CONFIG_PROPS = "/WEB-INF/props";
        int port = 8000;

        try{
            if (args.length > 0 ){
                port = Integer.valueOf(args[0]);
            }
        } catch (Exception e){
            System.out.format("Invalid port number '%s' %n", args[0]);
            System.exit(-1);
        }
        // Init server
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf(port));
        
        
        // Init App Context
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", 
                                                                 new File(webappsDirLocation).getAbsolutePath());
        WebResourceRoot root = new StandardRoot(ctx);
        
        
        // Map Home pages
        var additionHomePages = new File("./resources/home");
        root.addPreResources(new DirResourceSet(root, "/",
                                                additionHomePages.getAbsolutePath(), "/"));
        ctx.setResources(root);

        // Map WEB-INF/classes 
        var additionWebInfClasses = new File("./classes");
        
        root.addPreResources(new DirResourceSet(root, "/WEB-INF/classes",
                                  additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(root);
        
        // Map WEB-INF/pages
        var additionWebPages = new File("./resources/pages");
        root.addPreResources(new DirResourceSet(root, "/WEB-INF/pages",
                                                additionWebPages.getAbsolutePath(), "/"));
        ctx.setResources(root);
        
        // Map WEB-INF/data 
        var additionWebData = new File("./resources/data");
        root.addPreResources(new DirResourceSet(root, "/WEB-INF/data",
                                                additionWebData.getAbsolutePath(), "/"));
        ctx.setResources(root);
        
        // Map WEB-INF/props 
        var additionWebProps = new File("./resources/props");
        root.addPreResources(new DirResourceSet(root, "/WEB-INF/props",
                                                additionWebProps.getAbsolutePath(), "/"));
        ctx.setResources(root);
        
        // Add Error page
        var errorPage = new ErrorPage();
        errorPage.setErrorCode(HttpServletResponse.SC_NOT_FOUND);
        
        var errorLocation = "/WEB-INF/pages/404.html";
        errorPage.setLocation(errorLocation);
        ctx.addErrorPage(errorPage);
        
        // Start server
        tomcat.start();
        tomcat.getServer().await();
    }
}