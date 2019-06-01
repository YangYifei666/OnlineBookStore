<?php
error_reporting(0);
include_once("dbconnect.php");
$bookID = $_POST["bookID"];
$num=$_POST["num"];

    $sql ="UPDATE book SET num='$num' WHERE bookID='$bookID'";
    if ($conn->query($sql) === TRUE){
        echo "success";
    }else {
        echo "failed";
    }
?>