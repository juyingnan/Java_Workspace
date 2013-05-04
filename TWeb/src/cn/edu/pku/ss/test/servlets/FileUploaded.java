package cn.edu.pku.ss.test.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.edu.pku.ss.test.util.IOUtil;
import cn.edu.pku.ss.test.util.PropertiesUtil;
import cn.edu.pku.ss.test.util.Tools;

/**
 *
 * @author crystal
 */
public class FileUploaded extends HttpServlet {

	
	
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    // 定义文件的上传路径
    private String uploadPath = PropertiesUtil.getInstance().readValue(PropertiesUtil.UPLOAD_PATH);
    
    // 限制文件的上传大小
    private int maxPostSize = 100 * 1024 * 1024;
    private static final String FILE_SESSION = "Filedata";
    public FileUploaded() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Access !");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);

        PrintWriter out = response.getWriter();
        
        //保存文件到服务器中

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4096);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(maxPostSize);
        try {
            List fileItems = upload.parseRequest(request);
            Iterator iter = fileItems.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    String name = item.getName();
                    //System.out.println(item.getFieldName());
                    //System.out.println(name);
                    try {
                    	String fileSession= item.getFieldName();
                    	if(fileSession.startsWith(FILE_SESSION) && fileSession.length()>FILE_SESSION.length()){
                    		Integer id = Integer.parseInt(fileSession.substring(FILE_SESSION.length()));                    		
                        	String filePath = uploadPath + id + name;
                            item.write(new File(filePath));
                            //System.out.println("Session:" + request.getSession().getAttribute("ID").toString());
                            String content = IOUtil.read(filePath);
                            System.out.println("Save to : " + id + " file:" + filePath);
                            SessionTable.Table.put(id, content);
                            //response.setContentType("text/txt");
                            //response.getWriter().println(content);
                           // SaveFile s = new SaveFile();

                           // s.saveFile(name);
                    	}
                 	  

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "结束");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {   
    	if(request.getParameter("Init")!=null){    	
    	    HttpSession session=request.getSession();//
    		Integer id = (Integer) session.getAttribute("ID");
    	    if(id==null) id = Tools.RANDOM.nextInt();
    	    if(id<0)id*=-1;
    	    session.setAttribute("ID", id);    	    
    	    System.out.println("Init Session = " + id);
    	    response.setContentType("text/html");
            response.setHeader("Pragma","No-cache");
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
    	    response.getWriter().write("" + id);
    	} else if(request.getParameter("ViewCode")!=null){
    		Integer id = Integer.parseInt(request.getParameter("ViewCode"));
    		System.out.println("View Code: " + id);
    		response.setContentType("text/html");
            response.setHeader("Pragma","No-cache");
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);		
    		if(SessionTable.Table.containsKey(id)){
    		   	response.getWriter().write(SessionTable.Table.get(id).toString());
    		} else {    			
    			response.getWriter().write("Session missed!");
    		}    			
    	} else {
    		processRequest(request, response);
    	}
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	//System.out.println("Post:" + request.getAttribute("ID"));
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>

}
