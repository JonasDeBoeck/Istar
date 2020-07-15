<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" href="static/media/icon.png">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="static/css/styleMain3.css">
    <style>
        .space {
            position: relative;
            display: block;
            height: 100vh;
            width: 100%;
            background: #1B2735 radial-gradient(ellipse at bottom, #1B2735 0%, #090A0F 100%);
            color: white;
            padding-top: 20vh;
        }


        .stars > div {
            overflow: hidden;
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
        }

        .stars .stars-back {
            top: -50%;
            opacity: 0.5;
            background-image:
                    radial-gradient(2px 2px at 20px 30px, #eee, rgba(0,0,0,0)),
                    radial-gradient(2px 2px at 40px 70px, #fff, rgba(0,0,0,0)),
                    radial-gradient(1px 1px at 90px 40px, #fff, rgba(0,0,0,0)),
                    radial-gradient(2px 2px at 160px 120px, #ddd, rgba(0,0,0,0));
            background-repeat: repeat;
            background-size: 300px 300px;
            animation: stars 4s infinite linear;
        }

        .stars .stars-middle {
            background-image:
                    radial-gradient(3px 3px at 50px 160px, #ddd, rgba(0,0,0,0)),
                    radial-gradient(2px 2px at 90px 40px, #fff, rgba(0,0,0,0)),
                    radial-gradient(2px 2px at 130px 80px, #fff, rgba(0,0,0,0));
            background-repeat: repeat;
            background-size: 200px 200px;
            animation: stars 2.5s infinite linear;
        }

        .rocket {
            position: relative;
            width: 50px;
            margin: 0 auto;
            transition: transform .2s ease-in-out;
        }

        .fuselage {
            width: 50px;
        }

        .nose {
            width: 25px;
            margin: 0 auto;
            border-top: 15px solid #353535;
            border-top-left-radius: 50%;
            border-top-right-radius: 50%;
        }

        .head {
            possition: relative;
            display:block;
            border-bottom: 25px solid white;
            border-left: 10px solid transparent;
            border-right: 10px solid transparent;
            height: 0;
            width: 25px;
            margin: 0 auto;
        }

        .window {
            position: absolute;
            top:16px;
            left:16px;
            border-bottom: 15px solid #00a0d6;
            border-left: 5px solid transparent;
            border-right: 5px solid transparent;
            height: 0;
            width: 20px;
        }

        .neck{
            position: relative;
            width: 45px;
            height: 30px;
            margin: 0 auto;
            background: #f4f4f4;}

        .neck:after{
            position: absolute;
            top: 0;
            right: 0;
            content: '';
            width: 22.5px;
            height: 30px;
            background: #e0e0e0;
        }


        .body{
            position: relative;
            width: 50px;
            height: 120px;
            background: #f4f4f4;
            border-bottom: 3px solid #a81237;
            border-top: 3px solid #a81237;}

        .body:after{
            position: absolute;
            top: 0;
            right: 0;
            content: '';
            width: 25px;
            height: 120px;
            background: #e0e0e0;
        }


        .reactor {
            position: relative;
            border-bottom: 30px solid #4f4f4f;
            border-left: 10px solid transparent;
            border-right: 10px solid transparent;
            height: 0;
            width: 30px;
            margin: 0 auto;}

        .reactor:after {
            content: '';
            position: absolute;
            top: 0;
            left: -10px;
            width: 15px;
            opacity: 0.3;
            border-bottom: 30px solid #e0e0e0;
            border-left: 10px solid transparent;
        }


        .fire {
            position: relative;
            width: 25px;
            height: 70px;
            margin: 0 auto;
            border-bottom-right-radius: 50%;
            border-bottom-left-radius: 50%;
            animation: fire 0.2s infinite;}

        > div {
            position: absolute;
            background: #ED0303;
        }


        .spark1 {
            top: 30px;
            left: 5px;
            width: 40px;
            height: 40px;
            border-radius:50%;
            animation: fireBig 0.5s infinite;
        }

        .spark2 {
            top: 35px;
            right: 5px;
            width: 20px;
            height: 20px;
            border-radius:50%;
            animation: fireBig 0.3s infinite;
        }

        .spark3 {
            top: 40px;
            right: 20px;
            width: 50px;
            height: 50px;
            border-radius:50%;
            animation: fireSmall 0.4s infinite;
        }

        .spark4 {
            top: 40px;
            left: -20px;
            width: 30px;
            height: 30px;
            border-radius:50%;
            animation: fireSmall 0.7s infinite;
        }
        .spark5 {
            top: 30px;
            right: -10px;
            width: 10px;
            height: 10px;
            border-radius:50%;
            animation: fireSmall 0.6s infinite;
        }
        .spark6 {
            top: 60px;
            left: 5px;
            width: 10px;
            height: 10px;
            border-radius:50%;
            animation: fireSmall 0.2s infinite;
        }

        .left-fin {
            position: absolute;
            top : 73px;
            right: 50px;
            width: 5px;
            border-bottom: 110px solid white;
            border-left: 60px solid transparent;}

        .left-fin-end{
            position: absolute;
            top : 113px;
            right: 70px;
            border-bottom: 70px solid white;
            border-left: 70px solid transparent;
            border-top-left-radius: 50%;
            border-bottom-left-radius: 10%;
        }


        .right-fin {
            position: absolute;
            top : 73px;
            left: 50px;
            width: 5px;
            border-bottom: 110px solid white;
            border-right: 60px solid transparent;}


        .right-fin-end{
            position: absolute;
            top : 113px;
            left: 70px;
            border-bottom: 70px solid white;
            border-right: 70px solid transparent;
            border-bottom-right-radius: 10%;
        }

        .right-fin:after {
            content: '';
            z-index: 2;
            position: absolute;
            top : 0;
            left: 0;
            width: 4px;
            border-bottom: 110px solid #c4c4c4;
            border-right: 10px solid transparent;
        }



        .rocket:hover {
            transform: translate3d(30px, -30px, 30px) rotateY(30deg);}

        .window {
            left:16px;
            border-right: 6px solid transparent;
            width: 18px;
        }

        .body:after {
            width: 15px;
        }

        .neck:after {
            width: 13.5px;
        }

        .reactor:after {
            width: 25px;
        }

        .right-fin {
            border-right: 50px solid transparent;}

        .right-fin-end {
            border-right: 50px solid transparent;}

        .right-fin:after {
            border-right: 20px solid transparent;
        }

        @keyframes fire {
            0% {
                background: linear-gradient(to bottom, rgba(255,134,28,1) 0%, rgba(239,1,124,1) 50%, rgba(237,3,3,1) 100%);
            }
            50% {
                background: linear-gradient(to bottom, rgba(237,3,3,1) 0%, rgba(255,134,28,1) 51%, rgba(239,1,124,1) 100%);
            }
            100% {
                background: linear-gradient(to bottom, rgba(239,1,124,1) 0%, rgba(237,3,3,1) 51%, rgba(255,134,28,1) 100%);
            }
        }

        @keyframes fireBig {
            0% {
                width: 10px;
                height: 10px;
                background: #FF861C;
            }
            50% {
                background: #EF017C;
            }
            100% {
                width: 60px;
                height: 60px;
            }
        }

        @keyframes fireSmall {
            0% {
                width: 10px;
                height: 10px;
                background: #FF861C;
            }
            50% {
                background: #EF017C;
            }
            100% {
                width: 40px;
                height: 40px;
            }
        }

        @keyframes stars {
            0% {
                top: -100%;
            }
            100% {
                top: 0;
            }
        }

        #popup1{
            margin: 70px auto;
            padding: 20px;
            background: rgba(255, 255, 255, 0.5);
            border-radius: 5px;
            width: 30%;
            position: relative;
            z-index: 100;

        }

        h1, p{
            color: black;
        }
    </style>
</head>
<body>

<!-- Navigation -->
<main id="errorPage">
    <section class="space">
        <div class="stars">
            <article id="popup1">
                <h1>Server error</h1>
                <p>A Starship is on it's way to deal with the problem!</p>
                <a href="Controller?command=ShowOverview">Take me back to a safe place.</a>
            </article>
            <div class="stars-back"></div>
            <div class="stars-middle"></div>
            <div class="stars-front"></div>
        </div>
        <div class="rocket">
            <div class="fuselage">
                <div class="nose"></div>
                <div class="head">
                    <span class="window"></span>
                </div>
                <div class="neck"></div>
                <div class="body"></div>
                <div class="reactor"></div>
                <div class="fire">
                    <div class="spark1"></div>
                    <div class="spark2"></div>
                    <div class="spark3"></div>
                    <div class="spark4"></div>
                    <div class="spark5"></div>
                    <div class="spark6"></div>
                </div>
            </div>
            <div class="left-fin"></div>
            <div class="left-fin-end"></div>
            <div class="right-fin"></div>
            <div class="right-fin-end"></div>
        </div>
    </section>
    <div>
    </div>
</main>


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>