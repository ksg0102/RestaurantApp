<?php
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
//use Slim\Handlers\Strategies\RequestResponseArgs;
use Slim\Factory\AppFactory;
require __DIR__ . '/../vendor/autoload.php';

require '../includes/DbOperations.php';
require __DIR__ . '/../includes/menuOperations.php';
require __DIR__ . '/../includes/orderOperations.php';
require __DIR__ . '/../includes/employeeOperations.php';
require __DIR__ . '/../includes/ingredientsOperations.php';
require __DIR__ . '/../includes/tableOperations.php';
require __DIR__ .  '/../includes/surveyOperations.php';

$app = AppFactory::create();

$app->addRoutingMiddleware();
$errorMiddleware = $app->addErrorMiddleware(true, true, true);

$app->add(new Tuupola\Middleware\HttpBasicAuthentication([
    "secure"=>false,
    "users" => [
        "root" => "123456",
    ]
]));

/*
        $app->post('/MyApi/public/createmenu', function(Request $request, Response $response){
    if(!haveEmptyParameters(array('food', 'foodnum'), $request, $response)){

        $request_data = $request->getParsedBody();

        $food = $request_data['food'];
        $foodnum = $request_data['foodnum'];

        $db = new DbOperations;

        $result = $db->createUser($food, $foodnum);

        if($result == USER_CREATED){

            $message = array();
            $message['error'] = false;
            $message['message'] = 'User created successfully';

            $response->getbody()->write(json_encode($message));

             return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(201);

        }else if($result == USER_FAILURE){

            $message = array();
            $message['error'] = true;
            $message['message'] = 'Error occured';

            $response->getbody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);

        }else if($result == USER_EXISTS){

            $message = array();
            $message['error'] = true;
            $message['message'] = 'User Already Created';

            $response->getbody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);

        }
    }
    return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);
});*/

$app->post('/MyApi/public/createsurvey', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('firstq', 'secondq', 'thirdq'), $request, $response)){

        $request_data = $request->getParsedBody();

        $firstq = $request_data['firstq'];
        $secondq = $request_data['secondq'];
        $thirdq = $request_data['thirdq'];


        $db = new surveyOperation;
        $result = $db->createSurvey($firstq, $secondq, $thirdq);


        if($result == SURVEY_CREATED){
            $message = array();
            $message['error'] = false;
            $message['message'] = 'Survey created successfully';

            $response->getBody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(201);

        }else if($result == SURVEY_FAILED){

            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some error occurred';

            $response->getBody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);
        }
    
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);


});


$app->get('/MyApi/public/allSurveys', function(Request $request, Response $response){
    $db = new surveyOperation;
   
    $response_data = array();
    $response_data['error'] = false;
    $response_data['message']= "All survey";
    $response_data['surveys'] = $db->allSurveys();

    $response->getBody()->write(json_encode($response_data));
    return $response->withHeader('Content-type', 'application/json')->withStatus(200);

});




$app->post('/MyApi/public/createcustomer', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('phone', 'name', 'password', 'birthday','visited', 'credits'), $request, $response)){

        $request_data = $request->getParsedBody();

        $phone = $request_data['phone'];
        $name = $request_data['name'];
        $password = $request_data['password'];
        $birthday = $request_data['birthday'];
        $visited = $request_data['visited'];
        $credits = $request_data['credits'];

        $hash_password = password_hash($password, PASSWORD_DEFAULT);

        $db = new DbOperations;

        $result = $db->createCustomer($phone, $name, $hash_password, $birthday, $visited, $credits);

        if($result == USER_CREATED){

            $message = array();
            $message['error'] = false;
            $message['message'] = 'Customer created successfully';

            $response->getBody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(201);

        }else if($result == USER_FAILURE){

            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some error occurred';

            $response->getBody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);

        }else if($result == USER_EXISTS){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Customer Already Exists';

            $response->getBody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});


