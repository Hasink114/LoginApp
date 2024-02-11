<?php
    include 'config/config.php';
    $data = file_get_contents("php://input");
    $request = json_decode($data);
    $response = array();
    $isValidRequest = false;

    if(isset($request -> {'action'})){
        if($request -> {'action'} == 'Login'){
            $isValidRequest =true;
            $email = $request -> {'email'};
            $password = $request -> {'password'};
            $query = "SELECT * FROM users WHERE `email`= '" . $email . "'";
            $result = mysqli_query($connection, $query);

            if($result){
                if($row = mysqli_fetch_assoc($result)){
                    if($row['password'] == $password){
                        $response['id'] = $row['id'];
                        $response['username'] = $row['username'];
                        $response['status'] = true;
                        $response['responseCode'] = 0;
                        $response['message'] = "User Login Successful";
                    }
                    else{
                        $response['status'] = false;
                        $response['responseCode'] = 101;
                        $response['message'] = "Incorrect Password";
                    }
                }
                else{
                    $response['status'] = false;
                    $response['responseCode'] = 102;
                    $response['message'] = "You have not registered yet!";
                }
            }
            else{
                $response['status'] = false;
                $response['responseCode'] = 103;
                $response['message'] = "Something went wrong!";
            }
        }
        else{
            $response['status'] = true;
            $response['responseCode'] = 500;
            $response['message'] = "Invalid request";
        }
    }
    else{
        $response['status'] = false;
        $response['responseCode'] = 100;
        $response['message'] = "Request action not defined";
    }
    echo json_encode($response);
?>
