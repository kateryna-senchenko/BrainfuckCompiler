<!DOCTYPE html>
<html>

<head>
<script>
function execute() {

    var memory = prepareArray();
    var pointer = 0;
    var result = "";
    ${data}

   document.getElementById("demo").innerHTML = result;
   }

   function prepareArray() {
        var array = new Array(100);
        for(var i=0; i<array.length; i++)
            array[i] = 0;
        return array;
   }

</script>
</head>

<body>

<p id="demo">Check the output</p>
<button type="button" onclick="execute()">Click</button>

</body>

</html>