$app->post('/MyApi/public/customerlogin', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('phone', 'password'), $request, $response)){


        $request_data = $request->getParsedBody();


        $phone = $request_data['phone'];
        $password = $request_data['password'];

        $db = new DbOperations;

        $result = $db->customerLogin($phone, $password);

        if($result == USER_AUTHENTICATED){

            $customer = $db->getCustomerByPhone($phone);
            $response_data = array();

            $response_data['error']=false;
            $response_data['message'] = 'Login Successful';
            $response_data['customer']=$customer;

            $response->getBody()->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }else if($result == USER_NOT_FOUND){
            $response_data = array();

            $response_data['error']=true;
            $response_data['message'] = 'Customer not exist';

            $response->getBody()->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }else if($result == USER_PASSWORD_DO_NOT_MATCH){
            $response_data = array();

            $response_data['error']=true;
            $response_data['message'] = 'Invalid credential';

            $response->getBody->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);
        }
    }

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});

$app->get('/MyApi/public/service', function(Request $request, Response $response){
    $db = new orderOperations;
    $return_list = array();
    $return_list['error'] = false;
    $return_list['tables'] = $db->serve();
    $response->getBody()->write(json_encode($return_list));
    return $response->withHeader('Content-type', 'application/json')->withStatus(200);

});

$app->get('/MyApi/public/checktableshelp', function(Request $request, Response $response){
    $db = new tableOperations;
    $return_list = array();
    $return_list['error'] = false;
    $return_list['tables'] = $db->checkHelp();
    $response->getBody()->write(json_encode($return_list));
    return $response->withHeader('Content-type', 'application/json')->withStatus(200);

});

$app->get('/MyApi/public/checktablesrefill', function(Request $request, Response $response){
    $db = new tableOperations;
    $return_list = array();
    $return_list['error'] = false;
    $return_list['tables'] = $db->checkRefill();
    $response->getBody()->write(json_encode($return_list));
    return $response->withHeader('Content-type', 'application/json')->withStatus(200);

});



$app->put('/MyApi/public/orderDone/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];
    $db = new orderOperations;
    if($db->finish($id)){
        $return_list['error'] = false;
        $return_list['message'] = 'order finished';
        $response->getBody()->write(json_encode($return_list));
        return $response->withHeader('Content-type', 'application/json')->withStatus(201);
    }
    else{
        $return_list['error'] = true;
        $return_list['message'] = 'ooof... something went wrong';
        $response->getBody()->write(json_encode($return_list));
        return $response->withHeader('Content-type', 'application/json')->withStatus(422);
    }
    
});

$app->put('/MyApi/public/toHistory/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];
    $db = new orderOperations;
    if($db->toHistory($id)){
        $return_list['error'] = false;
        $return_list['message'] = 'order finished';
        $response->getBody()->write(json_encode($return_list));
        return $response->withHeader('Content-type', 'application/json')->withStatus(201);
    }
    else{
        $return_list['error'] = true;
        $return_list['message'] = 'ooof... something went wrong';
        $response->getBody()->write(json_encode($return_list));
        return $response->withHeader('Content-type', 'application/json')->withStatus(422);
    }
    
});
$app->put('/MyApi/public/toClear/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];
    $db = new tableOperations;
    if($db->clearHelp($id)){
        $return_list['error'] = false;
        $return_list['message'] = 'Table Helped';
        $response->getBody()->write(json_encode($return_list));
        return $response->withHeader('Content-type', 'application/json')->withStatus(201);
    }
    else{
        $return_list['error'] = true;
        $return_list['message'] = 'ooof... something went wrong';
        $response->getBody()->write(json_encode($return_list));
        return $response->withHeader('Content-type', 'application/json')->withStatus(422);
    }
    
});

$app->get('/MyApi/public/allcustomers', function(Request $request, Response $response){

    $db = new DbOperations;

    $customers = $db->getAllCustomer();

    $response_data = array();
    $response_data['error'] = false;
    $response_data['message']= "hello";

    $response_data['customers'] = $customers;

    $response->getBody()->write(json_encode($response_data));

    return $response
    ->withHeader('Content-type', 'application/json')
    ->withStatus(200);

});

