<?php

	class employeeOperations{
		private $con;
		
		function __construct(){
			require_once dirname(__FILE__) . '/DbConnect.php';
			$db = new DbConnect;
			$this->con = $db->connect();
		}
		
		public function createEmp($id, $password, $name, $wage, $role, $hours, $tips, $compmeals){
			if(!$this->empExist($id)){
				$state = $this->con->prepare("INSERT INTO employee (id, password, name, wage, role, hours, tips, compmeals) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				
				$state->bind_param("issisidi", $id, $password, $name, $wage, $role, $hours, $tips, $compmeals);
				
				if($state->execute()){
					return EMP_CREATE;
				}else{
					return EMP_FAIL;
				}	
			}
			return EMP_EXISTS;		
		}
		
		public function login($id, $password){
            if($this->empExist($id)){
                $hashed_password = $this->getPass($id); 
                if(password_verify($password, $hashed_password)){
                    return EMP_AUTHENTICATED;
                }else{
                    return EMP_PASSWORD_FAIL; 
                }
            }else{
                return EMP_NOT_FOUND; 
            }
        }
		
        private function getPass($id){
            $stmt = $this->con->prepare("SELECT password FROM employee WHERE id = ?");
            $stmt->bind_param("i", $id);
            $stmt->execute(); 
            $stmt->bind_result($password);
            $stmt->fetch(); 
            return $password; 
        }
		
		
		public function allEmp(){
			$state = $this->con->prepare("SELECT id, name, wage, role, hours, tips, compmeals FROM employee");
			$state->execute();
			$state->bind_result($id, $name, $wage, $role, $hours, $tips, $compmeals);
			
			$employees = array();
			while($state->fetch()){
				$employee = array();
				$employee['id'] = $id;
				$employee['name'] = $name;
				$employee['wage'] = $wage;
				$employee['role'] = $role;
				$employee['hours'] = $hours;
				$employee['tips'] = $tips;
				$employee['compmeals'] = $compmeals;
				array_push($employees, $employee);
			}
			return $employees;
		}
		
		public function findEmployee($id){
			$state = $this->con->prepare("SELECT id, name, wage, role, hours, tips, compmeals FROM employee WHERE id = ?");
			$state->bind_param("i", $id);
			$state->execute();
			$state->bind_result($id, $name, $wage, $role, $hours, $tips, $compmeals);
			$state->fetch();
			$employee = array();
			$employee['id'] = $id;
			$employee['name'] = $name;
			$employee['wage'] = $wage;
			$employee['role'] = $role;
			$employee['hours'] = $hours;
			$employee['tips'] = $tips;
			$employee['compmeals'] = $compmeals;
			return $employee;
		}
		
		public function empExist($id){
			$state = $this->con->prepare("SELECT id FROM employee WHERE id = ?");
			$state->bind_param("i", $id);
			$state->execute();
			$state->store_result();
			return ($state->num_rows > 0);
		}
		
		public function updateEmployee($name, $wage, $role, $hours, $tips, $compmeals, $id){
            $state = $this->con->prepare("UPDATE employee SET name = ?, wage = ?, role = ?, hours = ?, tips = ?, compmeals = ? WHERE id = ?");
            $state->bind_param("sisidii", $name, $wage, $role, $hours, $tips, $compmeals, $id);
            if($state->execute())
                return true;
            return false; 
		}



		
        public function deleteEmployee($id){
            $state = $this->con->prepare("DELETE FROM employee WHERE id = ?");
            $state->bind_param("i", $id);
            if($state->execute())
                return true; 
            return false; 
        }



        public function getEmplByID($id){
            $stmt = $this->con->prepare("SELECT name, wage, role, hours, tips, compmeals FROM employee WHERE id = ?");
            $stmt->bind_param("i", $id);
            $stmt->execute();
            $stmt->bind_result($name, $wage, $role, $hours, $tips, $compmeals);
            $stmt->fetch();
            $employee = array();
			$employee['name'] = $name;
			$employee['wage'] = $wage;
			$employee['role'] = $role;
			$employee['hours'] = $hours;
			$employee['tips'] = $tips;
			$employee['compmeals'] = $compmeals;
            return $employee;
        }
	}