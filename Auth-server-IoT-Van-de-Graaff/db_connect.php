<?php

class DB_CONNECT {

    private $host="localhost";
    private $user="raisnet";
    private $password="under7";
    private $db_name="thingsboard_users";

    private $con;
    
    function __construct() {
        $this->con = mysqli_connect($this->host, $this->user, $this->password, $this->db_name);

        if (mysqli_connect_errno()) {
            printf("Connect failed: %s\n", mysqli_connect_error());
            exit();
        }
    }
    
    function get_connection(){
        return $this->con;
    }
}

?>