$app->put('/MyApi/public/updatecustomer/{id}', function(Request $request, Response $response, array $args){

    $id = $args['id'];

    if(!haveEmptyParameters(array('phone', 'name', 'birthday','visited', 'credits'), $request, $response)){


        $reqBody = file_get_contents('php://input');
        parse_str($reqBody, $request_data);

        $phone = $request_data['phone'];
        $name = $request_data['name'];
        $password = $request_data['password'];
        $birthday = $request_data['birthday'];
        $visited = $request_data['visited'];
        $credits = $request_data['credits'];


        $db = new DbOperations;

        if($db->updateCustomer($phone, $name, $birthday, $visited, $credits, $id)){
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'Customer Updated Successfully';
            $customer = $db->getCustomerByPhone($phone);
            $response_data['customer'] = $customer;

            $response->getBody()->write(json_encode($response_data));

            return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);

        }else{
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Please try again later';
            $customer = $db->getCustomerByPhone($phone);
            $response_data['customer'] = $customer;

            $response->getBody()->write(json_encode($response_data));

            return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);

        }

    }

    return $response
    ->withHeader('Content-type', 'application/json')
    ->withStatus(200);

});

$app->put('/MyApi/public/updatepassword', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('currentpassword', 'newpassword', 'phone'), $request, $response)){

         $reqBody = file_get_contents('php://input');
        parse_str($reqBody, $request_data);

        $currentpassword = $request_data['currentpassword'];
        $newpassword = $request_data['newpassword'];
        $phone = $request_data['phone'];

        $db = new DbOperations;

        $result = $db->updatePassword($currentpassword, $newpassword, $phone);

        if($result == PASSWORD_CHANGED){
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'Password Changed';
            $response->getBody()->write(json_encode($response_data));
            return $response->withHeader('Content-type', 'application/json')
                            ->withStatus(200);

        }else if($result == PASSWORD_DO_NOT_MATCH){
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'You have given wrong password';
            $response->getBody()->write(json_encode($response_data));
            return $response->withHeader('Content-type', 'application/json')
                            ->withStatus(200);
        }else if($result == PASSWORD_NOT_CHANGED){
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Some error occurred';
            $response->getBody()->write(json_encode($response_data));
            return $response->withHeader('Content-type', 'application/json')
                            ->withStatus(200);
        }
    }

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});

$app->delete('/MyApi/public/deleteuser/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];

    $db = new DbOperations;

    $response_data = array();

    if($db->deleteCustomer($id)){
        $response_data['error'] = false;
        $response_data['message'] = 'User has been deleted';
    }else{
        $response_data['error'] = true;
        $response_data['message'] = 'Plase try again later';
    }

    $response->getBody()->write(json_encode($response_data));

    return $response
    ->withHeader('Content-type', 'application/json')
    ->withStatus(200);
});


$app->post('/MyApi/public/createMenuItem', function(Request $request, Response $response){
    if(!haveEmptyParameters(array('id', 'name', 'cost', 'descrip'), $request, $response)){
        $request_data = $request->getParsedBody();
        
        $id = $request_data['id'];
        $name = $request_data['name'];
        $cost = $request_data['cost'];
        $descrip = $request_data['descrip'];
        
        $db = new menuOperations;
        
        $result = $db->createItem($id, $name, $cost, $descrip);
        
        if($result == ITEM_CREATE){
            $message = array();
            $message['error'] = false;
            $message['message'] = 'Menu Created Successfully!';
            
            $response->getBody()->write(json_encode($message));
            
            return $response->withHeader('Content-type', 'application/json')->withStatus(201);
        }
            
        else if($result == ITEM_FAIL){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Item already Created';
            
            $response->getBody()->write(json_encode($message));
            
            return $response->withHeader('Content-type', 'application/json')->withStatus(422);          
        }
    }

    return $response->withHeader('Content-type', 'application/json')->withStatus(422);
});


