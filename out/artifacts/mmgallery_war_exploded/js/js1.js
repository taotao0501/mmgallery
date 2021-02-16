var flag=false//全局变量
function show_menu(){
		var title1=document.getElementById("menu1");
		if(flag){
			title1.style.display="none";
			flag=false;
		}else{
			title1.style.display="block";
			flag=true;
		}
}
function show_menu1(){
		var title1=document.getElementById("menu1");
			title1.style.display="none";
			flag=false;
}
