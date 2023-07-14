<?php

$data = json_decode(file_get_contents('php://input'), true);

if (isset($data['email']) && isset($data['name']) && isset($data['pass'])  && isset($data['privilege']) && isset($data['others'])) {

    $email = $data["email"];
    $name = $data["name"];
    $pass = $data["pass"];
    $privileges = $data["privilege"];
    $others = $data["others"];  
        
    require 'db_connect.php';

    $db = new DB_CONNECT();
    $con = $db->get_connection();

    $stmt = mysqli_prepare($con,"INSERT INTO `users` (email, name, pass, privilege, others) VALUES (?,?,?,?,?)");

    if(false == $stmt){
        $response["success"] = -1;
        $response["message"] = mysqli_error($con);
        echo json_encode($response);
        exit();
    }

    $stat = mysqli_stmt_bind_param($stmt,'sssss',$email,$name,$pass,$privileges,$others);

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

    $response["success"] = 0;
    $response["message"] = "Success";

    echo json_encode($response);
    
} else {
    $response["success"] = 1;
    $response["message"] = "Required field(s) is missing";

    echo json_encode($response);
}

mysqli_stmt_close($stmt);
mysqli_close($con);

?>