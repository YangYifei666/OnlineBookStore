<?php
error_reporting(0);
include_once("dbconnect.php");
$bookName = $_POST['bookName'];

    $sql = "SELECT * FROM book WHERE bookName like '%$bookType%' and num>0";

$result = $conn->query($sql);
if ($result->num_rows > 0) {
    $response["book"] = array();
    while ($row = $result ->fetch_assoc()){
        $booklist = array();
        $booklist[bookID] = $row["bookID"];
        $booklist[bookName] = $row["bookName"];
        $booklist[price] = $row["price"];
        $booklist[num] = $row["num"];
        $booklist[phone] = $row["phone"];
        $booklist[intro] = $row["intro"];
        $booklist[salerName] = $row["salerName"];
        array_push($response["book"], $booklist);
    }
    echo json_encode($response);
}else{
    echo "nodata";
}
?>