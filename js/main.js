//////////////////////////////////////
//////// UI AND FUNCTIONALITY ////////
//////////////////////////////////////

////////////////////////////
//////// CATEGORIES ////////
////////////////////////////
function removeCategory(category){
  var categoryName = $(category).text();
  //TODO Call API with name of category in order to remove it
  alert("Removed category: " + categoryName);

  //Refresh list after call
  refreshCategoryList();
};

function editCategory(category){
  $("#dialogModal").modal();
  var categoryName = $(category).text();
  $("#dialogModalTitel").empty();
  $("#dialogModalTitel").html(categoryName);

  $("#dialogModalBody").empty();
  $("#dialogModalBody").append(
      '<label for="categoryNameEdit">Rename category:</label>'
    + '<br><input type="text" class="form-control" id="categoryNameEdit" value="' + categoryName + '">'
  );

  $("#okDialogButton").remove();
  $("#dialogModalFooter").append(
      '<button type="button" class="btn btn-primary" '
    + 'data-dismiss="modal" '
    + 'id="okDialogButton" onclick="sendEditCategory(\'' + categoryName + '\')">Ok</button>'
  );
}

function sendEditCategory(editCategory){
  var newCategoryName = $("#categoryNameEdit").val();
  if(newCategoryName != ""){
    //TODO AJAX CALL
    alert("Ajax call - oldCategoryName: " + editCategory + " newCategoryName: " + newCategoryName);

    //TODO Refresh list if successful call was made to API
    refreshCategoryList();
  }else{
    alert("Didn't save changes. You must enter at least one character.")
  }

}

function addNewCategory(){
  var categoryName = $('#categoryName').val();
  var total = $(".contentMain li").length;
  if(categoryName != ""){
    //TODO Call create new category (API)
    //Use categoryName when you call API
    //If successful do below
    $(".contentMain" ).append(
        '<li class="list-group-item" id="category' + total + '">' + categoryName
      + '<span class="glyphicon glyphicon-trash"  onclick="removeCategory(category' + total + ')"></span>'
      + '<span class="glyphicon glyphicon-pencil" onclick="editCategory  (category' + total + ')"></span>'
      + '</li>'
    );
    $("#categoryName").val('');

    //EXAMPLE: så här måste funktionen göras om för att kopplas med AJAX-anropet.

    // if(postNewCategory(categoryName)){       //Detta är alltså vad som menas med att göra en call i kommentarerna under TODO.
    //   $(".contentMain" ).append(
    //       '<li class="list-group-item" id="category' + total + '">' + categoryName
    //     + '<span class="glyphicon glyphicon-trash"  onclick="removeCategory(category' + total + ')"></span>'
    //     + '<span class="glyphicon glyphicon-pencil" onclick="editCategory  (category' + total + ')"></span>'
    //     + '</li>'
    //   );
    //   $("#categoryName").val('');
    // }else{
    //   alert("Couldn't create your category: " + categoryName);
    // }
  }else{
    alert("You must enter at least one character.")
  }
}

function refreshCategoryList(){
  //TODO Get categoryList from API, replace dummy-data below
  var categoryArr = ["Dogs", "Cats", "Birds", "Cars", "Sausages", "Icecream"];
  $( ".contentMainHeader").empty();
  $( ".contentMain").empty();
  $( ".contentMainHeader" ).append(
      '<h2>Category</h2>'
    + '<hr>'
    + '<div class="titelCreate">'
    + '<h4>Create new category</h4>'
    + '<label for="categoryName">Enter category name:</label><br><input type="text" class="form-control" id="categoryName">'
    + '<button type="button" class="btn btn-success" onclick="addNewCategory()" id="buttonAddCategory">Add new category</button>'
    + '</div>'
  );

  $( ".contentMain" ).append('<ul class="list-group">');
  for(var i = 0; i<categoryArr.length; i++){
    $( ".contentMain" )
      .append(
        '<li class="list-group-item" id="category' + i + '">' + categoryArr[i]
      + '<span class="glyphicon glyphicon-trash"  onclick="removeCategory(category' + i + ')"></span>'
      + '<span class="glyphicon glyphicon-pencil" onclick="editCategory(category' + i + ')"></span>'
      + '</li>'
    );
  }
  $( ".contentMain" ).append('</ul>');
}
////////////////////////////
//////// CATEGORIES ////////
////////     END    ////////
////////////////////////////

