//logge inn
function logIn(bruker, passord) {

      var xhr = new XMLHttpRequest();
      xhr.open("POST", "http://192.168.56.101:3000/androidLogin/", true);
      xhr.setRequestHeader("Content-Type", "text/xml");
      xhr.onreadystatechange = function() {
         if (xhr.readyState === 4)
         {
             if(this.responseText === 'true'){
                document.getElementById('login').style.display = 'block';
                document.getElementById('feil').style.display = 'none';
              }
             else{
             document.getElementById('login').style.display = 'none';
                //document.getElementById('feil').innerHTML=this.res
                document.getElementById('feil').style.display = 'block';
              }
             //document.getElementById('username').innerHTML = bruker;
         }
       };
       //var body = ;
     xhr.send("<brukernavn>" + bruker + "</brukernavn><passord>" + passord + "</passord>");
     //xhr.send("brukernavn=" + bruker+ "&pass="+passord);
     console.log(bruker + " " + passord);



      //window.location.replace("login.html");
}
