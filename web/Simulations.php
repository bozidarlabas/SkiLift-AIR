<?php
/**
 * Created by PhpStorm.
 * User: jtrupina
 * Date: 11.11.2014.
 * Time: 13:10
 */

class Simulations {

    public $lifts;
    public $min, $max;

    public function __construct(){
        $this->min = 0;
        $this->max = 100;
        $this->lifts = array('lift1','lift2','lift3','lift4','lift5','lift6','lift7','lift8','lift9','lift10');
    }

    public function runSimulation(){

        $res = array();

        foreach($this->lifts as $lift){
            $temp = array();
            $rand = rand($this->min,$this->max);
            $temp["name"] = $lift;
            $temp["percentage"] = $rand;
            $temp["status"] = 1;
            array_push($res,$temp);


        }
        $this->makeJson($res);

    }

    private function makeJson($array){
        $fp = fopen('simulations.json', 'w');
        usort( $array, 'Simulations::sortArray');
        fwrite($fp, json_encode($array));
        fclose($fp);
    }

    public function changeStatus($index,$mode){
        $jsonString = file_get_contents('simulations.json');
        $data = json_decode($jsonString, true);
        $data[$index]['status'] = $mode;
        if($mode == 1){
            $data[$index]['percentage'] = 0;
        }
        else{
            $data[$index]['percentage'] = 200;
        }
        
        
        //usort( $data, 'Simulations::sortArray');

        $newJsonString = json_encode($data);
        file_put_contents('simulations.json', $newJsonString);
        
        $this->sendNotification();
        
        return true;
    }
    
    public function changePercentage($index,$liftPercentage){
        $jsonString = file_get_contents('simulations.json');
        $data = json_decode($jsonString, true);
        $data[$index]['percentage'] = $liftPercentage;
        
        //usort( $data, 'Simulations::sortArray');
        
        $newJsonString = json_encode($data);
        file_put_contents('simulations.json', $newJsonString);
        
        $this->sendNotification();
        return true;
    }
    
    public function sortArray($a,$b){
          return $a['percentage'] == $b['percentage'] ? 0 : ( $a['percentage'] > $b['percentage'] ) ? 1 : -1;
        
    }
    
    private function sendNotification(){
        include_once 'baza.class.php';
     
      
      
      $baza = new baza();
     $baza->spojiDB();
        
        $registatoin_ids = array();
   $sql = "SELECT * FROM Air";
   $result = $baza->selectUpit($sql);
   while($row = mysqli_fetch_assoc($result)){
    array_push($registatoin_ids, $row['registration_id']);
   }
 
   // Set POST variables
         $url = 'https://android.googleapis.com/gcm/send';
   
         $json_data = file_get_contents('simulations.json');
         $json = json_decode($json_data, true);

         
        $message = array();
        //$message = array("Notice" => $_POST['message']);
        foreach ($json as $key => $value){
            $message[$value['name']] = $value['percentage'];
        }
        
        
         $fields = array(
             'registration_ids' => $registatoin_ids,
             'data' => $message,
         );
   
         //echo $fields['data'];
         
         $headers = array(
             'Authorization: key=AIzaSyDw1dJ7G9c03mxM3qGsrEfBtOK1jCy9f_M',
             'Content-Type: application/json'
         );
        
         
       
         
         //echo $message;
         // Open connection
         $ch = curl_init();
   
         // Set the url, number of POST vars, POST data
         curl_setopt($ch, CURLOPT_URL, $url);
   
         curl_setopt($ch, CURLOPT_POST, true);
         curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
         curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
   
         // Disabling SSL Certificate support temporarly
         curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
   
         curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
         
       
         
         // Execute post
         $result = curl_exec($ch);
         if ($result === FALSE) {
             die('Curl failed: ' . curl_error($ch));
         }
   
         // Close connection
         curl_close($ch);
    } //Send Notification about change
    
} 