$app->post('/MyApi/public/createOrder', function(Request $request, Response $response){
    if(!haveEmptyParameters(array('tableNum', 'entree', 'side', 'drink', 'note', 'orderTotal', 'status'), $request, $response)){
        $request_data = $request->getParsedBody();
        
        $tableNum = $request_data['tableNum'];
        $entree = $request_data['entree'];
        $side = $request_data['side'];
        $drink = $request_data['drink'];
        $note = $request_data['note'];
        $orderTotal = $request_data['orderTotal'];
        $status = $request_data['status'];
        
        $db = new orderOperations;
        
        $result = $db->createOrder($tableNum, $entree, $side, $drink, $note, $orderTotal, $status);
        
        if($result == ORDER_CREATE){
            $message = array();
            $message['error'] = false;
            $message['message'] = 'success!';
            
            $response->getBody()->write(json_encode($message));
            
            return $response->withHeader('Content-type', 'application/json')->withStatus(201);
        }
            
        else if($result == ORDER_FAIL){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'error occured';
            
            $response->getBody()->write(json_encode($message));
            
            return $response->withHeader('Content-type', 'application/json')->withStatus(422);          
        }
    }

    return $response->withHeader('Content-type', 'application/json')->withStatus(422);
});


$app->post('/MyApi/public/createEmp', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('id', 'password', 'name', 'wage', 'role', 'hours', 'tips', 'compmeals'), $request, $response)){

        $request_data = $request->getParsedBody(); 

        $id = $request_data['id'];
        $password = $request_data['password'];
        $name = $request_data['name'];
        $wage = $request_data['wage'];
        $role = $request_data['role'];
        $hours = $request_data['hours'];
        $tips = $request_data['tips'];
        $compmeals = $request_data['compmeals'];

        $hash_password = password_hash($password, PASSWORD_DEFAULT);

        $db = new employeeOperations; 

        $result = $db->createEmp($id, $hash_password, $name, $wage, $role, $hours, $tips, $compmeals);
        
        if($result == EMP_CREATE){

            $message = array(); 
            $message['error'] = false; 
            $message['message'] = 'Employee created successfully';

            $response->getBody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(201);

        }else if($result == EMP_FAIL){

            $message = array(); 
            $message['error'] = true; 
            $message['message'] = 'Some error occurred';

            $response->getBody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);    

        }else if($result == EMP_EXISTS){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Employee Already Exists';

            $response->getBody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);    
});

$app->post('/MyApi/public/empLogin', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('id', 'password'), $request, $response)){
        $request_data = $request->getParsedBody(); 

        $id = $request_data['id'];
        $password = $request_data['password'];
        
        $db = new employeeOperations; 

        $result = $db->login($id, $password);

        if($result == EMP_AUTHENTICATED){
            
            $response_data = array();

            $response_data['error']=false; 
            $response_data['message'] = 'Login Successful';

            $response->getBody()->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);    

        }else if($result == EMP_NOT_FOUND){
            $response_data = array();

            $response_data['error']=true; 
            $response_data['message'] = 'Employeee not found';

            $response->getBody()->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);    

        }else if($result == EMP_PASSWORD_FAIL){
            $response_data = array();

            $response_data['error']=true; 
            $response_data['message'] = 'Invalid credential';

            $response->getBody()->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);  
        }
    }

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);    
});


$app->get('/MyApi/public/getItem/{id}', function(Request $request, Response $response, array $args){
        
    $id = $args['id'];
    $db = new menuOperations;
    
    $found = $db->itemExist($id);
    if($found){
        $item = $db->findItem($id);
        $message = array();
            
        $message['error'] = false;
        //$message['message'] = 'found data';
        $message['item'] = $item;
            
        $response->getBody()->write(json_encode($message));
        return $response->withHeader('Content-type', 'application/json')->withStatus(201);  
    }
    
    else{
        $message = array();
            
        $message['error'] = true;
        $message['message'] = 'no data found';
            
        $response->getBody()->write(json_encode($message));

        return $response->withHeader('Content-type', 'application/json')->withStatus(422);  
    }
});

