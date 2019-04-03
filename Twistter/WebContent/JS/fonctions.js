function init()
{
	noConnection=false;
	env=new Object();
	env.msgs=[];
	//SetVirtualDB();
	//document.getElementById("liste_message").innerHTML=localdb[1].getHTML() + localdb[2].getHTML() + localdb[3].getHTML();
}
function SetVirtualDB()
{
	localdb=[];
	var a1={"id":1,"login":"sly"};
	var a2={"id:":2,"login":"fab"};
	var a3={"id":4, "login":"joe"};
	//follows=[];
	//follows[1]=[2,4];
	//follows[2]=new Set();
	//follows[2].add(4);
	//follows[4]=[1];
	var c1=new Commentaire(1,"user3","hum",new Date(),0);
	localdb[1]=new Message(42,"3408748","Blabla",new Date());
	localdb[2]=new Message(43,"3408749","Hello",new Date());
	localdb[3]=new Message(44,"3408750","Buenos Dias", new Date(),c1);
}

function makeMainPanel(fromId,fromLogin,query)
{
	env.msgs=[]
	if (fromId=undefined)
	{
		fromId=-1;
	}
	env.fromId=fromId;
	env.fromLogin=fromLogin;
	console.log(env.fromLogin);
	env.query=query;
	var s="<header id=top";
	if (env.fromId < 0)
		s+="<div id=title> Actualités </div>";
	else
	{
		if (!env.follows.has(env.fromId))
		{
			s+="<div id=\"title\"> Page de " + fromLogin + "</div>";
			s+="<div class = \"add\"> <img src=\"Images/add.png\" title=\"suivre\" onclick='Javascript:follow()'></div></div>";
		}
		else
		{
			s+="<div id=\"title\"> Page de " + fromLogin + "</div>";
			s+="<div class = \"add\"> <img src=\"Images/remove.png\" title=\"suivre\" onclick='Javascript:stopfollow()'></div></div>";
		}
	}
	s+="</div> <div id=\"connect\"> <span id=\"log\" pageUser("+env.id+","+env.login+")\")>";
	s+="<div class = \"add\"> <img src=\"Images/disco.png\" title=\"suivre\" onclick='Javascript:disconnect()'></div></div>";
}
		function makeConnexionPanel(){   
			s="<div id='connexion_main'>"
			+"<h1> Ouvrir une session </h1>"
			+"<form action ='javascript:(fonction(){return;})()' method = 'post' onsubmit='return validateForm_con(this)'>"
			+"<div class = \"ids\">"
			+"<label style='padding-right:37px'> Login: </label>"
			+"<input type='text' placeholder='login' name='login'  style='margin-left:10px'/>"
			+"</div>"
			+" <div class ='ids'>"
			+"<label> Mot de passe: </label>"
			+"<input type='text' name='password' placeholder='password' />"
			+"</div> "
			+"<div class = 'connex'><div class = 'buttons'>"
			+"<input type='submit' value='connexion'  />"
			+"</div>"
			+"<div class = 'links'>"
			+"<a id='link1' href='https://www.w3schools.com'> Mot de passe perdu </a><br/>"
			+"<a id ='link2' href='javascript:void(0)' onclick='makeEnregistrementPanel()'> Pas encore inscrit? </a>"
			+"</div>"
			+"</div>"
			+"</form>"
			+"</div>";
			/*document.head.clear();
			document.head.innerHTML = entt;
			document.body.clear();
			document.body.innerHTML=s;*/
			$("head").append("<link class='link1' rel='stylesheet' href='connexion.css'>");	
			$("body").html(s);
			/*window.location.href="connexion.html";*/
		}
		function makeEnregistrementPanel(){
			s='<form class="modal_content" id="enregistre" action = "javascript:(fonction(){return;})()" method = "post" onsubmit="return validateForm(this)">'+
    '<div class="container">'+
      '<h1>Enregistrement</h1>'+
      '<p>Rejoignez Twister aujourd\'hui en créant votre compte</p>'+
      '<hr>'+
     '<div class="nom_prenom">'+
	  '<div class="nom">'+
			'<label for="nom"><b>Nom</b></label></br>'+
            '<input type="text" placeholder="Nom" name="nom" >'+
		'</div>'+  
        '<div class="prenom">'+
		    '<label for="prenom"><b>Prenom</b></label></br>'+
            '<input type="text" placeholder="Prenom" name="prenom" >'+
        '</div>'+
	  '</div>'+
	  '<div class="mdp">'+
		'<div class="login">'+
            '<label for="login"><b>Login:</b></label>'+
            '<input type="text" placeholder="Enter Login" name="login" >'+
		'</div>'+  
		'<div class="password">'+
            '<label for="psw"><b>Mot de passe:</b></label>'+
            '<input type="password" placeholder="Enter Password" name="psw" >'+
		'</div> '+ 
        '<div class="repeat">'+
           '<label for="psw_repeat"><b>Retapez:</b></label>'+
           '<input type="password" placeholder="Repeat Password" name="psw_repeat" >'+
        '</div>'+
	  '</div>'+
      '<div class="clear_fix">'+
		 '<div class="signupbtn">'+
			 '<input type="submit" value="Enregistrer"/>'+
         '</div>'+
         '<div class="cancelbtn">'+
			 '<input type="submit" value="Annuler"/>'+
         '</div>'+
      '</div>'+
    '</div>'+
  '</form>';
			$("head").append("<link class='link2' rel='stylesheet' href='Enregistrement.css'>");	
			$("body").html(s);
		}
	  function validateForm_con(form) {
		 $("#msg_err_connexion").remove();

         var login=form.login.value;
         var pass=form.password.value;
         var OK=verif_formulaire_connexion(login,pass);
         if(OK){
			 connecte(login,pass);
		 }
      }
      function verif_formulaire_connexion(l,p){
		  if(l.length==0){
			  func_erreur_connexion("Login obligatoire");
			  return false;
		  }
		  if(p.length==0){
			  func_erreur_connexion("Password obligatoire");
			  return false; 
		  }
		  return true;
	  }
	  function func_erreur_connexion(msg){
		  var msg_box="<div id=\"msg_err_connexion\">"+msg+"</div>";
		 			 
		  var old_msg=$("#msg_err_connexion");

		  if(old_msg.length==0){
			  $("form").prepend(msg_box);
			  $("#msg_err_connexion").css({"color":"red","margin-top":"10px"});
		  }
		  else{
			  old_msg.replaceWith(msg_box);
			  $("#msg_err_connexion").css({"color":"red","margin-top":"10px"});
		  }
	  }

	   function validateForm(form) {

		 $("#msg_err_connexion").remove(); 
		 var nom= form.nom.value;
		 var prenom=form.prenom.value;
         var login=form.login.value;
         var pass1=form.psw.value;
         var pass2=form.psw_repeat.value;
         var OK=verif_formulaire_enreg(nom, prenom, login,pass1,pass2);

         if(OK){
			 enregistrement(nom,prenom,login,pass1);
		 }
      }
      
      function verif_formulaire_enreg(nom,pren,l,p1,p2){
		   if(nom.length==0){
			  func_erreur_enregistrement("Nom obligatoire");
			  return false;
		  }
		   if(pren.length==0){
			  func_erreur_enregistrement("Prenom obligatoire");
			  return false;
		  }
		  if(l.length==0){
			  func_erreur_enregistrement("Login obligatoire");
			  return false;
		  }
		  if(l.length>20){
			  func_erreur_enregistrement("Bezzaf meqqar!");
			  return false;
		  }
		  if(p1.length==0){
			  func_erreur_enregistrement("Password obligatoire");
			  return false; 
		  }
		   if(p2.length==0){
			  func_erreur_enregistrement("Please, re-enter your password");
			  return false; 
		  }
		   if(p1!=p2){
			  func_erreur_enregistrement("The two passwords do not match each other");
			  return false; 
		  }
	
	
		  return true;
	  }
	 function func_erreur_enregistrement(msg){
		 var msg_box="<div id=\"msg_err_connexion\">"+msg+"</div>";
		  var old_msg=$("#msg_err_connexion");
		  if(old_msg.length==0){
			  
			  $(".mdp").append(msg_box);
			  $("#msg_err_connexion").css({"color":"red","margin-top":"10px"});
		  }
		  else{
			  old_msg.replaceWith(msg_box);
			  $("#msg_err_connexion").css({"color":"red","margin-top":"10px"});
		  }
	  }
	  function connecte(login,password){
	  	var idUser=78;
	  	var key="ABCD";
	  	if (!noConnection){
	  		$.ajax({
	  			type:"get",
	  			url:"login",
	  			data:"login="+login+"&pwd="+password,
	  			success:function (rep){
	  				connexionResponse(rep);
	  			},
	  			error:function(jqXHR,textStatus,error){
	  				alert(textStatus+" "+jqXHR+" "+error);
	  			}
	  		});
	  	}
	  	else{
	  		alert("noConnection");
	  	}
	  }

	  function connexionResponse(rep){
	  	var tab=JSON.parse(rep);
	  	if(tab.status=="OK"){
	  		env.key=tab.key;
	  		env.login=tab.id;
	  		/**************************makeMainPanel************************/
	  		$("head").remove(".link1");
	  		$("head").remove(".link2");
	  		$("head").append("<link rel='stylesheet' href='pagePricipale.css'>");	
	  		$("body").load("pagePricipale.html");//.html?????
	  		makeStatPanel(tab.key,tab.id);
	  		//refreshMessage();
	  		//alert(tab.key+" "+env.login);

	  	}
	  	else{
	  		alert(tab.key);
	  		func_erreur_connexion(tab.message);
	  	}
	  }

	  function enregistrement(name,fname,login,pwd){
	  	var idUser=78;
	  	var key="ABCD";
	  	if (!noConnection){
	  		$.ajax({
	  			type:"get",
	  			url:"createUser",
	  			data:"login="+login+"&pwd="+pwd+"&name="+name+"&fname="+fname,
	  			success:function (rep){
	  				enregistrementResponse(rep);
	  			},
	  			error:function(jqXHR,textStatus,error){
	  				alert(textStatus);
	  			}
	  		});
	  	}
	  	else{
	  		alert("noConnection");
	  	}
	  }

	  function enregistrementResponse(rep){
	  	var tab=JSON.parse(rep);
	  	if(tab.status=="OK"){
	  		alert("Enregistrement réussi :)");
	  		$(makeConnexionPanel);
	  	}
	  	else{
	  		func_erreur_enregistrement(tab.message);
	  	}
	  }
	  function makeStatPanel(key,login){
	  		if (!noConnection){
	  		$.ajax({
	  			type:"get",
	  			url:"listFriend",
	  			data:"key="+key+"&idUser="+login,
	  			success:function (rep){
	  				statResponse(rep);
	  			},
	  			error:function(jqXHR,textStatus,error){
	  				alert(textStatus);
	  			}
	  		});
	  	}
	  	else{
	  		alert("noConnection");
	  	}
	  }

	  function statResponse(rep){
	  	var tab=JSON.parse(rep);
	  	env.follows=tab.amis;
	  	env.nbr=tab.nbr;
	  	if(tab.status=="OK"){
	  		 $(".left").append('<p class="user" >'+env.login+'</p>');
	  		 $(".left").append('<p class="nbr_amis" onMouseOver="changeColor(this);" onmouseout="resetColor(this);" onclick="listFriend();" > Followers: <b>'+tab.nbr+'</b></p>');
	  	}
	  	else{
	  		alert("error");
	  	}
	  	//env.follows.push(tab.amis[0]);
	  }
	

	  function changeColor(node,list){
	  	 $(node).css('color','#1a8cff');
	  }
	  function resetColor(node){
	  	$(node).css('color','#000000');
	  }

	  function listFriend(){
	  	    //env.follows[env.login]=new set();	
	  
	  	if (env.nbr>0){
	  	    s='<nav class="amis" > <div class="ferme"><span onclick="fermer()" class="x">X</span> <br> </div><ul class="listFriend">';
	  	    for (var i=0;i<env.follows.length;i++){
	  	    	//alert(env.follows[0]);
	  	    	s+='<li> <a href="javascript:affichAmi();">'+env.follows[i]+'</a></li>';
	  	    	env.fromId=env.follows[i];
	  	    }
	  	    s+='</ul> </nav>';
	  	    $(".left").append(s);   
	  	}
	  }
	  function fermer(){
	  	$(".amis").hide();
	  }

	  function affichAmi(){
	    $(".nv_msg").hide();
	     $(".left").hide();
	      $(".right").hide();
	    var s='<nav class="ami_courant" ><div class="ferme2"><span onclick="fermer2()" class="x">X</span> <br> </div>';
	    	s+='<div class="haut"> </div>';
	    	s+='<div class="id">'+env.fromId+'</div> ';
	    	s+='<button class="unFollow" onclick="#">unFollow</button></nav>';
	    	$(".middle").append(s);
	    	$(".haut").css('width','50px');
	    	$(".haut").css('background-color','#1a8cff');
	    	$(".ami_courant").css('background-color','#e6fffa');
	    	$(".ami_courant").css('height','200px');
	  }
	  function fermer2(){
	  	$(".ami_courant").hide();
	  	$(".nv_msg").show();
	  	$(".left").show();
	  	$(".right").show();
	  }

	  function addMessage(){
	  	 if (!noConnection){
	  		$.ajax({
	  			type:"get",
	  			url:"addMessage",
	  			data:"key="+env.key+"&message="+$("#subject").val(),
	  			success:function (rep){
	  				messageResponse(rep);
	  			},
	  			error:function(jqXHR,textStatus,error){
	  				alert(textStatus+" "+jqXHR+" "+error);
	  			}
	  		});
	  	}
	  	else{
	  		alert("noConnection");
	  	}
	  }

	  function messageResponse(rep){
	  	var tab=JSON.parse(rep);
	  	///alert($("#subject").val());
	  	refreshMessage();
	  }

	  function refreshMessage(){

	  	if(! noConnection){
	  		$.ajax({
	  			type :"get",
	  			url : "listMessage",
	  			data :"key="+env.key,
	  			async: false,
	  			success : function(rep){
	  				refreshMessageResponse(rep);
	  			},
	  			error:function(jqXHR, textStatus, error){
	  				alert(textStatus + " " + jqXHR +" " + error);
	  			}
	  		});
	  	}
	  	else{
	  		alert("noConnection");
	  	}
	  }

	  function refreshMessageResponse(rep){
	  	var tab = JSON.parse(rep, revival);
	  	for(var i = tab.message.length - 1; i >= 0; i--){
	  		var m =tab.message[i];
	  		$("#liste_messages").prepend(m.getHTML());
	  		env.msgs[m.id] = m;

	  	}
	  }

	function revival(key,value){
		if (value.comments!=undefined)
		{
			var c = new Message(value.id, value.auteur, value.text, value.date, value.comments);
			return c;
		}
		else if (value.comment!=undefined)
		{
			var c = new Commentaire(value.id,value.auteur,value.comment,value.date);
			return c;
		}
		else if (key == "date")
		{
			//var d = new Date(value);
			return value;
		}
		return value;
	}
	function addLike(node){
		env.idMsg_cour=$(node).attr('id');
		env.node_cour=node;
	  	if(! noConnection){
	  		$.ajax({
	  			type :"get",
	  			url : "addLike",
	  			data :"idMessage="+$(node).attr('id'),
	  			async: false,
	  			success : function(rep){
	  				addLikeResponse(rep,node);
	  			},
	  			error:function(jqXHR, textStatus, error){
	  				alert(textStatus + " " + jqXHR +" " + error);
	  			}
	  		});
	  	}
	  	else{
	  		alert("noConnection");
	  	}
	  }
	  function addLikeResponse(rep,node){
	  	env.msgs[env.idMsg_cour].nbLikes=env.msgs[env.idMsg_cour].nbLikes+1;
	  	$(node).next().text(env.msgs[env.idMsg_cour].nbLikes);
	  }

	  function listComments(node){
	  	env.idMsg_cour=$(node).attr('id');

	  }

	  function viewComments(node){
	  	env.idMsg_cour=$(node).attr('id');
	  	if(! noConnection){
	  		$.ajax({
	  			type :"get",
	  			url : "listComments",
	  			data :"idMessage="+$(node).attr('id')+"&key="+env.key,
	  			async: false,
	  			success : function(rep){
	  				viewCommentsResponse(rep,node);
	  			},
	  			error:function(jqXHR, textStatus, error){
	  				alert(textStatus + " " + jqXHR +" " + error);
	  			}
	  		});
	  	}
	  	else{
	  		alert("noConnection");
	  	}
	  }

	  function viewCommentsResponse(rep,node){
	  	var tab = JSON.parse(rep, revival);
	  	alert(tab.comments.length);
	  	for(var i = tab.comments.length - 1; i >= 0; i--){
	  		var c =tab.comments[i];
	  		s='<div class="ferme3"><span onclick="fermer3()" class="x">X</span></div>';
	  		$("#list_comments").prepend(s);
	  		$("#list_comments").prepend(c.getHTML());
	  		//env.msgs[m.id] = m;

	  	}
	  }

	  function fermer3(){
	  	$("#list_comments").hide();
	  }

