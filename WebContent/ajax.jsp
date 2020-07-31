<%@ page import="java.io.*" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="ajax.AjaxProcessor" %>

<%
	AjaxProcessor ajaxCall = AjaxProcessor.getInstance(request);
	ajaxCall.getTableData(request,response);
	
%>