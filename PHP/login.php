<?php
error_reporting(0);
include_once("dbconnect.php");
$username = $_POST["username"];
$password = $_POST["password"];
$passwordsha = sha1($password);

 $sql = "SELECT * FROM user WHERE username = '$username' AND password = '$passwordsha'";
 $result = $conn->query($sql);
if ($result->num_rows > 0) {
    echo "success";
}else{
    echo "failed";
}
?>