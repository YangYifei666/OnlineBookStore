<?php
error_reporting(0);
include_once("dbconnect.php");
$username = $_POST["username"];
$password = $_POST["password"];
$passwordsha = sha1($password);
$sqlcheck = "SELECT * FROM user WHERE username = '$username'";
$result = $conn->query($sqlcheck);
 $sql = "UPDATE user SET password='$passwordsha' WHERE username='$username'";
    if ($conn->query($sql) === TRUE){
        echo "success";
    }else {
        echo "failed";
    }
?>