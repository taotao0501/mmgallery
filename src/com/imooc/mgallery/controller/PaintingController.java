package com.imooc.mgallery.controller;

import com.imooc.mgallery.service.PaintingService;
import com.imooc.mgallery.utils.PageModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/page")
public class PaintingController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    PaintingService paintingService = new PaintingService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("p");
        String rows = req.getParameter("r");
        String category = req.getParameter("c");
        if(page == null) {
            page = "1";
        }
        if(rows == null) {
            rows = "6";
        }
        PageModel pageModel = paintingService.pagination(Integer.parseInt(page),Integer.parseInt(rows),category);
        req.setAttribute("pageModel",pageModel);
        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req,resp);
    }
}
