$(document).on('click', '.nav-link', function(){
	console.log("hello");
	$(".nav-link").removeClass("active-link");
	$(this).addClass("active-link");
});

//for pop up

$(document).on("click", '.pop-up-btn', function(){
	$("#loginPopup").toggle();
}); 

$(".close").on("click", function(){
	$("#loginPopup").toggle();
});

$