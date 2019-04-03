function Commentaire(id,login,text,date){
	this.id=id;
	this.auteur=login;
	this.text=text;
	this.date=date;
}

Commentaire.prototype.getHTML=function(){
	return "<div id='message_"+this.id+"'class='message'> <div class='text_massage'>"
	+this.text+"</div> <div class='info.message'> <span> Poste de <span class='link' onclick='javascript.pageUser'"
	+this.auteur.id+"'/> </span></div></div>";
}


<div class='nv_msg'>
		nv_msg
	</div>
	<div class='list_msg'>
		list_msg
	</div>
