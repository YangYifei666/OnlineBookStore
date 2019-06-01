<?php
error_reporting(0);
include_once("dbconnect.php");
$username = $_POST['username'];

    $sql = "SELECT * FROM card WHERE sellerName= '$username'";
    
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    $response["order"] = array();
    while ($row = $result ->fetch_assoc()){
        $orderlist = array();
        $orderlist[bookName] = $row["bookName"];
        $orderlist[buyerName] = $row["buyerName"];
        $orderlist[phone] = $row["phone"];
        $orderlist[address] = $row["address"];
        array_push($response["order"], $orderlist);
    }
    echo json_encode($response);
}else{
    echo "nodata";
}
?>