$app->get('/MyApi/public/getOrder/{id}', function(Request $request, Response $response, array $args){
        
    $id = $args['id'];
    $db = new orderOperations;
    
    $found = $db->orderExist($id);
    if($found){
        $order = $db->findOrder($id);
        $message = array();
            
        $message['error'] = false;
        //$message['message'] = 'found data';
        $message['order'] = $order;
            
        $response->getBody()->write(json_encode($message));
        return $response->withHeader('Content-type', 'application/json')->withStatus(201);  
    }
    
    else{
        $message = array();
            
        $message['error'] = true;
        $message['message'] = 'no data found';
            
        $response->getBody()->write(json_encode($message));

        return $response->withHeader('Content-type', 'application/json')->withStatus(422);  
    }
});

$app->get('/MyApi/public/getEmp/{id}', function(Request $request, Response $response, array $args){
        
    $id = $args['id'];
    $db = new employeeOperations;
    
    $found = $db->empExist($id);
    if($found){
        $employee = $db->findEmployee($id);
        $message = array();
            
        $message['error'] = false;
        //$message['message'] = 'found data';
        $message['employee'] = $employee;
            
        $response->getBody()->write(json_encode($message));
        return $response->withHeader('Content-type', 'application/json')->withStatus(201);  
    }
    
    else{
        $message = array();
            
        $message['error'] = true;
        $message['message'] = 'no data found';
            
        $response->getBody()->write(json_encode($message));

        return $response->withHeader('Content-type', 'application/json')->withStatus(422);  
    }
});

$app->get('/MyApi/public/getItems', function(Request $request, Response $response){
    $db = new menuOperations;
    $return_array = $db->allItems();
    
    $message = array();
    $message['error'] = false;
    $message['items'] = $return_array;
    
    $response->getBody()->write(json_encode($message));
    
    return $response->withHeader('Content-type', 'application/json')->withStatus(200);  
});

$app->get('/MyApi/public/getOrders', function(Request $request, Response $response){
    $db = new orderOperations;
    $return_array = $db->allOrders();
    
    $message = array();
    $message['error'] = false;
    $message['orders'] = $return_array;
    
    $response->getBody()->write(json_encode($message));
    
    return $response->withHeader('Content-type', 'application/json')->withStatus(200);  
});

$app->get('/MyApi/public/getOrdersManager', function(Request $request, Response $response){
    $db = new orderOperations;
    $return_array = $db->allOrdersManager();
    
    $message = array();
    $message['error'] = false;
    $message['orders'] = $return_array;
    
    $response->getBody()->write(json_encode($message));
    
    return $response->withHeader('Content-type', 'application/json')->withStatus(200);  
});

$app->get('/MyApi/public/getAllEmp', function(Request $request, Response $response){
    $db = new employeeOperations;
    $return_array = $db->allEmp();
    
    $message = array();
    $message['error'] = false;
    $message['employees'] = $return_array;
    
    $response->getBody()->write(json_encode($message));
    
    return $response->withHeader('Content-type', 'application/json')->withStatus(200);  
});

$app->put('/MyApi/public/updateItem/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];

    if(!haveEmptyParameters(array('name','cost','descrip'), $request, $response)){

        $reqBody = file_get_contents('php://input');
        parse_str($reqBody, $request_data);
        $name = $request_data['name'];
        $cost = $request_data['cost'];
        $descrip = $request_data['descrip']; 

        $db = new menuOperations; 

        if($db->updateitem($id, $name, $cost, $descrip)){
            $response_data = array(); 
            $response_data['error'] = false; 
            $response_data['message'] = 'item Updated Successfully';
            $item = $db->findItem($id);
            $response_data['item'] = $item; 

            $response->getBody()->write(json_encode($response_data));

            return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);  
        
        }else{
            $response_data = array(); 
            $response_data['error'] = true; 
            $response_data['message'] = 'Please try again later';
            $item = $db->findItem($id);
            $response_data['item'] = $item; 

            $response->getBody()->write(json_encode($response_data));

            return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);  
              
        }

    }
    
    return $response
    ->withHeader('Content-type', 'application/json')
    ->withStatus(200);  
});


