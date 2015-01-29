<?php
    require 'Simulations.php';
    $sim = new Simulations();
    if(isset($_GET['run'])){
        $sim->runSimulation();
    }

    if(isset($_GET['lift']) && isset($_GET['mode'])){
        $index = $_GET['lift'];
        $mode = $_GET['mode'];
        if($sim->changeStatus($index,$mode)){
            echo 1;
            exit(1);
        }
        
    }
    
    if(isset($_POST['lift']) && isset($_POST['liftPercentage'])){
        if($sim->changePercentage($_POST['lift'],$_POST['liftPercentage'])){
            echo 1;
            exit(1);
        }
        
    }
    
?>




<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ski lift waiting time simulation</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Add custom css -->
    <link href="css/skilift.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <div id="description" class="container">
        <h1>Ski lift waiting time simulation</h1>
        <p>The script will run simulation of percentages (capacity 0-100) for all (ten) lifts. Press the button <i>Run simulation</i> in order to show results of the simulation.</p>
        <form action="index.php" method="get">
            <button type="submit" name="run" id="btn_run">Run simulation</button>
        </form>
    </div>
    <!--
    <h3 style="text-align: center">Graph panel</h3>
    <div id="container" class="container">

    </div>
    <h3 style="text-align: center">Lift panel</h3>
    <div id="lift" class="container">

    </div>
    -->
    <div class="container">
    <div class="row">
        <div id="lift" class="col-xs-6"><h3 style="text-align: center">Lift panel</h3></div>
        <div id="container" class="col-xs-6"></div>
    </div>
    </div>


        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/highcharts.js"></script>
    <script type="text/javascript" src="js/getJSON.js"></script>
</body>
</html>