///////////////////////////
//////// QUESTIONS ////////
///////////////////////////
function addNewQuestion(){
  //TODO values from below need to be sent to API as well! Look at comment below!
  var question = $("#createNewQuestion").val();
  var answer = $("#createNewQuestionAnswer").val();
  var categories = $("#multiBoxCategory").val();
  if(question != "" && answer != "" && categories != ""){
    var files  = document.getElementById('my-file-selector');
    var file = files.files[0];
    var reader  = new FileReader();
    if(typeof files.files[0] === 'undefined'){
      //TODO post to API (without file)
      //TODO If successful refresh UI
      refreshCategoryPickerHeader();
    }else{
      reader.readAsDataURL(file);
      reader.onload = function () {
        console.log(reader.result);//this is the base64 encdoded file as string
        //TODO post to API with file
        //TODO If successful refresh UI
        refreshCategoryPickerHeader();
      };
    }
  }else{
    alert("Didn't create a new question. Please enter question, answer and choose at least one category.")
  }
}

function removeQuestion(question){
  var questionName = $(question).text();
  var questionID = $(question).val();
  //TODO Call API with questionID and remove it
  alert("Removed question: " + questionName + " questionID: " + questionID);

  //Refresh list after call
  var selectedCategory = $('#selectCategoryHeader').val();
  showFilteredQuestions(selectedCategory);
};

function editQuestion(question){
  $("#dialogModal").modal();
  var questionName = $(question).text();
  var questionID = $(question).val();
  //TODO API, get all selected categories with questionID, store in prevSelected
  var prevSelected = ["Sausages", "Dogs"];

  $("#dialogModalTitel").empty();
  $("#dialogModalTitel").html(questionName);
  $("#dialogModalBody").empty();
  $("#dialogModalBody").append(
      '<label>Rename question:</label>'
    + '<br><input type="text" class="form-control" id="questionNameEdit" value="' + questionName + '">'
    + '<br><br><label>Change categories:</label><br>'
    + '<select multiple="" id="multiBoxCategoryEditQuestion" class="form-control select-checkbox" size="3">'
    + '</select><br>'
  );

  $("#multiBoxCategory option").each(function(){
      var add = $(this).val();
      $( "#multiBoxCategoryEditQuestion").append(
          '<option>' + add + '</option>'
      );
  });

  $('#multiBoxCategoryEditQuestion').val(prevSelected).trigger('chosen:updated');

  $("#okDialogButton").remove();
  $("#dialogModalFooter").append(
      '<button type="button" class="btn btn-primary" '
    + 'data-dismiss="modal" '
    + 'id="okDialogButton" onclick="sendEditQuestion(\'' + questionID + '\')">Ok</button>'
  );
}

function sendEditQuestion(questionID){
  var selectedCategories = $('#multiBoxCategoryEditQuestion').val();
  var newQuestion = $('#questionNameEdit').val();
  if(selectedCategories != "" && newQuestion != ""){
    //TODO AJAX CALL
    alert("Ajax call - questionID: " + questionID + " newQuestion: " + newQuestion + " selectedCategories: " + selectedCategories);
  }else{
    alert("Didn't save changes. Please enter at least one character and choose at least one category.")
  }
}

