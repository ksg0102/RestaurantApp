<?php

	class orderOperations{
		private $con;
		
		function __construct(){
			require_once dirname(__FILE__) . '/DbConnect.php';
			$db = new DbConnect;
			$this->con = $db->connect();
		}
		
		public function createOrder($tableNum, $entree, $side, $drink, $note, $orderTotal, $status){
			$state = $this->con->prepare("INSERT INTO orders (tableNum, entree, side, drink, note, orderTotal, status) VALUES (?, ?, ?, ?, ?, ?, ?)");
			$state->bind_param("issssdi", $tableNum, $entree, $side, $drink, $note, $orderTotal, $status);
			
			if($state->execute()){
				return ORDER_CREATE;
			}
			else{
				return ORDER_FAIL;
			}
		}
		public function finish($id){
			$state = $this->con->prepare("UPDATE orders SET status = 1  WHERE id = ?");
			$state->bind_param("i", $id);
			if($state->execute()) return true;
			else return false;
		}
		public function toHistory($id){
			$state = $this->con->prepare("UPDATE orders SET status = 2  WHERE tableNum = ?");
			$state->bind_param("i", $id);
			if($state->execute()) return true;
			else return false;
		}
		public function serve(){
			$state = $this->con->prepare("SELECT tableNum FROM orders WHERE status = 1");
			$state->execute();
			$state->bind_result($tableNum);
			$tables = array();
			while($state->fetch()){
				array_push($tables, $tableNum);
			}
			return $tables;
		}
		public function allorders(){
			include_once dirname(__FILE__) . '/ingredientsOperations.php';
			$state = $this->con->prepare("SELECT * FROM orders WHERE status = 0");
			$state->execute();
			$state->bind_result($id, $tableNum, $entree, $side, $drink, $note, $orderTotal, $status);			
			$orders = array();
			$ing = new ingredientsOperations;
			while($state->fetch()){
			$order = array();
			$entreeData = array();
			$sideData = array();
			$drinkData = array();
			foreach(explode(' ', $entree) as $i){
				$thisItem = array();
				$typeId = $i % 10;
				$flavorId = ($i - $typeId) %100;
				$sauceId = ($i - $typeId - $flavorId) %1000;
				$amount = $i - $typeId - $flavorId - $sauceId;
				$amount = $amount/1000;
				$flavor = $ing->getInfo($flavorId);
				$type = $ing->getInfo($typeId);
				$sauce = $ing->getInfo($sauceId);
				
				$thisItem['meatType'] = $type['food'];
				$thisItem['flavor'] = $flavor['food'];
				$thisItem['quantity'] = $amount;
				$thisItem['sauceType'] = $sauce['food'];
				$thisItem['sauceQuantity'] = $amount;
				$thisItem['price'] = $amount * 1.99;
				array_push($entreeData, $thisItem);
			}
			
			foreach(explode(' ', $side) as $i){
				$thisSide = array();
				$sideId = $i%1000;
				$amount = ($i-$sideId)/1000;
				$side = $ing->getInfo($sideId);
				$thisSide['item'] = $side['food'];
				$thisSide['quantity'] = $amount;
				array_push($sideData, $thisSide);
			}
			
			foreach(explode(' ', $drink) as $i){
				$thisDrink = array();
				$drinkId = $i%1000;
				$amount = ($i-$drinkId)/1000;
				$drink = $ing->getInfo($drinkId);
				$thisDrink['item'] = $drink['food'];
				$thisDrink['quantity'] = $amount;
				array_push($drinkData, $thisDrink);
			}
				$order['id'] = $id;
				$order['tableNum'] = $tableNum;
				$order['entree'] = $entreeData;
				$order['side'] = $sideData;
				$order['drink'] = $drinkData;
				$order['note'] = $note;
				$order['orderTotal'] = $orderTotal;
				$order['status'] = $status;
				array_push($orders, $order);
			}
			return $orders;
		}


		/*public function allorders(){
			$state = $this->con->prepare("SELECT id, tableNum, entree, side, drink, note, orderTotal, status FROM orders");
			$state->execute();
			$state->bind_result($id, $tableNum, $entree, $side, $drink, $note, $orderTotal, $status);
			$orders = array();
			$entreeData = array();
			$sideData = array();
			$drinkData = array();
			foreach(explode(' ', $entree) as $i){
				$thisItem = array();
				$thisItem['meatType'] = $entree[i];
				$thisItem['flavor'] = $entree[i];
				$thisItem['quantity'] = $entree[i];
				$thisItem['sauceType'] = $entree[i];
				$thisItem['sauceQuantity'] = $entree[i];
				$thisItem['price'] = [0];
				array_push($entreeData, $thisItem);

			}
			foreach(explode(' ', $side) as $i){
				$thisSide = array();
				$thisSide['item'] = $side[i];
				$thisSide['quantity'] = $side[i];
				array_push($sideData, $thisSide);
			}
			foreach(explode(' ', $drink) as $i){
				$thisDrink = array();
				$thisDrink['item'] = $drink[i];
				$thisDrink['quantity'] = $drink[i];
				array_push($drinkData, $thisDrink);
			}
			while($state->fetch()){
				$order = array();
				$order['id'] = $id;
				$order['tableNum'] = $tableNum;
				$order['entree'] = $entreeData;
				$order['side'] = $sideData;
				$order['drink'] = $drinkData;
				$order['note'] = $note;
				$order['orderTotal'] = $orderTotal;
				$order['status'] = $status;
				array_push($orders, $order);
			}
			return $orders;
		}*/



		public function allordersManager(){
			include_once dirname(__FILE__) . '/ingredientsOperations.php';
			$state = $this->con->prepare("SELECT * FROM orders");
			$state->execute();
			$state->bind_result($id, $tableNum, $entree, $side, $drink, $note, $orderTotal, $status);			
			$orders = array();
			$ing = new ingredientsOperations;
			while($state->fetch()){
			$order = array();
			$entreeData = array();
			$sideData = array();
			$drinkData = array();
			foreach(explode(' ', $entree) as $i){
				$thisItem = array();
				$typeId = $i % 10;
				$flavorId = ($i - $typeId) %100;
				$sauceId = ($i - $typeId - $flavorId) %1000;
				$amount = $i - $typeId - $flavorId - $sauceId;
				$amount = $amount/1000;
				$flavor = $ing->getInfo($flavorId);
				$type = $ing->getInfo($typeId);
				$sauce = $ing->getInfo($sauceId);
				
				$thisItem['meatType'] = $type['food'];
				$thisItem['flavor'] = $flavor['food'];
				$thisItem['quantity'] = $amount;
				$thisItem['sauceType'] = $sauce['food'];
				$thisItem['sauceQuantity'] = $amount;
				$thisItem['price'] = $amount * 1.99;
				array_push($entreeData, $thisItem);
			}
			
			foreach(explode(' ', $side) as $i){
				$thisSide = array();
				$sideId = $i%1000;
				$amount = ($i-$sideId)/1000;
				$side = $ing->getInfo($sideId);
				$thisSide['item'] = $side['food'];
				$thisSide['quantity'] = $amount;
				array_push($sideData, $thisSide);
			}
			
			foreach(explode(' ', $drink) as $i){
				$thisDrink = array();
				$drinkId = $i%1000;
				$amount = ($i-$drinkId)/1000;
				$drink = $ing->getInfo($drinkId);
				$thisDrink['item'] = $drink['food'];
				$thisDrink['quantity'] = $amount;
				array_push($drinkData, $thisDrink);
			}
				$order['id'] = $id;
				$order['tableNum'] = $tableNum;
				$order['entree'] = $entreeData;
				$order['side'] = $sideData;
				$order['drink'] = $drinkData;
				$order['note'] = $note;
				$order['orderTotal'] = $orderTotal;
				$order['status'] = $status;
				array_push($orders, $order);
			}
			return $orders;
		}
		
		public function findOrder($id){
			include_once dirname(__FILE__) . '/ingredientsOperations.php';
			$state = $this->con->prepare("SELECT * FROM orders WHERE id = ?");
			$state->bind_param("i", $id);
			$state->execute();
			$state->bind_result($id, $tableNum, $entree, $side, $drink, $note, $orderTotal, $status);
			$state->fetch();
			$order = array();
			$entreeData = array();
			$sideData = array();
			$drinkData = array();
			$ing = new ingredientsOperations;
			
			foreach(explode(' ', $entree) as $i){
				$thisItem = array();
				$typeId = $i % 10;
				$flavorId = ($i - $typeId) %100;
				$sauceId = ($i - $typeId - $flavorId) %1000;
				$amount = $i - $typeId - $flavorId - $sauceId;
				$amount = $amount/1000;
				$flavor = $ing->getInfo($flavorId);
				$type = $ing->getInfo($typeId);
				$sauce = $ing->getInfo($sauceId);
				
				$thisItem['meatType'] = $type['food'];
				$thisItem['flavor'] = $flavor['food'];
				$thisItem['quantity'] = $type['foodnum'];
				$thisItem['sauceType'] = $sauce['food'];
				$thisItem['sauceQuantity'] = $sauce['foodnum'];
				$thisItem['price'] = $amount * 1.99;
				array_push($entreeData, $thisItem);
			}
			
			foreach(explode(' ', $side) as $i){
				$thisSide = array();
				$side = $ing->getInfo($i);
				$thisSide['item'] = $side['food'];
				$thisSide['quantity'] = $side['foodnum'];
				array_push($sideData, $thisSide);
			}
			
			foreach(explode(' ', $drink) as $i){
				$thisDrink = array();
				$drink = $ing->getInfo($i);
				$thisDrink['item'] = $drink['food'];
				$thisDrink['quantity'] = $drink['foodnum'];
				array_push($drinkData, $thisDrink);
			}
			
			$order['id'] = $id;
			$order['tableNum'] = $tableNum;
			$order['entree'] = $entreeData;
			$order['side'] = $sideData;
			$order['drink'] = $drinkData;
			$order['note'] = $note;
			$order['orderTotal'] = $orderTotal;
			$order['status'] = $status;
			return $order;
		}
		
		public function orderExist($id){
			$state = $this->con->prepare("SELECT * FROM orders WHERE id = ?");
			$state->bind_param("i", $id);
			$state->execute();
			$state->store_result();
			if ($state->num_rows > 0) return true;
			return false;
		}

		
		public function updateorder($id, $tableNum, $entree, $side, $drink, $note, $orderTotal, $status){
            $state = $this->con->prepare("UPDATE orders SET tableNum = ?, entree = ?, side = ?, drink = ?, note = ?, orderTotal = ?, status = ? WHERE id = ?");
            $state->bind_param("issssdii", $tableNum, $entree, $side, $drink, $note, $orderTotal, $status, $id);
            if($state->execute())
                return true;
            return false; 
		}
		
        public function deleteOrder($id){
            $state = $this->con->prepare("DELETE FROM orders WHERE id = ?");
            $state->bind_param("i", $id);
            if($state->execute())
                return true; 
            return false; 
        }
        public function clearQueue(){
            $state = $this->con->prepare("DELETE FROM orders");
            if($state->execute())
                return true; 
            return false; 
        }
	}