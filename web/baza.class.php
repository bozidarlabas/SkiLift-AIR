<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



class baza{
   
    const server = "localhost";
    const baza = "WebDiP2013_079";
    const korisnik = "WebDiP2013_079";
    const lozinka = "admin_5fZs";
    
    
    
    function spojiDB(){
        
        $mysqli = new mysqli(self::server, self::korisnik,  self::lozinka, self::baza);
        $mysqli->set_charset("utf8_general_ci");
        return $mysqli;
    }
    function selectUpit($upit){
     $veza = self::spojiDB();
        $rezultat = $veza->query($upit) or trigger_error("Greska kod upita: {$upit} - Greska: ".$veza->error.'',E_USER_ERROR);
       
        if($rezultat){
            self::prekidVeze($veza);
        }
        
        if(!$rezultat){
           $rezultat = null;
       }
       
       return $rezultat;
    }
    
    function ostaliUpiti($upit){
    
        $veza = self::spojiDB();
        
        if($veza->query($upit)){
            self::prekidVeze($veza);
        }
        
        else {
            trigger_error("Greska prilikom pokretanja ostalog upita!", E_USER_ERROR);
        }
       
        
        return true;
    }
    
    function prekidVeze($mysqli){
        $mysqli ->close();
    }
    
} //kraj klase baze
