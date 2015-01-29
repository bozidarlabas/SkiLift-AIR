<?php

include_once 'baza.class.php';


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


 if(isset($_GET['regId'])){
     
     $baza = new baza();
     $baza->spojiDB();
     
     $regId = $_GET['regId'];
     
     $sql = "INSERT INTO Air (registration_id) values ('$regId')";
     
     $baza->ostaliUpiti($sql);
    
     
 }
 