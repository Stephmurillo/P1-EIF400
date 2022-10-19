/**
Embedded Tomcat demos
@author
Based on https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat
Slightly modified by loriacarlos@gmail.com
@since 2022
*/

package com.eif400.web;

import javax.servlet.ServletException;
 
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
 
public class RunWarExample {
 
    public static void main(String[] args) throws ServletException, LifecycleException {
        int port = 8080;
        String contextPath = "/";     
        String warFilePath = "app.war";
        
        try{
            if (args.length > 0 ){
                port = Integer.valueOf(args[0]);
            }
        } catch (Exception e){
            System.out.format("Invalid port '%s' %n", args[0]);
            System.exit(-1);
        }
        
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(".");
        tomcat.setPort(port);
         
        
        tomcat.getHost().setAppBase(".");
         
        tomcat.addWebapp(contextPath, warFilePath);
         
        tomcat.start();
        tomcat.getServer().await();
    }
}