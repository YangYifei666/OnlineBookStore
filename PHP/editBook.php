<?php
error_reporting(0);
include_once("dbconnect.php");
$bookName = $_POST["bookName"];
$price = $_POST["price"];
$num = $_POST["num"];
$phone = $_POST["phone"];
$intro = $_POST["intro"];
$bookID = $_POST["bookID"];
$sqlcheck = "SELECT * FROM book WHERE bookID = '$bookID'";
$result = $conn->query($sqlcheck);
 $sql = "UPDATE book SET bookName='$bookName',price='$price',num='$num',phone='$phone',intro='$intro' WHERE bookID='$bookID'";
    if ($conn->query($sql) === TRUE){
        echo "success";
    }else {
        echo "failed";
    }
?>