<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="frm" %>
<!DOCTYPE html>
<html>
<script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="../js/hotel.js"></script>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body style='height: 100vh' class='w-100'>
<div class='mt-5 d-flex justify-content-center'>
    <frm:form action = '/register' method='post'>
        <div class='form-group'>
            <label>
                Please Enter Username:
            </label>
            <input class='form-control' type = 'text' id="signup_name" name='username'/>
        </div>
        <div class='form-group'>
            <label>
                Please Enter Password:
            </label>
            <input class='form-control' type = 'password' id = "signup_password" name='password'/>
        </div>
        <div class='form-group'>
            <label>
                Please Enter Email:
            </label>
            <input class='form-control' type = 'email' id="signup_email" name='email'/>
        </div>
        <div class='form-group'>
            <label>
                Register As:
            </label>
            <select id="signup_role">
                <option>USER</option>
                <option>ADMIN</option>
            </select>
        </div>
        <input class='btn btn-primary mt-5' id="signupBtn" type = 'button' value='Sign up'/>
        <sec:csrfInput/>
    </frm:form>
</div>
</body>
</html>