/* PROFIL */
function chargerProfilUtlisateur(){
	/*$("#body").load("../HTML/test.txt", function(responseTxt, statusTxt, xhr){
		$.ajax({
			url : "../HTML/pageProfil.html",
			async : false;
			success : function(res){
				$("body").html();
			}
		});
		if(statusTxt == "success")
            alert("External content loaded successfully!");
        if(statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
    
	});*/
	s = "<header id=\"header\" class=\"w3-container w3-teal\">" + 
		"<div id=\"part1\"></div>" +
		"<div id=\"part2\">	</div>" +
		"<div id=\"imageProfil\">" +
			"<img id=\"profilPicture\" src=\"..\\images\\profil.jpg\" alt=\"Photo de profil\">" +
			"<button id=\"edit\"> Editer profil</button>" +
		"</div>" +
	"</header>" +
	"<div id=\"content\" class=\"w3-container\">" +
		"<div id=\"informations\">" +
			"<span id=\"prenom\"> Prénom </span> " +
			"<span id=\"login\"> Login</span>" +
		"</div>" +
		"<div id=\"tweet\">" +
			"<div id=\"newTweet\">" + 
				"<textarea id=\"subject\" name=\"subject\" placeholder=\"Quoi de neuf?\"></textarea>" +
				"<button class=\"post\" id=\"tweeter\" onclick=\"#\">Tweeter</button>" +
			"</div>" +
			"<div id=\"tweets\">" +
				"<div class=\"msg\" id=\"t1\">" +
					
				"</div>" +
				"<div class=\"msg\" id=\"t2\">" +
					
				"</div>" +
				"<div class=\"msg\" id=\"t3\">" +
					
				"</div>" +
			"</div>" +
		"</div>" +
	"</div>";
	$("head").remove(".link1");
	$("head").remove(".link2");
	$("head").append("<link class='link2' rel='stylesheet' href='../CSS/pageProfil.css'>");	
	//$("body").html(s);
	$("body").load("../HTML/pageProfil.html");
	alert("done");
}
