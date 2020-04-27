<?php

 class surveyOperation{

        private $con;

        function __construct(){
            require_once dirname(__FILE__) . '/DbConnect.php';
            $db = new DbConnect;
            $this->con = $db->connect();
        }

    public function createSurvey($firstq, $secondq, $thirdq){
    	$stmt = $this->con->prepare("INSERT INTO survey (firstq, secondq, thirdq) VALUES (?, ?, ?)");
                $stmt->bind_param("iii", $firstq, $secondq, $thirdq);
                if($stmt->execute()){
                    return SURVEY_CREATED;
                }else{
                    return SURVEY_FAILED;
                }
    }


    	public function allSurveys(){
            $stmt = $this->con->prepare("SELECT firstq, secondq, thirdq FROM survey;");
            $stmt->execute();
            $stmt->bind_result($firstq, $secondq, $thirdq);
            $surveys = array();
            while($stmt->fetch()){
                $survey = array();
                $survey['firstq'] = $firstq;
                $survey['secondq']=$secondq;
                $survey['thirdq'] = $thirdq;
                array_push($surveys, $survey);
            }
            return $surveys;
        }
    }

