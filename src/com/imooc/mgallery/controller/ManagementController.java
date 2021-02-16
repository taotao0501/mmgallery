package com.imooc.mgallery.controller;

import com.imooc.mgallery.entity.Painting;
import com.imooc.mgallery.service.PaintingService;
import com.imooc.mgallery.utils.PageModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@WebServlet("/management")
public class ManagementController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PaintingService paintingService = new PaintingService();

    /**
     * 对不同的请求方法进行分发
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); //为了doPostd调用 doGet
        resp.setContentType("text/html;charset=utf-8");
        String method = req.getParameter("method");
        if(method.equals("list")){
            this.list(req,resp);
        } else if(method.equals("delete")) {
            this.delete(req,resp);
        } else if(method.equals("show_create")){
            this.show_createPage(req,resp);
        } else if(method.equals("create")){
            this.create(req,resp);
        } else if(method.equals("show_update")) {
            this.showUpdatePage(req, resp);
        } else if (method.equals("update")) {
            this.update(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 分页展示
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        String r = req.getParameter("r");
        if(p == null) {
            p = "1";
        }
        if(r == null) {
            r = "6";
        }
        PageModel pageModel = paintingService.pagination(Integer.parseInt(p),Integer.parseInt(r));
        req.setAttribute("pageModel", pageModel);
        req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req,resp);
    }

    private void show_createPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/create.jsp").forward(req,resp);
    }

    private void showUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Painting painting = paintingService.findById(Integer.parseInt(id));
        req.setAttribute("painting", painting);
        req.getRequestDispatcher("/WEB-INF/jsp/update.jsp").forward(req,resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //文件上传时的数据处理与标准表单完全不同
//        String pname = req.getParameter("pname");
//        System.out.println(pname);
        //1.初始化FileUpload组件
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sf = new ServletFileUpload(factory);
        //2.遍历所有表单项FileItem
        try {
            List<FileItem> formData = sf.parseRequest(req);
            Painting painting = new Painting();
            for(FileItem fi:formData) {
                if (fi.isFormField()) {
                    System.out.println("普通输入项：" + fi.getFieldName() + ": " + fi.getString("UTF-8"));
                    switch(fi.getFieldName()){
                        case "pname":
                            painting.setPname(fi.getString("UTF-8"));
                            break;
                        case "category":
                            painting.setCategory(Integer.parseInt(fi.getString("UTF-8")));
                            break;
                        case "price":
                            painting.setPrice(Integer.parseInt(fi.getString("UTF-8")));
                            break;
                        case "description":
                            painting.setDescription(fi.getString("UTF-8"));
                            break;
                        default:
                            break;
                    }
                } else {
                    System.out.println("文件上传项:" + fi.getFieldName());
                    //3.文件保存到服务器目录中
                    String path = req.getServletContext().getRealPath("/upload");
                    System.out.println("上传文件目录:" + path);
                    String fileName = UUID.randomUUID().toString();
                    String suffix = fi.getName().substring(fi.getName().lastIndexOf("."));
                    fi.write(new File(path,fileName + suffix));
                    painting.setPreview("upload/" + fileName + suffix);
                }
            }
            paintingService.create(painting);//新增功能
            //这里用响应重定向是因为 这里的 create与list界面没有强关系，只是一个需要的展示页面
            //前面使用 请求转发，是因为resp里面的数据会直接发送到对应的页面上显示。
            resp.sendRedirect("/mmgallery_war_exploded/management?method=list");

        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现油画更新
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int isPreviewModified = 0;
        FileItemFactory factory = new DiskFileItemFactory();
        /**
         * FileItemFactory 用于将前端表单的数据转换为一个个FileItem对象
         * ServletFileUpload 则是为FileUpload组件提供Java web的Http请求解析
         */
        ServletFileUpload sf = new ServletFileUpload(factory);
        //2. 遍历所有FileItem
        try {
            List<FileItem> formData = sf.parseRequest(req);
            Painting painting = new Painting();
            for(FileItem fi:formData) {
                if(fi.isFormField()) {
                    System.out.println("普通输入项:" + fi.getFieldName() + ":" + fi.getString("UTF-8"));
                    switch (fi.getFieldName()) {
                        case "pname":
                            painting.setPname(fi.getString("UTF-8"));
                            break;
                        case "category":
                            painting.setCategory(Integer.parseInt(fi.getString("UTF-8")));
                            break;
                        case "price":
                            painting.setPrice(Integer.parseInt(fi.getString("UTF-8")));
                            break;
                        case "description":
                            painting.setDescription(fi.getString("UTF-8"));
                            break;
                        case "id":
                            painting.setId(Integer.parseInt(fi.getString("UTF-8")));
                            break;
                        case "isPreviewModified":
                            isPreviewModified = Integer.parseInt(fi.getString("UTF-8"));
                            break;
                        default:
                            break;
                    }
                }else {
                    if(isPreviewModified == 1) {
                        System.out.println("文件上传项:" + fi.getFieldName());
                        //3.文件保存到服务器目录
                        String path = req.getServletContext().getRealPath("/upload");
                        System.out.println("上传文件目录:" + path);
                        //String fileName = "test.jpg";
                        String fileName = UUID.randomUUID().toString();
                        //fi.getName()得到原始文件名,截取最后一个.后所有字符串,例如:wxml.jpg->.jpg
                        String suffix = fi.getName().substring(fi.getName().lastIndexOf("."));
                        //fi.write()写入目标文件
                        fi.write(new File(path,fileName + suffix));
                        painting.setPreview("upload/" + fileName + suffix);
                    }
                }
            }
            //更新数据的核心方法
            paintingService.update(painting, isPreviewModified);
            resp.sendRedirect("/mmgallery_war_exploded/management?method=list");//返回列表页
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        PrintWriter out = resp.getWriter();
        try {
            paintingService.delete(Integer.parseInt(id));
            out.println("{\"result\":\"ok\"}");
        } catch(Exception e) {
            e.printStackTrace();
            out.println("{\"result\":\"" + e.getMessage() + "\"}");
        }
    }
}
