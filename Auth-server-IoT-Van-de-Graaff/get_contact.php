<?php

$data = json_decode(file_get_contents('php://input'), true);

if(isset($data['email'])){

    $email = $data['email'];

    require 'db_connect.php';
    $db = new DB_CONNECT();
    $con = $db->get_connection();

    $query = "SELECT email,name,pass,privilege,others FROM `users` WHERE email=?";
    $stmt = mysqli_prepare($con,$query);

    if(false == $stmt){
        $response["success"] = 1;
        $response["message"] = mysqli_error($con);
        echo json_encode($response);
        exit();
    }

    $stat = mysqli_stmt_bind_param($stmt,'s',$email);

    if(false == $stat){
        $response["success"] = 1;
        $response["message"] = mysqli_error($con);
        echo json_encode($response);
        exit();
    }

    $stat = mysqli_stmt_execute($stmt);

    if(false == $stat){
        $response["success"] = 1;
        $response["message"] = mysqli_error($con);
        echo json_encode($response);
        exit();
    }

    $result = mysqli_stmt_get_result($stmt);
    $response = array();
    $response["success"] = 0;
    $response["message"] = "User's exists";

    if(mysqli_num_rows($result) > 0){
        while($row = mysqli_fetch_assoc($result)){
            $response["data"] = $row;
            echo json_encode($response);
        }
       
    } else {
        $response["success"] = 1;
        $response["message"] = "User's not found";

        echo json_encode($response);
    }

} else {
    $response["success"] = 1;
    $response["message"] = "Required field(s) is missing";

    echo json_encode($response);
}

mysqli_stmt_close($stmt);
mysqli_close($con);

?>