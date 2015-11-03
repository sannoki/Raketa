
$(document).ready(function(){ 
    $("#btn").click(function(){
        $.post("./startTrees", 
            {    "full":$("#mas_full").val(),
                 "gruz":$("#mas_gruz").val(),
                 "massa":$("#mas_rocet").val()
             },
                function(data){
                    $("#test").text(data);
                    if (data==="true")
                        {   $("#d4").addClass("start");
                            }
   });
                      
    });
});
              
 