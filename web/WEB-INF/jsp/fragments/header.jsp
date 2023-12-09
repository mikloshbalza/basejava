<%--
  Created by IntelliJ IDEA.
  User: tkach
  Date: 29.11.2023
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<div class="header">
    <a class="no-underline-anchor" href="resume?theme=${theme}">
        <div class="arrow-dot">
            <img src="img/left_arrow.svg" alt="">
        </div>
    </a>
    <a class="text-anchor" href="resume?theme=${theme == null ? 'light' : theme}">
        <span class="resumes-control-title">Управление резюме</span>
    </a>
</div>
