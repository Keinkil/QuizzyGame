function deleteCategory(categoryName) {
  $.ajax({
    type: "DELETE",
    url: "http://localhost:5000/api/1/category/" + categoryName,
    headers: {
      "Accept": "options"
   },
    success: function(res){
      console.log(res);
      removeCategory(res);
      
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) { 
      alert("Status: " + textStatus + " Error: " + errorThrown);
    }
  });
}

function putChangedCategory(oldCategoryName, newCategoryName) {

  var data = newCategoryName;
  $.ajax({
    type: "PUT",
    url: "http://localhost:5000/api/1/category/" + oldCategoryName,
    data: data,
    headers: {
      "Accept": "application/json"
    },
   success: function(result){     //Success sker när ett anrop går igenom.

     refreshCategoryList();
       console.log(result);
     },
     error: function(XMLHttpRequest, textStatus, errorThrown) {     //Error sker om anropet inte går igenom
       alert("Status: " + textStatus + " Error: " + errorThrown); //I detta fall sker då en alert
       alert("Couldn't rename your category: " + oldCategoryName);

     }
   });
 }

function getCategories(){
  var html = " ";
  $.ajax({
    type: "GET",
    url: "http://localhost:5000/api/1/category",
    headers: {
      "Accept": "application/json"
    },
    success: function(res){
      updateCategoryList(JSON.parse(res));
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      alert("Status: " + textStatus + " Error: " + errorThrown);
    }
  });
}

 function postNewCategory(categoryName){          //Såhär kan addCategory() (notera namnbytet på metoden) implementeras. Notera parametern också.
  var data = '{ "name":' + categoryName + '}';
   $.ajax({
    type: "POST",
    url: "http://localhost:5000/api/1/category",
    data: data,
     headers: {
      "Accept": "application/json"
     },
     success: function(result){     //Success sker när ett anrop går igenom.

     showNewlyAddedCategory(result);
       console.log(result);
     },
     error: function(XMLHttpRequest, textStatus, errorThrown) {     //Error sker om anropet inte går igenom
       alert("Status: " + textStatus + " Error: " + errorThrown); //I detta fall sker då en alert
       alert("Couldn't create your category: " + categoryName);

     }
   });
 }


function postQ() {
  var idVal = $('#newQuestion input[name=name]').val();
  var data = {};
  //data.name = $('#newQuestion input[name=name]').val();//-->
  data.name = document.getElementById("catDrop").value;
  data.question = $('#newQuestion input[name=question]').val();
  $.ajax({
    type: "POST",
    url: "http://localhost:5000/api/1/category/ ",
    data: JSON.stringify(data),
    headers: {
      "Accept": "application/json"
    }
  }).done(function(res) {
      console.log(res + data.name + data.question);
    }
  )
};

function showQ() {
  var idVal = document.getElementById("catDrop").value;
  $.ajax({
    type: "GET",
    url: "http://localhost:5000/api/1/category/ " + idVal,
    headers: {
      "Accept": "application/json"
    }
  }).done(function(res) {
    console.log(res + "mannen");
  })
};
