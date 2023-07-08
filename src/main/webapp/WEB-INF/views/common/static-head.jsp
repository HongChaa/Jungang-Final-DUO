<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/assets/css/common/header.css">
<link rel="stylesheet" href="/assets/css/main.css">
<link rel="stylesheet" href="/assets/css/review/chatting-review.css">

<c:if test="${login ne null}">
    <script src="http://localhost:3000/socket.io/socket.io.js" defer></script>
</c:if>
<script src="/assets/js/common/header.js" type="module" defer></script>
<script src="/assets/js/review/review-rate.js" defer type="module"></script>

