<?php
error_reporting(0);
include_once("dbconnect.php");
$username = $_POST['username'];

    $sql = "SELECT * FROM card WHERE buyerName= '$userName'";
    
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    $response["orderB"] = array();
    while ($row = $result ->fetch_assoc()){
        $orderlist = array();
        $orderlist[bookName] = $row["bookName"];
        $orderlist[price] = $row["price"];
        $orderlist[phone] = $row["phone"];
        $orderlist[sellerName] = $row["sellerName"];
        array_push($response["orderB"], $orderlist);
    }
    echo json_encode($response);
}else{
    echo "nodata";
}
?>