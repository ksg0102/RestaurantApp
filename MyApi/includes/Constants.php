<?php

    define('DB_HOST', 'localhost');
    define('DB_USER', 'root');
    define('DB_PASSWORD', '123456');
    define('DB_NAME', 'restaurantapp');

    define('USER_CREATED', 101);
    define('USER_EXISTS', 102);
    define('USER_FAILURE', 103);

    define('USER_AUTHENTICATED', 201);
    define('USER_NOT_FOUND', 202);
    define('USER_PASSWORD_DO_NOT_MATCH', 203);

    define('PASSWORD_CHANGED', 301);
    define('PASSWORD_DO_NOT_MATCH', 302);
    define('PASSWORD_NOT_CHANGED', 303);
    define('EMP_AUTHENTICATED', 304);
    define('EMP_PASSWORD_FAIL', 305);
    define('EMP_NOT_FOUND', 306);


     define('ITEM_CREATE', 401);
     define('ITEM_FAIL', 403);


     define('ORDER_CREATE', 501);
     define('ORDER_FAIL', 503);

     define('EMP_CREATE', 601);
     define('EMP_EXISTS', 602);
     define('EMP_FAIL', 603);
     
    
    /*define('USER_CREATED', 401);
    define('USER_EXISTS', 402);
    define('USER_FAILURE', 403);

    define('USER_AUTHENTICATED', 501);
    define('USER_NOT_FOUND', 502);
    define('USER_PASSWORD_DO_NOT_MATCH', 503);*/

    define('SURVEY_CREATED', 901);
   define('SURVEY_FAILED', 902);
  //define('PASSWORD_NOT_CHANGED', 603);
    
    define('TABLE_CREATE', 701);
    define('TABLE_FAIL', 702);
    define('TABLE_EXIST', 703);

    define('ING_CREATE', 801);
    define('ING_FAIL', 802);
    define('ING_EXIST', 803);