$app->put('/MyApi/public/changeOrder/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];

    if(!haveEmptyParameters(array('tableNum','entree','side', 'drink', 'note', 'orderTotal', 'status'), $request, $response)){

        $reqBody = file_get_contents('php://input');
        parse_str($reqBody, $request_data);
        
        $tableNum = $request_data['tableNum'];
        $entree = $request_data['entree'];
        $side = $request_data['side']; 
        $drink = $request_data['drink'];
        $note = $request_data['note'];
        $orderTotal = $request_data['orderTotal'];
        $status = $request_data['status'];

        $db = new orderOperations; 

        if($db->updateOrder($id, $tableNum, $entree, $drink, $side, $note, $orderTotal, $status)){
            $response_data = array(); 
            $response_data['error'] = false; 
            $response_data['message'] = 'Order Updated Successfully';
            $order = $db->findOrder($id);
            $response_data['order'] = $order; 

            $response->getBody()->write(json_encode($response_data));

            return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);  
        
        }else{
            $response_data = array(); 
            $response_data['error'] = true; 
            $response_data['message'] = 'Please try again later';
            $order = $db->findOrder($id);
            $response_data['order'] = $order; 

            $response->getBody()->write(json_encode($response_data));

            return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);  
              
        }

    }
    
    return $response
    ->withHeader('Content-type', 'application/json')
    ->withStatus(200);  
});

$app->put('/MyApi/public/updateEmp/{id}', function(Request $request, Response $response, array $args){




    $id = $args['id'];

    if(!haveEmptyParameters(array('name','wage','role', 'hours', 'tips', 'compmeals'), $request, $response)){

        $reqBody = file_get_contents('php://input');
        parse_str($reqBody, $request_data);
        
        $name = $request_data['name'];
        $wage = $request_data['wage'];
        $role = $request_data['role'];
        $hours = $request_data['hours'];
        $tips = $request_data['tips'];
        $compmeals = $request_data['compmeals'];

        $db = new employeeOperations; 

        if($db->updateEmployee($name, $wage, $role, $hours, $tips, $compmeals, $id)){
            $response_data = array(); 
            $response_data['error'] = false; 
            $response_data['message'] = 'Employee Updated Successfully';
            $employee = $db->findEmployee($id);
            $response_data['employee'] = $employee; 

            $response->getBody()->write(json_encode($response_data));

            return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);  
        
        }else{
            $response_data = array(); 
            $response_data['error'] = true; 
            $response_data['message'] = 'Please try again later';
            $employee = $db->getEmplByID($id);
            $response_data['employee'] = $employee; 

            $response->getBody()->write(json_encode($response_data));

            return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);  
        }

    }
    
    return $response
    ->withHeader('Content-type', 'application/json')
    ->withStatus(200);  

});

$app->delete('/MyApi/public/deleteItem/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];

    $db = new menuOperations; 

    $response_data = array();

    if($db->deleteItem($id)){
        $response_data['error'] = false; 
        $response_data['message'] = 'Item has been deleted';    
    }else{
        $response_data['error'] = true; 
        $response_data['message'] = 'Plase try again later';
    }

    $response->getBody()->write(json_encode($response_data));

    return $response
    ->withHeader('Content-type', 'application/json')
    ->withStatus(200);
});

$app->delete('/MyApi/public/deleteOrder/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];

    $db = new orderOperations; 

    $response_data = array();

    if($db->deleteOrder($id)){
        $response_data['error'] = false; 
        $response_data['message'] = 'Order has been deleted';    
    }else{
        $response_data['error'] = true; 
        $response_data['message'] = 'Plase try again later';
    }

    $response->getBody()->write(json_encode($response_data));

    return $response
    ->withHeader('Content-type', 'application/json')
    ->withStatus(200);
});

