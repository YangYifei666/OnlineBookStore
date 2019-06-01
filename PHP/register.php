<?php
error_reporting(0);
include_once("dbconnect.php");
$username = $_POST["username"];
$password = $_POST["password"];
// $username = "aaa";
// $password = "111";
$passwordsha = sha1($password);

 $sql = "INSERT INTO user(username,password) VALUES ('$username','$passwordsha')";
    if ($conn->query($sql) === TRUE){
        echo "success";
    }else {
        echo "failed";
    }
?>