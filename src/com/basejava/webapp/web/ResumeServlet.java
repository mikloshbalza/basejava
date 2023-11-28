package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SqlStorage;
import com.basejava.webapp.storage.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Resume> resumes = storage.getAllSorted();
        request.setAttribute("uuid1", resumes.get(0).getUuid());
        request.setAttribute("uuid2", resumes.get(1).getUuid());
        request.setAttribute("uuid3", resumes.get(2).getUuid());
        request.setAttribute("full_name1", resumes.get(0).getFullName());
        request.setAttribute("full_name2", resumes.get(1).getFullName());
        request.setAttribute("full_name3", resumes.get(2).getFullName());
        request.getRequestDispatcher("page.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
