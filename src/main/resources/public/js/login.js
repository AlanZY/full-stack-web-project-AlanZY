function show()
{
div1.style.display="inline";
div1.style.width=body.clientWidth;
div1.style.height=body.clientHeight;
div2.style.display="inline";
div2.style.top=body.clientHeight/2-div2.clientHeight/2;
div2.style.left=body.clientWidth/2-div2.clientWidth/2;
}

//close the window
function closeShow()
{
div1.style.display="none";
div2.style.display="none";
}


function sign_switch(){  
     var temp2=document.getElementById('login_in_color').className ;
	 if(temp2!='change_login')
	 {
	  document.getElementById('sign_up_color').className = 'change_sign';
	 }
	
	 
}  

function login_switch(){  

	 var temp1=document.getElementById('sign_up_color').className ;
	 if(temp1!='change_sign')
	 {
	  document.getElementById('login_in_color').className = 'change_login';

	 }
	
	
	 
}  