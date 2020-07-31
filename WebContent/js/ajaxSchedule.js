function goBack(parameter) {
	//alert("Ajax go to " + parameter);
	populateTable(parameter);
}

function goForward(parameter) {
	//alert("Ajax go to " + parameter);
	populateTable(parameter);
}

function goGroup(parameter) {
	//alert("Ajax group go to " + parameter);
	populateTable(parameter);
}

function goToday(parameter){
//	alert("TODAY");
	populateTable(parameter);
}

function populateTable(parameter) {
	
	//alert("I am here populateTable!");
	
    $.ajax({
        method : "POST",
        contentType : "application/json",
        url : "ajax.jsp?ajaxid="+parameter, //ajax id is the parameter we'll get on the server side
        dataType :  'json',
        timeout : 100000,
        success : function(result) {
        	$("#schedule").html(result);
        },
        error : function(e) {
            console.log("ERROR in ajax locad: ", e);
        }
    });
  
}


function setSession(name, email) {
	
	//alert("setSession - " + name + " - " + email);
	
    $.ajax({
        method : "POST",
        contentType : "application/json",
        url : "ajaxse.jsp?ajaxid="+email + "&nameid=" + name, 
        dataType :  'json',
        timeout : 100000,
        success : function(result) {
        	console.log("successful");
        },
        error : function(e) {
            console.log("ERROR in ajax locad: ", e);
        }
    });
  
}