$app->delete('/MyApi/public/clearOrderQueue', function(Request $request, Response $response){
    $db = new orderOperations;
    
    $response_data = array();
    
    if($db->clearQueue()){
        $response_data['error'] = false; 
        $response_data['message'] = 'clear';
    }
    
    else{
        $response_data['error'] = true; 
        $response_data['message'] = 'Plase try again later';
    }
    return $response
    ->withHeader('Content-type', 'application/json')
    ->withStatus(200);
});

$app->delete('/MyApi/public/deleteEmployee/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];

    $db = new employeeOperations; 

    $response_data = array();

    if($db->deleteEmployee($id)){
        $response_data['error'] = false; 
        $response_data['message'] = 'Employee has been deleted';    
    }else{
        $response_data['error'] = true; 
        $response_data['message'] = 'Plase try again later';
    }

    $response->getBody()->write(json_encode($response_data));

    return $response
    ->withHeader('Content-type', 'application/json')
    ->withStatus(200);
});


$app->post('/MyApi/public/createingredient', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('food', 'amount'), $request, $response)){

        //$reqBody = file_get_contents('php://input');
        //parse_str($reqBody, $request_params);
        $request_data = $request->getParsedBody();

        $food = $request_data['food'];
        $amount = $request_data['amount'];


        //$hash_password = password_hash($password, PASSWORD_DEFAULT);

        $db = new ingredientsOperations;

        $result = $db->createFood($food, $amount);

        if($result == ING_CREATE){

            $message = array();
            $message['error'] = false;
            $message['message'] = 'Ingredient created successfully';

            $response->getbody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(201);

        }else if($result == ING_FAIL){

            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some error occurred';

            $response->getbody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);

        }else if($result == ING_EXIST){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Ingredient Already Exists';

            $response->getbody()->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
    
});

$app->get('/MyApi/public/allingredients', function(Request $request, Response $response){

    $db = new ingredientsOperations;

    $ingts = $db->getAllIngredients();

    $response_data = array();

    $response_data['error'] = false;
    $response_data['ingts'] = $ingts;

    $response->getbody()->write(json_encode($response_data));

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);

});



$app->put('/MyApi/public/updateIngredient/{id}', function(Request $request, Response $response, array $args){

    $id = $args['id'];

    if(!haveEmptyParameters(array('food', 'foodnum'), $request, $response)){

        $reqBody = file_get_contents('php://input');
        parse_str($reqBody, $request_data);
        //$request_data = $request->getParsedBody();
        //$id = $request_data['id'];
        $food = $request_data['food'];
        $foodnum = $request_data['foodnum'];
        
        $db = new ingredientsOperations;

        if($db->updateIngredients($food, $foodnum, $id)){
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'Update Successful';
            $ing = $db->getFoodByName($food);
            $response_data['ing'] = $ing;

            $response->getbody()->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }else{
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Try Again';
            $ing = $db->getFoodByName($food);
            $response_data['ing'] = $ing;

            $response->getbody()->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);
        }

    }

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);

});

$app->delete('/MyApi/public/trashfood/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];

    $db = new ingredientsOperations;

    $response_data = array();

    if($db->trashedFood($id)){
        $response_data['error'] = false;
        $response_data['message'] = 'Food Thrown Out!!';
    }else{
        $response_data['error'] = true;
        $response_data['message'] = 'Error';
    }

    $response->getbody()->write(json_encode($response_data));

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

});

