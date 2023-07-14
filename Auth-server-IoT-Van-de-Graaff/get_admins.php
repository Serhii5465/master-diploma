<?php

require 'db_connect.php';

$db = new DB_CONNECT();
$con = $db->get_connection();

$query = "SELECT COUNT(1) AS count_admins FROM `users` WHERE privilege='admin'";
$result = mysqli_query($con, $query);

if ($result==false) {
    $response["success"] = 1;
    $response["message"] = mysqli_error($con);
    echo json_encode($response);
    exit();
} else {
    $response["success"] = 0;
    $response["message"] = "Good";
    $response["data"] = mysqli_fetch_assoc($result);
    echo json_encode($response);
}

mysqli_close($con);


?>