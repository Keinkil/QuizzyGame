////////////////////////////
//////// CATEGORIES ////////
////////////////////////////

function deleteCategory(categoryName) {
  $.ajax({
    type: "DELETE",
    url: "http://localhost:5000/api/1/category/" + categoryName,
    headers: {
      "Accept": "options"
   },
    success: function(res){
      refreshCategoryList()
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

function getCategories(type){
  $.ajax({
    type: "GET",
    url: "http://localhost:5000/api/1/category",
    headers: {
      "Accept": "application/json"
    },
    success: function(res){
      if(type === 1){
        updateCategoryList(JSON.parse(res));
      }else if(type === 2){
        updateCategoryListInQuestions(JSON.parse(res));
      }
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

////////////////////////////
//////// CATEGORIES ////////
////////     END    ////////
////////////////////////////

///////////////////////////
//////// QUESTIONS ////////
///////////////////////////

function postNewQuestion(categories, question) {
  var data = '{ "category":' + categories + '"question":' + question;
  $.ajax({
    type: "POST",
    url: "http://localhost:5000/api/1/question ",
    data: data,
    headers: {
      "Accept": "application/json"
 },
     success: function(result){     //Success sker när ett anrop går igenom.
      console.log(result);
     return result;
     },
     error: function(XMLHttpRequest, textStatus, errorThrown) {     //Error sker om anropet inte går igenom
       alert("Status: " + textStatus + " Error: " + errorThrown); //I detta fall sker då en alert


     }
   });
 }


 function deleteQuestion(questionID){
  $.ajax({
    type: "DELETE",
    url: "http://localhost:5000/api/1/question/" + questionID,
    headers: {
      "Accept": "options"
   },
    success: function(res){
        refreshDeletedQuestionList();
       console.log(res);
      
      
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) { 
      alert("Status: " + textStatus + " Error: " + errorThrown);
    }
  });
 }


function getQuestions(categoryName){
  $.ajax({
    type: "GET",
    url: "http://localhost:5000/api/1/question/" + categoryName,
    headers: {
      "Accept": "application/json"
    },
    success: function(res){
      showFilteredQuestions(JSON.parse(res));
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      alert("Status: " + textStatus + " Error: " + errorThrown);
    }
  });
}


function putChangeQuestion(questionID, question){
  var data = question;
  $.ajax({
    type: "PUT",
    url: "http://localhost:5000/api/1/question/" + questionID,
    headers: {
      "Accept": "application/json"
   },
    success: function(res){
      refreshCategoryPickerHeader();
      
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) { 
      alert("Status: " + textStatus + " Error: " + errorThrown);
    }
  });
}
