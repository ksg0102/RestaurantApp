<?php

    class DbOperations{

        private $con;

        function __construct(){
            require_once dirname(__FILE__) . '/DbConnect.php';
            $db = new DbConnect;
            $this->con = $db->connect();
        }




        public function createCustomer($phone, $name, $password, $birthday, $visited, $credits){
           if(!$this->isPhoneExist($phone)){
                $stmt = $this->con->prepare("INSERT INTO customer (phone, name, password, birthday, visited, credits) VALUES (?, ?, ?, ?, ?, ?)");
                $stmt->bind_param("ssssii", $phone, $name, $password, $birthday, $visited, $credits);
                if($stmt->execute()){
                    return USER_CREATED;
                }else{
                    return USER_FAILURE;
                }
           }
           return USER_EXISTS;
        }

        public function customerLogin($phone, $password){
            if($this->isPhoneExist($phone)){
                $hashed_password = $this->getCustomerPasswordByPhone($phone);
                if(password_verify($password, $hashed_password)){
                    return USER_AUTHENTICATED;
                }else{
                    return USER_PASSWORD_DO_NOT_MATCH;
                }
            }else{
                return USER_NOT_FOUND;
            }
        }

        private function getCustomerPasswordByPhone($phone){
            $stmt = $this->con->prepare("SELECT password FROM customer WHERE phone = ?");
            $stmt->bind_param("s", $phone);
            $stmt->execute();
            $stmt->bind_result($password);
            $stmt->fetch();
            return $password;
        }

        public function getAllCustomer(){
            $stmt = $this->con->prepare("SELECT id, phone, name, birthday, visited, credits FROM customer;");
            $stmt->execute();
            $stmt->bind_result($id, $phone, $name, $birthday, $visited, $credits);
            $customers = array();
            while($stmt->fetch()){
                $customer = array();
                $customer['id'] = $id;
                $customer['phone']=$phone;
                $customer['name'] = $name;
                $customer['birthday'] = $birthday;
                $customer['visited'] = $visited;
                $customer['credits'] = $credits;
                array_push($customers, $customer);
            }
            return $customers;
        }

        public function getCustomerByPhone($phone){
            $stmt = $this->con->prepare("SELECT id, phone, name, birthday, visited, credits FROM customer WHERE phone = ?");
            $stmt->bind_param("s", $phone);
            $stmt->execute();
            $stmt->bind_result($id, $phone, $name, $birthday,$visited, $credits);
            $stmt->fetch();
            $customer = array();
            $customer['id'] = $id;
            $customer['phone']=$phone;
            $customer['name'] = $name;
            $customer['birthday'] = $birthday;
            $customer['visited'] = $visited;
            $customer['credits'] = $credits;
            return $customer;
        }

        public function updateCustomer($phone, $name, $birthday, $visited, $credits, $id){
            $stmt = $this->con->prepare("UPDATE customer SET phone = ?, name = ?, birthday = ?, visited = ?, credits = ? WHERE id = ?");
            $stmt->bind_param("sssiii", $phone, $name, $birthday,$visited, $credits, $id);
            if($stmt->execute())
                return true;
            return false;
        }

        public function updatePassword($currentpassword, $newpassword, $phone){
            $hashed_password = $this->getCustomerPasswordByPhone($phone);

            if(password_verify($currentpassword, $hashed_password)){

                $hash_password = password_hash($newpassword, PASSWORD_DEFAULT);
                $stmt = $this->con->prepare("UPDATE customer SET password = ? WHERE phone = ?");
                $stmt->bind_param("ss",$hash_password, $phone);

                if($stmt->execute())
                    return PASSWORD_CHANGED;
                return PASSWORD_NOT_CHANGED;

            }else{
                return PASSWORD_DO_NOT_MATCH;
            }
        }

        public function deleteCustomer($id){
            $stmt = $this->con->prepare("DELETE FROM customer WHERE id = ?");
            $stmt->bind_param("i", $id);
            if($stmt->execute())
                return true;
            return false;
        }

        private function isPhoneExist($phone){
            $stmt = $this->con->prepare("SELECT id FROM customer WHERE phone = ?");
            $stmt->bind_param("s", $phone);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows > 0;
        }



            public function createUser($food, $foodnum){
            if(!$this->isnameExist($food)){
            $stmt = $this->con->prepare("INSERT INTO ingredient (food, foodnum) VALUES (?, ?)");
            $stmt->bind_param("si", $food, $foodnum);
            if($stmt->execute()){
                return USER_CREATED;
            }else{
                return USER_FAILURE;
            }
            }
            return USER_EXISTS;
        }

        private function isnameExist($food){
            $stmt = $this->con->prepare("SELECT id FROM ingredient WHERE food = ?");
            $stmt->bind_param("s", $food);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows > 0;
        }
    }