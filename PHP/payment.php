<?php
error_reporting(0);
include_once("dbconnect.php");
$bookName = $_POST["bookName"];
$bookID = $_POST["bookID"];
$sellerName=$_POST["sellerName"];
$price=$_POST["price"];
$buyerName=$_POST["buyerName"];
$phone=$_POST["phone"];
$address=$_POST["address"];
$num=$_POST["num"];

    $sql = "INSERT INTO card (phone,address,buyerName,bookName,price,bookID,sellerName) VALUES ('$phone','$address','$buyerName','$bookName','$price','$bookID','$sellerName')";
    // "UPDATE book SET num='$num' WHERE bookID='$bookID'";
    if ($conn->query($sql) === TRUE){
        echo "success";
    }else {
        echo "failed";
    }
?>