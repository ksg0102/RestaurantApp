<?php

	class menuOperations{
		private $con;
		
		function __construct(){
			require_once dirname(__FILE__) . '/DbConnect.php';
			$db = new DbConnect;
			$this->con = $db->connect();
		}
		
		public function createItem($id, $name, $cost, $descrip){
			$state = $this->con->prepare("INSERT INTO menu (id, name, cost, descrip) VALUES (?, ?, ?, ?)");
			
			$state->bind_param("isds", $id, $name, $cost, $descrip);
			
			if($state->execute()){
				return ITEM_CREATE;
			}
			else{
				return ITEM_FAIL;
			}			
		}
		
		public function allItems(){
			$state = $this->con->prepare("SELECT * FROM menu");
			$state->execute();
			$state->bind_result($id, $name, $cost, $descrip);
			
			$items = array();
			while($state->fetch()){
				$item = array();
				$item['id'] = $id;
				$item['name'] = $name;
				$item['cost'] = $cost;
				$item['descrip'] = $descrip;
				array_push($items, $item);
			}
			return $items;
		}
		
		public function findItem($id){
			$state = $this->con->prepare("SELECT * FROM menu WHERE id = ?");
			$state->bind_param("i", $id);
			$state->execute();
			$state->bind_result($id, $name, $cost, $descrip);
			$state->fetch();
			$item = array();
			$item['id'] = $id;
			$item['name'] = $name;
			$item['cost'] = $cost;
			$item['descrip'] = $descrip;
			return $item;
		}
		
		public function itemExist($id){
			$state = $this->con->prepare("SELECT * FROM menu WHERE id = ?");
			$state->bind_param("i", $id);
			$state->execute();
			$state->store_result();
			if ($state->num_rows > 0) return true;
			return false;
		}
		
		public function updateItem($id, $name, $cost, $descrip){
            $state = $this->con->prepare("UPDATE menu SET name = ?, cost = ?, descrip = ? WHERE id = ?");
            $state->bind_param("sdsi", $name, $cost, $descrip, $id);
            if($state->execute())
                return true;
            return false; 
		}
		
        public function deleteItem($id){
            $state = $this->con->prepare("DELETE FROM menu WHERE id = ?");
            $state->bind_param("i", $id);
            if($state->execute())
                return true; 
            return false; 
        }
	}