<?php
error_reporting(0);
include_once("dbconnect.php");
$bookName = $_POST['bookName'];
$bookType = $_POST['bookType'];
$price= $_POST['price'];
$num=$_POST['num'];
$phone = $_POST['phone'];
$intro = $_POST['intro'];
$salerName = $_POST['salerName'];
$decoded_string = base64_decode($encoded_string);
$path = '../profileimages/' . $image_name;
$file = fopen($path, 'wb');
$is_written = fwrite($file, $decoded_string);
fclose($file);
// if ($is_written > 0) {
    $sqlinsert = "INSERT INTO book(bookName,bookType,price,num,phone,intro,salerName) VALUES ('$bookName','$bookType','$price','$num','$phone','$intro','$salerName')";
    if ($conn->query($sqlinsert) === TRUE){
       echo "success";
    }else {
        echo "failed";
    }
// } else {
//     echo "Upload Failed";
// }

?>