$app->post('/MyApi/public/createTable', function(Request $request, Response $response){
    if(!haveEmptyParameters(array('number', 'status', 'needHelp', 'needRefill', 'orderTotal'), $request, $response)){
        $request_data = $request->getParsedBody();
        
        $number = $request_data['number'];
        $status = $request_data['status'];
        $needHelp = $request_data['needHelp'];
        $needRefill = $request_data['needRefill'];
        $orderTotal = $request_data['orderTotal'];
        
        $db = new tableOperations;
        
        $result = $db->newTable($number, $status, $needHelp, $needRefill, $orderTotal);
        
        if($result == TABLE_CREATE){
            $message = array();
            $message['error'] = false;
            $message['message'] = 'success!';
            
            $response->getBody()->write(json_encode($message));
            
            return $response->withHeader('Content-type', 'application/json')->withStatus(201);
        }
            
        else if($result == TABLE_FAIL){
            $message = array();
            $message['error'] = true;
            $message['message'] = 'error occured';
            
            $response->getBody()->write(json_encode($message));
            
            return $response->withHeader('Content-type', 'application/json')->withStatus(422);          
        }
    }

    return $response->withHeader('Content-type', 'application/json')->withStatus(422);
});

$app->get('/MyApi/public/allTables', function(Request $request, Response $response){

    $db = new tableOperations;

    $tables = $db->allTables();

    $response_data = array();

    $response_data['error'] = false;
    $response_data['tables'] = $tables;

    $response->getbody()->write(json_encode($response_data));

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);

});

$app->get('/MyApi/public/getTable/{number}', function(Request $request, Response $response, array $args){
    
    $number = $args['number'];
    $db = new tableOperations;
    
    $found = $db->tableExist($number);
    if($found){
        $table = $db->findtable($number);
        $message = array();
            
        $message['error'] = false;
        $message['table'] = $table;
            
        $response->getBody()->write(json_encode($message));
        return $response->withHeader('Content-type', 'application/json')->withStatus(201);  
    }
    
    else{
        $message = array();
            
        $message['error'] = true;
            
        $response->getBody()->write(json_encode($message));

        return $response->withHeader('Content-type', 'application/json')->withStatus(422);  
    }
});

$app->put('/MyApi/public/updateTable/{number}', function(Request $request, Response $response, array $args){

    $number = $args['number'];

    if(!haveEmptyParameters(array('status', 'needHelp', 'needRefill', 'orderTotal'), $request, $response)){

        $reqBody = file_get_contents('php://input');
        parse_str($reqBody, $request_data);

        $status = $request_data['status'];
        $needHelp = $request_data['needHelp'];
        $needRefill = $request_data['needRefill'];
        $orderTotal = $request_data['orderTotal'];
        
        $db = new tableOperations;

        if($db->updatetable($number, $status, $needHelp, $needRefill, $orderTotal)){
            $response_data = array();
            $response_data['error'] = false;
            $table = $db->findtable($number);
            $response_data['table'] = $table;

            $response->getbody()->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }else{
            $response_data = array();
            $response_data['error'] = true;
            $table = $db->findtable($number);
            $response_data['table'] = $table;

            $response->getbody()->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);
        }

    }

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);

});

$app->delete('/MyApi/public/deleteTable/{number}', function(Request $request, Response $response, array $args){
    $number = $args['number'];

    $db = new tableOperations;

    $response_data = array();

    if($db->deleteTable($number)){
        $response_data['error'] = false;
        $response_data['message'] = 'table successfully deleted';
    }else{
        $response_data['error'] = true;
        $response_data['message'] = 'Error';
    }

    $response->getbody()->write(json_encode($response_data));

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

});



//|| strlen($request_params[$param])<=0
function haveEmptyParameters($required_params, $request, $response){
    $error = false;
    $error_params = '';
    $reqBody = file_get_contents('php://input');
    parse_str($reqBody, $request_params);

    foreach($required_params as $param){
        if(!isset($request_params[$param]) ){
            $error = true;
            $error_params .= $param . ', ';
        }
    }

    if($error){
        $error_detail = array();
        $error_detail['error'] = true;
        $error_detail['message'] = 'Required parameters ' . substr($error_params, 0, -2) . ' are missing or empty';
        $response->getBody()->write(json_encode($error_detail));
        //$response->getBody()->write($error_detail);
    }
    return $error;
}

$app->run();
