package com.piccloud.config;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ApplicationConf
 */
@WebServlet("/ApplicationConf")
public class ApplicationConf extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String __ROOT__;
    public static String __PUBLIC__;
    public static String __PLUGIN__;
    public static String __UPLOAD__;
    public static String __NAME__;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplicationConf() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {  
		ServletContext application = this.getServletContext();
		
		__ROOT__  = "/PicCloud";
		__PUBLIC__  = __ROOT__ + "/public";
		__PLUGIN__	 = __ROOT__ + "/public/plugins";
		__UPLOAD__  =  __ROOT__ + "/public/upload";
		__NAME__  = "PicCLoud-电商图片云"; 
		
		application.setAttribute("WEBROOT", __ROOT__);
		application.setAttribute("PUBLIC", __PUBLIC__);
		application.setAttribute("PLUGIN", __PLUGIN__);
		application.setAttribute("UPLOAD", __UPLOAD__);
		application.setAttribute("NAME", __NAME__);
    }  

}