function refreshCategoryPickerHeader(){
  $( ".contentMain").empty();
  $( ".contentMainHeader").empty();

  //TODO Get categoryList from API, replace dummy-data below
  var categoryArr = ["Dogs", "Cats", "Birds", "Cars", "Sausages", "Icecream"];
  $( ".contentMainHeader" ).append(
      '<h2>Question</h2>'
    + '<hr>'
    + '<h4>Create new question</h4>'
    + '<div class="titelCreate">'
    + '<label>Enter question:</label><br>'
    + '<input type="text" class="form-control" id="createNewQuestion" value=""><br>'
    + '<label>Enter correct answer:</label><br>'
    + '<input type="text" class="form-control" id="createNewQuestionAnswer" value=""><br>'

    + '<label>Choose one or more categories:</label><br>'
    + '<select multiple="" id="multiBoxCategory" class="form-control select-checkbox" size="3">'
    + '</select><br>'

    + '<label>Upload multimedia (optional):</label><br>'
    + '<label class="btn btn-default" for="my-file-selector">'
    + '<input id="my-file-selector" type="file" style="display:none" '
    + 'onchange="$(\'#upload-file-info\').html(this.files[0].name)">'
    + 'Browse'
    + '</label>'
    + '<span class="label label-info" id="upload-file-info"></span><br><br>'

    + '<button type="button" class="btn btn-success" onclick="addNewQuestion()" id="buttonAddNewQuestion">Add new question</button>'

    + '</div>'
    + '<hr>'
    + '<label for="selectCategoryHeader">Fetch questions from:</label><br>'
    + '<select class="form-control" id="selectCategoryHeader">'
    + '</div>'
  );
  //Adds empty string so nothing is preselectedS
  $( "#selectCategoryHeader").append(
      '<option></option>'
  );
  for(var i = 0; i<categoryArr.length; i++){
    $( "#selectCategoryHeader").append(
        '<option>' + categoryArr[i] + '</option>'
    );
    $( "#multiBoxCategory").append(
        '<option>' + categoryArr[i] + '</option>'
    );
  }

  $('#selectCategoryHeader').change(function(){
    var data = $(this).val();
    if(data != ""){
      showFilteredQuestions(data);
    }
  });

};

function showFilteredQuestions(category){
  $(".contentMain").empty();
  //TODO replace questionsArr with the result from the API call (use category to call api)
  //TODO the call should give the answers and the IDs
  var questionsArr = [["Who loves candy?", 22], ["Who hates candy?", 33], ["Who eats most fruit?", 99], ["Who is superfunny?", 124]];

  $(".contentMain").append('<ul class="list-group">');
  for(var i = 0; i<questionsArr.length; i++){
    $( ".contentMain" )
      .append(
        '<li class="list-group-item" value="' + questionsArr[i][1] + '" id="question' + i + '">' + questionsArr[i][0]
      + '<span class="glyphicon glyphicon-trash"  onclick="removeQuestion(question' + i + ')"></span>'
      + '<span class="glyphicon glyphicon-pencil" onclick="editQuestion(question' + i + ')"></span>'
      + '</li>'
    );
  }
  $( ".contentMain" ).append('</ul>');
}
///////////////////////////
//////// QUESTIONS ////////
////////    END    ////////
///////////////////////////


///////////////////////////
////////  ANSWERS  ////////
///////////////////////////
function refreshAnswers(){
  //TODO replace questionsArr with the result from the API call (use category to call api)
  //TODO the call should give the answers and the IDs
  var questionsArr = [["Who loves candy?", 22], ["Who hates candy?", 33], ["Who eats most fruit?", 99], ["Who is superfunny?", 124]];
  $( ".contentMainHeader").empty();
  $( ".contentMain").empty();
  $( ".contentMainHeader" ).append(
      '<h2>Answer</h2>'
    + '<hr>'
    + '<div class="titelCreate">'
    + '<h4>Create new answer</h4>'
    + '<label for="selectQuestion">Fetch answers to:</label><br>'
    + '<select class="form-control" id="selectQuestion"></select><br>'
    + '</div>'
  );

  $( "#selectQuestion").append(
      '<option></option>'
  );
  for(var i = 0; i<questionsArr.length; i++){
    $( "#selectQuestion").append(
        '<option value="' + questionsArr[i][1] + '">' + questionsArr[i][0] + '</option>'
    );
  }

  $('#selectQuestion').change(function(){
    var questionID = $(this).val();
    if(questionID != ""){
      listAnswers(questionID);
    }
  });
}

