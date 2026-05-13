<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>IT-Lab2</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <script type="text/javascript" src="script.js"></script>
    <script type="text/javascript" src="quadratic.js"></script>
</head>
<body onload="functions(); clock(); setInterval(clock,1000); hook();">
<div id="content">
    <form id="data" name="data">
        <input placeholder="a" type="number" id="coeff_a" name="ca">x<sup>2</sup>+
        <input placeholder="b" type="number" id="coeff_b" name="cb">x+
        <input placeholder="c" type="number" id="coeff_c" name="cc"><br/>
        <div id="button">
            <input type="button" id="calculate" value="Calculate">
        </div>
    </form>
    <div id="list">
    </div>
</div>
</body>
</html>