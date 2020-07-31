function openForm() {
	document.getElementById("popupForm").style.display = "block";
}

function closeForm() {
	document.getElementById("popupForm").style.display = "none";
}

//SIDEBAR generating random colors for icons
var colors = [ '#A7ACD9', '#70A9A1','#E26D5C', '#EF959C', '#C2C094','#AE8CA3', '#817F82', '#D7FDEC'];
var groupicons = document.querySelectorAll('#group-img');
for(let i = 0; i < groupicons.length; i++){
	groupicons[i].style.background = colors[i];
}