function listAnswers(questionID){
  //TODO get answers based on questionsID
  //TODO the API-call should give the answerID, answer, correctAnswer(true/false), placeholder below (API should most likely return correct answer first so it's listed on top???)
  var answersArr = [["Albert Einstein", 11, true], ["Abraham Lincoln", 33, false], ["Elon Musk", 99, false], ["Bill Gates", 124, false]];
  $(".contentMain").empty();
  $(".contentMain").append(
      '<ul class="list-group">'
    + '<label for="answerName">Enter answer:</label><br><input type="text" class="form-control" id="answerName">'
    + '<button type="button" class="btn btn-success" onclick="addNewAnswer()" id="buttonAddAnswer">Add new answer</button>'
  );

  for(var i = 0; i<answersArr.length; i++){
    var styleRowLi = "";
    var styleRowEdit = "";
    var trashcan = "";
    if(answersArr[i][2]){
      styleRowLi = 'style="background-color:green;"';
      styleRowEdit = 'style="margin-right:24px;"';
    }else{
      trashcan = '<span class="glyphicon glyphicon-trash"  onclick="removeAnswer(answer' + i + ')"></span>';
    }

    $(".contentMain").append(
        '<li class="list-group-item" ' + styleRowLi + ' value="' + answersArr[i][1] + '" id="answer' + i + '">' + answersArr[i][0]
      + trashcan
      + '<span class="glyphicon glyphicon-pencil" ' + styleRowEdit + ' onclick="editAnswer(answer' + i + ')"></span>'
      + '</li>'
    );
  }
  $( ".contentMain" ).append('</ul>');
}

function editAnswer(answer){
  $("#dialogModal").modal();
  var answerName = $(answer).text();
  var answerID = $(answer).val();

  $("#dialogModalTitel").empty();
  $("#dialogModalTitel").html(answerName);
  $("#dialogModalBody").empty();
  $("#dialogModalBody").append(
      '<label>Change answer:</label>'
    + '<br><input type="text" class="form-control" id="answerNameEdit" value="' + answerName + '">'
  );

  $("#okDialogButton").remove();
  $("#dialogModalFooter").append(
      '<button type="button" class="btn btn-primary" '
    + 'data-dismiss="modal" '
    + 'id="okDialogButton" onclick="sendEditAnswer(\'' + answerID + '\')">Ok</button>'
  );
}

function sendEditAnswer(answerID){
  var newAnswerName = $("#answerNameEdit").val();
  if(newAnswerName != ""){
    //TODO AJAX CALL edit answer look below
    alert("Ajax call - answerID: " + answerID + " newAnswerName: " + newAnswerName);
    //TODO Refresh list if successful call was made to API
    var questionID = $('#selectQuestion').val();
    listAnswers(questionID);
  }else{
    alert("Didn't save changes, answer needs to be at least one character!")
  }
}

function addNewAnswer(){
  var questionID = $('#selectQuestion').val();
  var answerName = $('#answerName').val();
  var total = $(".contentMain li").length;

  if(answerName != "" && questionID != ""){
    //TODO Call create answer category (API)
    //Use answerName and questionID when you call API
    //If successful do below
    $(".contentMain" ).append(
        '<li class="list-group-item" id="answer' + total + '">' + answerName
      + '<span class="glyphicon glyphicon-trash"  onclick="removeAnswer(answer' + total + ')"></span>'
      + '<span class="glyphicon glyphicon-pencil" onclick="editAnswer  (answer' + total + ')"></span>'
      + '</li>'
    );
    $("#answerName").val('');
  }else {
    alert("Please enter your answer and choose a question!");
  }
}

function removeAnswer(answer){
  var answerID = $(answer).val();
  var answerName = $(answer).text();
  //TODO Call API with id
  alert("Removed question: " + answerName + " answerID: " + answerID);
  var selectedCategory = $('#selectCategoryHeader').val();

  //Refresh list after call
  var questionID = $('#selectQuestion').val();
  listAnswers(questionID);
};
///////////////////////////
////////  ANSWERS  ////////
////////    END    ////////
///////////////////////////

//////////////////////////
////////   MAIN   ////////
//////////////////////////
$(window).on('load', function(){
  $("#categoriesMenu").on('click', function() {
    refreshCategoryList();
  });

  $("#questionsMenu").on('click', function() {
    refreshCategoryPickerHeader();
  });

  $("#answersMenu").on('click', function() {
    refreshAnswers();
    $( ".contentMain").empty();
  });

  $('a').click(function(){
      $('.active').removeClass('active');
      $(this).addClass('active');
  });
});
//////////////////////////
////////   MAIN   ////////
////////   END    ////////
//////////////////////////
