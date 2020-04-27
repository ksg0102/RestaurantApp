<?php

    class ingredientsOperations{

        private $con;

        function __construct(){
            require_once dirname(__FILE__) . '/DbConnect.php';
            $db = new DbConnect;
            $this->con = $db->connect();
        }

        public function createFood($food, $foodnum){
            if(!$this->isFoodExist($food)){
                 $stmt = $this->con->prepare("INSERT INTO ingredients (food, foodnum) VALUES (?, ?)");
                 $stmt->bind_param("si", $food, $foodnum);
                 if($stmt->execute()){
                     return ING_CREATE;
                 }else{
                     return ING_FAIL;
                 }
            }
            return ING_EXIST;
         }

         public function getAllIngredients(){
            $stmt = $this->con->prepare("SELECT food, foodnum FROM ingredient;");
            $stmt->execute();
            $stmt->bind_result($food, $foodnum);
            $ingts = array();
            while($stmt->fetch()){
                $ing = array();
                $ing['food'] = $food;
                $ing['foodnum'] = $foodnum;
                array_push($ingts, $ing);
            }
            return $ingts;
            
        }


            public function getInfo($id){
            $state = $this->con->prepare("SELECT food, foodnum FROM ingredient WHERE id = ?");
            $state->bind_param("i", $id);
            $state->execute();
            $state->bind_result($food, $foodnum);
            $state->fetch();
            $returnarray = array();
            $returnarray['food'] = $food;
            $returnarray['foodnum'] = $foodnum;
            return $returnarray;
        }



        public function getFoodByName($food){
            $stmt = $this->con->prepare("SELECT food, foodnum, id FROM ingredient WHERE food = ?");
            $stmt->bind_param("s", $food);
            $stmt->execute();
            $stmt->bind_result($food, $foodnum, $id);
            $stmt->fetch();
            $ing = array();
            $ing['food'] = $food;
            $ing['foodnum'] = $foodnum;
            $ing['id'] = $id;
            return $ing;
        }

        public function updateIngredients($food, $foodnum, $id){
             $stmt = $this->con->prepare("UPDATE ingredient SET food = ?, foodnum = ? WHERE id = ?");
             $stmt->bind_param("ssi", $food, $foodnum, $id);
             if($stmt->execute())
                return true;
            return false;
         }

    
        public function trashedFood($id){
            $stmt = $this->con->prepare("DELETE FROM ingredient WHERE id = ?");
            $stmt->bind_param("i", $id);
            if($stmt->execute())
                return true;
            return false;
        }

         private function isFoodExist($food){
            //$stmt = $this->con->prepare("SELECT id FROM ingredient WHERE food = ?");
            $stmt = $this->con->prepare("SELECT id FROM ingredient WHERE food = ?");
            $stmt->bind_param("s", $food);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows > 0;
        }

    }