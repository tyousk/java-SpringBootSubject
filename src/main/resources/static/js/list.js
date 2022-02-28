
function clickBtn1(){
	const all  = document.getElementById("all_employees");
	const now  = document.getElementById("now_employees");
	const fire = document.getElementById("fire_employees");

	if(all.style.display=="none"){
		// noneで非表示
		all.style.display ="";
	}

	if(!(now.style.display=="none")){
		// noneで非表示
		now.style.display ="none";
	}

	if(!(fire.style.display=="none")){
		// noneで非表示
		fire.style.display ="none";
	}
}

function clickBtn2(){

	const all  = document.getElementById("all_employees");
	const now  = document.getElementById("now_employees");
	const fire = document.getElementById("fire_employees");

	if(!(all.style.display=="none")){
		// noneで非表示
		all.style.display ="none";
	}

	if(now.style.display=="none"){
		// noneで非表示
		now.style.display ="";
	}

	if(!(fire.style.display=="none")){
		// noneで非表示
		fire.style.display ="none";
	}
}

function clickBtn3(){
	const all  = document.getElementById("all_employees");
	const now  = document.getElementById("now_employees");
	const fire = document.getElementById("fire_employees");

	if(!(all.style.display=="none")){
		// noneで非表示
		all.style.display ="none";
	}

	if(!(now.style.display=="none")){
		// noneで非表示
		now.style.display ="none";
	}

	if(fire.style.display=="none"){
		// noneで非表示
		fire.style.display ="";
	}
}

